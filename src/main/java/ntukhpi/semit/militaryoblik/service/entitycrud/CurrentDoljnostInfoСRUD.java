package ntukhpi.semit.militaryoblik.service.entitycrud;

import ntukhpi.semit.militaryoblik.adapters.CurrentDoljnostInfoAdapter;
import ntukhpi.semit.militaryoblik.entity.CurrentDoljnostInfo;
import ntukhpi.semit.militaryoblik.service.CurrentDoljnostInfoService;
import ntukhpi.semit.militaryoblik.service.PrepodServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class CurrentDoljnostInfoСRUD {

    @Autowired
    PrepodServiceImpl prepodService;

    @Autowired
    CurrentDoljnostInfoService currentDoljnostInfoService;

    public void addCurrentDoljnost(Long idPerson, CurrentDoljnostInfoAdapter adapter) {
        CurrentDoljnostInfo newCurrentDoljnost = createNewInstance(idPerson, adapter);
        currentDoljnostInfoService.createCurrentDoljnostInfo(newCurrentDoljnost);
    }

    public void updateCurrentDoljnost(Long idCD, Long idPersonForUpdate, CurrentDoljnostInfoAdapter adapter) {
        CurrentDoljnostInfo familyMemberUpdate = createNewInstance(idPersonForUpdate, adapter);
        currentDoljnostInfoService.updateCurrentDoljnostInfo(idCD, familyMemberUpdate);
    }


    private CurrentDoljnostInfo createNewInstance(Long idPerson, CurrentDoljnostInfoAdapter adapter) {

        CurrentDoljnostInfo newCurrentDoljnostInfo = new CurrentDoljnostInfo();
        newCurrentDoljnostInfo.setPrepod(prepodService.getPrepodById(idPerson));
        //start
        newCurrentDoljnostInfo.setNumNakazStart(adapter.getNakazStart());
        newCurrentDoljnostInfo.setDateStart(LocalDate.parse(adapter.getDateStart(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        newCurrentDoljnostInfo.setCommentStart(adapter.getCommentStart().isBlank()? null : adapter.getCommentStart());
        //stop
        newCurrentDoljnostInfo.setNumNakazStop(adapter.getNakazStop().isBlank()? null : adapter.getNakazStop());
        newCurrentDoljnostInfo.setDateStop(
                adapter.getDateStop().isBlank()? null : LocalDate.parse(adapter.getDateStop(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        newCurrentDoljnostInfo.setCommentStop(adapter.getCommentStop().isBlank()? null : adapter.getCommentStop());
        return newCurrentDoljnostInfo;
    }
}
