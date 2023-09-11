package ntukhpi.semit.militaryoblik.utils;

import ntukhpi.semit.militaryoblik.adapters.D05Adapter;
import org.springframework.stereotype.Component;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Component
public class DataPreparer {

    public List<D05Adapter> sortD5AdapterByUAAlphabet(List<D05Adapter> list, String sortedField) {
        Collator collator = Collator.getInstance(new Locale("uk", "UA"));

        if("name".equals(sortedField)) {
            Collections.sort(list, Comparator.comparing(D05Adapter::getPib, collator));
        } else if ("tck".equals(sortedField))
            Collections.sort(list, Comparator.comparing(D05Adapter::getTerCentr, collator));
        return list;
    }

    public String[][] listToArray(List<D05Adapter> list) {
        String[][] workingData = new String[list.size()][18];
        for (int i = 0; i < list.size(); i++) {
            workingData[i] = list.get(i).objectToList();
        }

        return workingData;
    }
}
