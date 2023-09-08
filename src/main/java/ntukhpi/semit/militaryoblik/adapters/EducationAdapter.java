package ntukhpi.semit.militaryoblik.adapters;

import lombok.Getter;
import lombok.Setter;
import ntukhpi.semit.militaryoblik.entity.Education;
import ntukhpi.semit.militaryoblik.entity.VNZaklad;

@Getter
@Setter
public class EducationAdapter {
    private String year;
    private String diplomaSeries;
    private String diplomaNumber;
    private String speciality;
    private String qualification;

    private VNZaklad vnz;
    private String form;
    private String level;

    //private String type;

    public EducationAdapter(String year, String diplomaSeries, String diplomaNumber,
                            String speciality, String qualification, VNZaklad vnz,
                            String form, String level) {
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
        this(e.getYearVypusk(), e.getDiplomaSeries(), e.getDiplomaNumber(),
                e.getDiplomaSpeciality(), e.getDiplomaQualification(), e.getVnz(),
                e.getFormTraining(), e.getLevelTraining());
    }

    public EducationAdapter() {}

//    public Integer getYear() {
//        return year;
//    }
//    public void setYear(Integer year) {
//        this.year = year;
//    }
//    public String getDiplomaSeries() {return diplomaSeries;};
//    public void setDiplomaSeries(String diplomaSeries) {
//        this.diplomaSeries = diplomaSeries;
//    }
//    public Integer getDiplomaNumber() {
//        return diplomaNumber;
//    }
//    public void setDiplomaNumber(Integer diplomaNumber) {
//        this.diplomaNumber = diplomaNumber;
//    }
//    public String getSpeciality() {
//        return speciality;
//    }
//    public void setSpeciality(String speciality) {
//        this.speciality = speciality;
//    }
//    public String getQualification() {
//        return qualification;
//    }
//    public void setQualification(String qualification) {
//        this.qualification = qualification;
//    }
//
//
//    public EducationAdapter getVnz() {
//        return vnz;
//    }
//
//    public void setVnz(EducationAdapter vnz) {
//        this.vnz = vnz;
//    }
//    public String getForm() {
//        return form;
//    }
//    public void setForm(String form) {
//        this.form = form;
//    }
//    public String getLevel() {
//        return level;
//    }
//    public void setLevel(String level) {
//        this.level = level;
//    }

//    public void setType(String type) {
//        this.type = type;
//    }
//    public String getType() {
//        return type;
//    }


}
