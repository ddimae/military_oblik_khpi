package ntukhpi.semit.militaryoblik.service;


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
    public List<Kafedra> getAllKafedra() {
        return kafedraRepository.findAll();
    }
}
