package ntukhpi.semit.militaryoblik.adapters;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ntukhpi.semit.militaryoblik.entity.MilitaryPerson;
import ntukhpi.semit.militaryoblik.entity.VSklad;
import ntukhpi.semit.militaryoblik.entity.VZvanie;
import ntukhpi.semit.militaryoblik.entity.Voenkomat;
import ntukhpi.semit.militaryoblik.javafxutils.DataFormat;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MilitaryPersonAdapter implements IBaseAdapter {
    private Long id;
    private String vos;
    private String vCategory;
    private String vGrupa;
    private String vSklad;
    private String vZvanie;
    private String voenkomat;
    private String reserv;
    private String vPrydatnist;
    private String familyState;
    private String educationLevel;

    public MilitaryPersonAdapter(MilitaryPerson militaryPerson) {
        this.id = militaryPerson.getId();
        this.vos = militaryPerson.getVos();
        this.vCategory = DataFormat.safeStr(militaryPerson.getVCategory());
        this.vGrupa = militaryPerson.getVGrupa();
        this.vSklad = DataFormat.safeStr(militaryPerson.getVSklad());
        this.vZvanie = DataFormat.safeStr(militaryPerson.getVZvanie());
        this.voenkomat = DataFormat.safeStr(militaryPerson.getVoenkomat());
        this.reserv = militaryPerson.getReserv();
        this.vPrydatnist = militaryPerson.getVPrydatnist();
        this.familyState = militaryPerson.getFamilyState();
        this.educationLevel = militaryPerson.getEducationLevel();
    }
}