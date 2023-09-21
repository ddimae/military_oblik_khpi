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
import ntukhpi.semit.militaryoblik.adapters.ReservistAdapter;
import ntukhpi.semit.militaryoblik.entity.Education;
import ntukhpi.semit.militaryoblik.entity.VNZaklad;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.javafxutils.ControlledScene;
import ntukhpi.semit.militaryoblik.javafxutils.DataFormat;
import ntukhpi.semit.militaryoblik.javafxutils.Popup;
import ntukhpi.semit.militaryoblik.service.EducationServiceImpl;
import ntukhpi.semit.militaryoblik.service.PrepodServiceImpl;
import ntukhpi.semit.militaryoblik.service.VNZakladServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EducationEditController implements ControlledScene {
    @FXML
    public Label pibLabel;
    @FXML
    public ComboBox<VNZaklad> vnzComboBox;
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
    private EducationAdapter selectedEducation;

    private ObservableList<VNZaklad> vnzObservableList;
    private Prepod selectedPrepod;

    @Autowired
    EducationServiceImpl educationService;
    @Autowired
    PrepodServiceImpl prepodService;
    @Autowired
    VNZakladServiceImpl vnZakladService;

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

    public void setEducation(EducationAdapter education) {
        selectedEducation = education;
        pibLabel.setText(DataFormat.getPIB(prepodService.getPrepodById(selectedPrepod.getId())));

        VNZaklad vnz = education.getVnz();
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

        if (year.length() != 4) {
            Popup.wrongInputAlert("Рік повинен містити 4 цифри");
            return;
        }

        String diplomaNumber = diplomaNumberTextField.getText();
        String diplomaSeries = diplomaSeriesTextField.getText();

//        if (!diplomaNumber.matches("^[a-zA-Z0-9]*$") || !diplomaSeries.matches("^[a-zA-Z0-9]*$")) {
//            Popup.wrongInputAlert("Дипломний номер та серія можуть містити лише букви та цифри");
//            return;
//        }

        String specialty = specialtyTextField.getText();
        String qualification = qualificationTextField.getText();
        VNZaklad vnz = vnzComboBox.getValue();
        String form = formComboBox.getValue();
        String level = levelComboBox.getValue();

        if (vnz == null || form == null || level == null || diplomaNumber.isEmpty()
                || specialty.isEmpty() || qualification.isEmpty()) {
            Popup.wrongInputAlert("Заповніть обов'язкові поля"); //TODO Позначити у формі обов'язкові поля!!!
            return;
        }

        try {
            Education newEducation = new Education();

            newEducation.setPrepod(selectedPrepod);
            newEducation.setFormTraining(form);
            newEducation.setLevelTraining(level);
            newEducation.setVnz(vnz);
            newEducation.setYearVypusk(year);
            newEducation.setDiplomaNumber(diplomaNumber);
            newEducation.setDiplomaSeries(diplomaSeries);
            newEducation.setDiplomaSpeciality(specialty);
            newEducation.setDiplomaQualification(qualification);

            if (selectedEducation == null)
                mainController.addEducation(newEducation);
            else
                mainController.updateEducation(selectedEducation, newEducation);

            closeEdit(null);
            Popup.successSave();
        } catch (Exception e) {
            Popup.wrongInputAlert(e.getMessage());
        }
    }

    @FXML
    private void closeEdit(ActionEvent event) {
        try {
            ((Stage) vnzComboBox.getScene().getWindow()).close();
            MilitaryOblikKhPIMain.showEducationWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ObservableList<VNZaklad> getAllVNZ() {
        return FXCollections.observableArrayList(educationService.getAllVNZ());
    }

    @FXML
    private void addVNZ(ActionEvent event) {
        MilitaryOblikKhPIMain.showAddVNZWindow(vnzComboBox, vnzObservableList);
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
