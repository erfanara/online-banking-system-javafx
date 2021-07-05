package OBSApp.client.controllers;

import OBSApp.client.formViews.Message;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PayBillController implements Initializable {

    @FXML
    AnchorPane screen = new AnchorPane();

    @FXML
    JFXTextField account_id = new JFXTextField();

    @FXML
    JFXRadioButton choose_power = new JFXRadioButton();

    @FXML
    JFXRadioButton choose_water = new JFXRadioButton();

    @FXML
    JFXRadioButton choose_gas = new JFXRadioButton();

    @FXML
    JFXRadioButton choose_phone = new JFXRadioButton();

    ToggleGroup toggleGroup = new ToggleGroup();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choose_gas.setToggleGroup(toggleGroup);
        choose_power.setToggleGroup(toggleGroup);
        choose_phone.setToggleGroup(toggleGroup);
        choose_water.setToggleGroup(toggleGroup);
    }

    @FXML
    public void ReturnToServices(ActionEvent event) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("../formViews/AccountManagement.fxml"));
        screen.getChildren().clear();
        screen.getChildren().add(load);
    }

    @FXML
    public void Pay(){
        //Here goes paybill code :)
        //TODO -> Another FXML page that gets the remaining debt and pays...
        // the trouble is how do we get the type when there is just an enum and each of the types could have diferent conditions...
        Message.ShowMessage("پرداخت قبض"); //test
    }
}