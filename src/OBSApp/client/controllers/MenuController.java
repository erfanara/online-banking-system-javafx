package OBSApp.client.controllers;

import OBSApp.client.Main;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    JSONObject user = Main.getClient().getUserInfo();

    

    @FXML
    Text welcomeMessage = new Text();

    @FXML
    AnchorPane root = new AnchorPane();

    @FXML
    Pane screen = new Pane();

    public MenuController() throws Exception {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            welcomeMessage.setText((Main.getClient().getUserInfo().get("firstName")) + " خوش آمدی!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void SwitchToMain(ActionEvent event) throws IOException {
        
        Pane load = FXMLLoader.load(getClass().getResource("../formViews/Dashboard.fxml"));
        screen.getChildren().clear();
        screen.getChildren().add(load);
        
    }

    @FXML
    public void SwitchToServices(ActionEvent event) throws IOException {
        
        Pane load = FXMLLoader.load(getClass().getResource("../formViews/Services.fxml"));
        screen.getChildren().clear();
        screen.getChildren().add(load);
        
    }

    @FXML
    public void SwitchToAccountManagement(ActionEvent event) throws IOException {
        
        Pane load = FXMLLoader.load(getClass().getResource("../formViews/AccountManagement.fxml"));
        screen.getChildren().clear();
        screen.getChildren().add(load);
        
    }

    public void exit(ActionEvent event){
        Platform.exit();
    }

    public void minimize(ActionEvent event){
        ((Stage)((JFXButton)event.getSource()).getScene().getWindow()).setIconified(true);
    }
}
