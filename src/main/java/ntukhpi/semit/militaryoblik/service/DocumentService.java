package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.Document;

import java.util.List;

public interface DocumentService {

    Document createDocument(Document document);

    Document getDocumentById(Long id);

    List<Document> getAllDocument();

    Document updateDocument(Long id, Document updatedDocument);

    void deleteDocument(Long id);
}
