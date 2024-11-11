package ntukhpi.semit.militaryoblik.service.entitycrud;

import ntukhpi.semit.militaryoblik.adapters.ContactInfoAdapter;
import ntukhpi.semit.militaryoblik.entity.PersonalData;
import ntukhpi.semit.militaryoblik.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContactsCRUD {
    @Autowired
    PersonalDataService personalDataService;
    @Autowired
    PrepodService prepodService;
    @Autowired
    CountryService countryService;
    @Autowired
    RegionUkraineService regionUkraineService;


    public PersonalData updatePersonalData(Long idPerson, ContactInfoAdapter adapter) {
        PersonalData personalData = personalDataService.getPersonalDataByPrepodId(idPerson);
        if (personalData == null) {
            personalData = new PersonalData();
            personalData.setPrepod(prepodService.getPrepodById(idPerson));
            personalData.setCountry(countryService.getCountryByName("Україна"));
            //За замовченням встановлений міський номер ВОС НТУ ХПІ та адреса НТУ ХПІ
            personalData.setCity("Харків");
            personalData.setRowAddress("вул.Кирпичова, б.2");
            personalData.setPhoneMain("+380577004033");
            personalData = personalDataService.createPersonalData(personalData);
        }
        //Registration Address
        personalData.setCountry(countryService.getCountryByName(adapter.getCountry()));
        personalData.setPostIndex(adapter.getIndex());
        personalData.setCity(adapter.getCity());
        personalData.setRowAddress(adapter.getAddress());
        boolean isUkraine = String.valueOf(adapter.getCountry()).equals("Україна");

        personalData.setOblastUA((adapter.getRegion() != null && !adapter.getRegion().isBlank()) &&
                isUkraine ? regionUkraineService.getRegionUkraineByName(adapter.getRegion()) : null);

        //Fact Address
        personalData.setFactСountry(countryService.getCountryByName(adapter.getCountryFact()));
        personalData.setFactPostIndex(adapter.getIndexFact());
        personalData.setFactCity(adapter.getCityFact());
        personalData.setFactRowAddress(adapter.getAddressFact());
        boolean isUkraineFact = String.valueOf(adapter.getCountryFact()).equals("Україна");
        personalData.setFactOblastUA((adapter.getRegionFact() !=null && !adapter.getRegionFact().isBlank())&&isUkraineFact?
                regionUkraineService.getRegionUkraineByName(adapter.getRegionFact()):null);

        //Phones
        personalData.setPhoneMain(adapter.getMainPhone());
        personalData.setPhoneDop(adapter.getSecondPhone());

        return personalDataService.updatePersonalData(personalData);
    }



}
