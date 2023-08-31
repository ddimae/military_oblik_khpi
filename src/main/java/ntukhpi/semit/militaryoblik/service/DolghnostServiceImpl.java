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
    public List<Dolghnost> getAllCountry() {
        return dolghnostRepository.findAll();
    }
}
