package ntukhpi.semit.militaryoblik.javafxview;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;
import ntukhpi.semit.militaryoblik.adapters.EducationAdapter;
import ntukhpi.semit.militaryoblik.adapters.UniversityAdapter;
import ntukhpi.semit.militaryoblik.entity.Education;
import ntukhpi.semit.militaryoblik.entity.VNZaklad;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.javafxutils.AllStageSettings;
import ntukhpi.semit.militaryoblik.javafxutils.ControlledScene;
import ntukhpi.semit.militaryoblik.javafxutils.DataFormat;
import ntukhpi.semit.militaryoblik.javafxutils.Popup;
import ntukhpi.semit.militaryoblik.javafxutils.validators.EducationValidator;
import ntukhpi.semit.militaryoblik.service.PrepodServiceImpl;
import ntukhpi.semit.militaryoblik.service.VNZakladServiceImpl;
import ntukhpi.semit.militaryoblik.service.entitycrud.EducationCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EducationEditController implements ControlledScene {
    @FXML
    public Label pibLabel;
    @FXML
    public ComboBox<UniversityAdapter> vnzComboBox;
    @FXML
    public ComboBox<String> formComboBox;
    @FXML
    public ComboBox<String> levelComboBox;
    @FXML
    public TextField yearTextField;
    @FXML
    public TextField diplomaSeriesTextField;
    @FXML
    public TextField diplomaNumberTextField;
    @FXML
    public TextField specialtyTextField;
    @FXML
    public TextField qualificationTextField;

    private EducationAllController mainController;
    private Stage mainStage;
    private Stage currentStage;
    private EducationAdapter selectedEducation;

    private ObservableList<UniversityAdapter> vnzObservableList;
    private Prepod selectedPrepod;

//    @Autowired
//    EducationServiceImpl educationService;
    @Autowired
    EducationCRUD educationCRUD;
    @Autowired
    PrepodServiceImpl prepodService;
    @Autowired
    VNZakladServiceImpl vnZakladService;
    @Autowired
    EducationValidator educationValidator;

    @Override
    public void setMainController(Object mainController) {
        this.mainController = (EducationAllController) mainController;
    }

    @Override
    public void setData(Object data) {
        if (data instanceof EducationAdapter) {
            setEducation((EducationAdapter) data);
        }
    }

    @Override
    public void setMainStage(Stage stage) {
        mainStage = stage;
    }

    @Override
    public void setCurrentStage(Stage stage) {
        currentStage = stage;
    }

    public void setEducation(EducationAdapter education) {
        selectedEducation = education;
        pibLabel.setText(DataFormat.getPIB(prepodService.getPrepodById(selectedPrepod.getId())));

        UniversityAdapter vnz = education.getVnz();
        if (vnz != null) vnzComboBox.setValue(vnz);

        formComboBox.setValue(education.getForm());
        levelComboBox.setValue(education.getLevel());
        yearTextField.setText(education.getYear());
        diplomaSeriesTextField.setText(education.getDiplomaSeries());
        diplomaNumberTextField.setText(education.getDiplomaNumber());
        specialtyTextField.setText(education.getSpeciality());
        qualificationTextField.setText(education.getQualification());
    }

    @FXML
    private void saveEducation() {
        String year = yearTextField.getText();
        String diplomaNumber = diplomaNumberTextField.getText();
        String diplomaSeries = diplomaSeriesTextField.getText();
        String specialty = specialtyTextField.getText();
        String qualification = qualificationTextField.getText();
        UniversityAdapter vnz = vnzComboBox.getValue() != null ? vnzComboBox.getValue() : new UniversityAdapter();
        String form = formComboBox.getValue();
        String level = levelComboBox.getValue();

        try {
            educationValidator.validate(new EducationAdapter(null, year, diplomaSeries,
                                                            diplomaNumber, specialty, qualification,
                                                            vnz, form, level));
        } catch (Exception e) {
            Popup.wrongInputAlert(e.getMessage());
            return;
        }

        try {
            if (selectedEducation == null) {
                educationCRUD.addEducation(selectedPrepod.getId(),
                        new EducationAdapter(null, year, diplomaSeries, diplomaNumber, specialty, qualification, vnz, form, level));
            } else {
                educationCRUD.updateEducation(selectedEducation.getId(), selectedPrepod.getId(),
                        new EducationAdapter(null, year, diplomaSeries, diplomaNumber, specialty, qualification, vnz, form, level));
            }

            //DDE - refresh education list after add or edit or delete
            mainController.afterEducationCRUD();
            closeEdit(null);
            Popup.successSave();
        } catch (Exception e) {
            e.printStackTrace();
            Popup.wrongInputAlert(e.getMessage());
        }
    }

    @FXML
    private void closeEdit(ActionEvent event) {
        MilitaryOblikKhPIMain.showPreviousStage(mainStage, currentStage);
    }

    private ObservableList<UniversityAdapter> getAllVNZ() {
        return FXCollections.observableArrayList(vnZakladService.getAllVNZaklad().stream()
                .map(UniversityAdapter::new)
                .toList());
    }

    @FXML
    private void addVNZ(ActionEvent event) {
        Object[] arr = {vnzComboBox, vnzObservableList};
        MilitaryOblikKhPIMain.showStage(AllStageSettings.vnzAdd, currentStage, this, arr);
    }

    public void initialize() {
        ObservableList<String> formOptions = FXCollections.observableArrayList(
                "Денна",
                "Заочна"
        );
        ObservableList<String> levelOptions = FXCollections.observableArrayList(
                "бакалавр",
                "магістр",
                "спеціаліст"
        );

        vnzObservableList = getAllVNZ();

        vnzComboBox.setItems(vnzObservableList);
        formComboBox.setItems(formOptions);
        levelComboBox.setItems(levelOptions);

        selectedPrepod = prepodService.getPrepodById(ReservistsAllController.getSelectedPrepodId());

        pibLabel.setText(DataFormat.getPIB(selectedPrepod));
    }
}
