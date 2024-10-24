package ntukhpi.semit.militaryoblik.adapters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ntukhpi.semit.militaryoblik.entity.PersonalData;
import ntukhpi.semit.militaryoblik.javafxutils.DataFormat;

import java.util.Objects;

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
        this.country = DataFormat.safeStr(pd.getCountry());
        this.index = pd.getPostIndex();
        this.city = pd.getCity();
        this.region = DataFormat.safeStr(pd.getRegionKh());
        this.address = pd.getRowAddress();
        this.mainPhone = pd.getPhoneMain();
        this.secondPhone = pd.getPhoneDop();
        this.countryFact = DataFormat.safeStr(pd.getFactСountry());
        this.indexFact = pd.getFactPostIndex();
        this.cityFact = pd.getFactCity();
        this.regionFact = DataFormat.safeStr(pd.getRegionKh());
        this.addressFact = pd.getFactRowAddress();
        this.isForeinNumber = true;
    }

    public boolean isFactEqual() {
        return Objects.equals(country, countryFact) &&
                Objects.equals(index, indexFact) &&
                Objects.equals(city, cityFact) &&
                Objects.equals(region, regionFact) &&
                Objects.equals(address, addressFact);
    }
}
