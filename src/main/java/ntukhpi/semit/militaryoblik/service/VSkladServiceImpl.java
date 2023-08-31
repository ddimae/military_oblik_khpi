package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.VSklad;
import ntukhpi.semit.militaryoblik.repository.VNZakladRepository;
import ntukhpi.semit.militaryoblik.repository.VSkladRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VSkladServiceImpl implements VSkladService {

    private final VSkladRepository vSkladRepository;

    @Autowired
    public VSkladServiceImpl(VSkladRepository vSkladRepository) {
        this.vSkladRepository = vSkladRepository;
    }

    @Override
    public List<VSklad> getAllCountry() {
        return vSkladRepository.findAll();
    }
}
