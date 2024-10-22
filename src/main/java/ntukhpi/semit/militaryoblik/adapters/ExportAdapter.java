package ntukhpi.semit.militaryoblik.adapters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ntukhpi.semit.militaryoblik.entity.MilitaryPerson;
import ntukhpi.semit.militaryoblik.entity.PersonalData;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Fakultet;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;

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

    public ExportAdapter(Prepod prepod, MilitaryPerson militaryPerson, PersonalData personalData) {
        this.prepod = new PrepodAdapter(prepod);
        this.military = new MilitaryPersonAdapter(militaryPerson);
        this.contactInfo = new ContactInfoAdapter(personalData);
        this.fakultet = new FakultetAdapter(prepod.getKafedra().getFakultet());
        this.position = new PositionAdapter(prepod.getDolghnost());
        this.currentDoljnost = new CurrentDoljnostInfoAdapter(prepod);
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
}
