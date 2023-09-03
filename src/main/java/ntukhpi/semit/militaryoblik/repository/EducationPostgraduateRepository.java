package ntukhpi.semit.militaryoblik.repository;

import ntukhpi.semit.militaryoblik.entity.EducationPostgraduate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationPostgraduateRepository extends JpaRepository<EducationPostgraduate, Long> {
}