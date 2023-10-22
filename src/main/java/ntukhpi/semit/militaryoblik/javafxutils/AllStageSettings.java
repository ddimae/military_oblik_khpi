package ntukhpi.semit.militaryoblik.javafxutils;


/**
 * Клас для зберігання налаштувань створення сцен
 *
 * @author Степанов Михайло
 */
public final class AllStageSettings {
    public final static String STYLES_JAVAFX = "/javafxview/MOStyles.css";

    //============================

    private final static String LOGIN_JAVAFX = "/javafxview/LoginForm.fxml";
    private final static String LOGIN_JAVAFX_TITLE = "Вхід в застосунок";
    public final static SettingsStage loginSettings =
                        new SettingsStage(LOGIN_JAVAFX, LOGIN_JAVAFX_TITLE,
                                400, 300, false, false);

    //============================

    private final static String EDUCATION_JAVAFX = "/javafxview/EducationAll.fxml";
    private final static String EDUCATION_JAVAFX_TITLE = "Освіта";

    public final static SettingsStage educationAll =
                        new SettingsStage(EDUCATION_JAVAFX, EDUCATION_JAVAFX_TITLE,
                                0, 0, false, false);

    //============================

    private final static String POSITION_JAVAFX = "/javafxview/PositionOrdersEdit.fxml";
    private final static String POSITION_JAVAFX_TITLE = "Накази про призначення";

    public final static SettingsStage positionEdit =
                        new SettingsStage(POSITION_JAVAFX, POSITION_JAVAFX_TITLE,
                                0,0,false, false);

    //============================

    private final static String FAMILY_JAVAFX = "/javafxview/FamilyCompositionAll.fxml";
    private final static String FAMILY_JAVAFX_TITLE = "Родина";

    public final static SettingsStage familyAll =
                        new SettingsStage(FAMILY_JAVAFX, FAMILY_JAVAFX_TITLE,
                                0,0,false, false);

    //============================

    private final static String DOCUMENTS_JAVAFX = "/javafxview/DocumentsAll.fxml";
    private final static String DOCUMENTS_JAVAFX_TITLE = "Документи";

    public final static SettingsStage documentsAll =
                        new SettingsStage(DOCUMENTS_JAVAFX, DOCUMENTS_JAVAFX_TITLE,
                                0, 0, false, false);

    //============================

    private final static String CONTACTS_EDIT_JAVAFX = "/javafxview/ContactsEdit.fxml";
    private final static String CONTACTS_EDIT_JAVAFX_TITLE = "Контактна інформація";
    public final static SettingsStage contactsEdit =
                        new SettingsStage(CONTACTS_EDIT_JAVAFX, CONTACTS_EDIT_JAVAFX_TITLE,
                                0, 0, false, false);
    //============================

    private final static String MILITARY_REGISTRATION_JAVAFX = "/javafxview/MilitaryRegistration.fxml";
    private final static String MILITARY_REGISTRATION_JAVAFX_TITLE = "Дані військового обліку";
    public final static SettingsStage militaryRegistrationEdit =
                        new SettingsStage(MILITARY_REGISTRATION_JAVAFX, MILITARY_REGISTRATION_JAVAFX_TITLE,
                                0, 0, false, false);

    //============================

    private final static String EMPLOYEE_ADD_JAVAFX = "/javafxview/EmployeeAdd.fxml";
    private final static String EMPLOYEE_ADD_JAVAFX_TITLE = "Додати нового працівника";
    public final static SettingsStage employeeAdd =
            new SettingsStage(EMPLOYEE_ADD_JAVAFX, EMPLOYEE_ADD_JAVAFX_TITLE,
                    0, 0, false, false);

    //============================

    private final static String MILITARY_OBLIK_LIST_JAVAFX = "/javafxview/ReservistsAll.fxml";
    private final static String MILITARY_OBLIK_LIST_JAVAFX_TITLE = "Військовий облік НТУ \"ХПІ\"";

    public static SettingsStage militaryOblikSettings =
            new SettingsStage(MILITARY_OBLIK_LIST_JAVAFX, MILITARY_OBLIK_LIST_JAVAFX_TITLE,
                    1500, 720, false, false);

    //============================

    private final static String EDUCATION_POSTGRADUATE_JAVAFX = "/javafxview/EducationPostgraduateAll.fxml";
    private final static String EDUCATION_POSTGRADUATE_JAVAFX_TITLE = "Післядипломна освіта";
    public static SettingsStage educationPostgraduateAll =
                    new SettingsStage(EDUCATION_POSTGRADUATE_JAVAFX, EDUCATION_POSTGRADUATE_JAVAFX_TITLE,
                            0, 0, false, false);

    //============================

    private final static String VNZ_ADD_JAVAFX = "/javafxview/AddVNZ.fxml";
    private final static String VNZ_ADD_JAVAFX_TITLE = "Додати ВНЗ";
    public static SettingsStage vnzAdd =
            new SettingsStage(VNZ_ADD_JAVAFX, VNZ_ADD_JAVAFX_TITLE,
                    0, 0, false, false);

    //============================

    private final static String DOCUMENTS_ADD_JAVAFX = "/javafxview/DocumentsEdit.fxml";
    private final static String DOCUMENTS_ADD_JAVAFX_TITLE = "Додати новий документ";
    public static SettingsStage documentsAdd =
                    new SettingsStage(DOCUMENTS_ADD_JAVAFX, DOCUMENTS_ADD_JAVAFX_TITLE,
                            0, 0, false, false);

    //============================

    private final static String DOCUMENTS_EDIT_JAVAFX = DOCUMENTS_ADD_JAVAFX;
    private final static String DOCUMENTS_EDIT_JAVAFX_TITLE = "Редагувати документ";
    public static SettingsStage documentsEdit =
                    new SettingsStage(DOCUMENTS_EDIT_JAVAFX, DOCUMENTS_EDIT_JAVAFX_TITLE,
                            0, 0, false, false);

    //============================

    private final static String EDUCATION_EDIT_JAVAFX = "/javafxview/EducationEdit.fxml";
    private final static String EDUCATION_EDIT_JAVAFX_TITLE = "Редагувати освіту";
    public static SettingsStage educationEdit =
                    new SettingsStage(EDUCATION_EDIT_JAVAFX, EDUCATION_EDIT_JAVAFX_TITLE,
                            0, 0, false, false);

    //============================

    private final static String EDUCATION_ADD_JAVAFX = EDUCATION_EDIT_JAVAFX;
    private final static String EDUCATION_ADD_JAVAFX_TITLE = "Додати освіту";
    public static SettingsStage educationAdd =
            new SettingsStage(EDUCATION_ADD_JAVAFX, EDUCATION_ADD_JAVAFX_TITLE,
                    0, 0, false, false);

    //============================

    private final static String EDUCATION_POSTGRADUATE_EDIT_JAVAFX = "/javafxview/EducationPostgraduateEdit.fxml";
    private final static String EDUCATION_POSTGRADUATE_EDIT_JAVAFX_TITLE = "Редагувати аспірантуру, тощо";
    public static SettingsStage educationPostgraduateEdit =
                    new SettingsStage(EDUCATION_POSTGRADUATE_EDIT_JAVAFX, EDUCATION_POSTGRADUATE_EDIT_JAVAFX_TITLE,
                            0, 0, false, false);

    //============================

    private final static String EDUCATION_POSTGRADUATE_ADD_JAVAFX = EDUCATION_POSTGRADUATE_EDIT_JAVAFX;
    private final static String EDUCATION_POSTGRADUATE_ADD_JAVAFX_TITLE = "Додати аспірантуру, тощо";
    public static SettingsStage educationPostgraduateAdd =
                    new SettingsStage(EDUCATION_POSTGRADUATE_ADD_JAVAFX, EDUCATION_POSTGRADUATE_ADD_JAVAFX_TITLE,
                            0, 0, false, false);

    //============================

    private final static String FAMILY_ADD_JAVAFX = "/javafxview/FamilyCompositionEdit.fxml";
    private final static String FAMILY_ADD_JAVAFX_TITLE = "Додати нового члена родини";
    public static SettingsStage familyAdd =
                    new SettingsStage(FAMILY_ADD_JAVAFX, FAMILY_ADD_JAVAFX_TITLE,
                            0, 0, false, false);

    //============================

    private final static String FAMILY_EDIT_JAVAFX = FAMILY_ADD_JAVAFX;
    private final static String FAMILY_EDIT_JAVAFX_TITLE = "Редагувати склад родини";
    public static SettingsStage familyEdit =
                new SettingsStage(FAMILY_EDIT_JAVAFX, FAMILY_EDIT_JAVAFX_TITLE,
                        0, 0, false, false);

    //============================

    private final static String INSTITUTE_ADD_JAVAFX = "/javafxview/InstituteAdd_2.fxml";
    private final static String INSTITUTE_ADD_JAVAFX_TITLE = "Додавання нового інституту";
    public static SettingsStage instituteAdd =
            new SettingsStage(INSTITUTE_ADD_JAVAFX, INSTITUTE_ADD_JAVAFX_TITLE,
                    0, 0, false, false);

    //============================

    private final static String CATHEDRA_ADD_JAVAFX = "/javafxview/CathedraAdd.fxml";
    private final static String CATHEDRA_ADD_JAVAFX_TITLE = "Додавання нової кафедри";
    public static SettingsStage cathedraAdd =
            new SettingsStage(CATHEDRA_ADD_JAVAFX, CATHEDRA_ADD_JAVAFX_TITLE,
                    0, 0, false, false);

    //============================

    private final static String POSITION_ADD_JAVAFX = "/javafxview/PositionAdd.fxml";
    private final static String POSITION_ADD_JAVAFX_TITLE = "Додавання нової кафедри";
    public static SettingsStage positionAdd =
            new SettingsStage(POSITION_ADD_JAVAFX, POSITION_ADD_JAVAFX_TITLE,
                    0, 0, false, false);
}
