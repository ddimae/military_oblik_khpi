package ntukhpi.semit.militaryoblik.adapters;

import lombok.*;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;

import java.util.Objects;

/**
 * Автор - Линьков А.
 * Класс для хранения информации о студентах. Временное решение, необходимое для работы с таблицей.
 */
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class ReservistAdapter {
    private long id;
    private String pib;
    private String dr;
    private String gender;
    private String trc;
    private String rank;
    private String vos;
    private String type;
    private String category;

    //Поля для фильтрации. В таблице не отображаются.
    private String tck;
    private String institute;
    private String cathedra;


    public ReservistAdapter(String pib, String dr, String gender,
                            String trc, String rank, String vos,
                            String type, String category,
                            String tck, String institute, String cathedra) {
        this.pib = pib;
        this.dr = dr;
        this.gender = gender;
        this.trc = trc;
        this.rank = rank;
        this.vos = vos;
        this.type = type;
        this.category = category;
        this.tck = tck;
        this.institute = institute;
        this.cathedra = cathedra;
    }

    public ReservistAdapter(Prepod prep) {
        this.id = prep.getId();
        this.pib = prep.getFam() + " " + prep.getImya() + " " + prep.getOtch();
        this.dr = "01.01.0000";
        this.gender = "муж";
        this.trc = "Десь у Харкові";
        this.rank = "рядовий";
        this.vos = "123456";
        this.type = "Рядовий та сержантський склад";
        this.category = "інженерно-технічний";
        this.tck = "Просто ТЦК";
        this.institute = prep.getKafedra().getFakultet().getFname();
        this.cathedra = prep.getKafedra().getKname();
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ReservistAdapter{");
        sb.append(pib);
        sb.append(" ").append(dr);
        sb.append(" (").append(cathedra);
        sb.append(", ").append(institute);
        sb.append(')');
        return sb.toString();
    }
}
