package ntukhpi.semit.militaryoblik.adapters;

import lombok.Getter;
import lombok.Setter;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.*;

import java.time.LocalDate;

@Getter
@Setter
public class PrepodAdapter {
    private Long id;
    private String surname;
    private String name;
    private String midname;
    private LocalDate birth;
    private Kafedra cathedra;
    private Dolghnost position;
    private Zvanie status;
    private Stepen degree;

    public PrepodAdapter() {}

    public PrepodAdapter(Long id, String surname, String name,
                         String midname, LocalDate birth, Kafedra cathedra,
                         Dolghnost position, Zvanie status, Stepen degree) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.midname = midname;
        this.birth = birth;
        this.cathedra = cathedra;
        this.position = position;
        this.status = status;
        this.degree = degree;
    }

    public PrepodAdapter(Prepod prepod) {
        this(prepod.getId(), prepod.getFam(), prepod.getImya(),
                prepod.getOtch(), prepod.getDr(), prepod.getKafedra(),
                prepod.getDolghnost(), prepod.getZvanie(), prepod.getStepen());
    }
}
