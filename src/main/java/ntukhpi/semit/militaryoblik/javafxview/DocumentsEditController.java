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
import ntukhpi.semit.militaryoblik.javafxutils.validators.DocumentValidator;
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
    DocumentValidator documentValidator;

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
        dateDatePicker.setValue(DataFormat.UkrStandartToLocalDate(selectedDocument.getDate()));
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
     * Спроба збереження/редагування даних форми в БД після валідації
     */
    @FXML
    void saveDocuments(ActionEvent event) {
        String docType = DataFormat.getPureValue(typeComboBox.getValue());
        String number = numberTextField.getText().trim();
        String whoGives = whoGivesTextArea.getText().trim();
        String date = dateDatePicker.getEditor().getText().trim();

        try {
            documentValidator.validate(new DocumentAdapter(null, docType, number, whoGives, date));
        } catch (Exception e) {
            Popup.wrongInputAlert(e.getMessage());
            return;
        }

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
