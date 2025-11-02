package com.solarize.solarizeWebBackend.modules.client.strategy;

import com.solarize.solarizeWebBackend.shared.exceptions.InvalidDocumentException;
import org.springframework.stereotype.Component;

@Component
public class CnpjValidatorImpl implements DocumentStrategy{
    @Override
    public void validate(String documentNumber) {
        if (!documentNumber.matches("\\d{14}")) {
            throw new InvalidDocumentException("Invalid CNPJ format. Must contain 14 digits.");
        }
    }
}
