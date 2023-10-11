package ntukhpi.semit.militaryoblik.javafxview;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Fakultet;
import ntukhpi.semit.militaryoblik.javafxutils.ControlledScene;
import ntukhpi.semit.militaryoblik.javafxutils.Popup;
import ntukhpi.semit.militaryoblik.javafxutils.validators.TextFieldValidator;
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
    FakultetServiceImpl fakultetService;


    /**
     * Валідація даних вписаних у форму
     *
     * @param fullName Повна назва інституту
     * @param abbr Аббревіатура інституту
     * @param code Код інституту
     * @return true - Валідація пройдена. false - Валідація не пройдена
     */
    private boolean validateInstitute(String fullName, String abbr, String code) {
        Pattern ukrWords = Pattern.compile("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії,.\\-`'_\\s]*$");
        Pattern oneWord = Pattern.compile("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії]*$");
        Pattern onlyNumber = Pattern.compile("^\\d+$");

        TextFieldValidator fullNameValidator = new TextFieldValidator(100, true, ukrWords, "Повна назва", fullName, "може містити тільки українські літери та розділові знаки");
        TextFieldValidator abbrValidator = new TextFieldValidator(10, true, oneWord, "Абревіатура", abbr, "повнно містити тільки українські літери без пробілів");
        TextFieldValidator codeValidator = new TextFieldValidator(10, true, onlyNumber, "Код", code, "повинен містити тільки 1 число");

        try {
            fullNameValidator.validate();
            abbrValidator.validate();
            codeValidator.validate();

            if (fakultetService.findIDFakultetByFname(fullName) != null)
                throw new Exception("Інститут з такою назвою вже існує");
            if (fakultetService.findIDFakultetByAbr(abbr) != null)
                throw new Exception("Інститут з такою абревіатурою вже існує");
            if (fakultetService.findIDFakultetByOid(code) != null)
                throw new Exception("Інститут з таким кодом вже існує");
        } catch (Exception e) {
            Popup.wrongInputAlert(e.getMessage());
            return false;
        }
        return true;
    }


    /**
     * Спроба збереження даних форми в БД після валідації
     */
    @FXML
    void saveNewInstitute(ActionEvent event) {
        String fullName = fullNameTextArea.getText().trim();
        String abbr = abbreviationTextField.getText().trim();
        String code = codeTextField.getText().trim();

        if (!validateInstitute(fullName, abbr, code) || !Popup.saveConfirmation())
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
