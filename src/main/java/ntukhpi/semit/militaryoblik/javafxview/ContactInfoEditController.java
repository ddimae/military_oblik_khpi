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
import ntukhpi.semit.militaryoblik.adapters.ContactInfoAdapter;
import ntukhpi.semit.militaryoblik.adapters.DocumentsAdapter;
import ntukhpi.semit.militaryoblik.adapters.FakultetAdapter;
import ntukhpi.semit.militaryoblik.adapters.ReservistAdapter;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Country;
import ntukhpi.semit.militaryoblik.javafxutils.ControlledScene;
import ntukhpi.semit.militaryoblik.repository.CountryRepository;
import ntukhpi.semit.militaryoblik.repository.PersonalDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    private TextField regionFactTextArea;

    @FXML
    private TextField regionTextArea;

    @FXML
    private TextField secondPhoneTextArea;

    private ReservistsAllController mainController;
    private ReservistAdapter selectedReservist;
//    private ContactInfoAdapter selectedContactInfo;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    PersonalDataRepository personalDataRepository;


    @Override
    public void setMainController(Object mainController) {
        this.mainController = (ReservistsAllController) mainController;
    }

    @Override
    public void setData(Object data) {
        if (data instanceof ReservistAdapter) {
            setContactInfo((ReservistAdapter) data);
        }
    }

    private void setContactInfo(ReservistAdapter reservist) {
        selectedReservist = reservist;
        //TODO брати із БД контактну інформацію про резервиста
//        personalDataRepository.

        pibText.setText(reservist.getPib());

        countryComboBox.setValue("Україна");

        indexTextArea.setText("12345");
        indexFactTextArea.setText("54321");

        cityTextArea.setText("Київ");
        cityFactTextArea.setText("Харків");

        regionTextArea.setText("Київська обл.");
        regionFactTextArea.setText("Харківська обл.");

        addressFactTextArea.setText("вул Кирпичова, 5");

        mainPhoneTextArea.setText("+380123456789");
        secondPhoneTextArea.setText("+380453216543");
    }

    public void initialize() {
        ObservableList<String> countryList = FXCollections.observableArrayList( //TODO Переробити із використанням сервісів
                countryRepository.findAll().stream().map(Country::getCountryName).collect(Collectors.toList()));

        countryComboBox.setItems(countryList);
        countryFactComboBox.setItems(countryList);
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
        try {
            String country = String.valueOf(countryComboBox.getValue());
            String index = indexTextArea.getText();
            String city = cityTextArea.getText();
            String region = regionTextArea.getText();
            String address = addressTextArea.getText();
            PhoneNumber mainPhone = new PhoneNumber(mainPhoneTextArea.getText());
            PhoneNumber secondPhone = new PhoneNumber(secondPhoneTextArea.getText());

            String countryFact = String.valueOf(countryFactComboBox.getValue());
            String indexFact = indexFactTextArea.getText();
            String cityFact = cityFactTextArea.getText();
            String regionFact = regionFactTextArea.getText();
            String addressFact = addressFactTextArea.getText();

            Pattern ukrIndexRegex = Pattern.compile("(\\d{5})?");

            Pattern ukrPhoneFullRegex = Pattern.compile("^(\\+\\d{12})?$");
            Pattern ukrPhoneNoCountryCodeRegex = Pattern.compile("^(\\d{10})?$");
            Pattern ukrPhoneNoPlusRegex = Pattern.compile("^(\\d{12})?$");
            Pattern ukrPhoneCityRegex = Pattern.compile("^(\\d{8})?$");
//            Pattern ukrMainPhoneRegex = Pattern.compile("^((\\+\\d{12})|(\\d{10}))?$");
//            Pattern ukrSecondPhoneRegex = Pattern.compile("^((\\+\\d{12})|(\\d{10}))?$");

            Pattern foreinPhoneRegex = Pattern.compile("(^\\+\\d+)?");
            Pattern cityRegex = Pattern.compile("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії\\s]*$");
            Pattern regionRegex = Pattern.compile("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії,.\\s]*$");
            Pattern addressRegex = Pattern.compile("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії\\d,.\\-\\'\\&_\\s]*$");

            boolean isUkraine = country.equals("Україна");
            boolean isUkraineFact = countryFact.equals("Україна");

            if (country.equals("null"))
                throw new Exception("Країна реєстрації є обов'язковим полем");
            if (region.isEmpty())
                throw new Exception("Область реєстрації є обов'язковим полем");
            if (mainPhone.getNumber().isEmpty())
                throw new Exception("Телефон 1 є обов'язковим полем");

            if ((isUkraine && !ukrIndexRegex.matcher(index).matches()) || ((isUkraineFact && !ukrIndexRegex.matcher(indexFact).matches())))
                throw new Exception("Індекс повинен складатися із 5 цифр");

            if (!foreinNumberRadioButton.isSelected()) {
                mainPhone.validateNumber(ukrPhoneFullRegex, ukrPhoneNoCountryCodeRegex, ukrPhoneNoPlusRegex, ukrPhoneCityRegex);
                secondPhone.validateNumber(ukrPhoneFullRegex, ukrPhoneNoCountryCodeRegex, ukrPhoneNoPlusRegex, ukrPhoneCityRegex);

                if (mainPhone.isWrongNumber || (!secondPhone.getNumber().isEmpty() && secondPhone.isWrongNumber))
                    throw new Exception("Формат телефона повинен виглядати +380951203066, 0951203066, 380951203066, або 70768453");
            } else {
                mainPhone.validateNumber(foreinPhoneRegex, null, null, null);
                secondPhone.validateNumber(foreinPhoneRegex, null, null, null);

                if (mainPhone.isWrongNumber || (!secondPhone.getNumber().isEmpty() && secondPhone.isWrongNumber))
                    throw new Exception("Формат іноземного телефона повинен починатися зі знаку '+'");
            }

            if (!cityRegex.matcher(city).matches() || !cityRegex.matcher(cityFact).matches())
                throw new Exception("Назва міста повинна містити тільки українські літери");
            if (!regionRegex.matcher(region).matches() || (!regionRegex.matcher(regionFact).matches()))
                throw new Exception("Назва області може містити тільки українські літери та розділові знаки");
            if (!addressRegex.matcher(address).matches() || !addressRegex.matcher(addressFact).matches())
                throw new Exception("Адресса може містити українські літери, цифри та розділові знаки");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void handleEqualRadioButton(ActionEvent event) {
        if (equalRadioButton.isSelected()) {
            countryFactComboBox.setDisable(true);
            indexFactTextArea.setDisable(true);
            cityFactTextArea.setDisable(true);
            regionFactTextArea.setDisable(true);
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
            regionFactTextArea.setDisable(false);
            addressFactTextArea.setDisable(false);
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
        }
        else {
            foreinNumberRadioButton.setDisable(true);
            foreinNumberRadioButton.setSelected(true);
        }
    }
}
