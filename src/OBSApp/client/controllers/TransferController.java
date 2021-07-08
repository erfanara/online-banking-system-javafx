package OBSApp.client.controllers;

import OBSApp.client.Main;
import OBSApp.client.formViews.GetPassword;
import OBSApp.client.formViews.Message;
import OBSApp.core.exceptions.InsufficientFundsException;
import OBSApp.core.exceptions.InvalidDestAccIdException;
import OBSApp.core.exceptions.InvalidSrcAccIdException;
import OBSApp.core.exceptions.WrongPasswordException;
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
import java.util.Optional;
import java.util.ResourceBundle;

public class TransferController implements Initializable {

    

    @FXML
    AnchorPane screen = new AnchorPane();

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
        slider.setMax(200000);
        slider.valueProperty().addListener(((observableValue, number, t1) -> {
            amount.setText(String.valueOf(Math.round((Double) number)));
        }));
    }

    @FXML
    public void DoTransfer(ActionEvent event) throws Exception {
        if (isValidTransfer()) {
            GetPassword gp = new GetPassword();
            Optional<String> result = gp.showAndWait();
            result.ifPresent(password -> {
                try {
                    Main.getClient().transaction(source_account.getText(), password,
                            new BigDecimal(amount.getText()), target_account.getText());
                    Message.ShowMessage("موفقیت آمیز!!");

                } catch (
                        InsufficientFundsException |
                                InvalidDestAccIdException |
                                InvalidSrcAccIdException |
                                WrongPasswordException e
                ) {
                    Message.ShowMessage(e.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }


    public boolean isValidTransfer() {
        Message message = new Message();

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
        
        AnchorPane load = FXMLLoader.load(getClass().getResource("../formViews/Services.fxml"));
        screen.getChildren().clear();
        screen.getChildren().add(load);
        
    }

}
