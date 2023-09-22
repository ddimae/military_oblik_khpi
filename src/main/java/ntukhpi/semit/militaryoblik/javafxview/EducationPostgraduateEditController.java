/*
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
import ntukhpi.semit.militaryoblik.adapters.EducationPostgraduateAdapter;
import ntukhpi.semit.militaryoblik.entity.EducationPostgraduate;
import ntukhpi.semit.militaryoblik.entity.VNZaklad;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.javafxutils.ControlledScene;
import ntukhpi.semit.militaryoblik.javafxutils.DataFormat;
import ntukhpi.semit.militaryoblik.javafxutils.Popup;
import ntukhpi.semit.militaryoblik.service.EducationPostgraduateServiceImpl;
import ntukhpi.semit.militaryoblik.service.PrepodServiceImpl;
import ntukhpi.semit.militaryoblik.service.VNZakladServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EducationPostgraduateEditController implements ControlledScene {
    @FXML
    private Label pibLabel;
    @FXML
    public ComboBox<String> typeComboBox;
    @FXML
    public ComboBox<VNZaklad> vnzComboBox;
    @FXML
    private TextField yearTextField;

    private EducationPostgraduateAllController mainController;
    private EducationPostgraduateAdapter selectedEducation;

    private ObservableList<VNZaklad> vnzObservableList;

    private Prepod selectedPrepod;

    @Autowired
    EducationPostgraduateServiceImpl educationPostgraduateService;
    @Autowired
    PrepodServiceImpl prepodService;
    @Autowired
    VNZakladServiceImpl vnZakladService;

    @Override
    public void setMainController(Object mainController) {
        this.mainController = (EducationPostgraduateAllController) mainController;
    }

    @Override
    public void setData(Object data) {
        if (data instanceof EducationPostgraduateAdapter) {
            setPostgraduateEducation((EducationPostgraduateAdapter) data);
        }
    }
    public void setPostgraduateEducation(EducationPostgraduateAdapter postgraduateEducation) {
        selectedEducation = postgraduateEducation;
        pibLabel.setText(DataFormat.getPIB(prepodService.getPrepodById(selectedPrepod.getId())));

        VNZaklad vnz = postgraduateEducation.getVnz();
        if (vnz != null) vnzComboBox.setValue(vnz);

        typeComboBox.setValue(postgraduateEducation.getType());
        vnzComboBox.setValue(postgraduateEducation.getVnz());
        yearTextField.setText(postgraduateEducation.getYear());
    }

    @FXML
    private void saveEducation() {
        String year = yearTextField.getText();

        if (year.length() != 4) {
            Popup.wrongInputAlert("Рік повинен містити 4 цифри");
            return;
        }

        String type = typeComboBox.getValue();
        VNZaklad vnz = vnzComboBox.getValue();

        if (type == null || vnz == null) {
            Popup.wrongInputAlert("Заповніть обов'язкові поля");
            return;
        }

        try {
            EducationPostgraduate newEducation = new EducationPostgraduate();

            newEducation.setPrepod(selectedPrepod);
            newEducation.setYearFinish(year);
            newEducation.setLevelTraining(type);
            newEducation.setVnz(vnz);

            if (selectedEducation == null) {
                mainController.addPostgraduateEducation(newEducation);
            } else {
                mainController.updatePostgraduateEducation(selectedEducation, newEducation);
            }
            closeEdit(null);
            Popup.successSave();
        } catch (Exception e) {
            Popup.wrongInputAlert(e.getMessage());
        }
    }

    @FXML
    private void closeEdit(ActionEvent event) {
        try {
            ((Stage) typeComboBox.getScene().getWindow()).close();
            MilitaryOblikKhPIMain.showPostgraduateEducationWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ObservableList<VNZaklad> getAllVNZ() {
        return FXCollections.observableArrayList(educationPostgraduateService.getAllVNZ());
    }

    @FXML
    private void addVNZ(ActionEvent event) {
        MilitaryOblikKhPIMain.showAddVNZWindow(vnzComboBox, vnzObservableList);
    }

    public void initialize() {
        ObservableList<String> typeOptions = FXCollections.observableArrayList(
                "Адʼюнктура",
                "Аспірантура",
                "Докторантура"
        );

        vnzObservableList = getAllVNZ();

        selectedPrepod = prepodService.getPrepodById(ReservistsAllController.getSelectedPrepodId());
        pibLabel.setText(DataFormat.getPIB(selectedPrepod));

        typeComboBox.setItems(typeOptions);
        vnzComboBox.setItems(vnzObservableList);
    }
}
*/
