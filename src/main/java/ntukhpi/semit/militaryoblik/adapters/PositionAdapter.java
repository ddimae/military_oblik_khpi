package ntukhpi.semit.militaryoblik.adapters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Dolghnost;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PositionAdapter implements IBaseAdapter {
    private String fullName;
    private String shortName;
    private int category;

    public String getCategory() {
        return category == 1 ? "ППС" : "ІТС";
    }

    public PositionAdapter(Dolghnost d) {
        this.fullName = d.getDolghnName();
        this.shortName = d.getDolghnShortName();
        this.category = d.getCategoryEmployees();
    }
}
