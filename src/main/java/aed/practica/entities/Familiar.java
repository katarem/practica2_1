package aed.practica.entities;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

public class Familiar{

    private int id;
    private int idAlumno;
    private String nombre;
    private String sexo;
    private int telefono;
    private boolean custodia;



    private SimpleIntegerProperty idProperty;
    private SimpleIntegerProperty idAlumnoProperty;
    private SimpleStringProperty nombreProperty;
    private SimpleStringProperty sexoProperty;
    private SimpleIntegerProperty telefonoProperty;
    private SimpleBooleanProperty custodiaProperty;

    public Familiar(int id, int idAlumno, String nombre, String sexo, int telefono, boolean custodia) {
        this.id = id;
        this.idAlumno = idAlumno;
        this.nombre = nombre;
        this.sexo = sexo;
        this.telefono = telefono;
        this.custodia = custodia;

        this.idProperty = new SimpleIntegerProperty(id);
        this.idAlumnoProperty = new SimpleIntegerProperty(idAlumno);
        this.nombreProperty = new SimpleStringProperty(nombre);
        this.sexoProperty = new SimpleStringProperty(sexo);
        this.telefonoProperty = new SimpleIntegerProperty(telefono);
        this.custodiaProperty = new SimpleBooleanProperty(custodia);
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

    public void setTelefono(int telefono) { this.telefono = telefono; }

    public int getTelefono() {return telefono;}

    public boolean isCustodia() {return custodia;}

    public void setCustodia(boolean custodia) {this.custodia = custodia;}

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

    public SimpleIntegerProperty telefonoProperty() {
        return telefonoProperty;
    }

    public SimpleBooleanProperty custodiaProperty() {
        return custodiaProperty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Familiar familiar = (Familiar) o;
        return id == familiar.id && idAlumno == familiar.idAlumno && telefono == familiar.telefono && custodia == familiar.custodia && Objects.equals(nombre, familiar.nombre) && Objects.equals(sexo, familiar.sexo) && Objects.equals(idProperty, familiar.idProperty) && Objects.equals(idAlumnoProperty, familiar.idAlumnoProperty) && Objects.equals(nombreProperty, familiar.nombreProperty) && Objects.equals(sexoProperty, familiar.sexoProperty) && Objects.equals(telefonoProperty, familiar.telefonoProperty) && Objects.equals(custodiaProperty, familiar.custodiaProperty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idAlumno, nombre, sexo, telefono, custodia, idProperty, idAlumnoProperty, nombreProperty, sexoProperty, telefonoProperty, custodiaProperty);
    }

    @Override
    public String
    toString() {
        return "Familiar{" +
                "id=" + id +
                ", idAlumno=" + idAlumno +
                ", nombre='" + nombre + '\'' +
                ", sexo='" + sexo + '\'' +
                ", telefono=" + telefono +
                ", custodia=" + custodia +
                '}';
    }
}
