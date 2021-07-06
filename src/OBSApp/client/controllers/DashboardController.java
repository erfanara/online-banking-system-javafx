package OBSApp.client.controllers;

import OBSApp.client.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.json.JSONObject;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    JSONObject user = Main.getClient().getUserInfo();

    @FXML
    Pane image_holder = new Pane();

    @FXML
    ImageView profileImage = new ImageView();

    @FXML
    Text user_name = new Text();

    public DashboardController() throws Exception {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            profileImage.setImage(Main.getClient().getProfilePic());
        } catch (Exception e) {
            //set a backup image
            profileImage.setImage(new Image(new File("src/OBSApp/client/resources/user.png").toURI().toString()));
        }
        image_holder.getChildren().add(profileImage);
        try {
            user_name.setText(user.get("firstName") + " " + user.get("lastName"));
        } catch (Exception ignored){
            user_name.setText("نام کاربری!");
        }
        profileImage.setFitWidth(200);
        profileImage.setFitHeight(200);
    }
}
