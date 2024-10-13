package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.fromasukhpi.*;
import ntukhpi.semit.militaryoblik.repository.PrepodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PrepodServiceImpl implements PrepodService {

    private final PrepodRepository prepodRepository;
    private final KafedraServiceImpl kafedraServiceImpl;
    private final DolghnostServiceImpl dolghnostServiceImpl;
    private final StepenServiceImpl stepenServiceImpl;
    private final ZvanieServiceImpl zvanieServiceImpl;

    @Autowired
    public PrepodServiceImpl(PrepodRepository prepodRepository, KafedraServiceImpl kafedraServiceImpl, DolghnostServiceImpl dolghnostServiceImpl, StepenServiceImpl stepenServiceImpl, ZvanieServiceImpl zvanieServiceImpl) {
        this.prepodRepository = prepodRepository;
        this.kafedraServiceImpl = kafedraServiceImpl;
        this.dolghnostServiceImpl = dolghnostServiceImpl;
        this.stepenServiceImpl = stepenServiceImpl;
        this.zvanieServiceImpl = zvanieServiceImpl;
    }

    @Override
    public List<Prepod> getAllPrepod() {
        return prepodRepository.findAll();
    }

    public Prepod savePrepod(Prepod prepod) {
        return prepodRepository.save(prepod);
    }

    //Метод сохранения данных через их текстовые значения
    public Prepod savePrepod(String fam, String name, String otch,
                             LocalDate dr,
                             String pidrName, String posadaName,
                             String stepenName, String zvanieName, String email) {
        Kafedra kafedra = kafedraServiceImpl.getKafedraByName(pidrName);
        Dolghnost dolghnost = dolghnostServiceImpl.getDolghnostByName(posadaName);
        Stepen stepen = null;
        if (!"не має".equals(stepenName)) {
            stepen = stepenServiceImpl.getStepenByName(stepenName);
        }
        Zvanie zvanie = null;
        if (!"не має".equals(zvanieName)) {
            zvanie = zvanieServiceImpl.getZvanieByName(zvanieName);
        }
        Prepod prepToSave = new Prepod(fam,name,otch,dr,kafedra,dolghnost,stepen,zvanie,email);
        return prepodRepository.save(prepToSave);
    }

    //Метод сохранения данных через их Id
    public Prepod savePrepod(Long pidrId, String fam, String name, String otch,
                             Long posadaId,Long zvanieId, Long stepenId, String email,String drString) {
        Kafedra kafedra = kafedraServiceImpl.getKafedraById(pidrId);
        Dolghnost dolghnost = dolghnostServiceImpl.getDolghnostById(posadaId);
        Stepen stepen = null;
        if (stepenId!=null) {
            stepen = stepenServiceImpl.getStepenById(stepenId);
        }
        Zvanie zvanie = null;
        if (zvanieId!=null) {
            zvanie = zvanieServiceImpl.getZvanieById(zvanieId);
        }
        LocalDate dr = LocalDate.parse(drString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Prepod prepToSave = new Prepod(fam,name,otch,dr,kafedra,dolghnost,stepen,zvanie,email);
        return prepodRepository.save(prepToSave);
    }

    @Override
    public Prepod getPrepodById(Long id) {
        return prepodRepository.findById(id).get();
    }

    @Override
    public Prepod updatePrepod(Prepod prepod) {
        return prepodRepository.save(prepod);
    }

    @Override
    public void deletePrepodById(Long id) {
        prepodRepository.deleteById(id);
    }

    @Override
    public Prepod getPrepodByExapmle(Prepod prepod) {
        return prepodRepository.getPrepodByFamAndImyaAndOtchAndKafedra_Kid(prepod.getFam(),
                prepod.getImya(), prepod.getOtch(), prepod.getKafedra().getKid());
    }

    @Override
    public Prepod getPrepodByExapmleWithDr(Prepod prepod) {
        return prepodRepository.getPrepodByFamAndImyaAndOtchAndDr(prepod.getFam(),
                prepod.getImya(), prepod.getOtch(), prepod.getDr());
    }

    @Override
    public Prepod getPrepodByExapmleFIO(Prepod prepod) {
        return prepodRepository.getPrepodByFamAndImyaAndOtch(prepod.getFam(),
                prepod.getImya(), prepod.getOtch());
    }


    @Override
    //Метод для простого оновлення таблиці Prepod, наприклад із бд нту хпі
    //Якщо робити заповнення більш детальне, то треба "заглиблюватися" в структуру таблиць бази даних,
    //щоб врахувати, яку і куда інфу треба розпихати
    public void savePrepodToDB(List<Prepod> list) {
        list.stream().forEach(prep -> {
            prepodRepository.save(prep);
            Prepod prepInDB = getPrepodByExapmle(prep);
            if (prepInDB == null) {
                savePrepod(prepInDB);
            } else {
                prepInDB.setFam(prep.getFam());
                prepInDB.setImya(prep.getImya());
                prepInDB.setOtch(prep.getOtch());
                prepInDB.setDr(prep.getDr());
                prepInDB.setKafedra(prep.getKafedra());
                prepInDB.setDolghnost(prep.getDolghnost());
                prepInDB.setZvanie(prep.getZvanie());
                prepInDB.setStepen(prep.getStepen());
                prepInDB.setEmail(prep.getEmail());
                updatePrepod(prepInDB);

            }
        });
    }
}
