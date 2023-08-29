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
import ntukhpi.semit.militaryoblik.adapters.EducationAdapter;
import org.springframework.stereotype.Component;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;

import java.util.ArrayList;
import java.util.List;

@Component
public class EducationAllController {
    @FXML
    public TextField pibTextField;
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

    @FXML
    private void openAddWindow(ActionEvent event) {
        MilitaryOblikKhPIMain.openEditWindow("/javafxview/EducationEdit.fxml", "Додати дані про навчання", this, null);
    }

    @FXML
    private void openEditWindow(ActionEvent event) {
        EducationAdapter selectedEducation = educationTableView.getSelectionModel().getSelectedItem();
        if (selectedEducation != null) {
            MilitaryOblikKhPIMain.openEditWindow("/javafxview/EducationEdit.fxml", "Редагувати дані про навчання", this, selectedEducation);
        } else {
            MilitaryOblikKhPIMain.showAlert("Error", "No row selected", "Please select a row to edit.");
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
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No row selected");
            alert.setContentText("Please select a row to edit.");
            alert.showAndWait();
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

    private ObservableList<EducationAdapter> educationObservableList = getEducationData();

    private ObservableList<EducationAdapter> getEducationData() {
        List<EducationAdapter> educationArrayList = new ArrayList<>();

        educationArrayList.add(new EducationAdapter(2012, "А12", 120095, "Маркетинг", "Бакалавр з маркетингу", "Харківський національний університет імені В. Н. Каразіна", "Заочна", "бакалавр"));
        educationArrayList.add(new EducationAdapter(2017, "А03", 432214, "Психологія", "Магістр з психології", "Національний технічний університет «Харківський політехнічний інститут»", "Денна", "магістр"));
        educationArrayList.add(new EducationAdapter(2022, "А12", 673018, "Інженерія програмного забезпечення", "Бакалавр з інженерії програмного забезпечення", "Харківський національний університет радіоелектроніки", "Денна", "бакалавр"));

        return FXCollections.observableList(educationArrayList);
    }


    public void initialize() {
        vnzColumn.setCellValueFactory(new PropertyValueFactory<>("vnz"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        formColumn.setCellValueFactory(new PropertyValueFactory<>("form"));
        specialtyColumn.setCellValueFactory(new PropertyValueFactory<>("speciality"));
        qualificationColumn.setCellValueFactory(new PropertyValueFactory<>("qualification"));

        updateTable(educationObservableList);
    }

    private void updateTable(ObservableList<EducationAdapter> educationObservableList) {
        educationTableView.setItems(educationObservableList);
    }
}
