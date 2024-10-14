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
import ntukhpi.semit.militaryoblik.adapters.FamilyAdapter;
import ntukhpi.semit.militaryoblik.entity.FamilyMember;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.javafxutils.ControlledScene;
import ntukhpi.semit.militaryoblik.javafxutils.DataFormat;
import ntukhpi.semit.militaryoblik.javafxutils.Popup;
import ntukhpi.semit.militaryoblik.javafxutils.validators.FamilyValidator;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.TextFieldValidator;
import ntukhpi.semit.militaryoblik.service.FamilyMemberServiceImpl;
import ntukhpi.semit.militaryoblik.service.MilitaryPersonServiceImpl;
import ntukhpi.semit.militaryoblik.service.PrepodServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class FamilyCompositionEditController implements ControlledScene {
    @FXML
    public Label pibLabel;
    @FXML
    public ComboBox<String> relationshipComboBox;
    @FXML
    public TextField surnameTextField;
    @FXML
    public TextField nameTextField;
    @FXML
    public TextField patronymicTextField;
    @FXML
    public TextField yearTextField;

    private FamilyCompositionAllController mainController;
    private Stage mainStage;
    private Stage currentStage;
    private Prepod selectedPrepod;
    private FamilyAdapter selectedMember;

    @Autowired
    FamilyValidator familyValidator;
    @Autowired
    PrepodServiceImpl prepodService;

    @Override
    public void setMainController(Object mainController) {
        this.mainController = (FamilyCompositionAllController) mainController;
    }

    @Override
    public void setData(Object data) {
        setFamilyMembers((FamilyAdapter) data);
    }

    @Override
    public void setMainStage(Stage stage) {
        mainStage = stage;
    }

    @Override
    public void setCurrentStage(Stage stage) {
        currentStage = stage;
    }

    private void setFamilyMembers(FamilyAdapter family) {
        selectedPrepod = prepodService.getPrepodById(ReservistsAllController.getSelectedPrepodId());

        pibLabel.setText(DataFormat.getPIB(selectedPrepod));
        selectedMember = family;

        if (selectedMember == null)
            return;

        relationshipComboBox.setValue(family.getVidRidstva());
        surnameTextField.setText(family.getMemFam());
        nameTextField.setText(family.getMemName());
        patronymicTextField.setText(family.getMemOtch());
        yearTextField.setText(family.getRikNarodz());
    }

    @FXML
    void closeEdit(ActionEvent event) {
        MilitaryOblikKhPIMain.showPreviousStage(mainStage, currentStage);
    }


    @FXML
    void saveMember(ActionEvent event) {
        String vidRidstva = DataFormat.getPureValue(relationshipComboBox.getValue());

        String surname = surnameTextField.getText();
        String name = nameTextField.getText();
        String patronimic = patronymicTextField.getText();
        String year = yearTextField.getText();

        try {
            familyValidator.validate(new FamilyAdapter(null, surname, name, patronimic, vidRidstva, year));
        } catch (Exception e) {
            Popup.wrongInputAlert(e.getMessage());
            return;
        }

        try {
            FamilyMember newMember = new FamilyMember();

            newMember.setPrepod(selectedPrepod);
            newMember.setVidRidstva(vidRidstva);
            newMember.setMemFam(surname);
            newMember.setMemImya(name);
            newMember.setMemOtch(patronimic);
            newMember.setRikNarodz(year);

            if (selectedMember == null)
                mainController.addNewFamilyMember(newMember);
            else
                mainController.updateFamily(selectedMember, newMember);

            closeEdit(null);
            Popup.successSave();
        } catch (Exception e) {
            Popup.wrongInputAlert(e.getMessage());
        }
    }

    public void initialize() {
        ObservableList<String> typesOptions = FXCollections.observableArrayList(
                "дружина",
                "чоловік",
                "донька",
                "син",
                "донька подружжя",
                "син подружжя"
        );

        relationshipComboBox.setItems(typesOptions);
    }
}
