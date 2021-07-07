package OBSApp.client.controllers;

import OBSApp.client.formViews.Loading;
import OBSApp.core.User;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class testcontroller implements Initializable {

//    User user;
//
//    @FXML
//    FileChooser imageChooser = new FileChooser();
//
//    @FXML
//    Pane imageholder = new Pane();
//
//    ImageView imageView = new ImageView();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        File image = new File("OBSApp/client/database/resources/" + user.getNationalCode() + ".jpg");
//        if (image.exists())
//            imageView.setImage(new Image("OBSApp/client/database/" + user.getNationalCode() + ".jpg"));
//        else
//            imageView.setImage(new Image("OBSApp/client/resources/user.jpg"));
//        imageholder.setPrefWidth(100);
//        imageholder.setPrefHeight(100);
//        imageView.setFitWidth(100);
//        imageView.setFitHeight(100);
//        imageholder.getChildren().add(imageView);
    }

    public void hiya(javafx.event.ActionEvent event) {
        Loading loading = new Loading();
        loading.Show();
    }
}
