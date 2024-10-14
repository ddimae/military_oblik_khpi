package ntukhpi.semit.militaryoblik.javafxutils;

import javafx.scene.control.TableCell;
import ntukhpi.semit.militaryoblik.adapters.PrepodAdapter;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import org.springframework.cglib.core.Local;

import java.text.Collator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


/**
 * Клас для збереження методів форматування вхідних даних
 *
 * @author Степанов Михайло
 */
public class DataFormat {
    public static String getPIB(Prepod prepod) {
        return prepod.getFam() + " " + prepod.getImya() + " " + prepod.getOtch();
    }

    public static String getPIB(PrepodAdapter prepod) {
        return prepod.getSurname() + " " + prepod.getName() + " " + prepod.getMidname();
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

    public static LocalDate UkrStandartToLocalDate(String date) {
        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        } catch (Exception e) {
            return null;
        }
    }

    public static String getPureValue(String str) {
        if (str == null || str.equals("Не визначено"))
            return null;

        return str;
    }

    public static <T> TableCell<T, LocalDate> getTableDateCellFactory() {
        return new TableCell<T, LocalDate>() {
            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

                super.updateItem(date, empty);

                if (date != null && !empty)
                    setText(dateFormatter.format(date));
                else
                    setText("");
            }
        };
    }

    public static Collator getUkrCollator() {
        return Collator.getInstance(new Locale("uk", "UA"));
    }
}
