/*
package ntukhpi.semit.militaryoblik.javafxview;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.util.converter.DateStringConverter;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;
import ntukhpi.semit.militaryoblik.adapters.DocumentAdapter;
import ntukhpi.semit.militaryoblik.entity.Document;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.javafxutils.DataFormat;
import ntukhpi.semit.militaryoblik.javafxutils.Popup;
import ntukhpi.semit.militaryoblik.service.DocumentServiceImpl;
import ntukhpi.semit.militaryoblik.service.PrepodServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@Component
public class DocumentsAllController {

    // ========== Константи для переходу на форму з редагуванням/додаванням нових документів ==========
    private final static String DOCUMENTS_ADD_JAVAFX = "/javafxview/DocumentsEdit.fxml";
    private final static String DOCUMENTS_EDIT_JAVAFX = DOCUMENTS_ADD_JAVAFX;
    private final static String DOCUMENTS_ADD_JAVAFX_TITLE = "Додати новий документ";
    private final static String DOCUMENTS_EDIT_JAVAFX_TITLE = "Редагувати документ";

    // ========== Інтерактивні елементи форми ==========
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

    // Список для зображення всіх документів в таблиці
    private ObservableList<DocumentAdapter> docsObservableList;

    // Обраний в ReservistsAll викладач
    private Prepod selectedPrepod;

    // ========== Сервіси для роботи з БД ==========
    @Autowired
    DocumentServiceImpl documentService;

    @Autowired
    PrepodServiceImpl prepodService;


    */
/**
     * Отримує з БД список всіх документів конкретного викладача та перетворює його на ObservableList
     * @return ObservableList з усіма документави викладача
     *//*

    private ObservableList<DocumentAdapter> getDocumentsData() {
        return FXCollections.observableArrayList(documentService.getAllDocumentByPrepod(selectedPrepod).stream().map(DocumentAdapter::new).toList());
    }


    */
/**
     * Ініціалізація форми
     *//*

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

    */
/**
     * Обновляє таблицю після доданих чи прибраних даних
     *//*

    public void refreshDocsTable() {
        docsTableView.refresh();
    }


    */
/**
     * Додавання нового документу до таблиці та БД
     * @param document Новий документ
     *//*

    public void addNewDocument(Document document) {
        documentService.createDocument(document);

        docsObservableList.add(new DocumentAdapter(document));
        refreshDocsTable();
    }


    */
/**
     * Обновлення документа в таблиці та БД
     * @param oldDocument Старий зразок документа
     * @param newDocument Новий зразок документа
     *//*

    public void updateDocument(DocumentAdapter oldDocument, Document newDocument) {
        documentService.updateDocument(oldDocument.getId(), newDocument);

        docsObservableList.remove(oldDocument);
        docsObservableList.add(new DocumentAdapter(newDocument));
        refreshDocsTable();
    }

    */
/**
     * Видаляє обраний документ з таблиці та БД
     * @param event
     *//*

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

    */
/**
     * Відкриває вікно додавання нового документа
     * @param event
     *//*

    @FXML
    void openAddWindow(ActionEvent event) {
        MilitaryOblikKhPIMain.openEditWindow(DOCUMENTS_ADD_JAVAFX, DOCUMENTS_ADD_JAVAFX_TITLE, this, null);
    }

    */
/**
     * Відкриває вікно зміни документа
     * @param event
     *//*

    @FXML
    void openEditWindow(ActionEvent event) {
        DocumentAdapter selectedDocument = docsTableView.getSelectionModel().getSelectedItem();
        if (selectedDocument != null)
            MilitaryOblikKhPIMain.openEditWindow(DOCUMENTS_EDIT_JAVAFX, DOCUMENTS_EDIT_JAVAFX_TITLE, this, selectedDocument);
        else
            Popup.noSelectedRowAlert();
    }

    */
/**
     * Відкриває попереднє вікно
     * @param event
     *//*

    @FXML
    void returnToMainForm(ActionEvent event) {
        MilitaryOblikKhPIMain.showReservistsWindow();
    }
}
*/
