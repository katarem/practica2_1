package aed.practica.entities;

public record Direccion(int id, int idAlumno, String direccion) {
    public Direccion(int idAlumno, String direccion){ this(0, idAlumno, direccion);}
}
