package ntukhpi.semit.militaryoblik.entity.fromasukhpi;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="kafedra")
@Getter
@Setter
@NoArgsConstructor
public class Kafedra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kid")
    private Long kid;

    //Найменування кафедри повне
    @Column(length = 100, nullable = false,unique = true)
    private String kname;

    //Зв"язок із факультетом-інститутом
    @ManyToOne
    @JoinColumn(name = "fid", nullable = false)
    private Fakultet fakultet;

    //Скорочене найменування кафедри
    @Column(length = 10, nullable = false,unique = true)
    private String kabr;

    //Ідентифікатор кафедри в НТУ "ХПI" - код підрозділу для діловодства
    //Опис в конструкторі : "oid"       smallint,  <=== чому в лапках?!
    @Column(name = "oidkafedra",nullable = false, unique = true)
    private String oid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Kafedra kafedra = (Kafedra) o;

        return kname.equals(kafedra.kname);
    }

    @Override
    public int hashCode() {
        return kname.hashCode();
    }

    //Імя та прізвище завідувача : Олег РЕЗИНКІН або пусто
    @Column(length = 40)
    private String zavkaf;

    //Кафедральний телефон: 707-63-11 або 7076814 або мобільний або пусто
    // 30 - багато, но так зараз в БД АСУ УП ХПИ
    @Column(length = 30)
    private String telef;

    //Загальноуніверситетьська? true (1) = так
    @Column(name = "zagal")
    private Boolean zagal;

    //Випускна? true (1) = так
    @Column(name = "vipusk")
    private Boolean vipusk;

    //співробітник для зв'язку (???)
    @Column(length = 100)
    private String worker;

    //телефон співробітника для зв'язку
    @Column(length = 30,name = "work_telef")
    private String workTelef;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("");
        sb.append(kname).append(" (").append(kabr).append(',').append(oid);
        if (fakultet != null) {
            sb.append(',').append(fakultet.getAbr());
        }
        sb.append(")");
        return sb.toString();
    }

    public String showKafForPrepod() {
        final StringBuilder sb = new StringBuilder("");
        sb.append(" (").append(kabr);
        if (fakultet != null) {
            sb.append(',').append(fakultet.getAbr());
        }
        sb.append(")");
        return sb.toString();
    }

}
