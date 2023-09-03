package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.Voenkomat;

import java.util.List;

public interface VoenkomatService {

    Voenkomat createVoenkomat(Voenkomat voenkomat);

    Voenkomat getVoenkomatById(Long id);

    List<Voenkomat> getAllVoenkom();
    Voenkomat createVoenkomat(Voenkomat voenkomat);

    Voenkomat getVoenkomatById(Long id);

    Voenkomat updateVoenkomat(Long id, Voenkomat updatedVoenkomat);

    void deleteVoenkomat(Long id);

    Voenkomat updateVoenkomat(Long id, Voenkomat updatedVoenkomat);

    void deleteVoenkomat(Long id);
}
