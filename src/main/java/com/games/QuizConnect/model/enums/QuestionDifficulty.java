package com.games.QuizConnect.model.enums;

public enum QuestionDifficulty {
    EASY,
    MEDIUM,
    HARD;

    public Long getScore() {
        return switch (this) {
            case EASY -> 1L;
            case MEDIUM -> 2L;
            case HARD -> 3L;
            default -> 0L;
        };
    }
}
