package ntukhpi.semit.militaryoblik.javafxutils.validators;

import ntukhpi.semit.militaryoblik.adapters.EducationPostgraduateAdapter;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.IBaseValidator;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.TextFieldValidator;
import ntukhpi.semit.militaryoblik.javafxutils.validators.exceptions.UniversityNotFoundException;
import ntukhpi.semit.militaryoblik.service.VNZakladService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PosteducationValidator implements IBaseValidator<EducationPostgraduateAdapter> {
    Pattern onlyFourDigits = Pattern.compile("^[0-9]{4}$");

    VNZakladService vnZakladService;

    @Autowired
    public PosteducationValidator(VNZakladService vNZakladService) {
        this.vnZakladService = vNZakladService;
    }

    @Override
    public boolean validate(EducationPostgraduateAdapter info) throws Exception {
        TextFieldValidator yearValidator = new TextFieldValidator(4, true, onlyFourDigits, "Рік", info.getYear(), "повинен містити 4 цифри");
        TextFieldValidator typeValidator = new TextFieldValidator(-1, true, null, "Вид", info.getType(), null);
        TextFieldValidator universityValidator = new TextFieldValidator(-1, true, null, "ВНЗ", info.getVnz().getShortName(), null);

        typeValidator.validate();
        universityValidator.validate();
        yearValidator.validate();

        if (vnZakladService.findIdVNZakladByVnzShortName(info.getVnz().getShortName()) == null)
            throw new UniversityNotFoundException("ВНЗ з даною назвою не існує");

        return true;
    }
}
