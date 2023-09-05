package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.Document;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;

import java.util.List;

public interface DocumentService {

    Document createDocument(Document document);

    Document getDocumentById(Long id);

    List<Document> getAllDocument();

    List<Document> getAllDocumentByPrepod(Prepod prepod);

    Document updateDocument(Long id, Document updatedDocument);

    void deleteDocument(Long id);
}
