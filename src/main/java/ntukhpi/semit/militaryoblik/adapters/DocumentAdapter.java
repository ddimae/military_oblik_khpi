package ntukhpi.semit.militaryoblik.adapters;

import lombok.Getter;
import lombok.Setter;
import ntukhpi.semit.militaryoblik.entity.Document;

import java.time.LocalDate;

@Getter
@Setter
public class DocumentAdapter {
    private Long id;
    private String type;
    private String number;
    private String whoGives;
    private LocalDate date;

    public DocumentAdapter() {}

    public DocumentAdapter(Long id, String type, String number, String whoGives, LocalDate date) {
        this.id = id;
        this.type = type;
        this.number = number;
        this.whoGives = whoGives;
        this.date = date;
    }

    public DocumentAdapter(Document d) {
        this(d.getId(), d.getDocType(), d.getDocNumber(), d.getKtoVyd(), d.getDataVyd());
    }
}
