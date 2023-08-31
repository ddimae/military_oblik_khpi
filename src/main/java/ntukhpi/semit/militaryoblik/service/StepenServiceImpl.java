package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Stepen;
import ntukhpi.semit.militaryoblik.repository.StepenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StepenServiceImpl implements StepenService {

    private final StepenRepository stepenRepository;

    @Autowired
    public StepenServiceImpl(StepenRepository stepenRepository) {
        this.stepenRepository = stepenRepository;
    }

    @Override
    public List<Stepen> getAllCountry() {
        return stepenRepository.findAll();
    }
}
