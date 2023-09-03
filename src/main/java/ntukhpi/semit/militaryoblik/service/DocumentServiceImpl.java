package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.Document;
import ntukhpi.semit.militaryoblik.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentServiceImpl(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    public Document createDocument(Document document) {
        return documentRepository.save(document);
    }

    @Override
    public Document getDocumentById(Long id) {
        return documentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Document> getAllDocument() {
        return documentRepository.findAll();
    }

    @Override
    public Document updateDocument(Long id, Document updatedDocument) {
        Document existingDocument = documentRepository.findById(id).orElse(null);
        if (existingDocument != null) {
            updatedDocument.setId(existingDocument.getId());
            return documentRepository.save(updatedDocument);
        }
        return null;
    }

    //ToDo change when special conditions was presented
    @Override
    public void deleteDocument(Long id) {
        documentRepository.deleteById(id);
    }
}
