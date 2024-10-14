package ntukhpi.semit.militaryoblik.adapters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ntukhpi.semit.militaryoblik.entity.Document;
import ntukhpi.semit.militaryoblik.javafxutils.DataFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentAdapter implements IBaseAdapter {
    private Long id;
    private String type;
    private String number;
    private String whoGives;
    private String date;

    public DocumentAdapter(Document d) {
        this(d.getId(), d.getDocType(), d.getDocNumber(), d.getKtoVyd(), DataFormat.localDateToUkStandart(d.getDataVyd()));
    }
}
