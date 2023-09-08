package ntukhpi.semit.militaryoblik.javafxview;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import ntukhpi.semit.militaryoblik.adapters.EducationAdapter;
import ntukhpi.semit.militaryoblik.adapters.ReservistAdapter;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.javafxutils.ControlledScene;
import ntukhpi.semit.militaryoblik.javafxutils.DataFormat;
import ntukhpi.semit.militaryoblik.javafxutils.Popup;
import ntukhpi.semit.militaryoblik.service.EducationServiceImpl;
import ntukhpi.semit.militaryoblik.service.PrepodServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;

import java.util.ArrayList;
import java.util.List;

@Component
public class EducationAllController {
    private final static String EDUCATION_EDIT_JAVAFX = "/javafxview/EducationEdit.fxml";

    @FXML
    public Text pibText;
    @FXML
    public TableView<EducationAdapter> educationTableView;
    @FXML
    private TableColumn<EducationAdapter, String> vnzColumn;
    @FXML
    private TableColumn<EducationAdapter, String> yearColumn;
    @FXML
    private TableColumn<EducationAdapter, String> formColumn;
    @FXML
    private TableColumn<EducationAdapter, String> specialtyColumn;
    @FXML
    private TableColumn<EducationAdapter, String> qualificationColumn;

    private ObservableList<EducationAdapter> educationObservableList;

    private Prepod selectedPrepod;

    @Autowired
    EducationServiceImpl educationService;

    @Autowired
    PrepodServiceImpl prepodService;

    private ObservableList<EducationAdapter> getEducationData() {
        return FXCollections.observableArrayList(educationService.getAllEducationByPrepod(selectedPrepod).stream().map(EducationAdapter::new).toList());
    }

    public void initialize() {
        selectedPrepod = prepodService.getPrepodById(ReservistsAllController.getSelectedPrepodId());

        pibText.setText(DataFormat.getPIB(selectedPrepod));

        vnzColumn.setCellValueFactory(new PropertyValueFactory<>("vnz"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        formColumn.setCellValueFactory(new PropertyValueFactory<>("form"));
        specialtyColumn.setCellValueFactory(new PropertyValueFactory<>("speciality"));
        qualificationColumn.setCellValueFactory(new PropertyValueFactory<>("qualification"));

        educationObservableList = getEducationData();

        updateTable(educationObservableList);
    }

    private void updateTable(ObservableList<EducationAdapter> educationObservableList) {
        educationTableView.setItems(educationObservableList);
    }

    @FXML
    private void openAddWindow(ActionEvent event) {
        MilitaryOblikKhPIMain.openEditWindow(EDUCATION_EDIT_JAVAFX, "Додати дані про навчання", this, null);
    }

    @FXML
    private void openEditWindow(ActionEvent event) {
        EducationAdapter selectedEducation = educationTableView.getSelectionModel().getSelectedItem();
        if (selectedEducation != null) {
            MilitaryOblikKhPIMain.openEditWindow(EDUCATION_EDIT_JAVAFX, "Редагувати дані про навчання", this, selectedEducation);
        } else {
            Popup.noSelectedRowAlert();
        }
    }

    @FXML
    private void returnToMainForm(ActionEvent event) {
        MilitaryOblikKhPIMain.showReservistsWindow();
    }

    @FXML
    private void deleteSelectedRow(ActionEvent event) {
        EducationAdapter selectedEducation = educationTableView.getSelectionModel().getSelectedItem();

        if (selectedEducation != null) {
            educationObservableList.remove(selectedEducation);
//            educationService.deleteEducation(selectedEducation.getVnz());
        } else {
            Popup.noSelectedRowAlert();
        }
    }

    public void addEducation(EducationAdapter newEducation) {
        educationObservableList.add(newEducation);
        updateTable(educationObservableList);
    }

    public void updateEducation(EducationAdapter oldEducation, EducationAdapter newEducation) {
        educationObservableList.remove(oldEducation);
        educationObservableList.add(newEducation);
        educationTableView.refresh();
    }
}
