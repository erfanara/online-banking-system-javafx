package sample;

import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class TestController implements Initializable {

    @FXML Text name = new Text();
    @FXML Text code = new Text();
    @FXML Text phone_number = new Text();
    @FXML Text email = new Text();

    @FXML
    JFXListView<String> listView = new JFXListView<String>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void InitializeData(User user){
        name.setText(user.getFirstname() + user.getLastName());
        code.setText(user.getNationalCode());
        phone_number.setText(user.getPhoneNumber());
        email.setText(user.getEmail());
        for (Account account: user.getAccounts()){
            listView.getItems().add(String.valueOf(account.getId()));
        }
    }

}
