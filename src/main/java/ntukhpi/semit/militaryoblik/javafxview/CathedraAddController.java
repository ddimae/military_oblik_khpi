package ntukhpi.semit.militaryoblik.javafxview;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;
import ntukhpi.semit.militaryoblik.adapters.CathedraAdapter;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Fakultet;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Kafedra;
import ntukhpi.semit.militaryoblik.javafxutils.ControlledScene;
import ntukhpi.semit.militaryoblik.javafxutils.DataFormat;
import ntukhpi.semit.militaryoblik.javafxutils.Popup;
import ntukhpi.semit.militaryoblik.javafxutils.validators.CathedraValidator;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.TextFieldValidator;
import ntukhpi.semit.militaryoblik.service.FakultetServiceImpl;
import ntukhpi.semit.militaryoblik.service.KafedraServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.Collator;
import java.util.regex.Pattern;


/**
 * Контролер форми додавання нової кафедри
 *
 * @author Степанов Михайло
 */
@Component
public class CathedraAddController implements ControlledScene {
    @FXML
    public Button saveButton;

    @FXML
    public Button returnButton;

    @FXML
    public TextField abbreviationTextField;

    @FXML
    public TextField codeTextField;

    @FXML
    public TextArea fullNameTextArea;

    @FXML
    public ComboBox<Fakultet> instituteComboBox;

    @FXML
    public Label invalidDetailsLabel;

    private Fakultet emptyInstitute;
    private ComboBox<Kafedra> cathedraComboBox;
    private Stage mainStage;
    private Stage currentStage;

    @Autowired
    CathedraValidator cathedraValidator;

    @Autowired
    FakultetServiceImpl fakultetService;

    @Autowired
    KafedraServiceImpl kafedraService;

    @Override
    public void setMainController(Object mainController) {}

    @Override
    public void setData(Object data) {
        cathedraComboBox = (ComboBox<Kafedra>)data;
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
     * Початкова ініціалізація комбобоксів
     */
    public void initialize() {
        emptyInstitute = new Fakultet() {
            @Override
            public String toString() {
                return "Не визначено";
            }
        };
        Collator ukrCollator = DataFormat.getUkrCollator();

        instituteComboBox.getItems().add(emptyInstitute);
        instituteComboBox.getItems().
                addAll(fakultetService.getAllFak().stream().sorted((a, b) -> ukrCollator.compare(a.toString(), b.toString())).toList());
    }


    /**
     * Спроба збереження даних форми в БД після валідації
     */
    @FXML
    public void saveNewCafedra(ActionEvent actionEvent) {
        String institute = instituteComboBox.getValue() != null ? DataFormat.getPureValue(instituteComboBox.getValue().toString()) : null;
        String fullName = fullNameTextArea.getText().trim();
        String abbr = abbreviationTextField.getText().trim();
        String code = codeTextField.getText().trim();

        try {
            cathedraValidator.validate(new CathedraAdapter(institute, fullName, abbr, code));
        }  catch (Exception e) {
            Popup.wrongInputAlert(e.getMessage());
            return;
        }

        if (!Popup.saveConfirmation())
            return;

        try {
            Kafedra kafedra = new Kafedra();

            kafedra.setKname(fullName);
            kafedra.setKabr(abbr);
            kafedra.setOid(code);
            kafedra.setFakultet(instituteComboBox.getValue());

            kafedraService.createKafedra(kafedra);
            cathedraComboBox.getItems().add(kafedra);

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
    public void closeEdit(ActionEvent actionEvent) {
        MilitaryOblikKhPIMain.showPreviousStage(mainStage, currentStage);
    }

}
