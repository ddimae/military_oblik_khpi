package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Kafedra;

import java.util.List;

public interface KafedraService {

    Kafedra createKafedra(Kafedra kafedra);

    Kafedra getKafedraById(Long id);

    List<Kafedra> getAllKafedra();

    Kafedra updateKafedra(Long id, Kafedra updatedKafedra);

    void deleteKafedra(Long id);

    Kafedra getKafedraByName(String kafName);

    //for filtering
    List<Kafedra> findKafedrasOfFakultet(String fakName);

    //For insert new cafedra
    Long findIDKafedraByKname(String kafName);

    Long findIDKafedraByKabr(String kafAbr);

    Long findIDKafedraByOid(String kafOid);

}
