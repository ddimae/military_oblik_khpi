package ntukhpi.semit.militaryoblik.javafxview;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Dolghnost;
import ntukhpi.semit.militaryoblik.javafxutils.ControlledScene;
import ntukhpi.semit.militaryoblik.javafxutils.Popup;
import ntukhpi.semit.militaryoblik.javafxutils.validators.TextFieldValidator;
import ntukhpi.semit.militaryoblik.service.DolghnostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;


/**
 * Контролер форми додавання нової посади
 *
 * @author Степанов Михайло
 */
@Component
public class PositionAddController implements ControlledScene {

    @FXML
    private ToggleGroup employeeType;

    @FXML
    private TextField fullNameTextField;

    @FXML
    private RadioButton itcRadioButton;

    @FXML
    private RadioButton nppRadioButton;

    @FXML
    private TextField shortNameTaxtField;

    private Stage mainStage;
    private Stage currentStage;
    private ComboBox<Dolghnost> positionComboBox;

    @Autowired
    DolghnostServiceImpl dolghnostService;

    @Override
    public void setMainController(Object controller) {}

    @Override
    public void setData(Object data) {
        positionComboBox = (ComboBox<Dolghnost>)data;
    }

    @Override
    public void setMainStage(Stage stage) {
        mainStage = stage;
    }

    @Override
    public void setCurrentStage(Stage stage) {
        currentStage = stage;
    }

    /**
     * Валідація даних вписаних у форму
     *
     * @param fullName Повна назва посади
     * @param shortName Скорочена назва посади
     * @return true - Валідація пройдена. false - Валідація не пройдена
     */
    private boolean validatePosition(String fullName, String shortName) {
        Pattern ukrWords = Pattern.compile("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії,.\\-`'_\\s0-9]*$");

        TextFieldValidator fullNameValidator = new TextFieldValidator(40, true, ukrWords, "Назва посади", fullName, "може містити тільки українські літери та розділові знаки");
        TextFieldValidator shortNameValidator = new TextFieldValidator(15, false, ukrWords, "Скорочене позначення", shortName, "може містити тільки українські літери та розділові знаки");

        try {
            fullNameValidator.validate();
            shortNameValidator.validate();

            if (dolghnostService.findIDPosadaByName(fullName) != null)
                throw new Exception("Посада з такою назвою вже існує");
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
    void savePosition(ActionEvent event) {
        String fullName = fullNameTextField.getText();
        String shortName = shortNameTaxtField.getText();
        int category = nppRadioButton.isSelected() ? 1 : 2;

        if (!validatePosition(fullName, shortName) || !Popup.saveConfirmation())
            return;

        try {
            Dolghnost dolghnost = new Dolghnost();

            dolghnost.setDolghnName(fullName);
            dolghnost.setDolghnShortName(shortName);
            dolghnost.setCategoryEmployees(category);

            if (positionComboBox.getItems().get(positionComboBox.getItems().size() - 1).getCategoryEmployees() == category)
                positionComboBox.getItems().add(dolghnost);
            dolghnostService.createDolghnost(dolghnost);

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
