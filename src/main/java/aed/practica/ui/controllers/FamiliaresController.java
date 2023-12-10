package aed.practica.ui.controllers;

import aed.practica.connection.SQLiteConnection;
import aed.practica.entities.Familiar;
import aed.practica.repositories.FamiliarRepository;
import aed.practica.utils.Generator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    private TextField idField, idAlumnoField, nombreField, sexoField, telefonoField;

    @FXML
    private CheckBox custodiaField;

    @FXML
    private TableView tablaView;


    public FamiliaresController(){
        try {
            FXMLLoader f = new FXMLLoader(getClass().getResource("/fxml/FamiliaresView.fxml"));
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
        if(!idField.getText().isBlank()) showById();
        else if(!idAlumnoField.getText().isBlank()) showByAlumnoId();
        else showAll();
    }

    @FXML
    private void insert(){
        if(!nombreField.getText().isBlank() && !idAlumnoField.getText().isBlank() &&!sexoField.getText().isBlank()){
            var idAlumno = Integer.parseInt(idAlumnoField.getText());
            var nombre = nombreField.getText();
            var sexo = sexoField.getText();
            var telefono = Integer.parseInt(telefonoField.getText());
            var custodia = custodiaField.isSelected();
            repository.save(new Familiar(Generator.generarID(),idAlumno,nombre,sexo,telefono,custodia));
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
            var telefono = Integer.parseInt(telefonoField.getText());
            var custodia = custodiaField.isSelected();
            repository.updateById(id, new Familiar(id,idAlumno,nombre,sexo,telefono,custodia));
            showAll();
            clear();
        }
    }


    private void clear(){
        idField.clear();
        idAlumnoField.clear();
        nombreField.clear();
        sexoField.clear();
        telefonoField.clear();
        custodiaField.setSelected(false);
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

    private void showByAlumnoId(){
        var alumno = Integer.parseInt(idAlumnoField.getText());
        datos = repository.findCustodia(alumno);
        showFamiliares(datos);
    }

    private void showFamiliares(List<Familiar> datos){
        ObservableList<Familiar> familiares = FXCollections.observableList(datos);
        TableColumn<Familiar, Integer> columnaId = new TableColumn<>("ID");
        TableColumn<Familiar, Integer> columnaIdAlumno = new TableColumn<>("ID Alumno");
        TableColumn<Familiar,String> columnaNombre = new TableColumn<>("Nombre");
        TableColumn<Familiar,String> columnaSexo = new TableColumn<>("Sexo");
        TableColumn<Familiar, Integer> columnaTelefono = new TableColumn<>("Telefono");
        TableColumn<Familiar, Boolean> columnaCustodia = new TableColumn<>("Custodia");
        columnaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnaIdAlumno.setCellValueFactory(new PropertyValueFactory<>("idAlumno"));
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        columnaTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        columnaCustodia.setCellValueFactory(new PropertyValueFactory<>("custodia"));
        tablaView.getColumns().clear();
        tablaView.getColumns().addAll(columnaId,columnaIdAlumno,columnaNombre,columnaSexo,columnaTelefono,columnaCustodia);
        tablaView.setItems(familiares);
    }




    public BorderPane getView(){
        return view;
    }


}
