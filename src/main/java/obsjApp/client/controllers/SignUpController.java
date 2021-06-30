package obsjApp.client.controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import obsjApp.client.formViews.Message;
import obsjApp.core.User;
import obsjApp.core.Validation;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private final JFXTextField first_name = new JFXTextField();

    @FXML
    private final JFXTextField last_name = new JFXTextField();

    @FXML
    private final JFXTextField national_code = new JFXTextField();

    @FXML
    private final JFXTextField phone_number = new JFXTextField();

    @FXML
    private final JFXTextField email = new JFXTextField();

    @FXML
    private final JFXTextField pass = new JFXTextField();

    @FXML
    private final JFXTextField repeat_pass = new JFXTextField();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public void SubmitUserInfo(ActionEvent event) throws Exception {

        if (isValidSignUp()){
            User new_user = new User(first_name.getText(), last_name.getText(), national_code.getText(), phone_number.getText(), email.getText(), pass.getText());

            ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(new_user.getFirstname() + "_" + new_user.getLastName() + ".java"));
            stream.writeObject(new_user);
            stream.close();

            Message.ShowMessage("اطلاعات کاربر با موفقیت ثبت شد!");

            ReturnToLogin(event);
        }
    }

    public boolean isValidSignUp(){
        Message message = new Message();
        if (first_name.getText().length() < 3 || last_name.getText().length() < 3)
            message.AddStatement("نام باید بیش از 3 کاراکتر باشد!");

        if(!Validation.isValidEmail(email.getText()))
            message.AddStatement("آدرس پست الکترونیک معتبر نیست!");

        if (!Validation.isValidNationalCode(national_code.getText()))
            message.AddStatement("کد ملی معتبر نیست!");

        if (!Validation.isValidPhoneNumber(phone_number.getText()))
            message.AddStatement("شماره همراه معتبر نیست!");

        if (pass.getText() != null) {
            if (!pass.getText().equals(repeat_pass.getText()))
                message.AddStatement("رمز عبور به درستی تکرار نشده است!");

            if (pass.getText().length() < 5 || pass.getText().length() > 12)
                message.AddStatement("رمز عبور باید بین 5 الی 12 کاراکتر داشته باشد.");
        } else
            message.AddStatement("لطفا رمز عبور را وارد کنید.");

        if (message.getMessage() != null){
            message.ShowFinalMessage();
            return false;
        }
        return true;
    }

    public void ReturnToLogin(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../formViews/Login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
