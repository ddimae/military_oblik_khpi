package ntukhpi.semit.militaryoblik;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ntukhpi.semit.militaryoblik.javafxutils.AllStageSettings;
import ntukhpi.semit.militaryoblik.javafxutils.ControlledScene;
import ntukhpi.semit.militaryoblik.javafxutils.SettingsStage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.util.*;

public class MilitaryOblikKhPIMain extends Application {
    public static ConfigurableApplicationContext applicationContext; //was private

    @Override
    public void init() {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(MilitaryAccounting.class);
        applicationContext = builder.run();
    }

    @Override
    public void start(Stage primaryStage) {
        showLogInWindow();
    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }

    /**
     * Метод для створення Stage на основе fxml
     * автор Двухглавов, Степанов
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
            scene.getStylesheets().add(Objects.requireNonNull(MilitaryOblikKhPIMain.class.getResource(AllStageSettings.STYLES_JAVAFX)).toExternalForm());
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

            newStage.show();

            if (currentStage != null)
                currentStage.hide();
        } catch (Exception exception) {
            System.err.println("Помилка створення форми " + settings.getFxmlName());
        }

        return newStage;
    }

    public static void showPreviousStage(Stage previousStage, Stage currentStage) {
        previousStage.show();
        currentStage.close();
    }

    public static void exitApplication() {
        applicationContext.close();
        Platform.exit();
    }

    private void showLogInWindow() {
        showStage(AllStageSettings.loginSettings, null, null, null);
    }
}





