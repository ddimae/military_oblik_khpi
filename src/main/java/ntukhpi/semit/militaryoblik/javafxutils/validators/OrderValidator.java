package ntukhpi.semit.militaryoblik.javafxutils.validators;

import ntukhpi.semit.militaryoblik.adapters.CurrentDoljnostInfoAdapter;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.DateFieldValidator;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.IBaseValidator;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.TextFieldValidator;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class OrderValidator implements IBaseValidator<CurrentDoljnostInfoAdapter> {
    Pattern ukrNakazNumberRegex = Pattern.compile("^[1-9][0-9]{0,4}.+$");
    Pattern ukrCommmentRegex = Pattern.compile("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії0-9\\s.,'`_\\-]+$");
    Pattern ukrDateRegex = Pattern.compile("^\\d{2}\\.\\d{2}\\.\\d{4}$");

    @Override
    public boolean validate(CurrentDoljnostInfoAdapter info) throws Exception {
        String nakaz = info.getNakazStart();
        String nakazDiss = info.getNakazStop();
        String comment = info.getCommentStart();
        String commentDiss = info.getCommentStop();
        String date = info.getDateStart();
        String dateDiss = info.getDateStop();

        TextFieldValidator nakazValidator = new TextFieldValidator(12, true, ukrNakazNumberRegex, "Номер наказу", nakaz, "на початку має від 1 до 5 цифр");
        TextFieldValidator nakazDissValidator = new TextFieldValidator(12, false, ukrNakazNumberRegex, "Номер наказу", nakazDiss, "на початку має від 1 до 5 цифр");
        TextFieldValidator commentValidator = new TextFieldValidator(255, false, ukrCommmentRegex, "Коментар", comment, "вводиться українською");
        TextFieldValidator commentDissValidator = new TextFieldValidator(255, false, ukrCommmentRegex, "Коментар", commentDiss, "вводиться українською");
        DateFieldValidator dateValidator = new DateFieldValidator(true, ukrDateRegex, "Дата наказу", date, "повинна мати формат дати: dd.mm.yyyy");
        DateFieldValidator dateValidatorDiss = new DateFieldValidator(false, ukrDateRegex, "Дата наказу", dateDiss, "повинна мати формат дати: dd.mm.yyyy");

        nakazValidator.validate();
        nakazDissValidator.validate();
        commentValidator.validate();
        commentDissValidator.validate();
        dateValidator.validate();
        dateValidatorDiss.validate();

        if ((nakazDiss.length() == 0 && dateDiss.length() != 0) || (nakazDiss.length() != 0 && dateDiss.length() == 0)) {
            throw new Exception("Мають бути заповнені і наказ, і дата");
        }
        if (nakazDiss.length() == 0 && dateDiss.length() == 0 && commentDiss.length() != 0) {
            throw new Exception("Коментар не вводиться, якщо немає наказу");
        }

        return true;
    }
}
