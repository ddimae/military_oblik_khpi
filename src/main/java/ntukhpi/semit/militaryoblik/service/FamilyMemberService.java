package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.FamilyMember;

import java.util.List;

public interface FamilyMemberService {

    FamilyMember createFamilyMember(FamilyMember familyMember);

    FamilyMember getFamilyMemberById(Long id);

    List<FamilyMember> getAllFamilyMember();

    FamilyMember updateFamilyMember(Long id, FamilyMember updatedFamilyMember);

    void deleteFamilyMember(Long id);
}
