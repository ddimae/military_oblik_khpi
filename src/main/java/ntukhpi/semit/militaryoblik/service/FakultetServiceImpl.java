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
    public Fakultet createFakultet(Fakultet fakultet) {
        return fakultetRepository.save(fakultet);
    }

    @Override
    public Fakultet getFakultetById(Long id) {
        return fakultetRepository.findById(id).orElse(null);
    }

    @Override
    public List<Fakultet> getAllFak() {
        return fakultetRepository.findAll();
    }

    @Override
    public Fakultet updateFakultet(Long id, Fakultet updatedFakultet) {
        Fakultet existingFakultet = fakultetRepository.findById(id).orElse(null);
        if (existingFakultet != null) {
            updatedFakultet.setFid(existingFakultet.getFid());
            return fakultetRepository.save(updatedFakultet);
        }
        return null;
    }

    //ToDo change when special conditions was presented
    @Override
    public void deleteFakultet(Long id) {
        fakultetRepository.deleteById(id);
    }
}
