package ntukhpi.semit.militaryoblik.javafxutils.validators;

import ntukhpi.semit.militaryoblik.adapters.FakultetAdapter;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.IBaseValidator;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.TextFieldValidator;
import ntukhpi.semit.militaryoblik.service.FakultetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class InstituteValidator implements IBaseValidator<FakultetAdapter> {
    Pattern ukrWords = Pattern.compile("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії,.\\-`'_\\s]*$");
    Pattern oneWord = Pattern.compile("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії]*$");
    Pattern onlyNumber = Pattern.compile("^\\d+$");

    @Autowired
    FakultetServiceImpl fakultetService;

    @Override
    public boolean validate(FakultetAdapter info) throws Exception {
        TextFieldValidator fullNameValidator = new TextFieldValidator(100, true, ukrWords, "Повна назва", info.getName(), "може містити тільки українські літери та розділові знаки");
        TextFieldValidator abbrValidator = new TextFieldValidator(10, true, oneWord, "Абревіатура", info.getAbbr(), "повнно містити тільки українські літери без пробілів");
        TextFieldValidator codeValidator = new TextFieldValidator(10, true, onlyNumber, "Код", info.getCode(), "повинен містити тільки 1 число");

        fullNameValidator.validate();
        abbrValidator.validate();
        codeValidator.validate();

        if (fakultetService.findIDFakultetByFname(info.getName()) != null)
            throw new Exception("Інститут з такою назвою вже існує");
        if (fakultetService.findIDFakultetByAbr(info.getAbbr()) != null)
            throw new Exception("Інститут з такою абревіатурою вже існує");
        if (fakultetService.findIDFakultetByOid(info.getCode()) != null)
            throw new Exception("Інститут з таким кодом вже існує");

        return true;
    }
}
