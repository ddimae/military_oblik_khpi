package ntukhpi.semit.militaryoblik.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;

@Entity
@Table(name = "education_after")
@Getter
@Setter
@NoArgsConstructor
public class EducationPostgraduate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "edu_post_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "prepod_id", nullable = false)
    private Prepod prepod;

    //Заклад вищої освіти, який закінчив співробітник
    //private String vnz;
    @ManyToOne
    @JoinColumn(name = "vnz_id", nullable = false)
    private VNZaklad vnz;

    //Рік закінчення
    @Column(name = "year_end", length = 4, nullable = false)
    private String yearFinish;

    //Рівень навчання - аспірантура, ад'юнктура, докторантура
    //Обирається з переліку (фіксований)
    private String levelTraining;

    //Однаковим вважати записи, в яких співпадає Препод, ВНЗ та рік
    // ? можливо ВНЗ для після дипломної освіти зайве....
    // Аспирантуру у двух вузах ніхто не закінчує

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EducationPostgraduate that = (EducationPostgraduate) o;

        if (!prepod.equals(that.prepod)) return false;
        return yearFinish.equals(that.yearFinish);
    }

    @Override
    public int hashCode() {
        int result = prepod.hashCode();
        result = 31 * result + yearFinish.hashCode();
        return result;
    }
}


