package OBSApp.client.controllers;

import OBSApp.client.Main;
import OBSApp.client.formViews.Loading;
import OBSApp.client.formViews.Message;
import OBSApp.core.User;
import OBSApp.core.Validation;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    Loading loadingWindow = new Loading();

    @FXML
    Text test = new Text();

    @FXML
    JFXTextField first_name = new JFXTextField();

    @FXML
    JFXTextField last_name = new JFXTextField();

    @FXML
    JFXTextField national_code = new JFXTextField();

    @FXML
    JFXTextField phone_number = new JFXTextField();

    @FXML
    JFXTextField email = new JFXTextField();

    @FXML
    JFXPasswordField pass = new JFXPasswordField();

    @FXML
    JFXPasswordField repeat_pass = new JFXPasswordField();

    @FXML
    FileChooser imageChooser = new FileChooser();

    User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public void SubmitUserInfo(ActionEvent event) throws Exception {
        test.setText(first_name.getText());
        loadingWindow.Show();
        if (isValidSignUp()) {
            Main.getClient().signupRequest(first_name.getText(), last_name.getText(), national_code.getText(),
                    phone_number.getText(), email.getText(), pass.getText());


            loadingWindow.Close();
            Message.ShowMessage("اطلاعات کاربر با موفقیت ثبت شد!");
            ReturnToLogin(event);
        }
    }

    @FXML
    public void UploadImage(ActionEvent event) throws IOException {
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");

        imageChooser = new FileChooser();
        imageChooser.getExtensionFilters().add(imageFilter);

        File imageFile = imageChooser.showOpenDialog((Stage) ((Node) event.getSource()).getScene().getWindow());
        Image profileImage = new Image(imageFile.toURI().toString());

        File output = new File("src/OBSApp/client/database/" + user.getNationalCode() + ".png");
        BufferedImage BI = SwingFXUtils.fromFXImage(profileImage, null);

        ImageIO.write(BI, "png", output);

        Message.ShowMessage("تصویر بارگذاری شد!");

    }

    public boolean isValidSignUp() {
        Message message = new Message();
        if (first_name.getText().toString().length() < 3 || last_name.getText().toString().length() < 3)
            message.AddStatement("نام باید بیش از 3 کاراکتر باشد!");

        if (!Validation.isValidEmail(email.getText()))
            message.AddStatement("آدرس پست الکترونیک معتبر نیست!");

        if (!Validation.isValidNationalCode(national_code.getText()))
            message.AddStatement("کد ملی معتبر نیست!");

        if (!Validation.isValidPhoneNumber(phone_number.getText()))
            message.AddStatement("شماره همراه معتبر نیست!");

//        if (!pass.getText().equals("")) {
//            if (!pass.getText().equals(repeat_pass.getText()))
//                message.AddStatement("رمز عبور به درستی تکرار نشده است!");
//
//        } else
//            message.AddStatement("لطفا رمز عبور را وارد کنید.");

        if (message.getMessage() != null) {
            message.ShowFinalMessage();
            loadingWindow.Close();
            return false;
        }
        return true;
    }

    public void ReturnToLogin(ActionEvent event) throws IOException {
        loadingWindow.Show();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../formViews/Login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        loadingWindow.Close();
        stage.show();
    }

    public void exit(ActionEvent event){
        Platform.exit();
    }

    public void minimize(ActionEvent event){
        ((Stage)((JFXButton)event.getSource()).getScene().getWindow()).setIconified(true);
    }
}
