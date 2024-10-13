package ntukhpi.semit.militaryoblik.adapters;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactInfoAdapter {
    private String country;
    private String index;
    private String city;
    private String region;
    private String address;
    private String mainPhone;
    private String secondPhone;

    private String indexFact;
    private String cityFact;
    private String regionFact;
    private String addressFact;

    public ContactInfoAdapter() {}

    public ContactInfoAdapter(String country, String index, String city, String region,
                              String address, String mainPhone, String secondPhone,
                              String indexFact, String cityFact, String regionFact,
                              String addressFact) {
        this.country = country;
        this.index = index;
        this.city = city;
        this.region = region;
        this.address = address;
        this.mainPhone = mainPhone;
        this.secondPhone = secondPhone;
        this.indexFact = indexFact;
        this.cityFact = cityFact;
        this.regionFact = regionFact;
        this.addressFact = addressFact;
    }
}
