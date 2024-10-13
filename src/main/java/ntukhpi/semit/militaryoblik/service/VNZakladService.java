package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.VNZaklad;

import java.util.List;

public interface VNZakladService {

    VNZaklad createVNZaklad(VNZaklad vNZaklad);

    VNZaklad getVNZakladById(Long id);

    List<VNZaklad> getAllVNZaklad();

    VNZaklad updateVNZaklad(Long id, VNZaklad updatedVNZaklad);

    void deleteVNZaklad(Long id);


    Long findVNZakladByVnzName(String vnzName);

    Long findVNZakladByVnzShortName(String vnzShortName);
}
