package ntukhpi.semit.militaryoblik.utils.exportimport;

import ntukhpi.semit.militaryoblik.adapters.DocumentAdapter;
import ntukhpi.semit.militaryoblik.adapters.EIAdapter;

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
//        strList.remove("Закордонний паспорт");

        return strList.toArray(new String[0]);
    }

    public static <T> List<T[]> chunksArray(T[] arr, int chunkLen) {
        List<T[]> chunksList = new ArrayList<>();

        for (int i = 0; i < arr.length; i += chunkLen)
            chunksList.add(Arrays.copyOfRange(arr, i, Math.min(arr.length, i + chunkLen)));

        return chunksList;
    }

    public static List<String[]> exportAdapterToDataList(EIAdapter exportAdapter) {
        List<String[]> workingDatas = new ArrayList<>();

        String[] generalInfo = exportAdapter.getGeneralInfoAsStringArray();
        String[] contactInfo = exportAdapter.getContactInfoAsStringArray();
        String[] educationsInfo = EIDataPreparer.stringsListToStringArray(exportAdapter.getEducationsInfoAsStringArray(),
                EISettings.MAX_EDUCATION_NUMBER,
                EISettings.EDUCATION_COL_COUNT);
        String[] posteducationsInfo = EIDataPreparer.stringsListToStringArray(exportAdapter.getPosteducationsInfoAsStringArray(),
                EISettings.MAX_POSTEDUCATION_NUMBER,
                EISettings.POSTEDUCATION_COL_COUNT);
        String[] familyMembersInfo = EIDataPreparer.stringsListToStringArray(exportAdapter.getFamilyInfoAsStringArray(),
                EISettings.MAX_FAMILY_NUMBER,
                EISettings.FAMILY_COL_COUNT);
        String[] documentsInfo = EIDataPreparer.stringsDocumentsListToStringArray(exportAdapter.getDocumentsAsStringArray());

        workingDatas.add(generalInfo);
        workingDatas.add(contactInfo);
        workingDatas.add(educationsInfo);
        workingDatas.add(posteducationsInfo);
        workingDatas.add(familyMembersInfo);
        workingDatas.add(documentsInfo);

        return workingDatas;
    }

    public static EIAdapter dataToImportAdapter(String[] data) {
        EIAdapter importAdapter = new EIAdapter();
        int offset = 0;
        int groupLen = 0;

        for (int i = 0; i < EISettings.GLOBAL_DATAGROUPS_COUNT; i++) {
            groupLen = switch (i) {
                case 0 -> EISettings.GLOBAL_GENERAL_DATA_COUNT;
                case 1 -> EISettings.GLOBAL_CONTACTS_DATA_COUNT;
                case 2 -> EISettings.GLOBAL_EDUCATION_DATA_COUNT;
                case 3 -> EISettings.GLOBAL_POSTEDUCATION_DATA_COUNT;
                case 4 -> EISettings.GLOBAL_FAMILY_DATA_COUNT;
                case 5 -> EISettings.GLOBAL_DOCUMENTS_DATA_COUNT;
                default -> groupLen;
            };

            String[] subArr = Arrays.copyOfRange(data, EISettings.GLOBAL_IMPORT_ROW + offset - 1, offset + groupLen);

            System.out.println("===============================");
            for (int x = 0; x < subArr.length; x++)
                System.out.println(subArr[x]);

            offset += groupLen;

            switch (i) {
                case 0:
                    importAdapter.setGeneralInfoAsStringArray(subArr);
                    break;
                case 1:
                    importAdapter.setContactInfoAsStringArray(subArr);
                    break;
                case 2:
                    importAdapter.setEducationsInfoAsStringArray(subArr);
                    break;
                case 3:
                    importAdapter.setPosteducationsInfoAsStringArray(subArr);
                    break;
                case 4:
                    importAdapter.setFamilyInfoAsStringArray(subArr);
                    break;
                case 5:
                    importAdapter.setDocumentsAsStringArray(subArr);
                    break;
            }
        }

        return importAdapter;
    }

    public static EIAdapter mergeEIAdapters(EIAdapter source, EIAdapter target) {
        EIAdapter merged = new EIAdapter();

        merged.merge(source, true);
        merged.merge(target, true);

        merged.setEducations(target.getEducations());
        merged.setPosteducations(target.getPosteducations());
        merged.setFamilyMembers(target.getFamilyMembers());

        List<DocumentAdapter> sourceDocuments = source.getDocuments().stream().toList();
        List<DocumentAdapter> targetDocuments = target.getDocuments().stream().toList();

        for (int i = 0; i < 3; i++)
            sourceDocuments.get(i).merge(targetDocuments.get(i), true);

        merged.setDocuments(source.getDocuments());


//        TestAdapter a = new TestAdapter("A", "B", new CathedraAdapter("C", "D", "E", "F"));
//        TestAdapter b = new TestAdapter("A1", "", new CathedraAdapter(null, "D1", "E1", "  "));

//        a.merge(b, true);
//
//        System.out.println(a.getName());
//        System.out.println(a.getFigma());
//        System.out.println(a.getCathedra().getInstitute());
//        System.out.println(a.getCathedra().getFullName());
//        System.out.println(a.getCathedra().getAbbr());
//        System.out.println(a.getCathedra().getCode());
//        System.out.println("=================");

//        CathedraAdapter ca = a.getCathedra();
//        ca.setInstitute("TEST");
//        a.setCathedra(ca);
//
//        System.out.println(a.getName());
//        System.out.println(a.getFigma());
//        System.out.println(a.getCathedra().getInstitute());
//        System.out.println(a.getCathedra().getFullName());
//        System.out.println(a.getCathedra().getAbbr());
//        System.out.println(a.getCathedra().getCode());
//        System.out.println("=================");
//        System.out.println(b.getName());
//        System.out.println(b.getFigma());
//        System.out.println(b.getCathedra().getInstitute());
//        System.out.println(b.getCathedra().getFullName());
//        System.out.println(b.getCathedra().getAbbr());
//        System.out.println(b.getCathedra().getCode());
//        System.out.println("=================");

        return merged;
    }
}
