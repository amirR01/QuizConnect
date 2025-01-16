package com.games.QuizConnect.controller;

import com.games.QuizConnect.model.dto.request.CreateCategoryRequestDTO;
import com.games.QuizConnect.model.dto.response.IdResponseDTO;
import com.games.QuizConnect.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<?> addCategory(
            @RequestBody CreateCategoryRequestDTO createCategoryRequestDTO
    ) {
        createCategoryRequestDTO.validate();
        try {
            Integer categoryId = categoryService.addCategory(
                    createCategoryRequestDTO.getName(),
                    createCategoryRequestDTO.getDescription()
            );
            IdResponseDTO response = new IdResponseDTO();
            response.setId(categoryId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
