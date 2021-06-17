package sample;

import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private JFXTextArea user_name = new JFXTextArea();

    private ArrayList<User> users = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User user_input = null;
        ObjectInputStream inputStream = null;
        try {
            inputStream = new ObjectInputStream(new FileInputStream("object.java"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                user_input = (User) inputStream.readObject();
            }
            catch (EOFException e){
                try {
                    inputStream.close();
                    break;
                } catch (IOException ev) {
                    e.printStackTrace();
                }
            }
            catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (user_input != null) {
                users.add(user_input);
            }
        }
    }

    @FXML
    public void SetOnSignUpPressed(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void SetOnTestPressed(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Test.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        TestController testController = loader.getController();
        User target = SearchByUserName(user_name.getText());
        if (target == null) {
            System.out.println("Fail!");
            System.exit(1);
        }
        stage.setScene(scene);
        stage.show();
        testController.InitializeData(target);
    }

    @FXML
    public User SearchByUserName(String user_name) {
        for (User user : users) {
            if (user.getName().equals(user_name))
                return user;
        }
        return null;
    }
}
