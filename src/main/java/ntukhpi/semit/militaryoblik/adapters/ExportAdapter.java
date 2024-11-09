package ntukhpi.semit.militaryoblik.adapters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ntukhpi.semit.militaryoblik.entity.*;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.javafxutils.DataFormat;
import ntukhpi.semit.militaryoblik.utils.exportimport.EIDataPreparer;
import ntukhpi.semit.militaryoblik.utils.exportimport.EISettings;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExportAdapter implements IBaseAdapter {
    private PrepodAdapter prepod = new PrepodAdapter();
    private MilitaryPersonAdapter military = new MilitaryPersonAdapter();
    private ContactInfoAdapter contactInfo = new ContactInfoAdapter();
    private FakultetAdapter fakultet = new FakultetAdapter();
    private PositionAdapter position = new PositionAdapter();
    private CurrentDoljnostInfoAdapter currentDoljnost = new CurrentDoljnostInfoAdapter();
    private Set<EducationAdapter> educations = new HashSet<>();
    private Set<EducationPostgraduateAdapter> posteducations = new HashSet<>();
    private Set<FamilyAdapter> familyMembers = new HashSet<>();
    private Set<DocumentAdapter> documents = new HashSet<>();

    public ExportAdapter(Prepod prepod, MilitaryPerson militaryPerson) {
        this.prepod = new PrepodAdapter(prepod);
        this.military = new MilitaryPersonAdapter(militaryPerson);
        this.contactInfo = new ContactInfoAdapter(prepod.getContacts());
        this.fakultet = new FakultetAdapter(prepod.getKafedra().getFakultet());
        this.position = new PositionAdapter(prepod.getDolghnost());
        this.currentDoljnost = new CurrentDoljnostInfoAdapter(prepod);

        for (Education education : prepod.getEducationList()) {
            this.educations.add(new EducationAdapter(education));
        }
        for (EducationPostgraduate posteducation : prepod.getEducationPostList()) {
            this.posteducations.add(new EducationPostgraduateAdapter(posteducation));
        }
        for (FamilyMember familyMember : prepod.getFamily()) {
            this.familyMembers.add(new FamilyAdapter(familyMember));
        }
        for (Document document : prepod.getDocuments()) {
            this.documents.add(new DocumentAdapter(document));
        }
    }

    public String[] getGeneralInfoAsStringArray() {
        String surname = prepod.getSurname();
        String firstName = prepod.getName();
        String middleName = prepod.getMidname();
        String dateOfBirth = prepod.getBirth();
        String nationality = contactInfo.getCountry();
        String education = military.getEducationLevel();
        String category = position.getCategory();
        String degree = prepod.getDegree();
        String title = prepod.getStatus();
        String familyStatus = military.getFamilyState();
        String institute = fakultet.toString();
        String cathedra = prepod.getCathedra();
        String position = prepod.getPosition();
        String orderNumber = currentDoljnost.getNakazStart();
        String orderDate = currentDoljnost.getDateStart();
        String milGroup = military.getVGrupa();
        String milCategory = military.getVCategory();
        String milComposition = military.getVSklad();
        String milTitle = military.getVZvanie();
        String milSpeciality = military.getVos();
        String milSuitability = military.getVPrydatnist();
        String milOffice = military.getVoenkomat();
        String milSpecialRecord = military.getReserv();

        return new String[]{surname, firstName, middleName, dateOfBirth,
                            nationality, education, category, degree, title,
                            familyStatus, institute, cathedra, position,
                            orderNumber, orderDate, milGroup, milCategory, milComposition,
                            milTitle, milSpeciality, milSuitability, milOffice, milSpecialRecord};
    }

    public void setGeneralInfoAsStringArray(String[] arr) {
        String surname =            arr[0];
        String firstName =          arr[1];
        String middleName =         arr[2];
        String dateOfBirth =        arr[3];
//        String nationality =        arr[4];
        String education =          arr[5];
        String category =           arr[6];
        String degree =             arr[7];
        String title =              arr[8];
        String familyStatus =       arr[9];
        String institute =          arr[10];
        String cathedra =           arr[11];
        String position =           arr[12];
        String orderNumber =        arr[13];
        String orderDate =          arr[14];
        String milGroup =           arr[15];
        String milCategory =        arr[16];
        String milComposition =     arr[17];
        String milTitle =           arr[18];
        String milSpeciality =      arr[19];
        String milSuitability =     arr[20];
        String milOffice =          arr[21];
        String milSpecialRecord =   arr[22];

        prepod.setSurname(surname);
        prepod.setName(firstName);
        prepod.setMidname(middleName);
        prepod.setBirth(dateOfBirth);
//        contactInfo.setCountry(nationality);
        military.setEducationLevel(education);
        this.position.setCategory(category);
        prepod.setDegree(degree);
        prepod.setStatus(title);
        military.setFamilyState(familyStatus);
        fakultet.setName(institute);
        prepod.setCathedra(cathedra);
        prepod.setPosition(position);
        currentDoljnost.setNakazStart(orderNumber);
        currentDoljnost.setDateStart(orderDate);
        military.setVGrupa(milGroup);
        military.setVCategory(milCategory);
        military.setVSklad(milComposition);
        military.setVZvanie(milTitle);
        military.setVos(milSpeciality);
        military.setVPrydatnist(milSuitability);
        military.setVoenkomat(milOffice);
        military.setReserv(milSpecialRecord);
    }

    public String[] getContactInfoAsStringArray() {
        String country = contactInfo.getCountry();
        String region = contactInfo.getRegion();
        String city = contactInfo.getCity();
        String regionKh = "BOILERPLATE"; // FIXME: maybe delete this field from excel
        String address = contactInfo.getAddress();
        String index = contactInfo.getIndex();
        String mainPhone = contactInfo.getMainPhone();
        String secondPhone = contactInfo.getSecondPhone();
        String isFactEqual = contactInfo.isFactEqual() ? "ТАК" : "НІ";
        String countryFact = contactInfo.getCountryFact();
        String regionFact = contactInfo.getRegionFact();
        String cityFact = contactInfo.getCityFact();
        String regionKhFact = "BOILERPLATE"; // FIXME: maybe delete this field from excel
        String addressFact = contactInfo.getAddressFact();
        String indexFact = contactInfo.getIndexFact();

        return new String[]{country, region, city, regionKh, address, index,
                            mainPhone, secondPhone, isFactEqual, countryFact, regionFact, cityFact,
                            regionKhFact, addressFact, indexFact};
    }

    public void setContactInfoAsStringArray(String[] arr) {
            String country =          arr[0];
            String region =           arr[1];
            String city =             arr[2];
//            String regionKh =         arr[3];
            String address =          arr[4];
            String index =            arr[5];
            String mainPhone =        arr[6];
            String secondPhone =      arr[7];
            String isFactEqual =      arr[8];
            String countryFact =      arr[9];
            String regionFact =       arr[10];
            String cityFact =         arr[11];
//            String regionKhFact =     arr[12];
            String addressFact =      arr[13];
            String indexFact =        arr[14];

            contactInfo.setCountry(country);
            contactInfo.setRegion(region);
            contactInfo.setCity(city);
            contactInfo.setAddress(address);
            contactInfo.setIndex(index);
            contactInfo.setMainPhone(mainPhone);
            contactInfo.setSecondPhone(secondPhone);
            if (isFactEqual == "TAK") {
                contactInfo.setCountryFact(countryFact);
                contactInfo.setRegionFact(regionFact);
                contactInfo.setCityFact(cityFact);
                contactInfo.setAddressFact(addressFact);
                contactInfo.setIndexFact(indexFact);
            }
    }

    public List<String[]> getEducationsInfoAsStringArray() {
        List<String[]> educationsList = new ArrayList<>();

        for (EducationAdapter education : educations) {
            String name = DataFormat.safeStr(education.getVnz());
            String diplomaSeriesNumber = education.getDiplomaSeries() + education.getDiplomaNumber();
            String year = education.getYear();
            String speciality = education.getSpeciality();
            String qualification = education.getQualification();
            String form = education.getForm();
            String level = education.getLevel();

            educationsList.add(new String[]{name, diplomaSeriesNumber, year, speciality, qualification, form, level});
        }

        return educationsList;
    }

    public void setEducationsInfoAsStringArray(String[] arr) {
        List<String[]> educationsList = EIDataPreparer.chunksArray(arr, EISettings.EDUCATION_COL_COUNT);

        for (String[] education : educationsList) {

            String name =                   education[0];
            String diplomaSeries =          education[1];   // FIXME: separate series and number in Excel
            String diplomaNumber =          education[1];
            String year =                   education[2];
            String speciality =             education[3];
            String qualification =          education[4];
            String form =                   education[5];
            String level =                  education[6];

            VNZaklad vnZaklad = VNZaklad.getVNZakladByToString(name);

            this.educations.add(new EducationAdapter(null, year, diplomaSeries, diplomaNumber, speciality, qualification, vnZaklad, form, level));
        }
    }

    public List<String[]> getPosteducationsInfoAsStringArray() {
        List<String[]> posteducationsList = new ArrayList<>();

        for (EducationPostgraduateAdapter posteducation : posteducations) {
            String name = DataFormat.safeStr(posteducation.getVnz());
            String diplomaSeries = "BOILERPLATE";   // FIXME: No such field in DB
            String yearEnd = posteducation.getYear();
            String title = "BOILERPLATE";    // FIXME: No such field in DB
            String levelTraining = posteducation.getType();

            posteducationsList.add(new String[]{name, diplomaSeries, yearEnd, title, levelTraining});
        }

        return posteducationsList;
    }

    public void setPosteducationsInfoAsStringArray(String[] arr) {
        List<String[]> posteducationsList = EIDataPreparer.chunksArray(arr, EISettings.POSTEDUCATION_COL_COUNT);

        for (String[] posteducation : posteducationsList) {
            String name =           posteducation[0];
            String diplomaSeries =  posteducation[1];
            String yearEnd =        posteducation[2];
            String title =          posteducation[3];
            String levelTraining =  posteducation[4];

            VNZaklad vnZaklad = VNZaklad.getVNZakladByToString(name);

            this.posteducations.add(new EducationPostgraduateAdapter(null, levelTraining, vnZaklad, yearEnd));
        }
    }

    public List<String[]> getFamilyInfoAsStringArray() {
        List<String[]> familyList = new ArrayList<>();

        for (FamilyAdapter familyMember : familyMembers) {
            String level = familyMember.getVidRidstva();
            String pib = familyMember.getFullPib();
            String birth = familyMember.getRikNarodz();

            familyList.add(new String[]{level, pib, birth});
        }

        return familyList;
    }

    public void setFamilyInfoAsStringArray(String[] arr) {
        List<String[]> familyList = EIDataPreparer.chunksArray(arr, EISettings.FAMILY_COL_COUNT);

        for (String[] familyMember : familyList) {
            String level =  familyMember[0];
            String pib =    familyMember[1];
            String birth =  familyMember[2];

            // TODO: split PIB
            familyMembers.add(new FamilyAdapter(null, pib, pib, pib, level, birth));
        }
    }

    public List<String[]> getDocumentsAsStringArray() {
        List<String[]> documentsList = new ArrayList<>();

        for (DocumentAdapter document : documents) {
            String passportType = document.getType();
            String series = document.getNumber();
            String whoGives = document.getWhoGives();
            String date = document.getDate();

            documentsList.add(new String[]{passportType, series, whoGives, date});
        }

        return documentsList;

    }

    public void setDocumentsAsStringArray(String[] arr) {
        List<String[]> documentsList = EIDataPreparer.chunksArray(arr, EISettings.DOCUMENT_COL_COUNT);

        for (String[] document : documentsList) {
            String passportType =   arr[0];
            String series =         arr[1];
            String whoGives =       arr[2];
            String date =           arr[3];

            this.documents.add(new DocumentAdapter(null, passportType, series, whoGives, date));
        }
    }

    public String[] getSystemInfoAsStringArray() {
        return new String[]{prepod.getId().toString()};
    }
}
