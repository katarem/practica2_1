package aed.practica.entities;

/**
 * @author katarem
 * @apiNote Clase generada para ejercicio libre del proyecto
 */
public class ActividadExtraescolar{
    private int id;
    private int idAlumno;
    private String nombreActividad;

    public ActividadExtraescolar(int id, int idAlumno, String nombreActividad) {
        this.id = id;
        this.idAlumno = idAlumno;
        this.nombreActividad = nombreActividad;
    }

    public ActividadExtraescolar(int idAlumno, String nombreActividad) {
        this(0,idAlumno,nombreActividad);
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

    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    @Override
    public String toString() {
        return "ActividadExtraescolar{" +
                "id=" + id +
                ", idAlumno=" + idAlumno +
                ", nombreActividad='" + nombreActividad + '\'' +
                '}';
    }
}
