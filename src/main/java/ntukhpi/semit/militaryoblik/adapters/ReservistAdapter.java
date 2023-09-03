package ntukhpi.semit.militaryoblik.adapters;

/**
 * Автор - Линьков А.
 * Класс для хранения информации о студентах. Временное решение, необходимое для работы с таблицей.
 */
public class ReservistAdapter {
    private String pib;
    private String dr;
    private String gender;
    private String trc;
    private String rank;
    private String vos;
    private String type;
    private String category;
    private String prepodId;

    //Поля для фильтрации. В таблице не отображаются.
    private String tck;
    private String institute;
    private String cathedra;

    public ReservistAdapter(String pib, String dr, String gender,
                            String trc, String rank, String vos,
                            String type, String category, String prepodId,
                            String tck, String institute, String cathedra) {
        this.pib = pib;
        this.dr = dr;
        this.gender = gender;
        this.trc = trc;
        this.rank = rank;
        this.vos = vos;
        this.type = type;
        this.category = category;
        this.prepodId = prepodId;
        this.tck = tck;
        this.institute = institute;
        this.cathedra = cathedra;
    }

    public String getPrepodId() {
        return prepodId;
    }

    public void setPrepodId(String prepodId) {
        this.prepodId = prepodId;
    }

    public String getPib() {
        return pib;
    }

    public void setPib(String pib) {
        this.pib = pib;
    }

    public String getDr() {
        return dr;
    }

    public void setDr(String dr) {
        this.dr = dr;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTrc() {
        return trc;
    }

    public void setTrc(String trc) {
        this.trc = trc;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getVos() {
        return vos;
    }

    public void setVos(String vos) {
        this.vos = vos;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // Getters and setters for the new fields
    public String getTck() {
        return tck;
    }

    public void setTck(String tck) {
        this.tck = tck;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getCathedra() {
        return cathedra;
    }

    public void setCathedra(String cathedra) {
        this.cathedra = cathedra;
    }
}
