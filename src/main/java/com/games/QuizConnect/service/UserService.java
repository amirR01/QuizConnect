package com.games.QuizConnect.service;

import com.games.QuizConnect.model.entity.User;
import com.games.QuizConnect.model.enums.UserType;

import java.util.List;

public interface UserService {
    Integer login(String username, String password);

    Integer addUser(String username, String password, UserType userType);

    List<User> getAllPlayers();

    void followDesigner(Integer playerId, Integer designerId);
}
