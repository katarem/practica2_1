package aed.practica.ui.controllers;

import aed.practica.connection.SQLiteConnection;
import aed.practica.entities.Direccion;
import aed.practica.repositories.DireccionRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

public class DireccionesController implements Initializable {


    private Connection conn;

    @FXML
    private BorderPane view;

    private DireccionRepository repository;

    private List<Direccion> datos;
    @FXML
    private TextField idField, idAlumnoField, direccionField;

    @FXML
    private TableView tablaView;


    public DireccionesController(){
        try {
            FXMLLoader f = new FXMLLoader(getClass().getResource("/DireccionesView.fxml"));
            f.setController(this);
            f.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        conn = SQLiteConnection.newInstance();
        repository = new DireccionRepository(conn);
        showAll();

    }

    @FXML
    private void select(){
        if(idField.getText().isBlank()) showAll();
        else showById();
    }


    @FXML
    private void insert(){
        if(!idAlumnoField.getText().isBlank() && !direccionField.getText().isBlank()){
            var idAlumno = Integer.parseInt(idAlumnoField.getText());
            var direccion = direccionField.getText();
            repository.save(new Direccion(idAlumno,direccion));
            showAll();
        }
    }

    @FXML
    private void delete(){
        if(!idField.getText().isBlank()){
            var id = Integer.parseInt(idField.getText());
            repository.deleteById(id);
            showAll();
        }
    }

    @FXML
    private void update(){
        if(!idField.getText().isBlank() && !idAlumnoField.getText().isBlank() && !direccionField.getText().isBlank()){
            var id = Integer.parseInt(idField.getText());
            var idAlumno = Integer.parseInt(idAlumnoField.getText());
            var direccion = direccionField.getText();
            repository.updateById(id, new Direccion(idAlumno,direccion));
            showAll();
            clear();
        }
    }


    private void clear(){
        idField.clear();
        idAlumnoField.clear();
        direccionField.clear();
    }


    private void showAll(){
        datos = repository.findAll();
        showDirecciones(datos);
        clear();
    }

    private void showById(){
        var id = idField.getText();
        datos = List.of(repository.findOneById(Integer.parseInt(id)));
        showDirecciones(datos);
        clear();
    }

    private void showDirecciones(List<Direccion> datos){
        ObservableList<Direccion> direcciones = FXCollections.observableList(datos);
        TableColumn<Direccion, Integer> columnaId = new TableColumn<>("ID");
        TableColumn<Direccion,Integer> columnaIdAlumno = new TableColumn<>("ID Alumno");
        TableColumn<Direccion,String> columnaDireccion = new TableColumn<>("Direccion");
        columnaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnaIdAlumno.setCellValueFactory(new PropertyValueFactory<>("idAlumno"));
        columnaDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        tablaView.getColumns().clear();
        tablaView.getColumns().addAll(columnaId,columnaIdAlumno,columnaDireccion);
        tablaView.setItems(direcciones);
    }




    public BorderPane getView(){
        return view;
    }


}
