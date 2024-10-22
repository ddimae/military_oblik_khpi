package ntukhpi.semit.militaryoblik.adapters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Fakultet;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FakultetAdapter implements IBaseAdapter {
    private String id;
    private String name;
    private String abbr;
    private String code;

    public FakultetAdapter(Fakultet f) {
        this.id = f.getFid().toString();
        this.name = f.getFname();
        this.abbr = f.getAbr();
        this.code = f.getOid();
    }

    @Override
    public String toString() {
        return getName();
    }
}
