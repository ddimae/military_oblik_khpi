package ntukhpi.semit.militaryoblik.javafxview;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;
import ntukhpi.semit.militaryoblik.adapters.EducationPostgraduateAdapter;
import ntukhpi.semit.militaryoblik.javafxutils.ControlledScene;
import ntukhpi.semit.militaryoblik.javafxutils.Popup;

import java.net.URL;
import java.util.ResourceBundle;

public class EducationPostgraduateEditController implements Initializable, ControlledScene {
    @FXML
    private TextField pibTextField;
    @FXML
    public ComboBox<String> typeComboBox;
    @FXML
    public ComboBox<String> vnzComboBox;
    @FXML
    private TextField yearTextField;

    private EducationPostgraduateAllController mainController;
    private EducationPostgraduateAdapter selectedEducation;
    private boolean editingExistingEducation;

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
        String yearStr = yearTextField.getText();

        if (yearStr.length() != 4) {
            Popup.wrongInputAlert("Рік повинен містити 4 цифри");
            return;
        }

        int year;

        try {
            year = Integer.parseInt(yearStr);
        } catch (NumberFormatException e) {
            Popup.wrongInputAlert("Рік повинен бути числом");
            return;
        }

        String type = typeComboBox.getValue();
        String vnz = vnzComboBox.getValue();

        if (type == null || vnz == null) {
            Popup.wrongInputAlert("Заповніть обов'язкові поля"); //TODO Позначити у формі обов'язкові поля!!!
            return;
        }

        EducationPostgraduateAdapter education = new EducationPostgraduateAdapter(type, vnz, year);

        if (editingExistingEducation) {
            selectedEducation.setVnz(vnzComboBox.getValue());
            mainController.updatePostgraduateEducation((EducationPostgraduateAdapter) selectedEducation, education);
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> typeOptions = FXCollections.observableArrayList(
                "Адʼюнктура",
                "Аспірантура",
                "Докторантура"
        );
        ObservableList<String> vnzOptions = FXCollections.observableArrayList(
                "Харківський національний університет внутрішніх справ",
                "Харківський національний університет імені В. Н. Каразіна",
                "Національний технічний університет «Харківський політехнічний інститут»",
                "Національний університет «Юридична академія України імені Ярослава Мудрого»"
        );

        typeComboBox.setItems(typeOptions);
        vnzComboBox.setItems(vnzOptions);
    }
}
