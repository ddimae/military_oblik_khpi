package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.MilitaryPerson;

import java.util.List;

public interface MilitaryPersonService {
    MilitaryPerson createMilitaryPerson(MilitaryPerson militaryPerson);

    MilitaryPerson getMilitaryPersonById(Long id);

    List<MilitaryPerson> getAllMilitaryPerson();

    MilitaryPerson updateMilitaryPerson(Long id, MilitaryPerson updateMilitaryPerson);

    void deleteMilitaryPerson(Long id);
}
