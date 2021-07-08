package OBSApp.client.controllers;

import OBSApp.client.Main;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class LoanController implements Initializable {
    @FXML
    AnchorPane screen = new AnchorPane();

    @FXML
    JFXTextField amount = new JFXTextField();

    @FXML
    JFXTextField paymentPerDay = new JFXTextField();

    @FXML
    JFXTextField account_id = new JFXTextField();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public void requestLoan() throws Exception {
        Main.getClient().loanRequest(BigDecimal.valueOf(Long.parseLong(amount.getText())), Integer.parseInt(paymentPerDay.getText()));
    }

    @FXML
    public void ReturnToServices(ActionEvent event) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("../formViews/Services.fxml"));
        screen.getChildren().clear();
        screen.getChildren().add(load);
    }

}
