package ntukhpi.semit.militaryoblik;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ntukhpi.semit.militaryoblik.javafxview.LoginFormController;
import ntukhpi.semit.militaryoblik.javafxview.ReservistsAllController;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.util.Objects;

public class MilitaryOblikKhPIMain extends Application {
    private final static String LOGIN_JAVAFX ="/javafxview/LoginForm.fxml";
    private final static String MILITARY_OBLIK_LIST_JAVAFX = "/javafxview/ReservistsAll.fxml";

    static ConfigurableApplicationContext applicationContext; //was private
    static Parent rootNode; //was private

    @Override
    public void init() {
            SpringApplicationBuilder builder = new SpringApplicationBuilder(MilitaryAccounting.class);
            applicationContext = builder.run();
    }

    @Override
    public void start(Stage primaryStage) {
        showLogInWindow(primaryStage);

    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }

    private static void showLogInWindow(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(LoginFormController.class.getResource(LOGIN_JAVAFX));
            loader.setControllerFactory(aClass -> applicationContext.getBean(aClass));
            rootNode = loader.load();
            Scene scene = new Scene(rootNode, 400, 300);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Вхід в застосунок");
            primaryStage.show();
            primaryStage.setResizable(false);

            LoginFormController.loginStage = primaryStage;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void showReservistsWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(ReservistsAllController.class.getResource(MILITARY_OBLIK_LIST_JAVAFX));
            loader.setControllerFactory(aClass -> applicationContext.getBean(aClass));
            rootNode = loader.load();
            Scene scene = new Scene(rootNode, 1200, 800);
            scene.getStylesheets().add(Objects.requireNonNull(MilitaryOblikKhPIMain.class.getResource("/javafxview/MOStyles.css")).toExternalForm()); // Применить стиль

            Stage primaryStage = new Stage();

            primaryStage.setScene(scene);
            primaryStage.setTitle("MilitaryOblik");
            primaryStage.setFullScreen(true);
            primaryStage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


}

