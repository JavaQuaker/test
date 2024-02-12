package com.example.testWork.service;

public class Converter {
    static final String base62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String convert() {
        StringBuilder build = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int result = (int) (Math.random() * base62.length());
            build.append(base62.charAt(result));
        }
        return String.valueOf(build);
    }
}
