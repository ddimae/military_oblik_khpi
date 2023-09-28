package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.PersonalData;

public interface PersonalDataService {

    PersonalData getPersonalData();

    PersonalData getPersonalDataById(Long id);

    PersonalData createPersonalData(PersonalData personalData);

    PersonalData updatePersonalData(PersonalData updatedPersonalData);

    PersonalData getPersonalDataByPrepodId(Long id);

    void deletePersonalData(Long id);
}
