package aed.practica.entities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Direccion{

    private int id;
    private int idAlumno;
    private String direccion;

    private final SimpleIntegerProperty idProperty;
    private final SimpleIntegerProperty idAlumnoProperty;
    private final SimpleStringProperty direccionProperty;

    public Direccion(int id, int idAlumno, String direccion) {
        this.id = id;
        this.idAlumno = idAlumno;
        this.direccion = direccion;

        idProperty = new SimpleIntegerProperty(id);
        idAlumnoProperty = new SimpleIntegerProperty(idAlumno);
        direccionProperty = new SimpleStringProperty(direccion);

    }

    public Direccion(int idAlumno, String direccion) {
        this(0,idAlumno,direccion);
    }

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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


    public SimpleIntegerProperty idProperty() {
        return idProperty;
    }


    public SimpleIntegerProperty idAlumnoProperty() {
        return idAlumnoProperty;
    }

    public SimpleStringProperty direccionProperty() {
        return direccionProperty;
    }
}
