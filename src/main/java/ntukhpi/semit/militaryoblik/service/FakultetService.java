package ntukhpi.semit.militaryoblik.service;


import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Fakultet;

import java.util.List;

public interface FakultetService {

    Fakultet createFakultet(Fakultet fakultet);

    Fakultet getFakultetById(Long id);

    List<Fakultet> getAllFak();

    Fakultet updateFakultet(Long id, Fakultet updatedFakultet);

    void deleteFakultet(Long id);


}
