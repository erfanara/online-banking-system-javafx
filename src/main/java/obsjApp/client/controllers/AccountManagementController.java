package obsjApp.client.controllers;

import obsjApp.client.Main;
import obsjApp.client.formViews.Loading;
import obsjApp.core.Account;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class AccountManagementController implements Initializable {

    Loading loadingWindow = new Loading();

    @FXML
    AnchorPane screen = new AnchorPane();

    private JSONObject user;

    @FXML
    JFXTreeTableView<Account> accounts = new JFXTreeTableView<Account>();

    @FXML
    JFXTreeTableColumn<Account, String> id = new JFXTreeTableColumn<>("ID");

    @FXML
    TreeTableColumn<Account, String> alias = new TreeTableColumn<Account, String>("Alias");

    @FXML
    TreeTableColumn<Account, String> balance_A = new TreeTableColumn<Account, String>("Balance");

    @FXML
    TreeTableColumn<Account, String> date_created = new TreeTableColumn<Account, String>();

    public AccountManagementController() throws Exception {
    }

    public ObservableList<Account> list = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JSONArray array = null;
        try {
            array = Main.getClient().getAllAccIDs();
        } catch (Exception e) {
            e.printStackTrace();
        }

        loadingWindow.Show();

        alias.setCellValueFactory(new TreeItemPropertyValueFactory<Account, String>("alias"));
        balance_A.setCellValueFactory(new TreeItemPropertyValueFactory<Account, String>("balance"));
        id.setCellValueFactory(new TreeItemPropertyValueFactory<Account, String>("id"));

        final RecursiveTreeItem<Account> root = new RecursiveTreeItem<Account>(list, RecursiveTreeObject::getChildren);
        try {
            for (String id : array.toList().toArray(new String[0])) {

                JSONObject account = Main.getClient().getAccById(id);

                root.getChildren().add(new TreeItem<>(
                        new Account(account.getString("accPass"), account.getString("alias"),
                                BigDecimal.valueOf(Long.parseLong(account.getString("balance"))))));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        accounts.getColumns().setAll(alias, balance_A, id);
        accounts.setRoot(root);
        accounts.setShowRoot(false);

        accounts.setPrefWidth(600);
        id.setPrefWidth(accounts.getPrefWidth() / accounts.getColumns().size());
        alias.setPrefWidth(accounts.getPrefWidth() / accounts.getColumns().size());
        balance_A.setPrefWidth(accounts.getPrefWidth() / accounts.getColumns().size());

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
}
