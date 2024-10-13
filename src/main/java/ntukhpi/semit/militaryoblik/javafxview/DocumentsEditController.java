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
import javafx.util.StringConverter;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;
import ntukhpi.semit.militaryoblik.adapters.DocumentAdapter;
import ntukhpi.semit.militaryoblik.entity.Document;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.javafxutils.*;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.DateFieldValidator;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.TextFieldValidator;
import ntukhpi.semit.militaryoblik.service.DocumentServiceImpl;
import ntukhpi.semit.militaryoblik.service.MilitaryPersonServiceImpl;
import ntukhpi.semit.militaryoblik.service.PrepodServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;


/**
 * Контролер форми редагування документа резервіста
 *
 * @author Степанов Михайло
 */
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
    private Stage mainStage;
    private Stage currentStage;

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

    @Override
    public void setMainStage(Stage stage) {
        mainStage = stage;
    }

    @Override
    public void setCurrentStage(Stage stage) {
        currentStage = stage;
    }


    /**
     * Заповнює форму даними обраного документа.
     * Вилучає з комбобоксу типи документів, які
     * вже є в БД та/або взаємовилучають один одного
     *
     * @param document Інформація про обраний документ
     */
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

    /**
     * Початкова ініціалізація комбобоксу
     */
    public void initialize() {
        ObservableList<String> docsOptions = FXCollections.observableArrayList(
                "Паперовий паспорт",
                "ID картка",
                "Закордонний паспорт",
                "Посвідчення особи офіцера",
                "Військовий квиток"
        );

        typeComboBox.setItems(docsOptions);

        //Робиться датаПикерДисс українським
        dateDatePicker.setConverter(new StringConverter<LocalDate>() {
            String pattern = "dd.MM.yyyy";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                dateDatePicker.setPromptText(pattern.toLowerCase());
            }

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
    }


    /**
     * Валідація даних вписаних у форму
     *
     * @param docType Обраний тип документа
     * @param number Серія та/або номер документа
     * @param whoGives Орган, що видав документ
     * @param date Дата видачі
     * @return true - Валідація пройдена. false - Валідація не пройдена
     */
    public /*static*/ boolean validateDocument(String docType, String number, String whoGives, String date) {
        Pattern ukrOldSeriesNumberRegex = Pattern.compile("^[А-ЩЬЮЯҐЄІЇ]{2}\\d{6}$");
        Pattern ukrOldWhoGivesRegex = Pattern.compile("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії0-9\\s.,'`_\\-]+$");
        Pattern newSeriesRegex = Pattern.compile("^\\d{9}$");
        Pattern newWhoGivesRegex = Pattern.compile("^\\d{4}$");
        Pattern enOldSeriesNumberRegex = Pattern.compile("^[A-Z]{2}\\d{6}$");
        Pattern ukrDateRegex = Pattern.compile("^\\d{2}\\.\\d{2}\\.\\d{4}$");

        TextFieldValidator docTypeValidator = new TextFieldValidator(-1, true, null, "Тип документу", docType, null);
        TextFieldValidator numberValidator = new TextFieldValidator(10, true, null, "Серія та номер", number, null);
        TextFieldValidator whoGivesValidator = new TextFieldValidator(255, true, null, "Ким видан", whoGives, null);
        DateFieldValidator dateValidator = new DateFieldValidator(true, ukrDateRegex, "Дата видачі", date, "повинно мати формат дати: dd.mm.yyyy");

        try {
            docTypeValidator.validate();

            switch (docType) {
                case "Паперовий паспорт":
                    numberValidator.setRegex(ukrOldSeriesNumberRegex);
                    numberValidator.setErrorMsg("паперовога паспорта повинно містити 2 великі українські літери та 6 цифр");
                    whoGivesValidator.setRegex(ukrOldWhoGivesRegex);
                    whoGivesValidator.setErrorMsg("може містити українські літери, цифри, розділові знаки");
                    break;
                case "ID картка":
                    numberValidator.setRegex(newSeriesRegex);
                    numberValidator.setErrorMsg("ID картки повинно містити 9 цифр");
                    whoGivesValidator.setRegex(newWhoGivesRegex);
                    whoGivesValidator.setErrorMsg("повинно містити тільки 4 цифри");
                    break;
                case "Закордонний паспорт":
                    numberValidator.setRegex(enOldSeriesNumberRegex);
                    numberValidator.setErrorMsg("закордонного паспорта повинно містити 2 великі латинські літери та 6 цифр");
                    whoGivesValidator.setRegex(newWhoGivesRegex);
                    whoGivesValidator.setErrorMsg("повинно містити тільки 4 цифри");
                    break;
                case "Посвідчення особи офіцера":
                case "Військовий квиток":
                    numberValidator.setRegex(ukrOldSeriesNumberRegex);
                    numberValidator.setErrorMsg("Серія та номер посвідчення повинні містити 2 великі українські літери та 6 цифр");
                    whoGivesValidator.setRegex(ukrOldWhoGivesRegex);
                    whoGivesValidator.setErrorMsg("може містити українські літери, цифри, розділові знаки");
                    break;
            }

            numberValidator.validate();
            whoGivesValidator.validate();
            dateValidator.validate();
        }
        catch (Exception e) {
            Popup.wrongInputAlert(e.getMessage());
            return false;
        }

        return true;
    }


    /**
     * Спроба збереження/редагування даних форми в БД після валідації
     */
    @FXML
    void saveDocuments(ActionEvent event) {
        String docType = DataFormat.getPureValue(typeComboBox.getValue());

        String number = numberTextField.getText().trim();
        String whoGives = whoGivesTextArea.getText().trim();
        String date = dateDatePicker.getEditor().getText().trim();

        if (!validateDocument(docType, number, whoGives, date))
            return;

        try {
            Document newDocument = new Document();

            newDocument.setPrepod(selectedPrepod);
            newDocument.setDocType(docType);
            newDocument.setDocNumber(number);
            newDocument.setKtoVyd(whoGives);
            newDocument.setDataVyd(LocalDate.parse(dateDatePicker.getEditor().getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));

            if (selectedDocument == null)
                mainController.addNewDocument(newDocument);
            else
                mainController.updateDocument(selectedDocument, newDocument);

            closeEdit(null);
            Popup.successSave();
        } catch (Exception e) {
            e.printStackTrace();
            Popup.internalAlert(e.getMessage());
        }
    }

    /**
     * Обробник зміни значення комбобоксу типу документа
     */
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

    /**
     * Перехід до материнської форми
     */
    @FXML
    void closeEdit(ActionEvent event) {
        MilitaryOblikKhPIMain.showPreviousStage(mainStage, currentStage);
    }
}
