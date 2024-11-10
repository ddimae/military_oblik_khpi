package ntukhpi.semit.militaryoblik.adapters;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ntukhpi.semit.militaryoblik.entity.Education;
import ntukhpi.semit.militaryoblik.entity.EducationPostgraduate;
import ntukhpi.semit.militaryoblik.entity.VNZaklad;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EducationPostgraduateAdapter implements IBaseAdapter {
    private Long id;
    private String type;
    private VNZaklad vnz;
    private String year;

    public EducationPostgraduateAdapter(EducationPostgraduate e) {
        this(e.getId(), e.getLevelTraining(), e.getVnz(), e.getYearFinish());
    }
}
