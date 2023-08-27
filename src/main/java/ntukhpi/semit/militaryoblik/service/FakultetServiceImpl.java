package ntukhpi.semit.militaryoblik.service;


import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Fakultet;
import ntukhpi.semit.militaryoblik.repository.FakultetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FakultetServiceImpl implements FakultetService {

    private FakultetRepository fakultetRepository;

    public FakultetServiceImpl(FakultetRepository fakultetRepository) {
        super();
        this.fakultetRepository = fakultetRepository;
    }

    @Override
    public List<Fakultet> getAllFak() {
        return fakultetRepository.findAll();
    }
}
