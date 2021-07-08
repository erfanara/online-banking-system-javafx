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
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.AnchorPane;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class AccountManagementController implements Initializable {

    

    @FXML
    AnchorPane screen = new AnchorPane();


    @FXML
    TableView accounts = new TableView();

    @FXML
    TableColumn<Map, String> id = new TableColumn<>();

    @FXML
    TableColumn<Map, String> alias = new TableColumn<>();

    @FXML
    TableColumn<Map, String> balance = new TableColumn<>();

    @FXML
    TableColumn<Map, String> type = new TableColumn<>();

    @FXML
    TableColumn<Map, String> creationDate = new TableColumn<>();

    public AccountManagementController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JSONArray array = null;
        try {
            array = Main.getClient().getAllAccInfo();
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
        alias.setCellValueFactory(new MapValueFactory("alias"));
        balance.setCellValueFactory(new MapValueFactory("balance"));
        type.setCellValueFactory(new MapValueFactory("type"));
        creationDate.setCellValueFactory(new MapValueFactory("creationDate"));

        accounts.getItems().addAll(data);

    }

    @FXML
    public void GoToAddAccount(ActionEvent event) throws IOException {
        

        AnchorPane loader = FXMLLoader.load(getClass().getResource("../formViews/AddAccount.fxml"));

        screen.getChildren().clear();
        screen.getChildren().add(loader);

        
    }

    @FXML
    public void deleteAccount(ActionEvent event) {
        if (accounts.getSelectionModel().getSelectedItem() != null) {

            Map<String, String> target = (Map<String, String>) accounts.getSelectionModel().getSelectedItem();

            GetPassword gp = new GetPassword();
            Optional<String> result = gp.showAndWait();
            result.ifPresent(password -> {
                try {
                    Main.getClient().closeAcc(target.get("id"), password);
                } catch (WrongPasswordException e) {
                    Message.ShowMessage("رمز حساب غلط می باشد!!");
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }

            });

            accounts.getItems().remove(accounts.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    public void transactions(ActionEvent event) throws IOException {
        if (accounts.getSelectionModel().getSelectedItem() != null) {
            Map<String, String> target = (Map<String, String>) accounts.getSelectionModel().getSelectedItem();

//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("../formViews/TransactionsView.fxml"));
//            Pane load = FXMLLoader.load(getClass().getResource("../formViews/TransactionsView.fxml"));

            new TransactionsViewController (target.get("id"));
        }
    }
}
