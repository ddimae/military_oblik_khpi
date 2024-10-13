package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.FamilyMember;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.repository.FamilyMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FamilyMemberServiceImpl implements FamilyMemberService {

    private final FamilyMemberRepository familyMemberRepository;

    @Autowired
    public FamilyMemberServiceImpl(FamilyMemberRepository familyMemberRepository) {
        this.familyMemberRepository = familyMemberRepository;
    }

    @Override
    public FamilyMember createFamilyMember(FamilyMember familyMember) {
        return familyMemberRepository.save(familyMember);
    }

    @Override
    public FamilyMember getFamilyMemberById(Long id) {
        return familyMemberRepository.findById(id).orElse(null);
    }

    @Override
    public FamilyMember getFamilyMemberByExample(FamilyMember fm) {
        return familyMemberRepository
                .findFamilyMemberByPrepodAndMemFamAndMemImyaAndMemOtchAndVidRidstvaAndRikNarodz(
                        fm.getPrepod(),
                        fm.getMemFam(), fm.getMemImya(), fm.getMemOtch(),
                        fm.getVidRidstva(), fm.getRikNarodz()
                );
    }

    @Override
    public List<FamilyMember> getAllFamilyMember() {
        return familyMemberRepository.findAll();
    }

    @Override
    public List<FamilyMember> getAllFamilyMembersByPrepod(Prepod prepod) {
        return familyMemberRepository.findAllByPrepod(prepod);
    }

    @Override
    public FamilyMember updateFamilyMember(Long id, FamilyMember updatedFamilyMember) {
        FamilyMember existingFamilyMember = familyMemberRepository.findById(id).orElse(null);
        if (existingFamilyMember != null) {
            updatedFamilyMember.setId(existingFamilyMember.getId());
            return familyMemberRepository.save(updatedFamilyMember);
        }
        return null;
    }

    //ToDo change when special conditions was presented
    @Override
    public void deleteFamilyMember(Long id) {
        familyMemberRepository.deleteById(id);
    }

    @Override
    public void deleteAllByPrepod(Prepod prep) {
        List<FamilyMember> list = familyMemberRepository.findAllByPrepod(prep);
        for (FamilyMember fm: list) {
            familyMemberRepository.deleteById(fm.getId());
        }
    }


}
