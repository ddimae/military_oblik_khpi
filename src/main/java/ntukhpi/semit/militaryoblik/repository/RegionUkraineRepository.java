package ntukhpi.semit.militaryoblik.repository;

import ntukhpi.semit.militaryoblik.entity.fromasukhpi.RegionUkraine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionUkraineRepository extends JpaRepository<RegionUkraine, Long> {
}