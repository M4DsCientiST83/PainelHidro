package br.com.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexaoBanco 
{
    private static String url;
    private static String user;
    private static String pass;

    static 
    {
            try 
            {
                Properties props = new Properties();
                props.load(
                    ConexaoBanco.class
                        .getClassLoader()
                        .getResourceAsStream("db.properties")
                );

                url = props.getProperty("db.url");
                user = props.getProperty("db.user");
                pass = props.getProperty("db.password");
            } 
            catch (IOException e) 
            {
                throw new RuntimeException("Erro ao carregar db.properties", e);
            }
        }

    public static Connection conectar() throws SQLException 
    {
        return DriverManager.getConnection(url, user, pass);
    }
    
}
