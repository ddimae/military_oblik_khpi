package ntukhpi.semit.militaryoblik.service.entitycrud;

import ntukhpi.semit.militaryoblik.adapters.PrepodAdapter;
import ntukhpi.semit.militaryoblik.entity.Education;
import ntukhpi.semit.militaryoblik.entity.MilitaryPerson;
import ntukhpi.semit.militaryoblik.entity.Voenkomat;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Kafedra;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class EmployeeCRUD {
    @Autowired
    PrepodService prepodService;
    @Autowired
    KafedraService kafedraService;
    @Autowired
    DolghnostService dolghnostService;
    @Autowired
    StepenService stepenService;
    @Autowired
    ZvanieService zvanieService;
    @Autowired
    MilitaryPersonService militaryPersonService;
    @Autowired
    VoenkomatService voenkomatService;

    public Prepod addEmployee(String surname, String name, String midname,
                             String kafedraFullName,
                             String birthDate,
                             String dolghnost, String stepen, String zvanie) {
        Prepod newEmployee = createNewInstance(surname, name, midname, kafedraFullName,
                                                   birthDate, dolghnost, stepen, zvanie);
        return prepodService.createPrepod(newEmployee);
    }

//    public void updateEducation(Long idPersonForUpdate, String form, String level,
//                                String vnzName, String year, String diplomaNumber,
//                                String diplomaSeries, String specialty, String qualification) {
//        Education educationUpdate = createNewInstance(idPersonForUpdate, form, level,
//                vnzName, year, diplomaNumber, diplomaSeries, specialty, qualification);
//        educationService.updateEducation(idPersonForUpdate,educationUpdate);
//    }
    public void deleteEmployee(Long idPrepod) {
        Prepod delPrepod = prepodService.getPrepodById(idPrepod);
        MilitaryPerson mp = militaryPersonService.getMilitaryPersonByPrepod(delPrepod);
        militaryPersonService.deleteMilitaryPerson(mp.getId());
        prepodService.deletePrepodById(delPrepod.getId());
    }

    public void deleteEmployee(PrepodAdapter prepodAdapter) {
        Prepod delPrepod = findPrepodByKeySet(prepodAdapter.getSurname(),prepodAdapter.getName(),
                                         prepodAdapter.getMidname(),prepodAdapter.getCathedra());
        MilitaryPerson mp = militaryPersonService.getMilitaryPersonByPrepod(delPrepod);
        militaryPersonService.deleteMilitaryPerson(mp.getId());
        prepodService.deletePrepodById(delPrepod.getId());
    }

    public Prepod findPrepodByKeySet(String surname, String name, String midname,
                                   String kafedraFullName) {

        Kafedra kafEmpl = kafedraService.getKafedraByName(kafedraFullName);
        return kafEmpl==null? null : prepodService.getEmployeeByFullKeySet(surname,name,midname,kafedraFullName);
    }

    public Prepod adapterPrepodToPrepod(PrepodAdapter prepodAdapter) {
        return findPrepodByKeySet(prepodAdapter.getSurname(),prepodAdapter.getName(),
                prepodAdapter.getMidname(),prepodAdapter.getCathedra());
    }

    private Prepod createNewInstance(String surname, String name, String midname,
                                     String kafedraFullName,
                                        String birthDate,
                                        String dolghnost, String stepen, String zvanie) {
        Prepod newPrepod = new Prepod();

        newPrepod.setFam(surname);
        newPrepod.setImya(name);
        newPrepod.setOtch(midname);
        newPrepod.setKafedra(kafedraService.getKafedraByName(kafedraFullName));
//        if (!birthDate.isBlank())
            newPrepod.setDr(LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("dd.MM.yyyy")));

        newPrepod.setDolghnost(dolghnostService.getDolghnostByName(dolghnost));
        newPrepod.setStepen(stepenService.getStepenByName(stepen));
        newPrepod.setZvanie(zvanieService.getZvanieByName(zvanie));

        return newPrepod;
    }
}
