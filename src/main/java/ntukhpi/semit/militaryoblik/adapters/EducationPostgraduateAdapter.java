package ntukhpi.semit.militaryoblik.adapters;


public class EducationPostgraduateAdapter {
    Integer year;
    String type;
    String vnz;

    public EducationPostgraduateAdapter(String type, String vnz, Integer year) {
        this.year = year;
        this.type = type;
        this.vnz = vnz;
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
    public void setVnz(String vnz) {
        this.vnz = vnz;
    }
    public String getVnz() {
        return vnz;
    }
}
