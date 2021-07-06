package OBSApp.client.controllers;

import OBSApp.client.Main;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DeleteAccount implements Initializable {

    @FXML
    AnchorPane screen = new AnchorPane();

    String id;

    @FXML
    Text showID = new Text();

    @FXML
    JFXTextField pass = new JFXTextField();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showID.setText("شماره حساب" + id);
    }

    public void InitData(String id) {
        this.id = id;
    }

    @FXML
    public void Delete(ActionEvent event) throws Exception {
        Main.getClient().closeAcc(id, pass.getText());
        ReturnToAccountManagement(event);
    }

    public void ReturnToAccountManagement(ActionEvent event) throws IOException {
        Pane load = FXMLLoader.load(getClass().getResource("../formViews/AccountManagement.fxml"));
        screen.getChildren().clear();
        screen.getChildren().add(load);
    }
}
