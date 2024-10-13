package ntukhpi.semit.militaryoblik.javafxutils;

import javafx.stage.Stage;

/**
 * Інтерфейс для передачі інформації між двома кнотроллерами фарм
 * Утворює ієрархічний зв'язок контроллерів та виду (mvc)
 *
 * @author Cтепанов Михайло
 * @author Двухглавов Дмитро
 */
public interface ControlledScene {

    /**
     * Зберігяє об'єкт контроллера материнської форми
     * (з якої була відкрита поточна форма)
     *
     * @param controller Об'єкт контроллера материнської форми
     */
    void setMainController(Object controller);


    /**
     * Обробляє дані, передані з контроллера материнської форми в поточну
     *
     * @param data Об'єкт передаваємих даних
     */
    void setData(Object data);


    /**
     * Зберігяє об'єкт сцени материнської форми
     * (з якої була відкрита поточна форма)
     *
     * @param stage Сцена материнської форми
     */
    void setMainStage(Stage stage);


    /**
     * Зберігає об'єкт сцени поточної (дочірньої) форми
     * (Материнський кинтроллер відповідальний за створення дочірньої сцени)
     *
     * @param stage Сцена дочірньої форми
     */
    void setCurrentStage(Stage stage);
}

