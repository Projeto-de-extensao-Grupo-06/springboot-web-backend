package com.solarize.solarizeWebBackend.modules.client.strategy;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import com.solarize.solarizeWebBackend.shared.exceptions.InvalidDocumentException;
import org.springframework.stereotype.Component;

@Component
public class CpfValidatorImpl implements DocumentStrategy{
    private final CPFValidator validator = new CPFValidator();

    @Override
    public void validate(String documentNumber) {
        try {
            validator.assertValid(documentNumber);
        } catch (InvalidStateException e) {
            throw new InvalidDocumentException("Invalid CPF: " + documentNumber);
        }
    }
}
