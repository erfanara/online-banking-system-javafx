package OBSApp.client.controllers;

import OBSApp.client.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.json.JSONObject;

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
            user_name.setText(user.get("firstname") + " " + user.get("lastname"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        profileImage.setFitWidth(200);
        profileImage.setFitHeight(200);
    }
}
