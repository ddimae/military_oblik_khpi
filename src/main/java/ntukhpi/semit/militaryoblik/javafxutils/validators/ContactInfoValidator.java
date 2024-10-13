package ntukhpi.semit.militaryoblik.javafxutils.validators;

import lombok.Getter;
import ntukhpi.semit.militaryoblik.adapters.ContactInfoAdapter;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.IBaseValidator;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.PhoneNumberValidator;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.TextFieldValidator;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ContactInfoValidator implements IBaseValidator<ContactInfoAdapter> {
    private Pattern ukrIndexRegex = Pattern.compile("(\\d{5})?");
    private Pattern ukrPhoneFullRegex = Pattern.compile("^(\\+\\d{12})?$");
    private Pattern ukrPhoneNoCountryCodeRegex = Pattern.compile("^(\\d{10})?$");
    private Pattern ukrPhoneNoPlusRegex = Pattern.compile("^(\\d{12})?$");
    private Pattern ukrPhoneCityRegex = Pattern.compile("^(\\d{7})?$");
    private Pattern foreinPhoneRegex = Pattern.compile("(^\\+\\d+)?");
    private Pattern cityRegex = Pattern.compile("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії,.\\-`'_\\s]*$");
    private Pattern regionRegex = Pattern.compile("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії,.\\s]*$");
    private Pattern addressRegex = Pattern.compile("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії\\d,.\\-`'\\&_\\s]*$");

    @Getter()
    private PhoneNumberValidator mainPhoneForm;

    @Getter()
    private PhoneNumberValidator secondPhoneForm;

    public ContactInfoValidator() {};

    @Override
    public boolean validate(ContactInfoAdapter info) throws Exception {
        TextFieldValidator countryForm = new TextFieldValidator(-1, true, null, "Країна", info.getCountry(), null);
        TextFieldValidator indexForm = new TextFieldValidator(10, false, String.valueOf(info.getCountry()).equals("Україна")?ukrIndexRegex:null, "Індекс", info.getIndex(), "повинно складатися із 5 цифр");
        TextFieldValidator cityForm = new TextFieldValidator(30, true, cityRegex, "Місто", info.getCity(), "повинно містити тільки українські літери та розділові знаки");
        TextFieldValidator regionForm = new TextFieldValidator(255, false, null, "Область", info.getRegion(), null);
        TextFieldValidator addressForm = new TextFieldValidator(255, true, addressRegex, "Адресса", info.getAddress(), "може містити українські літери, цифри та розділові знаки");
        mainPhoneForm = new PhoneNumberValidator(info.getMainPhone(), 13, true, "Телефон 1", "повинно мати форму: +380951203066, 0951203066, 380951203066 або 7076845");
        secondPhoneForm = new PhoneNumberValidator(info.getSecondPhone(), 13, false, "Телефон 2", "повинно мати форму: +380951203066, 0951203066, 380951203066, або 7076845");

        TextFieldValidator countryFactForm = new TextFieldValidator(-1, false, null, "Країна", info.getCountryFact(), null);
        TextFieldValidator indexFactForm = new TextFieldValidator(10, false, String.valueOf(info.getCountryFact()).equals("Україна")?ukrIndexRegex:null, "Індекс", info.getIndexFact(), "повинно складатися із 5 цифр");
        TextFieldValidator cityFactForm = new TextFieldValidator(30, false, cityRegex, "Місто", info.getCityFact(), "повинно містити тільки українські літери та розділові знаки");
        TextFieldValidator regionFactForm = new TextFieldValidator(255, false, null, "Область", info.getRegionFact(), null);
        TextFieldValidator addressFactForm = new TextFieldValidator(255, false, addressRegex, "Адресса", info.getAddressFact(), "може містити українські літери, цифри та розділові знаки");

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
        if (info.isForeinNumber()) {
            mainPhoneForm.setErrorMsg("має іноземний формат та повинен починатися зі знаку '+'");
            secondPhoneForm.setErrorMsg("має іноземний формат та повинен починатися зі знаку '+'");

            mainPhoneForm.validateNumber(foreinPhoneRegex, null, null, null);
            secondPhoneForm.validateNumber(foreinPhoneRegex, null, null, null);
        } else {
            mainPhoneForm.validateNumber(ukrPhoneFullRegex, ukrPhoneNoCountryCodeRegex, ukrPhoneNoPlusRegex, ukrPhoneCityRegex);
            secondPhoneForm.validateNumber(ukrPhoneFullRegex, ukrPhoneNoCountryCodeRegex, ukrPhoneNoPlusRegex, ukrPhoneCityRegex);
        }

        return true;
    }
}
