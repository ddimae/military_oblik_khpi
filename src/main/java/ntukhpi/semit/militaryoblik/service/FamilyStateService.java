package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.FamilyState;

import java.util.List;

public interface FamilyStateService {

    FamilyState createFamilyState(FamilyState familyState);

    FamilyState getFamilyStateById(Long id);

    List<FamilyState> getAllFamilyState();

    FamilyState updateFamilyState(Long id, FamilyState updatedFamilyState);

    void deleteFamilyState(Long id);
}
