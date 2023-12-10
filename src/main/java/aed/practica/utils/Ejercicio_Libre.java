package aed.practica.utils;

import aed.practica.connection.JDBCOperations;
import aed.practica.connection.SQLiteConnection;
import aed.practica.entities.ActividadExtraescolar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Ejercicio_Libre {

    private Ejercicio_Libre(){}

    public static void ejercicio_libre(Connection c){
        System.out.println("CREAMOS TABLA");
        System.out.println(JDBCOperations.createTable(c,
"actividadExtraescolar(id INTEGER PRIMARY KEY AUTOINCREMENT, idAlumno INTEGER, nombreActividad VARCHAR(30), FOREIGN KEY (idAlumno) REFERENCES alumno(id));"));
        System.out.println("INSERTAMOS ENTIDADES");
        var entidades = List.of(
                new ActividadExtraescolar(1,"juego del palo"),
                new ActividadExtraescolar(1,"ajedrez"),
                new ActividadExtraescolar(3,"karate"),
                new ActividadExtraescolar(3,"futbol"),
                new ActividadExtraescolar(5,"cocina")
        );
        System.out.println(insertAllActividades(c,entidades));
        System.out.println("AHORA LAS MOSTRAMOS");
        var actividades = selectAllActividades(c);
        System.out.printf("%-15s %-20s %-20s","ID","IDALUMNO","NOMBRE ACTIVIDAD\n");
        actividades.forEach(actividad -> System.out.printf("%-15s %-20s %-20s\n",actividad.getId(),actividad.getIdAlumno(),actividad.getNombreActividad()));
        System.out.println("-- FIN DE LOS DATOS --");
        System.out.println("VAMOS A MOSTRAR LAS ACTIVIDADES DEL ALUMNO CON ID 1");
        actividades = selectActividadesByIdAlumno(c,1);
        actividades.forEach(actividad -> System.out.printf("%-15s %-20s %-20s\n",actividad.getId(),actividad.getIdAlumno(),actividad.getNombreActividad()));
        System.out.println("AHORA ELIMINAMOS LA TABLA");
        System.out.println(JDBCOperations.deleteTable(c,"actividadExtraescolar"));
    }

    public static String insertAllActividades(Connection c, List<ActividadExtraescolar> actividades){
        actividades.forEach(actividad -> {
            try (PreparedStatement ps = c.prepareStatement("INSERT INTO actividadExtraescolar(idAlumno, nombreActividad) VALUES(?,?);")) {
                ps.setInt(1, actividad.getIdAlumno());
                ps.setString(2, actividad.getNombreActividad());
                ps.execute();
            } catch (SQLException e) {
                System.err.println("[ERROR] " + actividad.toString() + " no agregada: " + e.getMessage());
            }
        });
        return "[SUCCESS] LAS ACTIVIDADES SE HAN AGREGADO CORRECTAMENTE";
    }

    public static List<ActividadExtraescolar> selectAllActividades(Connection c){
        try(PreparedStatement ps = c.prepareStatement("SELECT * FROM actividadExtraescolar;")){
            ArrayList<ActividadExtraescolar> actividades = new ArrayList<>();
            var rs = ps.executeQuery();
            while(rs.next()){
                var id = rs.getInt("id");
                var idAlumno = rs.getInt("idAlumno");
                var nombreActividad = rs.getString("nombreActividad");
                actividades.add(new ActividadExtraescolar(id,idAlumno,nombreActividad));
            }
            return actividades;
        } catch(SQLException e){
            System.err.println("[ERROR] Error en el select: " + e.getMessage());
            return null;
        }
    }

    public static List<ActividadExtraescolar> selectActividadesByIdAlumno(Connection c, int id){
        try(PreparedStatement ps = c.prepareStatement("SELECT * FROM actividadExtraescolar WHERE idAlumno=?;")){
            ArrayList<ActividadExtraescolar> actividades = new ArrayList<>();
            ps.setInt(1,id);
            var rs = ps.executeQuery();
            while(rs.next()){
                var idAlumno = rs.getInt("idAlumno");
                var nombreActividad = rs.getString("nombreActividad");
                actividades.add(new ActividadExtraescolar(id,idAlumno,nombreActividad));
            }
            return actividades;
        } catch(SQLException e){
            System.err.println("[ERROR] Error en el select: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        ejercicio_libre(SQLiteConnection.newInstance());
        //JDBCOperations.deleteTable(SQLiteConnection.newInstance(),"actividadExtraescolar");
    }
}
