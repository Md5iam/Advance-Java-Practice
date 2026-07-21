package bd.edu.seu.nookmanagementsystem.utill;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectionSingleton {
    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "nook_db";
    private static final String DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "12344321";

    private static Connection connection;
    private static connectionSingleton instance = new connectionSingleton();

    private connectionSingleton(){
        try{
            // Ensure the JDBC driver is loaded
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        }catch(ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("MySQL JDBC Driver not found");
        }catch(SQLException ex ){
            ex.printStackTrace();
            System.out.println("Problem in Database(connectionSingleton)");
        }
    }

    public static Connection getConnection(){
        return connection;
    }
}
