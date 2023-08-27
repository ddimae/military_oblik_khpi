package ntukhpi.semit.militaryoblik.adapters;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;

@Getter
@Setter
public class D05Adapter {

    private String column01;
    private String column02;
    private String column03;
    private String column04;
    private String column05;
    private String column06;
    private String column07;
    private String column08;
    private String column09;
    private String column10;
    private String column11;
    private String column12;
    private String column13;
    private String column14;
    private String column15;
    private String column16;
    private String column17;
    private String column18;

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
        return new String[]{getColumn01(), getColumn02(), getColumn03(), getColumn04(), getColumn05(), getColumn06(), getColumn07(),
                getColumn08(), getColumn09(), getColumn10(), getColumn11(), getColumn12(), getColumn13(), getColumn14(), getColumn15(),
                getColumn16(), getColumn17(), getColumn18()};
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
