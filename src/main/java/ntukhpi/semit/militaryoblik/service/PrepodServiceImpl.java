package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.repository.PrepodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrepodServiceImpl implements PrepodService {

    private final PrepodRepository prepodRepository;

    @Autowired
    public PrepodServiceImpl(PrepodRepository prepodRepository) {
        this.prepodRepository = prepodRepository;
    }

    @Override
    public List<Prepod> getAllPrepod() {
        return prepodRepository.findAll();
    }

    public Prepod savePrepod(Prepod prepod) {
        return prepodRepository.save(prepod);
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
