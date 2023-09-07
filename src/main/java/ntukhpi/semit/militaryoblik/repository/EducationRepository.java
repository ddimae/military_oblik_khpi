package ntukhpi.semit.militaryoblik.repository;

import ntukhpi.semit.militaryoblik.entity.Document;
import ntukhpi.semit.militaryoblik.entity.Education;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {
    List<Education> findAllByPrepod(Prepod prepod);
}