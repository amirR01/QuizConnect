package com.games.QuizConnect.model.dto.request;

import com.games.QuizConnect.model.BaseRequestDTO;
import lombok.Data;

@Data
public class CreateCategoryRequestRequestDTO extends BaseRequestDTO {

    private String name;

    private String description;

    @Override
    public void validate() {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
    }
}
