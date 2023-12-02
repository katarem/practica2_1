package aed.practica.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class UIController implements Initializable {

    @FXML
    private BorderPane view;

    public UIController(){
        try {
            FXMLLoader f = new FXMLLoader(getClass().getResource("/ui.fxml"));
            f.setController(this);
            f.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initialize'");
    }

    public BorderPane getView(){
        return view;
    }


}
