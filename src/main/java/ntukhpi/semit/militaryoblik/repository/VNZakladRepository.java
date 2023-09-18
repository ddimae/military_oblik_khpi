package ntukhpi.semit.militaryoblik.repository;

import ntukhpi.semit.militaryoblik.entity.VNZaklad;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Fakultet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface VNZakladRepository extends JpaRepository<VNZaklad, Long> {

    VNZaklad getVNZakladByVnzName(String vnzName);

    VNZaklad getVNZakladByVnzShortName(String vnzShortName);

}