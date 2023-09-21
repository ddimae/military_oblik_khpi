package ntukhpi.semit.militaryoblik.entity.fromasukhpi;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Table(name = "fakult")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Fakultet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fid")
    private Long fid;

    //Скорочене найменування
    @Column(length = 10, nullable = false, unique = true)
    private String abr;

    //??? true (1) = ???
    @Column(name = "sm_o")
    private Boolean sm_o;

    //??? true (1) = ???
    @Column(name = "sm_v")
    private Boolean sm_v;

    //Найменування факультету
    @Column(length = 100, nullable = false, unique = true)
    private String fname;

    //Імя та ПРІЗВИЩЕ декана
    @Column(length = 30)
    private String dekan;

    //Імя та ПРІЗВИЩЕ секретара приймальної комісії (???)
    @Column(length = 60)
    private String spk;

    //???? true (1) = ???
    @Column(name = "ok_yn")
    private Boolean okYn;

    //???? true (1) = ???
    @Column(name = "dog_otdel")
    private Boolean dogOtdel;

    //Є заочне навчання? true (1) = так
    @Column(name = "zaochniy")
    private Boolean zaochniy;

    //Телефон: 707-63-11 або 7076814 або пусто
    @Column(length = 10)
    private String telef;

    //Ідентифікатор факультету в НТУ "ХПі" - код підрозділу для діловодства
    //Опис в конструкторі : "oid"       smallint,  <=== чому в лапках?!
    @Column(name = "oid", nullable = false, unique = true)
    private String oid;

    //ENGLISH
    //Найменування факультету
    @Column(length = 100, name = "fname_e")
    private String fnameE;

    //Декан факультету
    @Column(length = 30, name = "dekan_e")
    private String dekanE;
//---- The end English -------

    //Декан факультету попередній
    @Column(length = 30, name = "dekan_old")
    private String dekanOld;

    //Назва посади керівника підрозділу на укр і инглиш
    //Опис в конструкторі : "position"  varchar(30),  <=== чому в лапках?!
    @Column(length = 30, name = "position")
    private String position;

    @Column(length = 30, name = "position_e")
    private String positionE;

    //Скорочене найменування new
    @Column(length = 10, name = "abr_new")
    private String abrNew;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("");
        sb.append(fname).append(" (");
        sb.append(abr).append(")");
        return sb.toString();
    }

}
