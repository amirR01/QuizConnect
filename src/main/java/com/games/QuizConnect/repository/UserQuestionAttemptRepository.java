package com.games.QuizConnect.repository;

import com.games.QuizConnect.model.entity.UserQuestionAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserQuestionAttemptRepository extends JpaRepository<UserQuestionAttempt, Integer> {
    boolean existsByUserIdAndQuestionId(Integer userId, Integer questionId);

}
