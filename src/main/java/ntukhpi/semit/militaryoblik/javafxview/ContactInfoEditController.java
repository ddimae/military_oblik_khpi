package ntukhpi.semit.militaryoblik.javafxview;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ntukhpi.semit.militaryoblik.adapters.ContactInfoAdapter;
import ntukhpi.semit.militaryoblik.adapters.DocumentsAdapter;
import ntukhpi.semit.militaryoblik.adapters.ReservistAdapter;
import ntukhpi.semit.militaryoblik.javafxutils.ControlledScene;
import org.springframework.stereotype.Component;

@Component
public class ContactInfoEditController implements ControlledScene {

    @FXML
    private TextField addressFactTextArea;

    @FXML
    private TextField addressTextArea;

    @FXML
    private TextField cityFactTextArea;

    @FXML
    private TextField cityTextArea;

    @FXML
    private ComboBox<String> countryComboBox;

    @FXML
    private RadioButton equalRadioButton;

    @FXML
    private TextField indexFactTextArea;

    @FXML
    private TextField indexTextArea;

    @FXML
    private TextField mainPhoneTextArea;

    @FXML
    private Text pibText;

    @FXML
    private TextField regionFactTextArea;

    @FXML
    private TextField regionTextArea;

    @FXML
    private TextField secondPhoneTextArea;

    private ReservistsAllController mainController;
    private ReservistAdapter selectedReservist;
//    private ContactInfoAdapter selectedContactInfo;

    @Override
    public void setMainController(Object mainController) {
        this.mainController = (ReservistsAllController) mainController;
    }

    @Override
    public void setData(Object data) {
        if (data instanceof ReservistAdapter) {
            setContactInfo((ReservistAdapter) data);
        }
    }

    private void setContactInfo(ReservistAdapter reservist) {
        selectedReservist = reservist;
        //TODO брати із БД контактну інформацію про резервиста

        pibText.setText(reservist.getPib());

        countryComboBox.setValue("Україна");

        indexTextArea.setText("12345");
        indexFactTextArea.setText("54321");

        cityTextArea.setText("Київ");
        cityFactTextArea.setText("Харків");

        regionTextArea.setText("Київська обл.");
        regionFactTextArea.setText("Харківська обл.");

        addressFactTextArea.setText("вул Кирпичова, 5");

        mainPhoneTextArea.setText("+380123456789");
        secondPhoneTextArea.setText("+380453216543");
    }

    public void initialize() {
        //TODO Брати із БД всі країни
        ObservableList<String> countryList = FXCollections.observableArrayList(
                "Україна",
                "Германія",
                "Польша",
                "Грузія",
                "Молдова",
                "Франція"
        );
        countryComboBox.setItems(countryList);
    }

    @FXML
    void closeEdit(ActionEvent event) {
        try {
            ((Stage) countryComboBox.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void saveContactInfo(ActionEvent event) {
        //TODO валідація введених даних
    }
}
