package ntukhpi.semit.militaryoblik.service;


import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Fakultet;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Kafedra;
import ntukhpi.semit.militaryoblik.repository.KafedraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafedraServiceImpl implements KafedraService {

    private final KafedraRepository kafedraRepository;

    @Autowired
    public KafedraServiceImpl(KafedraRepository kafedraRepository) {
        super();
        this.kafedraRepository = kafedraRepository;
    }

    @Override
    public Kafedra createKafedra(Kafedra kafedra) {
        return kafedraRepository.save(kafedra);
    }

    @Override
    public Kafedra getKafedraById(Long id) {
        return kafedraRepository.findById(id).orElse(null);
    }

    @Override
    public List<Kafedra> getAllKafedra() {
        return kafedraRepository.findAll();
    }

    @Override
    public Kafedra updateKafedra(Long id, Kafedra updatedKafedra) {
        Kafedra existingKafedra = kafedraRepository.findById(id).orElse(null);
        if (existingKafedra != null) {
            updatedKafedra.setKid(existingKafedra.getKid());
            return kafedraRepository.save(updatedKafedra);
        }
        return null;
    }

    //ToDo change when special conditions was presented
    @Override
    public void deleteKafedra(Long id) {
        kafedraRepository.deleteById(id);
    }

    @Override
    public Kafedra getKafedraByName(String kafName) {
        return kafedraRepository.getKafedraByKname(kafName);
    }

    @Override
    public List<Kafedra> findKafedrasOfFakultet(String fakName) {
        return kafedraRepository.getAllByFakultet_Fname(fakName);
    }


    @Override
    public Long findIDKafedraByKname(String kafName) {
        Kafedra kafInID = kafedraRepository.getKafedraByKname(kafName);
        return kafInID!=null?kafInID.getKid():null;
    }

    @Override
    public String findAbrKafedraByKname(String kafName) {
        Kafedra kafInID = kafedraRepository.getKafedraByKname(kafName);
        return kafInID!=null?kafInID.getKabr():null;
    }

    @Override
    public Long findIDKafedraByKabr(String kafAbr) {
        Kafedra kafInID = kafedraRepository.getKafedraByKabr(kafAbr);
        return kafInID!=null?kafInID.getKid():null;
    }

    @Override
    public Long findIDKafedraByOid(String kafOid) {
        Kafedra kafInID = kafedraRepository.getKafedraByOid(kafOid);
        return kafInID!=null?kafInID.getKid():null;
    }

}
