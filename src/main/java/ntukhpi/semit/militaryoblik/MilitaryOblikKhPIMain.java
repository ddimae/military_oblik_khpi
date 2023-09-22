package ntukhpi.semit.militaryoblik;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ntukhpi.semit.militaryoblik.entity.VNZaklad;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.javafxutils.ControlledScene;
import ntukhpi.semit.militaryoblik.javafxutils.SettingsStage;
//import ntukhpi.semit.militaryoblik.javafxview.AddVNZController;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.sqlite.date.DateParser;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

public class MilitaryOblikKhPIMain extends Application {
    public final static String STYLES_JAVAFX = "/javafxview/MOStyles.css";
    //============================
    public final static String LOGIN_JAVAFX = "/javafxview/LoginForm.fxml";
    public final static String LOGIN_JAVAFX_TITLE = "Вхід в застосунок";
    private SettingsStage loginSettings =
            new SettingsStage(LOGIN_JAVAFX, LOGIN_JAVAFX_TITLE,
                    400, 300, false, false);

    //============================

    private final static String EDUCATION_JAVAFX = "/javafxview/EducationAll.fxml";
    private final static String EDUCATION_JAVAFX_TITLE = "Освіта";

    private static SettingsStage educationAll =
            new SettingsStage(EDUCATION_JAVAFX, EDUCATION_JAVAFX_TITLE,
                    0, 0, false, true);

    //============================
    private final static String POSITION_JAVAFX = "/javafxview/PositionEdit.fxml";
    private final static String POSITION_JAVAFX_TITLE = "Посада";

    private static SettingsStage positionEdit =
            new SettingsStage(POSITION_JAVAFX, POSITION_JAVAFX_TITLE,
                    0,0,false, true);

    //============================
    private final static String FAMILY_JAVAFX = "/javafxview/FamilyCompositionAll.fxml";
    private final static String FAMILY_JAVAFX_TITLE = "Родина";

    private static SettingsStage familyAll =
            new SettingsStage(FAMILY_JAVAFX, FAMILY_JAVAFX_TITLE,
                    0,0,false, true);

    //============================
    private final static String DOCUMENTS_JAVAFX = "/javafxview/DocumentsAll.fxml";
    private final static String DOCUMENTS_JAVAFX_TITLE = "Документи";

    private static SettingsStage documentsAll =
            new SettingsStage(DOCUMENTS_JAVAFX, DOCUMENTS_JAVAFX_TITLE,
                    0, 0, false, true);

    //============================
//    private final static String MILITARY_REGISTRATION_JAVAFX = "/javafxview/MilitaryRegistration.fxml";
//    private final static String MILITARY_REGISTRATION_JAVAFX_TITLE = "Дані військового обліку";
//
//    private static SettingsStage registrationAll =
//            new SettingsStage(MILITARY_REGISTRATION_JAVAFX, MILITARY_REGISTRATION_JAVAFX_TITLE, 0, 0, false, true);
    //============================
    private final static String EDUCATION_POSTGRADUATE_JAVAFX = "/javafxview/EducationPostgraduateAll.fxml";
    private final static String EDUCATION_POSTGRADUATE_JAVAFX_TITLE = "Післядипломна освіта";
    private static SettingsStage educationPostgraduateAll =
            new SettingsStage(EDUCATION_POSTGRADUATE_JAVAFX, EDUCATION_POSTGRADUATE_JAVAFX_TITLE,
                    0, 0, false, true);
    //============================
    private final static String VNZ_ADD_JAVAFX = "/javafxview/AddVNZ.fxml";
    private final static String VNZ_ADD_JAVAFX_TITLE = "Додати ВНЗ";
    //============================

    public static ConfigurableApplicationContext applicationContext; //was private
    static Parent rootNode; //was private

    @Override
    public void init() {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(MilitaryAccounting.class);
        applicationContext = builder.run();
    }

    @Override
    public void start(Stage primaryStage) {
//        showLogInWindow(primaryStage);
        showLogInWindow();

    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }

    /**
     * Метод запуска окон редактирования
     * автор Кулак Анастасия
     *
     */
//    public static void openEditWindow(String fxmlFileName, String title, Object controller, Object selectedObject) {
//        try {
//            //FIXME При вызове окна редактирования основная форма сворачивается. Это неудобно.
//            // Нашел баг. Можно создать бесконечное количество окон
//            FXMLLoader loader = new FXMLLoader(MilitaryOblikKhPIMain.class.getResource(fxmlFileName));
//            loader.setControllerFactory(aClass -> applicationContext.getBean(aClass));
//            Parent root = loader.load();
//
//            ControlledScene editController = loader.getController();
//            editController.setMainController(controller);
//            editController.setData(selectedObject);
//
//            Stage stage = new Stage();
//            stage.setTitle(title);
//            stage.setResizable(false);
//            stage.initStyle(StageStyle.UNDECORATED);
//            Scene scene = new Scene(root);
//            // Применить стиль
//            scene.getStylesheets().add(Objects.requireNonNull(MilitaryOblikKhPIMain.class.getResource(STYLES_JAVAFX)).toExternalForm());
//            stage.setScene(scene);
//
//            if (currentStage != null) {
//                currentStage.close();
//
//            }
//            currentStage = stage;
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


//    public static void openAddVNZWindow(ComboBox<VNZaklad> comboBox, ObservableList<VNZaklad> observableList) {
//        try {
//            FXMLLoader loader = new FXMLLoader(MilitaryOblikKhPIMain.class.getResource(VNZ_ADD_JAVAFX));
//            Parent root = loader.load();
//
//            AddVNZController addVNZController = loader.getController();
//            addVNZController.setVNZData(comboBox, observableList);
//
//            Stage stage = new Stage();
//            stage.setTitle(VNZ_ADD_JAVAFX_TITLE);
//            stage.setScene(new Scene(root));
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * Метод для створення Stage на основе fxml
     * автор Двухглавовб Степанов
     */
    public static Stage getStageBySettings(SettingsStage settings, Stage currentStage, Object controller, Object data) {
        Stage stage = null;

        try {
            FXMLLoader loader = new FXMLLoader(MilitaryOblikKhPIMain.class.getResource(settings.getFxmlName()));
            Scene scene;

            loader.setControllerFactory(aClass -> applicationContext.getBean(aClass));
            Parent rootNode = loader.load();

            stage = new Stage();
            stage.setTitle(settings.getTitle());
            stage.initStyle(StageStyle.UNDECORATED);

            //Щоб задати довільний розмір, можна задати параметри 0 0
            if (settings.getWidth() > 300 && settings.getHeight() > 200)
                scene = new Scene(rootNode, settings.getWidth(), settings.getHeight());
            else
                scene = new Scene(rootNode);

            // Применить стиль
            scene.getStylesheets().add(Objects.requireNonNull(MilitaryOblikKhPIMain.class.getResource(STYLES_JAVAFX)).toExternalForm());
            stage.setScene(scene);
            stage.setFullScreen(settings.isFullScreen());
            stage.setResizable(settings.isResizable());

            ControlledScene editController = loader.getController();
            editController.setMainController(controller);
            editController.setData(data);
            editController.setMainStage(currentStage);
            editController.setCurrentStage(stage);

            return stage;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stage;
    }

    /**
     * Метод для запуска форми со всеми записями
     * автор Двухглавов
     *
     * @param settings
     */
    public static Stage showStage(SettingsStage settings, Stage currentStage, Object controller, Object data) {
        Stage newStage = null;

        try {
            newStage = getStageBySettings(settings, currentStage, controller, data);

            if (currentStage != null)
                currentStage.hide();

            newStage.show();
        } catch (Exception exception) {
            System.err.println("Помилка створення форми " + settings.getFxmlName());
        }

        return newStage;
    }

    public static void showPreviousStage(Stage previousStage, Stage currentStage) {
        currentStage.close();
        previousStage.show();
    }

    public static void exitApplication() {
        applicationContext.close();
        Platform.exit();
    }

//    private static void showLogInWindow(Stage primaryStage) {
//        try {
//            FXMLLoader loader = new FXMLLoader(LoginFormController.class.getResource(LOGIN_JAVAFX));
//            loader.setControllerFactory(aClass -> applicationContext.getBean(aClass));
//            rootNode = loader.load();
//            Scene scene = new Scene(rootNode, 400, 300);
//
//            primaryStage.setScene(scene);
//            primaryStage.setTitle(LOGIN_JAVAFX_TITLE);
//            primaryStage.show();
//            primaryStage.setResizable(false);
//
//            currentStage = primaryStage;
//        } catch (IOException ex) {
//            throw new RuntimeException(ex);
//        }
//    }

    // Наличие методов
    // public static void showStage(SettingsStage settings) и
//    public static Stage getStageByFXMLName(FXMLLoader loader, String title,
//                                           int width, int height, boolean fullScreen, boolean resizable)
    // и класса настроек SettingStage позволяют запустить одной строкой форму при наличии fxml и контроллера к ней
    //

    private void showLogInWindow() {
        showStage(loginSettings, null, null, null);
    }

//    public static void showReservistsWindow() {
//        showStage(militaryOblik);
//    }
//
//    public static void showEducationWindow() {
//        showStage(educationAll);
//    }
//
//    public static void showPositionWindow() {
//        showStage(positionEdit);
//    }
//    public static void showFamilyWindow() {
//        showStage(familyAll);
//    }
//    public static void showPostgraduateEducationWindow() {
//        showStage(educationPostgraduateAll);
//    }
//
//    public static void showDocumentsWindow() {
//        showStage(documentsAll);
//    }

//    Так было в исходном варианте
//    public static void showEducationWindow() {
//        try {
//            FXMLLoader loader = new FXMLLoader(EducationAllController.class.getResource(EDUCATION_JAVAFX));
//            loader.setControllerFactory(aClass -> applicationContext.getBean(aClass));
//            rootNode = loader.load();
//            Scene scene = new Scene(rootNode);
//            scene.getStylesheets().add(Objects.requireNonNull(MilitaryOblikKhPIMain.class.getResource(STYLES_JAVAFX)).toExternalForm()); // Применить стиль
//
//            Stage primaryStage = new Stage();
//
//            primaryStage.setScene(scene);
//            primaryStage.setTitle(EDUCATION_POSTGRADUATE_JAVAFX_TITLE);
////            primaryStage.setFullScreen(true);
//            primaryStage.show();
//            currentStage = primaryStage;
////            ((Stage) educationFirstButton.getScene().getWindow()).close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    public static void showEducationPostgraduateWindow() {
//        try {
//            FXMLLoader loader = new FXMLLoader(EducationPostgraduateAllController.class.getResource(EDUCATION_POSTGRADUATE_JAVAFX));
//            loader.setControllerFactory(aClass -> applicationContext.getBean(aClass));
//            rootNode = loader.load();
//            Scene scene = new Scene(rootNode);
//            scene.getStylesheets().add(Objects.requireNonNull(MilitaryOblikKhPIMain.class.getResource(STYLES_JAVAFX)).toExternalForm()); // Применить стиль
//
//            Stage primaryStage = new Stage();
//
//            primaryStage.setScene(scene);
//            primaryStage.setTitle(EDUCATION_POSTGRADUATE_JAVAFX_TITLE);
////            primaryStage.setFullScreen(true);
//            primaryStage.show();
//            currentStage = primaryStage;
////            ((Stage) educationFirstButton.getScene().getWindow()).close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

}





