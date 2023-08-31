package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.CurrentDoljnostInfo;
import ntukhpi.semit.militaryoblik.repository.CurrentDoljnostInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrentDoljnostInfoServiceImpl implements CurrentDoljnostInfoService {

    private final CurrentDoljnostInfoRepository currentDoljnostInfoRepository;

    @Autowired
    public CurrentDoljnostInfoServiceImpl(CurrentDoljnostInfoRepository currentDoljnostInfoRepository) {
        this.currentDoljnostInfoRepository = currentDoljnostInfoRepository;
    }

    @Override
    public List<CurrentDoljnostInfo> getAllCountry() {
        return currentDoljnostInfoRepository.findAll();
    }
}
