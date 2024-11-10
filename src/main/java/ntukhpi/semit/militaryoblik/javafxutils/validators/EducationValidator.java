package ntukhpi.semit.militaryoblik.javafxutils.validators;

import ntukhpi.semit.militaryoblik.adapters.EducationAdapter;
import ntukhpi.semit.militaryoblik.entity.VNZaklad;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.IBaseValidator;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.TextFieldValidator;
import ntukhpi.semit.militaryoblik.javafxutils.validators.exceptions.UniversityNotFoundException;
import ntukhpi.semit.militaryoblik.service.VNZakladService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class EducationValidator implements IBaseValidator<EducationAdapter> {
    Pattern onlyFourDigits = Pattern.compile("^[0-9]{4}$");
    Pattern diplomaSeries = Pattern.compile("^[0-9]+.+");

    VNZakladService vnZakladService;

    @Autowired
    public EducationValidator(VNZakladService vNZakladService) {
        this.vnZakladService = vNZakladService;
    }

    @Override
    public boolean validate(EducationAdapter info) throws Exception {
        TextFieldValidator universityValidator = new TextFieldValidator(-1, true, null, "ВНЗ", info.getVnz().getVnzShortName(), null);
        TextFieldValidator formValidator = new TextFieldValidator(-1, true, null, "Форма", info.getForm(), null);
        TextFieldValidator levelValidator = new TextFieldValidator(-1, true, null, "Рівень", info.getLevel(), null);
        TextFieldValidator yearValidator = new TextFieldValidator(4, true, onlyFourDigits, "Рік", info.getYear(), "повинен містити 4 цифри");
        // FIXME: Not obligatory in DB
        TextFieldValidator diplomaSeriesValidator = new TextFieldValidator(-1, false, null, "Серія диплому", info.getDiplomaSeries(), null);
        // FIXME: Not obligatory in DB
        TextFieldValidator diplomaNumberValidator = new TextFieldValidator(-1, true, diplomaSeries, "Номер диплому", info.getDiplomaNumber(), "Номер диплома на початку можe містити лише цифри");

        universityValidator.validate();
        formValidator.validate();
        levelValidator.validate();
        yearValidator.validate();
        diplomaSeriesValidator.validate();
        diplomaNumberValidator.validate();

        if (vnZakladService.findIdVNZakladByVnzShortName(info.getVnz().getVnzShortName()) == null)
            throw new UniversityNotFoundException("ВНЗ з даною назвою не існує");

        return true;
    }
}
