package com.example.testWork.service;

public class Converter {
    static final String BASE_62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String convert() {
        StringBuilder build = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int result = (int) (Math.random() * BASE_62.length());
            build.append(BASE_62.charAt(result));
        }
        return String.valueOf(build);
    }
}
