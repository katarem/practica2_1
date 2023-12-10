package aed.practica.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainUIController implements Initializable {

    @FXML
    private TabPane view;
    @FXML
    private Tab tablasTab, alumnosTab, direccionesTab, familiaresTab, asignaturasTab;
    private AlumnosController alumnosView = new AlumnosController();
    private DireccionesController direccionesView = new DireccionesController();
    private FamiliaresController familiaresView = new FamiliaresController();

    private AsignaturasController asignaturasView = new AsignaturasController();
    public MainUIController(){
        try{
            FXMLLoader l = new FXMLLoader(getClass().getResource("/fxml/MainUi.fxml"));
            l.setController(this);
            l.load();
        } catch(IOException e){
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        alumnosTab.setContent(alumnosView.getView());
        direccionesTab.setContent(direccionesView.getView());
        familiaresTab.setContent(familiaresView.getView());
        asignaturasTab.setContent(asignaturasView.getView());

    }

    public TabPane getView() {
        return view;
    }
}
