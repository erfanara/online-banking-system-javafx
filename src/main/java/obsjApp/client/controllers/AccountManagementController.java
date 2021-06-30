package obsjApp.client.controllers;

import com.jfoenix.controls.JFXTreeTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;
import obsjApp.core.Account;
import obsjApp.core.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AccountManagementController implements Initializable {

    Stage stage;
    Scene scene;
    Parent root;

    private User user;

    @FXML
    JFXTreeTableView<Account> accounts = new JFXTreeTableView<Account>();

    @FXML
    TreeTableColumn<Account, String> alias_or_id = new TreeTableColumn<Account, String>();

    @FXML
    TreeTableColumn<Account, String> balance = new TreeTableColumn<Account, String>();

    @FXML
    TreeTableColumn<Account, String> date_created = new TreeTableColumn<Account, String>();

    public AccountManagementController() throws Exception {
    }

    public void InitUser(User user) {
        this.user = user;
    }

//    public ObservableList<Account> list = FXCollections.observableArrayList( //example
//            new Account("9991", "0012", "", BigDecimal.valueOf(888)),
//            new Account("9992", "0013", "", BigDecimal.valueOf(889)),
//            new Account("9993", "0014", "Favorite", BigDecimal.valueOf(890))
//    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alias_or_id.setCellValueFactory(new TreeItemPropertyValueFactory<Account, String>("ID"));
        balance.setCellValueFactory(new TreeItemPropertyValueFactory<Account, String>("Balance"));
        accounts.getColumns().add(alias_or_id);
        accounts.getColumns().add(balance);
    }

    @FXML
    public void SwitchToMain(ActionEvent event) throws IOException {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("../formViews/Dashboard.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void SwitchToServices(ActionEvent event) throws IOException {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("../formViews/Services.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void SwitchToAccountManagement(ActionEvent event) throws IOException {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("../formViews/AccountManagement.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
