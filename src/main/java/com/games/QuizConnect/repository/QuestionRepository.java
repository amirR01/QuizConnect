package com.games.QuizConnect.repository;

import com.games.QuizConnect.model.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    List<Question> findAllByCategoryId(Integer categoryId);

    List<Question> findAllByDesignerId(Integer designerId);

    @Query(value =
            """
                    SELECT * FROM question q
                    LEFT JOIN user_question_attempt uqa ON q.id = uqa.question_id
                    WHERE uqa.question_id IS NULL AND
                    (:designerId IS NULL OR q.designer_id = :designerId) AND
                    (:categoryId IS NULL OR q.category_id = :categoryId)
                    """
            , nativeQuery = true
    )
    public List<Question> findNotAttemptedQuestions(
            Integer designerId,
            Integer categoryId
    );
}
