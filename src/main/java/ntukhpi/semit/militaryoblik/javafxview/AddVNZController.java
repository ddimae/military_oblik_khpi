package ntukhpi.semit.militaryoblik.javafxview;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ntukhpi.semit.militaryoblik.adapters.EducationAdapter;
import ntukhpi.semit.militaryoblik.entity.VNZaklad;

public class AddVNZController {
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField abbreviationTextField;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    private ObservableList<VNZaklad> vnzObservableList;
    private ComboBox<VNZaklad> vnzComboBox;

    public void initialize() {
        //
    }

    public void setVNZData(ComboBox<VNZaklad> comboBox, ObservableList<VNZaklad> vnzList) {
        this.vnzComboBox = comboBox;
        this.vnzObservableList = vnzList;
    }

    @FXML
    private void saveVNZ() {
        String name = nameTextField.getText();
        String abbreviation = abbreviationTextField.getText();

        if (!name.isEmpty() && !abbreviation.isEmpty()) {
            VNZaklad newVNZ = new VNZaklad();
            newVNZ.setName(name);
            newVNZ.setAbbreviation(abbreviation);

            vnzObservableList.add(newVNZ);
            vnzComboBox.setValue(newVNZ);

            closeStage();
        }
    }

    @FXML
    private void cancel() {
        closeStage();
    }

    private void closeStage() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
}
