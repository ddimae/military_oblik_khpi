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
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

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
        numberTextField.setText(document.getNumber());
        whoGivesTextArea.setText(document.getWhoGives());
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
//        try {
//            String docType = typeComboBox.getValue();
//            //TODO Брати із БД список всіх доків особи, дивитись які є і вилучати
//
//            String number = numberTextField.getText();
//            String whoGives = whoGivesTextArea.getText();
//            String dateString = dateDatePicker.getAccessibleText();
//            LocalDate date;
//
//            try {   //TODO Недороблені останні кейси
//                switch (docType) {
//                    case "Паперовий паспорт":
////                        if (!series.matches("^$"))
////                            throw new Exception("Серія паперового паспорта повинна містити 2 великі українські літери");
//                        if (!number.matches("^[А-ЩЬЮЯҐЄІЇ]{2}\\d{6}$"))
//                            throw new Exception("Серія та номер паперовога паспорта повинні містити 2 великі українські літери та 6 цифр");
//                        if (!whoGives.matches("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії0-9\\s.,]{0,255}$"))
//                            throw new Exception("Поле \"Ким видан\" може містити українські літери, цифри, розділові знаки (максимум 255 символів)");
//                        break;
//                    case "ID картка":
//                        if (!number.matches("^\\d{9}$"))
//                            throw new Exception("Номер ID картки повинен містити 9 цифр");
//                        if (!whoGives.matches("^\\d{4}$"))
//                            throw new Exception("Поле \"Ким видан\" може містити тільки 4 цифри");
//                        break;
//                    case "Закордонний паспорт":
//                        if (!number.matches("^[A-Z]{2}\\d{6}$"))
//                            throw new Exception("Серія та номер закордонного паспорта повинні містити 2 великі латинські літери та 6 цифр");
//                        if (!whoGives.matches("^\\d{4}$"))
//                            throw new Exception("Поле \"Ким видан\" може містити тільки 4 цифри");
//                        break;
//                    case "Посвідчення особи офіцера":
//                        if (!number.matches("^[А-ЩЬЮЯҐЄІЇ]{2}\\d{6}$"))
//                            throw new Exception("Серія та номер посвідчення повинні містити 2 великі українські літери та 6 цифр");
//                        if (!whoGives.matches("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії0-9\\s.,]{0,255}$"))
//                            throw new Exception("Поле \"Ким видан\" може містити українські літери, цифри, розділові знаки (максимум 255 символів)");
//                        break;
//                    case "Військовий квиток":
//                        if (!number.matches("^[А-ЩЬЮЯҐЄІЇ]{2}\\d{6}$"))
//                            throw new Exception("Серія та номер посвідчення повинні містити 2 великі українські літери та 6 цифр");
//                        if (!whoGives.matches("^[А-ЩЬЮЯҐЄІЇа-щьюяґєії0-9\\s.,]{0,255}$"))
//                            throw new Exception("Поле \"Ким видан\" може містити українські літери, цифри, розділові знаки (максимум 255 символів)");
//                        break;
//                }
//                try {
//                    date = LocalDate.parse(dateString);
//                } catch (Exception e) {
//                    throw new Exception("Неправильний формат дати: dd.mm.yyyy");
//                }
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
//
//
////            DocumentsAdapter editedDocument = new DocumentsAdapter(pibText.getText(), , , , , );
//        } catch (Exception e) {
//            System.out.println("Error here");
//        }
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
