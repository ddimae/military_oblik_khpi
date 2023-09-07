package ntukhpi.semit.militaryoblik.repository;

import ntukhpi.semit.militaryoblik.entity.Education;
import ntukhpi.semit.militaryoblik.entity.EducationPostgraduate;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationPostgraduateRepository extends JpaRepository<EducationPostgraduate, Long> {
    List<EducationPostgraduate> findAllByPrepod(Prepod prepod);
}