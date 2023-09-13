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
import ntukhpi.semit.militaryoblik.adapters.DocumentAdapter;
import ntukhpi.semit.militaryoblik.entity.Document;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.javafxutils.ControlledScene;
import ntukhpi.semit.militaryoblik.javafxutils.DataFormat;
import ntukhpi.semit.militaryoblik.javafxutils.FormTextInput;
import ntukhpi.semit.militaryoblik.javafxutils.Popup;
import ntukhpi.semit.militaryoblik.service.DocumentServiceImpl;
import ntukhpi.semit.militaryoblik.service.MilitaryPersonServiceImpl;
import ntukhpi.semit.militaryoblik.service.PrepodServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
    private Prepod selectedPrepod;
    private DocumentAdapter selectedDocument;

    @Autowired
    PrepodServiceImpl prepodService;

    @Autowired
    DocumentServiceImpl documentService;

    @Autowired
    MilitaryPersonServiceImpl militaryPersonService;

    @Override
    public void setMainController(Object mainController) {
        this.mainController = (DocumentsAllController) mainController;
    }

    @Override
    public void setData(Object data) {
        setDocument((DocumentAdapter) data);
    }

    private void setDocument(DocumentAdapter document) {
        selectedPrepod = prepodService.getPrepodById(ReservistsAllController.getSelectedPrepodId());

        pibText.setText(DataFormat.getPIB(selectedPrepod));
        selectedDocument = document;

        for (Document doc : documentService.getAllDocumentByPrepod(selectedPrepod)) {
            if (selectedDocument == null || !selectedDocument.getType().equals(doc.getDocType())) {
                typeComboBox.getItems().remove(doc.getDocType());
            }
            if (selectedDocument == null || !selectedDocument.getType().equals(doc.getDocType()))
                switch (doc.getDocType()) {
                    case "Паперовий паспорт":
                        typeComboBox.getItems().remove("ID картка");
                        break;
                    case "ID картка":
                        typeComboBox.getItems().remove("Паперовий паспорт");
                        break;
                }
        }
        switch (militaryPersonService.getMilitaryPersonByPrepod(selectedPrepod).getVZvanie().getSkladName()) {
            case "Офіцерський склад":
                typeComboBox.getItems().remove("Військовий квиток");
                break;
            case "Рядовий та сержантський склад":
                typeComboBox.getItems().remove("Посвідчення особи офіцера");
                break;
        }
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

    private boolean validateDocument(String docType, String number, String whoGives, String date) {
        Pattern ukrOldSeriesNumberRegex = Pattern.compile("^[А-ЩЬЮЯҐЄІЇ]{2}\\d{6}$");
        Pattern ukrOldWhoGivesRegex = Pattern.compile("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії0-9\\s.,]+$");
        Pattern newSeriesRegex = Pattern.compile("^\\d{9}$");
        Pattern newWhoGivesRegex = Pattern.compile("^\\d{4}$");
        Pattern enOldSeriesNumberRegex = Pattern.compile("^[A-Z]{2}\\d{6}$");
        Pattern ukrDateRegex = Pattern.compile("^\\d{2}\\.\\d{2}\\.\\d{4}$");

        FormTextInput docTypeForm = new FormTextInput(-1, true, null, "Тип документу", docType, null);
        FormTextInput numberForm = new FormTextInput(10, true, null, "Серія та номер", number, null);
        FormTextInput whoGivesForm = new FormTextInput(255, true, null, "Ким видан", whoGives, null);
        FormTextInput dateForm = new FormTextInput(10, true, ukrDateRegex, "Дата видачі", date, "повинен мати формат дати: dd.mm.yyyy");

        try {
            docTypeForm.validate();

            switch (docType) {
                case "Паперовий паспорт":
                    numberForm.setRegex(ukrOldSeriesNumberRegex);
                    numberForm.setErrorMsg("паперовога паспорта повинно містити 2 великі українські літери та 6 цифр");
                    whoGivesForm.setRegex(ukrOldWhoGivesRegex);
                    whoGivesForm.setErrorMsg("може містити українські літери, цифри, розділові знаки");
                    break;
                case "ID картка":
                    numberForm.setRegex(newSeriesRegex);
                    numberForm.setErrorMsg("ID картки повинно містити 9 цифр");
                    whoGivesForm.setRegex(newWhoGivesRegex);
                    whoGivesForm.setErrorMsg("повинно містити тільки 4 цифри");
                    break;
                case "Закордонний паспорт":
                    numberForm.setRegex(enOldSeriesNumberRegex);
                    numberForm.setErrorMsg("закордонного паспорта повинно містити 2 великі латинські літери та 6 цифр");
                    whoGivesForm.setRegex(newWhoGivesRegex);
                    whoGivesForm.setErrorMsg("повинно містити тільки 4 цифри");
                    break;
                case "Посвідчення особи офіцера":
                case "Військовий квиток":
                    numberForm.setRegex(ukrOldSeriesNumberRegex);
                    numberForm.setErrorMsg("Серія та номер посвідчення повинні містити 2 великі українські літери та 6 цифр");
                    whoGivesForm.setRegex(ukrOldWhoGivesRegex);
                    whoGivesForm.setErrorMsg("може містити українські літери, цифри, розділові знаки");
                    break;
            }

            numberForm.validate();
            whoGivesForm.validate();
            dateForm.validate();
            if (!date.equals(DataFormat.localDateToUkStandart(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy")))))
                throw new Exception(dateForm.getErrorMsg());
        } catch (DateTimeParseException e) {
            Popup.wrongInputAlert(dateForm.getErrorMsg());
            return false;
        }
        catch (Exception e) {
            Popup.wrongInputAlert(e.getMessage());
            return false;
        }

        return true;
    }
    @FXML
    void saveDocuments(ActionEvent event) {
        String docType = DataFormat.getPureComboBoxValue(typeComboBox);

        String number = numberTextField.getText();
        String whoGives = whoGivesTextArea.getText();
        String date = dateDatePicker.getEditor().getText();

        number = number.trim();
        whoGives = whoGives.trim();

        if (!validateDocument(docType, number, whoGives, date))
            return;

        try {
            Document newDocument = new Document();

            newDocument.setPrepod(selectedPrepod);
            newDocument.setDocType(docType);
            newDocument.setDocNumber(number);
            newDocument.setKtoVyd(whoGives);
            newDocument.setDataVyd(dateDatePicker.getValue());

            if (selectedDocument == null)
                mainController.addNewDocument(newDocument);
            else
                mainController.updateDocument(selectedDocument, newDocument);

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
