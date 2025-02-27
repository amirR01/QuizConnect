package com.games.QuizConnect.service;

import com.games.QuizConnect.model.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();

    Integer addCategory(String name, String description);
}
