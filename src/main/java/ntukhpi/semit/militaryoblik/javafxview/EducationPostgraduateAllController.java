package ntukhpi.semit.militaryoblik.javafxview;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;
import ntukhpi.semit.militaryoblik.adapters.EducationPostgraduateAdapter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EducationPostgraduateAllController {
    @FXML
    public TextField pibTextField;
    @FXML
    public TableView<EducationPostgraduateAdapter> postgraduateEducationTableView;
    @FXML
    public TableColumn<EducationPostgraduateAdapter, String> typeColumn;
    @FXML
    public TableColumn<EducationPostgraduateAdapter, String> vnzColumn;
    @FXML
    public TableColumn<EducationPostgraduateAdapter, String> yearColumn;

    private ObservableList<EducationPostgraduateAdapter> postgraduateEducationObservableList = getPostgraduateEducationData();

    private ObservableList<EducationPostgraduateAdapter> getPostgraduateEducationData() {
        List<EducationPostgraduateAdapter> postgraduateList = new ArrayList<>();

        postgraduateList.add(new EducationPostgraduateAdapter("Докторантура",  "Харківський національний університет імені В. Н. Каразіна", 2012));
        postgraduateList.add(new EducationPostgraduateAdapter("Аспірантура", "Національний технічний університет «Харківський політехнічний інститут»", 2015));

        return FXCollections.observableList(postgraduateList);
    }

    @FXML
    private void returnToMainForm(ActionEvent event) {
        MilitaryOblikKhPIMain.showReservistsWindow();
    }

    @FXML
    private void openAddWindow(ActionEvent event) {
        MilitaryOblikKhPIMain.openEditWindow("EducationPostgraduateEdit.fxml", "Додати дані про післядипломне навчання", this, null);
    }

    @FXML
    private void openEditWindow(ActionEvent event) {
        EducationPostgraduateAdapter selectedEducation = postgraduateEducationTableView.getSelectionModel().getSelectedItem();
        if (selectedEducation != null) {
            MilitaryOblikKhPIMain.openEditWindow("EducationPostgraduateEdit.fxml", "Редагувати дані про навчання", this, selectedEducation);
        } else {
            MilitaryOblikKhPIMain.showAlert("Error", "No row selected", "Please select a row to edit.");
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

    public void initialize() {
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        vnzColumn.setCellValueFactory(new PropertyValueFactory<>("vnz"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));

        updateTable(postgraduateEducationObservableList);
    }

    private void updateTable(ObservableList<EducationPostgraduateAdapter> postgraduateEducationObservableList) {
        postgraduateEducationTableView.setItems(postgraduateEducationObservableList);
    }
}
