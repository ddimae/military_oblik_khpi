package ntukhpi.semit.militaryoblik.javafxutils;

import javafx.scene.control.ComboBox;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataFormat {
    public static String getPIB(Prepod prepod) {    //TODO Точно створити окремий клас!!!
        return prepod.getFam() + " " + prepod.getImya() + " " + prepod.getOtch();
    }

    public static String localDateToUkStandart(LocalDate date) {
        List<String> pieces;

        if (date == null)
            return null;

        try {
            LocalDate.parse(date.toString());
            pieces = new ArrayList<>(Arrays.stream(date.toString().split("-")).toList());
        } catch (Exception e) {
            return null;
        }

        return pieces.get(2) + "." + pieces.get(1) + "." + pieces.get(0);
    }

    public static String getPureValue(ComboBox<String> cb) {
        String str = cb.getValue();

        if (str == null || str.equals("Не визначено"))
            return null;

        return str;
    }
}
