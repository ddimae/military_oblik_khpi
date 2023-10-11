package ntukhpi.semit.militaryoblik.javafxview;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;
import ntukhpi.semit.militaryoblik.adapters.DocumentAdapter;
import ntukhpi.semit.militaryoblik.entity.Document;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.javafxutils.AllStageSettings;
import ntukhpi.semit.militaryoblik.javafxutils.ControlledScene;
import ntukhpi.semit.militaryoblik.javafxutils.DataFormat;
import ntukhpi.semit.militaryoblik.javafxutils.Popup;
import ntukhpi.semit.militaryoblik.service.DocumentServiceImpl;
import ntukhpi.semit.militaryoblik.service.PrepodServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


/**
 * Контролер форми показу документів резервіста
 *
 * @author Степанов Михайло
 */
@Component
public class DocumentsAllController implements ControlledScene {

    @FXML
    private TableColumn<DocumentAdapter, LocalDate> dateColumn;

    @FXML
    private TableColumn<DocumentAdapter, String> givenColumn;

    @FXML
    private TableColumn<DocumentAdapter, String> numberColumn;

    @FXML
    private Text pibText;

    @FXML
    private TableColumn<DocumentAdapter, String> typeColumn;

    @FXML
    private TableView<DocumentAdapter> docsTableView;

    private ObservableList<DocumentAdapter> docsObservableList;
    private Prepod selectedPrepod;

    @Autowired
    DocumentServiceImpl documentService;

    @Autowired
    PrepodServiceImpl prepodService;

    private ReservistsAllController mainController;
    private Stage mainStage;
    private Stage currentStage;

    @Override
    public void setMainController(Object controller) {
        mainController = (ReservistsAllController) controller;
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
     * Отримання з БД списоку всіх документів конкретного резервіста
     * та перетворення його на ObservableList
     *
     * @return ObservableList з усіма документави резервіста
     */
    private ObservableList<DocumentAdapter> getDocumentsData() {
        return FXCollections.observableArrayList(documentService.getAllDocumentByPrepod(selectedPrepod).stream().map(DocumentAdapter::new).toList());
    }


    /**
     * Початкове ініціалізація иа зоаповнення таблиці документами
     * обраного резервіста
     */
    public void initialize() {
        // Зберігання викладача на випадок зміни вибору в формі ReservistsAll
        selectedPrepod = prepodService.getPrepodById(ReservistsAllController.getSelectedPrepodId());

        pibText.setText(DataFormat.getPIB(selectedPrepod));
        docsTableView.setPlaceholder(new Label("Ця людина поки не має жодного документа"));

        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        givenColumn.setCellValueFactory(new PropertyValueFactory<>("whoGives"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateColumn.setCellFactory(column -> DataFormat.getTableDateCellFactory());

        docsObservableList = getDocumentsData();

        docsTableView.setItems(docsObservableList);
    }

    /**
     * Оновлення таблиці після її зміни
     */
    public void refreshDocsTable() {
        docsTableView.refresh();
    }


    /**
     * Додавання нового документу до таблиці та БД
     *
     * @param document Новий документ
     */
    public void addNewDocument(Document document) {
        documentService.createDocument(document);

        docsObservableList.add(new DocumentAdapter(document));
        refreshDocsTable();
    }


    /**
     * Обновлення документа в таблиці та БД
     *
     * @param oldDocument Старий зразок документа
     * @param newDocument Новий зразок документа
     */
    public void updateDocument(DocumentAdapter oldDocument, Document newDocument) {
        documentService.updateDocument(oldDocument.getId(), newDocument);

        docsObservableList.remove(oldDocument);
        docsObservableList.add(new DocumentAdapter(newDocument));
        refreshDocsTable();
    }

    /**
     * Видалення обранного документу з таблиці та БД
     */
    @FXML
    void deleteSelectedRow(ActionEvent event) {
        DocumentAdapter selectedDocument = docsTableView.getSelectionModel().getSelectedItem();

        if (selectedDocument != null) {
            if (Popup.deleteConfirmation()) {   // Питаємо користувача
                docsObservableList.remove(selectedDocument);
                documentService.deleteDocument(selectedDocument.getId());
            }
        } else {
            Popup.noSelectedRowAlert();
        }
    }

    /**
     * Перехід до форми додавання нового документа
     */
    @FXML
    void openAddWindow(ActionEvent event) {
        MilitaryOblikKhPIMain.showStage(AllStageSettings.documentsAdd, currentStage, this, null);
    }

    /**
     * Перехід до форми зміни обраного документа
     */
    @FXML
    void openEditWindow(ActionEvent event) {
        DocumentAdapter selectedDocument = docsTableView.getSelectionModel().getSelectedItem();
        if (selectedDocument != null)
            MilitaryOblikKhPIMain.showStage(AllStageSettings.documentsEdit, currentStage, this, selectedDocument);
        else
            Popup.noSelectedRowAlert();
    }

    /**
     * Перехід до материнської форми
     */
    @FXML
    void returnToMainForm(ActionEvent event) {
        MilitaryOblikKhPIMain.showPreviousStage(mainStage, currentStage);
    }
}
