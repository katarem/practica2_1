package aed.practica.entities;

public record Familiar(int id, int idAlumno, String nombre, String sexo) {

    public Familiar(int idAlumno, String nombre, String sexo){ this(0, idAlumno, nombre, sexo);}

}
