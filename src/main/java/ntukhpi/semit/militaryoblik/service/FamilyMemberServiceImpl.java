package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.FamilyMember;
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
    public List<FamilyMember> getAllCountry() {
        return familyMemberRepository.findAll();
    }
}
