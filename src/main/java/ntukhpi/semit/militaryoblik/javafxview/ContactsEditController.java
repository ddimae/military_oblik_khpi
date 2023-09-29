package ntukhpi.semit.militaryoblik.javafxview;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;
import ntukhpi.semit.militaryoblik.adapters.ReservistAdapter;
import ntukhpi.semit.militaryoblik.entity.PersonalData;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Country;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.RegionUkraine;
import ntukhpi.semit.militaryoblik.javafxutils.ControlledScene;
import ntukhpi.semit.militaryoblik.javafxutils.DataFormat;
import ntukhpi.semit.militaryoblik.javafxutils.validators.TextFieldValidator;
import ntukhpi.semit.militaryoblik.javafxutils.Popup;
import ntukhpi.semit.militaryoblik.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.Collator;
import java.util.regex.Pattern;

@Component
public class ContactsEditController implements ControlledScene {

    @Getter
    @Setter
    private class PhoneNumberForm extends TextFieldValidator {
        private String number;
        private boolean isFullNumber;
        private boolean isNoCountryCodeNumber;
        private boolean isNoPlusNumber;
        private boolean isCityNumber;
        private boolean isWrongNumber;

        public PhoneNumberForm(String number) {
            super(-1, false, null, null, null, null);
            this.number = number;
            isFullNumber = false;
            isNoCountryCodeNumber = false;
            isNoPlusNumber = false;
            isCityNumber = false;
            isWrongNumber = false;
        }

        public PhoneNumberForm(String number, int maxLength, boolean isNecessary, String fieldName, String errorMsg) {
            super(maxLength, isNecessary, null, fieldName, number, errorMsg);
            this.number = number;
            isFullNumber = false;
            isNoCountryCodeNumber = false;
            isNoPlusNumber = false;
            isCityNumber = false;
            isWrongNumber = false;
        }

        public void validateNumber(Pattern full, Pattern noCountryCode, Pattern noPlus, Pattern city) throws Exception {
            isFullNumber = false;
            isNoCountryCodeNumber = false;
            isNoPlusNumber = false;
            isCityNumber = false;
            isWrongNumber = false;

            if (full != null && full.matcher(number).matches())
                isFullNumber = true;
            else if (noCountryCode != null && noCountryCode.matcher(number).matches())
                isNoCountryCodeNumber = true;
            else if (noPlus != null && noPlus.matcher(number).matches())
                isNoPlusNumber = true;
            else if (city != null && city.matcher(number).matches())
                isCityNumber = true;
            else {
                isWrongNumber = true;
                throw new Exception(getErrorMsg());
            }

            validate();
        }

        public void setToUkrStandart() {
            if (isNoCountryCodeNumber)
                number = "+38" + number;
            else if (isNoPlusNumber)
                number = "+" + number;
            else if (isCityNumber)
                number = "+38057" + number;
        }

        public void clone(PhoneNumberForm phone) {
            setMaxLength(phone.getMaxLength());
            setNecessary(phone.isNecessary());
            setRegex(phone.getRegex());
            setFieldName(phone.getFieldName());
            setTextField(phone.getTextField());
            setErrorMsg(phone.getErrorMsg());

            isFullNumber = phone.isFullNumber;
            isNoCountryCodeNumber = phone.isNoCountryCodeNumber;
            isNoPlusNumber = phone.isNoPlusNumber;
            isCityNumber = phone.isCityNumber;
            isWrongNumber = phone.isWrongNumber;
        }
    }

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

//        System.out.println(countryComboBox.getValue() + " " + countryFactComboBox.getValue() + "\n" +
//                indexTextField.getText() + " " + indexFactTextField.getText() + "\n" +
//                cityTextField.getText() + " " + cityFactTextField.getText() + "\n" +
//                regionComboBox.getValue() + " " + regionFactComboBox.getValue() + "\n" +
//                addressTextField.getText() + " " + addressFactTextField.getText());
//
//        System.out.println(countryComboBox.getValue() == countryFactComboBox.getValue());
//        System.out.println(indexTextField.getText() == indexFactTextField.getText());
//        System.out.println(cityTextField.getText() == cityFactTextField.getText());
//        System.out.println(regionComboBox.getValue() == regionFactComboBox.getValue());
//        System.out.println(addressTextField.getText() == addressFactTextField.getText());

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

    private void blockRegistrationCountry(String value) {
        countryComboBox.setValue(value);
        countryComboBox.setDisable(true);
    }

    private boolean validateInfo(String country, String index, String city,
                                 String region, String address, PhoneNumberForm mainPhone,
                                 PhoneNumberForm secondPhone, String countryFact, String indexFact,
                                 String cityFact, String regionFact, String addressFact,
                                 boolean isForeinNumber) {
        Pattern ukrIndexRegex = Pattern.compile("(\\d{5})?");
        Pattern ukrPhoneFullRegex = Pattern.compile("^(\\+\\d{12})?$");
        Pattern ukrPhoneNoCountryCodeRegex = Pattern.compile("^(\\d{10})?$");
        Pattern ukrPhoneNoPlusRegex = Pattern.compile("^(\\d{12})?$");
        Pattern ukrPhoneCityRegex = Pattern.compile("^(\\d{7})?$");
        Pattern foreinPhoneRegex = Pattern.compile("(^\\+\\d+)?");
        Pattern cityRegex = Pattern.compile("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії,.\\-`'_\\s]*$");
        Pattern regionRegex = Pattern.compile("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії,.\\s]*$");
        Pattern addressRegex = Pattern.compile("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії\\d,.\\-`'\\&_\\s]*$");

        TextFieldValidator countryForm = new TextFieldValidator(-1, true, null, "Країна", country, null);
        TextFieldValidator indexForm = new TextFieldValidator(10, false, String.valueOf(country).equals("Україна")?ukrIndexRegex:null, "Індекс", index, "повинно складатися із 5 цифр");
        TextFieldValidator cityForm = new TextFieldValidator(30, true, cityRegex, "Місто", city, "повинно містити тільки українські літери та розділові знаки");
        TextFieldValidator regionForm = new TextFieldValidator(255, false, null, "Область", region, null);
        TextFieldValidator addressForm = new TextFieldValidator(255, true, addressRegex, "Адресса", address, "може містити українські літери, цифри та розділові знаки");
        PhoneNumberForm mainPhoneForm = new PhoneNumberForm(mainPhone.getNumber(), 13, true, "Телефон 1", "повинно мати форму: +380951203066, 0951203066, 380951203066 або 7076845");
        PhoneNumberForm secondPhoneForm = new PhoneNumberForm(secondPhone.getNumber(), 13, false, "Телефон 2", "повинно мати форму: +380951203066, 0951203066, 380951203066, або 7076845");

        TextFieldValidator countryFactForm = new TextFieldValidator(-1, false, null, "Країна", countryFact, null);
        TextFieldValidator indexFactForm = new TextFieldValidator(10, false, String.valueOf(countryFact).equals("Україна")?ukrIndexRegex:null, "Індекс", indexFact, "повинно складатися із 5 цифр");
        TextFieldValidator cityFactForm = new TextFieldValidator(30, false, cityRegex, "Місто", cityFact, "повинно містити тільки українські літери та розділові знаки");
        TextFieldValidator regionFactForm = new TextFieldValidator(255, false, null, "Область", regionFact, null);
        TextFieldValidator addressFactForm = new TextFieldValidator(255, false, addressRegex, "Адресса", addressFact, "може містити українські літери, цифри та розділові знаки");

        try {
            countryForm.validate();
            countryFactForm.validate();
            indexForm.validate();
            indexFactForm.validate();
            cityForm.validate();
            cityFactForm.validate();
            regionForm.validate();
            regionFactForm.validate();
            addressForm.validate();
            addressFactForm.validate();
            if (isForeinNumber) {
                mainPhoneForm.setErrorMsg("має іноземний формат та повинен починатися зі знаку '+'");
                secondPhoneForm.setErrorMsg("має іноземний формат та повинен починатися зі знаку '+'");

                mainPhoneForm.validateNumber(foreinPhoneRegex, null, null, null);
                secondPhoneForm.validateNumber(foreinPhoneRegex, null, null, null);
            } else {
                mainPhoneForm.validateNumber(ukrPhoneFullRegex, ukrPhoneNoCountryCodeRegex, ukrPhoneNoPlusRegex, ukrPhoneCityRegex);
                secondPhoneForm.validateNumber(ukrPhoneFullRegex, ukrPhoneNoCountryCodeRegex, ukrPhoneNoPlusRegex, ukrPhoneCityRegex);
            }
            mainPhone.clone(mainPhoneForm);
            secondPhone.clone(secondPhoneForm);


        } catch (Exception e) {
            Popup.wrongInputAlert(e.getMessage());

            return false;
        }

        return true;
    }

    @FXML
    void saveContactInfo(ActionEvent event) throws Exception {
        String country = DataFormat.getPureValue(countryComboBox.getValue());
        String index = indexTextField.getText() != null ? indexTextField.getText().trim() : "";
        String city = cityTextField.getText() != null ? cityTextField.getText().trim() : "";
        String region = DataFormat.getPureValue(regionComboBox.getValue());
        String address = addressTextField.getText() != null ? addressTextField.getText().trim() : "";
        PhoneNumberForm mainPhone = new PhoneNumberForm(mainPhoneTextField.getText() != null ? mainPhoneTextField.getText().trim() : "") ;
        PhoneNumberForm secondPhone = new PhoneNumberForm(secondPhoneTextField.getText() != null ? secondPhoneTextField.getText().trim() : "");

        String countryFact = DataFormat.getPureValue(countryFactComboBox.getValue());
        String indexFact = indexFactTextField.getText() != null ? indexFactTextField.getText().trim() : "";
        String cityFact = cityFactTextField.getText() != null ? cityFactTextField.getText().trim() : "";
        String regionFact = DataFormat.getPureValue(regionFactComboBox.getValue());
        String addressFact = addressFactTextField.getText() != null ? addressFactTextField.getText().trim() : "";

        boolean isUkraine = String.valueOf(country).equals("Україна");
        boolean isUkraineFact = String.valueOf(countryFact).equals("Україна");
        boolean isForeinNumber = foreinNumberRadioButton.isSelected();

        if (!validateInfo(country, index, city, region,
                address, mainPhone, secondPhone,
                countryFact, indexFact, cityFact,
                regionFact, addressFact, isForeinNumber))
            return;

        if (!isForeinNumber) {
            mainPhone.setToUkrStandart();
            secondPhone.setToUkrStandart();
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

    @FXML
    void handleForeinNumberRadioButton(ActionEvent event) {

    }

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

    @FXML
    void closeEdit(ActionEvent event) {
        MilitaryOblikKhPIMain.showPreviousStage(mainStage, currentStage);
    }
}
