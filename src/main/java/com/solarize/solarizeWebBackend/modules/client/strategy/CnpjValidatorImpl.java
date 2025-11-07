package com.solarize.solarizeWebBackend.modules.client.strategy;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import com.solarize.solarizeWebBackend.shared.exceptions.InvalidDocumentException;
import org.springframework.stereotype.Component;

@Component
public class CnpjValidatorImpl implements DocumentStrategy{
    private final CNPJValidator validator = new CNPJValidator();

    @Override
    public void validate(String documentNumber) {
        try {
            validator.assertValid(documentNumber);
        } catch (InvalidStateException e) {
            throw new InvalidDocumentException("Invalid CNPJ: " + documentNumber);
        }
    }
}
