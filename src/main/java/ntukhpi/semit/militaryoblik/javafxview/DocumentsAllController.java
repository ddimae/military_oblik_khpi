package ntukhpi.semit.militaryoblik.javafxview;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;
import ntukhpi.semit.militaryoblik.adapters.DocumentsAdapter;
import ntukhpi.semit.militaryoblik.adapters.EducationAdapter;
import ntukhpi.semit.militaryoblik.javafxutils.Popup;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @FXML
    void deleteSelectedRow(ActionEvent event) {
        DocumentsAdapter selectedDocument = docsTableView.getSelectionModel().getSelectedItem();

        if (selectedDocument != null) {
            docsObservableList.remove(selectedDocument);
        } else {
            Popup.noSelectedRowAlert();
        }
    }

    @FXML
    void openAddWindow(ActionEvent event) { //FIXME Не працює передача ПІБ, бо не передається взагалі нічого (чекати на рішення від Кулак Анастасії)
//        MilitaryOblikKhPIMain.openEditWindow("/javafxview/EducationEdit.fxml", "Додати дані про навчання", this, null);
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

    private ObservableList<DocumentsAdapter> docsObservableList = getDocumentsData();
    private ObservableList<DocumentsAdapter> getDocumentsData() {
        List<DocumentsAdapter> docList = new ArrayList<>();

        docList.add(new DocumentsAdapter("ДВУХГЛАВОВ Д. Е", "Паперовий паспорт", "МН123456", "7115 УДМС УКРАЇНИ В ЧЕРКАСЬКІЙ ОБЛАСТІ", LocalDate.of(1995, 1, 23)));
        docList.add(new DocumentsAdapter("ДВУХГЛАВОВ Д. Е", "Закордонний паспорт", "АА573957", "7115 УДМС УКРАЇНИ В ЧЕРКАСЬКІЙ ОБЛАСТІ", LocalDate.of(1999, 5, 3)));

        return FXCollections.observableList(docList);
    };

    public void initialize() {
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        givenColumn.setCellValueFactory(new PropertyValueFactory<>("whoGives"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        updateTable(docsObservableList);
    }

    private void updateTable(ObservableList<DocumentsAdapter> docs) {
        docsTableView.setItems(docs);
    }
}
