package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.VSklad;

import java.util.List;

public interface VSkladService {

    VSklad createVSklad(VSklad vSklad);

    VSklad getVSkladById(Long id);
    VSklad getVSkladByName(String zvanieName);

    List<VSklad> getAllVSklad();

    VSklad updateVSklad(Long id, VSklad updatedVSklad);

    void deleteVSklad(Long id);
}
