package ntukhpi.semit.militaryoblik.repository;

import ntukhpi.semit.militaryoblik.entity.MilitaryPerson;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MilitaryPersonRepository extends JpaRepository<MilitaryPerson, Long> {
    MilitaryPerson findMilitaryPersonByPrepod(Prepod prepod);
}