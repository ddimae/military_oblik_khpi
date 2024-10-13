package ntukhpi.semit.militaryoblik.testWrite;

import ntukhpi.semit.militaryoblik.adapters.D05Adapter;
import ntukhpi.semit.militaryoblik.entity.*;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Dolghnost;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Fakultet;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Kafedra;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.service.CurrentDoljnostInfoService;
import ntukhpi.semit.militaryoblik.service.DocumentService;
import ntukhpi.semit.militaryoblik.service.MilitaryPersonService;
import ntukhpi.semit.militaryoblik.service.PersonalDataService;
import ntukhpi.semit.militaryoblik.utils.D05DataCollectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
    private CurrentDoljnostInfoService currentDoljnostInfoService;

    @Spy
    @InjectMocks
    private D05DataCollectService service;

//    @Test
//    void collectD05Adapter() {
//        doReturn(generateMilitary(1)).when(militaryPersonService).getMilitaryPersonById(1L);
//        doReturn(generateMilitary(2)).when(militaryPersonService).getMilitaryPersonById(2L);
//        doReturn(generatePersonalData(1)).when(personalDataService).getPersonalDataByPrepodId(1L);
//        doReturn(generatePersonalData(2)).when(personalDataService).getPersonalDataByPrepodId(2L);
//        doReturn(generateDocument(1, "Паперовий паспорт")).when(documentService).getDocumentsByPrepodId(1L);
//        doReturn(generateDocument(2, "ID картка")).when(documentService).getDocumentsByPrepodId(2L);
//        doReturn(generateCurrentDoljnostInfo()).when(currentDoljnostInfoService).getCurrentDoljnostInfoByPrepodId(1L);
//        doReturn(new CurrentDoljnostInfo()).when(currentDoljnostInfoService).getCurrentDoljnostInfoByPrepodId(2L);
//
//        List<Long> ids = Arrays.asList(1L, 2L);
//        Map<String, List<D05Adapter>> actual = service.collectD05Adapter(ids);

//        assertEquals("звання1", actual.get("військоммат1").get(0).getZvannia());
//        assertEquals("ПРІЗВИЩЕ1 Ім’я1 По батькові1", actual.get("військоммат1").get(0).getPib());
//        assertEquals("19.04.1985", actual.get("військоммат1").get(0).getBirthDate());
//        assertEquals("вос2", actual.get("військоммат2").get(0).getVos());
//        assertEquals("склад2", actual.get("військоммат2").get(0).getSklad());
//        assertEquals("магістр2, назва навчального закладу2 у 1992, 354342, спеціальність2; ", actual.get("військоммат2").get(0).getOsvita());
//        assertEquals("номер2, видав2, 1999-04-25", actual.get("військоммат2").get(0).getPasport());
//        assertEquals(", адреса прописки1", actual.get("військоммат1").get(0).getRegAddress());
//        assertEquals(", актуальна адреса1", actual.get("військоммат1").get(0).getActAddress());
//        assertEquals("військоммат1", actual.get("військоммат1").get(0).getTerCentr());
//        assertEquals("резерв1", actual.get("військоммат1").get(0).getSpecObl());
//        assertEquals("придатність1", actual.get("військоммат1").get(0).getPrudat());
//        assertEquals("одружений;\nрідство2 - прізвище2 ім’я2 по батькові2, 1992р.н.; ", actual.get("військоммат2").get(0).getSimStan());
//        assertEquals("назва посади1, кафедра1, наказ №443344 від 19.04.2021", actual.get("військоммат1").get(0).getPosada());
//        assertEquals("", actual.get("військоммат2").get(0).getPriznach());
//    }

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
        militaryPerson.setFamilyState("одружений");
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
        prepod.addMember(generateFamilyMember(count));
        prepod.addEducation(generateEducation(count));
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
        vnZaklad.setVnzName("назва закладу");
        education.setLevelTraining("магістр" + count);
        education.setVnz(vnZaklad);
        education.setYearVypusk("199" + count);
        education.setDiplomaSeries("35434" + count);
        education.setDiplomaSpeciality("спеціальність" + count);
        education.setPrepod(generatePrepodForMock());
        return education;
    }

    private FamilyMember generateFamilyMember(int count) {
        FamilyMember familyMember = new FamilyMember();
        familyMember.setVidRidstva("рідство" + count);
        familyMember.setMemFam("прізвище" + count);
        familyMember.setMemImya("ім’я" + count);
        familyMember.setMemOtch("по батькові" + count);
        familyMember.setRikNarodz("199" + count);
        familyMember.setPrepod(generatePrepodForMock());
        return familyMember;
    }

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

    private Prepod generatePrepodForMock() {
        Prepod prepod = new Prepod();
        Fakultet fakultet = new Fakultet();
        fakultet.setAbr("abr");
        fakultet.setFname("Fname");
        fakultet.setOid("oid");
        Dolghnost dolghnost = new Dolghnost();
        dolghnost.setDolghnName("назва посади");
        Kafedra kafedra = new Kafedra();
        kafedra.setKabr("кафедра");
        kafedra.setKname("кафедра");
        kafedra.setOid("iod");
        kafedra.setFakultet(fakultet);
        prepod.setDolghnost(dolghnost);
        prepod.setKafedra(kafedra);
        prepod.setId(22L);
        prepod.setDr(LocalDate.of(1985, Month.APRIL, 19));
        prepod.setImya("Ім’я");
        prepod.setFam("Прізвище");
        prepod.setOtch("По батькові");
        return prepod;
    }

}