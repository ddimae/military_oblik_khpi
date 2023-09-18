package ntukhpi.semit.militaryoblik.adapters;

import lombok.Getter;
import lombok.Setter;
import ntukhpi.semit.militaryoblik.entity.CurrentDoljnostInfo;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Dolghnost;

import java.time.LocalDate;

@Getter
@Setter
public class DoljnostAdapter {
    private Long id;

    private LocalDate dateStart;
    private String nakazStart;
    private String commentStart;
    private LocalDate dateStop;
    private String nakazStop;
    private String commentStop;

    private String dolghnName;
    private int categoryEmployees;

    public DoljnostAdapter(Long id, LocalDate dateStart, String nakazStart, String commentStart,
                           LocalDate dateStop, String nakazStop, String commentStop) {
        this.id = id;
        this.dateStart = dateStart;
        this.nakazStart = nakazStart;
        this.commentStart = commentStart;
        this.dateStop = dateStop;
        this.nakazStop = nakazStop;
        this.commentStop = commentStop;
    }

    public DoljnostAdapter(CurrentDoljnostInfo d) {
        this(d.getId(), d.getDateStart(), d.getNumNakazStart(), d.getCommentStart(),
                d.getDateStop(), d.getNumNakazStop(), d.getCommentStop());
    }

    public DoljnostAdapter(Long id, String dolghnName, int categoryEmployees) {
        this.id = id;
        this.dolghnName = dolghnName;
        this.categoryEmployees = categoryEmployees;
    }

    public DoljnostAdapter(Dolghnost d) {
        this(d.getId(), d.getDolghnName(), d.getCategoryEmployees());
    }

    private CurrentDoljnostInfo currentDoljnostInfo;
    private Dolghnost dolghnost;

    public CurrentDoljnostInfo getCurrentDoljnostInfo() {
        return currentDoljnostInfo;
    }
    public void setCurrentDoljnostInfo(CurrentDoljnostInfo currentDoljnostInfo) {
        this.currentDoljnostInfo = currentDoljnostInfo;
    }

    public Dolghnost getDolghnost() {
        return dolghnost;
    }
    public void setDolghnost(Dolghnost dolghnost) {
        this.dolghnost = dolghnost;
    }
}
