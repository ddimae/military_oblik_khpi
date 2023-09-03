package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.PersonalData;

import java.util.List;

public interface PersonalDataService {

    PersonalData getPersonalData();

    PersonalData getPersonalDataById(Long id);

    PersonalData createPersonalData(PersonalData personalData);

    PersonalData updatePersonalData(PersonalData updatedPersonalData);
}
