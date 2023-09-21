package ntukhpi.semit.militaryoblik.adapters;


import lombok.Getter;
import lombok.Setter;
import ntukhpi.semit.militaryoblik.entity.Education;
import ntukhpi.semit.militaryoblik.entity.EducationPostgraduate;
import ntukhpi.semit.militaryoblik.entity.VNZaklad;

@Getter
@Setter
public class EducationPostgraduateAdapter {
    private Long id;
    private String year;
    private String type;
    private VNZaklad vnz;

    public EducationPostgraduateAdapter(Long id, String type, VNZaklad vnz, String year) {
        this.id = id;
        this.year = year;
        this.type = type;
        this.vnz = vnz;
    }

    public EducationPostgraduateAdapter(EducationPostgraduate e) {
        this(e.getId(), e.getLevelTraining(), e.getVnz(), e.getYearFinish());
    }
}
