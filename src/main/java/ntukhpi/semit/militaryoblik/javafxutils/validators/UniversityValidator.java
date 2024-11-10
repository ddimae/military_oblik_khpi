package ntukhpi.semit.militaryoblik.javafxutils.validators;

import ntukhpi.semit.militaryoblik.adapters.IBaseAdapter;
import ntukhpi.semit.militaryoblik.adapters.UniversityAdapter;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.IBaseValidator;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.TextFieldValidator;
import ntukhpi.semit.militaryoblik.service.VNZakladService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.management.InstanceAlreadyExistsException;
import java.util.regex.Pattern;

@Component
public class UniversityValidator implements IBaseValidator<UniversityAdapter> {
    Pattern ukrWords = Pattern.compile("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії,.\\-`'_\\s0-9]*$");

    VNZakladService vnZakladService;

    @Autowired
    public UniversityValidator(VNZakladService vnzakladService) {
        this.vnZakladService = vnzakladService;
    }

    @Override
    public boolean validate(UniversityAdapter info) throws Exception {
        TextFieldValidator fullNameValidator = new TextFieldValidator(255, true, ukrWords, "Повна назва", info.getFullName(), "може містити тільки українські літери та розділові знаки");
        TextFieldValidator shortNameValidator = new TextFieldValidator(10, true, ukrWords, "Скорочене форма", info.getShortName(), "може містити тільки українські літери та розділові знаки");

        fullNameValidator.validate();
        shortNameValidator.validate();

        if (vnZakladService.findIdVNZakladByVnzName(info.getFullName()) != null)
            throw new InstanceAlreadyExistsException("Університет з такою повною назвою вже існує");
        if (vnZakladService.findIdVNZakladByVnzShortName(info.getShortName()) != null)  // FIXME: Maybe make abbr not unique
            throw new InstanceAlreadyExistsException("Університет з такою скороченою назвою вже існує");

        return true;
    }
}
