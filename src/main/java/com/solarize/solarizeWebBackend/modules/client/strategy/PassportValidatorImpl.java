package com.solarize.solarizeWebBackend.modules.client.strategy;

import com.solarize.solarizeWebBackend.shared.exceptions.InvalidDocumentException;
import org.springframework.stereotype.Component;

@Component
public class PassportValidatorImpl implements DocumentStrategy{
    @Override
    public void validate(String documentNumber) {
        if (!documentNumber.matches("[A-Za-z0-9]{5,15}")) {
            throw new InvalidDocumentException("Invalid Passport format. Must contain 5–15 alphanumeric characters.");
        }
    }
}
