package AccesoBBDD.util;

import AccesoBBDD.exception.LBException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private DatabaseConnection(){}
    private static Properties lb = new Properties();
    private static Connection connection = null;

    public static Properties getLB(){
        return lb;
    }

    public static Connection getConnection() throws LBException {
        try {
            lb.load(new FileReader(".\\LB.properties"));
            connection = DriverManager.getConnection(lb.getProperty("url"), lb);
            System.out.println("-------Conexión Correcta-------");
            return connection;
        }catch (SQLException e) {
            throw new LBException(1, e.getMessage());
        }catch (FileNotFoundException e){
            throw new LBException(3, e.getMessage());
        }catch (IOException e){
            throw new LBException(2, e.getMessage());
        }
    }
}
