package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.fromasukhpi.RegionUkraine;

import java.util.List;

public interface RegionUkraineService {

    RegionUkraine createRegionUkraine(RegionUkraine regionUkraine);

    RegionUkraine getRegionUkraineById(Long id);

    RegionUkraine getRegionUkraineByName(String name);

    List<RegionUkraine> getAllRegionUkraine();

    RegionUkraine updateRegionUkraine(Long id, RegionUkraine updatedRegionUkraine);

    void deleteRegionUkraine(Long id);
}
