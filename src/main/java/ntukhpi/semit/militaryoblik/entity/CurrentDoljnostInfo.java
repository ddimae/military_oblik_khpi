package ntukhpi.semit.militaryoblik.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "doljnost_nakazy")
@Getter
@Setter
@NoArgsConstructor
public class CurrentDoljnostInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "curr_dolj_id")
    Long id;

    @OneToOne
    @JoinColumn(name = "prepod_id",unique = true,nullable = false)
    private Prepod prepod;

    //ІНФО ПРО ПРИЗНАЧЕННЯ
    //Дата наказу про призначення
    @Column(name = "date_start",nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateStart;

    //Номер наказу про призначення
    @Column(name = "nakaz_start", length = 12,nullable = false)
    private String numNakazStart;

    //Поле для нотаток щодо призначення
    @Column(name = "comment_start")
    private String commentStart;

    //ІНФО ПРО ЗВІЛЬНЕННЯ
    //Дата наказу про звільнення
    @Column(name = "date_stop")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateStop;

    //Номер наказу про звільнення
    @Column(name = "nakaz_stop", length = 12)
    private String numNakazStop;

    //Поле для нотаток щодо звільнення
    @Column(name = "comment_stop")
    private String commentStop;


    public CurrentDoljnostInfo(
            Prepod selectedPrepod, String nakaz, LocalDate date, String comment,
            String nakazDiss, LocalDate dateDiss, String commentDiss) {
        this.id=0L;
        this.prepod = selectedPrepod;
        this.numNakazStart = nakaz;
        this.dateStart = date;
        this.commentStart = comment;
        this.numNakazStop = nakazDiss;
        this.dateStop = dateDiss;
        this.commentStop = commentDiss;
    }
}
