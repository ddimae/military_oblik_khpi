package ntukhpi.semit.militaryoblik.repository;

import ntukhpi.semit.militaryoblik.entity.MilitaryPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MilitaryPersonRepository extends JpaRepository<MilitaryPerson, Long> {
}