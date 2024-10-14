package ntukhpi.semit.militaryoblik.javafxutils.validators;

import ntukhpi.semit.militaryoblik.adapters.CathedraAdapter;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.IBaseValidator;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.TextFieldValidator;
import ntukhpi.semit.militaryoblik.service.KafedraServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class CathedraValidator implements IBaseValidator<CathedraAdapter> {
    private Pattern ukrWords = Pattern.compile("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії,.\\-`'_\\s]*$");
    private Pattern oneWord = Pattern.compile("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії]*$");
    private Pattern onlyNumber = Pattern.compile("^\\d+$");

    @Autowired
    KafedraServiceImpl kafedraService;

    public CathedraValidator() {}

    @Override
    public boolean validate(CathedraAdapter info) throws Exception {
        TextFieldValidator instituteValidator = new TextFieldValidator(-1, true, null, "Інститут", info.getInstitute(), null);
        TextFieldValidator fullNameValidator = new TextFieldValidator(100, true, ukrWords, "Повне ім'я", info.getFullName(), "може містити тільки українські літери та розділові знаки");
        TextFieldValidator abbrValidator = new TextFieldValidator(10, true, oneWord, "Абревіатура", info.getAbbr(), "повнно містити тільки українські літери без пробілів");
        TextFieldValidator codeValidator = new TextFieldValidator(10, true, onlyNumber, "Код", info.getCode(), "повинен містити тільки 1 число");

        instituteValidator.validate();
        fullNameValidator.validate();
        abbrValidator.validate();
        codeValidator.validate();

        if (kafedraService.findIDKafedraByKname(info.getFullName()) != null)
            throw new Exception("Кафедра з такою назвою вже інсує");
        if (kafedraService.findIDKafedraByKabr(info.getAbbr()) != null)
            throw new Exception("Кафедра з такою абревіатуро вже інсує");
        if (kafedraService.findIDKafedraByOid(info.getCode()) != null)
            throw new Exception("Кафедра з таким кодом вже інсує");

        return true;
    }
}
