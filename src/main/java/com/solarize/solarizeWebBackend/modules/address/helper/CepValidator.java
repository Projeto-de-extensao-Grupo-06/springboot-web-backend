package com.solarize.solarizeWebBackend.modules.address.helper;

public class CepValidator {
    private static final String CEP_REGEX = "^\\d{5}-?\\d{3}$";

    public static boolean isValid(String cep) {
        if (cep == null) return false;
        return cep.matches(CEP_REGEX);
    }
}
