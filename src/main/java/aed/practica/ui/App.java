package aed.practica.ui;

import aed.practica.ui.controllers.MainUIController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class App extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Colegio");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/school.png"))));
        var s = new Scene(new MainUIController().getView());
        stage.setScene(s);
        stage.setResizable(false);
        stage.setMinHeight(460);
        stage.setHeight(460);
        stage.setMinWidth(600);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
