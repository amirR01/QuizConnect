package com.games.QuizConnect.utils;

import java.util.List;
import java.util.Random;

public class Utils {
    public static <T> T getRandomElement(List<T> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }
}
