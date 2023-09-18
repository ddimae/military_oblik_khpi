package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.adapters.D05Adapter;
import ntukhpi.semit.militaryoblik.entity.*;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Dolghnost;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Kafedra;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class D05DataCollectServiceTest {

    @Mock
    private MilitaryPersonService militaryPersonService;
    @Mock
    private PersonalDataService personalDataService;
    @Mock
    private DocumentService documentService;
    @Mock
    private EducationService educationService;
//    @Mock
//    private FamilyStateService familyStateService;
    @Mock
    private CurrentDoljnostInfoService currentDoljnostInfoService;

    @Spy
    @InjectMocks
    private D05DataCollectService service;

    @Test
    void collectD05Adapter() {
        doReturn(generateMilitary(1)).when(militaryPersonService).getMilitaryPersonById(1L);
        doReturn(generateMilitary(2)).when(militaryPersonService).getMilitaryPersonById(2L);
        doReturn(generatePersonalData(1)).when(personalDataService).getPersonalDataByPrepodId(1L);
        doReturn(generatePersonalData(2)).when(personalDataService).getPersonalDataByPrepodId(2L);
        doReturn(generateEducation(1)).when(educationService).getEducationByPrepodId(1L);
        doReturn(generateEducation(2)).when(educationService).getEducationByPrepodId(2L);
//        doReturn(generateFamilyState(1)).when(familyStateService).getFamilyStateByPrepodId(1L);
//        doReturn(generateFamilyState(2)).when(familyStateService).getFamilyStateByPrepodId(2L);
        doReturn(generateDocument(1, "Паперовий паспорт")).when(documentService).getDocumentsByPrepodId(1L);
        doReturn(generateDocument(2, "ID картка")).when(documentService).getDocumentsByPrepodId(2L);
        doReturn(generateCurrentDoljnostInfo()).when(currentDoljnostInfoService).getCurrentDoljnostInfoByPrepodId(1L);
        doReturn(new CurrentDoljnostInfo()).when(currentDoljnostInfoService).getCurrentDoljnostInfoByPrepodId(2L);

        List<Long> ids = Arrays.asList(1L, 2L);
        List<D05Adapter> actual = service.collectD05Adapter(ids);

        assertEquals("звання1", actual.get(0).getZvannia());
        assertEquals("Прізвище1 Ім’я1 По батькові1", actual.get(0).getPib());
        assertEquals("1985-04-19", actual.get(0).getBirthDate());
        assertEquals("вос2", actual.get(1).getVos());
        assertEquals("склад2", actual.get(1).getSklad());
        assertEquals("магістр2, назва навчального закладу2 у 1992, 354342, спеціальність2", actual.get(1).getOsvita());
        assertEquals("номер2, видав2, 1999-04-25", actual.get(1).getPasport());
        assertEquals("адреса прописки1", actual.get(0).getRegAddress());
        assertEquals("актуальна адреса1", actual.get(0).getActAddress());
        assertEquals("військоммат1", actual.get(0).getTerCentr());
        assertEquals("резерв1", actual.get(0).getSpecObl());
        assertEquals("придатність1", actual.get(0).getPrudat());
        assertEquals("одружений2;\nрідство2 - прізвище2 ім’я2 по батькові2, 1992 ", actual.get(1).getSimStan());
        assertEquals("назва посади1, кафедра1, наказ №443344 від 2021-04-19", actual.get(0).getPosada());
        assertEquals("", actual.get(1).getPriznach());
    }

    private MilitaryPerson generateMilitary(int count) {
        MilitaryPerson militaryPerson = new MilitaryPerson();
        Voenkomat voenkomat = new Voenkomat();
        voenkomat.setVoenkomatName("військоммат" + count);
        VZvanie vZvanie = new VZvanie();
        vZvanie.setZvanieName("звання" + count);
        VSklad vSklad = new VSklad();
        vSklad.setSkladName("склад" + count);
        militaryPerson.setPrepod(generatePrepod(count));
        militaryPerson.setVoenkomat(voenkomat);
        militaryPerson.setVZvanie(vZvanie);
        militaryPerson.setVSklad(vSklad);
        militaryPerson.setVos("вос" + count);
        militaryPerson.setVCategory(count);
        militaryPerson.setVGrupa("группа обліку" + count);
        militaryPerson.setVPrydatnist("придатність" + count);
        militaryPerson.setReserv("резерв" + count);
        return militaryPerson;
    }

    private Prepod generatePrepod(int count) {
        Prepod prepod = new Prepod();
        Dolghnost dolghnost = new Dolghnost();
        dolghnost.setDolghnName("назва посади" + count);
        Kafedra kafedra = new Kafedra();
        kafedra.setKabr("кафедра" + count);
        prepod.setDolghnost(dolghnost);
        prepod.setKafedra(kafedra);
        prepod.setId((long) count);
        prepod.setDr(LocalDate.of(1985, Month.APRIL, 19));
        prepod.setImya("Ім’я" + count);
        prepod.setFam("Прізвище" + count);
        prepod.setOtch("По батькові" + count);
        return prepod;
    }

    private PersonalData generatePersonalData(int count) {
        PersonalData personalData = new PersonalData();
        personalData.setRowAddress("адреса прописки" + count);
        personalData.setFactRowAddress("актуальна адреса" + count);
        return personalData;
    }

    private Education generateEducation(int count) {
        Education education = new Education();
        VNZaklad vnZaklad = new VNZaklad();
        vnZaklad.setVnzShortName("назва навчального закладу" + count);
        education.setLevelTraining("магістр" + count);
        education.setVnz(vnZaklad);
        education.setYearVypusk("199" + count);
        education.setDiplomaSeries("35434" + count);
        education.setDiplomaSpeciality("спеціальність" + count);
        return education;
    }

//    private FamilyState generateFamilyState(int count) {
//        FamilyState familyState = new FamilyState();
//        FamilyMember familyMember = new FamilyMember();
//        familyMember.setVid_ridstva("рідство" + count);
//        familyMember.setMem_fam("прізвище" + count);
//        familyMember.setMem_imya("ім’я" + count);
//        familyMember.setMem_otch("по батькові" + count);
//        familyMember.setRikNarodz("199" + count);
//        familyState.setFamilyState("одружений" + count);
//        Set<FamilyMember> members = new HashSet<>();
//        members.add(familyMember);
//        familyState.setFamily(members);
//        return familyState;
//    }

    private List<Document> generateDocument(int count, String docType) {
        Document document = new Document();
        document.setDocType(docType);
        document.setDocNumber("номер" + count);
        document.setKtoVyd("видав" + count);
        document.setDataVyd(LocalDate.of(1999, Month.APRIL, 25));
        return List.of(document);
    }

    private CurrentDoljnostInfo generateCurrentDoljnostInfo() {
        CurrentDoljnostInfo currentDoljnostInfo = new CurrentDoljnostInfo();
        currentDoljnostInfo.setCommentStart("початок");
        currentDoljnostInfo.setNumNakazStart("№443344");
        currentDoljnostInfo.setDateStart(LocalDate.of(2021, Month.APRIL, 19));
        return currentDoljnostInfo;
    }

}