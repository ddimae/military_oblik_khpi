package ntukhpi.semit.militaryoblik.javafxutils.validators;

import ntukhpi.semit.militaryoblik.javafxutils.DataFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class DateFieldValidator extends TextFieldValidator {
    public DateFieldValidator(boolean isNecessary, Pattern regex, String fieldName, String dataField, String errorMsg) {
        super(-1, isNecessary, regex, fieldName, dataField, errorMsg);
    }

    @Override
    public void validate() throws Exception {
        super.validate();

        boolean isBlank = getTextField() == null || getTextField().isBlank();

        try {
            if (!isBlank && !getTextField().equals(DataFormat.localDateToUkStandart(LocalDate.parse(getTextField(), DateTimeFormatter.ofPattern("dd.MM.yyyy")))))
                throw new Exception(getErrorMsg());
        } catch (Exception e) {
            throw new Exception(getErrorMsg());
        }
    }
}
