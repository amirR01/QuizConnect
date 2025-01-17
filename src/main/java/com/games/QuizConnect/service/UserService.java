package com.games.QuizConnect.service;

import com.games.QuizConnect.model.entity.User;
import com.games.QuizConnect.model.enums.UserType;

import java.util.List;

public interface UserService {
    User login(String username, String password);

    Integer addUser(String username, String password, UserType userType);

    List<User> getAllPlayers();

    List<User> getAllDesigners();

    void followDesigner(Integer playerId, Integer designerId);

    void unfollowDesigner(Integer playerId, Integer designerId);

    void followPlayer(Integer playerId, String playerToFollowUsername);

    void unfollowPlayer(Integer playerId, String playerToUnfollowUsername);


}
