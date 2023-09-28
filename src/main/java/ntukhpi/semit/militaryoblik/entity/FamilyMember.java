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

    @Column(name = "mem_fam",length = 40)
    private String memFam;
    @Column(name = "mem_imya",length = 30)
    private String memImya;
    @Column(name = "mem_otch",length = 30)
    private String memOtch;

    // Ступінь рідства
    // Обирається з переліку (фіксований)
    // чоловік; дружина; син; донька
    // Правільність вводу не контролюємо: чоловік Петрова, син Альона - проблема тих, хто вводить
    @Column(name = "vid_ridstva", length = 10, nullable = false)
    private String vidRidstva;

    //Рік народження - 4 цифри, перевіряємо програмно тільки факт вводу 4 цифри
    @Column(name = "rik_narodz", length = 4)
    private String rikNarodz;

    public FamilyMember(Prepod prepod, String memFam, String memImya, String memOtch,
                        String vidRidstva, String rikNarodz) {
        this.prepod = prepod;
        this.memFam = memFam;
        this.memImya = memImya;
        this.memOtch = memOtch;
        this.vidRidstva = vidRidstva;
        this.rikNarodz = rikNarodz;
    }

    //Одинакові члени родини - всі однакові поля, крім іd


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FamilyMember that = (FamilyMember) o;

        if (!prepod.equals(that.prepod)) return false;
        if (!Objects.equals(memFam, that.memFam)) return false;
        if (!memImya.equals(that.memImya)) return false;
        if (!Objects.equals(memOtch, that.memOtch)) return false;
        if (!vidRidstva.equals(that.vidRidstva)) return false;
        return Objects.equals(rikNarodz, that.rikNarodz);
    }

    @Override
    public int hashCode() {
        int result = prepod.hashCode();
        result = 31 * result + (memFam != null ? memFam.hashCode() : 0);
        result = 31 * result + memImya.hashCode();
        result = 31 * result + (memOtch != null ? memOtch.hashCode() : 0);
        result = 31 * result + vidRidstva.hashCode();
        result = 31 * result + (rikNarodz != null ? rikNarodz.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("");
        //sb.append(prepod.getFam()).append(": ");
        sb.append(vidRidstva).append(" - ");
        if (memFam != null && memFam.length() > 0)
            sb.append(memFam);
        if (memImya != null && memImya.length() > 0)
            sb.append(" ").append(memImya);
        if (memOtch != null && memOtch.length() > 0)
            sb.append(" ").append(memOtch);
        if (rikNarodz!=null && rikNarodz.length()>0) {
            if ((memFam != null && memFam.length() > 0) ||
                    (memImya != null && memImya.length() > 0) ||
                    (memOtch != null && memOtch.length() > 0)) {
                sb.append(", ");
            }
                sb.append(rikNarodz).append(" р.н.");

        }
        return sb.toString();
    }
}
