package com.solarize.solarizeWebBackend.modules.client.strategy;

import com.solarize.solarizeWebBackend.shared.exceptions.InvalidDocumentException;
import org.springframework.stereotype.Component;

@Component
public class CpfValidatorImpl implements DocumentStrategy{
    @Override
    public void validate(String documentNumber) {
        if (!documentNumber.matches("\\d{11}")) {
            throw new InvalidDocumentException("Invalid CPF format. Must contain 11 digits.");
        }
    }
}
