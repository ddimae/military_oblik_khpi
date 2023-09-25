package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.adapters.D05Adapter;
import ntukhpi.semit.militaryoblik.entity.*;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map;

@Service
public class D05DataCollectService {

    private final MilitaryPersonService militaryPersonService;
    private final PersonalDataService personalDataService;
    private final DocumentService documentService;
    private final CurrentDoljnostInfoService currentDoljnostInfoService;

    @Autowired
    public D05DataCollectService(MilitaryPersonService militaryPersonService, PersonalDataService personalDataService, DocumentService documentService, CurrentDoljnostInfoService currentDoljnostInfoService) {
        this.militaryPersonService = militaryPersonService;
        this.personalDataService = personalDataService;
        this.documentService = documentService;
        this.currentDoljnostInfoService = currentDoljnostInfoService;
    }

    @Transactional
    public Map<String, List<D05Adapter>> collectD05Adapter(List<Long> miliratiPersonIds) {
        Map<String, List<D05Adapter>> data = new HashMap<>();
        List<D05Adapter> collectData = collectData(miliratiPersonIds);
        for (D05Adapter adapter: collectData) {
            List<D05Adapter> list = data.get(adapter.getTerCentr());
            if(list != null) {
                List<D05Adapter> adapters = data.get(adapter.getTerCentr());
                adapters.add(adapter);
            } else {
                List<D05Adapter> tckList = new ArrayList<>();
                tckList.add(adapter);
                data.put(adapter.getTerCentr(), tckList);
            }
        }

        return data;
    }

    // Метод збору данних з бд для запису в додаток 5. Аргументом метод приймає список id MilitaryPerson.
    @Transactional
    public List<D05Adapter> collectData(List<Long> miliratiPersonIds) {
        List<D05Adapter> adapters = new ArrayList<>();
        if (miliratiPersonIds != null) {
            for (Long id : miliratiPersonIds) {
                MilitaryPerson person = militaryPersonService.getMilitaryPersonById(id);
                if (person.getPrepod().getId() != null) {
                    PersonalData personalData = personalDataService.getPersonalDataByPrepodId(person.getPrepod().getId());
                    List<Document> documents = documentService.getDocumentsByPrepodId(person.getPrepod().getId());
                    CurrentDoljnostInfo currentDoljnostInfo = currentDoljnostInfoService.getCurrentDoljnostInfoByPrepodId(person.getPrepod().getId());
                    adapters.add(mappedToAdapter(person, personalData, documents, currentDoljnostInfo));
                }
            }
        }
        return adapters;
    }

    // Маппінг отриманих данних з бд в D05Adapter
    private D05Adapter mappedToAdapter(MilitaryPerson person, PersonalData personalData,
                                       List<Document> documents, CurrentDoljnostInfo currentDoljnostInfo) {
        D05Adapter adapter = new D05Adapter();
        adapter.setZvannia(person.getVZvanie().getZvanieName());
        adapter.setPib(concatPib(person));
        adapter.setBirthDate(person.getPrepod().getDr().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        adapter.setVos(person.getVos());
        adapter.setSklad(person.getVSklad().getSkladName());
        adapter.setKatObl(person.getVCategory().toString());
        adapter.setOsvita(concatEducation(person.getPrepod().getEducationList()));
        adapter.setPasport(concatDocument(documents));
        if (personalData != null) {
            adapter.setRegAddress(concatRezAddress(personalData));
            adapter.setActAddress(concatFactAddress(personalData));
        }
        adapter.setTerCentr(person.getVoenkomat().getVoenkomatName());
        adapter.setSpecObl(person.getReserv());
        adapter.setPrudat(person.getVPrydatnist());
        adapter.setSimStan(concatFamilyList(person));
        adapter.setPosada(concatPosada(person.getPrepod(), currentDoljnostInfo));
        adapter.setPriznach("");
        return adapter;
    }

    // Форматування данних про сім’ю відповідно заданному шаблону.
    // Не піде - подивись приклад: може не бути прізвища, году нарождення
    private String concatFamilyList(MilitaryPerson person) {
        Set<FamilyMember> members = person.getPrepod().getFamily();
        StringBuilder family = new StringBuilder();
        family.append(person.getFamilyState() != null ? person.getFamilyState() + ";" : "").append("\n");
        for (FamilyMember member : members) {
            if (member != null) {
                family.append(member.getVid_ridstva() != null ? member.getVid_ridstva() : "").append(" - ");
                family.append(member.getMem_fam() != null ? member.getMem_fam() : "");
                family.append(member.getMem_imya() != null ? " " + member.getMem_imya() : "");
                family.append(member.getMem_otch() != null ? " " + member.getMem_otch() : "");
                family.append(member.getRikNarodz() != null ? ", " + member.getRikNarodz() + "р.н." : "").append("; ");
            }
        }
        return family.toString();
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

    // Форматування данних про адресу реєстрації
    private String concatRezAddress(PersonalData personalData) {
        StringBuilder address = new StringBuilder();
        if (personalData != null) {
            if (personalData.getOblastUA() != null) {
                address.append(personalData.getOblastUA().getCountryName() != null ? personalData.getOblastUA().getCountryName() + " обл., " : "");
            }
            address.append(personalData.getCity() != null ? personalData.getCity() : "").append(", ");
            address.append(personalData.getRowAddress() != null ? personalData.getRowAddress() : "");
        }
        return address.toString();
    }

    // Форматування данних про поточну адресу проживання
    private String concatFactAddress(PersonalData personalData) {
        StringBuilder address = new StringBuilder();
        if (personalData != null) {
            if (personalData.getFactOblastUA() != null) {
                address.append(personalData.getFactOblastUA().getCountryName() != null ? personalData.getFactOblastUA().getCountryName() + " обл., " : "");
            }
            address.append(personalData.getFactCity() != null ? personalData.getFactCity() : "").append(", ");
            address.append(personalData.getFactRowAddress() != null ? personalData.getFactRowAddress() : "");
        }
        return address.toString();
    }

    // Форматування данних про освіту відповідно заданному шаблону.
    // Не піде - у людини може бути декілька вузів, які закінчені
    // Наприклад, офіцер може закінчити один вуз на тактичний рівень, а потім - на стратегічний. Для офіцерів важливо
    // Не піде 2 - треба враховувати, що чогось немає....- номера, спеціальності, квалифікації, году. ДД
    // Треба, як у попередньому випадку - з освітою
    private String concatEducation(Set<Education> educations) {
        StringBuilder osvita = new StringBuilder();
        for (Education education : educations) {
            if (education != null) {
                osvita.append(education.getLevelTraining() != null ? education.getLevelTraining() + ", " : "");
                if (education.getVnz() != null) {
                    osvita.append(education.getVnz().getVnzShortName() != null ? education.getVnz().getVnzShortName() : "");
                    osvita.append(education.getYearVypusk() != null ? " у " + education.getYearVypusk() : "");
                }
                osvita.append(education.getDiplomaSeries() != null ? ", " + education.getDiplomaSeries() : "");
                osvita.append(education.getDiplomaSpeciality() != null ? ", " + education.getDiplomaSpeciality() : "");
            }
            osvita.append("; ");
        }
        return osvita.toString();
    }

    // Форматування данних про ПІБ відповідно заданному шаблону.
    private String concatPib(MilitaryPerson person) {
        String pib = "";
        if (person.getPrepod() != null) {
            pib = String.format("%s %s %s", person.getPrepod().getFam().toUpperCase(), person.getPrepod().getImya(), person.getPrepod().getOtch());
        }
        return pib;
    }

    // Форматування данних про посаду відповідно заданному шаблону.
    private String concatPosada(Prepod prepod, CurrentDoljnostInfo currentDoljnostInfo) {
        String nakaz = "";
        StringBuilder posada = new StringBuilder();
        posada.append(prepod.getDolghnost().getDolghnName()).append(", ");
        posada.append(prepod.getKafedra().getKabr());
        if (currentDoljnostInfo != null) {
            if (currentDoljnostInfo.getNumNakazStop() != null && currentDoljnostInfo.getDateStop() != null) {
                nakaz = String.format(", звільнення: наказ %s від %s", currentDoljnostInfo.getNumNakazStop(),
                        currentDoljnostInfo.getDateStop().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            } else if (currentDoljnostInfo.getNumNakazStart() != null && currentDoljnostInfo.getDateStart() != null) {
                nakaz = String.format(", наказ %s від %s", currentDoljnostInfo.getNumNakazStart(),
                        currentDoljnostInfo.getDateStart().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            }
        }

        posada.append(nakaz);

        return posada.toString();
    }
}
