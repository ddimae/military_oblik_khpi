package ntukhpi.semit.militaryoblik.javafxview;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;
import ntukhpi.semit.militaryoblik.adapters.ReservistAdapter;
import ntukhpi.semit.militaryoblik.entity.*;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.javafxutils.ControlledScene;
import ntukhpi.semit.militaryoblik.javafxutils.Popup;
import ntukhpi.semit.militaryoblik.javafxutils.validators.TextFieldValidator;
import ntukhpi.semit.militaryoblik.repository.VZvanieRepository;
import ntukhpi.semit.militaryoblik.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class MilitaryRegistrationEditController implements ControlledScene {
    @FXML
    public Label pibLabel;
    @FXML
    public ComboBox<String> groupComboBox;
    @FXML
    public ComboBox<String> categoryComboBox;
    @FXML
    public ComboBox<String> vSkladComboBox;
    @FXML
    public ComboBox<String> rankComboBox;
    @FXML
    public ComboBox<String> validityComboBox;
    @FXML
    public TextField vosTextField;
    @FXML
    public TextField voenkomatTextField;
    @FXML
    public TextField familyStanTextField;
    @FXML
    public TextField educationTextField;
    @FXML
    public ComboBox<String> educationComboBox;
    @FXML
    public ComboBox<String> familyStateComboBox;
    @FXML
    public ComboBox<String> voenkomatComboBox;

    @Autowired
    VZvanieRepository vZvanieRepository;

    @Autowired
    VSkladServiceImpl vSkladService;

    @Autowired
    VZvanieServiceImpl vZvanieService;

    @Autowired
    VoenkomatServiceImpl voenkomatService;

    @Autowired
    PrepodServiceImpl prepodService;

    @Autowired
    MilitaryPersonServiceImpl militaryPersonService;


    private ReservistsAllController mainController;
    private Prepod selectedPrepod;
    private Stage mainStage;
    private Stage currentStage;

    @Override
    public void setMainController(Object mainController) {
        this.mainController = (ReservistsAllController) mainController;
    }

    @Override
    public void setData(Object data) {
        if (data instanceof ReservistAdapter)
            setMilitaryRegistrationInfo((ReservistAdapter) data);
    }

    @Override
    public void setMainStage(Stage stage) {
        mainStage = stage;
    }

    @Override
    public void setCurrentStage(Stage stage) {
        currentStage = stage;
    }

    private void setMilitaryRegistrationInfo(ReservistAdapter reservist) {
        selectedPrepod = prepodService.getPrepodById(ReservistsAllController.getSelectedPrepodId());

        pibLabel.setText(reservist.getPib());

        vosTextField.setText(reservist.getVos());
        categoryComboBox.setValue(reservist.getCategory());
        groupComboBox.setValue(reservist.getVGrupa());
        vSkladComboBox.setValue(reservist.getVSklad());

        if (reservist.getRank() != null)
            rankComboBox.setValue(reservist.getRank());
        else
            rankComboBox.getSelectionModel().selectFirst();

        validityComboBox.setValue(reservist.getVPrydatnist());
        voenkomatTextField.setText(reservist.getTrc());

        if (reservist.getFamilyState() != null)
            familyStanTextField.setText(reservist.getFamilyState());
        else
            familyStanTextField.setText("одружений");

        if (reservist.getFamilyState() != null)
            educationTextField.setText(reservist.getEducationLevel());
        else
            educationTextField.setText("повна вища");


//        familyStanTextField.setText(reservist.getFamilyState());
//        educationTextField.setText(reservist.getEducationLevel());
    }

    public void initialize() {
        groupComboBox.getItems().addAll("військовозабов'язаний", "призовник");
        categoryComboBox.getItems().addAll("1", "2");
        vSkladComboBox.getItems().addAll(vSkladService.getAllVSklad()
                .stream().map(VSklad::getSkladName).toList());
        rankComboBox.getItems().addAll(vZvanieService.getAllVZvanie()
                .stream().map(VZvanie::getZvanieName).toList());
        validityComboBox.getItems().addAll("придатний", "обмежено-придатний", "непридатний");

        voenkomatComboBox.getItems().addAll(voenkomatService.getAllVoenkomat()
                .stream().map(Voenkomat::getVoenkomatName).toList());


        // TODO: Заполнение комбобокса в зависимости от пола
        /*
        if (selectedReservist.getGender().equals("муж"))
            familyStateComboBox.getItems().addAll(
                    "одружений",
                    "неодружений",
                    "розлучений",
                    "вдівець");
        else
            familyStateComboBox.getItems().addAll(
                    "заміжня",
                    "незаміжня",
                    "розлучена",
                    "вдова");
         */

        familyStateComboBox.getItems().addAll(
                "одружений",
                "неодружений",
                "заміжня",
                "незаміжня",
                "розлучений",
                "розлучена",
                "вдова",
                "вдівець");

        educationComboBox.getItems().addAll(
                "базова загальна середня",
                "повна загальна середня",
                "професійно-технічна",
                "неповна вища",
                "базова вища",
                "повна вища");
    }

    @FXML
    public void familyStateSelected(ActionEvent actionEvent) {
        familyStanTextField.setText(familyStateComboBox.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void educationLevelSelected(ActionEvent actionEvent) {
        educationTextField.setText(educationComboBox.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void tckSelected(ActionEvent actionEvent) {
        voenkomatTextField.setText(voenkomatComboBox.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void closeEdit(ActionEvent actionEvent) {
        MilitaryOblikKhPIMain.showPreviousStage(mainStage, currentStage);
        mainController.updateForm();

    }

    private boolean validateMilitaryRegistrationInfo(String vos, String category, String group, String sklad, String zvanie,
                                                     String prydatnist, String voenkomat, String familyStan, String osvita) {
        Pattern onlyNumber = Pattern.compile("^\\d+$");
        Pattern ukrWords = Pattern.compile("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії,.\\-`'_\\s]*$");

        TextFieldValidator vosValidator = new TextFieldValidator(6, true, onlyNumber, "ВОС", vos, "повинен містити тільки 1 число з 6 цифр");
        TextFieldValidator categoryValidator = new TextFieldValidator(1, true, onlyNumber, "Категорія обліку", category, null);
//        TextFieldValidator groupValidator = new TextFieldValidator(-1, true, ukrWords, "Група обліку", group, null);
//        TextFieldValidator skladValidator = new TextFieldValidator(-1, true, ukrWords, "Склад", sklad, null);
//        TextFieldValidator zvanieValidator = new TextFieldValidator(-1, true, ukrWords, "Військове звання", zvanie, null);
//        TextFieldValidator prydatnistValidator = new TextFieldValidator(-1, true, ukrWords, "Придатність до військової служби", prydatnist, null);
        TextFieldValidator voenkomatValidator = new TextFieldValidator(-1, true, ukrWords, "ТРЦ", voenkomat, "може містити тільки українські літери та розділові знаки");
        TextFieldValidator familyStanValidator = new TextFieldValidator(-1, true, ukrWords, "Сімейний стан", familyStan, "може містити тільки українські літери та розділові знаки");
        TextFieldValidator osvitaValidator = new TextFieldValidator(-1, true, ukrWords, "Освіта", osvita, "може містити тільки українські літери та розділові знаки");

        try {
            vosValidator.validate();

            categoryValidator.validate();
//            groupValidator.validate();
//            skladValidator.validate();
//            zvanieValidator.validate();
//            prydatnistValidator.validate();

            voenkomatValidator.validate();
            familyStanValidator.validate();
            osvitaValidator.validate();

            if (voenkomatService.getIDVoenkomatByName(voenkomat) == null) {
                Voenkomat newVoenkomat = new Voenkomat();
                newVoenkomat.setVoenkomatName(voenkomat);
                voenkomatService.createVoenkomat(newVoenkomat);
            }

        } catch (Exception e) {
            Popup.wrongInputAlert(e.getMessage());
            return false;
        }
        return true;
    }

    @FXML
    public void saveMilitaryRegistrationInfo(ActionEvent actionEvent) {
        String vos = vosTextField.getText().trim();
        String category = categoryComboBox.getValue() != null ? categoryComboBox.getValue() : null;
        String group = groupComboBox.getValue() != null ? groupComboBox.getValue() : null;
        String vSklad = vSkladComboBox.getValue() != null ? vSkladComboBox.getValue() : null;
        String vZvanie = rankComboBox.getValue() != null ? rankComboBox.getValue() : null;
        String prydatnist = validityComboBox.getValue() != null ? validityComboBox.getValue() : null;
        String voenkomat = voenkomatTextField.getText().trim();
        String familyState = familyStanTextField.getText().trim();
        String educationLevel = educationTextField.getText().trim();

        if (!validateMilitaryRegistrationInfo(vos, category, group, vSklad,
                vZvanie, prydatnist, voenkomat, familyState, educationLevel) || !Popup.saveConfirmation())
            return;


        try {
            MilitaryPerson militaryPerson = militaryPersonService.getMilitaryPersonByPrepod(selectedPrepod);

            militaryPerson.setPrepod(selectedPrepod);
            militaryPerson.setVos(vos);
            militaryPerson.setVCategory(Integer.parseInt(category));
            militaryPerson.setVGrupa(group);
            militaryPerson.setVSklad(vSkladService.getVSkladByName(vSklad));
            militaryPerson.setVZvanie(vZvanieService.getVzvanieByName(vZvanie));
            militaryPerson.setVPrydatnist(prydatnist);
            militaryPerson.setVoenkomat(voenkomatService.getVoenkomatByName(voenkomat));
            militaryPerson.setFamilyState(familyState);
            militaryPerson.setEducationLevel(educationLevel);

            militaryPersonService.updateMilitaryPerson(militaryPerson.getId(), militaryPerson);

            closeEdit(null);
            Popup.successSave();
        } catch (Exception e) {
            e.printStackTrace();
            Popup.internalAlert(e.getMessage());
        }
    }
}
