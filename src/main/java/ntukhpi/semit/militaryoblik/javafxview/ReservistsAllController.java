package ntukhpi.semit.militaryoblik.javafxview;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;
import ntukhpi.semit.militaryoblik.adapters.ReservistAdapter;
import ntukhpi.semit.militaryoblik.entity.MilitaryPerson;
import ntukhpi.semit.militaryoblik.entity.Voenkomat;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Fakultet;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Kafedra;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.javafxutils.AllStageSettings;
import ntukhpi.semit.militaryoblik.javafxutils.ControlledScene;

import ntukhpi.semit.militaryoblik.javafxutils.DataFormat;

import ntukhpi.semit.militaryoblik.javafxutils.Popup;
import ntukhpi.semit.militaryoblik.service.*;
import ntukhpi.semit.militaryoblik.utils.DataWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    public Text numberOfReservistsText;

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

    @Autowired
    DataWriteService dataWriteService;

    @Override
    public void setMainController(Object controller) {
        mainController = (LoginFormController) controller;
    }

    @Override
    public void setData(Object data) {
    }

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
                "-Оберіть категорію звань",
                "Офіцерський склад",
                "Рядовий та сержантський склад"
        );

        // Присваивание комбо-боксам определенных выше значений
        tckComboBox.getItems().add("-Оберіть ТЦК");
        tckComboBox.getItems().addAll(tckOptions);

        instituteComboBox.getItems().add("-Оберіть інститут");
        instituteComboBox.getItems().addAll(instituteOptions);

        cathedraComboBox.getItems().add("-Оберіть кафедру");
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
            if (mp != null && mp.getVSklad() != null)
                reservistsList.add(new ReservistAdapter(mp));
        }

        updateTable(reservistsList);

        instituteLabel.setWrapText(true);
        cathedraLabel.setWrapText(true);

        // Відновлення вибору рядка перед переходом до іншої форми
//        selectReservist(selectedReservist);
    }

    public static void setSelectedReservist(ReservistAdapter reservist) {
        selectedReservist = reservist;
    }

    public ReservistAdapter selectReservist(ReservistAdapter reservist) {
        if (reservist == null)
            return null;

        reservistsTableView.getSelectionModel().select(reservist);

        return selectReservist();
    }

    public ReservistAdapter selectReservist() {
        ReservistAdapter reservist = reservistsTableView.getSelectionModel().getSelectedItem();

        if (reservist == null)
            return null;
        selectedReservist = reservist;

        return selectedReservist;
    }

    public void updateForm() {
        reservistsList.clear();

        ObservableList<String> tckOptions = FXCollections.observableArrayList("-Оберіть ТЦК");
        tckOptions.addAll(voenkomatServiceImpl.getAllVoenkomat().stream()
                .map(Voenkomat::getVoenkomatName).sorted().toList());

        tckComboBox.setItems(tckOptions);

        for (Prepod prepod : prepodServiceImpl.getAllPrepod()) {
            MilitaryPerson mp = militaryPersonServiceImpl.getMilitaryPersonByPrepod(prepod);
            if (mp != null) {
                reservistsList.add(new ReservistAdapter(mp));
            }
        }

        //updateTable(reservistsList);
        selectReservist(selectedReservist);

        reservistsTableView.refresh();

        sortTable();
    }

    public static Long getSelectedPrepodId() {
        if (selectedReservist == null)
            return null;

        return selectedReservist.getId();
    }

    @FXML
    private void cathedraChanged() {
        String selectedCathedra = cathedraComboBox.getSelectionModel().getSelectedItem();
        String selectedKafAbr = kafedraServiceImpl.findAbrKafedraByKname(selectedCathedra);
//        cathedraLabel.setText(selectedCathedra != null && !selectedCathedra.equals("-Оберіть кафедру") ? "Кафедра: " + selectedCathedra : "Кафедра: ");
        cathedraLabel.setText(selectedCathedra != null && !selectedCathedra.equals("-Оберіть кафедру") ? "Кафедра: " + selectedKafAbr : "Кафедра: ");

        sortTable();
    }

    @FXML
    private void instituteChanged() {
        String selectedInstitute = instituteComboBox.getSelectionModel().getSelectedItem();
        String selectedFakAbr = fakultetServiceImpl.findAbrFakultetByFname(selectedInstitute);
//        instituteLabel.setText(selectedInstitute != null && !selectedInstitute.equals("-Оберіть інститут") ? "Інститут: " + selectedInstitute : "Інститут: ");
        instituteLabel.setText(selectedInstitute != null && !selectedInstitute.equals("-Оберіть інститут") ? "Інститут: " + selectedFakAbr : "Інститут: ");

        ObservableList<String> cathedraOptions = FXCollections.observableArrayList();
        cathedraOptions.add("-Оберіть кафедру");

        if (selectedInstitute.equals("-Оберіть інститут"))
            cathedraOptions.addAll(FXCollections.observableArrayList(kafedraServiceImpl.getAllKafedra()
                    .stream().map(Kafedra::getKname).sorted().toList()));
        else
            cathedraOptions.addAll(FXCollections.observableArrayList(
                    kafedraServiceImpl.findKafedrasOfFakultet(selectedInstitute)
                            .stream().map(Kafedra::getKname).sorted().toList()));

        cathedraComboBox.setItems(cathedraOptions);
        cathedraComboBox.getSelectionModel().selectFirst();

        sortTable();
    }

    @FXML
    private void tckChanged() {
        String selectedTck = tckComboBox.getSelectionModel().getSelectedItem();
        tckLabel.setText(selectedTck != null && !selectedTck.equals("-Оберіть ТЦК") ? "ТЦК: " + selectedTck : "ТЦК: ");

        sortTable();
    }

    @FXML
    private void typeChanged() {
        String selectedType = typeComboBox.getSelectionModel().getSelectedItem();
        typeLabel.setText(selectedType != null && !selectedType.equals("-Оберіть категорію звань") ? "Тип: " + selectedType : "Тип: ");

        sortTable();
    }

    /**
     * Обработчик событий для фильтрации по выбранным критериям. Срабатывает при выборе значений комбо-бокса.
     */
    private void sortTable() {
        String selectedTck = tckComboBox.getSelectionModel().getSelectedItem();
        String selectedType = typeComboBox.getSelectionModel().getSelectedItem();
        String selectedInstitute = instituteComboBox.getSelectionModel().getSelectedItem();
        String selectedCathedra = cathedraComboBox.getSelectionModel().getSelectedItem();


        ObservableList<ReservistAdapter> filteredList = reservistsList.filtered(reservistAdapter ->
                (selectedInstitute == null || selectedInstitute.equals("-Оберіть інститут") ||
                        reservistAdapter.getInstitute().equals(selectedInstitute)) &&
                        (selectedCathedra == null || selectedCathedra.equals("-Оберіть кафедру") ||
                                reservistAdapter.getCathedra().equals(selectedCathedra)) &&
                        (selectedTck == null || selectedTck.equals("-Оберіть ТЦК") ||
                                reservistAdapter.getTck().equals(selectedTck)) &&
                        (selectedType == null || selectedType.equals("-Оберіть категорію звань") ||
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
    public void updateTable(ObservableList<ReservistAdapter> observableList) {
        ObservableList<ReservistAdapter> sortedList = FXCollections.observableArrayList(
                observableList.stream()
                        .sorted((a, b) ->
                                DataFormat.getUkrCollator().compare(a.getPib(), b.getPib())).toList());

        reservistsTableView.setItems(sortedList);
        reservistsTableView.refresh();


        numberOfReservistsText.setText(String.valueOf(observableList.size()));
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
        if (selectReservist() != null)
            MilitaryOblikKhPIMain.showStage(AllStageSettings.positionEdit, currentStage, this, null);
        else
            Popup.noSelectedRowAlert();
    }

    @FXML
    private void handleFamilyButton() {
        if (selectReservist() != null)
            MilitaryOblikKhPIMain.showStage(AllStageSettings.familyAll, currentStage, this, null);
        else
            Popup.noSelectedRowAlert();
    }

    @FXML
    private void handleEducationButton() {
        if (selectReservist() != null)
            MilitaryOblikKhPIMain.showStage(AllStageSettings.educationAll, currentStage, this, null);
        else
            Popup.noSelectedRowAlert();
    }

    @FXML
    private void handlePostgraduateEducationButton() {
        if (selectReservist() != null)
            MilitaryOblikKhPIMain.showStage(AllStageSettings.educationPostgraduateAll, currentStage, this, null);
        else
            Popup.noSelectedRowAlert();
    }

    @FXML
    private void handleDocumentsButton() {
        if (selectReservist() != null)
            MilitaryOblikKhPIMain.showStage(AllStageSettings.documentsAll, currentStage, this, null);
        else
            Popup.noSelectedRowAlert();
    }

    @FXML
    void handleContactInfoButton(ActionEvent event) {
        ReservistAdapter reservist = selectReservist();
        if (reservist != null) {
            MilitaryOblikKhPIMain.showStage(AllStageSettings.contactsEdit, currentStage, this, reservist);
        } else
            Popup.noSelectedRowAlert();
    }

    @FXML
    public void handleMilitaryRegistrationEditButton(ActionEvent actionEvent) {
        ReservistAdapter reservist = selectReservist();
        if (reservist != null) {
            MilitaryOblikKhPIMain.showStage(AllStageSettings.militaryRegistrationEdit, currentStage, this, reservist);
        } else {
            Popup.noSelectedRowAlert();
        }
    }

    @FXML
    void handleOblikButton(ActionEvent event) {
        MilitaryOblikKhPIMain.showStage(AllStageSettings.employeeAdd, currentStage, this, null);
    }

    @FXML
    private void handlePrintDodatok05Button() {
        System.out.println("handlePrintDodatok05Button");
        ObservableList<ReservistAdapter> listToSave = reservistsTableView.getItems();
        FileChooser fileChooser = new FileChooser();
        String resultDirName = "docs/results";
        File resultDir = new File(resultDirName);
        String resultSave = "";
        if (!resultDir.isDirectory()) {
            boolean success = resultDir.mkdirs();
            if (success) {
                System.out.println("Created path: " + resultDir.getPath());
            } else {
                resultSave = "Помилка створення каталогу для зберігання результатів";
                Alert confirmationDialog = null;
                confirmationDialog = new Alert(Alert.AlertType.ERROR);
                confirmationDialog.setTitle("Помилка формування Додатку 05");
                confirmationDialog.setHeaderText(null);
                confirmationDialog.setContentText(resultSave);
                confirmationDialog.showAndWait();
            }
        }

        fileChooser.setInitialDirectory(resultDir);
        String resultFileName = "dodatok05_" + LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        fileChooser.setInitialFileName(resultFileName);
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Файли Excel з формою 05 (*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);
        resultSave = dataWriteService.writeDataToExcelBase(listToSave, fileChooser.showSaveDialog(new Stage()));

        Alert confirmationDialog = null;
        if (resultSave.startsWith("Дані успішно збережені")) {
            confirmationDialog = new Alert(Alert.AlertType.INFORMATION);
            confirmationDialog.setTitle("Формування Додатку 05");
        } else {
            confirmationDialog = new Alert(Alert.AlertType.ERROR);
            confirmationDialog.setTitle("Помилка формування Додатку 05");
        }
        confirmationDialog.setHeaderText(null);
        confirmationDialog.setContentText(resultSave);
        confirmationDialog.showAndWait();


    }

    private FileChooser getFilePathFp2(String fam) {
        FileChooser fileChooser = new FileChooser();
        String resultDirName = "docs/results";
        File resultDir = new File(resultDirName);
        String resultSave = "";
        if (!resultDir.isDirectory()) {
            boolean success = resultDir.mkdirs();
            if (success) {
                System.out.println("Created path: " + resultDir.getPath());
            } else {
                resultSave = "Помилка створення каталогу для зберігання результатів";
                Alert confirmationDialog = null;
                confirmationDialog = new Alert(Alert.AlertType.ERROR);
                confirmationDialog.setTitle("Помилка формування форми П-2");
                confirmationDialog.setHeaderText(null);
                confirmationDialog.setContentText(resultSave);
                confirmationDialog.showAndWait();
            }
        }

        fileChooser.setInitialDirectory(resultDir);
        String resultFileName = fam+"_formaP2_" + LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        fileChooser.setInitialFileName(resultFileName);
        return fileChooser;
    }
}