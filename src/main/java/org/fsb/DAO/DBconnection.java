package org.fsb.DAO;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {

    private static Connection connexion;
    Dotenv dotenv = Dotenv.load();

    private final String DB_URL = dotenv.get("DB_URL");
    private final String USER = dotenv.get("USER");
    private final String PASS = dotenv.get("PASS");

    private DBconnection() throws SQLException{

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        }
        assert DB_URL != null;
        connexion= DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public static Connection getInstance(){
        if (connexion == null)
            try {
                new DBconnection();
            }catch(Exception e){
                System.out.println("--"+e.getMessage());
            }
        return connexion;
    }
}