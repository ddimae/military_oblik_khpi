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
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;
import ntukhpi.semit.militaryoblik.adapters.DocumentsAdapter;
import ntukhpi.semit.militaryoblik.entity.Document;
import ntukhpi.semit.militaryoblik.javafxutils.ControlledScene;
import ntukhpi.semit.militaryoblik.javafxutils.Popup;
import ntukhpi.semit.militaryoblik.service.DocumentServiceImpl;
import ntukhpi.semit.militaryoblik.service.PrepodServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.regex.Pattern;

@Component
public class DocumentsEditController implements ControlledScene {

    @FXML
    private TextField numberTextField;

    @FXML
    private DatePicker dateDatePicker;

    @FXML
    private TextArea whoGivesTextArea;

    @FXML
    private Text pibText;

    @FXML
    private ComboBox<String> typeComboBox;

    private DocumentsAllController mainController;
    private Long selectedPrepodId;
    private DocumentsAdapter selectedDocument;
//    private boolean editingExistingDocument;

    @Autowired
    PrepodServiceImpl prepodService;

    @Autowired
    DocumentServiceImpl documentService;

    @Override
    public void setMainController(Object mainController) {
        this.mainController = (DocumentsAllController) mainController;
    }

    @Override
    public void setData(Object data) {
        setDocument((DocumentsAdapter) data);
    }

    private void setDocument(DocumentsAdapter document) {
        selectedPrepodId = ReservistsAllController.getSelectedPrepodId();

        pibText.setText(MilitaryOblikKhPIMain.getPIB(prepodService.getPrepodById(selectedPrepodId)));

        selectedDocument = document;
        if (selectedDocument == null)
            return;

        typeComboBox.setValue(selectedDocument.getType());
        numberTextField.setText(selectedDocument.getNumber());
        whoGivesTextArea.setText(selectedDocument.getWhoGives());
        dateDatePicker.setValue(selectedDocument.getDate());
    }

    @FXML
    void closeEdit(ActionEvent event) {
        try {
            ((Stage) typeComboBox.getScene().getWindow()).close();
            MilitaryOblikKhPIMain.showDocumentsWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleTypeChange(ActionEvent event) {
        String type = typeComboBox.getValue();
        switch (type) {
            case "ID картка":
                numberTextField.setPromptText("123456789");
                whoGivesTextArea.setPromptText("1234");
                break;
            case "Закордонний паспорт":
                numberTextField.setPromptText("VG123456");
                whoGivesTextArea.setPromptText("1234");
                break;
            case "Паперовий паспорт":
            case "Посвідчення особи офіцера":
            case "Військовий квиток":
            default:
                numberTextField.setPromptText("МГ123456");
                whoGivesTextArea.setPromptText("Назва органу");
                break;
        }
    }

    @FXML
    void saveDocuments(ActionEvent event) {
        String docType = String.valueOf(typeComboBox.getValue());
        //TODO Брати із БД список всіх доків особи, дивитись які є і вилучати

        String number = numberTextField.getText();
        String whoGives = whoGivesTextArea.getText();
        LocalDate date = dateDatePicker.getValue();
        String dateString = MilitaryOblikKhPIMain.localDateToUkStandart(date);

          //TODO мейбі перенести після зберігання в бд

        Pattern ukrOldSeriesNumberRegex = Pattern.compile("^[А-ЩЬЮЯҐЄІЇ]{2}\\d{6}$");
        Pattern ukrOldWhoGivesRegex = Pattern.compile("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії0-9\\\\s.,]{1,255}$");
        Pattern newSeriesRegex = Pattern.compile("^\\d{9}$");
        Pattern newWhoGivesRegex = Pattern.compile("^\\d{4}$");
        Pattern enOldSeriesNumberRegex = Pattern.compile("^[A-Z]{2}\\d{6}$");

        number = number.trim();
        whoGives = whoGives.trim();

        try {
            if (docType.equals("null") || docType.isEmpty())
                throw new Exception("\"Тип документа\" є обов'язковим полем");
            if (number.isEmpty())
                throw new Exception("\"Серія та номер\" є обов'язковим полем");
            if (whoGives.isEmpty())
                throw new Exception("\"Ким видан\" є обов'язковим полем");
            if (date == null) {
                dateDatePicker.setValue(null);
                throw new Exception("Неправильний формат дати: dd.mm.yyyy");
            }
            if (dateString == null || dateString.isEmpty())
                throw new Exception("\"Дата видачі\" є обов'язковим полем");

            switch (docType) {
                case "Паперовий паспорт":
                    if (!ukrOldSeriesNumberRegex.matcher(number).matches())
                        throw new Exception("Серія та номер паперовога паспорта повинні містити 2 великі українські літери та 6 цифр");
                    if (!ukrOldWhoGivesRegex.matcher(whoGives).matches())
                        throw new Exception("Поле \"Ким видан\" може містити українські літери, цифри, розділові знаки (максимум 255 символів)");
                    break;
                case "ID картка":
                    if (!newSeriesRegex.matcher(number).matches())
                        throw new Exception("Номер ID картки повинен містити 9 цифр");
                    if (!newWhoGivesRegex.matcher(whoGives).matches())
                        throw new Exception("Поле \"Ким видан\" може містити тільки 4 цифри");
                    break;
                case "Закордонний паспорт":
                    if (!enOldSeriesNumberRegex.matcher(number).matches())
                        throw new Exception("Серія та номер закордонного паспорта повинні містити 2 великі латинські літери та 6 цифр");
                    if (!newWhoGivesRegex.matcher(whoGives).matches())
                        throw new Exception("Поле \"Ким видан\" може містити тільки 4 цифри");
                    break;
                case "Посвідчення особи офіцера":
                case "Військовий квиток":
                    if (!ukrOldSeriesNumberRegex.matcher(number).matches())
                        throw new Exception("Серія та номер посвідчення повинні містити 2 великі українські літери та 6 цифр");
                    if (!ukrOldWhoGivesRegex.matcher(whoGives).matches())
                        throw new Exception("Поле \"Ким видан\" може містити українські літери, цифри, розділові знаки (максимум 255 символів)");
                    break;
            }

            Document newDocument = new Document();

            newDocument.setPrepod(prepodService.getPrepodById(selectedPrepodId));
            newDocument.setDocType(docType);
            newDocument.setDocNumber(number);
            newDocument.setKtoVyd(whoGives);
            newDocument.setDataVyd(date);

            if (selectedDocument == null) {
                documentService.createDocument(newDocument);
                mainController.addNewDocument(new DocumentsAdapter(newDocument));
            }
            else {
                documentService.updateDocument(selectedDocument.getId(), newDocument);
                mainController.updateDocument(selectedDocument, new DocumentsAdapter(newDocument));
            }

            closeEdit(null);
            Popup.successSave();
        } catch (Exception e) {
            Popup.wrongInputAlert(e.getMessage());
        }
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
