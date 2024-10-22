package ntukhpi.semit.militaryoblik.adapters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ntukhpi.semit.militaryoblik.entity.PersonalData;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactInfoAdapter implements IBaseAdapter {
    private String country;
    private String index;
    private String city;
    private String region;
    private String address;
    private String mainPhone;
    private String secondPhone;

    private String countryFact;
    private String indexFact;
    private String cityFact;
    private String regionFact;
    private String addressFact;

    private boolean isForeinNumber;

    public ContactInfoAdapter(PersonalData pd) {
        this.country = pd.getCountry().toString();
        this.index = pd.getPostIndex();
        this.city = pd.getCity();
        this.region = pd.getRegionKh() != null ? pd.getRegionKh().toString() : "";
        this.address = pd.getRowAddress();
        this.mainPhone = pd.getPhoneMain();
        this.secondPhone = pd.getPhoneDop();
        this.countryFact = pd.getFactСountry().toString();
        this.indexFact = pd.getFactPostIndex();
        this.cityFact = pd.getFactCity();
        this.regionFact = pd.getFactRegionKh() != null ? pd.getRegionKh().toString() : "";
        this.addressFact = pd.getFactRowAddress();
        this.isForeinNumber = true;
    }
}
