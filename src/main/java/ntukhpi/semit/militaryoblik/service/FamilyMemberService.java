package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.FamilyMember;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;

import java.util.List;

public interface FamilyMemberService {

    FamilyMember createFamilyMember(FamilyMember familyMember);

    FamilyMember getFamilyMemberById(Long id);

    FamilyMember getFamilyMemberByExample(FamilyMember fm);

    List<FamilyMember> getAllFamilyMember();

    List<FamilyMember> getAllFamilyMembersByPrepod(Prepod prepod);

    FamilyMember updateFamilyMember(Long id, FamilyMember updatedFamilyMember);

    void deleteFamilyMember(Long id);

    void deleteAllByPrepod(Prepod prep);
}
