package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.MilitaryPerson;
import ntukhpi.semit.militaryoblik.repository.MilitaryPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MilitaryPersonServiceImpl implements MilitaryPersonService {

    private final MilitaryPersonRepository militaryPersonRepository;

    @Autowired
    public MilitaryPersonServiceImpl(MilitaryPersonRepository militaryPersonRepository) {
        this.militaryPersonRepository = militaryPersonRepository;
    }

    @Override
    public List<MilitaryPerson> getAllCountry() {
        return militaryPersonRepository.findAll();
    }
}
