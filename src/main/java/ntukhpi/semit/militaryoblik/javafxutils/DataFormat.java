package ntukhpi.semit.militaryoblik.javafxutils;

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

        System.out.println(pieces.get(2) + "." + pieces.get(1) + "." + pieces.get(0));

        return pieces.get(2) + "." + pieces.get(1) + "." + pieces.get(0);
    }
}