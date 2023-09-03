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
    public List<RegionKharkiv> getAllPrepod() {
        return regionKharkivRepository.findAll();
    }
}
