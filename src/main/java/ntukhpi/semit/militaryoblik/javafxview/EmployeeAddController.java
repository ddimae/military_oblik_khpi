package ntukhpi.semit.militaryoblik.javafxview;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;
import ntukhpi.semit.militaryoblik.entity.VNZaklad;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.*;
import ntukhpi.semit.militaryoblik.javafxutils.ControlledScene;
import ntukhpi.semit.militaryoblik.javafxutils.DataFormat;
import ntukhpi.semit.militaryoblik.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void setMainController(Object mainController) {

    }

    @Override
    public void setData(Object data) {

    }

    public void initialize() {
        List<String> institutesList = new ArrayList<>();
        List<String> cathedrasList = new ArrayList<>();
        List<String> degreesList = new ArrayList<>();
        List<String> statusList = new ArrayList<>();

        institutesList.add("Не визначено");
        cathedrasList.add("Не визначено");
//        degreesList.add("Не визначено");
//        statusList.add("Не визначено");

        institutesList.addAll(fakultetService.getAllFak().stream().map(Fakultet::getFname).sorted().toList());
        cathedrasList.addAll(kafedraService.getAllKafedra().stream().map(Kafedra::getKname).sorted().toList());
        degreesList.addAll(stepenService.getAllStepen().stream().map(Stepen::getStepenLong).sorted().toList());
        statusList.addAll(zvanieService.getAllZvanie().stream().map(Zvanie::getZvanieName).sorted().toList());

        degreesList.remove("не має");
        degreesList.add(0, "не має");
        statusList.remove("не визначено");
        statusList.add(0, "не визначено");

        instituteComboBox.setItems(FXCollections.observableArrayList(institutesList));
        cathedraComboBox.setItems(FXCollections.observableArrayList(cathedrasList));
        degreeComboBox.setItems(FXCollections.observableArrayList(degreesList));
        statusComboBox.setItems(FXCollections.observableArrayList(statusList));

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

    private boolean validateInfo() {
        return true;
    }

    @FXML
    void saveEmployee(ActionEvent event) {

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
}
