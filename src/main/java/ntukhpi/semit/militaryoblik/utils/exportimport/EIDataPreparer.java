package ntukhpi.semit.militaryoblik.utils.exportimport;

import java.util.*;

public class EIDataPreparer {
    public static String[] stringsListToStringArray(List<String[]> list, int maxStringGroupsCapacity, int strArrLen) {
        List<String> arr = new ArrayList<>(maxStringGroupsCapacity);
        int count = 0;


        for (String[] strings : list) {
            arr.addAll(Arrays.asList(strings));
            count++;
        }
        addEmptyStringsToList(arr, (maxStringGroupsCapacity - count) * strArrLen);

        return arr.toArray(new String[0]);
    }

    public static void addEmptyStringsToList(List<String> list, int count) {
        list.addAll(Collections.nCopies(count, ""));
    }

    public static String[] stringsDocumentsListToStringArray(List<String[]> documents) {
        List<String> strList = new ArrayList<>();
        int count = 0;

        documents = documents.stream().sorted((o1, o2) -> EISettings.documentsOrder.get(o1[0]) - EISettings.documentsOrder.get(o2[0])).toList();

        for (int i = 0; i < 3; i++) {
            if (documents.size() <= count || EISettings.documentsOrder.get(documents.get(count)[0]) != i) {
                int linesCount = switch (i) {
                    case 0, 1 -> 4;
                    case 2 -> 3;
                    default -> 0;
                };

                addEmptyStringsToList(strList, linesCount);
            } else {
                String[] temp2 = documents.get(count);
                List<String> temp = Arrays.asList(temp2);
                strList.addAll(temp);
                count++;
            }
        }
        strList.remove("Закордонний паспорт");

        return strList.toArray(new String[0]);
    }
}
