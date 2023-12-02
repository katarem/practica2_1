package aed.practica.connection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SQLiteConnection {
    
    private SQLiteConnection(){}
    //Uso un properties que es bastante más seguro y permite configurar fuera de la aplicación
    //No vi el uso de Class.forName(driver) en la docu de sqlite.
    //De hecho es una base tan sencilla que no tiene usuario ni contraseña
    public static Connection newInstance(){
        try{
            Properties p = new Properties();
            p.load(Main.class.getResourceAsStream("/dbconfig.props"));
            String url = p.getProperty("url");
            return DriverManager.getConnection(url);

        } catch (IOException e) {
            System.err.println("NO se ha podido establecer conexión con el servidor");
            System.err.println(e.getMessage());
            return null;
        } catch(SQLException e){
            System.err.println("NO se ha podido establecer conexión con el servidor");
            System.err.println(e.getMessage());
            return null;
        }  
    }


}
