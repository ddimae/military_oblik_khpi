package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.VZvanie;

import java.util.List;

public interface VZvanieService {

    VZvanie createVZvanie(VZvanie vZvanie);

    VZvanie getVZvanieById(Long id);

    List<VZvanie> getAllVZvanie();

    VZvanie updateVZvanie(Long id, VZvanie updatedVZvanie);

    void deleteVZvanie(Long id);
}
