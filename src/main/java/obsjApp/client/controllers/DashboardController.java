package obsjApp.client.controllers;

import obsjApp.client.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.JSONObject;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    JSONObject user = Main.getClient().getUserInfo();

    @FXML
    ImageView profileImage = new ImageView();

    public DashboardController() throws Exception {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            profileImage.setImage(Main.getClient().getProfilePic());
        } catch (Exception e) {
            e.printStackTrace();
        }
        profileImage.setFitWidth(100);
        profileImage.setFitHeight(100);
    }
}
