package ntukhpi.semit.militaryoblik.entity.fromasukhpi;

import lombok.*;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Entity
//Унікальним робимо ПІБ+кафедра.
//Тобто приймаємо за аксіому, що однофамільці на одній кафедрі не працюють
@Table(name = "prepod",uniqueConstraints = @UniqueConstraint(columnNames = {"fam", "imya","otch","kid"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Prepod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prepod_id")
    Long id;

    @Column(length = 40,nullable = false)
    private String fam;
    @Column(length = 30,nullable = false)
    private String imya;
    @Column(length = 30,nullable = false)
    private String otch;

    //дата рождения
    @Column(name = "data_rozd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dr;

    //В БД АСУ УПД ХПИ можно получить дані про посаду, яку обіймає співробітник
    //Тобто фіксується поточна (або остання посада) співробітника
    // Пара dolghnost + kafedra (або пара полів в таблиці dolghn_id + kid) визначають, яку посаду та на якій кафедрі
    // працює співробітник.
    // Для обліку не вистачає інформації про накази про призначення на посаду,
    // а також про звільнення з посади, тому що деякий час інформація про таких співробітників зберігається у 2 відділі.
    //Через те, що це клас "незмінний", створений клас CurrentDoljnostInfo, який буде зберігати дані про накази
    //про призначення та звільнення
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kid",nullable = false)
    private Kafedra kafedra;

    @ManyToOne
    @JoinColumn(name = "dolghn_id",nullable = false)
    private Dolghnost dolghnost;

    //Вчене звання - це доцент, професор, старший науковий співробітник, академік, тощо
    @ManyToOne
    @JoinColumn(name = "zvanie_id")
    private Zvanie zvanie;

    //Науковий ступінь - кандидат технічних наук, доктор технічних наук
    @ManyToOne
    @JoinColumn(name = "stepen_id")
    private Stepen stepen;

    @Column
    private String email;

    public Prepod(String fam, String imya, String otch, Kafedra kafedra, Dolghnost dolghnost, Zvanie zvanie, Stepen stepen, String email) {
        this.fam = fam;
        this.imya = imya;
        this.otch = otch;
        this.kafedra = kafedra;
        this.dolghnost = dolghnost;
        this.zvanie = zvanie;
        this.stepen = stepen;
        this.email = email;
    }

    public Prepod(String fam, String imya, String otch, Kafedra kafedra) {
        this.fam = fam;
        this.imya = imya;
        this.otch = otch;
        this.kafedra = kafedra;
    }

    public Prepod(String fam, String imya, String otch, Kafedra kafedra, Dolghnost dolghnost) {
        this.fam = fam;
        this.imya = imya;
        this.otch = otch;
        this.kafedra = kafedra;
        this.dolghnost = dolghnost;
    }
    public Prepod(String fam, String imya, String otch, LocalDate dr,
                  Kafedra kafedra, Dolghnost dolghnost,
                  Stepen stepen, Zvanie zvanie,String email) {
        this.fam = fam;
        this.imya = imya;
        this.otch = otch;
        this.dr = dr;
        this.kafedra = kafedra;
        this.dolghnost = dolghnost;
        this.stepen = stepen;
        this.zvanie =zvanie;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Prepod prepod = (Prepod) o;

        if (!fam.equals(prepod.fam)) return false;
        if (!imya.equals(prepod.imya)) return false;
        if (!otch.equals(prepod.otch)) return false;
        return kafedra.equals(prepod.kafedra);
    }

    @Override
    public int hashCode() {
        int result = fam.hashCode();
        result = 31 * result + imya.hashCode();
        result = 31 * result + otch.hashCode();
        result = 31 * result + kafedra.hashCode();
        return result;
    }
}
