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
    public CurrentDoljnostInfo createCurrentDoljnostInfo(CurrentDoljnostInfo currentDoljnostInfo) {
        return currentDoljnostInfoRepository.save(currentDoljnostInfo);
    }

    @Override
    public CurrentDoljnostInfo getCurrentDoljnostInfoById(Long id) {
        return currentDoljnostInfoRepository.findById(id).orElse(null);
    }

    @Override
    public CurrentDoljnostInfo getCurrentDoljnost() {
        List<CurrentDoljnostInfo> list = currentDoljnostInfoRepository.findAll();
        return !list.isEmpty()?list.get(0):null;
    }

    @Override
    public CurrentDoljnostInfo updateCurrentDoljnostInfo(Long id, CurrentDoljnostInfo updateCurrentDoljnostInfo) {
        CurrentDoljnostInfo existingCurrentDoljnostInfo = currentDoljnostInfoRepository.findById(id).orElse(null);
        if (existingCurrentDoljnostInfo != null) {
            updateCurrentDoljnostInfo.setId(existingCurrentDoljnostInfo.getId());
            return currentDoljnostInfoRepository.save(updateCurrentDoljnostInfo);
        }
        return null;
    }

    //ToDo change when special conditions was presented
    @Override
    public void deleteCurrentDoljnostInfo(Long id) {
        currentDoljnostInfoRepository.deleteById(id);
    }

    @Override
    public CurrentDoljnostInfo getCurrentDoljnostInfoByPrepodId(Long id) {
        return currentDoljnostInfoRepository.findCurrentDoljnostInfoByPrepodId(id);
    }
}
