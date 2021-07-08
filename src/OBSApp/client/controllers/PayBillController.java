package OBSApp.client.controllers;

import OBSApp.client.Main;
import OBSApp.client.formViews.GetPassword;
import OBSApp.client.formViews.Message;
import OBSApp.core.exceptions.WrongPasswordException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.AnchorPane;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class PayBillController implements Initializable {

    

    @FXML
    AnchorPane screen = new AnchorPane();

    @FXML
    TextField account_id = new TextField();

    @FXML
    TableView bills = new TableView();

    @FXML
    TableColumn<Map, String> id = new TableColumn<>();

    @FXML
    TableColumn<Map, String> paymentId = new TableColumn<>();

    @FXML
    TableColumn<Map, String> amount = new TableColumn<>();

    @FXML
    TableColumn<Map, String> type = new TableColumn<>();

    @FXML
    TableColumn<Map, String> subsidiaryCompanyId = new TableColumn<>();

    @FXML
    TableColumn<Map, String> creationDate = new TableColumn<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JSONArray array = null;
        try {
            array = Main.getClient().getCurrentBills();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ObservableList<Map<String, Object>> data
                = FXCollections.<Map<String, Object>>observableArrayList();

        for (int i = 0; i < array.length(); i++) {
            JSONObject jo = (JSONObject) array.get(i);

            data.add(jo.toMap());
        }

        

        id.setCellValueFactory(new MapValueFactory("id"));
        paymentId.setCellValueFactory(new MapValueFactory("paymentId"));
        subsidiaryCompanyId.setCellValueFactory(new MapValueFactory("subsidiaryCompanyId"));
        type.setCellValueFactory(new MapValueFactory("type"));
        amount.setCellValueFactory(new MapValueFactory("amount"));
        creationDate.setCellValueFactory(new MapValueFactory("creationDate"));

        bills.getItems().addAll(data);

        
    }

    @FXML
    public void ReturnToServices(ActionEvent event) throws IOException {
        
        AnchorPane load = FXMLLoader.load(getClass().getResource("../formViews/Services.fxml"));
        screen.getChildren().clear();
        screen.getChildren().add(load);
        
    }

    @FXML
    public void pay(ActionEvent event) throws Exception {

        if (bills.getSelectionModel().getSelectedItem() != null) {

            Map<String, String> target = (Map<String, String>) bills.getSelectionModel().getSelectedItem();

            GetPassword gp = new GetPassword();
            Optional<String> result = gp.showAndWait();
            result.ifPresent(password -> {
                try {
                    if(Main.getClient().payBill(target.get(id) + target.get(paymentId), account_id.getText(), password))
                        Message.ShowMessage("موفقیت آمیز!!");
                } catch (WrongPasswordException e) {
                    Message.ShowMessage("رمز حساب غلط می باشد!!");
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }

            });

            bills.getItems().remove(bills.getSelectionModel().getSelectedItem());
        }
    }
}
