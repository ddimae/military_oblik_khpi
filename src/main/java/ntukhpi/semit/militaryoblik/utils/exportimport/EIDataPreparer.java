package ntukhpi.semit.militaryoblik.utils.exportimport;

import java.util.ArrayList;
import java.util.List;

public class EIDataPreparer {
    public static String[] stringsListToStringArray(List<String[]> list, int maxStringGroupsCapacity, int strArrLen) {
        List<String> arr = new ArrayList<>(maxStringGroupsCapacity);
        int count = 0;


        for (String[] string : list) {
            for (String s : string) {
                arr.add(s);
            }
            count++;
        }
        for (; count < maxStringGroupsCapacity; count++) {
           for (int i = 0; i < strArrLen; i++) {
               arr.add("empty");
           }
        }

        return arr.toArray(new String[0]);
    }
}
