package ntukhpi.semit.militaryoblik.javafxview;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;
import ntukhpi.semit.militaryoblik.adapters.EducationAdapter;
import ntukhpi.semit.militaryoblik.adapters.EducationPostgraduateAdapter;
import ntukhpi.semit.militaryoblik.entity.EducationPostgraduate;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.javafxutils.AllStageSettings;
import ntukhpi.semit.militaryoblik.javafxutils.ControlledScene;
import ntukhpi.semit.militaryoblik.javafxutils.DataFormat;
import ntukhpi.semit.militaryoblik.javafxutils.Popup;
import ntukhpi.semit.militaryoblik.service.EducationPostgraduateServiceImpl;
import ntukhpi.semit.militaryoblik.service.PrepodServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Optional;

@Component
public class EducationPostgraduateAllController implements ControlledScene {
    @FXML
    public Label pibLabel;
    @FXML
    private TableView<EducationPostgraduateAdapter> vnzTableView;
    @FXML
    private TableColumn<EducationPostgraduateAdapter, String> shortNameColumn;
    @FXML
    private TableColumn<EducationPostgraduateAdapter, String> yearColumn;
    @FXML
    public Label fullNameLabel;
    @FXML
    public Label shortNameLabel;
    @FXML
    public Label yearLabel;
    @FXML
    public Label typeLabel;

    private ObservableList<EducationPostgraduateAdapter> postgraduateEducationObservableList;
    private Prepod selectedPrepod;
    private ReservistsAllController mainController;
    private Stage mainStage;
    private Stage currentStage;

    @Autowired
    EducationPostgraduateServiceImpl educationPostgraduateService;

    @Autowired
    PrepodServiceImpl prepodService;

    @Override
    public void setMainController(Object controller) {
        mainController = (ReservistsAllController) controller;
    }

    @Override
    public void setData(Object data) {}

    @Override
    public void setMainStage(Stage stage) {
        mainStage = stage;
    }

    @Override
    public void setCurrentStage(Stage stage) {
        currentStage = stage;
    }

    private ObservableList<EducationPostgraduateAdapter> getEducationPostgraduateData() {
        return FXCollections.observableArrayList(educationPostgraduateService.
                getAllEducationPostgraduateByPrepod(selectedPrepod).
                stream().map(EducationPostgraduateAdapter::new).
                sorted(Comparator.comparing(EducationPostgraduateAdapter::getYear)).
                toList());
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

        postgraduateEducationObservableList = getEducationPostgraduateData();

        vnzTableView.setItems(getEducationPostgraduateData());

    }

    private void populateCentralFields(EducationPostgraduateAdapter selectedEducation) {
        yearLabel.setText(selectedEducation.getYear());
        typeLabel.setText(selectedEducation.getType());
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

    private void refreshVNZTable() {
        vnzTableView.setItems(getEducationPostgraduateData());
        vnzTableView.refresh();
    }

    @FXML
    private void openAddWindow(ActionEvent event) {
        MilitaryOblikKhPIMain.showStage(AllStageSettings.educationPostgraduateAdd, currentStage, this, null);
    }

    @FXML
    private void openEditWindow(ActionEvent event) {
        EducationPostgraduateAdapter selectedEducation = vnzTableView.getSelectionModel().getSelectedItem();
        if (selectedEducation != null) {
            MilitaryOblikKhPIMain.showStage(AllStageSettings.educationPostgraduateEdit, currentStage, this, selectedEducation);
        } else {
            Popup.noSelectedRowAlert();
        }
    }

    @FXML
    private void returnToMainForm(ActionEvent event) {
        MilitaryOblikKhPIMain.showPreviousStage(mainStage, currentStage);
    }

    @FXML
    private void deleteSelectedRow(ActionEvent event) {
        EducationPostgraduateAdapter selectedPostgaduateEducation = vnzTableView.getSelectionModel().getSelectedItem();

        if (selectedPostgaduateEducation != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Підтвердження видалення");
            alert.setHeaderText("Ви впевнені, що хочете видалити цей запис?");
            alert.setContentText("Для підтвердження натисніть OK.");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
//                postgraduateEducationObservableList.remove(selectedPostgaduateEducation);
                educationPostgraduateService.deleteEducationPostgraduate(selectedPostgaduateEducation.getId());
                refreshVNZTable();
            }
        } else {
            Popup.noSelectedRowAlert();
        }
    }

    public void addPostgraduateEducation(EducationPostgraduate newEducation) {
        educationPostgraduateService.createEducationPostgraduate(newEducation);

//        postgraduateEducationObservableList.add(new EducationPostgraduateAdapter(newEducation));
//        vnzTableView.refresh();
        refreshVNZTable();
    }

    public void updatePostgraduateEducation(EducationPostgraduateAdapter oldEducation, EducationPostgraduate newEducation) {
        educationPostgraduateService.updateEducationPostgraduate(oldEducation.getId(), newEducation);

//        postgraduateEducationObservableList.remove(oldEducation);
//        postgraduateEducationObservableList.add(new EducationPostgraduateAdapter(newEducation));
//        vnzTableView.refresh();
        refreshVNZTable();
    }
}
