package ntukhpi.semit.militaryoblik.utils.exportimport;

import java.util.AbstractMap;
import java.util.Map;

public final class EISettings {
    public final static String TEMPLATE_PATH = "docs/templates/update_form_p2.xlsx";
    public final static String TEMPLATE_PASSWORD = "oblik";

    public final static int GLOBAL_IMPORT_ROW = 1;
    public final static int GLOBAL_IMPORT_COLUMN = 2;
    public final static int GLOBAL_EXPORT_ROW = 1;
    public final static int GLOBAL_EXPORT_COLUMN = 3;
    public final static int GLOBAL_SHEET_INDEX = 6;

    public final static int MAX_EDUCATION_NUMBER = 4;
    public final static int EDUCATION_COL_COUNT = 8;
    public final static int EDUCATION_SHEET_INDEX = 3;
    public final static int EDUCATION_GLOBAL_TABLE_START_COLUMN = 6;
    public final static int EDUCATION_GLOBAL_TABLE_START_ROW = 1;
    public final static int EDUCATION_DEST_TABLE_START_COLUMN = 0;
    public final static int EDUCATION_DEST_TABLE_START_ROW = 4;

    public final static int MAX_POSTEDUCATION_NUMBER = 6;
    public final static int POSTEDUCATION_COL_COUNT = 3;
    public final static int POSTEDUCATION_SHEET_INDEX = 3;
    public final static int POSTEDUCATION_GLOBAL_TABLE_START_COLUMN = 6;
    public final static int POSTEDUCATION_GLOBAL_TABLE_START_ROW = 7;
    public final static int POSTEDUCATION_DEST_TABLE_START_COLUMN = 0;
    public final static int POSTEDUCATION_DEST_TABLE_START_ROW = 12;

    public final static int MAX_FAMILY_NUMBER = 6;
    public final static int FAMILY_COL_COUNT = 5;
    public final static int FAMILY_SHEET_INDEX = 4;
    public final static int FAMILY_GLOBAL_TABLE_START_COLUMN = 6;
    public final static int FAMILY_GLOBAL_TABLE_START_ROW = 15;
    public final static int FAMILY_DEST_TABLE_START_COLUMN = 0;
    public final static int FAMILY_DEST_TABLE_START_ROW = 4;

    public final static int DOCUMENT_COL_COUNT = 4;

    public final static int GLOBAL_DROPDOWN_ROW = 16;
    public final static int GLOBAL_DROPDOWN_COLUMN = 16;

    public final static int GLOBAL_GENERAL_DATA_COUNT = 23;
    public final static int GLOBAL_CONTACTS_DATA_COUNT = 13;
    public final static int GLOBAL_EDUCATION_DATA_COUNT = MAX_EDUCATION_NUMBER * EDUCATION_COL_COUNT;
    public final static int GLOBAL_POSTEDUCATION_DATA_COUNT = MAX_POSTEDUCATION_NUMBER * POSTEDUCATION_COL_COUNT;
    public final static int GLOBAL_FAMILY_DATA_COUNT = MAX_POSTEDUCATION_NUMBER * FAMILY_COL_COUNT;
    public final static int GLOBAL_DOCUMENTS_DATA_COUNT = 12;
    public final static int GLOBAL_DATAGROUPS_COUNT = 6;

    public final static Map<String, Integer> documentsOrder = Map.ofEntries(
            new AbstractMap.SimpleEntry<String, Integer>("Паперовий паспорт", 0),
            new AbstractMap.SimpleEntry<String, Integer>("ID картка", 0),
            new AbstractMap.SimpleEntry<String, Integer>("Військовий квиток", 1),
            new AbstractMap.SimpleEntry<String, Integer>("Посвідчення особи офіцера", 1),
            new AbstractMap.SimpleEntry<String, Integer>("Військовий квиток офіцера запасу", 1),
            new AbstractMap.SimpleEntry<String, Integer>("Закордонний паспорт", 2)
    );
}
