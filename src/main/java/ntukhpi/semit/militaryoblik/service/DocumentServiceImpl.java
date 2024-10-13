package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.Document;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.repository.DocumentRepository;
import org.hibernate.Transaction;
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
    public List<Document> getAllDocumentByPrepod(Prepod prepod) {
        return documentRepository.findAllByPrepod(prepod);
    }

    @Override
    public Document updateDocument(Long id, Document updatedDocument) {     //TODO Чи точно потрібно так складно?
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

    @Override
    public List<Document> getDocumentsByPrepodId(Long id) {
        return documentRepository.findAllByPrepodId(id);
    }

    @Override
    public Document getDocumentByExample(Document docToFind) {
        return documentRepository.findDocumentByDocNumber(docToFind.getDocNumber());
    }

    @Override
    public void deleteDocumentsByPrepod(Prepod prep) {
        List<Document> list = documentRepository.findAllByPrepodId(prep.getId());
        for (Document doc: list) {
            documentRepository.deleteById(doc.getId());
        }
    }
}
