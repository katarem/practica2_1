package aed.practica.connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCOperations {
    
    private JDBCOperations(){}

    public static String createTable(Connection c, String query){
        try{
            Statement s = c.createStatement();
            s.execute("CREATE TABLE " + query);
            s.close();
            return "[SUCCESS] Tabla " + query.split("\\(")[0] + " creada correctamente";
        } catch(SQLException e){
            if(e.getMessage().contains("already exists"))
                return "[ERROR] Ya existía en la base de datos";
            else
                return "[ERROR] " + e.getMessage();
        }
    }

    public static String deleteTable(Connection c, String tableName){
        try {
            Statement s = c.createStatement();
            s.execute("DROP TABLE " + tableName + ";");
            s.close();
            return "[SUCCESS] Tabla " + tableName + " ha sido eliminada correctamente";
        } catch (SQLException e) {
            return "[ERROR] Ha ocurrido un error en la eliminación de la tabla: " + e.getMessage();
        }
    }
}
