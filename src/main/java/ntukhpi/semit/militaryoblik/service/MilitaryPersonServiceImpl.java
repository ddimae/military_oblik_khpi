package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.MilitaryPerson;
import ntukhpi.semit.militaryoblik.entity.VSklad;
import ntukhpi.semit.militaryoblik.entity.VZvanie;
import ntukhpi.semit.militaryoblik.entity.Voenkomat;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.repository.MilitaryPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MilitaryPersonServiceImpl implements MilitaryPersonService {

    private final MilitaryPersonRepository militaryPersonRepository;

    private final VoenkomatServiceImpl voenkomatServiceImpl;
    private final VSkladServiceImpl vskladServiceImpl;
    private final VZvanieServiceImpl vzvanieServiceImpl;
    @Autowired
    public MilitaryPersonServiceImpl(MilitaryPersonRepository militaryPersonRepository, VoenkomatServiceImpl voenkomatServiceImpl, VSkladServiceImpl vskladServiceImpl, VZvanieServiceImpl vzvanieServiceImpl) {
        this.militaryPersonRepository = militaryPersonRepository;
        this.voenkomatServiceImpl = voenkomatServiceImpl;
        this.vskladServiceImpl = vskladServiceImpl;
        this.vzvanieServiceImpl = vzvanieServiceImpl;
    }

    @Override
    public MilitaryPerson createMilitaryPerson(MilitaryPerson militaryPerson) {
        return militaryPersonRepository.save(militaryPerson);
    }

    @Override
    public MilitaryPerson getMilitaryPersonById(Long id) {
        return militaryPersonRepository.findById(id).orElse(null);
    }

    @Override
    public MilitaryPerson getMilitaryPersonByPrepod(Prepod prep) {
        return militaryPersonRepository.findMilitaryPersonByPrepod(prep);
    }

    @Override
    public List<MilitaryPerson> getAllMilitaryPerson() {
        return militaryPersonRepository.findAll();
    }

    @Override
    public MilitaryPerson updateMilitaryPerson(Long id, MilitaryPerson updateMilitaryPerson) {
        MilitaryPerson existingMilitaryPerson = militaryPersonRepository.findById(id).orElse(null);
        if (existingMilitaryPerson != null) {
            updateMilitaryPerson.setId(existingMilitaryPerson.getId());
            return militaryPersonRepository.save(updateMilitaryPerson);
        }
        return null;
    }

    //ToDo change when special conditions was presented
    @Override
    public void deleteMilitaryPerson(Long id) {
        militaryPersonRepository.deleteById(id);
    }

    @Override
    public MilitaryPerson saveMilitaryInfo(Prepod prep, String voenkomatName, String vzvanieName, String vskladName,
                                 String vos, int vCategory, String grupaObliku, String pridatnist, String reserv) {
        MilitaryPerson militaryPersonToSave = new MilitaryPerson();
        Voenkomat voenkomat = voenkomatServiceImpl.getVoenkomatByName(voenkomatName);
        VZvanie vZvanie = vzvanieServiceImpl.getVzvanieByName(vzvanieName);
        VSklad vSklad = vskladServiceImpl.getVSkladByName(vskladName);
        militaryPersonToSave.setPrepod(prep);
        militaryPersonToSave.setVoenkomat(voenkomat);
        militaryPersonToSave.setVZvanie(vZvanie);
        militaryPersonToSave.setVSklad(vSklad);
        militaryPersonToSave.setVos(vos);
        militaryPersonToSave.setVCategory(vCategory);
        militaryPersonToSave.setVGrupa(grupaObliku);
        militaryPersonToSave.setVPrydatnist(pridatnist);
        militaryPersonToSave.setReserv(reserv);
        return createMilitaryPerson(militaryPersonToSave);
    }
}
