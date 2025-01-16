package com.games.QuizConnect.repository;

import com.games.QuizConnect.model.entity.Question;
import com.games.QuizConnect.model.enums.QuestionDifficulty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    @Query(value = """
            SELECT q FROM Question q
            WHERE (:designerId IS NULL OR q.designer.id = :designerId) AND
            (:categoryId IS NULL OR q.category.id = :categoryId) AND
            (:difficulty IS NULL OR q.difficulty = :difficulty)""")
    List<Question> findAllQuestions(
            Integer designerId,
            Integer categoryId,
            QuestionDifficulty difficulty
    );

    @Query(value =
            """
                    SELECT * FROM question q
                    LEFT JOIN 
                    (SELECT * from user_question_attempt inner_uqa 
                    where inner_uqa.user_id = :playerId ) uqa 
                    ON q.id = uqa.question_id
                    WHERE uqa.question_id IS NULL AND
                    (:designerId IS NULL OR q.designer_id = :designerId) AND
                    (:categoryId IS NULL OR q.category_id = :categoryId) AND 
                    (:difficulty IS NULL OR q.difficulty = :difficulty)
                    """
            , nativeQuery = true
    )
    List<Question> findNotAttemptedQuestions(
            Integer playerId,
            Integer designerId,
            Integer categoryId,
            QuestionDifficulty difficulty
    );
}
