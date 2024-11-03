package ntukhpi.semit.militaryoblik.adapters;

import lombok.Getter;
import lombok.Setter;
import ntukhpi.semit.militaryoblik.entity.VNZaklad;
import ntukhpi.semit.militaryoblik.entity.VSklad;
import ntukhpi.semit.militaryoblik.entity.VZvanie;
import ntukhpi.semit.militaryoblik.entity.Voenkomat;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.*;
import ntukhpi.semit.militaryoblik.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DropdownAdapter implements IBaseAdapter {
    private String[] degrees;
    private String[] titles;
    private String[] institutes;
    private String[] cathedras;
    private String[] positions;
    private String[] milCompositions;
    private String[] milTitles;
    private String[] milOffices;

    private String[] countries;
    private String[] regions;
    private String[] regionsKh;

    private String[] universities;

    public DropdownAdapter(StepenService stepenService, ZvanieService zvanieService, FakultetService fakultetService,
                           KafedraService kafedraService, DolghnostService dolghnostService, VSkladService vSkladService,
                           VZvanieService vZvanieService, VoenkomatService voenkomatService, CountryService countryService,
                           RegionUkraineService regionUkraineService, RegionKharkivService regionKharkivService,
                           VNZakladService vnZakladService) {
        degrees = stepenService.getAllStepen().stream().map(Stepen::toString).toArray(String[]::new);
        titles = zvanieService.getAllZvanie().stream().map(Zvanie::toString).toArray(String[]::new);
        institutes = fakultetService.getAllFak().stream().map(Fakultet::toString).toArray(String[]::new);
        cathedras = kafedraService.getAllKafedra().stream().map(Kafedra::toString).toArray(String[]::new);
        positions = dolghnostService.getAllDolghnost().stream().map(Dolghnost::toString).toArray(String[]::new);
        milCompositions = vSkladService.getAllVSklad().stream().map(VSklad::toString).toArray(String[]::new);
        milTitles = vZvanieService.getAllVZvanie().stream().map(VZvanie::toString).toArray(String[]::new);
        milOffices = voenkomatService.getAllVoenkomat().stream().map(Voenkomat::toString).toArray(String[]::new);

        countries = countryService.getAllCountry().stream().map(Country::toString).toArray(String[]::new);
        regions = regionUkraineService.getAllRegionUkraine().stream().map(RegionUkraine::toString).toArray(String[]::new);
        regionsKh = regionKharkivService.getAllRegionKharkiv().stream().map(RegionKharkiv::toString).toArray(String[]::new);

        universities = vnZakladService.getAllVNZaklad().stream().map(VNZaklad::toString).toArray(String[]::new);
    }

    public List<String[]> toList() {
        return List.of(degrees, titles, institutes,
                cathedras, positions, milCompositions,
                milTitles, milOffices, countries,
                regions, regionsKh, universities);
    }
}
