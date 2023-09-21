package ntukhpi.semit.militaryoblik.javafxview;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ntukhpi.semit.militaryoblik.adapters.EducationAdapter;
import ntukhpi.semit.militaryoblik.entity.VNZaklad;
import ntukhpi.semit.militaryoblik.javafxutils.ControlledScene;
import ntukhpi.semit.militaryoblik.service.VNZakladService;
import ntukhpi.semit.militaryoblik.service.VNZakladServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddVNZController {
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField abbreviationTextField;
    @FXML
    private Button saveButton;

    private ObservableList<VNZaklad> vnzObservableList;
    private ComboBox<VNZaklad> vnzComboBox;

    @Autowired
    VNZakladServiceImpl vnZakladService;

    public void initialize() {
        // ...
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
            newVNZ.setVnzName(name);
            newVNZ.setVnzShortName(abbreviation);

            try {
                vnZakladService.createVNZaklad(newVNZ);
            } catch (Exception e) {
                e.printStackTrace();
            }

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
