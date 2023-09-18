package ntukhpi.semit.militaryoblik.adapters;

import lombok.Getter;
import lombok.Setter;
import ntukhpi.semit.militaryoblik.entity.FamilyMember;

@Getter
@Setter
public class FamilyAdapter {
    Long id;
    private String memFam;
    private String memName;
    private String memOtch;
    private String vidRidstva;
    private String rikNarodz;

    FamilyAdapter(Long id, String memFam, String memName, String memOtch,
                  String vidRidstva, String rikNarodz) {
        this.id = id;
        this.memFam = memFam;
        this.memName = memName;
        this.memOtch = memOtch;
        this.vidRidstva = vidRidstva;
        this.rikNarodz = rikNarodz;
    }

    public FamilyAdapter(FamilyMember f) {
        this(f.getId(), f.getMem_fam(), f.getMem_imya(), f.getMem_otch(),
                f.getVid_ridstva(), f.getRikNarodz());
    }

    public String getFullPib() {
        return memFam + " " + memName + " " + memOtch;
    }
}
