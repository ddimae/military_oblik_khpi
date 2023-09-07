package ntukhpi.semit.militaryoblik.adapters;


import ntukhpi.semit.militaryoblik.entity.Education;
import ntukhpi.semit.militaryoblik.entity.EducationPostgraduate;
import ntukhpi.semit.militaryoblik.entity.VNZaklad;

public class EducationPostgraduateAdapter {
    private Integer year;
    private String type;
    private VNZaklad vnz;

    public EducationPostgraduateAdapter(String type, VNZaklad vnz, Integer year) {
        this.year = year;
        this.type = type;
        this.vnz = vnz;
    }

    public EducationPostgraduateAdapter(EducationPostgraduate e) {
        this(e.getLevelTraining(), e.getVnz(), e.getYearFinish());
    }

    public void setYear(Integer year) {
        this.year = year;
    }
    public Integer getYear() {
        return year;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
    public void setVnz(VNZaklad vnz) {
        this.vnz = vnz;
    }
    public VNZaklad getVnz() {
        return vnz;
    }
}
