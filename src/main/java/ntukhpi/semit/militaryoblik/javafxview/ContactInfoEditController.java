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
    private class PhoneNumber {
        String number;
        boolean isFullNumber;
        boolean isNoCountryCodeNumber;
        boolean isNoPlusNumber;
        boolean isCityNumber;
        boolean isWrongNumber;

        public PhoneNumber(String number) {
            this.number = number;
            isFullNumber = false;
            isNoCountryCodeNumber = false;
            isNoPlusNumber = false;
            isCityNumber = false;
            isWrongNumber = false;
        }

        public void validateNumber(Pattern full, Pattern noCountryCode, Pattern noPlus, Pattern city) {
            if (full != null && full.matcher(number).matches())
                isFullNumber = true;
            else if (noCountryCode != null && noCountryCode.matcher(number).matches())
                isNoCountryCodeNumber = true;
            else if (noPlus != null && noPlus.matcher(number).matches())
                isNoPlusNumber = true;
            else if (city != null && city.matcher(number).matches())
                isCityNumber = true;
            else
                isWrongNumber = true;
        }

        public void setToUkrStandart() {
            if (isNoCountryCodeNumber)
                number = "+38" + number;
            else if (isNoPlusNumber)
                number = "+" + number;
            else if (isCityNumber)
                number = "+38057" + number;
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
                              String region, String address, String mainPhone,
                              String secondPhone, String countyFact, String indexFact,
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
        FormTextInput indexForm = new FormTextInput(10, false, country.equals("Україна")?ukrIndexRegex:null, "Індекс", index, "Індекс повинен складатися із 5 цифр");
        FormTextInput cityForm = new FormTextInput(30, true, cityRegex, "Місто", city, "Назва міста повинна містити тільки українські літери");
        FormTextInput regionForm = new FormTextInput(255, false, null, "Область", region, null);
        FormTextInput addressForm = new FormTextInput(255, true, addressRegex, "Адресса", address, "Адресса може містити українські літери, цифри та розділові знаки");
        PhoneNumber mainPhoneForm = new PhoneNumber(mainPhoneTextField.getText());
        PhoneNumber secondPhoneForm = new PhoneNumber(secondPhoneTextField.getText());

        FormTextInput countryFactForm = new FormTextInput(30, false, null, "Країна", country, null);
        FormTextInput indexFactForm = new FormTextInput(10, false, country.equals("Україна")?ukrIndexRegex:null, "Індекс", index, "Індекс повинен складатися із 5 цифр");
        FormTextInput cityFactForm = new FormTextInput(30, false, cityRegex, "Місто", city, "Назва міста повинна містити тільки українські літери");
        FormTextInput regionFactForm = new FormTextInput(255, false, null, "Область", region, null);
        FormTextInput addressFactForm = new FormTextInput(255, false, addressRegex, "Адресса", address, "Адресса може містити українські літери, цифри та розділові знаки");

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
                mainPhoneForm.validateNumber(ukrPhoneFullRegex, ukrPhoneNoCountryCodeRegex, ukrPhoneNoPlusRegex, ukrPhoneCityRegex);
                secondPhoneForm.validateNumber(ukrPhoneFullRegex, ukrPhoneNoCountryCodeRegex, ukrPhoneNoPlusRegex, ukrPhoneCityRegex);

                if (mainPhoneForm.isWrongNumber || (!secondPhoneForm.getNumber().isEmpty() && secondPhoneForm.isWrongNumber))
                    throw new Exception("Формат телефона повинен виглядати +380951203066, 0951203066, 380951203066, або 7076845");

                mainPhoneForm.setToUkrStandart();
                secondPhoneForm.setToUkrStandart();
            } else {
                mainPhoneForm.validateNumber(foreinPhoneRegex, null, null, null);
                secondPhoneForm.validateNumber(foreinPhoneRegex, null, null, null);

                if (mainPhoneForm.isWrongNumber || (!secondPhoneForm.getNumber().isEmpty() && secondPhoneForm.isWrongNumber))
                    throw new Exception("Формат іноземного телефона повинен починатися зі знаку '+'");
            }
        } catch (Exception e) {
            Popup.wrongInputAlert(e.getMessage());
            return false;
        }

        return true;
    }

    @FXML
    void saveContactInfo(ActionEvent event) throws Exception {
        String country = countryComboBox.getValue();
        String index = indexTextField.getText();
        String city = cityTextField.getText();
        String region = regionComboBox.getValue();
        String address = addressTextField.getText();
        String mainPhone = mainPhoneTextField.getText();
        String secondPhone = secondPhoneTextField.getText();

        String countryFact = String.valueOf(countryFactComboBox.getValue());
        String indexFact = indexFactTextField.getText();
        String cityFact = cityFactTextField.getText();
        String regionFact = regionFactComboBox.getValue();
        String addressFact = addressFactTextField.getText();

        boolean isUkraine = country.equals("Україна");
        boolean isUkraineFact = countryFact.equals("Україна");

        city = city.trim();
        address = address.trim();

        if (!validateInfo(country, index, city, region,
                address, mainPhone, secondPhone,
                countryFact, indexFact, cityFact,
                regionFact, addressFact, foreinNumberRadioButton.isSelected()))
            return;


        try {
//            if (country.equals("null") || country.equals("Не визначено"))
//                throw new Exception("\"Країна реєстрації\" є обов'язковим полем");
//            if (city.isEmpty())
//                throw new Exception("\"Місто реєстрації\" є обов'язковим полем");
//            if (address.isEmpty())
//                throw new Exception("\"Адреса реєстрації\" є обов'язковим полем");
//            if (mainPhone.getNumber().isEmpty())
//                throw new Exception("\"Телефон 1\" є обов'язковим полем");
//
//            if ((isUkraine && !ukrIndexRegex.matcher(index).matches()) || ((isUkraineFact && !ukrIndexRegex.matcher(indexFact).matches())))
//                throw new Exception("Індекс повинен складатися із 5 цифр");
//
//            if (!foreinNumberRadioButton.isSelected()) {
//                mainPhone.validateNumber(ukrPhoneFullRegex, ukrPhoneNoCountryCodeRegex, ukrPhoneNoPlusRegex, ukrPhoneCityRegex);
//                secondPhone.validateNumber(ukrPhoneFullRegex, ukrPhoneNoCountryCodeRegex, ukrPhoneNoPlusRegex, ukrPhoneCityRegex);
//
//                if (mainPhone.isWrongNumber || (!secondPhone.getNumber().isEmpty() && secondPhone.isWrongNumber))
//                    throw new Exception("Формат телефона повинен виглядати +380951203066, 0951203066, 380951203066, або 7076845");
//
//                mainPhone.setToUkrStandart();
//                secondPhone.setToUkrStandart();
//            } else {
//                mainPhone.validateNumber(foreinPhoneRegex, null, null, null);
//                secondPhone.validateNumber(foreinPhoneRegex, null, null, null);
//
//                if (mainPhone.isWrongNumber || (!secondPhone.getNumber().isEmpty() && secondPhone.isWrongNumber))
//                    throw new Exception("Формат іноземного телефона повинен починатися зі знаку '+'");
//            }
//
//            if (!cityRegex.matcher(city).matches() || !cityRegex.matcher(cityFact).matches())
//                throw new Exception("Назва міста повинна містити тільки українські літери");
////            if (!regionRegex.matcher(region).matches() || (!regionRegex.matcher(regionFact).matches()))
////                throw new Exception("Назва області може містити тільки українські літери та розділові знаки");
//            if (!addressRegex.matcher(address).matches() || !addressRegex.matcher(addressFact).matches())
//                throw new Exception("Адресса може містити українські літери, цифри та розділові знаки");
//
//
//            if (countryFact.equals("null")) //TODO Запитати чи це точно потрібно
//                personalData.setCountry_fact(countryService.getCountryByName("Україна"));

            personalData.setCountry(countryService.getCountryByName(country));
            personalData.setPostIndex(index);
            personalData.setCity(city);
            personalData.setRowAddress(address);
            personalData.setPhoneMain(mainPhone);
            personalData.setPhoneDop(secondPhone);
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
