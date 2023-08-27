package ntukhpi.semit.militaryoblik.javafxview;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Fakultet;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Kafedra;
import ntukhpi.semit.militaryoblik.repository.FakultetRepository;
import ntukhpi.semit.militaryoblik.repository.KafedraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReservistsAllController {
    @FXML
    public ComboBox<String> tckComboBox;

    @FXML
    public ComboBox<String> instituteComboBox;

    @FXML
    public ComboBox<String> cathedraComboBox;

    @FXML
    public ComboBox<String> typeComboBox;

    @FXML
    public TextField filterTextField;

    @FXML
    public TextField cathedraTextField;

    @FXML
    public TextField categoryTextField;

    @FXML
    public TextField numberOfReservistsTextField;

    @FXML
    private TableView<Reservist> reservistsTableView;

    @FXML
    private TableColumn<Reservist, String> pibColumn;

    @FXML
    private TableColumn<Reservist, String> drColumn;

    @FXML
    private TableColumn<Reservist, String> genderColumn;

    @FXML
    private TableColumn<Reservist, String> trcColumn;

    @FXML
    private TableColumn<Reservist, String> rankColumn;

    @FXML
    private TableColumn<Reservist, String> vosColumn;

    @FXML
    private TableColumn<Reservist, String> typeColumn;

    @FXML
    private TableColumn<Reservist, String> categoryColumn;

    //Список для хранения информации о студентах. Заполняется предопределенными значениями.
    // Список используется для заполнения таблицы.
    private final ObservableList<Reservist> reservistsData = getSampleReservistsData();

    @Autowired
    FakultetRepository fakultetRepository;

    @Autowired
    KafedraRepository kafedraRepository;

    /**
     * Initializes the conkafedraRepositorytroller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {


        // Пример информации, хранящейся в комбо-боксах
        ObservableList<String> tckOptions = FXCollections.observableArrayList(
                "TCK 1",
                "TCK 2",
                "TCK 3"
                //fakultetRepository.findAll().stream().map(Fakultet::getFname).toList()
        );
        ObservableList<String> instituteOptions = FXCollections.observableArrayList(
//                "Institute 1",
//                "Institute 2",
//                "Institute 3"
                fakultetRepository.findAll().stream().map(Fakultet::getFname).toList()
        );
        ObservableList<String> cathedraOptions = FXCollections.observableArrayList(
//                "Cathedra 1",
//                "Cathedra 2",
//                "Cathedra 3"
                kafedraRepository.findAll().stream().map(Kafedra::getKname).toList()
        );
        ObservableList<String> typeOptions = FXCollections.observableArrayList(
                "Officer",
                "Non-Commissioned Officer"
        );

        // Присваивание комбо-боксам определенных выше значений
        tckComboBox.setItems(tckOptions);
        instituteComboBox.setItems(instituteOptions);
        cathedraComboBox.setItems(cathedraOptions);
        typeComboBox.setItems(typeOptions);

        /*
        tckComboBox.getSelectionModel().select(0);
        instituteComboBox.getSelectionModel().select(0);
        cathedraComboBox.getSelectionModel().select(0);
        typeComboBox.getSelectionModel().select(0);
         */


        // Устанавливаем соответствие полей класса Reservist и колонок таблицы
        pibColumn.setCellValueFactory(new PropertyValueFactory<>("pib"));
        drColumn.setCellValueFactory(new PropertyValueFactory<>("dr"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        trcColumn.setCellValueFactory(new PropertyValueFactory<>("trc"));
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        vosColumn.setCellValueFactory(new PropertyValueFactory<>("vos"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        updateTable(reservistsData);
    }

    /**
     * @return возвращает список студентов
     */
    private ObservableList<Reservist> getSampleReservistsData() {
        List<Reservist> reservistsList = new ArrayList<>();

        reservistsList.add(new Reservist("John Doe", "1990-01-15", "Male", "TRC 1", "Captain", "VOS 123", "Officer", "Category A", "TCK 1", "Institute 1", "Cathedra 1"));
        reservistsList.add(new Reservist("Jane Smith", "1985-05-20", "Female", "TRC 2", "Lieutenant", "VOS 456", "Officer", "Category B", "TCK 2", "Institute 2", "Cathedra 2"));
        reservistsList.add(new Reservist("Michael Johnson", "1988-11-03", "Male", "TRC 3", "Sergeant", "VOS 789", "Non-Commissioned Officer", "Category C", "TCK 1", "Institute 3", "Cathedra 3"));
        reservistsList.add(new Reservist("Emily Williams", "1992-09-10", "Female", "TRC 1", "Lieutenant", "VOS 234", "Officer", "Category A", "TCK 2", "Institute 1", "Cathedra 1"));
        reservistsList.add(new Reservist("William Brown", "1987-06-25", "Male", "TRC 2", "Captain", "VOS 567", "Officer", "Category B", "TCK 1", "Institute 2", "Cathedra 2"));
        reservistsList.add(new Reservist("Emma Davis", "1991-04-30", "Female", "TRC 3", "Sergeant", "VOS 890", "Non-Commissioned Officer", "Category C", "TCK 1", "Institute 3", "Cathedra 3"));
        reservistsList.add(new Reservist("James Miller", "1986-08-17", "Male", "TRC 1", "Lieutenant", "VOS 345", "Officer", "Category A", "TCK 1", "Institute 1", "Cathedra 1"));
        reservistsList.add(new Reservist("Olivia Wilson", "1989-02-05", "Female", "TRC 2", "Captain", "VOS 678", "Officer", "Category B", "TCK 2", "Institute 2", "Cathedra 1"));
        reservistsList.add(new Reservist("Alexander Garcia", "1993-07-21", "Male", "TRC 3", "Sergeant", "VOS 901", "Non-Commissioned Officer", "Category C", "TCK 9", "Institute 3", "Cathedra 2"));
        reservistsList.add(new Reservist("Sophia Martinez", "1985-12-12", "Female", "TRC 1", "Lieutenant", "VOS 456", "Officer", "Category A", "TCK 1", "Institute 1", "Cathedra 2"));
        reservistsList.add(new Reservist("Daniel Robinson", "1987-09-28", "Male", "TRC 2", "Captain", "VOS 789", "Officer", "Category B", "TCK 1", "Institute 3", "Cathedra 1"));
        reservistsList.add(new Reservist("Isabella Clark", "1991-11-15", "Female", "TRC 3", "Sergeant", "VOS 012", "Non-Commissioned Officer", "Category C", "TCK 12", "Institute 3", "Cathedra 2"));
        reservistsList.add(new Reservist("Liam Lee", "1994-03-06", "Male", "TRC 1", "Lieutenant", "VOS 567", "Officer", "Category A", "TCK 3", "Institute 3", "Cathedra 3"));
        reservistsList.add(new Reservist("Ava Scott", "1988-06-09", "Female", "TRC 2", "Captain", "VOS 890", "Officer", "Category B", "TCK 1", "Institute 2", "Cathedra 1"));
        reservistsList.add(new Reservist("Mason Allen", "1990-01-29", "Male", "TRC 3", "Sergeant", "VOS 123", "Non-Commissioned Officer", "Category C", "TCK 15", "Institute 3", "Cathedra 1"));
        reservistsList.add(new Reservist("Sophie Anderson", "1992-09-25", "Female", "TRC 1", "Lieutenant", "VOS 234", "Officer", "Category A", "TCK 1", "Institute 1", "Cathedra 1"));
        reservistsList.add(new Reservist("Ethan Turner", "1987-06-29", "Male", "TRC 2", "Captain", "VOS 567", "Officer", "Category B", "TCK 1", "Institute 2", "Cathedra 1"));
        reservistsList.add(new Reservist("Grace Murphy", "1991-04-21", "Female", "TRC 3", "Sergeant", "VOS 890", "Non-Commissioned Officer", "Category C", "TCK 18", "Institute 3", "Cathedra 1"));
        reservistsList.add(new Reservist("Benjamin White", "1986-08-19", "Male", "TRC 1", "Lieutenant", "VOS 345", "Officer", "Category A", "TCK 1", "Institute 3", "Cathedra 1"));
        reservistsList.add(new Reservist("Lily Turner", "1989-02-11", "Female", "TRC 2", "Captain", "VOS 678", "Officer", "Category B", "TCK 2", "Institute 3", "Cathedra 2"));
        reservistsList.add(new Reservist("Lucas Hall", "1993-07-27", "Male", "TRC 3", "Sergeant", "VOS 901", "Non-Commissioned Officer", "Category C", "TCK 21", "Institute 3", "Cathedra 2"));
        reservistsList.add(new Reservist("Chloe Parker", "1985-12-06", "Female", "TRC 1", "Lieutenant", "VOS 456", "Officer", "Category A", "TCK 2", "Institute 1", "Cathedra 22"));
        reservistsList.add(new Reservist("Henry Hill", "1987-09-22", "Male", "TRC 2", "Captain", "VOS 789", "Officer", "Category B", "TCK 3", "Institute 2", "Cathedra 23"));
        reservistsList.add(new Reservist("Mia Cole", "1991-11-12", "Female", "TRC 3", "Sergeant", "VOS 012", "Non-Commissioned Officer", "Category C", "TCK 2", "Institute 3", "Cathedra 2"));
        reservistsList.add(new Reservist("Owen Wood", "1994-03-10", "Male", "TRC 1", "Lieutenant", "VOS 567", "Officer", "Category A", "TCK 2", "Institute 1", "Cathedra 25"));
        reservistsList.add(new Reservist("Amelia Adams", "1988-06-07", "Female", "TRC 2", "Captain", "VOS 890", "Officer", "Category B", "TCK 2", "Institute 2", "Cathedra 26"));
        reservistsList.add(new Reservist("Oliver Scott", "1990-01-26", "Male", "TRC 3", "Sergeant", "VOS 123", "Non-Commissioned Officer", "Category C", "TCK 2", "Institute 3", "Cathedra 2"));
        reservistsList.add(new Reservist("Sophia Garcia", "1993-07-22", "Female", "TRC 1", "Lieutenant", "VOS 234", "Officer", "Category A", "TCK 2", "Institute 1", "Cathedra 28"));
        reservistsList.add(new Reservist("Ethan Miller", "1987-06-30", "Male", "TRC 2", "Captain", "VOS 567", "Officer", "Category B", "TCK 2", "Institute 2", "Cathedra 29"));
        reservistsList.add(new Reservist("Grace Clark", "1991-04-20", "Female", "TRC 3", "Sergeant", "VOS 890", "Non-Commissioned Officer", "Category C", "TCK 30", "Institute 3", "Cathedra 3"));
        reservistsList.add(new Reservist("Benjamin Anderson", "1986-08-20", "Male", "TRC 1", "Lieutenant", "VOS 345", "Officer", "Category A", "TCK 3", "Institute 1", "Cathedra 1"));
        reservistsList.add(new Reservist("Lily Wilson", "1989-02-12", "Female", "TRC 2", "Captain", "VOS 678", "Officer", "Category B", "TCK 3", "Institute 2", "Cathedra 32"));
        reservistsList.add(new Reservist("Lucas Turner", "1993-07-26", "Male", "TRC 3", "Sergeant", "VOS 901", "Non-Commissioned Officer", "Category C", "TCK 33", "Institute 3", "Cathedra 3"));
        reservistsList.add(new Reservist("Chloe Parker", "1985-12-07", "Female", "TRC 1", "Lieutenant", "VOS 456", "Officer", "Category A", "TCK 3", "Institute 1", "Cathedra 3"));
        reservistsList.add(new Reservist("Henry Hill", "1987-09-21", "Male", "TRC 2", "Captain", "VOS 789", "Officer", "Category B", "TCK 3", "Institute 2", "Cathedra 3"));

        return FXCollections.observableList(reservistsList);
    }

    /**
     * Обработчик событий для фильтрации по выбранным критериям. Срабатывает при выборе значений комбо-бокса.
     */
    @FXML
    private void sortTable() {
        String selectedTck = tckComboBox.getSelectionModel().getSelectedItem();
        String selectedInstitute = instituteComboBox.getSelectionModel().getSelectedItem();
        String selectedCathedra = cathedraComboBox.getSelectionModel().getSelectedItem();
        String selectedType = typeComboBox.getSelectionModel().getSelectedItem();

        //TODO: Написать метод для фильтрации студентов в таблице по критериям, выбранным в комбо-боксах

        //ObservableList<Reservist> filteredList = FXCollections.observableArrayList();

        //updateTable(filteredList);
    }

    /**
     * Метод для обновления информации в таблице.
     * Должен вызываться при каждом изменении значений в ячейках либо при сортировке.
     *
     * @param observableList список студентов
     */
    private void updateTable(ObservableList<Reservist> observableList) {
        reservistsTableView.setItems(observableList);

        numberOfReservistsTextField.setText(String.valueOf(observableList.size()));
    }
}
