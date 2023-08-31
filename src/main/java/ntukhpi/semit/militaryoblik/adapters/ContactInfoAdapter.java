package ntukhpi.semit.militaryoblik.adapters;

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

    public ContactInfoAdapter(String country, String index, String city, String region, String address, String mainPhone, String secondPhone, String indexFact, String cityFact, String regionFact, String addressFact) {
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMainPhone() {
        return mainPhone;
    }

    public void setMainPhone(String mainPhone) {
        this.mainPhone = mainPhone;
    }

    public String getSecondPhone() {
        return secondPhone;
    }

    public void setSecondPhone(String secondPhone) {
        this.secondPhone = secondPhone;
    }

    public String getIndexFact() {
        return indexFact;
    }

    public void setIndexFact(String indexFact) {
        this.indexFact = indexFact;
    }

    public String getCityFact() {
        return cityFact;
    }

    public void setCityFact(String cityFact) {
        this.cityFact = cityFact;
    }

    public String getRegionFact() {
        return regionFact;
    }

    public void setRegionFact(String regionFact) {
        this.regionFact = regionFact;
    }

    public String getAddressFact() {
        return addressFact;
    }

    public void setAddressFact(String addressFact) {
        this.addressFact = addressFact;
    }
}
