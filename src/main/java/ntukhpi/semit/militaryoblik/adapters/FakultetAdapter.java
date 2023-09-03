package ntukhpi.semit.militaryoblik.adapters;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakultetAdapter {
    private String id;
    private String name;

    public FakultetAdapter(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
