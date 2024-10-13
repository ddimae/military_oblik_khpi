package ntukhpi.semit.militaryoblik.adapters;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;

@Getter
@Setter
public class D05Adapter {

    private String poriad_nom;
    private String zvannia;
    private String pib;
    private String birthDate;
    private String reestNom;
    private String vos;
    private String sklad;
    private String katObl;
    private String osvita;
    private String pasport;
    private String regAddress;
    private String actAddress;
    private String terCentr;
    private String specObl;
    private String prudat;
    private String simStan;
    private String posada;
    private String priznach;

    public D05Adapter() {
    }

    public void setVirtualValues(int index) {
        Field[] fieldsList = this.getClass().getDeclaredFields();
        try {
            fieldsList[0].set(this, String.valueOf(index));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        for (int i = 1; i < fieldsList.length; i++) {
//            fieldsList[i].setAccessible(true);
            try {
                fieldsList[i].set(this, "Рядок " + index + ": значення для колонки №" + fieldsList[i].getName().substring(6, 8));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String[] objectToList() {
        return new String[]{getPoriad_nom(), getZvannia(), getPib(), getBirthDate(), getReestNom(), getVos(), getSklad(),
                getKatObl(), getOsvita(), getPasport(), getRegAddress(), getActAddress(), getTerCentr(), getSpecObl(), getPrudat(),
                getSimStan(), getPosada(), getPriznach()};
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Field[] fieldsList = this.getClass().getDeclaredFields();
        for (int i = 0; i < fieldsList.length; i++) {
            try {
                sb.append(" ").append(fieldsList[i].get(this).toString());
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return sb.toString();
    }

}
