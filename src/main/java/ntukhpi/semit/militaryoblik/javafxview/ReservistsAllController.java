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

import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.service.*;

import ntukhpi.semit.militaryoblik.javafxutils.Popup;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain.currentStage;

@Component
public class ReservistsAllController {
    private final static String CONTACT_INFO_JAVAFX = "/javafxview/ContactsEdit.fxml";
    private final static String CONTACT_INFO_JAVAFX_TITLE = "Контактна інформація";

    private final static String MILITARY_REGISTRATION_JAVAFX = "/javafxview/MilitaryRegistration.fxml";
    private final static String MILITARY_REGISTRATION_JAVAFX_TITLE = "Дані військового обліку";

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

    //============================================
    //    call to detail info about reservist
    //============================================
    // Обробка кнопок
    @FXML
    private Button educationButton;

    //Список для хранения информации о студентах. Заполняется предопределенными значениями.
    // Список используется для заполнения таблицы.
    private ObservableList<ReservistAdapter> reservistsList = FXCollections.observableArrayList();

    private static String selectedPrepodId;

    @Autowired
    FakultetRepository fakultetRepository;

    @Autowired
    KafedraRepository kafedraRepository;

    @Autowired
    VoenkomatRepository voenkomatRepository;

    /**
     * Initializes the conkafedraRepositorytroller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        ObservableList<String> tckOptions = FXCollections.observableArrayList(

                voenkomatServiceImpl.getAllVoenkomat().stream().map(Voenkomat::getVoenkomatName).sorted().toList()
        );
        ObservableList<String> instituteOptions = FXCollections.observableArrayList(
                fakultetServiceImpl.getAllFak().stream().map(Fakultet::getFname).sorted().toList()
        );
        ObservableList<String> cathedraOptions = FXCollections.observableArrayList(
                kafedraServiceImpl.getAllKafedra().stream().map(Kafedra::getKname).sorted().toList()
        );
        ObservableList<String> typeOptions = FXCollections.observableArrayList(
                "-не обрано",
                "Офіцерський склад",
                "Рядовий та сержантський склад"
        );

        // Присваивание комбо-боксам определенных выше значений
        tckComboBox.getItems().add("-не обрано");
        tckComboBox.getItems().addAll(tckOptions);

        instituteComboBox.getItems().add("-не обрано");
        instituteComboBox.getItems().addAll(instituteOptions);

        cathedraComboBox.getItems().add("-не обрано");
        cathedraComboBox.getItems().addAll(cathedraOptions);

        typeComboBox.getItems().addAll(typeOptions);

        // Устанавливаем соответствие полей класса ReservistAdapter и колонок таблицы
        pibColumn.setCellValueFactory(new PropertyValueFactory<>("pib"));
        drColumn.setCellValueFactory(new PropertyValueFactory<>("dr"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        trcColumn.setCellValueFactory(new PropertyValueFactory<>("trc"));
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        vosColumn.setCellValueFactory(new PropertyValueFactory<>("vos"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        //FIXME: В случае, если какое-либо поле не будет определено, логика не прописана.
        // Нужно обсудить
        for (Prepod prepod : prepodServiceImpl.getAllPrepod())
            reservistsList.add(new ReservistAdapter(prepod));


        updateTable(reservistsList);

        //Set handlers for buttons which show details about Reservist
    }

    public String setSelectedPrepodId() {
        ReservistAdapter reservist = reservistsTableView.getSelectionModel().getSelectedItem();

        if (reservist == null)
            return null;
        selectedPrepodId = reservist.getPrepodId();

        return selectedPrepodId;
    }

    public static Long getSelectedPrepodId() {
        if (selectedPrepodId == null || selectedPrepodId.isEmpty())
            return null;

        return Long.parseLong(selectedPrepodId);
    }

    public void stopApp(ActionEvent actionEvent) {
        MilitaryOblikKhPIMain.applicationContext.close();
        Platform.exit();
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

        StringBuilder filterText = new StringBuilder();

        if (selectedInstitute != null && !selectedInstitute.equals("-не обрано"))
            filterText.append(" Інститут: ").append(selectedInstitute);

        if (selectedCathedra != null && !selectedCathedra.equals("-не обрано"))
            filterText.append(" Кафедра: ").append(selectedCathedra);

        if (selectedTck != null && !selectedTck.equals("-не обрано"))
            filterText.append(" ТЦК: ").append(selectedTck);

        if (selectedType != null && !selectedType.equals("-не обрано"))
            filterText.append(" Тип: ").append(selectedType);

        filterTextField.setText(filterText.toString());

        ObservableList<ReservistAdapter> filteredList = reservistsList.filtered(reservistAdapter ->
                (selectedInstitute == null || selectedInstitute.equals("-не обрано") ||
                        reservistAdapter.getInstitute().equals(selectedInstitute)) &&
                        (selectedCathedra == null || selectedCathedra.equals("-не обрано") ||
                                reservistAdapter.getCathedra().equals(selectedCathedra)) &&
                        (selectedTck == null || selectedTck.equals("-не обрано") ||
                                reservistAdapter.getTck().equals(selectedTck)) &&
                        (selectedType == null || selectedType.equals("-не обрано") ||
                                reservistAdapter.getType().equals(selectedType))
        );

        updateTable(filteredList);
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


    @FXML
    public void stopApp(ActionEvent actionEvent) {
        MilitaryOblikKhPIMain.applicationContext.close();
        Platform.exit();
    }

    //============================================
    //    call to detail info about reservist
    //============================================
    // Обробка кнопок
    @FXML
    private Button educationButton;

    @FXML
    private void handleEducationButton() {
        if (setSelectedPrepodId() != null)
            MilitaryOblikKhPIMain.showEducationWindow();
        else
            Popup.noSelectedRowAlert();
    }

    @FXML
    private void handleDocumentsButton() {
        if (setSelectedPrepodId() != null)
            MilitaryOblikKhPIMain.showDocumentsWindow();
        else
            Popup.noSelectedRowAlert();
    }

    @FXML

    void handleContactInfoButton(ActionEvent event) {
        if (setSelectedPrepodId() != null) {
            MilitaryOblikKhPIMain.openEditWindow(CONTACT_INFO_JAVAFX, CONTACT_INFO_JAVAFX_TITLE, this, null);

        }
        else
            Popup.noSelectedRowAlert();
    }

    @FXML
    public void handleMilitaryRegistrationEditButton(ActionEvent actionEvent) {
        ReservistAdapter reservist = reservistsTableView.getSelectionModel().getSelectedItem();
        if (reservist != null) {
            MilitaryOblikKhPIMain.openEditWindow(MILITARY_REGISTRATION_JAVAFX,
                    MILITARY_REGISTRATION_JAVAFX_TITLE, this, reservist);
        } else {
            MilitaryOblikKhPIMain.noSelectedRowAlert();
        }
    }
}