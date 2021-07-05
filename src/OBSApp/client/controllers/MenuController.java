package OBSApp.client.controllers;

import OBSApp.client.Main;
import OBSApp.client.formViews.Loading;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.EventObject;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    JSONObject user;

    Loading loadingWindow = new Loading();

    @FXML
    AnchorPane root = new AnchorPane();

    @FXML
    Pane screen = new Pane();

    public void InitUser(String id) throws Exception {
        this.user = Main.getClient().getUserInfo();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void InitData(String id) throws Exception {
        JSONObject user = Main.getClient().getAccById(id);
    }

    @FXML
    public void SwitchToMain(ActionEvent event) throws IOException {
        loadingWindow.Show();
        Pane load = FXMLLoader.load(getClass().getResource("../formViews/Dashboard.fxml"));
        screen.getChildren().clear();
        screen.getChildren().add(load);
        loadingWindow.Close();
    }

    @FXML
    public void SwitchToServices(ActionEvent event) throws IOException {
        loadingWindow.Show();
        Pane load = FXMLLoader.load(getClass().getResource("../formViews/Services.fxml"));
        screen.getChildren().clear();
        screen.getChildren().add(load);
        loadingWindow.Close();
    }

    @FXML
    public void SwitchToAccountManagement(ActionEvent event) throws IOException {
        loadingWindow.Show();
        Pane load = FXMLLoader.load(getClass().getResource("../formViews/AccountManagement.fxml"));
        screen.getChildren().clear();
        screen.getChildren().add(load);
        loadingWindow.Close();
    }

    public void exit(ActionEvent event){
        Platform.exit();
    }

    public void minimize(ActionEvent event){
        ((Stage)((JFXButton)event.getSource()).getScene().getWindow()).setIconified(true);
    }
}
