package com.games.QuizConnect.model.dto.response;

import com.games.QuizConnect.model.entity.Category;
import lombok.Data;

@Data
public class ViewCategoryResponseDTO {

    private Integer id;

    private String name;

    private String description;

    public static ViewCategoryResponseDTO fromEntity(Category category) {
        ViewCategoryResponseDTO viewCategoryResponseDTO = new ViewCategoryResponseDTO();
        viewCategoryResponseDTO.setId(category.getId());
        viewCategoryResponseDTO.setName(category.getName());
        viewCategoryResponseDTO.setDescription(category.getDescription());
        return viewCategoryResponseDTO;
    }
}
