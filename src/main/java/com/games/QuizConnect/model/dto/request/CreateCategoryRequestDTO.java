package com.games.QuizConnect.model.dto.request;

import com.games.QuizConnect.model.BaseDTO;
import lombok.Data;

@Data
public class CreateCategoryRequestDTO extends BaseDTO {

    private String name;

    private String description;

    @Override
    public void validate() {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
    }
}
