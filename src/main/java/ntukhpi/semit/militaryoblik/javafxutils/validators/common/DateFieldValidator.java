package ntukhpi.semit.militaryoblik.javafxutils.validators.common;

import ntukhpi.semit.militaryoblik.javafxutils.DataFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;


/**
 * Клас валідації поля вводу дати
 *
 * @author Степанов Михайло
 */
public class DateFieldValidator extends TextFieldValidator {

    /**
     * Створює об'єкт класа валідації даної дати
     * із даними параметрами валідації
     *
     * @param isNecessary Обов'язковість заповнення поля номера телефона
     * @param regex Регулярний вираз формату дати
     * @param fieldName Назва поля для заповнення
     * @param dataField Дата
     * @param errorMsg Повідомлення про помилку валідації
     */
    public DateFieldValidator(boolean isNecessary, Pattern regex, String fieldName, String dataField, String errorMsg) {
        super(-1, isNecessary, regex, fieldName, dataField, errorMsg);
    }

    @Override
    public void validate() throws Exception {
        super.validate();

        boolean isBlank = getTextField() == null || getTextField().isBlank();

        try {
            if (!isBlank && !getTextField().equals(DataFormat.localDateToUkStandart(LocalDate.parse(getTextField(), DateTimeFormatter.ofPattern("dd.MM.yyyy"))))) {
                throw new Exception(getErrorMsg());
            }
        } catch (Exception e) {
            throw new Exception(getErrorMsg());
        }
    }
}
