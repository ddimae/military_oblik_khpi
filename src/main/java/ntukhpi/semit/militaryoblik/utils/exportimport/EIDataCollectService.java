package ntukhpi.semit.militaryoblik.utils.exportimport;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import ntukhpi.semit.militaryoblik.adapters.DropdownAdapter;
import ntukhpi.semit.militaryoblik.adapters.ExportAdapter;
import ntukhpi.semit.militaryoblik.adapters.MilitaryPersonAdapter;
import ntukhpi.semit.militaryoblik.adapters.PrepodAdapter;
import ntukhpi.semit.militaryoblik.entity.MilitaryPerson;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EIDataCollectService {
    private final PrepodService prepodService;
    private final MilitaryPersonService militaryPersonService;
    private final StepenService degreeService;
    private final ZvanieService zvanieService;
    private final FakultetService fakultetService;
    private final KafedraService kafedraService;
    private final DolghnostService dolghnostService;
    private final VSkladService vSkladService;
    private final VZvanieService vZvanieService;
    private final VoenkomatService voenkomatService;
    private final CountryService countryService;
    private final RegionUkraineService regionUkraineService;
    private final RegionKharkivService regionKharkivService;
    private final VNZakladService vnZakladService;

    @Autowired
    public EIDataCollectService(PrepodService prepodService,
                                MilitaryPersonService militaryPersonService,
                                StepenService stepenService,
                                ZvanieService zvanieService,
                                FakultetService fakultetService,
                                KafedraService kafedraService,
                                DolghnostService dolghnostService,
                                VSkladService vSkladService,
                                VZvanieService vZvanieService,
                                VoenkomatService voenkomatService,
                                CountryService countryService,
                                RegionUkraineService regionUkraineService,
                                RegionKharkivService regionKharkivService,
                                VNZakladService vnZakladService) {
        this.prepodService = prepodService;
        this.militaryPersonService = militaryPersonService;
        this.degreeService = stepenService;
        this.zvanieService = zvanieService;
        this.fakultetService = fakultetService;
        this.kafedraService = kafedraService;
        this.dolghnostService = dolghnostService;
        this.vSkladService = vSkladService;
        this.vZvanieService = vZvanieService;
        this.voenkomatService = voenkomatService;
        this.countryService = countryService;
        this.regionUkraineService = regionUkraineService;
        this.regionKharkivService = regionKharkivService;
        this.vnZakladService = vnZakladService;
    }

    public DropdownAdapter getDropdownAdapter() {
        return new DropdownAdapter(degreeService, zvanieService, fakultetService,
                                    kafedraService, dolghnostService, vSkladService,
                                    vZvanieService, voenkomatService, countryService,
                                    regionUkraineService, regionKharkivService, vnZakladService);
    }

    @Transactional
    public ExportAdapter collectData(Long prepodId) throws Exception {
        Prepod prepod = prepodService.getPrepodById(prepodId);
        MilitaryPerson militaryPerson = militaryPersonService.getMilitaryPersonByPrepod(prepod);

        if (prepod == null || militaryPerson == null) // FIXME: refactor. maybe add try/catch
            throw new Exception("Prepod or MilitaryPerson is not defined");

        return new ExportAdapter(prepod, militaryPerson);
    }

    public void fillIds(ExportAdapter ioAdapter) {
        PrepodAdapter ioPrepod = ioAdapter.getPrepod();
        Prepod foundPrepod = prepodService.getPrepodByExapmleFIO(new Prepod(ioPrepod.getSurname(), ioPrepod.getName(), ioPrepod.getMidname(), null));

        MilitaryPersonAdapter ioMilitary = ioAdapter.getMilitary();
        MilitaryPerson foundMilitary = militaryPersonService.getMilitaryPersonByPrepod(foundPrepod);

        if (foundPrepod != null) {
            ioPrepod.setId(foundPrepod.getId());
            ioMilitary.setId(foundMilitary.getId());
        }




    }
}
