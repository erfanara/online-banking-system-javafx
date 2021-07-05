package OBSApp.client.controllers;

import OBSApp.client.formViews.Message;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class TransferController implements Initializable {

    @FXML
    JFXTextField source_user = new JFXTextField();

    @FXML
    JFXTextField target_user = new JFXTextField();

    @FXML
    JFXTextField source_account = new JFXTextField();

    @FXML
    JFXTextField target_account = new JFXTextField();

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
//        slider.setMax(Double.parseDouble(account.getBalance().toString()));
        checkBox.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
            target_user.setEditable(!newValue);
            target_user.setVisible(!newValue);
        });
        slider.valueProperty().addListener(((observableValue, number, t1) -> {
            amount.setText(String.valueOf(Math.round((Double) number)));
        }));
//        amount.textProperty().bind(slider.valueProperty().asString("%.0f"));
    }

    @FXML
    public void DoTransfer(ActionEvent event) {
        if (isValidTransfer()) {
            //the transfer code
        }
    }

    public boolean isValidTransfer() {
        Message message = new Message();

        if (source_user.getText().equals(""))
            message.AddStatement("لطفا کاربر مبدأ را مشخص کنید!");

        if (!target_user.getText().equals("") && !checkBox.isSelected())
            message.AddStatement("لطفا کاربر مقصد را مشخص کنید!");
        else if (!target_user.getText().equals("") && source_user.getText().equals(target_user.getText()) && checkBox.isSelected())
            message.AddStatement("برای انتقال بین حساب های یک کاربر، چک باکس را فعال کنید!");

        if (source_account.getText().equals(""))
            message.AddStatement("لطفا حساب مبدا را مشخص کنید!");

        if (target_account.getText().equals(""))
            message.AddStatement("لطفا حساب مقصد را مشخص کنید!");
        else {
            if (source_account.getText().equals(target_account.getText()))
                message.AddStatement("حساب های مبدا و مقصد یکسان وارد شده اند!");
        }

        if (message.getMessage() != null) {
            message.ShowFinalMessage();
            return false;
        }

        return true;
    }
}
