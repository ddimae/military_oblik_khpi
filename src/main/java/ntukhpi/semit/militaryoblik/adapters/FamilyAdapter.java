package ntukhpi.semit.militaryoblik.adapters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ntukhpi.semit.militaryoblik.entity.FamilyMember;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FamilyAdapter implements IBaseAdapter {
    private Long id;
    private String memFam;
    private String memName;
    private String memOtch;
    private String vidRidstva;
    private String rikNarodz;

    public FamilyAdapter(FamilyMember f) {
        this(f.getId(), f.getMemFam(), f.getMemImya(), f.getMemOtch(),
                f.getVidRidstva(), f.getRikNarodz());
    }

    public String getFullPib() {
        return memFam + " " + memName + " " + memOtch;
    }
}
