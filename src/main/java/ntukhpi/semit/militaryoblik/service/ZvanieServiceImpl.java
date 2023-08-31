package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Zvanie;
import ntukhpi.semit.militaryoblik.repository.ZvanieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZvanieServiceImpl implements ZvanieService {

    private final ZvanieRepository zvanieRepository;

    @Autowired
    public ZvanieServiceImpl(ZvanieRepository zvanieRepository) {
        this.zvanieRepository = zvanieRepository;
    }

    @Override
    public List<Zvanie> getAllCountry() {
        return zvanieRepository.findAll();
    }
}
