package ntukhpi.semit.militaryoblik.utils.exportimport;

import ntukhpi.semit.militaryoblik.adapters.ExportAdapter;
import ntukhpi.semit.militaryoblik.entity.MilitaryPerson;
import ntukhpi.semit.militaryoblik.entity.PersonalData;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Fakultet;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.service.FakultetService;
import ntukhpi.semit.militaryoblik.service.MilitaryPersonService;
import ntukhpi.semit.militaryoblik.service.PersonalDataService;
import ntukhpi.semit.militaryoblik.service.PrepodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EIDataCollectService {
    private final PrepodService prepodService;
    private final MilitaryPersonService militaryPersonService;
    private final PersonalDataService personalDataService;

    @Autowired
    public EIDataCollectService(PrepodService prepodService,
                                MilitaryPersonService militaryPersonService,
                                PersonalDataService personalDataService) {
        this.prepodService = prepodService;
        this.militaryPersonService = militaryPersonService;
        this.personalDataService = personalDataService;
    }

    @Transactional
    public ExportAdapter collectData(Long prepodId) throws Exception {
        Prepod prepod = prepodService.getPrepodById(prepodId);
        MilitaryPerson militaryPerson = militaryPersonService.getMilitaryPersonByPrepod(prepod);
        PersonalData personalData = personalDataService.getPersonalDataByPrepodId(prepodId);

        if (prepod == null || militaryPerson == null || personalData == null) // FIXME: refactor. maybe add try/catch
            throw new Exception("Prepod or MilitaryPerson is not defined");

        return new ExportAdapter(prepod, militaryPerson, personalData);
    }
}
