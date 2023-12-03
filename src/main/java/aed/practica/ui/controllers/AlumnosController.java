package aed.practica.ui.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

import aed.practica.connection.SQLiteConnection;
import aed.practica.entities.Alumno;
import aed.practica.repositories.AlumnosRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class AlumnosController implements Initializable {


    private Connection conn;

    @FXML
    private BorderPane view;

    private AlumnosRepository repository;

    private List<Alumno> datos;
    @FXML
    private TextField idField, nombreField, telefonoField;

    @FXML
    private TableView tablaView;


    public AlumnosController(){
        try {
            FXMLLoader f = new FXMLLoader(getClass().getResource("/AlumnosView.fxml"));
            f.setController(this);
            f.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        conn = SQLiteConnection.newInstance();
        repository = new AlumnosRepository(conn);
        showAll();

    }

    @FXML
    private void select(){
        if(idField.getText().isBlank()) showAll();
        else showById();
    }


    @FXML
    private void insert(){
        if(!nombreField.getText().isBlank() && !telefonoField.getText().isBlank()){
            var nombre = nombreField.getText();
            var telefono = Integer.parseInt(telefonoField.getText());
            repository.save(new Alumno(nombre,telefono));
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
        if(!idField.getText().isBlank() && !nombreField.getText().isBlank() && !telefonoField.getText().isBlank()){
            var id = Integer.parseInt(idField.getText());
            var nombre = nombreField.getText();
            var telefono = Integer.parseInt(telefonoField.getText());
            repository.updateById(id, new Alumno(nombre,telefono));
            showAll();
            clear();
        }
    }


    private void clear(){
        idField.clear();
        nombreField.clear();
        telefonoField.clear();
    }


    private void showAll(){
        datos = repository.findAll();
        showAlumnos(datos);
        clear();
    }

    private void showById(){
        var id = idField.getText();
        datos = List.of(repository.findOneById(Integer.parseInt(id)));
        showAlumnos(datos);
        clear();
    }

    private void showAlumnos(List<Alumno> datos){
        ObservableList<Alumno> alumnos = FXCollections.observableList(datos);
        TableColumn<Alumno, Integer> columnaId = new TableColumn<>("ID");
        TableColumn<Alumno,String> columnaNombre = new TableColumn<>("Nombre");
        TableColumn<Alumno,Integer> columnaTelefono = new TableColumn<>("Telefono");
        columnaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        tablaView.getColumns().clear();
        tablaView.getColumns().addAll(columnaId,columnaNombre,columnaTelefono);
        tablaView.setItems(alumnos);
    }




    public BorderPane getView(){
        return view;
    }


}
