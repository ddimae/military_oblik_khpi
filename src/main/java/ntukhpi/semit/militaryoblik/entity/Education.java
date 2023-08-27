package ntukhpi.semit.militaryoblik.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;

@Entity
@Table(name = "education")
@Getter
@Setter
@NoArgsConstructor
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "edu_id")
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
    @Column(name = "year_vypusk",length = 4)
    private String yearVypusk;
    //Серия и номер диплома про закінчення
    @Column(name = "diploma_series",length = 3)
    private String diplomaSeries;
    @Column(name = "diploma_numbers",length = 12)
    private String diplomaNumber;

    //Специальность подготовки
    // У меня - Математичне та програмне забезпечення АСУ
    //На кафедре - 121 Інженерія програмного забезпечення та 122 Комп"ютерні науки
    @Column(name = "diploma_spec")
    private String diplomaSpeciality;
    //Квалификація
    //У меня - інженер-математик
    //У вас будет бакалавр або магістр інженерії програмного забезпечення
    @Column(name = "diploma_qualification")
    private String diplomaQualification;

    //Форма навчання - денна або заочна
    //Обирається з переліку (фіксований)
    private String formTraining;
    //Рівень навчання - бакалавр, магістр, спеціаліст
    //Обирається з переліку (фіксований)
    private String levelTraining;

}
