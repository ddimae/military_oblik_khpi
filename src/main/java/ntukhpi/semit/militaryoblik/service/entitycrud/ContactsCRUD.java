package ntukhpi.semit.militaryoblik.service.entitycrud;

import ntukhpi.semit.militaryoblik.entity.FamilyMember;
import ntukhpi.semit.militaryoblik.entity.MilitaryPerson;
import ntukhpi.semit.militaryoblik.entity.PersonalData;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Country;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.RegionUkraine;
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


    public PersonalData updatePersonalData(Long idPerson,
                   String country, String index, String city, String address, String region,
                   String countryFact, String indexFact, String cityFact, String addressFact, String regionFact,
                                           String mainPhone, String secondPhone) {
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
        personalData.setCountry(countryService.getCountryByName(country));
        personalData.setPostIndex(index);
        personalData.setCity(city);
        personalData.setRowAddress(address);
        boolean isUkraine = String.valueOf(country).equals("Україна");

        personalData.setOblastUA((region!=null && !region.isBlank())&&isUkraine?regionUkraineService.getRegionUkraineByName(region):null);

        //Fact Address
        personalData.setFactСountry(countryService.getCountryByName(countryFact));
        personalData.setFactPostIndex(indexFact);
        personalData.setFactCity(cityFact);
        personalData.setFactRowAddress(addressFact);
        boolean isUkraineFact = String.valueOf(countryFact).equals("Україна");
        personalData.setFactOblastUA((regionFact!=null && !regionFact.isBlank())&&isUkraineFact?
                regionUkraineService.getRegionUkraineByName(regionFact):null);

        //Phones
        personalData.setPhoneMain(mainPhone);
        personalData.setPhoneDop(secondPhone);

        return personalDataService.updatePersonalData(personalData);
    }



}
