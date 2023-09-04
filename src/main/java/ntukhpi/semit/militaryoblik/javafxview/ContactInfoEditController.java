package ntukhpi.semit.militaryoblik.javafxview;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
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
import ntukhpi.semit.militaryoblik.javafxutils.Popup;
import ntukhpi.semit.militaryoblik.service.*;
import org.antlr.v4.runtime.misc.Array2DHashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.constant.DynamicCallSiteDesc;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    private TextField addressFactTextArea;

    @FXML
    private TextField addressTextArea;

    @FXML
    private TextField cityFactTextArea;

    @FXML
    private TextField cityTextArea;

    @FXML
    private ComboBox<String> countryComboBox;

    @FXML
    private ComboBox<String> countryFactComboBox;

    @FXML
    private RadioButton equalRadioButton;

    @FXML
    private RadioButton foreinNumberRadioButton;

    @FXML
    private TextField indexFactTextArea;

    @FXML
    private TextField indexTextArea;

    @FXML
    private TextField mainPhoneTextArea;

    @FXML
    private Text pibText;

    @FXML
    private ComboBox<String> regionFactComboBox;

    @FXML
    private ComboBox<String> regionComboBox;

    @FXML
    private TextField secondPhoneTextArea;

    private ReservistsAllController mainController;
    private Long selectedPrepodId;
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
        setContactInfo((ReservistAdapter) data);
    }

    private void setContactInfo(ReservistAdapter reservist) {
//        personalData = personalDataService.getPersonalDataById(selectedPrepodId);
        personalData = personalDataService.getPersonalDataById(reservist.getId());
        //Якщо немає звязаної інформації, то запускати треба щось на установку нової!!!

//        pibText.setText(MilitaryOblikKhPIMain.getPIB(prepodService.getPrepodById(selectedPrepodId)));
        pibText.setText(MilitaryOblikKhPIMain.getPIB(prepodService.getPrepodById(reservist.getId())));

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

        indexTextArea.setText(personalData.getPostIndex());
        indexFactTextArea.setText(personalData.getFactPostIndex());

        cityTextArea.setText(personalData.getCity());
        cityFactTextArea.setText(personalData.getFactCity());

        if (region != null)
            regionComboBox.setValue(region.getCountryName());
        if (regionFact != null)
            regionFactComboBox.setValue(regionFact.getCountryName());

        addressTextArea.setText(personalData.getRowAddress());
        addressFactTextArea.setText(personalData.getFactRowAddress());

        mainPhoneTextArea.setText(personalData.getPhoneMain());
        secondPhoneTextArea.setText(personalData.getPhoneDop());

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

//        selectedPrepodId = ReservistsAllController.getSelectedPrepodId();

        handleChangeCountry(null);
    }

    @FXML
    void closeEdit(ActionEvent event) {
        try {
            ((Stage) countryComboBox.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void saveContactInfo(ActionEvent event) {
        String country = String.valueOf(countryComboBox.getValue());
        String index = indexTextArea.getText();
        String city = cityTextArea.getText();
        String region = regionComboBox.getValue();
        String address = addressTextArea.getText();
        PhoneNumber mainPhone = new PhoneNumber(mainPhoneTextArea.getText());
        PhoneNumber secondPhone = new PhoneNumber(secondPhoneTextArea.getText());

        String countryFact = String.valueOf(countryFactComboBox.getValue());
        String indexFact = indexFactTextArea.getText();
        String cityFact = cityFactTextArea.getText();
        String regionFact = regionFactComboBox.getValue();
        String addressFact = addressFactTextArea.getText();

        Pattern ukrIndexRegex = Pattern.compile("(\\d{5})?");

        Pattern ukrPhoneFullRegex = Pattern.compile("^(\\+\\d{12})?$");
        Pattern ukrPhoneNoCountryCodeRegex = Pattern.compile("^(\\d{10})?$");
        Pattern ukrPhoneNoPlusRegex = Pattern.compile("^(\\d{12})?$");
        Pattern ukrPhoneCityRegex = Pattern.compile("^(\\d{7})?$");

        Pattern foreinPhoneRegex = Pattern.compile("(^\\+\\d+)?");
        Pattern cityRegex = Pattern.compile("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії\\s]*$");
        Pattern regionRegex = Pattern.compile("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії,.\\s]*$");
        Pattern addressRegex = Pattern.compile("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії\\d,.\\-\\'\\&_\\s]*$");

        boolean isUkraine = country.equals("Україна");
        boolean isUkraineFact = countryFact.equals("Україна");

        city = city.trim();
        address = address.trim();

        try {
            if (country.equals("null") || country.equals("Не визначено"))
                throw new Exception("Країна реєстрації є обов'язковим полем");
            if (city.isEmpty())
                throw new Exception("Місто реєстрації є обов'язковим полем");
            if (address.isEmpty())
                throw new Exception("Адреса реєстрації є обов'язковим полем");
            if (mainPhone.getNumber().isEmpty())
                throw new Exception("Телефон 1 є обов'язковим полем");

            if ((isUkraine && !ukrIndexRegex.matcher(index).matches()) || ((isUkraineFact && !ukrIndexRegex.matcher(indexFact).matches())))
                throw new Exception("Індекс повинен складатися із 5 цифр");

            if (!foreinNumberRadioButton.isSelected()) {
                mainPhone.validateNumber(ukrPhoneFullRegex, ukrPhoneNoCountryCodeRegex, ukrPhoneNoPlusRegex, ukrPhoneCityRegex);
                secondPhone.validateNumber(ukrPhoneFullRegex, ukrPhoneNoCountryCodeRegex, ukrPhoneNoPlusRegex, ukrPhoneCityRegex);

                if (mainPhone.isWrongNumber || (!secondPhone.getNumber().isEmpty() && secondPhone.isWrongNumber))
                    throw new Exception("Формат телефона повинен виглядати +380951203066, 0951203066, 380951203066, або 7076845");

                mainPhone.setToUkrStandart();
                secondPhone.setToUkrStandart();
            } else {
                mainPhone.validateNumber(foreinPhoneRegex, null, null, null);
                secondPhone.validateNumber(foreinPhoneRegex, null, null, null);

                if (mainPhone.isWrongNumber || (!secondPhone.getNumber().isEmpty() && secondPhone.isWrongNumber))
                    throw new Exception("Формат іноземного телефона повинен починатися зі знаку '+'");
            }

            if (!cityRegex.matcher(city).matches() || !cityRegex.matcher(cityFact).matches())
                throw new Exception("Назва міста повинна містити тільки українські літери");
//            if (!regionRegex.matcher(region).matches() || (!regionRegex.matcher(regionFact).matches()))
//                throw new Exception("Назва області може містити тільки українські літери та розділові знаки");
            if (!addressRegex.matcher(address).matches() || !addressRegex.matcher(addressFact).matches())
                throw new Exception("Адресса може містити українські літери, цифри та розділові знаки");


            if (countryFact.equals("null"))
                personalData.setCountry_fact(countryService.getCountryByName("Україна"));

            personalData.setCountry(countryService.getCountryByName(country));
            personalData.setPostIndex(index);
            personalData.setCity(city);
            personalData.setRowAddress(address);
            personalData.setPhoneMain(mainPhone.getNumber());
            personalData.setPhoneDop(secondPhone.getNumber());
            personalData.setPrepod(prepodService.getPrepodById(selectedPrepodId));
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

            Popup.successSave();
            closeEdit(null);
        } catch (Exception e) {
            Popup.wrongInputAlert(e.getMessage());
        }
    }

    @FXML
    void handleEqualRadioButton(ActionEvent event) {
        if (equalRadioButton.isSelected()) {
            countryFactComboBox.setDisable(true);
            indexFactTextArea.setDisable(true);
            cityFactTextArea.setDisable(true);
            regionFactComboBox.setDisable(true);
            addressFactTextArea.setDisable(true);

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
            indexFactTextArea.setDisable(false);
            cityFactTextArea.setDisable(false);
            regionFactComboBox.setDisable(false);
            addressFactTextArea.setDisable(false);
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
