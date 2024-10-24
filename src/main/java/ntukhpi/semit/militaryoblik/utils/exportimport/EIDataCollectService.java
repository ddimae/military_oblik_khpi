package ntukhpi.semit.militaryoblik.utils.exportimport;

import ntukhpi.semit.militaryoblik.adapters.ExportAdapter;
import ntukhpi.semit.militaryoblik.entity.MilitaryPerson;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
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

    @Autowired
    public EIDataCollectService(PrepodService prepodService,
                                MilitaryPersonService militaryPersonService,
                                PersonalDataService personalDataService) {
        this.prepodService = prepodService;
        this.militaryPersonService = militaryPersonService;
    }

    @Transactional
    public ExportAdapter collectData(Long prepodId) throws Exception {
        Prepod prepod = prepodService.getPrepodById(prepodId);
        MilitaryPerson militaryPerson = militaryPersonService.getMilitaryPersonByPrepod(prepod);

        if (prepod == null || militaryPerson == null) // FIXME: refactor. maybe add try/catch
            throw new Exception("Prepod or MilitaryPerson is not defined");

        return new ExportAdapter(prepod, militaryPerson);
    }
}
