package OBSApp.client.formViews;

import com.jfoenix.controls.JFXButton;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;

public class Message {

    public StringBuilder getMessage() {
        return message;
    }

    private StringBuilder message;

    public Message(){
        message = new StringBuilder();
    }

    public void AddStatement(String message){
        this.message.append(message).append("\n");
    }

    public static void ShowMessage(String message) {
        Stage messageStage = new Stage();
        final double[] xOffset = new double[1];
        final double[] yOffset = new double[1];
        Pane root;
        Scene scene;
        Text message_show = new Text();
        root = new Pane();
        scene = new Scene(root, 400, 350);
        messageStage.setScene(scene);

        messageStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);

        root.setStyle("-fx-background-radius: 25px;" +
                "-fx-border-width: 1px;" +
                "-fx-border-color: Blue;" +
                "-fx-background-color: White;" +
                "-fx-border-radius: 25px;");

        message_show.setText(message);
        message_show.setX(50);
        message_show.setY(110);
        message_show.setWrappingWidth(300);
        message_show.setFont(Font.loadFont("file:src/OBSApp/client/resources/font/IRKoodak.ttf", 20));
        message_show.setStyle("-fx-text-alignment: Center;");

        Button button = new Button();
        button.setLayoutX(160);
        button.setLayoutY(285);
        button.setPrefWidth(80);
        button.setPrefHeight(40);
        button.setStyle("-fx-background-color: blue;" +
                "-fx-font-color: white;" +
                "-fx-font-weight: Bold;");
        button.setText("تأیید");
        button.setTextFill(Color.WHITE);
        button.setFont(Font.loadFont("file:src/OBSApp/client/resources/font/IRKoodak.ttf", 20));

        JFXButton exit_button = new JFXButton();
        ImageView exit_button_image = new ImageView(new Image(new File("src/OBSApp/client/resources/exit.png").toURI().toString()));
        exit_button.setLayoutX(345);
        exit_button.setLayoutY(5);
        exit_button.setPrefWidth(35);
        exit_button.setPrefHeight(40);
        exit_button.setStyle("-fx-background-radius: 100%;");
        exit_button.setOnMouseClicked(Event -> messageStage.close());
        exit_button.setGraphic(exit_button_image);

        root.getChildren().addAll(message_show, button, exit_button);

        messageStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);

        root.setOnMousePressed(event -> { // for dragging the window
            xOffset[0] = event.getSceneX();
            yOffset[0] = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            messageStage.setX(event.getScreenX() - xOffset[0]);
            messageStage.setY(event.getScreenY() - yOffset[0]);
        });

        button.setOnMousePressed(Event -> messageStage.close());

        messageStage.show();
    }

    public void ShowFinalMessage(){
        ShowMessage(this.message.toString());;
    }
}
