package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.PersonalData;
import ntukhpi.semit.militaryoblik.repository.PersonalDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonalDataServiceImpl implements PersonalDataService {

    private final PersonalDataRepository personalDataRepository;

    @Autowired
    public PersonalDataServiceImpl(PersonalDataRepository personalDataRepository) {
        this.personalDataRepository = personalDataRepository;
    }

    @Override
    public PersonalData getPersonalData() {
        List<PersonalData> list = personalDataRepository.findAll();
        return !list.isEmpty()?list.get(0):null;
    }

    @Override
    public PersonalData getPersonalDataById(Long id) {
        return personalDataRepository.findById(id).orElse(null);
    }

    @Override
    public PersonalData createPersonalData(PersonalData personalData) {
        return personalDataRepository.save(personalData);
    }

    @Override
    public PersonalData updatePersonalData(PersonalData updatedPersonalData) {
        return personalDataRepository.save(updatedPersonalData);
    }

    @Override
    public PersonalData getPersonalDataByPrepodId(Long id) {
        return personalDataRepository.findPersonalDataByPrepodId(id);
    }

    @Override
    public void deletePersonalData(Long id) {
        personalDataRepository.deleteById(id);
    }
}
