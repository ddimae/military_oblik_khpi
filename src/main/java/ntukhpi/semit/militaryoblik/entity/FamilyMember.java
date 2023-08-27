package ntukhpi.semit.militaryoblik.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;

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
    @Column(name = "vid_ridstva", length = 10)
    private String vid_ridstva;

    //Рік народження - 4 цифри, перевіряємо програмно тільки факт вводу 4 цифри
    @Column(name = "rik_narodz", length = 4)
    private String rikNarodz;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "family_stat_id")
    private FamilyState prepodFamilyState;



}
