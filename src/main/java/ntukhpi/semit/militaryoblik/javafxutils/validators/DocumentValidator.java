package ntukhpi.semit.militaryoblik.javafxutils.validators;

import ntukhpi.semit.militaryoblik.adapters.DocumentAdapter;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.DateFieldValidator;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.IBaseValidator;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.TextFieldValidator;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class DocumentValidator implements IBaseValidator<DocumentAdapter> {
    Pattern ukrOldSeriesNumberRegex = Pattern.compile("^[А-ЩЬЮЯҐЄІЇ]{2}\\d{6}$");
    Pattern ukrOldWhoGivesRegex = Pattern.compile("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії0-9\\s.,'`_\\-]+$");
    Pattern newSeriesRegex = Pattern.compile("^\\d{9}$");
    Pattern newWhoGivesRegex = Pattern.compile("^\\d{4}$");
    Pattern enOldSeriesNumberRegex = Pattern.compile("^[A-Z]{2}\\d{6}$");
    Pattern ukrDateRegex = Pattern.compile("^\\d{2}\\.\\d{2}\\.\\d{4}$");

    @Override
    public boolean validate(DocumentAdapter info) throws Exception {
        TextFieldValidator docTypeValidator = new TextFieldValidator(-1, true, null, "Тип документу", info.getType(), null);
        TextFieldValidator numberValidator = new TextFieldValidator(10, true, null, "Серія та номер", info.getNumber(), null);
        TextFieldValidator whoGivesValidator = new TextFieldValidator(255, true, null, "Ким видан", info.getWhoGives(), null);
        DateFieldValidator dateValidator = new DateFieldValidator(true, ukrDateRegex, "Дата видачі", info.getDate(), "повинно мати формат дати: dd.mm.yyyy");

        docTypeValidator.validate();

        switch (info.getType()) {
            case "Паперовий паспорт":
                numberValidator.setRegex(ukrOldSeriesNumberRegex);
                numberValidator.setErrorMsg("паперовога паспорта повинно містити 2 великі українські літери та 6 цифр");
                whoGivesValidator.setRegex(ukrOldWhoGivesRegex);
                whoGivesValidator.setErrorMsg("може містити українські літери, цифри, розділові знаки");
                break;
            case "ID картка":
                numberValidator.setRegex(newSeriesRegex);
                numberValidator.setErrorMsg("ID картки повинно містити 9 цифр");
                whoGivesValidator.setRegex(newWhoGivesRegex);
                whoGivesValidator.setErrorMsg("повинно містити тільки 4 цифри");
                break;
            case "Закордонний паспорт":
                numberValidator.setRegex(enOldSeriesNumberRegex);
                numberValidator.setErrorMsg("закордонного паспорта повинно містити 2 великі латинські літери та 6 цифр");
                whoGivesValidator.setRegex(newWhoGivesRegex);
                whoGivesValidator.setErrorMsg("повинно містити тільки 4 цифри");
                break;
            case "Посвідчення особи офіцера":
            case "Військовий квиток":
                numberValidator.setRegex(ukrOldSeriesNumberRegex);
                numberValidator.setErrorMsg("Серія та номер посвідчення повинні містити 2 великі українські літери та 6 цифр");
                whoGivesValidator.setRegex(ukrOldWhoGivesRegex);
                whoGivesValidator.setErrorMsg("може містити українські літери, цифри, розділові знаки");
                break;
        }

        numberValidator.validate();
        whoGivesValidator.validate();
        dateValidator.validate();

        return true;
    }
}
