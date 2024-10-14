package ntukhpi.semit.militaryoblik.adapters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.*;
import ntukhpi.semit.militaryoblik.javafxutils.DataFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrepodAdapter implements IBaseAdapter {
    private String institute;
    private String surname;
    private String name;
    private String midname;
    private String birth;
    private String cathedra;
    private String position;
    private String status;
    private String degree;

    public PrepodAdapter(Prepod prepod) {
        this(null , prepod.getFam(), prepod.getImya(),
                prepod.getOtch(), DataFormat.localDateToUkStandart(prepod.getDr()), prepod.getKafedra().toString(),
                prepod.getDolghnost().toString(), prepod.getZvanie().toString(), prepod.getStepen().toString());
    }
}
