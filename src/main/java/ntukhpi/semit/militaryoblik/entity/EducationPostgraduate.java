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
public class EducationPostgraduate  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "edu_post_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "prepod_id")
    private Prepod prepod;

    //Заклад вищої освіти, який закінчив співробітник
    //private String vnz;
    @ManyToOne
    @JoinColumn(name = "vnz_id")
    private VNZaklad vnz;

    //Рік закінчення
    @Column(name = "year_end",length = 4)
    private String yearFinish;

    //Рівень навчання - аспірантура, ад'юнктура, докторантура
    //Обирається з переліку (фіксований)
    private String levelTraining;

}
