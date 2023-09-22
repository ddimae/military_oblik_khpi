package ntukhpi.semit.militaryoblik.javafxview;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;
import ntukhpi.semit.militaryoblik.adapters.ReservistAdapter;
import ntukhpi.semit.militaryoblik.entity.MilitaryPerson;
import ntukhpi.semit.militaryoblik.entity.Voenkomat;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Fakultet;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Kafedra;

import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.javafxutils.AllStageSettings;
import ntukhpi.semit.militaryoblik.javafxutils.ControlledScene;
import ntukhpi.semit.militaryoblik.service.*;

import ntukhpi.semit.militaryoblik.javafxutils.Popup;


import ntukhpi.semit.militaryoblik.utils.DataPreparer;
import ntukhpi.semit.militaryoblik.utils.DataWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReservistsAllController implements ControlledScene {
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
    public Label instituteLabel;

    @FXML
    public Label tckLabel;

    @FXML
    public Label typeLabel;

    @FXML
    public Label cathedraLabel;

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

    private LoginFormController mainController;
    private Stage mainStage;
    private Stage currentStage;

    @Autowired
    FakultetServiceImpl fakultetServiceImpl;

    @Autowired
    KafedraServiceImpl kafedraServiceImpl;

    @Autowired
    VoenkomatServiceImpl voenkomatServiceImpl;

    @Autowired
    PrepodServiceImpl prepodServiceImpl;

    @Autowired
    MilitaryPersonServiceImpl militaryPersonServiceImpl;

    @Override
    public void setMainController(Object controller) {
        mainController = (LoginFormController) controller;
    }

    @Override
    public void setData(Object data) {}

    @Override
    public void setMainStage(Stage stage) {
        mainStage = stage;
    }

    @Override
    public void setCurrentStage(Stage stage) {
        currentStage = stage;
    }

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
        for (Prepod prepod : prepodServiceImpl.getAllPrepod()) {
            MilitaryPerson mp = militaryPersonServiceImpl.getMilitaryPersonByPrepod(prepod);
            //Виводяться лише ті, хто є військовозабовязаними, тобто мають створений об'єкт MilitaryPerson
            if (mp!=null && mp.getVSklad() != null)
                reservistsList.add(new ReservistAdapter(mp));
        }

        updateTable(reservistsList);

        instituteLabel.setWrapText(true);
        cathedraLabel.setWrapText(true);

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

//        StringBuilder filterText = new StringBuilder();
//
//        if (selectedInstitute != null && !selectedInstitute.equals("-не обрано"))
//            filterText.append(" Інститут: ").append(selectedInstitute);
//
//        if (selectedCathedra != null && !selectedCathedra.equals("-не обрано"))
//            filterText.append(" Кафедра: ").append(selectedCathedra);
//
//        if (selectedTck != null && !selectedTck.equals("-не обрано"))
//            filterText.append(" ТЦК: ").append(selectedTck);
//
//        if (selectedType != null && !selectedType.equals("-не обрано"))
//            filterText.append(" Тип: ").append(selectedType);
//
//        filterTextField.setText(filterText.toString());

        //StringBuilder filterText = new StringBuilder();

        instituteLabel.setText(selectedInstitute != null && !selectedInstitute.equals("-не обрано") ? "Інститут: " + selectedInstitute : "Інститут: ");
        cathedraLabel.setText(selectedCathedra != null && !selectedCathedra.equals("-не обрано") ? "Кафедра: " + selectedCathedra : "Кафедра: ");
        tckLabel.setText(selectedTck != null && !selectedTck.equals("-не обрано") ? "ТЦК: " + selectedTck : "ТЦК: ");
        typeLabel.setText(selectedType != null && !selectedType.equals("-не обрано") ? "Тип: " + selectedType : "Тип: ");


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
        MilitaryOblikKhPIMain.exitApplication();
    }

    //============================================
    //    call to detail info about reservist
    //============================================
    @FXML
    private void handlePositionButton() {
//        if (setSelectedPrepodId() != null)
//            MilitaryOblikKhPIMain.showPositionWindow();
//        else
//            Popup.noSelectedRowAlert();
    }

    @FXML
    private void handleFamilyButton() {
//        if (setSelectedPrepodId() != null)
//            MilitaryOblikKhPIMain.showFamilyWindow();
//        else
//            Popup.noSelectedRowAlert();
    }

    @FXML
    private void handleEducationButton() {
//        if (setSelectedPrepodId() != null)
//            MilitaryOblikKhPIMain.showEducationWindow();
//        else
//            Popup.noSelectedRowAlert();
    }

    @FXML
    private void handlePostgraduateEducationButton() {
//        if (setSelectedPrepodId() != null)
//            MilitaryOblikKhPIMain.showPostgraduateEducationWindow();
//        else
//            Popup.noSelectedRowAlert();
    }

    @FXML
    private void handleDocumentsButton() {
        if (setSelectedPrepodId() != null)
            MilitaryOblikKhPIMain.showStage(AllStageSettings.documentsAll, currentStage, this, null);
        else
            Popup.noSelectedRowAlert();
    }

    @FXML
    void handleContactInfoButton(ActionEvent event) {
//        ReservistAdapter reservist = setSelectedPrepodId();
//        if (reservist != null) {
//            MilitaryOblikKhPIMain.openEditWindow(CONTACT_INFO_JAVAFX, CONTACT_INFO_JAVAFX_TITLE, this, reservist);
//        }
//        else
//            Popup.noSelectedRowAlert();
    }

    @FXML
    public void handleMilitaryRegistrationEditButton(ActionEvent actionEvent) {
//        ReservistAdapter reservist = setSelectedPrepodId();
//        if (reservist != null) {
//            MilitaryOblikKhPIMain.openEditWindow(MILITARY_REGISTRATION_JAVAFX, MILITARY_REGISTRATION_JAVAFX_TITLE, this, reservist);
//        } else {
//            Popup.noSelectedRowAlert();
//        }
    }

    @FXML
    void handleOblikButton(ActionEvent event) {
//        ReservistAdapter reservist = setSelectedPrepodId();
//        MilitaryOblikKhPIMain.openEditWindow(EMPLOYEE_ADD_JAVAFX, EMPLOYEE_ADD_JAVAFX_TITLE, this, reservist);
    }

    @Autowired
    DataPreparer dataPreparer;

    @Autowired
    D05DataCollectService d05DataCollectService;
    @FXML
    private void handlePrintDodatok05Button() {
        System.out.println("handlePrintDodatok05Button");
        ObservableList<ReservistAdapter> listToSave = reservistsTableView.getItems();
        (new DataWriteService(dataPreparer,d05DataCollectService)).writeDataToExcelBase(listToSave);
    }
}