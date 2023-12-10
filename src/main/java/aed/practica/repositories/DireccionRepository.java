package aed.practica.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import aed.practica.entities.Direccion;

public class DireccionRepository implements Repository<Direccion> {

    private Connection conn;

    public DireccionRepository(Connection conn){ this.conn = conn;}
    
    @Override
    public List<Direccion> findAll() {
        try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM direccion;")){
            var rs = ps.executeQuery();
            ArrayList<Direccion> direcciones = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String direccion = rs.getString("direccion");
                int idAlumno = rs.getInt("idAlumno");
                direcciones.add(new Direccion(id, idAlumno, direccion));
            }
            rs.close();
            System.out.println("[SUCCESS] Direcciones leídos correctamente.");
            return direcciones;
        } catch (SQLException e) {
            System.err.println("[ERROR] Ha ocurrido un error leyendo los valores: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Direccion findOneById(int id) {
        try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM direccion WHERE id=?;")){
            //preparamos consulta
            ps.setInt(1, id);            
            var rs = ps.executeQuery();
            if(!rs.next()) throw new SQLException(" No existe dirección con ese id.");
            
            //no hacemos bucle, porque sólo debería haber 1 valor, el id es PRIMARY KEY
            
            String direccion = rs.getString("direccion");
            int idAlumno = rs.getInt("idAlumno"); 

            return new Direccion(id, idAlumno, direccion);
        } catch(SQLException e){
            System.err.println("[ERROR] Hubo errores ejecutando el select: " + e.getMessage());
            return null;
        }
    }

    public List<Direccion> findByIdAlumno(int idAlumno){
        try(PreparedStatement ps = conn.prepareStatement(
            "SELECT * FROM direccion INNER JOIN alumno ON direccion.idAlumno=alumno.id WHERE direccion.idAlumno=?;")){
            ps.setInt(1, idAlumno);
            var rs = ps.executeQuery();
            ArrayList<Direccion> direcciones = new ArrayList<>(); 
            while (rs.next()) {
                int id = rs.getInt("id");
                String direccion = rs.getString("direccion");
                direcciones.add(new Direccion(id, idAlumno, direccion));
            }
            System.out.println(
                "[SUCCESS] Se han encontrado " + direcciones.size() + " direcciones con id de alumno " + idAlumno);
            return direcciones;
        }catch(SQLException e){
            System.err.println("[ERROR] Ha ocurrido un error en los select: " + e.getMessage());
            return null;
        }
    }



    @Override
    public Direccion save(Direccion t) {

        //siempre cierra el flujo funcione o no

        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO direccion(idAlumno, direccion) VALUES(?,?);")){
                //osea, existen direcciones
            ps.setInt(1, t.getIdAlumno());
            ps.setString(2, t.getDireccion());
            var res = ps.executeUpdate();
            
            if(res!=1) throw new SQLException("Direccion " + t.getDireccion() + " no insertada.");
            System.out.println("[SUCCESS] Dirección introducida con éxito.");
            return t;
        } catch (SQLException e) {
            System.err.println("[ERROR] " + e.getMessage());
            return null;
        }
    }

    @Override
    public void updateById(int id, Direccion t) {
        try(PreparedStatement s = conn.prepareStatement("UPDATE direccion SET idAlumno=?,direccion=? WHERE id=?")) {

            s.setInt(1, t.getIdAlumno());
            s.setString(2, t.getDireccion());
            s.setInt(3, id);

            var columnasModificadas = s.executeUpdate();
            s.close();
            if(columnasModificadas<1) throw new SQLException(" la dirección no existe.");
            System.out.println("[SUCCESS] Dirección con id" + t.getId() + " ha sido actualizada con éxito.");
        } catch (SQLException e) {
            System.err.println("[ERROR] Hubo un error al actualizar: ");
        }   
    }


    @Override
    public void deleteById(int id) {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM direccion WHERE id=?")) {
            ps.setInt(1, id);
            var columnasModificadas = ps.executeUpdate();
            if(columnasModificadas<1) throw new SQLException("Dirección con id " + id + " no existe");
            System.out.println("[SUCCESS] Dirección con id " + id + " eliminado exitosamente.");
        } catch (Exception e) {
            System.err.println("[ERROR] Hubo un error eliminando alumno: " + e.getMessage());
        }
    }

    public void deleteByIdAlumno(int idAlumno) {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM direccion WHERE idAlumno=?")) {
            ps.setInt(1, idAlumno);
            var columnasModificadas = ps.executeUpdate();
            if(columnasModificadas<1) throw new SQLException("Dirección con id de alumno  " + idAlumno + " no existe");
            System.out.println("[SUCCESS] Dirección con id de alumno " + idAlumno + " eliminado exitosamente.");
        } catch (Exception e) {
            System.err.println("[ERROR] Hubo un error eliminando alumno: " + e.getMessage());
        }
    }
    
}
