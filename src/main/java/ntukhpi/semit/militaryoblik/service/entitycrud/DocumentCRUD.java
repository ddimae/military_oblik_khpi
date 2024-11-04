package ntukhpi.semit.militaryoblik.service.entitycrud;

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

    public void addDocument(Long idPerson, String docType, String number,
                            String whoGives, String dateFromDataPicker) {
        Document newDocument = createNewInstance(idPerson, docType, number,
                whoGives, dateFromDataPicker);
        Document newDocumentInDB = documentService.getDocumentByExample(newDocument);
        if (newDocumentInDB == null) {
            documentService.createDocument(newDocument);
        } else {
            throw new RuntimeException(MSG_DOUBLE_DOC_NUMBER);
        }

    }

    public void updateDocument(Long idDoc, Long idPersonForUpdate, String docType, String number,
                               String whoGives, String dateFromDataPicker) {
        Document documentUpdate = createNewInstance(idPersonForUpdate, docType, number,
                whoGives, dateFromDataPicker);
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


    private Document createNewInstance(Long idPerson, String docType, String number,
                                       String whoGives, String dateFromDataPicker) {

        Document newDocument = new Document();

        newDocument.setPrepod(prepodService.getPrepodById(idPerson));
        newDocument.setDocType(docType);
        newDocument.setDocNumber(number);
        newDocument.setKtoVyd(whoGives);
        newDocument.setDataVyd(LocalDate.parse(dateFromDataPicker, DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        return newDocument;
    }
}
