package OBSApp.client.formViews;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Loading {

    Stage loadingStage;

    public Loading(){
        loadingStage = new Stage();
        Pane root = new Pane();
        Scene scene = new Scene(root, 275, 150);
        loadingStage.setScene(scene);

        loadingStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);

        Text text = new Text();
        text.setText("در حال بارگذاری...");
        text.setX(60);
        text.setY(85);
        text.setFont(Font.loadFont("file:src/OBSApp/client/resources/font/IRKoodak.ttf", 24));
        text.setStyle("-fx-text-alignment: Center;");

        root.getChildren().add(text);

        root.setStyle("-fx-background-radius: 25px;" +
                "-fx-border-width: 1px;" +
                "-fx-border-color: Blue;" +
                "-fx-background-color: White;" +
                "-fx-border-radius: 25px;");

        loadingStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);

    }

    public void Show(){
        loadingStage.show();
    }

    public void Close(){
        loadingStage.close();
    }
}
