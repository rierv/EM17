package com.ingsw1718.ingswinspectorapp.entity;

import com.ingsw1718.ingswinspectorapp.common.Date;
import com.ingsw1718.ingswinspectorapp.entity.DocumentTypeEnum.DocumentType;

/**
 * 
 */
public class Document extends Identificator {

    /**
     * Default constructor
     */
    public Document(String id, DocumentType documentType, String documentNumber, Date documentExpirationDate) {
        System.out.println("Created a Document with id " + id);
        this.id = id;
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.documentExpirationDate = documentExpirationDate;
        System.out.println(documentExpirationDate);
    }

    private String id;

    private DocumentType documentType;

    private String documentNumber;

    private Date documentExpirationDate;

    @Override
    public String getIdentificatorType() {
        return Identificator.TYPE_DOCUMENT;
    }

    public String getId() {
        return id;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public Date getDocumentExpirationDate() {
        return this.documentExpirationDate;
    }

    public boolean equals(Object object) {
        return object != null && object instanceof Document && ((Document)(object)).getDocumentExpirationDate().equals((this.getDocumentExpirationDate())) && ((Document)(object)).getDocumentNumber().equals(this.getDocumentNumber()) && ((Document)(object)).getDocumentType().equals(this.getDocumentType());
    }
}