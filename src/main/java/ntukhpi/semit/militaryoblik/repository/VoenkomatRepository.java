package ntukhpi.semit.militaryoblik.repository;

import ntukhpi.semit.militaryoblik.entity.VZvanie;
import ntukhpi.semit.militaryoblik.entity.Voenkomat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoenkomatRepository extends JpaRepository<Voenkomat,Long> {

    Voenkomat findVoenkomatByVoenkomatName(String voenkomatName);

}
