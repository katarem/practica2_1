package aed.practica.entities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Alumno{

    private int id;
    private String nombre;
    private int telefono;
    private List<Direccion> direcciones;
    private final SimpleStringProperty nombreProperty;
    private final SimpleIntegerProperty telefonoProperty, idProperty;

    public Alumno(int id, String nombre, int telefono, List<Direccion> direcciones){
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direcciones = direcciones;

        nombreProperty = new SimpleStringProperty(nombre);
        telefonoProperty = new SimpleIntegerProperty(telefono);
        idProperty = new SimpleIntegerProperty(id);
    }
    public Alumno(int id, String nombre, int telefono){ this(id, nombre, telefono, List.of());}

    public Alumno(String nombre, int telefono){ this(0, nombre, telefono, List.of()); }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public List<Direccion> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(List<Direccion> direcciones) {
        this.direcciones = direcciones;
    }


    public SimpleStringProperty nombreProperty() {
        return nombreProperty;
    }

    public SimpleIntegerProperty telefonoProperty() {
        return telefonoProperty;
    }

    public SimpleIntegerProperty idProperty() {
        return idProperty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alumno alumno = (Alumno) o;
        return id == alumno.id && telefono == alumno.telefono && Objects.equals(nombre, alumno.nombre) && Objects.equals(direcciones, alumno.direcciones) && Objects.equals(nombreProperty, alumno.nombreProperty) && Objects.equals(telefonoProperty, alumno.telefonoProperty) && Objects.equals(idProperty, alumno.idProperty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, telefono, direcciones, nombreProperty, telefonoProperty, idProperty);
    }

    @Override
    public String toString() {
        return "Alumno{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", telefono=" + telefono +
                ", direcciones=" + direcciones +
                '}';
    }
}
