/*
package ntukhpi.semit.militaryoblik.javafxview;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import ntukhpi.semit.militaryoblik.MilitaryOblikKhPIMain;
import ntukhpi.semit.militaryoblik.adapters.DocumentAdapter;
import ntukhpi.semit.militaryoblik.adapters.FamilyAdapter;
import ntukhpi.semit.militaryoblik.entity.Document;
import ntukhpi.semit.militaryoblik.entity.FamilyMember;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.javafxutils.DataFormat;
import ntukhpi.semit.militaryoblik.javafxutils.Popup;
import ntukhpi.semit.militaryoblik.service.FamilyMemberServiceImpl;
import ntukhpi.semit.militaryoblik.service.PrepodServiceImpl;
import org.hibernate.type.descriptor.jdbc.JdbcTypeFamilyInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FamilyCompositionAllController {
    private final static String FAMILY_ADD_JAVAFX = "/javafxview/FamilyCompositionEdit.fxml";
    private final static String FAMILY_EDIT_JAVAFX = FAMILY_ADD_JAVAFX;
    private final static String FAMILY_ADD_JAVAFX_TITLE = "Додати нового члена родини";
    private final static String FAMILY_EDIT_JAVAFX_TITLE = "Редагувати склад родини";
    @FXML
    private Label pibLabel;
    @FXML
    public Button editFamilyStateButton;
    @FXML
    public TableView<FamilyAdapter> familyTableView;
    @FXML
    public TableColumn<FamilyAdapter, String> vidRidstvaColumn;
    @FXML
    public TableColumn<FamilyAdapter, String> pibColumn;
    @FXML
    public TableColumn<FamilyAdapter, String> yearColumn;

    private ObservableList<FamilyAdapter> familyObservableList;

    private Prepod selectedPrepod;

    @Autowired
    PrepodServiceImpl prepodService;
    @Autowired
    FamilyMemberServiceImpl familyMemberService;

    private ObservableList<FamilyAdapter> getFamilyObservableList() {
        return FXCollections.observableArrayList(familyMemberService.getAllFamilyMembersByPrepod(selectedPrepod).stream().map(FamilyAdapter::new).toList());
    }

    public void initialize() {
        selectedPrepod = prepodService.getPrepodById(ReservistsAllController.getSelectedPrepodId());

        pibLabel.setText(DataFormat.getPIB(selectedPrepod));
        familyTableView.setPlaceholder(new Label("Ця людина поки не має жодного члена родини"));

        vidRidstvaColumn.setCellValueFactory(new PropertyValueFactory<>("vidRidstva"));
        pibColumn.setCellValueFactory(cellData -> {
            FamilyAdapter familyAdapter = cellData.getValue();
            String fullPib = familyAdapter.getFullPib();
            return new SimpleStringProperty(fullPib);
        });
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("rikNarodz"));

        familyObservableList = getFamilyObservableList();

        familyTableView.setItems(familyObservableList);
    }

    public void refreshFamilyTable() {
        familyTableView.refresh();
    }

    public void addNewFamilyMember(FamilyMember family) {
        familyMemberService.createFamilyMember(family);

        familyObservableList.add(new FamilyAdapter(family));
        refreshFamilyTable();
    }

    public void updateFamily(FamilyAdapter oldSklad, FamilyMember newSklad) {
        familyMemberService.updateFamilyMember(oldSklad.getId(), newSklad);

        familyObservableList.remove(oldSklad);
        familyObservableList.add(new FamilyAdapter(newSklad));
        refreshFamilyTable();
    }

    @FXML
    void deleteSelectedRow(ActionEvent event) {
        FamilyAdapter selectedItem = familyTableView.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            if (Popup.deleteConfirmation()) {
                familyObservableList.remove(selectedItem);
                familyMemberService.deleteFamilyMember(selectedItem.getId());
            }
        } else {
            Popup.noSelectedRowAlert();
        }
    }

    @FXML
    private void openAddWindow(ActionEvent event) {
        MilitaryOblikKhPIMain.openEditWindow(FAMILY_ADD_JAVAFX, FAMILY_ADD_JAVAFX_TITLE, this, null);
    }

    @FXML
    private void openEditWindow(ActionEvent event) {
        FamilyAdapter selectedItem = familyTableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null)
            MilitaryOblikKhPIMain.openEditWindow(FAMILY_EDIT_JAVAFX, FAMILY_EDIT_JAVAFX_TITLE, this, selectedItem);
        else
            Popup.noSelectedRowAlert();
    }

    @FXML
    void returnToMainForm(ActionEvent event) {
        MilitaryOblikKhPIMain.showReservistsWindow();
    }
}
*/
