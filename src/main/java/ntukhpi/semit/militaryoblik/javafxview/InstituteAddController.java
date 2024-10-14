package ntukhpi.semit.militaryoblik.javafxview;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;
import ntukhpi.semit.militaryoblik.adapters.FakultetAdapter;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Fakultet;
import ntukhpi.semit.militaryoblik.javafxutils.ControlledScene;
import ntukhpi.semit.militaryoblik.javafxutils.Popup;
import ntukhpi.semit.militaryoblik.javafxutils.validators.InstituteValidator;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.TextFieldValidator;
import ntukhpi.semit.militaryoblik.service.FakultetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;


/**
 * Контролер форми додавання нового інституту
 *
 * @author Степанов Михайло
 */
@Component
public class InstituteAddController implements ControlledScene {
    @FXML
    private TextField abbreviationTextField;

    @FXML
    private TextField codeTextField;

    @FXML
    private TextArea fullNameTextArea;

    private ComboBox<Fakultet> instituteComboBox;
    private Stage mainStage;
    private Stage currentStage;

    @Override
    public void setMainController(Object mainController) {}

    @Override
    public void setData(Object data) {
        instituteComboBox = (ComboBox<Fakultet>)data;
    }

    @Override
    public void setMainStage(Stage stage) {
        mainStage = stage;
    }

    @Override
    public void setCurrentStage(Stage stage) {
        currentStage = stage;
    }

    @Autowired
    InstituteValidator instituteValidator;

    @Autowired
    FakultetServiceImpl fakultetService;


    /**
     * Спроба збереження даних форми в БД після валідації
     */
    @FXML
    void saveNewInstitute(ActionEvent event) {
        String fullName = fullNameTextArea.getText().trim();
        String abbr = abbreviationTextField.getText().trim();
        String code = codeTextField.getText().trim();

        try {
            instituteValidator.validate(new FakultetAdapter(null, fullName, abbr, code));
        } catch (Exception e) {
            Popup.wrongInputAlert(e.getMessage());
            return;
        }

        if (!Popup.saveConfirmation())
            return;

        try {
            Fakultet fakultet = new Fakultet();

            fakultet.setFname(fullName);
            fakultet.setAbr(abbr);
            fakultet.setOid(code);

            fakultetService.createFakultet(fakultet);
            instituteComboBox.getItems().add(fakultet);

            closeEdit(null);
            Popup.successSave();
        } catch (Exception e) {
            e.printStackTrace();
            Popup.internalAlert(e.getMessage());
        }
    }


    /**
     * Перехід до материнської форми
     */
    @FXML
    void closeEdit(ActionEvent event) {
        MilitaryOblikKhPIMain.showPreviousStage(mainStage, currentStage);
    }
}
