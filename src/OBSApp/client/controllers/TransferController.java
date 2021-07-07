package OBSApp.client.controllers;

import OBSApp.client.Main;
import OBSApp.client.formViews.Loading;
import OBSApp.client.formViews.Message;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXSlider;
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

public class TransferController implements Initializable {

    Loading loadingWindow = new Loading();

    @FXML
    AnchorPane screen = new AnchorPane();

    @FXML
    JFXTextField target_user = new JFXTextField();

    @FXML
    JFXTextField source_account = new JFXTextField();

    @FXML
    JFXTextField target_account = new JFXTextField();

    @FXML

    JFXTextField sourcePass = new JFXTextField();

    @FXML
    JFXTextField amount = new JFXTextField();

    @FXML
    JFXSlider slider = new JFXSlider();

    @FXML
    JFXCheckBox checkBox = new JFXCheckBox();

    public TransferController() throws Exception {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        slider.setMax(200000);
        checkBox.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
            target_user.setEditable(!newValue);
            target_user.setVisible(!newValue);
        });
        slider.valueProperty().addListener(((observableValue, number, t1) -> {
            amount.setText(String.valueOf(Math.round((Double) number)));
        }));
    }

    @FXML
    public void DoTransfer(ActionEvent event) throws Exception {
        if (isValidTransfer()) {
            Main.getClient().transaction(source_account.getText(), sourcePass.getText(),
                    BigDecimal.valueOf(Long.parseLong(amount.getText())), target_account.getText());
            //the transfer code
        }
    }

    public boolean isValidTransfer() {
        Message message = new Message();

        if (!target_user.getText().equals("") && !checkBox.isSelected())
            message.AddStatement("لطفا کاربر مقصد را مشخص کنید!");
        else if (!target_user.getText().equals("") && !checkBox.isSelected())
            message.AddStatement("برای انتقال بین حساب های یک کاربر، چک باکس را فعال کنید!");

        if (source_account.getText().equals(""))
            message.AddStatement("لطفا حساب مبدا را مشخص کنید!");

        if (target_account.getText().equals(""))
            message.AddStatement("لطفا حساب مقصد را مشخص کنید!");
        else {
            if (source_account.getText().equals(target_account.getText()))
                message.AddStatement("حساب های مبدا و مقصد یکسان وارد شده اند!");
        }

        if (message.isFilled()) {
            message.ShowFinalMessage();
            return false;
        }

        return true;
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
