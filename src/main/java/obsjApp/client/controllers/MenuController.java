package obsjApp.client.controllers;

import obsjApp.client.Main;
import obsjApp.client.formViews.Loading;
import OBSApp.core.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    User user;

    Loading loadingWindow = new Loading();

    @FXML
    Pane screen = new Pane();

    @FXML
    ImageView profileImage = new ImageView();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File image = new File("OBSApp/client/database/resources/" + user.getNationalCode() + ".jpg");
        if (image.exists())
            profileImage.setImage(new Image("OBSApp/client/database/" + user.getNationalCode() + ".jpg"));
        else
            profileImage.setImage(new Image("OBSApp/client/resources/user.jpg"));
        profileImage.setFitWidth(100);
        profileImage.setFitHeight(100);
    }

    public void InitData(String id) throws Exception {
        JSONObject user = Main.getClient().getAccById(id);
    }

    @FXML
    public void SwitchToMain(ActionEvent event) throws IOException {
        loadingWindow.Show();
        Pane load = FXMLLoader.load(getClass().getResource("../formViews/Dashboard.fxml"));
        screen.getChildren().add(load);
        loadingWindow.Close();
    }

    @FXML
    public void SwitchToServices(ActionEvent event) throws IOException {
        loadingWindow.Show();
        Pane load = FXMLLoader.load(getClass().getResource("../formViews/Services.fxml"));
        screen.getChildren().add(load);
        loadingWindow.Close();
    }

    @FXML
    public void SwitchToAccountManagement(ActionEvent event) throws IOException {
        loadingWindow.Show();
        Pane load = FXMLLoader.load(getClass().getResource("../formViews/AccountManagement.fxml"));
        screen.getChildren().add(load);
        loadingWindow.Close();
    }
}
