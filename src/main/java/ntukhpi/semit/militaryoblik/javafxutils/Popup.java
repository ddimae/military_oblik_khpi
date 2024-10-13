package ntukhpi.semit.militaryoblik.javafxutils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;

import java.util.Optional;


/**
 * Клас для збереження методів створення та показу спливаючих вікон
 *
 * @author Степанов Михайло
 * @author Кулак Анастасія
 */
public class Popup {
    /**
     * Запуск вікна для повідомлення про помилку
     *
     * @author Кулак Анастасія
     */
    private static void showAlert(Alert.AlertType type, String title, String headerText, String contentText) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }


    /**
     * Запуск діалогового вікна
     *
     * @author Кулак Анастасія
     * @param type Тип діалогового вікна
     * @param title Назва вікна
     * @param headerText Головний текст
     * @param contentText Опис
     * @return true - Користувач погодився на дію. false - Користувач не погодився на дію
     */
    private static boolean showDialog(Alert.AlertType type, String title, String headerText, String contentText) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
            return true;
        else
            return false;
    }

    /**
     * Запуск вікна з повідомленням про помилку невибраного рядка
     *
     * @author Степанов Михайло
     */
    public static void noSelectedRowAlert() {
        showAlert(Alert.AlertType.ERROR ,"Помилка", "Не вибраний рядок", "Натисніть на рядок, щоб його вибрати");
    }


    /**
     * Запуск вікна з повідомленням про помилку неправильного вводу даних
     *
     * @author Степанов Михайло
     * @param msg Опис помилки
     */
    public static void wrongInputAlert(String msg) {
        showAlert(Alert.AlertType.ERROR, "Помилка", "Неправильно введені дані", msg);
    }

    /**
     * Запуск вікна з повідомленням про системну помилку
     *
     * @author Степанов Михайло
     * @param msg Опис помилки
     */
    public static void internalAlert(String msg) {
        showAlert(Alert.AlertType.ERROR, "Помилка", "Внутрішня помилка програми", msg);
    }


    /**
     * Запуск вікна з повідомленням про успішне збереження даних
     *
     * @author Степанов Михайло
     */
    public static void successSave() {
        showAlert(Alert.AlertType.CONFIRMATION, "Збережено", "Дані успішно збережені", "");
    }


    /**
     * Запуск діалогового вікна з підтвердженням видалення рядка
     *
     * @author Степанов Михайло
     */
    public static boolean deleteConfirmation() {
        return showDialog(Alert.AlertType.CONFIRMATION, "Підтвердіть дію", "Обраний рядок буде видалений без можливості відновлення", "Ви впевнені в своїх діях?");
    }


    /**
     * Запуск діалогового вікна з підтвердженням збереження даних
     *
     * @author Степанов Михайло
     */
    public static boolean saveConfirmation() {
        return showDialog(Alert.AlertType.CONFIRMATION, "Підтвердіть дію", "Дані форми будуть збережені.\n" +
                "Частина даних буде збережена у окремі таблиці без можливоcті редагування.\n" +
                "Якщо впевнені в правильності заповнення, продовжуйте збереження", "Дані введені правильно?");
    }
}
