package ntukhpi.semit.militaryoblik.javafxview;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;
import ntukhpi.semit.militaryoblik.adapters.MilitaryPersonAdapter;
import ntukhpi.semit.militaryoblik.adapters.PrepodAdapter;
import ntukhpi.semit.militaryoblik.adapters.ReservistAdapter;
import ntukhpi.semit.militaryoblik.entity.*;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.javafxutils.ControlledScene;
import ntukhpi.semit.militaryoblik.javafxutils.Popup;
import ntukhpi.semit.militaryoblik.javafxutils.validators.MilitaryRegistrationValidator;
import ntukhpi.semit.militaryoblik.javafxutils.validators.exceptions.VoenkomatNotFoundException;
import ntukhpi.semit.militaryoblik.service.*;
import ntukhpi.semit.militaryoblik.service.entitycrud.EmployeeCRUD;
import ntukhpi.semit.militaryoblik.service.entitycrud.MilitaryRegistrationCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    public TextField validityTextField;
    @FXML
    public TextField vinTextField;
    @FXML
    public ComboBox<String> educationComboBox;
    @FXML
    public ComboBox<String> familyStateComboBox;
    @FXML
    public ComboBox<String> voenkomatComboBox;
    @FXML
    public TextArea validityTextArea;

    @Autowired
    MilitaryRegistrationValidator militaryRegistrationValidator;

    @Autowired
    MilitaryRegistrationCRUD militaryRegistrationCRUD;

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

    @Autowired
    EmployeeCRUD employeeCRUD;


    private ReservistsAllController mainController;
    private Prepod selectedPrepod;


    private Stage mainStage;
    private Stage currentStage;

    private PrepodAdapter newPrepod;
    public void setNewPrepod(PrepodAdapter newPrepod){
        this.newPrepod = newPrepod;
    }
    public PrepodAdapter getNewPrepod(){
        return newPrepod;
    }

    @Override
    public void setMainController(Object mainController) {
        this.mainController = (ReservistsAllController) mainController;
    }

    @Override
    public void setData(Object data) {
        if (data instanceof ReservistAdapter)
            setMilitaryRegistrationInfo((ReservistAdapter) data);
        //DDE
        if (data instanceof PrepodAdapter) {
            setNewPrepod((PrepodAdapter) data);
            setMilitaryRegistrationInfo(newPrepod);
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

    private void setMilitaryRegistrationInfo(ReservistAdapter reservist) {
        selectedPrepod = prepodService.getPrepodById(ReservistsAllController.getSelectedPrepodId());

        pibLabel.setText(reservist.getPib());

        vosTextField.setText(reservist.getVos());
        categoryComboBox.setValue(reservist.getCategory());
        groupComboBox.setValue(reservist.getVGrupa());
        vSkladComboBox.setValue(reservist.getVSklad());
        vinTextField.setText(reservist.getVin());

        if (reservist.getRank() != null)
            rankComboBox.setValue(reservist.getRank());
        else
            rankComboBox.getSelectionModel().selectFirst();

        if (reservist.getVPrydatnist() != null)
            validityTextArea.setText(reservist.getVPrydatnist()); // validityTextField.setText(reservist.getVPrydatnist());
        else
            validityTextArea.setText("придатний"); // validityTextField.setText("придатний");

        voenkomatTextField.setText(reservist.getTrc());

        if (reservist.getFamilyState() != null)
            familyStanTextField.setText(reservist.getFamilyState());
        else
            familyStanTextField.setText("неодружений");

        if (reservist.getFamilyState() != null)
            educationTextField.setText(reservist.getEducationLevel());
        else
            educationTextField.setText("повна вища");
    }

    //Перегрузка для нового препода
    private void setMilitaryRegistrationInfo(PrepodAdapter newPrepod) {
        //selectedPrepod = prepodService.getPrepodById(ReservistsAllController.getSelectedPrepodId());

        ReservistAdapter reservist = new ReservistAdapter(militaryRegistrationCRUD.getMilitaryPersonByPrepod(newPrepod));
        pibLabel.setText(reservist.getPib());

        vosTextField.setText(reservist.getVos());
        categoryComboBox.setValue(reservist.getCategory());
        groupComboBox.setValue(reservist.getVGrupa());
        vSkladComboBox.setValue(reservist.getVSklad());
        vinTextField.setText(reservist.getVin());

        if (reservist.getRank() != null)
            rankComboBox.setValue(reservist.getRank());
        else
            rankComboBox.getSelectionModel().selectFirst();

        if (reservist.getVPrydatnist() != null)
            validityTextArea.setText(reservist.getVPrydatnist()); // validityTextField.setText(reservist.getVPrydatnist());
        else
            validityTextArea.setText("придатний"); // validityTextField.setText("придатний");

        voenkomatTextField.setText(reservist.getTrc());

        if (reservist.getFamilyState() != null)
            familyStanTextField.setText(reservist.getFamilyState());
        else
            familyStanTextField.setText("неодружений");

        if (reservist.getFamilyState() != null)
            educationTextField.setText(reservist.getEducationLevel());
        else
            educationTextField.setText("повна вища");
    }

    public void initialize() {
        groupComboBox.getItems().addAll("військовозабов'язаний", "призовник");
        categoryComboBox.getItems().addAll("1", "2");
        vSkladComboBox.getItems().addAll(vSkladService.getAllVSklad()
                .stream().map(VSklad::getSkladName).toList());
        rankComboBox.getItems().addAll(vZvanieService.getAllVZvanie()
                .stream().map(VZvanie::getZvanieName).toList());
        validityComboBox.getItems().addAll("придатний", "обмежено-придатний", "непридатний");

        // validityTextArea.setWrapText(true);

        voenkomatComboBox.getItems().addAll(voenkomatService.getAllVoenkomat()
                .stream().map(Voenkomat::getVoenkomatName).toList());


        // Заполнение комбобокса в зависимости от пола
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
    public void validitySelected(ActionEvent actionEvent) {
        //validityTextField.setText(validityComboBox.getSelectionModel().getSelectedItem());
        validityTextArea.setText(validityComboBox.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void closeEdit(ActionEvent actionEvent) {
        //Якщо додається новий запис, то треба недовведений запис ВИЛУЧИТИ!!!
        //Вона має бути введена повністю
        if (newPrepod!=null) {
            employeeCRUD.deleteEmployee(newPrepod); //***
        }
        mainController.updateForm();
        MilitaryOblikKhPIMain.showPreviousStage(mainStage, currentStage);
    }


    @FXML
    public void saveMilitaryRegistrationInfo(ActionEvent actionEvent) {
        String vos = vosTextField.getText().trim();
        String category = categoryComboBox.getValue() != null ? categoryComboBox.getValue() : null;
        String group = groupComboBox.getValue() != null ? groupComboBox.getValue() : null;
        String vSklad = vSkladComboBox.getValue() != null ? vSkladComboBox.getValue() : null;
        String vZvanie = rankComboBox.getValue() != null ? rankComboBox.getValue() : null;
        String prydatnist = validityTextArea.getText().trim(); // validityTextField.getText().trim();
        String voenkomat = voenkomatTextField.getText().trim();
        String familyState = familyStanTextField.getText().trim();
        String educationLevel = educationTextField.getText().trim();
        String vin = vinTextField.getText().trim();

        try {
            militaryRegistrationValidator.validate(new MilitaryPersonAdapter(null, vos, category,
                                                    group, vSklad, vZvanie, voenkomat,
                                                null, prydatnist, familyState, educationLevel, vin));
        } catch (VoenkomatNotFoundException ignored) {
            if (!Popup.saveConfirmation())
                return;
        } catch (Exception e) {
            Popup.wrongInputAlert(e.getMessage());
            return;
        }

//            Винесено в VoenkomatCRUD
//            Voenkomat newVoenkomat = new Voenkomat();
//            newVoenkomat.setVoenkomatName(voenkomat);
//            voenkomatService.createVoenkomat(newVoenkomat);

        try {
//            MilitaryPerson militaryPerson = militaryPersonService.getMilitaryPersonByPrepod(selectedPrepod);
//
//            militaryPerson.setPrepod(selectedPrepod);
//            militaryPerson.setVos(vos);
//            militaryPerson.setVCategory(Integer.parseInt(category));
//            militaryPerson.setVGrupa(group);
//            militaryPerson.setVSklad(vSkladService.getVSkladByName(vSklad));
//            militaryPerson.setVZvanie(vZvanieService.getVzvanieByName(vZvanie));
//            militaryPerson.setVPrydatnist(prydatnist);
//            militaryPerson.setVoenkomat(voenkomatService.getVoenkomatByName(voenkomat));
//            militaryPerson.setFamilyState(familyState);
//            militaryPerson.setEducationLevel(educationLevel);
//
//            militaryPersonService.updateMilitaryPerson(militaryPerson.getId(), militaryPerson);

            //NewPrepod - це запис про препода, який створюється в цій програмі.
            //Відразу після стоврення йому необхідно ввести дані його обліку.
            //Якщо військово-облікова інформація введена не буде, цей запис відразу вилучається із бази!
            if (newPrepod == null) {
                militaryRegistrationCRUD.updateMilitaryPerson(selectedPrepod.getId(),
                        new MilitaryPersonAdapter(null, vos, category,
                                group, vSklad, vZvanie,
                                voenkomat, null, prydatnist,
                                familyState, educationLevel, vin));
            } else {
                militaryRegistrationCRUD.updateMilitaryPerson(employeeCRUD.adapterPrepodToPrepod(newPrepod).getId(),
                        new MilitaryPersonAdapter(null, vos, category,
                                group, vSklad, vZvanie,
                                voenkomat, null, prydatnist,
                                familyState, educationLevel, vin));
                //???
                MilitaryPerson mp = militaryPersonService.
                        getMilitaryPersonByPrepod(employeeCRUD.adapterPrepodToPrepod(newPrepod)); //***
                mainController.selectReservist(new ReservistAdapter(mp)); //***
                setNewPrepod(null);
            }

            closeEdit(null);
            Popup.successSave();
        } catch (Exception e) {
            e.printStackTrace();
            Popup.internalAlert(e.getMessage());
        }
    }

    public void hotKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.S && keyEvent.isControlDown())
            saveMilitaryRegistrationInfo(null);
        if (keyEvent.getCode() == KeyCode.ESCAPE)
            closeEdit(null);
    }
}
