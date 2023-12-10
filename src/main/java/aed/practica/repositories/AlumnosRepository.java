package aed.practica.repositories;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import aed.practica.entities.Alumno;
import aed.practica.entities.Direccion;

public class AlumnosRepository implements Repository<Alumno> {
    private Connection conn;
    private DireccionRepository direccionRepository;

    public AlumnosRepository(Connection conn){ this.conn = conn; direccionRepository = new DireccionRepository(conn);}
    @Override
    public List<Alumno> findAll() {
        try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM alumno;")){
            var rs = ps.executeQuery();
            ArrayList<Alumno> alumnos = new ArrayList<>();
            while (rs.next()) {
                int idAlumno = rs.getInt("id");
                String nombre = rs.getString("nombre");
                int telefono = Integer.parseInt(rs.getString("telefono"));

                var direcciones = direccionRepository.findByIdAlumno(idAlumno);

                alumnos.add(new Alumno(idAlumno, nombre, telefono, direcciones));
            }
            rs.close();
            System.out.println("[SUCCESS] Alumnos leídos correctamente.");
            return alumnos;
        } catch (SQLException e) {
            System.err.println("[ERROR] Ha ocurrido un error leyendo los valores: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Alumno findOneById(int id) {
        try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM alumno WHERE id=?;")){
            //preparamos consulta
            ps.setInt(1, id);            
            var rs = ps.executeQuery();
            if(!rs.next()) throw new SQLException(" No existe alumno con ese id.");
            
            
            int idAlumno = rs.getInt("id");
            String nombre = rs.getString("nombre");
            int telefono = Integer.parseInt(rs.getString("telefono"));

            List<Direccion> direcciones = direccionRepository.findByIdAlumno(idAlumno);
            return new Alumno(idAlumno, nombre, telefono,direcciones);
        } catch(SQLException e){
            System.err.println("[ERROR] Hubo errores ejecutando el select: " + e.getMessage());
            return null;
        }
    }

    public List<Alumno> findByName(String nombre){
        try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM alumno WHERE nombre=?")){
            ArrayList<Alumno> alumnos = new ArrayList<>();
            ps.setString(1, nombre);
            var rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String telefono = rs.getString("telefono");
                var direcciones = direccionRepository.findByIdAlumno(id);
                alumnos.add(new Alumno(id, nombre, Integer.parseInt(telefono),direcciones));
            }
            System.out.println("[SUCCESS] Encontrados " + alumnos.size() + " alumnos con el mismo nombre.");
            return alumnos;
        } catch(SQLException e){
            System.err.println("[ERROR] Hubo un error al buscar alumno/s: ");
            return null;
        }
    }

    @Override
    public Alumno save(Alumno t) {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO alumno(nombre, telefono) VALUES(?,?);")){
            if(t.getDirecciones().size()!=0)
                t.getDirecciones()
                .forEach(direccion -> direccionRepository.save(direccion));

            ps.setString(1, t.getNombre());
            ps.setString(2, String.valueOf(t.getTelefono()));
            var res = ps.executeUpdate();
            
            if(res!=1) throw new SQLException("Alumno " + t.getNombre() + " no insertado.");
            System.out.println("[SUCCESS] Alumno introducido con éxito.");
            return t;
        } catch (SQLException e) {
            System.err.println("[ERROR] " + e.getMessage());
            return null;
        }
    }

    @Override
    public void updateById(int id, Alumno t) {
        try(PreparedStatement s = conn.prepareStatement("UPDATE alumno SET nombre=?,telefono=? WHERE id=?")) {

            s.setString(1, t.getNombre());
            s.setString(2, Integer.toString(t.getTelefono()));
            s.setInt(3, id);

            var columnasModificadas = s.executeUpdate();
            s.close();
            if(columnasModificadas<1) throw new SQLException(" el alumno no existe.");
            System.out.println("[SUCCESS] Alumno con id" + t.getId() + " ha sido actualizado con éxito.");
        } catch (SQLException e) {
            System.err.println("[ERROR] Hubo un error al actualizar: ");
        }   
    }


    @Override
    public void deleteById(int id) {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM alumno WHERE id=?")) {

            //primero debemos eliminar las direcciones asociadas a ese alumno
            var alumno = findOneById(id);
            if(alumno.getDirecciones().size()>0) alumno.getDirecciones().forEach(direccion -> direccionRepository.deleteById(direccion.getId()));

            ps.setInt(1, id);
            var columnasModificadas = ps.executeUpdate();
            if(columnasModificadas<1) throw new SQLException(" Alumno con id " + id + " no existe");
            System.out.println("[SUCCESS] Alumno con id " + id + " eliminado exitosamente.");
        } catch (Exception e) {
            System.err.println("[ERROR] Hubo un error eliminando alumno: " + e.getMessage());
        }
    }
    
}
