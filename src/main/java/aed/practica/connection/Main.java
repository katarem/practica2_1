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

public class Main {
    
    public static void main(String[] args) {    
        Connection c = SQLiteConnection.newInstance();
        crearTablas(c);
        insertarDatos(c);
    }

    public static void eliminaciones(Connection c){
        //añado un alumno con direcciones (los anteriores no tienen)
        AlumnosRepository rep = new AlumnosRepository(c);
        DireccionRepository drep = new DireccionRepository(c);

        var alumnoDireccionado = new Alumno("guamasa tiene varias casa", 2141532211);
        alumnoDireccionado.getDirecciones().addAll(List.of(
            new Direccion(alumnoDireccionado.getId(), "Plaza españa"),
            new Direccion(alumnoDireccionado.getId(), "Calle viana")
        ));

        //rep.save(alumnoDireccionado);
        //recuperamos de la bbdd porque no me sé el id, no tengo más que uno con este nombre tan
        //curioso
        var id = rep.findByName("guamasa tiene varias casa").get(0).getId();
        
        var direcciones = drep.findByIdAlumno(id);
        direcciones.forEach(System.out::println);

        //esto preguntaré en clase, pero entiendo que primero
        //debemos obtener referencia al alumno buscándolo en la base
        //luego encontrar sus direcciones y al final
        //alumno.direccion().forEach(direccion -> drep.deleteById(direccion.id()));
        //eliminar el alumno
        rep.deleteById(id);
    }




    public static void consultas_familiares(Connection c){
        //vamos a añadir un par de familiares, 2 padres para el alumno con id 2 
        FamiliarRepository rep = new FamiliarRepository(c);
        int idAlumno = 2;
        var fams = List.of(
            new Familiar(idAlumno, "juan", "hombre"),
            new Familiar(idAlumno, "maria dolores", "mujer")
        );
        fams.forEach(familiar -> rep.save(familiar));

        //consultamos
        List<Familiar> obtenidos = rep.findCustodia(idAlumno);
        obtenidos.forEach(System.out::println);

        //probamos a meter un par más de familiares
        var famss = List.of(
            new Familiar(1, "pedro", "hombre"),
            new Familiar(3, "pabla", "mujer"),
            new Familiar(3, "roberta", "mujer"),
            new Familiar(3, "paquita", "hombre")
        );
        famss.forEach(familiar -> rep.save(familiar));
        
        //consultamos para alumno 1, como vimos en las inserciones, debería salir sólo 1
        var obtenidosAl1 = rep.findCustodia(1);
        //también obtenemos de alumno 2, en este caso deberían salir 3
        var obtenidosAl2 = rep.findCustodia(2);

        System.out.println("---FAMILIARES ALUMNO ID 1---");
        obtenidosAl1.forEach(System.out::println);
        System.out.println("---FIN ID 1---");
        System.out.println("---FAMILIARES ALUMNO ID 3---");
        obtenidosAl2.forEach(System.out::println);
        System.out.println("---FIN ID 3---");

        //por último intentaremos obtener familiares de id 4, que no le hemos agregado ningunp
        var obtenidosAl4 = rep.findCustodia(4);
        System.out.println("---FAMILIARES ALUMNO ID 4---");
        obtenidosAl4.forEach(System.out::println);
        System.out.println("---FIN ID 4---");

    }



    public static void borrarTabla(Connection c){
        String nombreTable = "alumno";
        System.out.println(JDBCOperations.deleteTable(c, nombreTable));
    }

    public static void ej_consultas(Connection c){
        AlumnosRepository rep = new AlumnosRepository(c);
        //solo 1 se llama paco
        String nombre = "paco";
        var guardados = rep.findByName(nombre);
        guardados.forEach(System.out::println);

        //ya existe 1 jose en la bbdd, agregamos otro
        Alumno a = new Alumno("jose", 1111111111);
        rep.save(a);
        //leemos cuantos alumnos se llaman jose y nos los devuelvan
        guardados = rep.findByName("jose");
        guardados.forEach(System.out::println);

        //por último intentaremos buscar un nombre que NO existe en la bbdd
        guardados = rep.findByName("jesus");
        guardados.forEach(System.out::println);

    }




    public static void crearTablas(Connection c){
        System.out.println("[INFO] Comienzo crear tablas");
        String tablaAlumnos = 
        "alumno(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre VARCHAR(30) NOT NULL, telefono VARCHAR(9) NOT NULL);";
        String tablaDireccion = 
        "direccion(id INTEGER PRIMARY KEY AUTOINCREMENT, idAlumno INTEGER, direccion VARCHAR(30),FOREIGN KEY (idAlumno) REFERENCES alumno(id));";
        String tablaFamiliar = 
        "familiar(id INTEGER AUTOINCREMENT, idAlumno INTEGER, nombre VARCHAR(30), sexo VARCHAR(10), telefono INTEGER, custodia BOOLEAN, FOREIGN KEY (idAlumno) REFERENCES alumno(id));";
        String tablaAsignatura = 
        "asignatura(id INTEGER AUTOINCREMENT, idAlumno INTEGER, nombreAsignatura VARCHAR(20), curso VARCHAR(20), notas INTEGER, FOREIGN KEY (idAlumno) REFERENCES alumno(id));";

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
            new Alumno("señor pablo", 904835344)
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
            new Familiar(4, "maria del carmen", "mujer"),
            new Familiar(4, "maria de los dolores", "mujer"),
            new Familiar(4, "paco del monte", "hombre"),
            new Familiar(2, "federico", "hombre"),
            new Familiar(1, "tia paola", "mujer"),
            new Familiar(7, "luigi", "padre")
        );

        var asignaturas = List.of(
            new Asignatura(6, "cacería de osos", "supervivencia", 7),
            new Asignatura(1, "quimica", "2 bachillerato", 5),
            new Asignatura(1, "fisica", "2 bachillerato", 4),
            new Asignatura(1, "cocina", "2 bacihllerato", 0),
            new Asignatura(3, "tiro", "FP Superior de armas", 9),
            new Asignatura(2, "ingles", "3 de la eso", 6),
            new Asignatura(4, "quimica", "1 bachillerato", 8)
        );

        alumnos.forEach(alumno -> alurep.save(alumno));
        direcciones.forEach(direccion -> drep.save(direccion));
        familiares.forEach(familiar -> famrep.save(familiar));
        asignaturas.forEach(asignatura -> asirep.save(asignatura));
        System.out.println("[INFO] Termina la inserción de datos");
    }




}
