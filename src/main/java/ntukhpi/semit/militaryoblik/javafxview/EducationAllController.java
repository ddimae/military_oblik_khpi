package ntukhpi.semit.militaryoblik.javafxview;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import ntukhpi.semit.militaryoblik.adapters.EducationAdapter;
import ntukhpi.semit.militaryoblik.entity.Education;
import ntukhpi.semit.militaryoblik.entity.VNZaklad;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.javafxutils.DataFormat;
import ntukhpi.semit.militaryoblik.javafxutils.Popup;
import ntukhpi.semit.militaryoblik.service.EducationServiceImpl;
import ntukhpi.semit.militaryoblik.service.PrepodServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;

import java.util.Optional;
import java.util.logging.Logger;

@Component
public class EducationAllController {
    private final static String EDUCATION_EDIT_JAVAFX = "/javafxview/EducationEdit.fxml";

    @FXML
    public Label pibLabel;
    @FXML
    public Label fullNameLabel;
    @FXML
    public Label shortNameLabel;
    @FXML
    public Label yearLabel;
    @FXML
    public Label typeLabel;
    @FXML
    public Label levelLabel;
    @FXML
    public Label diplomaSeriesLabel;
    @FXML
    public Label diplomaNumberLabel;
    @FXML
    public Label specialityLabel;
    @FXML
    public Label qalificationLabel;
    @FXML
    private TableView<EducationAdapter> vnzTableView;
    @FXML
    private TableColumn<EducationAdapter, String> shortNameColumn;
    @FXML
    private TableColumn<EducationAdapter, String> yearColumn;

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

        pibLabel.setText(DataFormat.getPIB(selectedPrepod));

        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        shortNameColumn.setCellValueFactory(cellData -> {
            String vnzName = String.valueOf(cellData.getValue().getVnz());
            String[] parts = vnzName.split("\\(");

            if (parts.length > 0) {
                String shortName = parts[0].trim();
                return new SimpleStringProperty(shortName);
            } else {
                return new SimpleStringProperty(vnzName);
            }
        });

        vnzTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                populateCentralFields(newValue);
            }
        });

        educationObservableList = getEducationData();

        updateTable(educationObservableList);
    }

    private void populateCentralFields(EducationAdapter selectedEducation) {
        yearLabel.setText(selectedEducation.getYear());
        diplomaSeriesLabel.setText(selectedEducation.getDiplomaSeries());
        diplomaNumberLabel.setText(selectedEducation.getDiplomaNumber());
        specialityLabel.setText(selectedEducation.getSpeciality());
        qalificationLabel.setText(selectedEducation.getQualification());
        typeLabel.setText(selectedEducation.getForm());
        levelLabel.setText(selectedEducation.getLevel());
        shortNameLabel.setText(String.valueOf(selectedEducation.getVnz()).substring(0,
                String.valueOf(selectedEducation.getVnz()).indexOf('(')));

        String vnzFullName = String.valueOf(selectedEducation.getVnz());
        String fullName = "";
        if (vnzFullName.indexOf('(') != -1 && vnzFullName.indexOf(')') != -1) {
            fullName = vnzFullName.substring(vnzFullName.indexOf('(') + 1, vnzFullName.indexOf(')'));
        }
        fullNameLabel.setText(fullName);
        fullNameLabel.setWrapText(true);
    }

    private void updateTable(ObservableList<EducationAdapter> educationObservableList) {
        vnzTableView.setItems(educationObservableList);
    }

    @FXML
    private void openAddWindow(ActionEvent event) {
        MilitaryOblikKhPIMain.openEditWindow(EDUCATION_EDIT_JAVAFX, "Додати дані про навчання", this, null);
    }

    @FXML
    private void openEditWindow(ActionEvent event) {
        EducationAdapter selectedEducation = vnzTableView.getSelectionModel().getSelectedItem();
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
    public void deleteSelectedRow(ActionEvent event) {
        EducationAdapter selectedEducation = vnzTableView.getSelectionModel().getSelectedItem();

        if (selectedEducation != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Підтвердження видалення");
            alert.setHeaderText("Ви впевнені, що хочете видалити цей запис?");
            alert.setContentText("Для підтвердження натисніть OK.");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                educationObservableList.remove(selectedEducation);
                educationService.deleteEducation(selectedEducation.getId());
            }
        } else {
            Popup.noSelectedRowAlert();
        }
    }

    public void addEducation(Education newEducation) {
        educationService.createEducation(newEducation);

        educationObservableList.add(new EducationAdapter(newEducation));
        vnzTableView.refresh();
    }

    public void updateEducation(EducationAdapter oldEducation, Education newEducation) {
        educationService.updateEducation(oldEducation.getId(), newEducation);

        educationObservableList.remove(oldEducation);
        educationObservableList.add(new EducationAdapter(newEducation));
        vnzTableView.refresh();
    }
}
