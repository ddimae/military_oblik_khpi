package ntukhpi.semit.militaryoblik.javafxutils.validators;

import ntukhpi.semit.militaryoblik.adapters.FamilyAdapter;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.IBaseValidator;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.TextFieldValidator;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class FamilyValidator implements IBaseValidator<FamilyAdapter> {
    Pattern ukrWords = Pattern.compile("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії\\-\\s]+$");
    Pattern onlyYear = Pattern.compile("^\\d{4}$");

    @Override
    public boolean validate(FamilyAdapter info) throws Exception {
        TextFieldValidator relationshipValidator = new TextFieldValidator(-1, true, null, "Ступінь рідства", info.getVidRidstva(), null);
        TextFieldValidator surnameValidator = new TextFieldValidator(40, false, ukrWords, "Прізвище", info.getMemFam(), "повинно містити українські літери");
        TextFieldValidator nameValidator = new TextFieldValidator(30, false, ukrWords, "Ім'я", info.getMemName(), "повинно містити українські літери");
        TextFieldValidator midnameValidator = new TextFieldValidator(30, false, ukrWords, "По батькові", info.getMemOtch(), "повинно містити українські літери");
        TextFieldValidator yearValidator = new TextFieldValidator(4, true, onlyYear, "Рік народження", info.getRikNarodz(), "повинно містити 4 цифри");

        relationshipValidator.validate();
        surnameValidator.validate();
        nameValidator.validate();
        midnameValidator.validate();
        yearValidator.validate();

        return true;
    }
}
