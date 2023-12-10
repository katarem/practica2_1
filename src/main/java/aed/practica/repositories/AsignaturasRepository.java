package aed.practica.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import aed.practica.entities.Asignatura;

public class AsignaturasRepository implements Repository<Asignatura>{
    
    private Connection conn;

    public AsignaturasRepository(Connection conn){ this.conn = conn;}


    @Override
    public List<Asignatura> findAll() {
        try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM asignatura;")){
            var rs = ps.executeQuery();
            ArrayList<Asignatura> asignaturas = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                int idAlumno = rs.getInt("idAlumno");
                String nombreAsignatura = rs.getString("nombreAsignatura");
                String curso = rs.getString("curso");
                int notas = rs.getInt("notas");
                asignaturas.add(new Asignatura(id, idAlumno, nombreAsignatura, curso, notas));
            }
            rs.close();
            System.out.println("[SUCCESS] Asignaturas leídas correctamente.");
            return asignaturas;
        } catch (SQLException e) {
            System.err.println("[ERROR] Ha ocurrido un error leyendo los valores: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Asignatura findOneById(int id) {
        try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM asignatura WHERE idAlumno=?;")){
            //preparamos consulta
            ps.setInt(1, id);            
            var rs = ps.executeQuery();
            if(!rs.next()) throw new SQLException(" No existe asignatura con ese id.");
            
            //no hacemos bucle, porque sólo debería haber 1 valor, el id es PRIMARY KEY
            
            String nombreAsignatura = rs.getString("nombreAsignatura");
            String curso = rs.getString("curso");
            int notas = rs.getInt("notas");
            System.out.println("[SUCCESS] Asignatura encontrada con éxito.");
            return new Asignatura(id, id, nombreAsignatura, curso, notas);
        } catch(SQLException e){
            System.err.println("[ERROR] Hubo errores ejecutando el select: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Asignatura save(Asignatura t) {

        //siempre cierra el flujo funcione o no

        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO asignatura(id, idAlumno, nombreAsignatura, curso, notas) VALUES(?,?,?,?,?);")){
                //osea, existen direcciones
            ps.setInt(1,t.getId());
            ps.setInt(2, t.getIdAlumno());
            ps.setString(3, t.getNombreAsignatura());
            ps.setString(4, t.getCurso());
            ps.setInt(5, t.getNotas());
            var res = ps.executeUpdate();
            
            if(res!=1) throw new SQLException("Asignatura " + t.getNombreAsignatura() + " no insertada.");
            System.out.println("[SUCCESS] Asignatura " + t.getNombreAsignatura() + " introducida con éxito.");
            return t;
        } catch (SQLException e) {
            System.err.println("[ERROR] " + e.getMessage());
            return null;
        }
    }

    @Override
    public void updateById(int id, Asignatura t) {
        try(PreparedStatement s = conn.prepareStatement("UPDATE asignatura SET idAlumno=?,nombreAsignatura=?,curso=?,notas=? WHERE id=?;")) {
            s.setInt(1,t.getIdAlumno());
            s.setString(2, t.getNombreAsignatura());
            s.setString(3, t.getCurso());
            s.setInt(4, t.getNotas());
            s.setInt(5, t.getId());
            
            var columnasModificadas = s.executeUpdate();
            if(columnasModificadas<1) throw new SQLException(" el familiar no existe.");
            System.out.println("[SUCCESS] Asignatura con nombre " + t.getId() + " ha sido actualizada con éxito.");
        } catch (SQLException e) {
            System.err.println("[ERROR] Hubo un error al actualizar: " + e.getMessage());
        }   
    }


    @Override
    public void deleteById(int id) {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM asignatura WHERE id=?")) {
            ps.setInt(1, id);
            var columnasModificadas = ps.executeUpdate();
            if(columnasModificadas<1) throw new SQLException("Asignaturas con idAlumno " + id + " no existen");
            System.out.println("[SUCCESS] " + columnasModificadas + " asignaturas han sido eliminadas exitosamente.");
        } catch (Exception e) {
            System.err.println("[ERROR] Hubo un error eliminando asignatura: " + e.getMessage());
        }
    }
    
}
