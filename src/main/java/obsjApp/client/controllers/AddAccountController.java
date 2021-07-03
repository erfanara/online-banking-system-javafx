package obsjApp.client.controllers;

import obsjApp.client.Main;
import obsjApp.client.formViews.Loading;
import obsjApp.client.formViews.Message;
import obsjApp.core.Account;
import obsjApp.core.Validation;
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

    Loading loadingWindow = new Loading();

    @FXML
    Pane screen = new Pane();

    @FXML
    JFXTextField id = new JFXTextField();

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
        loadingWindow.Show();

        Account.Type selectedType = toggleGroup.getSelectedToggle() == savingAccountCheck?
                Account.Type.SAVING : Account.Type.CHECKING;

        if (isValidAccount())
            Main.getClient().createAcc(selectedType, alias.getText(), pass.getText());
        loadingWindow.Close();
    }

    public boolean isValidAccount() {
        Message message = new Message();

        if (!Validation.isValidAccountID(id.getText())) {
            message.AddStatement("شماره حساب معتبر نیست(16 رقم)!");
        }
        if (pass.getText() != null) {
            if (!pass.getText().equals(repeat_pass.getText()))
                message.AddStatement("رمز عبور به درستی تکرار نشده است!");

            if (pass.getText().length() < 5 || pass.getText().length() > 12)
                message.AddStatement("رمز عبور باید بین 5 الی 12 کاراکتر داشته باشد.");
        } else
            message.AddStatement("لطفا رمز عبور را وارد کنید.");

        if (!message.getMessage().equals("")) {
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
