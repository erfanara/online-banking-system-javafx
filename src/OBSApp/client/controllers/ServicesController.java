package OBSApp.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ServicesController implements Initializable {

    @FXML
    AnchorPane screen = new AnchorPane();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void goToPayBill(javafx.event.ActionEvent event) throws IOException {
        Pane load = FXMLLoader.load(getClass().getResource("../formViews/PayBill.fxml"));
        screen.getChildren().clear();
        screen.getChildren().add(load);
    }

    public void goToLoan(ActionEvent event) throws IOException {
        Pane load = FXMLLoader.load(getClass().getResource("../formViews/Loan.fxml"));
        screen.getChildren().clear();
        screen.getChildren().add(load);
    }

    public void goToTransfer(ActionEvent event) throws IOException {
        Pane load = FXMLLoader.load(getClass().getResource("../formViews/Transfer.fxml"));
        screen.getChildren().clear();
        screen.getChildren().add(load);
    }
}
