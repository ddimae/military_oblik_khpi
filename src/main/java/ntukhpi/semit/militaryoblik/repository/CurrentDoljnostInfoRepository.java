package ntukhpi.semit.militaryoblik.repository;

import ntukhpi.semit.militaryoblik.entity.CurrentDoljnostInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrentDoljnostInfoRepository extends JpaRepository<CurrentDoljnostInfo, Long> {

    CurrentDoljnostInfo findCurrentDoljnostInfoByPrepodId(Long id);
}