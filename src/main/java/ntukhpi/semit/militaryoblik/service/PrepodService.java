package ntukhpi.semit.militaryoblik.service;


import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;

import java.util.List;

public interface PrepodService {

    List<Prepod> getAllPrepod();

    public Prepod getPrepodById(Long id);
    Prepod updatePrepod(Prepod prepod);
    void deletePrepodById(Long id);
    Prepod getPrepodByExapmle(Prepod prepod);

    Prepod getPrepodByExapmleWithDr(Prepod prepod);

    void savePrepodToDB(List<Prepod> list);
}
