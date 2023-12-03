package aed.practica.entities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Familiar{

    private int id;
    private int idAlumno;
    private String nombre;
    private String sexo;

    private SimpleIntegerProperty idProperty;
    private SimpleIntegerProperty idAlumnoProperty;
    private SimpleStringProperty nombreProperty;
    private SimpleStringProperty sexoProperty;

    public Familiar(int id, int idAlumno, String nombre, String sexo) {
        this.id = id;
        this.idAlumno = idAlumno;
        this.nombre = nombre;
        this.sexo = sexo;

        this.idProperty = new SimpleIntegerProperty(id);
        this.idAlumnoProperty = new SimpleIntegerProperty(idAlumno);
        this.nombreProperty = new SimpleStringProperty(nombre);
        this.sexoProperty = new SimpleStringProperty(sexo);
    }

    public Familiar(int idAlumno, String nombre, String sexo){ this(0, idAlumno, nombre, sexo);}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public SimpleIntegerProperty idProperty() {
        return idProperty;
    }

    public SimpleIntegerProperty idAlumnoProperty() {
        return idAlumnoProperty;
    }

    public SimpleStringProperty nombreProperty() {
        return nombreProperty;
    }

    public SimpleStringProperty sexoProperty() {
        return sexoProperty;
    }
}
