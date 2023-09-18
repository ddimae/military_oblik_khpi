package ntukhpi.semit.militaryoblik.javafxview;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;
import ntukhpi.semit.militaryoblik.adapters.DoljnostAdapter;
import ntukhpi.semit.militaryoblik.entity.CurrentDoljnostInfo;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.javafxutils.ControlledScene;
import ntukhpi.semit.militaryoblik.javafxutils.DataFormat;
import ntukhpi.semit.militaryoblik.javafxutils.Popup;
import ntukhpi.semit.militaryoblik.service.CurrentDoljnostInfoServiceImpl;
import ntukhpi.semit.militaryoblik.service.MilitaryPersonServiceImpl;
import ntukhpi.semit.militaryoblik.service.PrepodServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PositionEditController implements ControlledScene {
    @FXML
    public Label pibLabel;
    @FXML
    public Label statusLabel;
    @FXML
    public Label positionLabel;
    @FXML
    public Label departmentLabel;
    @FXML
    public Button dismissalButton;
    @FXML
    public TextField orderTextField;
    @FXML
    public DatePicker dateDatePicker;
    @FXML
    public TextArea commentTextArea;

    private Prepod selectedPrepod;
    private CurrentDoljnostInfo selectedDoljnost;

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
        if (data instanceof DoljnostAdapter)
            setCurrentDoljnostInfo((DoljnostAdapter) data);
    }

    private void setCurrentDoljnostInfo(DoljnostAdapter position) {
        selectedDoljnost = position.getCurrentDoljnostInfo();

        orderTextField.setText(selectedDoljnost.getNumNakazStart());
        dateDatePicker.setValue(selectedDoljnost.getDateStart());
        commentTextArea.setText(selectedDoljnost.getCommentStart());
    }

    public void initialize() {
        selectedPrepod = prepodService.getPrepodById(ReservistsAllController.getSelectedPrepodId());

        pibLabel.setText(DataFormat.getPIB(selectedPrepod));
        statusLabel.setText("ПРАЦЮЄ");
        positionLabel.setText(String.valueOf(selectedPrepod.getDolghnost()));
        departmentLabel.setText(String.valueOf(selectedPrepod.getKafedra()));
    }

    @FXML
    void savePosition(ActionEvent event) {
        String nakaz = orderTextField.getText();
        String date = dateDatePicker.getEditor().getText();
        String comment = commentTextArea.getText();

        try {
            if (selectedDoljnost == null) {
                selectedDoljnost = dolghnostService.getCurrentDoljnost();

                 orderTextField.setText(selectedDoljnost.getNumNakazStart());
                 dateDatePicker.setValue(selectedDoljnost.getDateStart());
                 commentTextArea.setText(selectedDoljnost.getCommentStart());
            } else {
                selectedDoljnost.setNumNakazStart(nakaz);
                selectedDoljnost.setDateStart(dateDatePicker.getValue());
                selectedDoljnost.setCommentStart(comment);

//                 dolghnostService.updateCurrentDoljnostInfo(selectedDoljnost.getId(), selectedDoljnost);

                closeEdit(null);
                Popup.successSave();
            }
        } catch (Exception e) {
            Popup.wrongInputAlert(e.getMessage());
        }
    }

    @FXML
    void closeEdit(ActionEvent event) {
        try {
            ((Stage) commentTextArea.getScene().getWindow()).close();
            MilitaryOblikKhPIMain.showReservistsWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





////    @Override
////    public void setData(Object data) {
////        setDoljnost((DoljnostAdapter) data);
////    }
//
//    @Override
//    public void setData(Object data) {
//        selectedPrepod = prepodService.getPrepodById(ReservistsAllController.getSelectedPrepodId());
//        selectedDoljnost = (DoljnostAdapter) data;
//
//        pibLabel.setText(DataFormat.getPIB(selectedPrepod));
//        positionLabel.setText(String.valueOf(selectedPrepod.getDolghnost()));
//        departmentLabel.setText(String.valueOf(selectedPrepod.getKafedra()));
//        orderTextField.setText(selectedDoljnost.getNakazStart());
//        dateDatePicker.setValue(selectedDoljnost.getDateStart());
//        commentTextArea.setText(selectedDoljnost.getCommentStart());
//    }
//
//    private void changeStatusToDismissed() {
//        statusLabel.setText("ЗВІЛЬНЕНИЙ");
//    }
//
////    private void setDoljnost(DoljnostAdapter position) {
////        selectedDoljnost = position;
////
////        orderTextField.setText(selectedDoljnost.getNakazStart());
////        dateDatePicker.setValue(selectedDoljnost.getDateStart());
////        commentTextArea.setText(selectedDoljnost.getCommentStart());
////    }
//
////    private ObservableList<DoljnostAdapter> doljnostObservableList;
////    private ObservableList<DoljnostAdapter> getDoljnostObservableList() {
////        return FXCollections.observableArrayList(dolghnostService.getAllDoljnostByPrepod(selectedPrepod).stream().map(DoljnostAdapter::new).toList());
////    }
//
////    public void updatePosition(DoljnostAdapter oldData, CurrentDoljnostInfo newData) {
////        dolghnostService.updateCurrentDoljnostInfo(oldData.getId(), newData);
////
////        doljnostObservableList.remove(oldData);
////        doljnostObservableList.add(new DoljnostAdapter(newData));
////    }
//
//    @FXML
//    void savePosition(ActionEvent event) {
//        String nakaz = orderTextField.getText();
//        String date = dateDatePicker.getEditor().getText();
//        String comment = commentTextArea.getText();
//
//        try {
//            selectedDoljnost.setNakazStart(nakaz);
//            selectedDoljnost.setDateStart(dateDatePicker.getValue());
//            selectedDoljnost.setCommentStart(comment);
//
//            dolghnostService.updateCurrentDoljnostInfo(selectedDoljnost.getId(), selectedDoljnost.getCurrentDoljnostInfo());
//
//            closeEdit(null);
//
//            Popup.successSave();
//        } catch (Exception e) {
//            Popup.wrongInputAlert(e.getMessage());
//        }
//    }
////    @FXML
////    void savePosition(ActionEvent event) {
////        String nakaz = orderTextField.getText();
////        String date = dateDatePicker.getEditor().getText();
////        String comment = commentTextArea.getText();
////
////        try {
////            CurrentDoljnostInfo newDoljnost = new CurrentDoljnostInfo();
////
////            newDoljnost.setNumNakazStart(nakaz);
////            newDoljnost.setDateStart(dateDatePicker.getValue());
////            newDoljnost.setCommentStart(comment);
////
////            updatePosition(selectedDoljnost, newDoljnost);
////
////            closeEdit(null);
////            Popup.successSave();
////        } catch (Exception e) {
////            Popup.wrongInputAlert(e.getMessage());
////        }
////    }
//
//    @FXML
//    void closeEdit(ActionEvent event) {
//        try {
//            ((Stage) commentTextArea.getScene().getWindow()).close();
//            MilitaryOblikKhPIMain.showReservistsWindow();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void initialize() {
//        selectedPrepod = prepodService.getPrepodById(ReservistsAllController.getSelectedPrepodId());
//
//        pibLabel.setText(DataFormat.getPIB(selectedPrepod));
//        positionLabel.setText(String.valueOf(selectedPrepod.getDolghnost()));
//        departmentLabel.setText(String.valueOf(selectedPrepod.getKafedra()));
//
//        try {
//            List<CurrentDoljnostInfo> doljnostList = dolghnostService.getAllDoljnostByPrepod(selectedPrepod);
//
//            if (!doljnostList.isEmpty()) {
//                selectedDoljnost = new DoljnostAdapter((CurrentDoljnostInfo) doljnostList); // Перетворюємо його в DoljnostAdapter
//
//                orderTextField.setText(selectedDoljnost.getNakazStart());
//                dateDatePicker.setValue(selectedDoljnost.getDateStart());
//                commentTextArea.setText(selectedDoljnost.getCommentStart());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

