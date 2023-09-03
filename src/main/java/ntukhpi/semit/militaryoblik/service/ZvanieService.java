package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Zvanie;

import java.util.List;

public interface ZvanieService {

    Zvanie createZvanie(Zvanie zvanie);

    Zvanie getZvanieById(Long id);

    List<Zvanie> getAllZvanie();

    Zvanie updateZvanie(Long id, Zvanie updatedZvanie);

    void deleteZvanie(Long id);
}
