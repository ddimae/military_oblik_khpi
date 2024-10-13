package ntukhpi.semit.militaryoblik.repository;

import ntukhpi.semit.militaryoblik.entity.VZvanie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VZvanieRepository extends JpaRepository<VZvanie, Long> {
    VZvanie findByZvanieName(String vZvanieName);
}