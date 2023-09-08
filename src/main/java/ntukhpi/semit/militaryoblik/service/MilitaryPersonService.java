package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.MilitaryPerson;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;

import java.util.List;

public interface MilitaryPersonService {
    MilitaryPerson createMilitaryPerson(MilitaryPerson militaryPerson);

    MilitaryPerson getMilitaryPersonById(Long id);

    MilitaryPerson getMilitaryPersonByPrepod(Prepod prep);

    List<MilitaryPerson> getAllMilitaryPerson();

    MilitaryPerson updateMilitaryPerson(Long id, MilitaryPerson updateMilitaryPerson);

    void deleteMilitaryPerson(Long id);

    MilitaryPerson saveMilitaryInfo(Prepod prep, String voenkomatName, String vzvanieName, String skladName,
                          String vos, int category, String grupaObliku, String pridatnist, String reserv);
}
