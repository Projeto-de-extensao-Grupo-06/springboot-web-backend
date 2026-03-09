package com.solarize.solarizeWebBackend.shared.config;

import java.text.Normalizer;

public class TextFunctions {
    public static String unaccent(String input) {
        if (input == null) return null;

        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
}
