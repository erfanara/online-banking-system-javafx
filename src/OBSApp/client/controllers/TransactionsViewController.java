package OBSApp.client.controllers;

import OBSApp.client.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

public class TransactionsViewController {

    String accID;

    AnchorPane screen = new AnchorPane();

    

    TableView transactions = new TableView();

    TableColumn<Map, String> amount = new TableColumn<>("Amount");

    TableColumn<Map, String> balance = new TableColumn<>("Balance");

    TableColumn<Map, String> reason = new TableColumn<>("Reason");

    TableColumn<Map, String> type = new TableColumn<>("Type");

    TableColumn<Map, String> id = new TableColumn<>("ID");

    TableColumn<Map, String> creationDate = new TableColumn<>("Date Created");

    public TransactionsViewController(String accID) {
        this.accID = accID;


        JSONArray array = null;
        try {
            JSONObject jo = Main.getClient().getAccTransactionsById(accID);
            array = (JSONArray) jo.get("transactions");
        } catch (Exception e) {
            e.printStackTrace();
        }

        ObservableList<Map<String, Object>> data
                = FXCollections.<Map<String, Object>>observableArrayList();

        for (int i = 0; i < array.length(); i++) {
            JSONObject jo = (JSONObject) array.get(i);

            data.add(jo.toMap());
        }

        

        type.setCellValueFactory(new MapValueFactory("type"));
        amount.setCellValueFactory(new MapValueFactory("amount"));
        balance.setCellValueFactory(new MapValueFactory("balance"));
        reason.setCellValueFactory(new MapValueFactory("reason"));
        id.setCellValueFactory(new MapValueFactory("peerObj"));
        creationDate.setCellValueFactory(new MapValueFactory("creationDate"));

        transactions.getItems().addAll(data);
        transactions.getColumns().addAll(type, amount, balance, reason, id, creationDate);


        transactions.setPrefWidth(700);
        transactions.setPrefHeight(700);
        Pane pane = new Pane();
        pane.getChildren().add(transactions);
        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        
    }

    public void ReturnToServices(ActionEvent event) throws IOException {
        
        AnchorPane load = FXMLLoader.load(getClass().getResource("../formViews/Services.fxml"));
        screen.getChildren().clear();
        screen.getChildren().add(load);
        
    }
}
