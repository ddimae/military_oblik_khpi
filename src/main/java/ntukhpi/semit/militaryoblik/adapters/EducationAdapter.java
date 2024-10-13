package ntukhpi.semit.militaryoblik.adapters;

import lombok.Getter;
import lombok.Setter;
import ntukhpi.semit.militaryoblik.entity.Education;
import ntukhpi.semit.militaryoblik.entity.VNZaklad;

@Getter
@Setter
public class EducationAdapter {
    private Long id;
    private String year;
    private String diplomaSeries;
    private String diplomaNumber;
    private String speciality;
    private String qualification;

    private VNZaklad vnz;
    private VNZaklad vnzShortName;
    private String form;
    private String level;

    public EducationAdapter(Long id, String year, String diplomaSeries, String diplomaNumber,
                            String speciality, String qualification, VNZaklad vnz,
                            String form, String level) {
        this.id = id;
        this.year = year;
        this.diplomaSeries = diplomaSeries;
        this.diplomaNumber = diplomaNumber;
        this.speciality = speciality;
        this.qualification = qualification;
        this.vnz = vnz;
        this.form = form;
        this.level = level;
    }

    public EducationAdapter(Education e) {
        this(e.getId(), e.getYearVypusk(), e.getDiplomaSeries(), e.getDiplomaNumber(),
                e.getDiplomaSpeciality(), e.getDiplomaQualification(), e.getVnz(),
                e.getFormTraining(), e.getLevelTraining());
    }
}
