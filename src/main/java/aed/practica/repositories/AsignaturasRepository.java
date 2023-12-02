package aed.practica.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import aed.practica.entities.Asignatura;
import aed.practica.entities.Familiar;

public class AsignaturasRepository implements Repository<Asignatura>{
    
    private Connection conn;

    public AsignaturasRepository(Connection conn){ this.conn = conn;}


    @Override
    public List<Asignatura> findAll() {
        try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM asignatura;")){
            var rs = ps.executeQuery();
            ArrayList<Asignatura> asignaturas = new ArrayList<>();
            while (rs.next()) {
                int idAlumno = rs.getInt("idAlumno");
                String nombreAsignatura = rs.getString("nombreAsignatura");
                String curso = rs.getString("curso");
                int notas = rs.getInt("notas");
                asignaturas.add(new Asignatura(idAlumno, nombreAsignatura, curso, notas));
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
            return new Asignatura(id, nombreAsignatura, curso, notas);
        } catch(SQLException e){
            System.err.println("[ERROR] Hubo errores ejecutando el select: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Asignatura save(Asignatura t) {

        //siempre cierra el flujo funcione o no

        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO asignatura(idAlumno, nombreAsignatura, curso, notas) VALUES(?,?,?,?);")){
                //osea, existen direcciones
            ps.setInt(1, t.idAlumno());
            ps.setString(2, t.nombreAsignatura());
            ps.setString(3, t.curso());
            ps.setInt(4, t.notas());
            var res = ps.executeUpdate();
            
            if(res!=1) throw new SQLException("Asignatura " + t.nombreAsignatura() + " no insertada.");
            System.out.println("[SUCCESS] Asignatura " + t.nombreAsignatura() + " introducida con éxito.");
            return t;
        } catch (SQLException e) {
            System.err.println("[ERROR] " + e.getMessage());
            return null;
        }
    }

    @Override
    public void updateById(int id, Asignatura t) {
        try(PreparedStatement s = conn.prepareStatement("UPDATE asignatura SET nombre=?,curso=? WHERE idAlumno=?")) {
            
            s.setString(1, t.nombreAsignatura());
            s.setString(2, t.curso());
            s.setInt(3, t.notas());
            s.setInt(4, t.idAlumno());
            
            var columnasModificadas = s.executeUpdate();
            if(columnasModificadas<1) throw new SQLException(" el familiar no existe.");
            System.out.println("[SUCCESS] Asignatura con nombre " + t.nombreAsignatura() + " ha sido actualizada con éxito.");
        } catch (SQLException e) {
            System.err.println("[ERROR] Hubo un error al actualizar: ");
        }   
    }


    @Override
    public void deleteById(int id) {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM asignatura WHERE idAlumno=?")) {
            ps.setInt(1, id);
            var columnasModificadas = ps.executeUpdate();
            if(columnasModificadas<1) throw new SQLException("Asignatura con id " + id + " no existe");
            System.out.println("[SUCCESS] Asignatura con id " + id + " eliminada exitosamente.");
        } catch (Exception e) {
            System.err.println("[ERROR] Hubo un error eliminando asignatura: " + e.getMessage());
        }
    }
    
}
