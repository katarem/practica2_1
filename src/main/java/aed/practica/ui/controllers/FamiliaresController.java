package aed.practica.ui.controllers;

import aed.practica.connection.SQLiteConnection;
import aed.practica.entities.Alumno;
import aed.practica.entities.Familiar;
import aed.practica.repositories.FamiliarRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

public class FamiliaresController implements Initializable {


    private Connection conn;

    @FXML
    private BorderPane view;

    private FamiliarRepository repository;

    private List<Familiar> datos;
    @FXML
    private TextField idField, idAlumnoField, nombreField, sexoField;

    @FXML
    private TableView tablaView;


    public FamiliaresController(){
        try {
            FXMLLoader f = new FXMLLoader(getClass().getResource("/FamiliaresView.fxml"));
            f.setController(this);
            f.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        conn = SQLiteConnection.newInstance();
        repository = new FamiliarRepository(conn);
        showAll();

    }

    @FXML
    private void select(){
        if(idField.getText().isBlank()) showAll();
        else showById();
    }


    @FXML
    private void insert(){
        if(!nombreField.getText().isBlank() && !idAlumnoField.getText().isBlank() &&!sexoField.getText().isBlank()){
            var idAlumno = Integer.parseInt(idAlumnoField.getText());
            var nombre = nombreField.getText();
            var sexo = sexoField.getText();
            repository.save(new Familiar(idAlumno,nombre,sexo));
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
        if(!idField.getText().isBlank() && !idAlumnoField.getText().isBlank() && !nombreField.getText().isBlank() && !sexoField.getText().isBlank()){
            var id = Integer.parseInt(idField.getText());
            var idAlumno = Integer.parseInt(idAlumnoField.getText());
            var nombre = nombreField.getText();
            var sexo = sexoField.getText();
            repository.updateById(id, new Familiar(id,idAlumno,nombre,sexo));
            showAll();
            clear();
        }
    }


    private void clear(){
        idField.clear();
        idAlumnoField.clear();
        nombreField.clear();
        sexoField.clear();
    }


    private void showAll(){
        datos = repository.findAll();
        showFamiliares(datos);
        clear();
    }

    private void showById(){
        var id = idField.getText();
        datos = List.of(repository.findOneById(Integer.parseInt(id)));
        showFamiliares(datos);
        clear();
    }

    private void showFamiliares(List<Familiar> datos){
        ObservableList<Familiar> familiares = FXCollections.observableList(datos);
        TableColumn<Familiar, Integer> columnaId = new TableColumn<>("ID");
        TableColumn<Familiar, Integer> columnaIdAlumno = new TableColumn<>("Custodia");
        TableColumn<Familiar,String> columnaNombre = new TableColumn<>("Nombre");
        TableColumn<Familiar,String> columnaSexo = new TableColumn<>("Sexo");
        columnaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnaIdAlumno.setCellValueFactory(new PropertyValueFactory<>("idAlumno"));
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        tablaView.getColumns().clear();
        tablaView.getColumns().addAll(columnaId,columnaIdAlumno,columnaNombre,columnaSexo);
        tablaView.setItems(familiares);
    }




    public BorderPane getView(){
        return view;
    }


}
