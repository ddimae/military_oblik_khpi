package ntukhpi.semit.militaryoblik.javafxview;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;
import org.springframework.stereotype.Component;

@Component
public class LoginFormController {

    public static Stage loginStage;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;

    private static final String CORRECT_LOGIN = "2otdel";
    private static final String CORRECT_PASSWORD = "oblik";

    @FXML
    private void logIn() {
        if (loginField.getText().equals(CORRECT_LOGIN) && passwordField.getText().equals(CORRECT_PASSWORD))
            showReservistsForm();
        else
            showInvalidDataWindow();
    }

    private static void showReservistsForm() {
        loginStage.close();
        MilitaryOblikKhPIMain.showReservistsWindow();
    }

    private void showInvalidDataWindow() {
        Stage invalidDataStage = new Stage();
        invalidDataStage.setTitle("Помилка входу!");


        invalidDataStage.alwaysOnTopProperty();
        invalidDataStage.initModality(Modality.APPLICATION_MODAL);
        loginStage.hide();
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
            loginStage.show();
        });

        exitButton.setOnAction(e -> {
            invalidDataStage.close();
            Platform.exit();
        });

        Scene scene = new Scene(grid, 400, 200);
        invalidDataStage.setScene(scene);

        invalidDataStage.showAndWait();
    }
}
