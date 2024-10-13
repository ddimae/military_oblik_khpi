package ntukhpi.semit.militaryoblik.javafxview;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;
import ntukhpi.semit.militaryoblik.javafxutils.AllStageSettings;
import ntukhpi.semit.militaryoblik.javafxutils.ControlledScene;
import ntukhpi.semit.militaryoblik.javafxutils.SettingsStage;
import org.springframework.stereotype.Component;

import java.util.prefs.Preferences;

@Component
public class LoginFormController implements ControlledScene {
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;

    private static final String MILITARY_OBLIK_CORRECT_LOGIN = "2otdel";
    private static final String MILITARY_OBLIK_CORRECT_PASSWORD = "oblik";
    private final Preferences preferences = Preferences.userNodeForPackage(LoginFormController.class);

    private Stage mainStage;
    private Stage currentStage;

    @Override
    public void setMainController(Object controller) {}

    @Override
    public void setData(Object data) {}

    @Override
    public void setMainStage(Stage stage) {
        mainStage = stage;
    }

    @Override
    public void setCurrentStage(Stage stage) {
        currentStage = stage;
    }

    @FXML
    private void initialize() {
        loginField.setText(preferences.get("military_oblik_login", ""));
        passwordField.setText(preferences.get("military_oblik_password", ""));

        // Добавляем обработчик события нажатия клавиши Enter в текстовом поле для логина
        loginField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                logIn();
            }
        });

        // Добавляем обработчик события нажатия клавиши Enter в текстовом поле для пароля
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                logIn();
            }
        });
    }

    @FXML
    private void logIn() {
        String savedLogin = preferences.get("military_oblik_login", "");
        String savedPassword = preferences.get("military_oblik_password", "");

        if (loginField.getText().trim().equals(MILITARY_OBLIK_CORRECT_LOGIN) &&
                passwordField.getText().trim().equals(MILITARY_OBLIK_CORRECT_PASSWORD)) {

            preferences.put("military_oblik_login", MILITARY_OBLIK_CORRECT_LOGIN);
            preferences.put("military_oblik_password", MILITARY_OBLIK_CORRECT_PASSWORD);

            showReservistsForm();
        }
        else if (loginField.getText().trim().equals(savedLogin) && passwordField.getText().trim().equals(savedPassword))
            showReservistsForm();
        else
            showInvalidDataWindow();
    }

    private void showReservistsForm() {
        MilitaryOblikKhPIMain.showStage(AllStageSettings.militaryOblikSettings, currentStage, this, null);
    }

    //TODO Нужна форма JavaFX!!!! Для универсальности!!!
    private void showInvalidDataWindow() {
        Stage invalidDataStage = new Stage();
        invalidDataStage.setTitle("Помилка входу!");


        invalidDataStage.alwaysOnTopProperty();
        invalidDataStage.initModality(Modality.APPLICATION_MODAL);
        currentStage.hide();
        //invalidDataStage.initStyle(StageStyle.UNDECORATED);
        invalidDataStage.setResizable(false);

        GridPane grid = new GridPane();
        grid.setAlignment(javafx.geometry.Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label errorLabel = new Label("   Невірний логін або пароль.");
        errorLabel.setFont(new Font("System", 20));
        grid.add(errorLabel, 0, 0, 2, 1);

        Button tryAgainButton = new Button("Ввести дані заново");
        tryAgainButton.setFont(new Font("System", 18));
        grid.add(tryAgainButton, 0, 1);

        Button exitButton = new Button("Вийти");
        exitButton.setFont(new Font("System", 17));
        grid.add(exitButton, 1, 1);

        tryAgainButton.setOnAction(e -> {
            loginField.setText("");
            passwordField.setText("");
            invalidDataStage.close();
            currentStage.show();
        });

        exitButton.setOnAction(e -> {
            closeForm(null);
        });

        Scene scene = new Scene(grid, 400, 200);
        invalidDataStage.setScene(scene);

        invalidDataStage.showAndWait();
    }

    @FXML
    public void closeForm(ActionEvent actionEvent) {
        MilitaryOblikKhPIMain.exitApplication();
    }
}
