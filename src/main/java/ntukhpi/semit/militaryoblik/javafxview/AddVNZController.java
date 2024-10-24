package ntukhpi.semit.militaryoblik.javafxview;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;
import ntukhpi.semit.militaryoblik.adapters.EducationAdapter;
import ntukhpi.semit.militaryoblik.entity.VNZaklad;
import ntukhpi.semit.militaryoblik.javafxutils.ControlledScene;
import ntukhpi.semit.militaryoblik.javafxutils.Popup;
import ntukhpi.semit.militaryoblik.service.VNZakladService;
import ntukhpi.semit.militaryoblik.service.VNZakladServiceImpl;
import ntukhpi.semit.militaryoblik.service.entitiescrud.VNZCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddVNZController implements ControlledScene {
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField abbreviationTextField;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    private ObservableList<VNZaklad> vnzObservableList;
    private ComboBox<VNZaklad> vnzComboBox;
    private Stage mainStage;
    private Stage currentStage;

//    DDE - вот воно ж тепер тут не треба ?!...
    // А без нього не працює!
    @Autowired
//    VNZakladServiceImpl vnZakladService;
    VNZakladService vnZakladService;


    @Override
    public void setMainController(Object controller) {}

    @Override
    public void setData(Object data) {
        Object[] arrData = (Object[]) data;

        setVNZData((ComboBox<VNZaklad>)arrData[0], (ObservableList<VNZaklad>)arrData[1]);
    }

    @Override
    public void setMainStage(Stage stage) {
        mainStage = stage;
    }

    @Override
    public void setCurrentStage(Stage stage) {
        currentStage = stage;
    }

    public void initialize() {
        //
    }

    public void setVNZData(ComboBox<VNZaklad> comboBox, ObservableList<VNZaklad> vnzList) {
        this.vnzComboBox = comboBox;
        this.vnzObservableList = vnzList;
    }

    @FXML
    private void saveVNZ() {
        String name = nameTextField.getText();
        String abbreviation = abbreviationTextField.getText();

        if (!name.isEmpty() && !abbreviation.isEmpty()) {

            if (!Popup.saveConfirmation())
                return;

            //DDE
            VNZaklad newVNZ = new VNZaklad();
            newVNZ.setVnzName(name);
            newVNZ.setVnzShortName(abbreviation);
            try {
//                vnZakladService.createVNZaklad(newVNZ);
                (new VNZCRUD(vnZakladService)).addVNZ(name,abbreviation);
                //DDE Переніс сюди - бо не бачу сенсу додавати заклад у перелік,
                //якщо збереження закончиться із проблемою
                //!!! Правда користувачу про це дізнатися не вдастся....
                vnzObservableList.add(newVNZ);
                vnzComboBox.setValue(newVNZ);
            } catch (Exception e) {
                e.printStackTrace();
            }

            cancel();
        }
    }

    @FXML
    private void cancel() {
        MilitaryOblikKhPIMain.showPreviousStage(mainStage, currentStage);
    }
}
