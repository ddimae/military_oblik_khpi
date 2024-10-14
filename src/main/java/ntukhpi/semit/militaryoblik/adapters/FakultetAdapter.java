package ntukhpi.semit.militaryoblik.adapters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FakultetAdapter implements IBaseAdapter {
    private String id;
    private String name;
    private String abbr;
    private String code;

    @Override
    public String toString() {
        return getName();
    }
}
