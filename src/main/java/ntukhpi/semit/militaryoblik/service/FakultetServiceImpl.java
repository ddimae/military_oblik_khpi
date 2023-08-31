package ntukhpi.semit.militaryoblik.service;


import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Fakultet;
import ntukhpi.semit.militaryoblik.repository.FakultetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FakultetServiceImpl implements FakultetService {

    private final FakultetRepository fakultetRepository;

    @Autowired
    public FakultetServiceImpl(FakultetRepository fakultetRepository) {
        super();
        this.fakultetRepository = fakultetRepository;
    }

    @Override
    public List<Fakultet> getAllFak() {
        return fakultetRepository.findAll();
    }
}
