package aed.practica.entities;

import java.util.ArrayList;
import java.util.List;

public record Alumno(int id, String nombre, int telefono, List<Direccion> direccion){

    public Alumno(int id, String nombre, int telefono){ this(id, nombre, telefono, List.of());}

    public Alumno(String nombre, int telefono){ this(0, nombre, telefono, List.of()); }

}
