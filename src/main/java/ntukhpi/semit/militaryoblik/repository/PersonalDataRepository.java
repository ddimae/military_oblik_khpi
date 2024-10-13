package ntukhpi.semit.militaryoblik.repository;

import ntukhpi.semit.militaryoblik.entity.PersonalData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalDataRepository extends JpaRepository<PersonalData, Long> {

    PersonalData findPersonalDataByPrepodId(Long id);
}