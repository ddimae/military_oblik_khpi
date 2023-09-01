package ntukhpi.semit.militaryoblik.javafxview;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;
import ntukhpi.semit.militaryoblik.adapters.EducationAdapter;
import ntukhpi.semit.militaryoblik.javafxutils.ControlledScene;
import org.springframework.stereotype.Component;

@Component
public class EducationEditController implements ControlledScene {
    @FXML
    public ComboBox<String> vnzComboBox;
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
    private boolean editingExistingEducation;

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

    @FXML
    private void saveEducation() {
        String yearStr = yearTextField.getText();
        String diplomaNumberStr = diplomaNumberTextField.getText();

        if (yearStr.length() != 4 || diplomaNumberStr.length() != 6) {
            MilitaryOblikKhPIMain.showAlert("Error", "Invalid input", "Year must have 4 digits, and diploma number must have 6 digits"); //TODO перевести на урк
            return;
        }

        int year;
        int diplomaNumber;

        try {
            year = Integer.parseInt(yearStr);
            diplomaNumber = Integer.parseInt(diplomaNumberStr);
        } catch (NumberFormatException e) {
            MilitaryOblikKhPIMain.showAlert("Error", "Invalid input", "Year and diploma number must be valid integers");//TODO перевести на урк
            return;
        }

        String diplomaSeries = diplomaSeriesTextField.getText();
        String specialty = specialtyTextField.getText();
        String qualification = qualificationTextField.getText();
        String vnz = vnzComboBox.getValue();
        String form = formComboBox.getValue();
        String level = levelComboBox.getValue();

        if (vnz == null || form == null || level == null || diplomaSeries.isEmpty()
                || specialty.isEmpty() || qualification.isEmpty()) {
            MilitaryOblikKhPIMain.showAlert("Error", "All fields are required", "Please fill in all data"); //TODO перевести на урк
            return;
        }

        EducationAdapter education = new EducationAdapter(year, diplomaSeries, diplomaNumber,
                specialty, qualification, vnz, form, level);

        if (editingExistingEducation) {
            selectedEducation.setVnz(vnzComboBox.getValue());
            mainController.updateEducation(selectedEducation, education);
        } else {
            mainController.addEducation(education);
        }

        ((Stage) vnzComboBox.getScene().getWindow()).close();
    }

    public void setEducation(EducationAdapter education) {
        this.selectedEducation = education;
        editingExistingEducation = true;
        vnzComboBox.setValue(education.getVnz());
        formComboBox.setValue(education.getForm());
        levelComboBox.setValue(education.getLevel());
        yearTextField.setText(String.valueOf(education.getYear()));
        diplomaSeriesTextField.setText(education.getDiplomaSeries());
        diplomaNumberTextField.setText(String.valueOf(education.getDiplomaNumber()));
        specialtyTextField.setText(education.getSpeciality());
        qualificationTextField.setText(education.getQualification());
    }

    @FXML
    private void closeEdit(ActionEvent event) {
        try {
            ((Stage) vnzComboBox.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        ObservableList<String> vnzOptions = FXCollections.observableArrayList(
                "Харківський національний університет внутрішніх справ",
                "Харківський національний університет імені В. Н. Каразіна",
                "Національний технічний університет «Харківський політехнічний інститут»",
                "Національний університет «Юридична академія України імені Ярослава Мудрого»"
        );
        ObservableList<String> formOptions = FXCollections.observableArrayList(
                "Денна",
                "Заочна"
        );
        ObservableList<String> levelOptions = FXCollections.observableArrayList(
                "бакалавр",
                "магістр",
                "спеціаліст"
        );

        vnzComboBox.setItems(vnzOptions);
        formComboBox.setItems(formOptions);
        levelComboBox.setItems(levelOptions);
    }

}
