package ntukhpi.semit.militaryoblik.javafxview;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;
import ntukhpi.semit.militaryoblik.adapters.ReservistAdapter;
import ntukhpi.semit.militaryoblik.entity.VSklad;
import ntukhpi.semit.militaryoblik.entity.VZvanie;
import ntukhpi.semit.militaryoblik.javafxutils.ControlledScene;
import ntukhpi.semit.militaryoblik.repository.VZvanieRepository;
import ntukhpi.semit.militaryoblik.service.VSkladServiceImpl;
import ntukhpi.semit.militaryoblik.service.VZvanieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class MilitaryRegistrationEditController implements ControlledScene {
    @FXML
    public Label pibLabel;
    @FXML
    public ComboBox<String> groupComboBox;
    @FXML
    public ComboBox<String> categoryComboBox;
    @FXML
    public ComboBox<String> vSkladComboBox;
    @FXML
    public ComboBox<String> rankComboBox;
    @FXML
    public ComboBox<String> validityComboBox;
    @FXML
    public TextField vosTextField;
    @FXML
    public TextField trcTextField;
    @FXML
    public TextField specialRegistrationTextField;


    @Autowired
    VZvanieRepository vZvanieRepository;

    private ReservistsAllController mainController;
    private ReservistAdapter selectedReservist;
    private Stage mainStage;
    private Stage currentStage;

    @Override
    public void setMainController(Object mainController) {
        this.mainController = (ReservistsAllController) mainController;
    }

    @Override
    public void setData(Object data) {
        if (data instanceof ReservistAdapter)
            setMilitaryRegistrationInfo((ReservistAdapter) data);
    }

    @Override
    public void setMainStage(Stage stage) {
        mainStage = stage;
    }

    @Override
    public void setCurrentStage(Stage stage) {
        currentStage = stage;
    }

    private void setMilitaryRegistrationInfo(ReservistAdapter reservist) {
        pibLabel.setText(reservist.getPib());

        vosTextField.setText(reservist.getVos());
        categoryComboBox.setValue(reservist.getCategory());
        groupComboBox.setValue("-не обрано");
        vSkladComboBox.setValue(reservist.getVSklad());
        rankComboBox.setValue(reservist.getRank());
        validityComboBox.setValue(reservist.getVPrydatnist());
        trcTextField.setText(reservist.getTrc());

    }

    @Autowired
    VSkladServiceImpl vSkladService;

    @Autowired
    VZvanieServiceImpl vZvanieService;


    public void initialize() {
        ObservableList<String> vSkladOptions = FXCollections.observableArrayList("-не обрано");
        vSkladOptions.addAll(vSkladService.getAllVSklad().stream().map(VSklad::getSkladName).toList());

        ObservableList<String> vZvanieOptions = FXCollections.observableArrayList("-не обрано");
        vZvanieOptions.addAll(vZvanieService.getAllVZvanie().stream().map(VZvanie::getZvanieName).toList());

        groupComboBox.getItems().addAll("-не обрано", "військовозабов'язаний", "призовник");
        categoryComboBox.getItems().addAll("-не обрано", "1", "2");
        vSkladComboBox.getItems().addAll(vSkladOptions);
        rankComboBox.getItems().addAll(vZvanieOptions);
        validityComboBox.getItems().addAll("-не обрано", "придатний", "обмежено-придатний", "непридатний");
    }

    @FXML
    public void closeEdit(ActionEvent actionEvent) {
        MilitaryOblikKhPIMain.showPreviousStage(mainStage, currentStage);
    }

    public void saveMilitaryRegistrationInfo(ActionEvent actionEvent) {

    }
}
