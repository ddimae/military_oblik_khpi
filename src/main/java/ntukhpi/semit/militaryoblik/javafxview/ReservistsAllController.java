package ntukhpi.semit.militaryoblik.javafxview;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;
import ntukhpi.semit.militaryoblik.adapters.ReservistAdapter;
import ntukhpi.semit.militaryoblik.entity.Voenkomat;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Fakultet;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Kafedra;

import ntukhpi.semit.militaryoblik.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReservistsAllController {
    private final static String CONTACT_INFO_JAVAFX = "/javafxview/ContactsEdit.fxml";
    private final static String CONTACT_INFO_JAVAFX_TITLE = "Контактна інформація";

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
    public TextField numberOfReservistsTextField;

    @FXML
    private TableView<ReservistAdapter> reservistsTableView;

    @FXML
    private TableColumn<ReservistAdapter, String> pibColumn;

    @FXML
    private TableColumn<ReservistAdapter, String> drColumn;

    @FXML
    private TableColumn<ReservistAdapter, String> genderColumn;

    @FXML
    private TableColumn<ReservistAdapter, String> trcColumn;

    @FXML
    private TableColumn<ReservistAdapter, String> rankColumn;

    @FXML
    private TableColumn<ReservistAdapter, String> vosColumn;

    @FXML
    private TableColumn<ReservistAdapter, String> typeColumn;

    @FXML
    private TableColumn<ReservistAdapter, String> categoryColumn;

    //Список для хранения информации о студентах. Заполняется предопределенными значениями.
    // Список используется для заполнения таблицы.
    private final ObservableList<ReservistAdapter> reservistsData = getSampleReservistsData();

    @Autowired
    FakultetServiceImpl fakultetServiceImpl;

    @Autowired
    KafedraServiceImpl kafedraServiceImpl;

    @Autowired
    VoenkomatServiceImpl voenkomatServiceImpl;

    @Autowired
    PrepodServiceImpl prepodServiceImpl;

    /**
     * Initializes the conkafedraRepositorytroller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {


        // Пример информации, хранящейся в комбо-боксах
        ObservableList<String> tckOptions = FXCollections.observableArrayList(
                //                "TCK 1",
//                "TCK 2",
//                "TCK 3"
//                fakultetRepository.findAll().stream().map(Fakultet::getFname).toList()
                voenkomatServiceImpl.getAllVoenkomat().stream().map(Voenkomat::getVoenkomatName).toList()
        );
        ObservableList<String> instituteOptions = FXCollections.observableArrayList(
//                "Institute 1",
//                "Institute 2",
//                "Institute 3"
                fakultetServiceImpl.getAllFak().stream().map(Fakultet::getFname).toList()
        );
        ObservableList<String> cathedraOptions = FXCollections.observableArrayList(
//                "Cathedra 1",
//                "Cathedra 2",
//                "Cathedra 3"
                kafedraServiceImpl.getAllKafedra().stream().map(Kafedra::getKname).toList()
        );
        ObservableList<String> typeOptions = FXCollections.observableArrayList(
                "Офіцерський склад",
                "Рядовий та сержантський склад"
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


        // Устанавливаем соответствие полей класса ReservistAdapter и колонок таблицы
        pibColumn.setCellValueFactory(new PropertyValueFactory<>("pib"));
        drColumn.setCellValueFactory(new PropertyValueFactory<>("dr"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        trcColumn.setCellValueFactory(new PropertyValueFactory<>("trc"));
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        vosColumn.setCellValueFactory(new PropertyValueFactory<>("vos"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        updateTable(reservistsData);

        //Set handlers for buttons which show details about Reservist
    }

    public void stopApp(ActionEvent actionEvent) {
        MilitaryOblikKhPIMain.applicationContext.close();
        Platform.exit();
    }

    /**
     * @return возвращает список студентов
     */
    private ObservableList<ReservistAdapter> getSampleReservistsData() {
        List<ReservistAdapter> reservistsList = new ArrayList<>();
//        List<Prepod> prepodList = prepodServiceImpl.getAllPrepod();
//        System.out.println(prepodList);
//        prepodList.stream().forEach(
//                prep -> reservistsList.add(new ReservistAdapter(prep))
//        );
        reservistsList.add(new ReservistAdapter("John Doe", "1990-01-15", "Male", "TRC 1", "Captain", "VOS 123", "Officer", "Category A", "TCK 1", "Institute 1", "Cathedra 1"));
        reservistsList.add(new ReservistAdapter("Jane Smith", "1985-05-20", "Female", "TRC 2", "Lieutenant", "VOS 456", "Officer", "Category B", "TCK 2", "Institute 2", "Cathedra 2"));
        reservistsList.add(new ReservistAdapter("Michael Johnson", "1988-11-03", "Male", "TRC 3", "Sergeant", "VOS 789", "Non-Commissioned Officer", "Category C", "TCK 1", "Institute 3", "Cathedra 3"));
        reservistsList.add(new ReservistAdapter("Emily Williams", "1992-09-10", "Female", "TRC 1", "Lieutenant", "VOS 234", "Officer", "Category A", "TCK 2", "Institute 1", "Cathedra 1"));
        reservistsList.add(new ReservistAdapter("William Brown", "1987-06-25", "Male", "TRC 2", "Captain", "VOS 567", "Officer", "Category B", "TCK 1", "Institute 2", "Cathedra 2"));
        reservistsList.add(new ReservistAdapter("Emma Davis", "1991-04-30", "Female", "TRC 3", "Sergeant", "VOS 890", "Non-Commissioned Officer", "Category C", "TCK 1", "Institute 3", "Cathedra 3"));
        reservistsList.add(new ReservistAdapter("James Miller", "1986-08-17", "Male", "TRC 1", "Lieutenant", "VOS 345", "Officer", "Category A", "TCK 1", "Institute 1", "Cathedra 1"));
        reservistsList.add(new ReservistAdapter("Olivia Wilson", "1989-02-05", "Female", "TRC 2", "Captain", "VOS 678", "Officer", "Category B", "TCK 2", "Institute 2", "Cathedra 1"));
        reservistsList.add(new ReservistAdapter("Alexander Garcia", "1993-07-21", "Male", "TRC 3", "Sergeant", "VOS 901", "Non-Commissioned Officer", "Category C", "TCK 9", "Institute 3", "Cathedra 2"));
        reservistsList.add(new ReservistAdapter("Sophia Martinez", "1985-12-12", "Female", "TRC 1", "Lieutenant", "VOS 456", "Officer", "Category A", "TCK 1", "Institute 1", "Cathedra 2"));
        reservistsList.add(new ReservistAdapter("Daniel Robinson", "1987-09-28", "Male", "TRC 2", "Captain", "VOS 789", "Officer", "Category B", "TCK 1", "Institute 3", "Cathedra 1"));
        reservistsList.add(new ReservistAdapter("Isabella Clark", "1991-11-15", "Female", "TRC 3", "Sergeant", "VOS 012", "Non-Commissioned Officer", "Category C", "TCK 12", "Institute 3", "Cathedra 2"));
        reservistsList.add(new ReservistAdapter("Liam Lee", "1994-03-06", "Male", "TRC 1", "Lieutenant", "VOS 567", "Officer", "Category A", "TCK 3", "Institute 3", "Cathedra 3"));
        reservistsList.add(new ReservistAdapter("Ava Scott", "1988-06-09", "Female", "TRC 2", "Captain", "VOS 890", "Officer", "Category B", "TCK 1", "Institute 2", "Cathedra 1"));
        reservistsList.add(new ReservistAdapter("Mason Allen", "1990-01-29", "Male", "TRC 3", "Sergeant", "VOS 123", "Non-Commissioned Officer", "Category C", "TCK 15", "Institute 3", "Cathedra 1"));
        reservistsList.add(new ReservistAdapter("Sophie Anderson", "1992-09-25", "Female", "TRC 1", "Lieutenant", "VOS 234", "Officer", "Category A", "TCK 1", "Institute 1", "Cathedra 1"));
        reservistsList.add(new ReservistAdapter("Ethan Turner", "1987-06-29", "Male", "TRC 2", "Captain", "VOS 567", "Officer", "Category B", "TCK 1", "Institute 2", "Cathedra 1"));
        reservistsList.add(new ReservistAdapter("Grace Murphy", "1991-04-21", "Female", "TRC 3", "Sergeant", "VOS 890", "Non-Commissioned Officer", "Category C", "TCK 18", "Institute 3", "Cathedra 1"));
        reservistsList.add(new ReservistAdapter("Benjamin White", "1986-08-19", "Male", "TRC 1", "Lieutenant", "VOS 345", "Officer", "Category A", "TCK 1", "Institute 3", "Cathedra 1"));
        reservistsList.add(new ReservistAdapter("Lily Turner", "1989-02-11", "Female", "TRC 2", "Captain", "VOS 678", "Officer", "Category B", "TCK 2", "Institute 3", "Cathedra 2"));
        reservistsList.add(new ReservistAdapter("Lucas Hall", "1993-07-27", "Male", "TRC 3", "Sergeant", "VOS 901", "Non-Commissioned Officer", "Category C", "TCK 21", "Institute 3", "Cathedra 2"));
        reservistsList.add(new ReservistAdapter("Chloe Parker", "1985-12-06", "Female", "TRC 1", "Lieutenant", "VOS 456", "Officer", "Category A", "TCK 2", "Institute 1", "Cathedra 22"));
        reservistsList.add(new ReservistAdapter("Henry Hill", "1987-09-22", "Male", "TRC 2", "Captain", "VOS 789", "Officer", "Category B", "TCK 3", "Institute 2", "Cathedra 23"));
        reservistsList.add(new ReservistAdapter("Mia Cole", "1991-11-12", "Female", "TRC 3", "Sergeant", "VOS 012", "Non-Commissioned Officer", "Category C", "TCK 2", "Institute 3", "Cathedra 2"));
        reservistsList.add(new ReservistAdapter("Owen Wood", "1994-03-10", "Male", "TRC 1", "Lieutenant", "VOS 567", "Officer", "Category A", "TCK 2", "Institute 1", "Cathedra 25"));
        reservistsList.add(new ReservistAdapter("Amelia Adams", "1988-06-07", "Female", "TRC 2", "Captain", "VOS 890", "Officer", "Category B", "TCK 2", "Institute 2", "Cathedra 26"));
        reservistsList.add(new ReservistAdapter("Oliver Scott", "1990-01-26", "Male", "TRC 3", "Sergeant", "VOS 123", "Non-Commissioned Officer", "Category C", "TCK 2", "Institute 3", "Cathedra 2"));
        reservistsList.add(new ReservistAdapter("Sophia Garcia", "1993-07-22", "Female", "TRC 1", "Lieutenant", "VOS 234", "Officer", "Category A", "TCK 2", "Institute 1", "Cathedra 28"));
        reservistsList.add(new ReservistAdapter("Ethan Miller", "1987-06-30", "Male", "TRC 2", "Captain", "VOS 567", "Officer", "Category B", "TCK 2", "Institute 2", "Cathedra 29"));
        reservistsList.add(new ReservistAdapter("Grace Clark", "1991-04-20", "Female", "TRC 3", "Sergeant", "VOS 890", "Non-Commissioned Officer", "Category C", "TCK 30", "Institute 3", "Cathedra 3"));
        reservistsList.add(new ReservistAdapter("Benjamin Anderson", "1986-08-20", "Male", "TRC 1", "Lieutenant", "VOS 345", "Officer", "Category A", "TCK 3", "Institute 1", "Cathedra 1"));
        reservistsList.add(new ReservistAdapter("Lily Wilson", "1989-02-12", "Female", "TRC 2", "Captain", "VOS 678", "Officer", "Category B", "TCK 3", "Institute 2", "Cathedra 32"));
        reservistsList.add(new ReservistAdapter("Lucas Turner", "1993-07-26", "Male", "TRC 3", "Sergeant", "VOS 901", "Non-Commissioned Officer", "Category C", "TCK 33", "Institute 3", "Cathedra 3"));
        reservistsList.add(new ReservistAdapter("Chloe Parker", "1985-12-07", "Female", "TRC 1", "Lieutenant", "VOS 456", "Officer", "Category A", "TCK 3", "Institute 1", "Cathedra 3"));
        reservistsList.add(new ReservistAdapter("Henry Hill", "1987-09-21", "Male", "TRC 2", "Captain", "VOS 789", "Officer", "Category B", "TCK 3", "Institute 2", "Cathedra 3"));

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

        //ObservableList<ReservistAdapter> filteredList = FXCollections.observableArrayList();

        //updateTable(filteredList);
    }

    /**
     * Метод для обновления информации в таблице.
     * Должен вызываться при каждом изменении значений в ячейках либо при сортировке.
     *
     * @param observableList список студентов
     */
    private void updateTable(ObservableList<ReservistAdapter> observableList) {
        reservistsTableView.setItems(observableList);

        numberOfReservistsTextField.setText(String.valueOf(observableList.size()));
    }

    //============================================
    //    call to detail info about reservist
    //============================================
    // Обробка кнопок
    @FXML
    private Button educationButton;

    @FXML
    private void handleEducationButton() {
        //currentStage.close();
        MilitaryOblikKhPIMain.showEducationWindow();
    }

    @FXML
    private void handleDocumentsButton() {
        MilitaryOblikKhPIMain.showDocumentsWindow();
    }

    @FXML
    void handleContactInfoButton(ActionEvent event) {
        ReservistAdapter reservist = reservistsTableView.getSelectionModel().getSelectedItem();
        if (reservist != null) {
            MilitaryOblikKhPIMain.openEditWindow(CONTACT_INFO_JAVAFX, CONTACT_INFO_JAVAFX_TITLE, this, reservist);
        } else {
            MilitaryOblikKhPIMain.noSelectedRowAlert();
        }
    }
}
