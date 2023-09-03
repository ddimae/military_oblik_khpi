package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Dolghnost;
import ntukhpi.semit.militaryoblik.repository.DolghnostRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface DolghnostService {
    Dolghnost createDolghnost(Dolghnost dolghnost);

    Dolghnost getDolghnostById(Long id);

    List<Dolghnost> getAllDolghnost();

    Dolghnost updateDolghnost(Long id, Dolghnost updatedDolghnost);

    void deleteDolghnost(Long id);
}
