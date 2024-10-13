package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Dolghnost;
import ntukhpi.semit.militaryoblik.repository.DolghnostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DolghnostServiceImpl implements DolghnostService {
    private final DolghnostRepository dolghnostRepository;

    @Autowired
    public DolghnostServiceImpl(DolghnostRepository dolghnostRepository) {
        this.dolghnostRepository = dolghnostRepository;
    }

    @Override
    public Dolghnost createDolghnost(Dolghnost dolghnost) {
        return dolghnostRepository.save(dolghnost);
    }

    @Override
    public Dolghnost getDolghnostById(Long id) {
        return dolghnostRepository.findById(id).orElse(null);
    }

    @Override
    public Dolghnost getDolghnostByCategory(Integer id) {
        return dolghnostRepository.getDolghnostByCategoryEmployees(id);
    }

    @Override
    public List<Dolghnost> getAllDolghnost() {
        return dolghnostRepository.findAll();
    }

    @Override
    public Dolghnost updateDolghnost(Long id, Dolghnost updatedDolghnost) {
        Dolghnost existingDolghnost = dolghnostRepository.findById(id).orElse(null);
        if (existingDolghnost != null) {
            updatedDolghnost.setId(existingDolghnost.getId());
            return dolghnostRepository.save(updatedDolghnost);
        }
        return null;
    }

    //ToDo change when special conditions was presented
    @Override
    public void deleteDolghnost(Long id) {
        dolghnostRepository.deleteById(id);
    }

    @Override
    public Dolghnost getDolghnostByName(String posadaName) {
        return dolghnostRepository.getDolghnostByDolghnName(posadaName);
    }

    public Long findIDPosadaByName(String posadaName) {
        Dolghnost posadaInDB = dolghnostRepository.getDolghnostByDolghnName(posadaName);
        return posadaInDB!=null?posadaInDB.getId():null;
    }
}
