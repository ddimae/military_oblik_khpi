package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.fromasukhpi.RegionKharkiv;
import ntukhpi.semit.militaryoblik.repository.RegionKharkivRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionKharkivServiceImpl implements RegionKharkivService {

    private final RegionKharkivRepository regionKharkivRepository;

    @Autowired
    public RegionKharkivServiceImpl(RegionKharkivRepository regionKharkivRepository) {
        this.regionKharkivRepository = regionKharkivRepository;
    }

    @Override
    public RegionKharkiv createRegionKharkiv(RegionKharkiv regionKharkiv) {
        return regionKharkivRepository.save(regionKharkiv);
    }

    @Override
    public RegionKharkiv getRegionKharkivById(Long id) {
        return regionKharkivRepository.findById(id).orElse(null);
    }

    @Override
    public List<RegionKharkiv> getAllRegionKharkiv() {
        return regionKharkivRepository.findAll();
    }

    @Override
    public RegionKharkiv updateRegionKharkiv(Long id, RegionKharkiv updatedRegionKharkiv) {
        RegionKharkiv existingRegionKharkiv = regionKharkivRepository.findById(id).orElse(null);
        if (existingRegionKharkiv != null) {
            updatedRegionKharkiv.setId(existingRegionKharkiv.getId());
            return regionKharkivRepository.save(updatedRegionKharkiv);
        }
        return null;
    }

    //ToDo change when special conditions was presented
    @Override
    public void deleteRegionKharkiv(Long id) {
        regionKharkivRepository.deleteById(id);
    }
}
