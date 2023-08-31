package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.VNZaklad;
import ntukhpi.semit.militaryoblik.repository.VNZakladRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VNZakladServiceImpl implements VNZakladService {

    private final VNZakladRepository vnZakladRepository;

    @Autowired
    public VNZakladServiceImpl(VNZakladRepository vnZakladRepository) {
        this.vnZakladRepository = vnZakladRepository;
    }

    @Override
    public List<VNZaklad> getAllCountry() {
        return vnZakladRepository.findAll();
    }
}
