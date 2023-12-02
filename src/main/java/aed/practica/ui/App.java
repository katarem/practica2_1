package aed.practica.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        
        stage.setTitle("Colegio");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/school.svg")));

        VBox v = new VBox();
        Label l = new Label("hola wayland");
        v.getChildren().add(l);
        stage.setScene(new Scene(v));
        stage.show();

    }
    public static void main(String[] args) {
        launch(args);
    }
}
