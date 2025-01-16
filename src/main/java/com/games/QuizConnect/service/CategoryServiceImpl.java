package com.games.QuizConnect.service;

import com.games.QuizConnect.model.entity.Category;
import com.games.QuizConnect.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Integer addCategory(String name, String description) {
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        category = categoryRepository.saveAndFlush(category);
        return category.getId();
    }
}
