package OBSApp.client.controllers;

import OBSApp.client.Main;
import OBSApp.client.formViews.Message;
import OBSApp.core.Account;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class AddAccountController implements Initializable {

    

    @FXML
    Pane screen = new Pane();

    @FXML
    JFXTextField alias = new JFXTextField();

    @FXML
    JFXPasswordField pass = new JFXPasswordField();

    @FXML
    JFXPasswordField repeat_pass = new JFXPasswordField();

    @FXML
    JFXRadioButton checkingAccountCheck = new JFXRadioButton();

    @FXML
    JFXRadioButton savingAccountCheck = new JFXRadioButton();

    ToggleGroup toggleGroup = new ToggleGroup();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkingAccountCheck.setToggleGroup(toggleGroup);
        savingAccountCheck.setToggleGroup(toggleGroup);
    }

    @FXML
    public void SubmitAccount(ActionEvent event) throws Exception {
        

        Account.Type selectedType = toggleGroup.getSelectedToggle() == savingAccountCheck?
                Account.Type.SAVING : Account.Type.CHECKING;

        if (isValidAccount())
            Main.getClient().createAcc(selectedType, alias.getText(), pass.getText());
        
        ReturnToManagement(event);
    }

    public boolean isValidAccount() {
        Message message = new Message();

        if (pass.getText() != null) {
            if (!pass.getText().equals(repeat_pass.getText()))
                message.AddStatement("رمز عبور به درستی تکرار نشده است!");
        } else
            message.AddStatement("لطفا رمز عبور را وارد کنید.");

        if (message.isFilled()) {
            message.ShowFinalMessage();
            return false;
        }
        return true;
    }

    @FXML
    public void ReturnToManagement(ActionEvent event) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("../formViews/AccountManagement.fxml"));
        screen.getChildren().clear();
        screen.getChildren().add(load);
    }
}
