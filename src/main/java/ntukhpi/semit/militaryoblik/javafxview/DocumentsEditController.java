package ntukhpi.semit.militaryoblik.javafxview;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ntukhpi.semit.militaryoblik.adapters.DocumentsAdapter;
import ntukhpi.semit.militaryoblik.adapters.EducationAdapter;
import ntukhpi.semit.militaryoblik.javafxutils.ControlledScene;
import org.springframework.stereotype.Component;

@Component
public class DocumentsEditController implements ControlledScene {

    @FXML
    private TextField numberTextField;

    @FXML
    private DatePicker dateDatePicker;

    @FXML
    private TextArea given;

    @FXML
    private Text pibText;

    @FXML
    private TextField seriesTextField;

    @FXML
    private ComboBox<String> typeComboBox;

    private DocumentsAllController mainController;
    private DocumentsAdapter selectedDocument;
    private boolean editingExistingDocument;

    @Override
    public void setMainController(Object mainController) {
        this.mainController = (DocumentsAllController) mainController;
    }

    @Override
    public void setData(Object data) {
        if (data instanceof DocumentsAdapter) {
            setDocument((DocumentsAdapter) data);
        }
    }

    private void setDocument(DocumentsAdapter document) {
        this.selectedDocument = document;
        editingExistingDocument = true;
        pibText.setText(document.getPib()); //TODO Ім'я та побатькові зробити ініціалами. Розробити окрему функцію
        typeComboBox.setValue(document.getType());
        seriesTextField.setText(document.getSeries());
        numberTextField.setText(document.getNumber());
        given.setText(document.getWhoGives());
        dateDatePicker.setValue(document.getDate());
    }

    @FXML
    void closeEdit(ActionEvent event) {
        try {
            ((Stage) typeComboBox.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void saveDocuments(ActionEvent event) {
        //TODO валідація введених даних
    }

    public void initialize() {
        ObservableList<String> docsOptions = FXCollections.observableArrayList(
                "Паперовий паспорт",
                "ID картка",
                "Закордонний паспорт",
                "Посвідчення особи офіцера",
                "Військовий квиток"
        );

        typeComboBox.setItems(docsOptions);
    }
}
