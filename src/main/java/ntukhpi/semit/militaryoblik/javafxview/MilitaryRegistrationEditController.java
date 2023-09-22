/*
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
import ntukhpi.semit.militaryoblik.entity.VZvanie;
import ntukhpi.semit.militaryoblik.javafxutils.ControlledScene;
import ntukhpi.semit.militaryoblik.repository.VZvanieRepository;
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
    @FXML
    public TextField registrationDateTextField;

    @Autowired
    VZvanieRepository vZvanieRepository;

    private ReservistsAllController mainController;
    private ReservistAdapter selectedReservist;

    @Override
    public void setMainController(Object mainController) {
        this.mainController = (ReservistsAllController) mainController;
    }

    @Override
    public void setData(Object data) {
        if (data instanceof ReservistAdapter)
            setMilitaryRegistrationInfo((ReservistAdapter) data);
    }

    private void setMilitaryRegistrationInfo(ReservistAdapter reservist) {
        pibLabel.setText(reservist.getPib());
        groupComboBox.setValue("-не обрано");
        categoryComboBox.setValue(reservist.getCategory());
        vSkladComboBox.setValue(reservist.getType());
        rankComboBox.setValue(reservist.getRank());
        validityComboBox.setValue("Придатний до військової служби");

        vosTextField.setText(reservist.getVos());

        trcTextField.setText(reservist.getTrc());

        specialRegistrationTextField.setText("Что-то здесь точно должно быть написано");

        registrationDateTextField.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    }

    public void initialize() {
        groupComboBox.getItems().addAll("1", "2");
        categoryComboBox.getItems().addAll();
        vSkladComboBox.getItems().addAll(
                "-не обрано",
                "Рядовий та сержантський склад",
                "Командний",
                "Інженерний",
                "Інженерно-технічний",
                "Технічно-оперативний",
                "Медичний"
        );
        rankComboBox.getItems().addAll(vZvanieRepository.findAll().stream().map(VZvanie::getZvanieName).sorted().toList());
        validityComboBox.getItems().addAll(
                "-не обрано",
                "Придатний до військової служби",
                "Придатний до військової служби з незначними обмеженнями",
                "Обмежено придатний до військової служби",
                "Тимчасово непридатний",
                "Повна непридатність");
    }

    @FXML
    public void closeEdit(ActionEvent actionEvent) {
        try {
            ((Stage) groupComboBox.getScene().getWindow()).close();
            MilitaryOblikKhPIMain.showReservistsWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveMilitaryRegistrationInfo(ActionEvent actionEvent) {

    }
}
*/
