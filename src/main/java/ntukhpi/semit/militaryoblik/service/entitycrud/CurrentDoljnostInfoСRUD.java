package ntukhpi.semit.militaryoblik.service.entitycrud;

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

    public void addCurrentDoljnost(Long idPerson, String nakaz, String dateStr, String comment,
                                   String nakazDiss, String dateDissStr, String commentDiss) {
        CurrentDoljnostInfo newCurrentDoljnost = createNewInstance(idPerson,
                nakaz, dateStr, comment, nakazDiss, dateDissStr, commentDiss);
        currentDoljnostInfoService.createCurrentDoljnostInfo(newCurrentDoljnost);
    }

    public void updateCurrentDoljnost(Long idCD, Long idPersonForUpdate, String nakaz, String dateStr, String comment,
                                      String nakazDiss, String dateDissStr, String commentDiss) {
        CurrentDoljnostInfo familyMemberUpdate = createNewInstance(idPersonForUpdate,
                nakaz, dateStr, comment, nakazDiss, dateDissStr, commentDiss);
        currentDoljnostInfoService.updateCurrentDoljnostInfo(idCD, familyMemberUpdate);
    }


    private CurrentDoljnostInfo createNewInstance(Long idPerson, String nakaz, String dateStr, String comment,
                                                  String nakazDiss, String dateDissStr, String commentDiss) {

        CurrentDoljnostInfo newCurrentDoljnostInfo = new CurrentDoljnostInfo();
        newCurrentDoljnostInfo.setPrepod(prepodService.getPrepodById(idPerson));
        //start
        newCurrentDoljnostInfo.setNumNakazStart(nakaz);
        newCurrentDoljnostInfo.setDateStart(LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        newCurrentDoljnostInfo.setCommentStart(comment.isBlank()? null : comment);
        //stop
        newCurrentDoljnostInfo.setNumNakazStop(nakazDiss.isBlank()? null : nakazDiss);
        newCurrentDoljnostInfo.setDateStop(
                dateDissStr.isBlank()? null : LocalDate.parse(dateDissStr, DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        newCurrentDoljnostInfo.setCommentStop(commentDiss.isBlank()? null : commentDiss);
        return newCurrentDoljnostInfo;
    }
}
