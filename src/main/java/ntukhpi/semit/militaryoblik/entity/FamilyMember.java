package ntukhpi.semit.militaryoblik.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;

import java.util.Objects;

@Entity
@Table(name = "family_member")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class FamilyMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fmemeber_id")
    Long id;

    @ManyToOne
    @JoinColumn(name = "prepod_id",nullable = false)
    private Prepod prepod;

    @Column(length = 40)
    private String mem_fam;
    @Column(length = 30)
    private String mem_imya;
    @Column(length = 30)
    private String mem_otch;

    // Ступінь рідства
    // Обирається з переліку (фіксований)
    // чоловік; дружина; син; донька
    // Правільність вводу не контролюємо: чоловік Петрова, син Альона - проблема тих, хто вводить
    @Column(name = "vid_ridstva", length = 10,nullable = false)
    private String vid_ridstva;

    //Рік народження - 4 цифри, перевіряємо програмно тільки факт вводу 4 цифри
    @Column(name = "rik_narodz", length = 4,nullable = false)
    private String rikNarodz;

    //Одинакові члени родини - всі однакові поля, крім іd


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FamilyMember that = (FamilyMember) o;

        if (!prepod.equals(that.prepod)) return false;
        if (!Objects.equals(mem_fam, that.mem_fam)) return false;
        if (!Objects.equals(mem_imya, that.mem_imya)) return false;
        if (!Objects.equals(mem_otch, that.mem_otch)) return false;
        if (!vid_ridstva.equals(that.vid_ridstva)) return false;
        return rikNarodz.equals(that.rikNarodz);
    }

    @Override
    public int hashCode() {
        int result = prepod.hashCode();
        result = 31 * result + (mem_fam != null ? mem_fam.hashCode() : 0);
        result = 31 * result + (mem_imya != null ? mem_imya.hashCode() : 0);
        result = 31 * result + (mem_otch != null ? mem_otch.hashCode() : 0);
        result = 31 * result + vid_ridstva.hashCode();
        result = 31 * result + rikNarodz.hashCode();
        return result;
    }
}
