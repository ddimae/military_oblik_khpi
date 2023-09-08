package ntukhpi.semit.militaryoblik.javafxutils;

import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.regex.Pattern;

@Getter
@Setter
@AllArgsConstructor
public class FormTextInput {
    private int maxLength;
    private boolean isNecessary;
    private Pattern regex;

    private String fieldName;
    private String textField;
    private String errorMsg;

    public void validate() throws Exception {
        if ((textField == null || textField.isBlank()) && isNecessary)
            throw new Exception("\"" + fieldName + "\" є обов'язковим полем");

        if (regex != null && !regex.matcher(textField).matches())
            throw new Exception(errorMsg);

        if (maxLength != -1 && textField != null && textField.length() > maxLength)
            throw new Exception("Максимальна довжина поля \"" + fieldName + "\" становить " + maxLength + " символів");
    }
}
