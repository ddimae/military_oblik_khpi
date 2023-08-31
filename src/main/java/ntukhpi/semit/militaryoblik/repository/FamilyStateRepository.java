package ntukhpi.semit.militaryoblik.repository;

import ntukhpi.semit.militaryoblik.entity.FamilyState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyStateRepository extends JpaRepository<FamilyState, Long> {
}