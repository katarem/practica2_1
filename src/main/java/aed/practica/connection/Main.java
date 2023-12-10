package aed.practica.connection;

import java.sql.Connection;
import java.util.List;

import aed.practica.entities.Alumno;
import aed.practica.entities.Asignatura;
import aed.practica.entities.Direccion;
import aed.practica.entities.Familiar;
import aed.practica.repositories.AlumnosRepository;
import aed.practica.repositories.AsignaturasRepository;
import aed.practica.repositories.DireccionRepository;
import aed.practica.repositories.FamiliarRepository;
import aed.practica.utils.Generator;

public class Main {
    
    public static void main(String[] args) {
        Connection c = SQLiteConnection.newInstance();
        Generator.crearTablas(c);
        Generator.insertarDatos(c);
    }






}
