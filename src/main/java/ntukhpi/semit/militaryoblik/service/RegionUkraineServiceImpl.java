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
    public List<RegionUkraine> getAllCountry() {
        return regionUkraineRepository.findAll();
    }
}
