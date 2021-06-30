package Controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import core.Account;
import core.User;
import core.Validation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Form_Views.Message;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class AddAccountController implements Initializable {

    Stage stage;
    Scene scene;
    Parent root;

    User user;

    @FXML
    JFXTextField id = new JFXTextField();

    @FXML
    JFXTextField alias = new JFXTextField();

    @FXML
    JFXPasswordField pass = new JFXPasswordField();

    @FXML
    JFXPasswordField repeat_pass = new JFXPasswordField();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void InitUser(User user) {
        this.user = user;
    }

    @FXML
    public void SubmitAccount(ActionEvent event) throws Exception {

        if (isValidAccount()) {
            File user_info = new File("data/" + user.getFirstname() + "_" + user.getLastName() + ".java");
            User current_user = null;
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(user_info));

            if (user_info.exists()) {
                current_user = (User) inputStream.readObject();
                //this code is wrong entirely but just leaving it like this until further notice
//            current_user.getAccounts().put("1", new Account(id.getText(), pass.getText(), alias.getText()));
                user_info.delete();
                user_info.createNewFile();
                ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(user_info));
                outputStream.writeObject(current_user);
            }
        }

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

        if (message.getMessage() == null) {
            message.ShowFinalMessage();
            return false;
        }
        return true;
    }

    @FXML
    public void SwitchToMain(ActionEvent event) throws IOException {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("../Form_Views/Dashboard.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void SwitchToServices(ActionEvent event) throws IOException {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("../Form_Views/Services.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void SwitchToAccountManagement(ActionEvent event) throws IOException {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("../Form_Views/AccountManagement.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
