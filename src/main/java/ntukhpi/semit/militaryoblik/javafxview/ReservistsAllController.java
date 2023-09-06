package ntukhpi.semit.militaryoblik.javafxview;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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

@Component
public class ReservistsAllController {
    //Константи для виклику форм редагування
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

    //Список для хранения информации о резервистах.
    // Список используется для заполнения таблицы.
    private ObservableList<ReservistAdapter> reservistsList = FXCollections.observableArrayList();

    private static ReservistAdapter selectedReservist;

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
        reservistsList.clear();
        for (Prepod prepod : prepodServiceImpl.getAllPrepod())
            reservistsList.add(new ReservistAdapter(prepod));


        updateTable(reservistsList);

        // Відновлення вибору рядка перед переходом до іншої форми
        if (selectedReservist != null)
            reservistsTableView.getSelectionModel().select(selectedReservist);
    }

    public ReservistAdapter setSelectedPrepodId() {
        ReservistAdapter reservist = reservistsTableView.getSelectionModel().getSelectedItem();

        if (reservist == null)
            return null;
        selectedReservist = reservist;

        return selectedReservist;
    }

    public static Long getSelectedPrepodId() {
        return selectedReservist.getId();
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
        ReservistAdapter reservist = setSelectedPrepodId();
        if (reservist != null) {
            MilitaryOblikKhPIMain.openEditWindow(CONTACT_INFO_JAVAFX, CONTACT_INFO_JAVAFX_TITLE, this, reservist);
        }
        else
            Popup.noSelectedRowAlert();
    }

    @FXML
    public void handleMilitaryRegistrationEditButton(ActionEvent actionEvent) {
        ReservistAdapter reservist = setSelectedPrepodId();
        if (reservist != null) {
            MilitaryOblikKhPIMain.openEditWindow(MILITARY_REGISTRATION_JAVAFX, MILITARY_REGISTRATION_JAVAFX_TITLE, this, reservist);
        } else {
            Popup.noSelectedRowAlert();
        }
    }
}