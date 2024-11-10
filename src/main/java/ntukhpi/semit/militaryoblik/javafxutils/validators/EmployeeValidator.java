package ntukhpi.semit.militaryoblik.javafxutils.validators;

import ntukhpi.semit.militaryoblik.adapters.PrepodAdapter;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.DateFieldValidator;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.IBaseValidator;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.TextFieldValidator;
import ntukhpi.semit.militaryoblik.javafxutils.validators.exceptions.CathedraNotFoundException;
import ntukhpi.semit.militaryoblik.javafxutils.validators.exceptions.InstituteNotFoundException;
import ntukhpi.semit.militaryoblik.javafxutils.validators.exceptions.PositionNotFoundException;
import ntukhpi.semit.militaryoblik.service.DolghnostService;
import ntukhpi.semit.militaryoblik.service.FakultetService;
import ntukhpi.semit.militaryoblik.service.KafedraService;
import ntukhpi.semit.militaryoblik.service.PrepodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.util.regex.Pattern;

@Component
public class EmployeeValidator implements IBaseValidator<PrepodAdapter> {
    Pattern ukrWords = Pattern.compile("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії\\-\\s]+$");
    Pattern ukrDateRegex = Pattern.compile("^\\d{2}\\.\\d{2}\\.\\d{4}$");

    KafedraService kafedraService;
    FakultetService fakultetService;
    DolghnostService dolghnostService;
    PrepodService prepodService;

    @Autowired
    public EmployeeValidator(KafedraService kafedraService,
                             FakultetService fakultetService,
                             DolghnostService dolghnostService,
                             PrepodService prepodService) {
        this.kafedraService = kafedraService;
        this.fakultetService = fakultetService;
        this.dolghnostService = dolghnostService;
        this.prepodService = prepodService;
    }

    @Override
    public boolean validate(PrepodAdapter info) throws Exception {
        TextFieldValidator instituteValidator = new TextFieldValidator(-1, true, null, "Інститут", info.getInstitute(), null);
        TextFieldValidator cathedraValidator = new TextFieldValidator(-1, true, null, "Кафедра", info.getCathedra(), null);
        TextFieldValidator surnameValidator = new TextFieldValidator(40, true, ukrWords, "Прізвище", info.getSurname(), "повинно містити українські літери");
        TextFieldValidator nameValidator = new TextFieldValidator(30, true, ukrWords, "Ім'я", info.getName(), "повинно містити українські літери");
        TextFieldValidator midnameValidator = new TextFieldValidator(30, true, ukrWords, "По батькові", info.getMidname(), "повинно містити українські літери");
        // FIXME: Not obligatory in DB
        DateFieldValidator dateValidator = new DateFieldValidator(true, ukrDateRegex, "Дата народження", info.getBirth(), "повинно мати формат дати: dd.mm.yyyy");
        TextFieldValidator positionValidator = new TextFieldValidator(-1, true, null, "Посада", info.getPosition(), null);

        instituteValidator.validate();
        cathedraValidator.validate();
        surnameValidator.validate();
        nameValidator.validate();
        midnameValidator.validate();
        dateValidator.validate();
        positionValidator.validate();

        if (prepodService.getEmployeeByFullKeySet(info.getSurname(), info.getName(), info.getMidname(), info.getCathedra()) != null)
            throw new InstanceAlreadyExistsException("Людина з таким ПІБ на даній кафедрі вже існує");

        if (dolghnostService.findIDPosadaByName(info.getPosition()) == 0)
            throw new Exception("Поле 'Посада' є обов'язковим для заповнення");
        if (dolghnostService.getDolghnostByName(info.getPosition()) == null)
            throw new PositionNotFoundException("Такої посади не знайдено");

        if (fakultetService.findFakultetByFname(info.getInstitute()) == null)
            throw new InstituteNotFoundException("Інститута з такою назвою не існує");

        if (kafedraService.getKafedraByName(info.getCathedra()) == null)
            throw new CathedraNotFoundException("Кафедри з такою назвою не існує");
        if (!kafedraService.findKafedrasOfFakultet(info.getInstitute()).stream().anyMatch((k) -> k.getKname().equals(info.getCathedra())))
            throw new CathedraNotFoundException("Такої кафедри при даному інституті не існує");

        return true;
    }
}
