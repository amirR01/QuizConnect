package com.games.QuizConnect.model;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class BaseResponseDTO<T> {
    private T data;
    private Integer status;
    private String errorMessage;

    public static <T> BaseResponseDTO<T> ok(T data) {
        BaseResponseDTO<T> response = new BaseResponseDTO<>();
        response.setData(data);
        response.setStatus(HttpStatus.OK.value());
        return response;
    }

    public static <T> BaseResponseDTO<T> error(String errorMessage) {
        BaseResponseDTO<T> response = new BaseResponseDTO<>();
        response.setErrorMessage(errorMessage);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return response;
    }
}
