package OBSApp.client.controllers;

import OBSApp.client.formViews.Loading;
import OBSApp.core.Transaction;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
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
    JFXTreeTableView<Transaction> transactions = new JFXTreeTableView<Transaction>();

    @FXML
    TreeTableColumn<Transaction, String> amount = new TreeTableColumn<Transaction, String>("مقدار");

    @FXML
    TreeTableColumn<Transaction, String> reason = new TreeTableColumn<Transaction, String>("علت");

    @FXML
    TreeTableColumn<Transaction, String> balance = new TreeTableColumn<Transaction, String>("موجودی باقی مانده");

    public void InitData(int userID){
        this.userID = userID;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        amount.setCellValueFactory(new TreeItemPropertyValueFactory<>("amount"));
        reason.setCellValueFactory(new TreeItemPropertyValueFactory<>("reason"));
        balance.setCellValueFactory(new TreeItemPropertyValueFactory<>("balance"));

        transactions.setPrefWidth(600);
        amount.setPrefWidth(transactions.getPrefWidth() / 3);
        reason.setPrefWidth(transactions.getPrefWidth() / 3);
        balance.setPrefWidth(transactions.getPrefWidth() / 3);
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
