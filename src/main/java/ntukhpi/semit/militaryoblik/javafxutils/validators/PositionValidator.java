package ntukhpi.semit.militaryoblik.javafxutils.validators;

import ntukhpi.semit.militaryoblik.adapters.PositionAdapter;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.IBaseValidator;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.TextFieldValidator;
import ntukhpi.semit.militaryoblik.service.DolghnostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PositionValidator implements IBaseValidator<PositionAdapter> {
    Pattern ukrWords = Pattern.compile("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії,.\\-`'_\\s0-9]*$");

    @Autowired
    DolghnostServiceImpl dolghnostService;

    @Override
    public boolean validate(PositionAdapter info) throws Exception {
        TextFieldValidator fullNameValidator = new TextFieldValidator(40, true, ukrWords, "Назва посади", info.getFullName(), "може містити тільки українські літери та розділові знаки");
        TextFieldValidator shortNameValidator = new TextFieldValidator(15, false, ukrWords, "Скорочене позначення", info.getShortName(), "може містити тільки українські літери та розділові знаки");

        fullNameValidator.validate();
        shortNameValidator.validate();

        if (dolghnostService.findIDPosadaByName(info.getFullName()) != null)
            throw new Exception("Посада з такою назвою вже існує");

        return true;
    }
}
