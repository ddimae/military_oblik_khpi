package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.CurrentDoljnostInfo;

public interface CurrentDoljnostInfoService {

    CurrentDoljnostInfo createCurrentDoljnostInfo(CurrentDoljnostInfo currentDoljnostInfo);

    CurrentDoljnostInfo getCurrentDoljnostInfoById(Long id);

    CurrentDoljnostInfo getCurrentDoljnost();

    CurrentDoljnostInfo updateCurrentDoljnostInfo(Long id, CurrentDoljnostInfo updateCurrentDoljnostInfo);

    void deleteCurrentDoljnostInfo(Long id);

    CurrentDoljnostInfo getCurrentDoljnostInfoByPrepodId(Long id);
}
