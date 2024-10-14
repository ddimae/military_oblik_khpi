package ntukhpi.semit.militaryoblik.javafxutils.validators;

import ntukhpi.semit.militaryoblik.adapters.MilitaryPersonAdapter;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.IBaseValidator;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.TextFieldValidator;
import ntukhpi.semit.militaryoblik.service.VoenkomatServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class MilitaryRegistrationValidator implements IBaseValidator<MilitaryPersonAdapter> {
    Pattern onlyNumber = Pattern.compile("^\\d+$");
    Pattern ukrWords = Pattern.compile("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії,.\\-`'_\\s]*$");

    @Autowired
    VoenkomatServiceImpl voenkomatService;

    @Override
    public boolean validate(MilitaryPersonAdapter info) throws Exception {
        TextFieldValidator vosValidator = new TextFieldValidator(6, true, onlyNumber, "ВОС", info.getVos(), "повинен містити тільки 1 число з 6 цифр");
        TextFieldValidator categoryValidator = new TextFieldValidator(1, true, onlyNumber, "Категорія обліку", info.getVCategory(), null);

//        TextFieldValidator groupValidator = new TextFieldValidator(-1, true, ukrWords, "Група обліку", group, null);
//        TextFieldValidator skladValidator = new TextFieldValidator(-1, true, ukrWords, "Склад", sklad, null);
//        TextFieldValidator zvanieValidator = new TextFieldValidator(-1, true, ukrWords, "Військове звання", zvanie, null);

        TextFieldValidator prydatnistValidator = new TextFieldValidator(-1, true, ukrWords, "Придатність", info.getVPrydatnist(), "може містити тільки українські літери та розділові знаки");
        TextFieldValidator voenkomatValidator = new TextFieldValidator(-1, true, ukrWords, "ТЦК", info.getVoenkomat(), "може містити тільки українські літери та розділові знаки");
        TextFieldValidator familyStanValidator = new TextFieldValidator(-1, true, ukrWords, "Сімейний стан", info.getFamilyState(), "може містити тільки українські літери та розділові знаки");
        TextFieldValidator osvitaValidator = new TextFieldValidator(-1, true, ukrWords, "Освіта", info.getEducationLevel(), "може містити тільки українські літери та розділові знаки");

        vosValidator.validate();
        categoryValidator.validate();
//            groupValidator.validate();
//            skladValidator.validate();
//            zvanieValidator.validate();

        prydatnistValidator.validate();
        voenkomatValidator.validate();
        familyStanValidator.validate();
        osvitaValidator.validate();

        return true;
    }
}
