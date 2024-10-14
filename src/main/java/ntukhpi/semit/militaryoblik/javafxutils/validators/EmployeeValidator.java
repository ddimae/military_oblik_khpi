package ntukhpi.semit.militaryoblik.javafxutils.validators;

import ntukhpi.semit.militaryoblik.adapters.PrepodAdapter;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.DateFieldValidator;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.IBaseValidator;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.TextFieldValidator;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class EmployeeValidator implements IBaseValidator<PrepodAdapter> {
    Pattern ukrWords = Pattern.compile("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії\\-\\s]+$");
    Pattern ukrDateRegex = Pattern.compile("^\\d{2}\\.\\d{2}\\.\\d{4}$");

    @Override
    public boolean validate(PrepodAdapter info) throws Exception {
        TextFieldValidator instituteValidator = new TextFieldValidator(-1, true, null, "Інститут", info.getInstitute(), null);
        TextFieldValidator cathedraValidator = new TextFieldValidator(-1, true, null, "Кафедра", info.getCathedra(), null);
        TextFieldValidator surnameValidator = new TextFieldValidator(40, true, ukrWords, "Прізвище", info.getSurname(), "повинно містити українські літери");
        TextFieldValidator nameValidator = new TextFieldValidator(30, true, ukrWords, "Ім'я", info.getName(), "повинно містити українські літери");
        TextFieldValidator midnameValidator = new TextFieldValidator(30, true, ukrWords, "По батькові", info.getMidname(), "повинно містити українські літери");
        DateFieldValidator dateValidator = new DateFieldValidator(false, ukrDateRegex, "Дата народження", info.getBirth(), "повинно мати формат дати: dd.mm.yyyy");

        instituteValidator.validate();
        cathedraValidator.validate();
        surnameValidator.validate();
        nameValidator.validate();
        midnameValidator.validate();
        dateValidator.validate();

        return true;
    }
}
