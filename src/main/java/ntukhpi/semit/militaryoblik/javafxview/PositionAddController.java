package ntukhpi.semit.militaryoblik.javafxview;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;
import ntukhpi.semit.militaryoblik.adapters.PositionAdapter;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Dolghnost;
import ntukhpi.semit.militaryoblik.javafxutils.ControlledScene;
import ntukhpi.semit.militaryoblik.javafxutils.Popup;
import ntukhpi.semit.militaryoblik.javafxutils.validators.PositionValidator;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.TextFieldValidator;
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
    PositionValidator positionValidator;

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
     * Спроба збереження даних форми в БД після валідації
     */
    @FXML
    void savePosition(ActionEvent event) {
        String fullName = fullNameTextField.getText();
        String shortName = shortNameTaxtField.getText();
        int category = nppRadioButton.isSelected() ? 1 : 2;

        try {
            positionValidator.validate(new PositionAdapter(fullName, shortName, category));
        } catch (Exception e) {
            Popup.wrongInputAlert(e.getMessage());
            return;
        }

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
