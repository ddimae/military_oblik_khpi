package ntukhpi.semit.militaryoblik.adapters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ntukhpi.semit.militaryoblik.entity.VNZaklad;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UniversityAdapter implements IBaseAdapter {
    Long id;
    String fullName;
    String shortName;

    public UniversityAdapter(VNZaklad vnZaklad) {
        this.fullName = vnZaklad.getVnzName();
        this.shortName = vnZaklad.getVnzShortName();
    }

    @Override
    public String toString() {
        return shortName + " (" + fullName + ")";
    }

    public static UniversityAdapter getUniversityAdapterByToString(String str) {
        UniversityAdapter vnzaklad = new UniversityAdapter();

        if (str == null || str.isEmpty())
            return vnzaklad;

        String[] arr = str.split(" \\(");

        vnzaklad.setShortName(arr[0]);
        vnzaklad.setFullName(arr[1].length() - 2 >= 0 ? arr[1].substring(0, arr[1].length() - 2) : "");

        return vnzaklad;
    }
}
