package com.solarize.solarizeWebBackend.modules.client.strategy;

import com.solarize.solarizeWebBackend.shared.exceptions.InvalidDocumentException;
import org.springframework.stereotype.Component;

@Component
public class RgValidatorImpl implements DocumentStrategy {
    @Override
    public void validate(String documentNumber) {
        if (!documentNumber.matches("[0-9]{5,14}[A-Za-zXx]?")) {
            throw new InvalidDocumentException("Invalid RG format. Should have 5–14 digits and may end with a letter (e.g., X).");
        }
    }
}
