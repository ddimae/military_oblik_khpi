package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.CurrentDoljnostInfo;

import java.util.List;

public interface CurrentDoljnostInfoService {

    CurrentDoljnostInfo createCurrentDoljnostInfo(CurrentDoljnostInfo currentDoljnostInfo);

    CurrentDoljnostInfo getCurrentDoljnostInfoById(Long id);

    List<CurrentDoljnostInfo> getAllCurrentDoljnost();

    CurrentDoljnostInfo updateCurrentDoljnostInfo(Long id, CurrentDoljnostInfo updateCurrentDoljnostInfo);

    void deleteCurrentDoljnostInfo(Long id);
}
