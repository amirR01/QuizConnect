package com.games.QuizConnect.service;

import com.games.QuizConnect.model.entity.User;
import com.games.QuizConnect.model.enums.UserType;
import com.games.QuizConnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    final private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addUser(String username, String password, UserType userType) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setUserType(userType);

        userRepository.saveAndFlush(user);
    }

    @Override
    public List<User> getAllPlayers() {
        return userRepository.findAll();
    }

    @Override
    public void followDesigner(Integer playerId, Integer designerId) {
        User player = userRepository.findById(playerId).orElseThrow(() -> new IllegalArgumentException("Player not found"));
        User designer = userRepository.findById(designerId).orElseThrow(() -> new IllegalArgumentException("Designer not found"));

        player.getPlayerDetails().getFollowedDesigners().add(designer);
        userRepository.saveAndFlush(player);
    }
}