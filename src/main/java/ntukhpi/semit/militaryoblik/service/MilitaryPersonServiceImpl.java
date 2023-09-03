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
    public MilitaryPerson createMilitaryPerson(MilitaryPerson militaryPerson) {
        return militaryPersonRepository.save(militaryPerson);
    }

    @Override
    public MilitaryPerson getMilitaryPersonById(Long id) {
        return militaryPersonRepository.findById(id).orElse(null);
    }

    @Override
    public MilitaryPerson getMilitaryPerson() {
        List<MilitaryPerson> list = militaryPersonRepository.findAll();
        return !list.isEmpty()?list.get(0):null;
    }

    @Override
    public MilitaryPerson updateMilitaryPerson(Long id, MilitaryPerson updateMilitaryPerson) {
        MilitaryPerson existingMilitaryPerson = militaryPersonRepository.findById(id).orElse(null);
        if (existingMilitaryPerson != null) {
            updateMilitaryPerson.setId(existingMilitaryPerson.getId());
            return militaryPersonRepository.save(updateMilitaryPerson);
        }
        return null;
    }

    //ToDo change when special conditions was presented
    @Override
    public void deleteMilitaryPerson(Long id) {
        militaryPersonRepository.deleteById(id);
    }
}
