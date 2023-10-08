package ntukhpi.semit.militaryoblik.javafxview;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.*;
import ntukhpi.semit.militaryoblik.javafxutils.AllStageSettings;
import ntukhpi.semit.militaryoblik.javafxutils.ControlledScene;
import ntukhpi.semit.militaryoblik.javafxutils.DataFormat;
import ntukhpi.semit.militaryoblik.javafxutils.validators.DateFieldValidator;
import ntukhpi.semit.militaryoblik.javafxutils.validators.TextFieldValidator;
import ntukhpi.semit.militaryoblik.javafxutils.Popup;
import ntukhpi.semit.militaryoblik.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.Collator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;


/**
 * Контролер форми додавання нового співробітника
 *
 * @author Степанов Михайло
 */
@Component
public class EmployeeAddController implements ControlledScene {

    @FXML
    private DatePicker birthDatePicker;

    @FXML
    private ComboBox<Kafedra> cathedraComboBox;

    @FXML
    private ComboBox<Stepen> degreeComboBox;

    @FXML
    private ComboBox<Fakultet> instituteComboBox;

    @FXML
    private RadioButton itcRadioButton;

    @FXML
    private TextField midnameTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private RadioButton nppRadioButton;

    @FXML
    private ComboBox<Dolghnost> positionComboBox;

    @FXML
    private ComboBox<Zvanie> statusComboBox;

    @FXML
    private TextField surnameTextField;

    @Autowired
    FakultetServiceImpl fakultetService;

    @Autowired
    KafedraServiceImpl kafedraService;

    @Autowired
    DolghnostServiceImpl dolghnostService;

    @Autowired
    StepenServiceImpl stepenService;

    @Autowired
    ZvanieServiceImpl zvanieService;

    @Autowired
    PrepodServiceImpl prepodService;

    private Fakultet emptyInstitute;
    private Kafedra emptyCathedra;
    private Collator ukrCollator;
    private boolean isChangeCombobox;
    private ReservistsAllController mainController;
    private Stage mainStage;
    private Stage currentStage;

    @Override
    public void setMainController(Object mainController) {
        this.mainController = (ReservistsAllController) mainController;
    }

    @Override
    public void setData(Object data) {

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
        emptyCathedra = new Kafedra() {
            @Override
            public String toString() {
                return "Не визначено";
            }
        };
        ukrCollator = DataFormat.getUkrCollator();
        isChangeCombobox = false;

        instituteComboBox.getItems().add(emptyInstitute);
        instituteComboBox.getItems().addAll(FXCollections.observableArrayList(fakultetService.getAllFak().stream().sorted((a, b) -> ukrCollator.compare(a.toString(), b.toString())).toList()));

        degreeComboBox.setItems(FXCollections.observableArrayList(stepenService.getAllStepen().stream().sorted((a, b) -> ukrCollator.compare(a.toString(), b.toString())).toList()));
        statusComboBox.setItems(FXCollections.observableArrayList(zvanieService.getAllZvanie().stream().sorted((a, b) -> ukrCollator.compare(a.toString(), b.toString())).toList()));

        degreeComboBox.getItems().remove(stepenService.getStepenById(0L));
        degreeComboBox.getItems().add(0,stepenService.getStepenById(0L));
        statusComboBox.getItems().remove(zvanieService.getZvanieById(0L));
        statusComboBox.getItems().add(0,zvanieService.getZvanieById(0L));

        handleInstituteChange(null);
        handleTypeChange(null);
    }

    /**
     * Валідація даних вписаних у форму
     *
     * @param institute Обриний інститут
     * @param cathedra Обрана кафедра
     * @param surname Прізвище нового співробітника
     * @param name Ім'я нового співробітника
     * @param midname По-батькові нового співробітника
     * @param date Дата народження нового співробітника
     * @return true - Валідація пройдена. false - Валідація не пройдена
     */
    private boolean validateInfo(String institute, String cathedra, String surname,
                                 String name, String midname, String date) {
        Pattern ukrWords = Pattern.compile("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії\\-\\s]+$");
        Pattern ukrDateRegex = Pattern.compile("^\\d{2}\\.\\d{2}\\.\\d{4}$");

        TextFieldValidator instituteValidator = new TextFieldValidator(-1, true, null, "Інститут", institute, null);
        TextFieldValidator cathedraValidator = new TextFieldValidator(-1, true, null, "Кафедра", cathedra, null);
        TextFieldValidator surnameValidator = new TextFieldValidator(40, true, ukrWords, "Прізвище", surname, "повинно містити українські літери");
        TextFieldValidator nameValidator = new TextFieldValidator(30, true, ukrWords, "Ім'я", name, "повинно містити українські літери");
        TextFieldValidator midnameValidator = new TextFieldValidator(30, true, ukrWords, "По батькові", midname, "повинно містити українські літери");
        DateFieldValidator dateValidator = new DateFieldValidator(false, ukrDateRegex, "Дата народження", date, "повинно мати формат дати: dd.mm.yyyy");

        try {
            instituteValidator.validate();
            cathedraValidator.validate();
            surnameValidator.validate();
            nameValidator.validate();
            midnameValidator.validate();
            dateValidator.validate();
        } catch (Exception e) {
            Popup.wrongInputAlert(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Спроба збереження/редагування даних форми в БД після валідації
     */
    @FXML
    void saveEmployee(ActionEvent event) {
        String institute = instituteComboBox.getValue() != null ? DataFormat.getPureValue(instituteComboBox.getValue().toString()) : null;
        String cathedra = cathedraComboBox.getValue() != null ? DataFormat.getPureValue(cathedraComboBox.getValue().toString()) : null;
        String surname = surnameTextField.getText().trim();
        String name = nameTextField.getText().trim();
        String midname = midnameTextField.getText().trim();
        String birthDate = birthDatePicker.getEditor().getText();
        String position = positionComboBox.getValue() != null ? positionComboBox.getValue().toString() : null;
        String degree = degreeComboBox.getValue() != null ? degreeComboBox.getValue().toString() : null;
        String status = statusComboBox.getValue() != null ? statusComboBox.getValue().toString() : null;

        if (!validateInfo(institute, cathedra, surname, name, midname, birthDate) || !Popup.saveConfirmation())
            return;

        try {
            Prepod prepod = new Prepod();

            prepod.setFam(surname);
            prepod.setImya(name);
            prepod.setOtch(midname);
            if (!birthDate.isBlank())
                prepod.setDr(LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            prepod.setKafedra(cathedraComboBox.getValue());

            if (position == null)
                positionComboBox.getSelectionModel().select(dolghnostService.getDolghnostByCategory(0));
            if (degree == null)
                degreeComboBox.getSelectionModel().select(stepenService.getStepenById(0L));
            if (status == null)
                statusComboBox.getSelectionModel().select(zvanieService.getZvanieById(0L));

            prepod.setDolghnost(positionComboBox.getValue());
            prepod.setStepen(degreeComboBox.getValue());
            prepod.setZvanie(statusComboBox.getValue());

            prepodService.savePrepod(prepod);

            closeEdit(null);
            Popup.successSave();
        } catch (Exception e) {
            e.printStackTrace();
            Popup.internalAlert(e.getMessage());
        }
    }

    /**
     * Обробник зміни стану кнопок вибору типу працівника
     * (Науково-педагогічний працівник / Інженерно-технічний працівник)
     */
    @FXML
    void handleTypeChange(ActionEvent event) {
        int filterId = nppRadioButton.isSelected() ? 1 : 2;
        boolean isFirst = positionComboBox.getValue() != null;

        positionComboBox.setItems(FXCollections.observableArrayList(dolghnostService.getAllDolghnost().stream().filter(p -> filterId == p.getCategoryEmployees() || p.getCategoryEmployees() == 0).toList()));
        if (isFirst) {
            positionComboBox.getSelectionModel().selectFirst();
            degreeComboBox.getSelectionModel().selectFirst();
            statusComboBox.getSelectionModel().selectFirst();
        }

        if (filterId == 2) {
            degreeComboBox.setDisable(true);
            statusComboBox.setDisable(true);
        } else {
            degreeComboBox.setDisable(false);
            statusComboBox.setDisable(false);
        }
    }

    /**
     * Обробник зміни значення комбобоксу інституту
     */
    @FXML
    void handleInstituteChange(ActionEvent event) {
        if (isChangeCombobox)
            return;
        isChangeCombobox = true;

        boolean isFirst = cathedraComboBox.getValue() != null;

        cathedraComboBox.getItems().clear();
        cathedraComboBox.getItems().add(emptyCathedra);
        cathedraComboBox.getItems().addAll(FXCollections.observableArrayList(kafedraService.getAllKafedra().
                                                                                            stream().
                                                                                            filter(k -> instituteComboBox.getValue() == null || DataFormat.getPureValue(instituteComboBox.getValue().toString()) == null || k.getFakultet().toString().equals(instituteComboBox.getValue().toString())).
                                                                                            sorted((a, b) -> ukrCollator.compare(a.toString(), b.toString())).
                                                                                            toList()));
        if (isFirst)
            cathedraComboBox.getSelectionModel().selectFirst();

        isChangeCombobox = false;
    }

    /**
     * Обробник зміни значення комбобоксу кафедри
     */
    @FXML
    void handleCathedraChange(ActionEvent event) {
        if (isChangeCombobox || cathedraComboBox.getValue().getFakultet() == null)
            return;
        isChangeCombobox = true;

        instituteComboBox.getSelectionModel().select(cathedraComboBox.getValue().getFakultet());
        Kafedra currentCathedra = cathedraComboBox.getValue();

        isChangeCombobox = false;
        handleInstituteChange(null);
        isChangeCombobox = true;

        cathedraComboBox.getSelectionModel().select(currentCathedra);

        isChangeCombobox = false;
    }

    /**
     * Перехід до форми додавання нового інституту
     */
    @FXML
    void handleInstituteButton(ActionEvent event) {
        MilitaryOblikKhPIMain.showStage(AllStageSettings.instituteAdd, currentStage, this, instituteComboBox);
    }

    /**
     * Перехід до форми додавання нової кафедри
     */
    @FXML
    void handleCathedraButton(ActionEvent event) {
        MilitaryOblikKhPIMain.showStage(AllStageSettings.cathedraAdd, currentStage, this, cathedraComboBox);
    }

    /**
     * Перехід до форми додавання нової посади
     */
    @FXML
    void handlePositionButton(ActionEvent event) {
        MilitaryOblikKhPIMain.showStage(AllStageSettings.positionAdd, currentStage, this, positionComboBox);
    }

    /**
     * Перехід до материнської форми
     */
    @FXML
    void closeEdit(ActionEvent event) {
        MilitaryOblikKhPIMain.showPreviousStage(mainStage, currentStage);
    }
}
