package ntukhpi.semit.militaryoblik.utils.D5;

import ntukhpi.semit.militaryoblik.adapters.D05Adapter;
import org.springframework.stereotype.Component;

import java.text.Collator;
import java.util.*;

@Component
public class D5DataPreparer {

    public Map<String, List<D05Adapter>> sortD5AdapterByUAAlphabet(Map<String, List<D05Adapter>> map, String sortedField) {
        Collator collator = Collator.getInstance(new Locale("uk", "UA"));
        for (List<D05Adapter> adapters: map.values()) {
            if ("name".equals(sortedField)) {
                Collections.sort(adapters, Comparator.comparing(D05Adapter::getPib, collator));
            } else if ("tck".equals(sortedField)) {
                Collections.sort(adapters, Comparator.comparing(D05Adapter::getTerCentr, collator));
            }
        }
        return map;
    }

    public String[][] listToArray(List<D05Adapter> list) {
        String[][] workingData = new String[list.size()][18];
        for (int i = 0; i < list.size(); i++) {
            workingData[i] = list.get(i).objectToList();
        }

        return workingData;
    }
}
