package ntukhpi.semit.militaryoblik.javafxutils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;

import java.util.Optional;

public class Popup {
    /**
     * Метод запуска окон для сообщений об ошибке
     * автор Кулак Анастасия
     */
    private static void showAlert(Alert.AlertType type, String title, String headerText, String contentText) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

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
     * Метод виведення повідомлення о помилки про неибраний рядок
     * автор Степанов Михайло
     */
    public static void noSelectedRowAlert() {
        showAlert(Alert.AlertType.ERROR ,"Помилка", "Не вибраний рядок", "Натисніть на рядок, щоб його вибрати");
    }

    /**
     * Метод виведення повідомлення о помилки неправильнову вводі даних
     * автор Степанов Михайло
     */
    public static void wrongInputAlert(String msg) {
        showAlert(Alert.AlertType.ERROR, "Помилка", "Неправильно введені дані", msg);
    }

    public static void internalAlert(String msg) {
        showAlert(Alert.AlertType.ERROR, "Помилка", "Внутрішня помилка програми", msg);
    }

    /**
     * Метод виведення повідомлення о підтвержені збереження даних
     * автор Степанов Михайло
     */
    public static void successSave() {
        showAlert(Alert.AlertType.CONFIRMATION, "Збережено", "Дані успішно збережені", "");
    }

    public static boolean deleteConfirmation() {
        return showDialog(Alert.AlertType.CONFIRMATION, "Підтвердіть дію", "Обраний рядок буде безповоротно видалений", "Ви впевнені в своїх діях?");
    }

    public static boolean saveConfirmation() {
        return showDialog(Alert.AlertType.CONFIRMATION, "Підтвердіть дію", "Заповнена форма буде збережена і не підлягатиме редагуванню", "Ви впевнені в своїх діях?");
    }
}
