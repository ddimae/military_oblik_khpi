package ntukhpi.semit.militaryoblik.repository;

import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
}