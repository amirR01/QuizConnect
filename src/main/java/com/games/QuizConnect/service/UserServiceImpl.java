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

    // TODO: use Spring Security to authenticate users and store current user in the session
    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Incorrect password");
        }
        return user;
    }

    public Integer addUser(String username, String password, UserType userType) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setUserType(userType);
        user.setPlayerDetails(new User.PlayerDetails());
        user.setDesignerDetails(new User.DesignerDetails());

        user = userRepository.saveAndFlush(user);
        return user.getId();
    }

    public List<User> getAllPlayers() {
        return userRepository.findAll();
    }

    public void followDesigner(Integer playerId, Integer designerId) {
        User player = userRepository.findById(playerId).orElseThrow(() -> new IllegalArgumentException("Player not found"));
        User designer = userRepository.findById(designerId).orElseThrow(() -> new IllegalArgumentException("Designer not found"));

        player.getPlayerDetails().getFollowedDesigners().add(designer);
        userRepository.saveAndFlush(player);
    }
}