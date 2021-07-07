package OBSApp.client.controllers;

import OBSApp.client.Main;
import OBSApp.client.formViews.Loading;
import OBSApp.client.formViews.Message;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PayBillController implements Initializable {

    Loading loadingWindow = new Loading();

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
        loadingWindow.Show();
        AnchorPane load = FXMLLoader.load(getClass().getResource("../formViews/Services.fxml"));
        screen.getChildren().clear();
        screen.getChildren().add(load);
        loadingWindow.Close();
    }

    @FXML
    public void Pay(){
        Message.ShowMessage("پرداخت قبض"); //test
    }
}
