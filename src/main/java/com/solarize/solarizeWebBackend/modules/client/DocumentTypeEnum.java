package com.solarize.solarizeWebBackend.modules.client;

public enum DocumentTypeEnum {
    CPF("cpf"),
    RG("rg"),
    CNPJ("cnpj"),
    PASSPORT("passport");

    public String value;

    DocumentTypeEnum(String value) {
        this.value = value;
    }
}
