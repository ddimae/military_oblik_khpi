package ntukhpi.semit.militaryoblik.repository;

import ntukhpi.semit.militaryoblik.entity.Document;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findAllByPrepod(Prepod prepod);

    List<Document> findAllByPrepodId(Long id);
}