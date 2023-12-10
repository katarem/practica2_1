package aed.practica.utils;

import aed.practica.connection.JDBCOperations;
import aed.practica.entities.Alumno;
import aed.practica.entities.Asignatura;
import aed.practica.entities.Direccion;
import aed.practica.entities.Familiar;
import aed.practica.repositories.AlumnosRepository;
import aed.practica.repositories.AsignaturasRepository;
import aed.practica.repositories.DireccionRepository;
import aed.practica.repositories.FamiliarRepository;

import java.security.SecureRandom;
import java.sql.Connection;
import java.util.List;

public class Generator {

    private Generator(){}

    public static int generarID(){
        SecureRandom sr = new SecureRandom();
        return sr.nextInt(100,900);
    }

    public static void crearTablas(Connection c){
        System.out.println("[INFO] Comienzo crear tablas");
        String tablaAlumnos =
                "alumno(id INTEGER PRIMARY KEY, nombre VARCHAR(20), telefono INTEGER);";
        String tablaDireccion =
                "direccion(id INTEGER PRIMARY KEY, idAlumno INTEGER, direccion VARCHAR(30), FOREIGN KEY (idAlumno) REFERENCES alumno(id));";
        String tablaFamiliar =
                "familiar(id INTEGER, idAlumno INTEGER, nombre VARCHAR(20), sexo VARCHAR(10), telefono INTEGER, custodia BOOLEAN, FOREIGN KEY (idAlumno) REFERENCES alumno(id));";
        String tablaAsignatura =
                "asignatura(id INTEGER, idAlumno INTEGER, nombreAsignatura VARCHAR(20), curso VARCHAR(20), notas INTEGER, FOREIGN KEY (idAlumno) REFERENCES alumno(id));";

        var outputAlumnos = JDBCOperations.createTable(c, tablaAlumnos);
        var outputDireccion = JDBCOperations.createTable(c, tablaDireccion);
        var outputFamiliar = JDBCOperations.createTable(c, tablaFamiliar);
        var outputAsignatura = JDBCOperations.createTable(c, tablaAsignatura);

        System.out.println(outputAlumnos);
        System.out.println(outputDireccion);
        System.out.println(outputFamiliar);
        System.out.println(outputAsignatura);

        System.out.println("[INFO] Termina crear tablas");
    }

    public static void insertarDatos(Connection c){
        System.out.println("[INFO] Comienza inserción de datos");
        AlumnosRepository alurep = new AlumnosRepository(c);
        DireccionRepository drep = new DireccionRepository(c);
        FamiliarRepository famrep = new FamiliarRepository(c);
        AsignaturasRepository asirep = new AsignaturasRepository(c);

        var alumnos = List.of(
                new Alumno("juan", 329058322),
                new Alumno("paco", 120415234),
                new Alumno("maria", 999922211),
                new Alumno("pedro", 940853243),
                new Alumno("marta", 483573495),
                new Alumno("pocahontas", 529385322),
                new Alumno("mario", 195834532),
                new Alumno("paula", 908534543),
                new Alumno("señor pablo", 904835344),
                new Alumno("roronoa zoro", 534525312)
        );

        var direcciones = List.of(
                new Direccion(2, "avenida anaga"),
                new Direccion(2, "cuadrilatero la laguna"),
                new Direccion(4, "los cristianos"),
                new Direccion(5, "la concepcion"),
                new Direccion(5, "las fuentes"),
                new Direccion(5, "el teide")
        );

        var familiares = List.of(
                new Familiar(Generator.generarID(),4, "maria del carmen", "mujer",111111111,true),
                new Familiar(Generator.generarID(),4, "maria de los dolores", "mujer",82182551,false),
                new Familiar(Generator.generarID(),4, "paco del monte", "hombre",343957825,false),
                new Familiar(Generator.generarID(),2, "federico", "hombre",452398753,true),
                new Familiar(Generator.generarID(),1, "tia paola", "mujer",329548724,true),
                new Familiar(Generator.generarID(),7, "luigi", "padre",534308535,false)
        );

        var asignaturas = List.of(
                new Asignatura(Generator.generarID(), 6, "cacería de osos", "supervivencia", 7),
                new Asignatura(Generator.generarID(), 1, "quimica", "2 bachillerato", 5),
                new Asignatura(Generator.generarID(), 1, "fisica", "2 bachillerato", 4),
                new Asignatura(Generator.generarID(), 1, "cocina", "2 bacihllerato", 0),
                new Asignatura(Generator.generarID(), 3, "tiro", "FP Superior de armas", 9),
                new Asignatura(Generator.generarID(), 2, "ingles", "3 de la eso", 6),
                new Asignatura(Generator.generarID(), 4, "quimica", "1 bachillerato", 8)
        );

        alumnos.forEach(alumno -> alurep.save(alumno));
        direcciones.forEach(direccion -> drep.save(direccion));
        familiares.forEach(familiar -> famrep.save(familiar));
        asignaturas.forEach(asignatura -> asirep.save(asignatura));
        System.out.println("[INFO] Termina la inserción de datos");
    }

    public static void main(String[] args) {
        System.out.println(generarID());
    }


}
