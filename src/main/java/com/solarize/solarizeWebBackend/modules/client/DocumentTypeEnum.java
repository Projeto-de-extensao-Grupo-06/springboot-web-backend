package com.solarize.solarizeWebBackend.modules.client;

import com.solarize.solarizeWebBackend.modules.client.strategy.*;

public enum DocumentTypeEnum {
    CPF(new CpfValidatorImpl()),
    RG(new RgValidatorImpl()),
    CNPJ(new CnpjValidatorImpl()),
    PASSPORT(new PassportValidatorImpl());

    public final DocumentStrategy strategy;

    DocumentTypeEnum(DocumentStrategy strategy) {
        this.strategy = strategy;
    }

}
