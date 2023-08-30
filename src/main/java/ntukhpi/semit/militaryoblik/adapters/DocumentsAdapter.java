package ntukhpi.semit.militaryoblik.adapters;

import java.time.LocalDate;
import java.util.Date;

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

    public String getPib() {
        return pib;
    }

    public void setPib(String pib) {
        this.pib = pib;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getWhoGives() {
        return whoGives;
    }

    public void setWhoGives(String whoGives) {
        this.whoGives = whoGives;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
