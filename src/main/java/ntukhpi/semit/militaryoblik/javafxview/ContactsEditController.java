package ntukhpi.semit.militaryoblik.javafxview;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;
import ntukhpi.semit.militaryoblik.adapters.ContactInfoAdapter;
import ntukhpi.semit.militaryoblik.adapters.ReservistAdapter;
import ntukhpi.semit.militaryoblik.entity.PersonalData;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Country;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.RegionUkraine;
import ntukhpi.semit.militaryoblik.javafxutils.ControlledScene;
import ntukhpi.semit.militaryoblik.javafxutils.DataFormat;
import ntukhpi.semit.militaryoblik.javafxutils.validators.ContactInfoValidator;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.PhoneNumberValidator;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.TextFieldValidator;
import ntukhpi.semit.militaryoblik.javafxutils.Popup;
import ntukhpi.semit.militaryoblik.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Console;
import java.text.Collator;
import java.util.regex.Pattern;


/**
 * Контролер форми редагування контактної інформації резервіста
 *
 * @author Степанов Михайло
 */
@Component
public class ContactsEditController implements ControlledScene {

    @FXML
    private TextField addressFactTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField cityFactTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private ComboBox<String> countryComboBox;

    @FXML
    private ComboBox<String> countryFactComboBox;

    @FXML
    private RadioButton equalRadioButton;

    @FXML
    private RadioButton foreinNumberRadioButton;

    @FXML
    private TextField indexFactTextField;

    @FXML
    private TextField indexTextField;

    @FXML
    private TextField mainPhoneTextField;

    @FXML
    private Text pibText;

    @FXML
    private ComboBox<String> regionFactComboBox;

    @FXML
    private ComboBox<String> regionComboBox;

    @FXML
    private TextField secondPhoneTextField;

    private ReservistsAllController mainController;
    private ReservistAdapter selectedReservist;
    private PersonalData personalData;
    private Stage mainStage;
    private Stage currentStage;

    @Autowired
    ContactInfoValidator contactInfoValidator;

    @Autowired
    CountryServiceImpl countryService;

    @Autowired
    RegionUkraineServiceImpl regionUkraineService;

    @Autowired
    PersonalDataServiceImpl personalDataService;

    @Autowired
    PrepodServiceImpl prepodService;


    @Override
    public void setMainController(Object mainController) {
        this.mainController = (ReservistsAllController) mainController;
    }

    @Override
    public void setData(Object data) {
        if (data instanceof ReservistAdapter)
            setContactInfo((ReservistAdapter) data);
    }

    @Override
    public void setMainStage(Stage stage) {
        mainStage = stage;
    }

    @Override
    public void setCurrentStage(Stage stage) {
        currentStage = stage;
    }


    /**
     * Заповнює форму даними обраного резервіста
     *
     * @param reservist Інформація про обраного резервіста
     */
    private void setContactInfo(ReservistAdapter reservist) {
        selectedReservist = reservist;
        personalData = personalDataService.getPersonalDataByPrepodId(selectedReservist.getId());
        //Якщо немає звязаної інформації, то запускати треба щось на установку нової!!!

        pibText.setText(DataFormat.getPIB(prepodService.getPrepodById(selectedReservist.getId())));
        if (personalData == null) {
            personalData = new PersonalData();
            return;
        }

        Country country = personalData.getCountry();
        Country countryFact = personalData.getFactСountry();
        RegionUkraine region = personalData.getOblastUA();
        RegionUkraine regionFact = personalData.getFactOblastUA();

        if (country != null)
            countryComboBox.setValue(country.getCountryName());
        if (countryFact != null)
            countryFactComboBox.setValue(countryFact.getCountryName());

        indexTextField.setText(personalData.getPostIndex());
        indexFactTextField.setText(personalData.getFactPostIndex());

        cityTextField.setText(personalData.getCity());
        cityFactTextField.setText(personalData.getFactCity());

        if (region != null)
            regionComboBox.setValue(region.getCountryName());
        if (regionFact != null)
            regionFactComboBox.setValue(regionFact.getCountryName());

        addressTextField.setText(personalData.getRowAddress());
        addressFactTextField.setText(personalData.getFactRowAddress());

        mainPhoneTextField.setText(personalData.getPhoneMain());
        secondPhoneTextField.setText(personalData.getPhoneDop());

        if (((countryComboBox.getValue() != null && countryComboBox.getValue().equals(countryFactComboBox.getValue())) || countryComboBox.getValue() == countryFactComboBox.getValue()) &&
            ((indexTextField.getText() != null && indexTextField.getText().equals(indexFactTextField.getText())) || indexTextField.getText() == indexFactTextField.getText()) &&
            ((cityTextField.getText() != null && cityTextField.getText().equals(cityFactTextField.getText())) || cityTextField.getText() == cityFactTextField.getText()) &&
            ((regionComboBox.getValue() != null && regionComboBox.getValue().equals(regionFactComboBox.getValue())) || regionComboBox.getValue() == regionFactComboBox.getValue()) &&
            ((addressTextField.getText() != null && addressTextField.getText().equals(addressFactTextField.getText()))) || addressTextField.getText() == addressFactTextField.getText()) {
                equalRadioButton.setSelected(true);
                handleEqualRadioButton(null);
        }

        handleChangeCountry(null);
    }


    /**
     * Початкова ініціалізація комбобоксів та стану кнопок
     */
    public void initialize() {
        Collator ukrCollator = DataFormat.getUkrCollator();

        countryComboBox.getItems().add("Не визначено");
        countryComboBox.getItems().addAll(countryService.getAllCountry().stream().map(Country::toString).sorted(ukrCollator).toList());
        countryComboBox.getItems().remove("Україна");
        countryComboBox.getItems().add(1, "Україна");
        countryFactComboBox.setItems(countryComboBox.getItems());

        regionComboBox.getItems().add("Не визначено");
        regionComboBox.getItems().addAll(regionUkraineService.getAllRegionUkraine().stream().map(RegionUkraine::toString).sorted(ukrCollator).toList());
        regionFactComboBox.setItems(regionComboBox.getItems());

        blockRegistrationCountry("Україна");
        handleChangeCountry(null);
    }


    /**
     * Блокування комбобоксу вибору країни реєстрації
     *
     * @param value Значення комбобоксу країни реєстрації
     */
    private void blockRegistrationCountry(String value) {
        countryComboBox.setValue(value);
        countryComboBox.setDisable(true);
    }


    /**
     * Спроба збереження/редагування даних форми в БД після валідації
     */
    @FXML
    void saveContactInfo(ActionEvent event) {
        String country = DataFormat.getPureValue(countryComboBox.getValue());
        String index = indexTextField.getText() != null ? indexTextField.getText().trim() : "";
        String city = cityTextField.getText() != null ? cityTextField.getText().trim() : "";
        String region = DataFormat.getPureValue(regionComboBox.getValue());
        String address = addressTextField.getText() != null ? addressTextField.getText().trim() : "";
        PhoneNumberValidator mainPhone = new PhoneNumberValidator(mainPhoneTextField.getText() != null ? mainPhoneTextField.getText().trim() : "") ;
        PhoneNumberValidator secondPhone = new PhoneNumberValidator(secondPhoneTextField.getText() != null ? secondPhoneTextField.getText().trim() : "");

        String countryFact = DataFormat.getPureValue(countryFactComboBox.getValue());
        String indexFact = indexFactTextField.getText() != null ? indexFactTextField.getText().trim() : "";
        String cityFact = cityFactTextField.getText() != null ? cityFactTextField.getText().trim() : "";
        String regionFact = DataFormat.getPureValue(regionFactComboBox.getValue());
        String addressFact = addressFactTextField.getText() != null ? addressFactTextField.getText().trim() : "";

        boolean isUkraine = String.valueOf(country).equals("Україна");
        boolean isUkraineFact = String.valueOf(countryFact).equals("Україна");
        boolean isForeinNumber = foreinNumberRadioButton.isSelected();

        try {
            contactInfoValidator.validate(new ContactInfoAdapter(country, index, city,
                    region, address, mainPhone.getNumber(), secondPhone.getNumber(),
                    countryFact, indexFact, cityFact, regionFact, addressFact, isForeinNumber
            ));
        } catch (Exception e) {
            Popup.wrongInputAlert(e.getMessage());
            return;
        }

        if (!isForeinNumber) {
            mainPhone.clone(contactInfoValidator.getMainPhoneForm().setToUkrStandart());
            secondPhone.clone(contactInfoValidator.getSecondPhoneForm().setToUkrStandart());
        }


        try {
            personalData.setCountry(countryService.getCountryByName(country));
            personalData.setPostIndex(index);
            personalData.setCity(city);
            personalData.setRowAddress(address);
            personalData.setPhoneMain(mainPhone.getNumber());
            personalData.setPhoneDop(secondPhone.getNumber());
            personalData.setPrepod(prepodService.getPrepodById(selectedReservist.getId()));
            if (isUkraine && regionComboBox.getValue() != null)
                personalData.setOblastUA(regionUkraineService.getRegionUkraineByName(region));
            else
                personalData.setOblastUA(null);

            if (equalRadioButton.isSelected()) {
                personalData.setFactСountry(countryService.getCountryByName(country));
                personalData.setFactPostIndex(index);
                personalData.setFactCity(city);
                personalData.setFactRowAddress(address);
                if (isUkraine && regionComboBox.getValue() != null)
                    personalData.setFactOblastUA(regionUkraineService.getRegionUkraineByName(region));
                else
                    personalData.setFactOblastUA(null);
            } else {
                personalData.setFactСountry(countryService.getCountryByName(countryFact));
                personalData.setFactPostIndex(indexFact);
                personalData.setFactCity(cityFact);
                personalData.setFactRowAddress(addressFact);
                if (isUkraineFact && regionFactComboBox.getValue() != null)
                    personalData.setFactOblastUA(regionUkraineService.getRegionUkraineByName(regionFact));
                else
                    personalData.setFactOblastUA(null);
            }

            personalDataService.updatePersonalData(personalData);

            closeEdit(null);
            Popup.successSave();
        } catch (Exception e) {
            e.printStackTrace();
            Popup.internalAlert(e.getMessage());
        }
    }


    /**
     * Обробник зміни стану кнопки рівності даних
     */
    @FXML
    void handleEqualRadioButton(ActionEvent event) {
        if (equalRadioButton.isSelected()) {
            countryFactComboBox.setDisable(true);
            indexFactTextField.setDisable(true);
            cityFactTextField.setDisable(true);
            regionFactComboBox.setDisable(true);
            addressFactTextField.setDisable(true);
        } else {
            countryFactComboBox.setDisable(false);
            indexFactTextField.setDisable(false);
            cityFactTextField.setDisable(false);
            regionFactComboBox.setDisable(false);
            addressFactTextField.setDisable(false);
        }
    }

    /**
     * Обробник зміни стану кнопки іноземного номера
     */
    @FXML
    void handleForeinNumberRadioButton(ActionEvent event) {

    }


    /**
     * Обробник зміни значення комбобоксу країни
     */
    @FXML
    void handleChangeCountry(ActionEvent event) {
        if (String.valueOf(countryComboBox.getValue()).equals("Україна")) {
            foreinNumberRadioButton.setDisable(false);
            foreinNumberRadioButton.setSelected(false);
            regionComboBox.setDisable(false);
        }
        else {
            foreinNumberRadioButton.setDisable(true);
            foreinNumberRadioButton.setSelected(true);
            regionComboBox.setDisable(true);
        }

        if (String.valueOf(countryFactComboBox.getValue()).equals("Україна") && !equalRadioButton.isSelected()) {
            regionFactComboBox.setDisable(false);
        } else {
            regionFactComboBox.setDisable(true);
        }
    }


    /**
     * Перехід до материнської форми
     */
    @FXML
    void closeEdit(ActionEvent event) {
        MilitaryOblikKhPIMain.showPreviousStage(mainStage, currentStage);
    }
}
