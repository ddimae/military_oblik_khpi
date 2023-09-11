package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.adapters.D05Adapter;
import ntukhpi.semit.militaryoblik.entity.*;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class D05DataCollectService {

    private final MilitaryPersonService militaryPersonService;
    private final PersonalDataService personalDataService;
    private final DocumentService documentService;
    private final EducationService educationService;
    private final FamilyStateService familyStateService;
    private final CurrentDoljnostInfoService currentDoljnostInfoService;

    @Autowired
    public D05DataCollectService(MilitaryPersonService militaryPersonService, PersonalDataService personalDataService, DocumentService documentService, EducationService educationService, FamilyStateService familyStateService, CurrentDoljnostInfoService currentDoljnostInfoService) {
        this.militaryPersonService = militaryPersonService;
        this.personalDataService = personalDataService;
        this.documentService = documentService;
        this.educationService = educationService;
        this.familyStateService = familyStateService;
        this.currentDoljnostInfoService = currentDoljnostInfoService;
    }

    // Метод збору данних з бд для запису в додаток 5. Аргументом метод приймає список id MilitaryPerson.
    @Transactional
    public List<D05Adapter> collectD05Adapter(List<Integer> miliratiPersonIds) {
        List<D05Adapter> adapters = new ArrayList<>();
        if (miliratiPersonIds != null) {
            for (Integer id : miliratiPersonIds) {
                MilitaryPerson person = militaryPersonService.getMilitaryPersonById(id.longValue());
                if (person.getPrepod().getId() != null) {
                    PersonalData personalData = personalDataService.getPersonalDataByPrepodId(person.getPrepod().getId());
                    Education education = educationService.getEducationByPrepodId(person.getPrepod().getId());
                    FamilyState familyState = familyStateService.getFamilyStateByPrepodId(person.getPrepod().getId());
                    List<Document> documents = documentService.getDocumentsByPrepodId(person.getPrepod().getId());
                    CurrentDoljnostInfo currentDoljnostInfo = currentDoljnostInfoService.getCurrentDoljnostInfoByPrepodId(person.getPrepod().getId());
                    adapters.add(mappedToAdapter(person, personalData, education, familyState, documents, currentDoljnostInfo));
                }
            }
        }
        return adapters;
    }

    // Маппінг отриманих данних з бд в D05Adapter
    private D05Adapter mappedToAdapter(MilitaryPerson person, PersonalData personalData, Education education, FamilyState familyState,
                                       List<Document> documents, CurrentDoljnostInfo currentDoljnostInfo) {
        D05Adapter adapter = new D05Adapter();
        adapter.setZvannia(person.getVZvanie().getZvanieName());
        adapter.setPib(concatPib(person));
        adapter.setBirthDate(person.getPrepod().getDr().toString());
        adapter.setVos(person.getVos());
        adapter.setSklad(person.getVSklad().getSkladName());
        adapter.setKatObl(person.getVCategory().toString());
        adapter.setOsvita(concatEducation(education));
        adapter.setPasport(concatDocument(documents));
        if (personalData != null) {
            adapter.setRegAddress(personalData.getRowAddress());
            adapter.setActAddress(personalData.getFactRowAddress());
        }
        adapter.setTerCentr(person.getVoenkomat().getVoenkomatName());
        adapter.setSpecObl(person.getReserv());
        adapter.setPrudat(person.getVPrydatnist());
        adapter.setSimStan(concatFamilyState(familyState));
        adapter.setPosada(concatPosada(person.getPrepod(), currentDoljnostInfo));
        adapter.setPriznach(concatPriznach(currentDoljnostInfo));
        return adapter;
    }

    // Форматування данних про сім’ю відповідно заданному шаблону.
    private String concatFamilyState(FamilyState familyState) {
        String fam = "";
        if (familyState != null) {
            Set<FamilyMember> members = familyState.getFamily();
            StringBuilder family = new StringBuilder();
            family.append(familyState.getFamilyState()).append(";\n");
            for (FamilyMember member : members) {
                String famMember = String.format("%s - %s %s %s, %s ", member.getVid_ridstva(), member.getMem_fam(),
                        member.getMem_imya(), member.getMem_otch(), member.getRikNarodz());
                family.append(famMember);
            }
            fam = family.toString();
        }

        return fam;
    }

    // Форматування данних про документ відповідно заданному шаблону.
    private String concatDocument(List<Document> documents) {
        String doc = "";
        for (Document document : documents) {
            if ("Паперовий паспорт".equals(document.getDocType()) || "ID картка".equals(document.getDocType())) {
                doc = String.format("%s, %s, %s", document.getDocNumber(), document.getKtoVyd(), document.getDataVyd());
            }
        }
        return doc;
    }

    // Форматування данних про освіту відповідно заданному шаблону.
    private String concatEducation(Education education) {
        String ed = "";
        if (education != null) {
            ed = String.format("%s, %s у %s, %s, %s", education.getLevelTraining(), education.getVnz().getVnzShortName(),
                    education.getYearVypusk(), education.getDiplomaSeries(), education.getDiplomaSpeciality());
        }
        return ed;
    }

    // Форматування данних про ПІБ відповідно заданному шаблону.
    private String concatPib(MilitaryPerson person) {
        String pib = "";
        if (person.getPrepod() != null) {
            pib = String.format("%s %s %s", person.getPrepod().getFam(), person.getPrepod().getImya(), person.getPrepod().getOtch());
        }
        return pib;
    }

    // Форматування данних про призначення відповідно заданному шаблону.
    private String concatPriznach(CurrentDoljnostInfo currentDoljnostInfo) {
        String priznach = "";
        if (currentDoljnostInfo != null) {
            if (currentDoljnostInfo.getCommentStop() != null) {
                priznach = String.format("%s, наказ %s від %s", currentDoljnostInfo.getCommentStop(), currentDoljnostInfo.getNumNakazStop(), currentDoljnostInfo.getDateStop());
            }
        }
        return priznach;
    }

    // Форматування данних про посаду відповідно заданному шаблону.
    private String concatPosada(Prepod prepod, CurrentDoljnostInfo currentDoljnostInfo) {
        String nakaz = "";
        StringBuilder posada = new StringBuilder();
        posada.append(prepod.getDolghnost().getDolghnName()).append(", ");
        posada.append(prepod.getKafedra().getKname()).append(", ");
        if (currentDoljnostInfo != null) {
            nakaz = String.format("наказ %s від %s", currentDoljnostInfo.getNumNakazStart(), currentDoljnostInfo.getDateStart());
        }

        posada.append(nakaz);

        return posada.toString();
    }
}
