package ntukhpi.semit.militaryoblik.adapters;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class DocumentsAdapter {
    private String pib;
    private String type;
    private String series;
    private String number;
    private String whoGives;
    private LocalDate date;

    public DocumentsAdapter() {}

    public DocumentsAdapter(String pib, String type, String series, String number, String whoGives, LocalDate date) {
        this.pib = pib;
        this.type = type;
        this.series = series;
        this.number = number;
        this.whoGives = whoGives;
        this.date = date;
    }
}
