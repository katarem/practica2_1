package aed.practica.ui.controllers;

import aed.practica.connection.SQLiteConnection;
import aed.practica.entities.Alumno;
import aed.practica.entities.Asignatura;
import aed.practica.repositories.AlumnosRepository;
import aed.practica.repositories.AsignaturasRepository;
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

public class AsignaturasController implements Initializable {


    private Connection conn;

    @FXML
    private BorderPane view;

    private AsignaturasRepository repository;

    private List<Asignatura> datos;
    @FXML
    private TextField idField,idAlumnoField, nombreAsignaturaField, cursoField, notasField;

    @FXML
    private TableView tablaView;


    public AsignaturasController(){
        try {
            FXMLLoader f = new FXMLLoader(getClass().getResource("/AsignaturasView.fxml"));
            f.setController(this);
            f.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        conn = SQLiteConnection.newInstance();
        repository = new AsignaturasRepository(conn);
        showAll();

    }

    @FXML
    private void select(){
        if(idField.getText().isBlank()) showAll();
        else showById();
    }


    @FXML
    private void insert(){
        if(!idAlumnoField.getText().isBlank()&& !nombreAsignaturaField.getText().isBlank() && !cursoField.getText().isBlank() && !notasField.getText().isBlank()){
            var idAlumno = Integer.parseInt(idAlumnoField.getText());
            var nombreAsignatura = nombreAsignaturaField.getText();
            var curso = cursoField.getText();
            var notas = Integer.parseInt(notasField.getText());
            repository.save(new Asignatura(idAlumno,nombreAsignatura,curso,notas));
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
        if(!idAlumnoField.getText().isBlank() && !nombreAsignaturaField.getText().isBlank() && !notasField.getText().isBlank()){
            var idAlumno = Integer.parseInt(idAlumnoField.getText());
            var nombreAsignatura = nombreAsignaturaField.getText();
            var curso = cursoField.getText();
            var notas = Integer.parseInt(notasField.getText());
            repository.updateById(idAlumno, new Asignatura(idAlumno,nombreAsignatura,curso,notas));
            showAll();
            clear();
        }
    }


    private void clear(){
        idAlumnoField.clear();
        nombreAsignaturaField.clear();
        cursoField.clear();
        notasField.clear();
    }


    private void showAll(){
        datos = repository.findAll();
        showAsignaturas(datos);
        clear();
    }

    private void showById(){
        var id = idField.getText();
        datos = List.of(repository.findOneById(Integer.parseInt(id)));
        showAsignaturas(datos);
        clear();
    }

    private void showAsignaturas(List<Asignatura> datos){
        ObservableList<Asignatura> asignaturas = FXCollections.observableList(datos);
        TableColumn<Alumno, Integer> columnaIdAlumno = new TableColumn<>("ID Alumno");
        TableColumn<Alumno,String> columnaNombre = new TableColumn<>("Nombre");
        TableColumn<Alumno,String> columnaCurso = new TableColumn<>("Curso");
        TableColumn<Alumno, Integer> columnaNotas = new TableColumn<>("Notas");
        columnaIdAlumno.setCellValueFactory(new PropertyValueFactory<>("idAlumno"));
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombreAsignatura"));
        columnaCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
        columnaNotas.setCellValueFactory(new PropertyValueFactory<>("notas"));
        tablaView.getColumns().clear();
        tablaView.getColumns().addAll(columnaIdAlumno,columnaNombre,columnaCurso,columnaNotas);
        tablaView.setItems(asignaturas);
    }




    public BorderPane getView(){
        return view;
    }


}
