package ntukhpi.semit.militaryoblik.repository;

import ntukhpi.semit.militaryoblik.entity.VSklad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VSkladRepository extends JpaRepository<VSklad, Long> {
    VSklad findVSkladBySkladName(String vskladName);

}