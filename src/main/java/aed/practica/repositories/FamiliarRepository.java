package aed.practica.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import aed.practica.entities.Direccion;
import aed.practica.entities.Familiar;

public class FamiliarRepository implements Repository<Familiar> {

    private Connection conn;

    public FamiliarRepository(Connection conn) { this.conn = conn;}

    @Override
    public List<Familiar> findAll() {
        try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM familiar;")){
            var rs = ps.executeQuery();
            ArrayList<Familiar> familiares = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                int idAlumno = rs.getInt("idAlumno");
                String nombre = rs.getString("nombre");
                String sexo = rs.getString("sexo");
                int telefono = rs.getInt("telefono");
                boolean custodia = rs.getBoolean("custodia");
                familiares.add(new Familiar(id, idAlumno, nombre, sexo,telefono,custodia));
            }
            rs.close();
            System.out.println("[SUCCESS] Familiares leídos correctamente.");
            return familiares;
        } catch (SQLException e) {
            System.err.println("[ERROR] Ha ocurrido un error leyendo los valores: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Familiar findOneById(int id) {
        try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM familiar WHERE id=?;")){
            //preparamos consulta
            ps.setInt(1, id);            
            var rs = ps.executeQuery();
            if(!rs.next()) throw new SQLException(" No existe familiar con ese id.");
            
            int idAlumno = rs.getInt("idAlumno"); 
            String nombre = rs.getString("nombre");
            String sexo = rs.getString("sexo");
            int telefono = rs.getInt("telefono");
            boolean custodia = rs.getBoolean("custodia");
            System.out.println("[SUCCESS] Familiar encontrado con éxito.");
            return new Familiar(id, idAlumno, nombre, sexo,telefono,custodia);
        } catch(SQLException e){
            System.err.println("[ERROR] Hubo errores ejecutando el select: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Familiar save(Familiar t) {

        //siempre cierra el flujo funcione o no

        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO familiar(id,idAlumno, nombre, sexo, telefono, custodia) VALUES(?,?,?,?,?,?);")){
                //osea, existen direcciones
            ps.setInt(1,t.getId());
            ps.setInt(2, t.getIdAlumno());
            ps.setString(3, t.getNombre());
            ps.setString(4, t.getSexo());
            ps.setInt(5,t.getTelefono());
            ps.setBoolean(6,t.isCustodia());
            var res = ps.executeUpdate();
            
            if(res!=1) throw new SQLException("Familiar " + t.getNombre() + " no insertado.");
            System.out.println("[SUCCESS] Familiar " + t.getNombre() + " introducido con éxito.");
            return t;
        } catch (SQLException e) {
            System.err.println("[ERROR] " + e.getMessage());
            return null;
        }
    }

    @Override
    public void updateById(int id, Familiar t) {
        try(PreparedStatement s = conn.prepareStatement("UPDATE familiar SET idAlumno=?,nombre=?,sexo=?,telefono=?,custodia=? WHERE id=?")) {
            
            s.setInt(1, t.getIdAlumno());
            s.setString(2, t.getNombre());
            s.setString(3, t.getSexo());
            s.setInt(4, id);
            s.setInt(4,t.getTelefono());
            s.setBoolean(5,t.isCustodia());
            s.setInt(6,t.getId());
            var columnasModificadas = s.executeUpdate();
            if(columnasModificadas<1) throw new SQLException(" el familiar no existe.");
            System.out.println("[SUCCESS] Familiar con id" + t.getId() + " ha sido actualizado con éxito.");
        } catch (SQLException e) {
            System.err.println("[ERROR] Hubo un error al actualizar: " + e.getMessage());
        }   
    }


    @Override
    public void deleteById(int id) {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM familiar WHERE id=?;")) {
            ps.setInt(1, id);
            var columnasModificadas = ps.executeUpdate();
            if(columnasModificadas<1) throw new SQLException("Familiar con id " + id + " no existe");
            System.out.println("[SUCCESS] Familiar con id " + id + " eliminado exitosamente.");
        } catch (Exception e) {
            System.err.println("[ERROR] Hubo un error eliminando familiar: " + e.getMessage());
        }
    }
    
    public List<Familiar> findCustodia(int idAlumno){

        try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM familiar WHERE idAlumno=?;")){
            ArrayList<Familiar> familiares = new ArrayList<>();
            ps.setInt(1, idAlumno);
            var rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String sexo = rs.getString("sexo");
                int telefono = rs.getInt("telefono");
                boolean custodia = rs.getBoolean("custodia");
                familiares.add(new Familiar(id, idAlumno, nombre, sexo,telefono,custodia));
            }
            System.out.println("[SUCCESS] Encontrados " + familiares.size() + " familiares del alumno con id " + idAlumno);
            return familiares;
        } catch(SQLException e){
            System.err.println("[ERROR] Hubo un error consultando familiares: " + e.getMessage());
            return null;
        }
    }


}
