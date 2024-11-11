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

    public Prepod addEmployee(PrepodAdapter adapter) {
        Prepod newEmployee = createNewInstance(adapter);
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

    private Prepod createNewInstance(PrepodAdapter adapter) {
        Prepod newPrepod = new Prepod();

        newPrepod.setFam(adapter.getSurname());
        newPrepod.setImya(adapter.getName());
        newPrepod.setOtch(adapter.getMidname());
        newPrepod.setKafedra(kafedraService.getKafedraByName(adapter.getCathedra()));
//        if (!birthDate.isBlank())
            newPrepod.setDr(LocalDate.parse(adapter.getBirth(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));

        newPrepod.setDolghnost(dolghnostService.getDolghnostByName(adapter.getPosition()));
        newPrepod.setStepen(stepenService.getStepenByName(adapter.getDegree()));
        newPrepod.setZvanie(zvanieService.getZvanieByName(adapter.getStatus()));

        return newPrepod;
    }
}
