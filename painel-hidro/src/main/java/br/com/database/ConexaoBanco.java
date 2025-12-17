package br.com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.InputStream;

public class ConexaoBanco 
{
    private static ConexaoBanco instancia;
    private Connection connection;

    private ConexaoBanco() 
    {
        try 
        {
            Properties props = new Properties();

            InputStream input = ConexaoBanco.class
                .getClassLoader()
                .getResourceAsStream("db.properties");

            if (input == null) 
            {
                throw new RuntimeException("Arquivo db.properties não encontrado no classpath");
            }

            props.load(input);

            String url  = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String pass = props.getProperty("db.password");

            connection = DriverManager.getConnection(url, user, pass);
        } 
        catch (Exception e) 
        {
            throw new RuntimeException("Erro ao conectar ao banco", e);
        }
    }

    public static synchronized ConexaoBanco getInstancia() 
    {
        if (instancia == null) 
        {
            instancia = new ConexaoBanco();
        }
        return instancia;
    }

    public Connection getConnection() 
    {
        return connection;
    }
}
