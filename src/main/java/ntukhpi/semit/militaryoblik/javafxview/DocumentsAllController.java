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
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;
import ntukhpi.semit.militaryoblik.adapters.DocumentsAdapter;
import ntukhpi.semit.militaryoblik.entity.Document;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.javafxutils.DataFormat;
import ntukhpi.semit.militaryoblik.javafxutils.Popup;
import ntukhpi.semit.militaryoblik.service.DocumentServiceImpl;
import ntukhpi.semit.militaryoblik.service.PrepodServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.print.Doc;
import java.time.LocalDate;

@Component
public class DocumentsAllController {

    private final static String DOCUMENTS_ADD_JAVAFX = "/javafxview/DocumentsEdit.fxml";
    private final static String DOCUMENTS_EDIT_JAVAFX = "/javafxview/DocumentsEdit.fxml";
    private final static String DOCUMENTS_ADD_JAVAFX_TITLE = "Додати новий документ";
    private final static String DOCUMENTS_EDIT_JAVAFX_TITLE = "Редагувати документ";


    @FXML
    private TableColumn<DocumentsAdapter, LocalDate> dateColumn;

    @FXML
    private TableColumn<DocumentsAdapter, String> givenColumn;

    @FXML
    private TableColumn<DocumentsAdapter, String> numberColumn;

    @FXML
    private Text pibText;

    @FXML
    private TableColumn<DocumentsAdapter, String> typeColumn;

    @FXML
    private TableView<DocumentsAdapter> docsTableView;

    private ObservableList<DocumentsAdapter> docsObservableList;

    private Prepod selectedPrepod;

    @Autowired
    DocumentServiceImpl documentService;

    @Autowired
    PrepodServiceImpl prepodService;

    private ObservableList<DocumentsAdapter> getDocumentsData() {
        return FXCollections.observableArrayList(documentService.getAllDocumentByPrepod(selectedPrepod).stream().map(DocumentsAdapter::new).toList());
    }

    public void initialize() {
        selectedPrepod = prepodService.getPrepodById(ReservistsAllController.getSelectedPrepodId());

        pibText.setText(DataFormat.getPIB(selectedPrepod));
        docsTableView.setPlaceholder(new Label("Ця людина поки не має жодного документа"));

        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        givenColumn.setCellValueFactory(new PropertyValueFactory<>("whoGives"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        docsObservableList = getDocumentsData();

        docsTableView.setItems(docsObservableList);
    }

    public void refreshDocsTable() {
        docsTableView.refresh();
    }

    public void addNewDocument(DocumentsAdapter document) {
        docsObservableList.add(document);
        refreshDocsTable();
    }

    public void updateDocument(DocumentsAdapter oldDocument, DocumentsAdapter newDocument) {
        docsObservableList.remove(oldDocument);
        docsObservableList.add(newDocument);
        refreshDocsTable();
    }

    @FXML
    void deleteSelectedRow(ActionEvent event) {
        DocumentsAdapter selectedDocument = docsTableView.getSelectionModel().getSelectedItem();

        if (selectedDocument != null) {
            if (Popup.deleteConfirmation()) {
                docsObservableList.remove(selectedDocument);
                documentService.deleteDocument(selectedDocument.getId());
            }
        } else {
            Popup.noSelectedRowAlert();
        }
    }

    @FXML
    void openAddWindow(ActionEvent event) {
        MilitaryOblikKhPIMain.openEditWindow(DOCUMENTS_ADD_JAVAFX, DOCUMENTS_ADD_JAVAFX_TITLE, this, null);
    }

    @FXML
    void openEditWindow(ActionEvent event) {
        DocumentsAdapter selectedDocument = docsTableView.getSelectionModel().getSelectedItem();
        if (selectedDocument != null) {
            MilitaryOblikKhPIMain.openEditWindow(DOCUMENTS_EDIT_JAVAFX, DOCUMENTS_EDIT_JAVAFX_TITLE, this, selectedDocument);
        } else {
            Popup.noSelectedRowAlert();
        }
    }

    @FXML
    void returnToMainForm(ActionEvent event) {
        MilitaryOblikKhPIMain.showReservistsWindow();
    }
}
