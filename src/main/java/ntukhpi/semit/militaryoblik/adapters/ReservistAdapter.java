package ntukhpi.semit.militaryoblik.adapters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ntukhpi.semit.militaryoblik.entity.MilitaryPerson;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;

import java.time.format.DateTimeFormatter;

/**
 * Автор - Линьков А.
 * Класс для хранения информации о студентах. Временное решение, необходимое для работы с таблицей.
 */
@Getter
@Setter
@AllArgsConstructor
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

    public ReservistAdapter(MilitaryPerson militaryPerson) {
        Prepod prep = militaryPerson.getPrepod();
        this.id = prep.getId();
        this.pib = prep.getFam() + " " + prep.getImya() + " " + prep.getOtch();
        this.dr = prep.getDr().toString(); // format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));//"01.01.0000";
        this.gender = "муж"; // DDE - Еще бы понять, откуда брать значение...
        this.trc = militaryPerson.getVoenkomat().getVoenkomatName();
        this.rank = militaryPerson.getVZvanie().getZvanieName();
        this.vos = militaryPerson.getVos();
        int kodSkladu = militaryPerson.getVZvanie().getKodSkladu();
        this.type = kodSkladu == 1 ? "Офіцерський склад" : "Рядовий та сержантський склад";
        this.category = "" + militaryPerson.getVCategory();
        this.tck = this.trc; //<=== DDE ??? Внимательно присмотревшись так и не понял ШО ЦЕ?
        this.institute = prep.getKafedra().getFakultet().getFname();
        this.cathedra = prep.getKafedra().getKname();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReservistAdapter that = (ReservistAdapter) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
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
