package ntukhpi.semit.militaryoblik.adapters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ntukhpi.semit.militaryoblik.javafxutils.DataFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CathedraAdapter implements IBaseAdapter {
    private String institute;
    private String fullName;
    private String abbr;
    private String code;
}
