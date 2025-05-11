package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionSingleton extends ConexionBD {

    private static ConexionSingleton instancia;
    private Connection conexion;

    private ConexionSingleton() {
        try {
            String URL = "jdbc:mysql://localhost:3306/nc-biblioteca";
            String USUARIO = "root";
            String CONTRASEÑA = "";
            this.conexion = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ConexionSingleton obtenerInstancia() {
        if (instancia == null) {
            instancia = new ConexionSingleton();
        }
        return instancia;
    }

    @Override
    public Connection establecerConexion() {
        return this.conexion;
    }
}