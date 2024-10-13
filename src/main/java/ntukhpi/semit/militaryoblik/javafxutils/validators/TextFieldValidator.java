package ntukhpi.semit.militaryoblik.javafxutils.validators;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.regex.Pattern;


/**
 * Клас валідації поля вводу текстових даних
 *
 * @author Степанов Михайло
 */
@Getter
@Setter
@AllArgsConstructor
public class TextFieldValidator {
    private int maxLength;
    private boolean isNecessary;
    private Pattern regex;

    private String fieldName;
    private String textField;
    private String errorMsg;


    /**
     * Валідація тексту за встановленими параметрами
     *
     * @throws Exception Повідомлення про причину непроходження валідації
     */
    public void validate() throws Exception {
        boolean isBlank = textField == null || textField.isBlank();

        if (isBlank && isNecessary)
            throw new Exception("\"" + fieldName + "\" є обов'язковим полем");

        if (!isBlank && regex != null && !regex.matcher(textField).matches())
            throw new Exception("\"" + fieldName + "\" " + errorMsg);

        if (maxLength != -1 && !isBlank && textField.length() > maxLength)
            throw new Exception("Максимальна довжина поля \"" + fieldName + "\" становить " + maxLength + " символів");
    }
}
