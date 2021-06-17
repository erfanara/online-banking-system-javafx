package sample;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private JFXTextField first_name = new JFXTextField();

    @FXML
    private JFXTextField last_name = new JFXTextField();

    @FXML
    private JFXTextField national_code = new JFXTextField();

    @FXML
    private JFXTextField phone_number = new JFXTextField();

    @FXML
    private JFXTextField email = new JFXTextField();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public void SubmitUserInfo(ActionEvent event) throws IOException {
        ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream("object.java"));

        if (first_name.getText() == null || last_name.getText() == null || national_code.getText() == null || phone_number.getText() == null || email.getText() == null)
            return;

        User new_user = new User(first_name.getText(), last_name.getText(), national_code.getText(), phone_number.getText(), email.getText());

        stream.writeObject(new_user);
        stream.close();

        ReturnToLogin(event);
    }

    public void ReturnToLogin(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
