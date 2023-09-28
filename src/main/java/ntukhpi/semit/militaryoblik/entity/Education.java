package ntukhpi.semit.militaryoblik.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import org.hibernate.annotations.ColumnDefault;

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
    @JoinColumn(name = "prepod_id",nullable = false)
    private Prepod prepod;

    //Заклад вищої освіти, який закінчив співробітник
    //private String vnz;
    @ManyToOne
    @JoinColumn(name = "vnz_id",nullable = false)
    private VNZaklad vnz;

    //Рік закінчення
    @Column(name = "year_vypusk",length = 4,nullable = false)
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
    @Column(name = "form_training")
    @ColumnDefault(value = "Денна")
    private String formTraining;
    //Рівень навчання - бакалавр, магістр, спеціаліст
    //Обирається з переліку (фіксований)
    @Column(name = "level_training")
    private String levelTraining;

    //Однаковим вважати записи, в яких співпадає Препод, ВНЗ та рік
    // Можно одночасно отримати в один рік два дипломи, але у різних вузах
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Education education = (Education) o;

        if (!prepod.equals(education.prepod)) return false;
        if (!vnz.equals(education.vnz)) return false;
        return yearVypusk.equals(education.yearVypusk);
    }

    @Override
    public int hashCode() {
        int result = prepod.hashCode();
        result = 31 * result + vnz.hashCode();
        result = 31 * result + yearVypusk.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("");
        sb.append(yearVypusk).append(" - ").append(vnz.getVnzShortName());
        if (diplomaSpeciality!=null&&diplomaSpeciality.length()>0) sb.append(", ").append(diplomaSpeciality);
        if (diplomaNumber!=null&&diplomaNumber.length()>0) {
            sb.append(" (");
            if (diplomaSeries!=null&&diplomaSeries.length()>0) sb.append(diplomaSeries);
            sb.append(" №").append(diplomaNumber);
            sb.append(")");
        }

        return sb.toString();
    }

}
