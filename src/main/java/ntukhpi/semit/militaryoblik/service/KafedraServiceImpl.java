package ntukhpi.semit.militaryoblik.service;


import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Kafedra;
import ntukhpi.semit.militaryoblik.repository.KafedraRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafedraServiceImpl implements KafedraService {

    private KafedraRepository kafedraRepository;

    public KafedraServiceImpl(KafedraRepository kafedraRepository) {
        super();
        this.kafedraRepository = kafedraRepository;
    }

    @Override
    public List<Kafedra> getAllKafedra() {
        return kafedraRepository.findAll();
    }
}
