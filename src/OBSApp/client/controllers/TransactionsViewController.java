package OBSApp.client.controllers;

import OBSApp.client.formViews.Loading;
import OBSApp.core.Transaction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TranactionsViewController implements Initializable {

    int userID;

    @FXML
    AnchorPane screen = new AnchorPane();

    Loading loadingWindow = new Loading();

    @FXML
    TableView<Transaction> transactions = new TableView<>();

    @FXML
    TableColumn<Transaction, String> amount = new TableColumn<>();

    @FXML
    TableColumn<Transaction, String> balance = new TableColumn<>();

    @FXML
    TableColumn<Transaction, Transaction.Reason> reason = new TableColumn<>();

    @FXML
    TableColumn<Transaction, Transaction.Type> type = new TableColumn<>();

    public void InitData(int userID){
        this.userID = userID;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        amount.setCellFactory(new PropertyValueFactory("amount"));
        balance.setCellFactory(new PropertyValueFactory("balance"));
        reason.setCellFactory(new PropertyValueFactory("reason"));
        type.setCellFactory(new PropertyValueFactory("type"));

        transactions.getColumns().addAll(amount, balance, reason, type);
    }

    @FXML
    public void ReturnToServices(ActionEvent event) throws IOException {
        loadingWindow.Show();
        AnchorPane load = FXMLLoader.load(getClass().getResource("../formViews/Services.fxml"));
        screen.getChildren().clear();
        screen.getChildren().add(load);
        loadingWindow.Close();
    }
}
