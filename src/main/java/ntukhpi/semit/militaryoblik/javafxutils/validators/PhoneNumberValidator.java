package ntukhpi.semit.militaryoblik.javafxutils.validators;

import lombok.Getter;
import lombok.Setter;

import java.util.regex.Pattern;

/**
 * Клас валідації номеру телефона
 *
 * @author Степанов Михайло
 */
@Getter
@Setter
public class PhoneNumberValidator extends TextFieldValidator {
    private String number;
    private boolean isFullNumber;
    private boolean isNoCountryCodeNumber;
    private boolean isNoPlusNumber;
    private boolean isCityNumber;
    private boolean isWrongNumber;


    /**
     * Створює об'єкт класа валідації даного номера телефона
     * із параметрами валідації за замовчуванням
     *
     * @param number Номер телефона
     */
    public PhoneNumberValidator(String number) {
        super(-1, false, null, null, null, null);
        this.number = number;
        isFullNumber = false;
        isNoCountryCodeNumber = false;
        isNoPlusNumber = false;
        isCityNumber = false;
        isWrongNumber = false;
    }


    /**
     * Створює об'єкт класа валідації даного номера телефона
     * із даними параметрами валідації
     *
     * @param number Номер телефона
     * @param maxLength Максимальна довжина номера телефона
     * @param isNecessary Обов'язковість заповнення поля номера телефона
     * @param fieldName Назва поля для заповнення
     * @param errorMsg Повідомлення про помилку валідації
     */
    public PhoneNumberValidator(String number, int maxLength, boolean isNecessary, String fieldName, String errorMsg) {
        super(maxLength, isNecessary, null, fieldName, number, errorMsg);
        this.number = number;
        isFullNumber = false;
        isNoCountryCodeNumber = false;
        isNoPlusNumber = false;
        isCityNumber = false;
        isWrongNumber = false;
    }


    /**
     * Валідація номера телефона за встановленими параметрами
     * та даними регулярними виразами
     *
     * @param full Регулярний вираз повної форми номеру телефона
     * @param noCountryCode Регулярний вираз форми телефону без коду країни
     * @param noPlus Регулярний вираз номеру телефона без знаку + спочатку
     * @param city Регулярний вираз номеру телефона у міському форматі
     * @throws Exception Повідомлення про причину непроходження валідації
     */
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


    /**
     * Форматування номера телефона за Українським стандартом
     * (Перед викликом функції треба викликати validateNumber)
     */
    public void setToUkrStandart() {
        if (isNoCountryCodeNumber)
            number = "+38" + number;
        else if (isNoPlusNumber)
            number = "+" + number;
        else if (isCityNumber)
            number = "+38057" + number;
    }


    /**
     * Клонування об'єкта класа валідації номеру телефона
     *
     * @param phone Об'єкт класу валідації номеру телефона, значення якого треба присвоїти даному класу
     */
    public void clone(PhoneNumberValidator phone) {
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