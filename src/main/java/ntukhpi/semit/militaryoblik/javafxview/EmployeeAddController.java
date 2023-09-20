package ntukhpi.semit.militaryoblik.javafxview;

import com.sun.tools.jconsole.JConsoleContext;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.*;
import ntukhpi.semit.militaryoblik.javafxutils.ControlledScene;
import ntukhpi.semit.militaryoblik.javafxutils.DataFormat;
import ntukhpi.semit.militaryoblik.javafxutils.validators.DateFieldValidator;
import ntukhpi.semit.militaryoblik.javafxutils.validators.TextFieldValidator;
import ntukhpi.semit.militaryoblik.javafxutils.Popup;
import ntukhpi.semit.militaryoblik.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class EmployeeAddController implements ControlledScene {

    @FXML
    private DatePicker birthDatePicker;

    @FXML
    private ComboBox<String> cathedraComboBox;

    @FXML
    private ComboBox<String> degreeComboBox;

    @FXML
    private ComboBox<String> instituteComboBox;

    @FXML
    private RadioButton itcRadioButton;

    @FXML
    private TextField midnameTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private RadioButton nppRadioButton;

    @FXML
    private ComboBox<String> positionComboBox;

    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    private TextField surnameTextField;

    private ReservistsAllController mainController;

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

    @Override
    public void setMainController(Object mainController) {
        this.mainController = (ReservistsAllController) mainController;
    }

    @Override
    public void setData(Object data) {

    }

    public void initialize() {
        Collator ukrCollator = DataFormat.getUkrCollator();

        instituteComboBox.getItems().add("Не визначено");
        instituteComboBox.getItems().addAll(FXCollections.observableArrayList(fakultetService.getAllFak().stream().map(Fakultet::toString).sorted(ukrCollator).toList()));

        degreeComboBox.setItems(FXCollections.observableArrayList(stepenService.getAllStepen().stream().map(Stepen::toString).sorted(ukrCollator).toList()));
        statusComboBox.setItems(FXCollections.observableArrayList(zvanieService.getAllZvanie().stream().map(Zvanie::toString).sorted(ukrCollator).toList()));

        degreeComboBox.getItems().remove("не має");
        degreeComboBox.getItems().add(0,"не має");
        statusComboBox.getItems().remove("не визначено");
        statusComboBox.getItems().add(0,"не визначено");

        handleInstituteChange(null);
        handleTypeChange(null);
    }

    @FXML
    void closeEdit(ActionEvent event) {
        try {
            ((Stage) cathedraComboBox.getScene().getWindow()).close();
            MilitaryOblikKhPIMain.showReservistsWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    @FXML
    void saveEmployee(ActionEvent event) {
        String institute = DataFormat.getPureComboBoxValue(instituteComboBox);
        String cathedra = DataFormat.getPureComboBoxValue(cathedraComboBox);
        String surname = surnameTextField.getText().trim();
        String name = nameTextField.getText().trim();
        String midname = midnameTextField.getText().trim();
        String birthDate = birthDatePicker.getEditor().getText();
        String position = positionComboBox.getValue();
        String degree = degreeComboBox.getValue();
        String status = statusComboBox.getValue();

        if (!validateInfo(institute, cathedra, surname, name, midname, birthDate) || !Popup.saveConfirmation())
            return;

        try {
            Prepod prepod = new Prepod();

            prepod.setFam(surname);
            prepod.setImya(name);
            prepod.setOtch(midname);
            prepod.setDr(birthDatePicker.getValue());
            prepod.setKafedra(kafedraService.getKafedraByName(cathedra));

            if (position == null)
                position = positionComboBox.getItems().get(0);
            if (degree == null)
                degree = degreeComboBox.getItems().get(0);
            if (status == null)
                status = statusComboBox.getItems().get(0);

            prepod.setDolghnost(dolghnostService.getDolghnostByName(position));
            prepod.setStepen(stepenService.getStepenByName(degree));
            prepod.setZvanie(zvanieService.getZvanieByName(status));

            prepodService.savePrepod(prepod);

            closeEdit(null);
            Popup.successSave();
        } catch (Exception e) {
            e.printStackTrace();
            Popup.internalAlert(e.getMessage());
        }
    }

    @FXML
    void handleTypeChange(ActionEvent event) {
        int filterId = nppRadioButton.isSelected() ? 1 : 2;
        boolean isFirst = positionComboBox.getValue() != null;

        List<String> positionsList = dolghnostService.getAllDolghnost().stream().filter(p -> filterId == p.getCategoryEmployees() || p.getCategoryEmployees() == 0).map(Dolghnost::toString).toList();

        positionComboBox.setItems(FXCollections.observableArrayList(positionsList));
        if (isFirst)
            positionComboBox.getSelectionModel().selectFirst();

        if (filterId == 2) {
            degreeComboBox.setDisable(true);
            statusComboBox.setDisable(true);
        } else {
            degreeComboBox.setDisable(false);
            statusComboBox.setDisable(false);
        }
    }

    @FXML
    void handleInstituteChange(ActionEvent event) {
        boolean isFirst = cathedraComboBox.getValue() != null;

        cathedraComboBox.getItems().clear();
        cathedraComboBox.getItems().add(0, "Не визначено");
        cathedraComboBox.getItems().addAll(FXCollections.observableArrayList(kafedraService.getAllKafedra().
                                                                                            stream().
                                                                                            filter(k -> k.getFakultet().toString().equals(instituteComboBox.getValue())).
                                                                                            map(Kafedra::getKname).
                                                                                            sorted().
                                                                                            toList()));
        if (isFirst)
            cathedraComboBox.getSelectionModel().selectFirst();
    }
}