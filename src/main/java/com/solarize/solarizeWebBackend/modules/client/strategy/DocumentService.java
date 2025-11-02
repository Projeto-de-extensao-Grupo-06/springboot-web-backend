package com.solarize.solarizeWebBackend.modules.client.strategy;

import com.solarize.solarizeWebBackend.modules.client.DocumentTypeEnum;
import com.solarize.solarizeWebBackend.shared.exceptions.InvalidDocumentException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DocumentService {

    private final Map<String, DocumentStrategy> documentStrategyMap;

    public DocumentService() {
        this.documentStrategyMap = Map.of(
                DocumentTypeEnum.CPF.name(), new CpfValidatorImpl(),
                DocumentTypeEnum.CNPJ.name(), new CnpjValidatorImpl(),
                DocumentTypeEnum.RG.name(), new RgValidatorImpl(),
                DocumentTypeEnum.PASSPORT.name(), new PassportValidatorImpl()
        );
    }

    public void validateDocument(DocumentTypeEnum documentType, String documentNumber) {
        DocumentStrategy strategy = documentStrategyMap.get(documentType.name());

        if (strategy == null) {
            throw new InvalidDocumentException("Invalid document type: " + documentType);
        }

        strategy.validate(documentNumber);
    }
}
