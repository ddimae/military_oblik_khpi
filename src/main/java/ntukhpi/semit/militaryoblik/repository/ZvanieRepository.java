package ntukhpi.semit.militaryoblik.repository;

import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Stepen;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Zvanie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZvanieRepository extends JpaRepository<Zvanie, Long> {
    Zvanie getZvanieByZvanieName(String zvName);
}