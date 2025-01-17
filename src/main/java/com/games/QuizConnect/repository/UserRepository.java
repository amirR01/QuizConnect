package com.games.QuizConnect.repository;

import com.games.QuizConnect.model.entity.User;
import com.games.QuizConnect.model.enums.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    List<User> findAllByUserType(UserType userType);
}
