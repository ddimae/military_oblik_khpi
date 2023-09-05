package ntukhpi.semit.militaryoblik.adapters;

import lombok.Getter;
import lombok.Setter;
import ntukhpi.semit.militaryoblik.entity.Document;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class DocumentsAdapter {
    private Long id;
    private String type;
    private String number;
    private String whoGives;
    private LocalDate date;

    public DocumentsAdapter() {}

    public DocumentsAdapter(Long id, String type, String number, String whoGives, LocalDate date) {
        this.id = id;
        this.type = type;
        this.number = number;
        this.whoGives = whoGives;
        this.date = date;
    }

    public DocumentsAdapter(Document d) {
        this(d.getId(), d.getDocType(), d.getDocNumber(), d.getKtoVyd(), d.getDataVyd());
    }
}
