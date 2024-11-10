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
        if (category == 1)
            return "НПП";
        if (category == 2)
            return "ІТС";
        return null;
    }

    public void setCategory(String category) {
        if (category.equals("НПП"))
            this.category = 1;
        if (category.equals("ІТС"))
            this.category = 2;
    }

    public PositionAdapter(Dolghnost d) {
        this.fullName = d.getDolghnName();
        this.shortName = d.getDolghnShortName();
        this.category = d.getCategoryEmployees();
    }
}
