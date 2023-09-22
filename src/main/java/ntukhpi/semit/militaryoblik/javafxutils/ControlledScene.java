package ntukhpi.semit.militaryoblik.javafxutils;

import javafx.stage.Stage;

public interface ControlledScene {
    void setMainController(Object mainController);
    void setData(Object data);
    void setMainStage(Stage stage);
    void setCurrentStage(Stage stage);
}

