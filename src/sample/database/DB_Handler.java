package sample.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DB_Handler extends Config{
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString  = "jdbc:mysql://" + dbHost + ":" + dbPort
                + "/" +  dbName;

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPassword);
        return dbConnection;
    }
}
