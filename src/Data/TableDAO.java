package Data;
// https://www.w3schools.com/java/java_lambda.asp
// https://www.w3schools.com/java/ref_keyword_instanceof.asp

import DataBase.ConexionSingleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

public class TableDAO {
    protected Connection db;
    {
        db = ConexionSingleton.obtenerInstancia().establecerConexion();
    }

    // Metodo reutilizable para obtener cualquier dato de cualquier columna de la BD
    // Function
    // <ResultSet> – the type of the input to the function
    // <R> – the type of the result of the function
    protected <R> R getTableInfo(String tableName, String column, Object value, Function<ResultSet, R> result) {
        String sql = "SELECT * FROM " + tableName + " WHERE " + column + " = ?";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            // comparo de que clase es el operador que se recive
            if (value instanceof String) {
                // si es cadena
                stmt.setString(1, (String) value);
            } else if (value instanceof Integer) {
                // Si es numero
                stmt.setInt(1, (Integer) value);
            } else {
                return null;
            }
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Applies this function to the given argument.
                    // Params:t – the function argument
                    // Returns: the function result
                    // uso apply para pasarle el resultado de la query a la funcion Function<ResultSet, R> y me devuelve un resultado generico dependiendo de la columna
                    return result.apply(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}