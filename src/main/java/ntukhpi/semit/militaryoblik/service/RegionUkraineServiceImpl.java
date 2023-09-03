package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.fromasukhpi.RegionUkraine;
import ntukhpi.semit.militaryoblik.repository.RegionUkraineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionUkraineServiceImpl implements RegionUkraineService {

    private final RegionUkraineRepository regionUkraineRepository;

    @Autowired
    public RegionUkraineServiceImpl(RegionUkraineRepository regionUkraineRepository) {
        this.regionUkraineRepository = regionUkraineRepository;
    }

    @Override
    public RegionUkraine createRegionUkraine(RegionUkraine regionUkraine) {
        return regionUkraineRepository.save(regionUkraine);
    }

    @Override
    public RegionUkraine getRegionUkraineById(Long id) {
        return regionUkraineRepository.findById(id).orElse(null);
    }

    @Override
    public RegionUkraine getRegionUkraineByName(String name) {
        return regionUkraineRepository.findByCountryName(name);
    }

    @Override
    public List<RegionUkraine> getAllRegionUkraine() {
        return regionUkraineRepository.findAll();
    }

    @Override
    public RegionUkraine updateRegionUkraine(Long id, RegionUkraine updatedRegionUkraine) {
        RegionUkraine existingRegionUkraine = regionUkraineRepository.findById(id).orElse(null);
        if (existingRegionUkraine != null) {
            updatedRegionUkraine.setId(existingRegionUkraine.getId());
            return regionUkraineRepository.save(updatedRegionUkraine);
        }
        return null;
    }

    //ToDo change when special conditions was presented
    @Override
    public void deleteRegionUkraine(Long id) {
        regionUkraineRepository.deleteById(id);
    }
}
