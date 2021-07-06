package OBSApp.client.controllers;

import OBSApp.client.Main;
import OBSApp.client.formViews.Loading;
import OBSApp.core.Account;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AccountManagementController implements Initializable {

    Loading loadingWindow = new Loading();

    @FXML
    AnchorPane screen = new AnchorPane();

    @FXML
    TableView<JSONObject> accounts = new TableView<>();

    @FXML
    TableColumn<JSONObject, String> id = new TableColumn<>();

    @FXML
    TableColumn<JSONObject, String> alias = new TableColumn<>();

    @FXML
    TableColumn<JSONObject, String> balance = new TableColumn<>();

    @FXML
    TableColumn<JSONObject, String> type = new TableColumn<>();

    public AccountManagementController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JSONArray array = null;
        try {
            array = Main.getClient().getAllAccIDs();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ObservableList data = FXCollections.observableArrayList(array);

        loadingWindow.Show();

        id.setCellFactory(new PropertyValueFactory("id"));
        alias.setCellFactory(new PropertyValueFactory("alias"));
        balance.setCellFactory(new PropertyValueFactory("balance"));
        type.setCellFactory(new PropertyValueFactory("type"));

        accounts.getColumns().setAll(id, alias, balance, type);

        accounts.setItems(data);

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

            JSONObject target = accounts.getSelectionModel().getSelectedItem();

            accounts.getItems().remove(accounts.getSelectionModel().getSelectedItem());

            FXMLLoader fxmlloader = new FXMLLoader();
            AnchorPane loader = fxmlloader.load(getClass().getResource("../formViews/AddAccount.fxml"));

            DeleteAccount controller = fxmlloader.getController();
            controller.InitData((String) target.get("id"));

            screen.getChildren().clear();
            screen.getChildren().add(loader);
        }
    }
}
