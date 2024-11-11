package ntukhpi.semit.militaryoblik.service.entitycrud;

import ntukhpi.semit.militaryoblik.adapters.DocumentAdapter;
import ntukhpi.semit.militaryoblik.entity.Document;
import ntukhpi.semit.militaryoblik.service.DocumentService;
import ntukhpi.semit.militaryoblik.service.PrepodServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class DocumentCRUD {

    private final String MSG_DOUBLE_DOC_NUMBER = "Документ із таким номером є у базі даних!";

    @Autowired
    PrepodServiceImpl prepodService;

    @Autowired
    DocumentService documentService;

    public void addDocument(Long idPerson, DocumentAdapter adapter) {
        Document newDocument = createNewInstance(idPerson, adapter);
        Document newDocumentInDB = documentService.getDocumentByExample(newDocument);
        if (newDocumentInDB == null) {
            documentService.createDocument(newDocument);
        } else {
            throw new RuntimeException(MSG_DOUBLE_DOC_NUMBER);
        }

    }

    public void updateDocument(Long idDoc, Long idPersonForUpdate, DocumentAdapter adapter) {
        Document documentUpdate = createNewInstance(idPersonForUpdate, adapter);
        Document newDocumentInDB = documentService.getDocumentByExample(documentUpdate);
        if (newDocumentInDB == null || (newDocumentInDB != null && newDocumentInDB.getId()==idDoc)) {
            documentService.updateDocument(idDoc, documentUpdate);
        } else {
            throw new RuntimeException(MSG_DOUBLE_DOC_NUMBER);
        }
    }

    public void deleteDocument(Long idDelDocument) {
        documentService.deleteDocument(idDelDocument);
    }


    private Document createNewInstance(Long idPerson, DocumentAdapter adapter) {

        Document newDocument = new Document();

        newDocument.setPrepod(prepodService.getPrepodById(idPerson));
        newDocument.setDocType(adapter.getType());
        newDocument.setDocNumber(adapter.getNumber());
        newDocument.setKtoVyd(adapter.getWhoGives());
        newDocument.setDataVyd(LocalDate.parse(adapter.getDate(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        return newDocument;
    }
}
