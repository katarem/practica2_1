package aed.practica.entities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Asignatura{
    private int idAlumno;
    private String nombreAsignatura;
    private String curso;
    private int notas;

    private SimpleIntegerProperty idAlumnoProperty;
    private SimpleStringProperty nombreAsignaturaProperty;
    private SimpleStringProperty cursoProperty;
    private SimpleIntegerProperty notasProperty;

    public Asignatura(int idAlumno, String nombreAsignatura, String curso, int notas) {
        this.idAlumno = idAlumno;
        this.nombreAsignatura = nombreAsignatura;
        this.curso = curso;
        this.notas = notas;

        this.idAlumnoProperty = new SimpleIntegerProperty(idAlumno);
        this.nombreAsignaturaProperty = new SimpleStringProperty(nombreAsignatura);
        this.cursoProperty = new SimpleStringProperty(curso);
        this.notasProperty = new SimpleIntegerProperty(notas);
    }
    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    public void setNombreAsignatura(String nombreAsignatura) {
        this.nombreAsignatura = nombreAsignatura;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public int getNotas() {
        return notas;
    }

    public void setNotas(int notas) {
        this.notas = notas;
    }

    public SimpleIntegerProperty idAlumnoProperty() {
        return idAlumnoProperty;
    }

    public SimpleStringProperty nombreAsignaturaProperty() {
        return nombreAsignaturaProperty;
    }

    public SimpleStringProperty cursoProperty() {
        return cursoProperty;
    }

   public SimpleIntegerProperty notasProperty() {
        return notasProperty;
    }
}
