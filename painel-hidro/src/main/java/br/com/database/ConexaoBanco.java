package br.com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco 
{
    private static final String URL =
            "jdbc:mysql://localhost:3306/sistema_usuarios";
        private static final String USER = "root";
        private static final String PASSWORD = "devwill16zerofour";

        public static Connection conectar() throws SQLException 
        {
            return DriverManager.getConnection(URL, USER, PASSWORD);    
        }
}
