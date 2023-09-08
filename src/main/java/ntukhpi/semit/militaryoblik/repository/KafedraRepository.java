package ntukhpi.semit.militaryoblik.repository;

import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Kafedra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KafedraRepository extends JpaRepository<Kafedra,Long> {
      Kafedra getKafedraByKname(String kname);
      List<Kafedra> getAllByFakultet_Fname(String fakName);

}