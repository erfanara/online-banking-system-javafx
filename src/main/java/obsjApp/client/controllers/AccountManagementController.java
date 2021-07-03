package obsjApp.client.controllers;

import obsjApp.client.formViews.Loading;
import obsjApp.core.Account;
import obsjApp.core.User;
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

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class AccountManagementController implements Initializable {

    Loading loadingWindow = new Loading();

    @FXML
    AnchorPane screen = new AnchorPane();

    private User user;

    @FXML
    JFXTreeTableView<Account> accounts = new JFXTreeTableView<Account>();

    @FXML
    TreeTableColumn<Account, String> alias = new TreeTableColumn<Account, String>("Alias");

    @FXML
    TreeTableColumn<Account, String> balance_A = new TreeTableColumn<Account, String>("Balance");

    @FXML
    TreeTableColumn<Account, String> date_created = new TreeTableColumn<Account, String>();

    public AccountManagementController() throws Exception {
    }

    public ObservableList<Account> list = FXCollections.observableArrayList( //example
            new Account("9991", "0012", BigDecimal.valueOf(888), user),
            new Account("9992", "0013", BigDecimal.valueOf(889), user),
            new Account("9993", "0014", BigDecimal.valueOf(890), user)
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadingWindow.Show();

        alias.setCellValueFactory(new TreeItemPropertyValueFactory<Account, String>("alias"));
        balance_A.setCellValueFactory(new TreeItemPropertyValueFactory<Account, String>("balance"));

        final RecursiveTreeItem<Account> root = new RecursiveTreeItem<Account>(list, RecursiveTreeObject::getChildren);
        try {
            root.getChildren().add(new TreeItem<>(new Account("9994", "0015", BigDecimal.valueOf(891), user))); //test
        } catch (Exception e) {
            e.printStackTrace();
        }
        accounts.getColumns().setAll(alias, balance_A);
        accounts.setRoot(root);
        accounts.setShowRoot(false);

        accounts.setPrefWidth(600);
        alias.setPrefWidth(accounts.getPrefWidth() / 2);
        balance_A.setPrefWidth(accounts.getPrefWidth() / 2);

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
