package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.FamilyState;

public interface FamilyStateService {

    FamilyState createFamilyState(FamilyState familyState);

    FamilyState getFamilyStateById(Long id);

    FamilyState getFamilyState();

    FamilyState updateFamilyState(Long id, FamilyState updatedFamilyState);

    void deleteFamilyState(Long id);

    FamilyState getFamilyStateByPrepodId(Long id);
}
