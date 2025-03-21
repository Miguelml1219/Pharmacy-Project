package Pharmacy_Project.connection;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Clase para manejar la conexión a la base de datos MySQL.
 */
public class ConnectionDB {
    /**
     * Establece y retorna una conexión con la base de datos.
     *
     * @return un objeto {@link Connection} si la conexión es exitosa, de lo contrario retorna null.
     */
    public Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/farmacia", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
