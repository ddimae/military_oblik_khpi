package ntukhpi.semit.militaryoblik.javafxview;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;
import ntukhpi.semit.militaryoblik.adapters.CurrentDoljnostInfoAdapter;
import ntukhpi.semit.militaryoblik.entity.CurrentDoljnostInfo;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.javafxutils.ControlledScene;
import ntukhpi.semit.militaryoblik.javafxutils.DataFormat;
import ntukhpi.semit.militaryoblik.javafxutils.Popup;
import ntukhpi.semit.militaryoblik.javafxutils.validators.OrderValidator;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.DateFieldValidator;
import ntukhpi.semit.militaryoblik.javafxutils.validators.common.TextFieldValidator;
import ntukhpi.semit.militaryoblik.service.CurrentDoljnostInfoServiceImpl;
import ntukhpi.semit.militaryoblik.service.MilitaryPersonServiceImpl;
import ntukhpi.semit.militaryoblik.service.PrepodServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

@Component
public class PositionOrdersEditController implements ControlledScene {
    @FXML
    public Label pibLabel;
    @FXML
    public Label statusPrepodLabel;
    @FXML
    public Label positionLabel;
    @FXML
    public Label kafedraLabel;

    @FXML
    public TextField orderTextField;
    @FXML
    public DatePicker dateDatePicker;
    @FXML
    public TextArea commentTextArea;

    @FXML
    public TextField orderDissTextField;
    @FXML
    public DatePicker dateDissDatePicker;
    @FXML
    public TextArea commentDissTextArea;

    private Prepod selectedPrepod;

    private CurrentDoljnostInfo currentDoljnostInfo;
    private Stage mainStage;
    private Stage currentStage;

    @Autowired
    OrderValidator orderValidator;
    @Autowired
    PrepodServiceImpl prepodService;
    @Autowired
    CurrentDoljnostInfoServiceImpl dolghnostService;
    @Autowired
    MilitaryPersonServiceImpl militaryPersonService;

    private ReservistsAllController mainController;

    @Override
    public void setMainController(Object mainController) {
        this.mainController = (ReservistsAllController) mainController;
    }

    @Override
    public void setData(Object data) {
        if (data instanceof CurrentDoljnostInfoAdapter)
            setCurrentDoljnostInfo((CurrentDoljnostInfoAdapter) data);
    }

    @Override
    public void setMainStage(Stage stage) {
        mainStage = stage;
    }

    @Override
    public void setCurrentStage(Stage stage) {
        currentStage = stage;
    }

    private void setCurrentDoljnostInfo(CurrentDoljnostInfoAdapter position) {

        statusPrepodLabel.setText(position.getStatusPrepod());
        positionLabel.setText(position.getDolghnName());
        kafedraLabel.setText(position.getKafedraName());
        orderTextField.setText(position.getNakazStart());
        dateDatePicker.getEditor().setText(position.getDateStart());
        if (position.getDateStart() != null && position.getDateStart().length() > 0) {
            dateDatePicker.setValue(LocalDate.parse(dateDatePicker.getEditor().getText(),
                    DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        }
        commentTextArea.setText(position.getCommentStart());
        orderDissTextField.setText(position.getNakazStop());
        dateDissDatePicker.getEditor().setText(position.getDateStop());
        if (position.getDateStop() != null && position.getDateStop().length() > 0) {
            dateDissDatePicker.setValue(LocalDate.parse(dateDissDatePicker.getEditor().getText(),
                    DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        }
        commentDissTextArea.setText(position.getCommentStop());
    }

    public void initialize() {
        selectedPrepod = prepodService.getPrepodById(ReservistsAllController.getSelectedPrepodId());
        pibLabel.setText(DataFormat.getPIB(selectedPrepod));
        currentDoljnostInfo = selectedPrepod.getPosadaNakazy();
        setCurrentDoljnostInfo(new CurrentDoljnostInfoAdapter(selectedPrepod));

        //Робиться датаПикер українським
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

        //Робиться датаПикерДисс українським
        dateDissDatePicker.setConverter(new StringConverter<LocalDate>() {
            String pattern = "dd.MM.yyyy";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                dateDissDatePicker.setPromptText(pattern.toLowerCase());
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

    @FXML
    void saveOrdersInfo(ActionEvent event) {
        String nakaz = orderTextField.getText() != null ? orderTextField.getText().trim() : "";
        String dateStr = dateDatePicker.getEditor().getText();
        String comment = commentTextArea.getText() != null ? commentTextArea.getText().trim() : "";
        String nakazDiss = orderDissTextField.getText() != null ? orderDissTextField.getText().trim() : "";
        String dateDissStr = dateDissDatePicker.getEditor().getText();
        String commentDiss = commentDissTextArea.getText() != null ? commentDissTextArea.getText().trim() : "";

        try {
            orderValidator.validate(new CurrentDoljnostInfoAdapter(dateStr, nakaz, comment,
                                                                    dateDissStr, nakazDiss, commentDiss,
                                                                    null, null, null));
        } catch (Exception e) {
            Popup.wrongInputAlert(e.getMessage());
            return;
        }

        try {
            LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            LocalDate dateDiss = null;
            if (dateDissStr.length() > 0)
                dateDiss = LocalDate.parse(dateDissStr, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            CurrentDoljnostInfo newCurrDolgnost = new CurrentDoljnostInfo(selectedPrepod, nakaz, date, comment,
                    nakazDiss, dateDiss, commentDiss);
            CurrentDoljnostInfo cd = selectedPrepod.getPosadaNakazy();
            if (cd == null) {
                dolghnostService.createCurrentDoljnostInfo(newCurrDolgnost);
            } else {
                dolghnostService.updateCurrentDoljnostInfo(cd.getId(), newCurrDolgnost);
            }
            closeEdit(null);
            Popup.successSave();

        } catch (Exception e) {
            Popup.wrongInputAlert(e.getMessage());
        }
    }


    @FXML
    void closeEdit(ActionEvent event) {
        MilitaryOblikKhPIMain.showPreviousStage(mainStage, currentStage);
    }


}

