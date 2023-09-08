package ntukhpi.semit.militaryoblik.javafxview;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;
import ntukhpi.semit.militaryoblik.adapters.EducationPostgraduateAdapter;
import ntukhpi.semit.militaryoblik.entity.VNZaklad;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.javafxutils.ControlledScene;
import ntukhpi.semit.militaryoblik.javafxutils.Popup;
import ntukhpi.semit.militaryoblik.service.EducationPostgraduateServiceImpl;
import ntukhpi.semit.militaryoblik.service.PrepodServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class EducationPostgraduateEditController implements Initializable, ControlledScene {
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
    private boolean editingExistingEducation;

    private ObservableList<VNZaklad> vnzObservableList;

    private Prepod selectedPrepod;

    @Autowired
    EducationPostgraduateServiceImpl educationPostgraduateService;
    @Autowired
    PrepodServiceImpl prepodService;

    public void setPostgraduateEducation(EducationPostgraduateAdapter postgraduateEducation) {
        this.selectedEducation = postgraduateEducation;
        editingExistingEducation = true;
        typeComboBox.setValue(postgraduateEducation.getType());
        vnzComboBox.setValue(postgraduateEducation.getVnz());
        yearTextField.setText(String.valueOf(postgraduateEducation.getYear()));
    }

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
            Popup.wrongInputAlert("Заповніть обов'язкові поля"); //TODO Позначити у формі обов'язкові поля!!!
            return;
        }

        EducationPostgraduateAdapter education = new EducationPostgraduateAdapter(type, vnz, year);

        if (editingExistingEducation) {
            selectedEducation.setVnz(vnzComboBox.getValue());
            mainController.updatePostgraduateEducation(selectedEducation, education);
        } else {
            mainController.addPostgraduateEducation(education);
        }

        ((Stage) vnzComboBox.getScene().getWindow()).close();
    }

    @FXML
    private void closeEdit(ActionEvent event) {
        try {
            ((Stage) typeComboBox.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addVNZ(ActionEvent event) {
        MilitaryOblikKhPIMain.openAddVNZWindow(vnzComboBox, vnzObservableList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> typeOptions = FXCollections.observableArrayList(
                "Адʼюнктура",
                "Аспірантура",
                "Докторантура"
        );

        vnzObservableList = FXCollections.observableArrayList(educationPostgraduateService.getAllVNZ());

        selectedPrepod = prepodService.getPrepodById(ReservistsAllController.getSelectedPrepodId());
        pibLabel.setText(MilitaryOblikKhPIMain.getPIB(selectedPrepod));

        typeComboBox.setItems(typeOptions);
        vnzComboBox.setItems(vnzObservableList);
    }
}
