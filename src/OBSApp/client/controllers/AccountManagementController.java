package OBSApp.client.controllers;

import OBSApp.client.Main;
import OBSApp.client.formViews.Loading;
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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AccountManagementController implements Initializable {

    Loading loadingWindow = new Loading();

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

        loadingWindow.Show();

        id.setCellValueFactory(new MapValueFactory("id"));
        alias.setCellValueFactory(new MapValueFactory("alias"));
        balance.setCellValueFactory(new MapValueFactory("balance"));
        type.setCellValueFactory(new MapValueFactory("type"));

        accounts.getColumns().addAll(id, alias, balance, type);

//        accounts.setItems(data);
        accounts.getItems().addAll(data);

        loadingWindow.Close();
    }

    @FXML
    public void GoToAddAccount(ActionEvent event) throws IOException {
        loadingWindow.Show();

        AnchorPane loader = FXMLLoader.load(getClass().getResource("../formViews/AddAccount.fxml"));

        screen.getChildren().clear();
        screen.getChildren().add(loader);

        loadingWindow.Close();
    }

    @FXML
    public void deleteAccount(ActionEvent event) throws IOException {
        if (accounts.getSelectionModel().getSelectedItem() != null) {

            Map<String, String> target = (Map<String, String>) accounts.getSelectionModel().getSelectedItem();

            accounts.getItems().remove(accounts.getSelectionModel().getSelectedItem());

            FXMLLoader fxmlloader = new FXMLLoader();
            fxmlloader.setLocation(getClass().getResource("AddAccount.fxml"));

            AnchorPane loader = fxmlloader.load(getClass().getResource("../formViews/AddAccount.fxml"));

            DeleteAccount controller = fxmlloader.getController();
            controller.InitData((String) target.get("id"));

            screen.getChildren().clear();
            screen.getChildren().add(loader);
        }
    }
}
