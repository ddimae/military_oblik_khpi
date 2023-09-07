package ntukhpi.semit.militaryoblik.javafxview;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import ntukhpi.semit.militaryoblik.javafxutils.FormTextInput;
import ntukhpi.semit.militaryoblik.javafxutils.Popup;
import ntukhpi.semit.militaryoblik.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class ContactInfoEditController implements ControlledScene {

    @Getter
    @Setter
    private class PhoneNumberForm extends FormTextInput {
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

    private void setContactInfo(ReservistAdapter reservist) {
        selectedReservist = reservist;
        personalData = personalDataService.getPersonalDataById(selectedReservist.getId());
        //Якщо немає звязаної інформації, то запускати треба щось на установку нової!!!

        pibText.setText(DataFormat.getPIB(prepodService.getPrepodById(selectedReservist.getId())));

        if (personalData == null) {
            personalData = new PersonalData();
            return;
        }

        Country country = personalData.getCountry();
        Country countryFact = personalData.getCountry_fact();
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

        handleChangeCountry(null);
    }

    public void initialize() {
        List<String> countryList = new ArrayList<>();
        List<String> regionList = new ArrayList<>();

        countryList.add("Не визначено");
        regionList.add("Не визначено");

        countryList.addAll(countryService.getAllCountry().stream().map(Country::getCountryName).toList());
        regionList.addAll(regionUkraineService.getAllRegionUkraine().stream().map(RegionUkraine::getCountryName).toList());

        ObservableList<String> countryObservableList = FXCollections.observableArrayList(countryList);
        ObservableList<String> regionObservableList = FXCollections.observableArrayList(regionList);

        countryComboBox.setItems(countryObservableList);
        countryFactComboBox.setItems(countryObservableList);

        regionComboBox.setItems(regionObservableList);
        regionFactComboBox.setItems(regionObservableList);

        handleChangeCountry(null);
    }

    @FXML
    void closeEdit(ActionEvent event) {
        try {
            ((Stage) countryComboBox.getScene().getWindow()).close();
            MilitaryOblikKhPIMain.showReservistsWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateInfo(String country, String index, String city,
                                 String region, String address, PhoneNumberForm mainPhone,
                                 PhoneNumberForm secondPhone, String countryFact, String indexFact,
                                 String cityFact, String regionFact, String addressFact,
                                 boolean isForeinNumber) throws Exception {
        Pattern ukrIndexRegex = Pattern.compile("(\\d{5})?");
        Pattern ukrPhoneFullRegex = Pattern.compile("^(\\+\\d{12})?$");
        Pattern ukrPhoneNoCountryCodeRegex = Pattern.compile("^(\\d{10})?$");
        Pattern ukrPhoneNoPlusRegex = Pattern.compile("^(\\d{12})?$");
        Pattern ukrPhoneCityRegex = Pattern.compile("^(\\d{7})?$");
        Pattern foreinPhoneRegex = Pattern.compile("(^\\+\\d+)?");
        Pattern cityRegex = Pattern.compile("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії\\s]*$");
        Pattern regionRegex = Pattern.compile("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії,.\\s]*$");
        Pattern addressRegex = Pattern.compile("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії\\d,.\\-\\'\\&_\\s]*$");

        FormTextInput countryForm = new FormTextInput(30, true, null, "Країна", country, null);
        FormTextInput indexForm = new FormTextInput(10, false, String.valueOf(country).equals("Україна")?ukrIndexRegex:null, "Індекс", index, "Індекс повинен складатися із 5 цифр");
        FormTextInput cityForm = new FormTextInput(30, true, cityRegex, "Місто", city, "Назва міста повинна містити тільки українські літери");
        FormTextInput regionForm = new FormTextInput(255, false, null, "Область", region, null);
        FormTextInput addressForm = new FormTextInput(255, true, addressRegex, "Адресса", address, "Адресса може містити українські літери, цифри та розділові знаки");
        PhoneNumberForm mainPhoneForm = new PhoneNumberForm(mainPhone.getNumber(), 13, true, "Телефон 1", "Формат телефона повинен виглядати +380951203066, 0951203066, 380951203066, або 7076845");
        PhoneNumberForm secondPhoneForm = new PhoneNumberForm(secondPhone.getNumber(), 13, false, "Телефон 2", "Формат телефона повинен виглядати +380951203066, 0951203066, 380951203066, або 7076845");

        FormTextInput countryFactForm = new FormTextInput(30, false, null, "Країна", countryFact, null);
        FormTextInput indexFactForm = new FormTextInput(10, false, String.valueOf(countryFact).equals("Україна")?ukrIndexRegex:null, "Індекс", indexFact, "Індекс повинен складатися із 5 цифр");
        FormTextInput cityFactForm = new FormTextInput(30, false, cityRegex, "Місто", cityFact, "Назва міста повинна містити тільки українські літери");
        FormTextInput regionFactForm = new FormTextInput(255, false, null, "Область", regionFact, null);
        FormTextInput addressFactForm = new FormTextInput(255, false, addressRegex, "Адресса", addressFact, "Адресса може містити українські літери, цифри та розділові знаки");

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
                mainPhoneForm.setErrorMsg("Формат іноземного телефона повинен починатися зі знаку '+'");
                secondPhoneForm.setErrorMsg("Формат іноземного телефона повинен починатися зі знаку '+'");

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
        String country = DataFormat.getPureValue(countryComboBox);
        String index = indexTextField.getText();
        String city = cityTextField.getText();
        String region = DataFormat.getPureValue(regionComboBox);
        String address = addressTextField.getText();
        PhoneNumberForm mainPhone = new PhoneNumberForm(mainPhoneTextField.getText());
        PhoneNumberForm secondPhone = new PhoneNumberForm(secondPhoneTextField.getText());

        String countryFact = DataFormat.getPureValue(countryFactComboBox);
        String indexFact = indexFactTextField.getText();
        String cityFact = cityFactTextField.getText();
        String regionFact = DataFormat.getPureValue(regionFactComboBox);
        String addressFact = addressFactTextField.getText();

        boolean isUkraine = String.valueOf(country).equals("Україна");
        boolean isUkraineFact = String.valueOf(countryFact).equals("Україна");
        boolean isForeinNumber = foreinNumberRadioButton.isSelected();

        city = city.trim();
        address = address.trim();

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
                personalData.setCountry_fact(countryService.getCountryByName(country));
                personalData.setFactPostIndex(index);
                personalData.setFactCity(city);
                personalData.setFactRowAddress(address);
                if (isUkraine && regionComboBox.getValue() != null)
                    personalData.setFactOblastUA(regionUkraineService.getRegionUkraineByName(region));
                else
                    personalData.setFactOblastUA(null);
            } else {
                personalData.setCountry_fact(countryService.getCountryByName(countryFact));
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
            Popup.wrongInputAlert(e.getMessage());
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

//            countryFactComboBox.setValue(countryComboBox.getValue());
//            indexFactTextArea.setText(indexTextArea.getText());
//            cityFactTextArea.setText(cityTextArea.getText());
//            regionFactTextArea.setText(regionTextArea.getText());
//            addressFactTextArea.setText(addressTextArea.getText());

//            countryFactComboBox.setValue("");
//            indexFactTextArea.setText("");
//            cityFactTextArea.setText("");
//            regionFactTextArea.setText("");
//            addressFactTextArea.setText("");
        } else {
            countryFactComboBox.setDisable(false);
            indexFactTextField.setDisable(false);
            cityFactTextField.setDisable(false);
            regionFactComboBox.setDisable(false);
            addressFactTextField.setDisable(false);
        }

        handleChangeCountry(null);
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

        if (String.valueOf(countryFactComboBox.getValue()).equals("Україна")) {
            regionFactComboBox.setDisable(false);
        } else {
            regionFactComboBox.setDisable(true);
        }
    }
}
