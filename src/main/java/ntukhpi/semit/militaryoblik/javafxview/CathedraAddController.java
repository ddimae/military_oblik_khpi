package ntukhpi.semit.militaryoblik.javafxview;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Fakultet;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Kafedra;
import ntukhpi.semit.militaryoblik.javafxutils.ControlledScene;
import ntukhpi.semit.militaryoblik.javafxutils.DataFormat;
import ntukhpi.semit.militaryoblik.javafxutils.Popup;
import ntukhpi.semit.militaryoblik.javafxutils.validators.TextFieldValidator;
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
     * Валідація даних вписаних у форму
     *
     * @param institute Обраний інститут, до якого відноситься нова кафедра
     * @param fullName Повна назва кафедри
     * @param abbr Аббревіатура кафедри
     * @param code Код кафедри
     * @return true - Валідація пройдена. false - Валідація не пройдена
     */
    private boolean validateCathedra(String institute, String fullName, String abbr, String code) {
        Pattern ukrWords = Pattern.compile("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії,.\\-`'_\\s]*$");
        Pattern oneWord = Pattern.compile("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії]*$");
        Pattern onlyNumber = Pattern.compile("^\\d+$");

        TextFieldValidator instituteValidator = new TextFieldValidator(-1, true, null, "Інститут", institute, null);
        TextFieldValidator fullNameValidator = new TextFieldValidator(100, true, ukrWords, "Повне ім'я", fullName, "може містити тільки українські літери та розділові знаки");
        TextFieldValidator abbrValidator = new TextFieldValidator(10, true, oneWord, "Абревіатура", abbr, "повнно містити тільки українські літери без пробілів");
        TextFieldValidator codeValidator = new TextFieldValidator(10, true, onlyNumber, "Код", code, "повинен містити тільки 1 число");

        try {
            instituteValidator.validate();
            fullNameValidator.validate();
            abbrValidator.validate();
            codeValidator.validate();

            if (kafedraService.findIDKafedraByKname(fullName) != null)
                throw new Exception("Кафедра з такою назвою вже інсує");
            if (kafedraService.findIDKafedraByKabr(abbr) != null)
                throw new Exception("Кафедра з такою абревіатуро вже інсує");
            if (kafedraService.findIDKafedraByOid(code) != null)
                throw new Exception("Кафедра з таким кодом вже інсує");
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
    public void saveNewCafedra(ActionEvent actionEvent) {
        String institute = instituteComboBox.getValue() != null ? DataFormat.getPureValue(instituteComboBox.getValue().toString()) : null;
        String fullName = fullNameTextArea.getText().trim();
        String abbr = abbreviationTextField.getText().trim();
        String code = codeTextField.getText().trim();

        if (!validateCathedra(institute, fullName, abbr, code) || !Popup.saveConfirmation())
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
