package ntukhpi.semit.militaryoblik.javafxview;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;
import ntukhpi.semit.militaryoblik.adapters.EducationAdapter;
import ntukhpi.semit.militaryoblik.adapters.EducationPostgraduateAdapter;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.javafxutils.Popup;
import ntukhpi.semit.militaryoblik.service.EducationPostgraduateServiceImpl;
import ntukhpi.semit.militaryoblik.service.EducationServiceImpl;
import ntukhpi.semit.militaryoblik.service.PrepodServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EducationPostgraduateAllController {
    private final static String EDUCATION_POSTGRADUATE_EDIT_JAVAFX = "/javafxview/EducationPostgraduateEdit.fxml";

    @FXML
    public Label pibLabel;
    @FXML
    public TableView<EducationPostgraduateAdapter> postgraduateEducationTableView;
    @FXML
    public TableColumn<EducationPostgraduateAdapter, String> typeColumn;
    @FXML
    public TableColumn<EducationPostgraduateAdapter, String> vnzColumn;
    @FXML
    public TableColumn<EducationPostgraduateAdapter, String> yearColumn;

    private ObservableList<EducationPostgraduateAdapter> postgraduateEducationObservableList;

    @FXML
    private void returnToMainForm(ActionEvent event) {
        MilitaryOblikKhPIMain.showReservistsWindow();
    }

    private Prepod selectedPrepod;

    @Autowired
    EducationPostgraduateServiceImpl educationPostgraduateService;
    @Autowired
    PrepodServiceImpl prepodService;

    @FXML
    private void openAddWindow(ActionEvent event) {
        MilitaryOblikKhPIMain.openEditWindow(EDUCATION_POSTGRADUATE_EDIT_JAVAFX, "Додати дані про післядипломне навчання", this, null);
    }

    @FXML
    private void openEditWindow(ActionEvent event) {
        EducationPostgraduateAdapter selectedEducation = postgraduateEducationTableView.getSelectionModel().getSelectedItem();
        if (selectedEducation != null) {
            MilitaryOblikKhPIMain.openEditWindow(EDUCATION_POSTGRADUATE_EDIT_JAVAFX, "Редагувати дані про навчання", this, selectedEducation);
        } else {
            Popup.noSelectedRowAlert();
        }
    }

    @FXML
    private void deleteSelectedRow(ActionEvent event) {
        EducationPostgraduateAdapter selectedEducation = postgraduateEducationTableView.getSelectionModel().getSelectedItem();

        if (selectedEducation != null) {
            postgraduateEducationObservableList.remove(selectedEducation);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No row selected");
            alert.setContentText("Please select a row to edit.");
            alert.showAndWait();
        }
    }

    public void addPostgraduateEducation(EducationPostgraduateAdapter newEducation) {
        postgraduateEducationObservableList.add(newEducation);
        updateTable(postgraduateEducationObservableList);
    }

    public void updatePostgraduateEducation(EducationPostgraduateAdapter oldEducation, EducationPostgraduateAdapter newEducation) {
        postgraduateEducationObservableList.remove(oldEducation);
        postgraduateEducationObservableList.add(newEducation);
        postgraduateEducationTableView.refresh();
    }

    private ObservableList<EducationPostgraduateAdapter> getEducationPostgraduateData() {
        return FXCollections.observableArrayList(educationPostgraduateService.getAllEducationPostgraduateByPrepod(selectedPrepod).stream().map(EducationPostgraduateAdapter::new).toList());
    }

    public void initialize() {
        selectedPrepod = prepodService.getPrepodById(ReservistsAllController.getSelectedPrepodId());

        pibLabel.setText(MilitaryOblikKhPIMain.getPIB(selectedPrepod));

        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        vnzColumn.setCellValueFactory(new PropertyValueFactory<>("vnz"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));

        postgraduateEducationObservableList = getEducationPostgraduateData();

        updateTable(postgraduateEducationObservableList);
    }

    private void updateTable(ObservableList<EducationPostgraduateAdapter> postgraduateEducationObservableList) {
        postgraduateEducationTableView.setItems(postgraduateEducationObservableList);
    }
}
