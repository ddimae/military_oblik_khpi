package ntukhpi.semit.militaryoblik.javafxutils;

import javafx.scene.control.Alert;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;

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

    /**
     * Метод виведення повідомлення о підтвержені збереження даних
     * автор Степанов Михайло
     */
    public static void successSave() {
        showAlert(Alert.AlertType.CONFIRMATION, "Збережено", "Дані успішно збережені", "");
    }
}
