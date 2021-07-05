package OBSApp.client.controllers;

import OBSApp.client.Main;
import OBSApp.client.formViews.Loading;
import OBSApp.client.formViews.Message;
import OBSApp.core.User;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    JSONObject user;

    Loading loadingWindow = new Loading();

    @FXML
    JFXTextField user_name = new JFXTextField();

    @FXML
    JFXPasswordField pass = new JFXPasswordField();

    static final ArrayList<User> users = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public void SetOnSignUpPressed(ActionEvent event) throws IOException {
        loadingWindow.Show();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../formViews/SignUp.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        loadingWindow.Close();
        stage.show();
    }

    @FXML
    public void LoginRequest(ActionEvent event) throws Exception {
        Main.getClient().loginRequest(user_name.getText(), pass.getText());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../formViews/Menu.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public boolean isValidLogin() {
        Message message = new Message();

        if (user_name.getText().equals("")) {
            message.AddStatement("لطفا نام کاربری خود را وارد کنید!");
            if (pass.getText().equals(""))
                message.AddStatement("لطفا رمز عبور خود را وارد کنید!");
        }
        if (message.getMessage().equals("")) {
            message.ShowFinalMessage();
            return false;
        }
        return true;
    }
}