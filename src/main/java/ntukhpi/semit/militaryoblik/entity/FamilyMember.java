package ntukhpi.semit.militaryoblik.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;

import java.util.Objects;

@Entity
@Table(name = "family_member")
@Getter
@Setter
@NoArgsConstructor
public class FamilyMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fmemeber_id")
    Long id;

    @ManyToOne
    @JoinColumn(name = "prepod_id", nullable = false)
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
    @Column(name = "vid_ridstva", length = 10, nullable = false)
    private String vid_ridstva;

    //Рік народження - 4 цифри, перевіряємо програмно тільки факт вводу 4 цифри
    @Column(name = "rik_narodz", length = 4)
    private String rikNarodz;

    public FamilyMember(Prepod prepod, String mem_fam, String mem_imya, String mem_otch,
                        String vid_ridstva, String rikNarodz) {
        this.prepod = prepod;
        this.mem_fam = mem_fam;
        this.mem_imya = mem_imya;
        this.mem_otch = mem_otch;
        this.vid_ridstva = vid_ridstva;
        this.rikNarodz = rikNarodz;
    }

    //Одинакові члени родини - всі однакові поля, крім іd

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("");
        //sb.append(prepod.getFam()).append(": ");
        sb.append(vid_ridstva).append(" - ");
        if (mem_fam != null && mem_fam.length() > 0)
            sb.append(mem_fam);
        if (mem_imya != null && mem_imya.length() > 0)
            sb.append(" ").append(mem_imya);
        if (mem_otch != null && mem_otch.length() > 0)
            sb.append(" ").append(mem_otch);
        if (rikNarodz!=null && rikNarodz.length()>0) {
            if ((mem_fam != null && mem_fam.length() > 0) ||
                    (mem_imya != null && mem_imya.length() > 0) ||
                    (mem_otch != null && mem_otch.length() > 0)) {
                sb.append(", ");
            }
                sb.append(rikNarodz).append(" р.н.");

        }
        return sb.toString();
    }
}
