package ntukhpi.semit.militaryoblik.adapters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ntukhpi.semit.militaryoblik.entity.Education;
import ntukhpi.semit.militaryoblik.entity.EducationPostgraduate;
import ntukhpi.semit.militaryoblik.entity.MilitaryPerson;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.javafxutils.DataFormat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExportAdapter implements IBaseAdapter {
    private PrepodAdapter prepod;
    private MilitaryPersonAdapter military;
    private ContactInfoAdapter contactInfo;
    private FakultetAdapter fakultet;
    private PositionAdapter position;
    private CurrentDoljnostInfoAdapter currentDoljnost;
    private Set<EducationAdapter> educations = new HashSet<>();
    private Set<EducationPostgraduateAdapter> posteducations = new HashSet<>();

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

    public List<String[]> getEducationsInfoAsStringArray() {
        List<String[]> educationsList = new ArrayList<>();

        for (EducationAdapter education : educations) {
            String name = DataFormat.safeStr(education.getVnz());
            String diplomaSeries = education.getDiplomaSeries() + education.getDiplomaNumber();
            String year = education.getYear();
            String speciality = education.getSpeciality();
            String qualification = education.getQualification();
            String form = education.getForm();
            String level = education.getLevel();

            educationsList.add(new String[]{name, diplomaSeries, year, speciality, qualification, form, level});
        }

        return educationsList;
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
}
