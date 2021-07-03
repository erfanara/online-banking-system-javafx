package obsjApp.client.controllers;

import obsjApp.core.Transaction;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class TranactionsViewController implements Initializable {

    @FXML
    JFXTreeTableView<Transaction> transactions = new JFXTreeTableView<Transaction>();

    @FXML
    TreeTableColumn<Transaction, String> amount = new TreeTableColumn<Transaction, String>("مقدار");

    @FXML
    TreeTableColumn<Transaction, String> reason = new TreeTableColumn<Transaction, String>("علت");

    @FXML
    TreeTableColumn<Transaction, String> balance = new TreeTableColumn<Transaction, String>("موجودی باقی مانده");

    @FXML
    TreeTableColumn<Transaction, String> date = new TreeTableColumn<Transaction, String>("تاریخ");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        amount.setCellValueFactory(new TreeItemPropertyValueFactory<>("amount"));
        reason.setCellValueFactory(new TreeItemPropertyValueFactory<>("reason"));
        balance.setCellValueFactory(new TreeItemPropertyValueFactory<>("balance"));

        transactions.setPrefWidth(700);
        amount.setPrefWidth(transactions.getPrefWidth() / 4);
        reason.setPrefWidth(transactions.getPrefWidth() / 4);
        balance.setPrefWidth(transactions.getPrefWidth() / 4);
    }
}
