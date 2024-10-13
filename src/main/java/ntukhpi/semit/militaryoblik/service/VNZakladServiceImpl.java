package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.VNZaklad;
import ntukhpi.semit.militaryoblik.repository.VNZakladRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VNZakladServiceImpl implements VNZakladService {

    private final VNZakladRepository vnZakladRepository;

    @Autowired
    public VNZakladServiceImpl(VNZakladRepository vnZakladRepository) {
        this.vnZakladRepository = vnZakladRepository;
    }

    @Override
    public VNZaklad createVNZaklad(VNZaklad vNZaklad) {
        return vnZakladRepository.save(vNZaklad);
    }

    @Override
    public VNZaklad getVNZakladById(Long id) {
        return vnZakladRepository.findById(id).orElse(null);
    }

    @Override
    public List<VNZaklad> getAllVNZaklad() {
        return vnZakladRepository.findAll();
    }

    @Override
    public VNZaklad updateVNZaklad(Long id, VNZaklad updatedVNZaklad) {
        VNZaklad existingVNZaklad = vnZakladRepository.findById(id).orElse(null);
        if (existingVNZaklad != null) {
            updatedVNZaklad.setId(existingVNZaklad.getId());
            return vnZakladRepository.save(updatedVNZaklad);
        }
        return null;
    }

    //ToDo change when special conditions was presented
    @Override
    public void deleteVNZaklad(Long id) {
        vnZakladRepository.deleteById(id);
    }


    @Override
    public Long findVNZakladByVnzName(String vnzName) {
        VNZaklad vnzInDB = vnZakladRepository.getVNZakladByVnzName(vnzName);
        return vnzInDB!=null?vnzInDB.getId():null;
    }

    @Override
    public Long findVNZakladByVnzShortName(String vnzShortName) {
        VNZaklad vnzInDB = vnZakladRepository.getVNZakladByVnzShortName(vnzShortName);
        return vnzInDB!=null?vnzInDB.getId():null;
    }
}
