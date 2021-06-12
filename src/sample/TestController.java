package sample;

import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class TestController implements Initializable {

    @FXML
    private ObservableList<String> items = FXCollections.observableArrayList("Online", "Banking", "System", "Service");

    @FXML
    private Text text = new Text();

    @FXML
    private JFXListView<String> listView = new JFXListView<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView.getItems().addAll(items);
    }

    @FXML
    public void click(ActionEvent event) {
        String temp;
        temp = listView.getSelectionModel().getSelectedItem();
        text.setText(temp);
    }
}
