package Pharmacy_Project.connection;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Clase para gestionar la conexión a la base de datos de la farmacia.
 */
public class ConnectionDB{

    /**
     * Establece y devuelve una conexión a la base de datos.
     *
     * @return un objeto {@link Connection} si la conexión es exitosa, de lo contrario retorna {@code null}.
     */
    public Connection getConnection()
    {
        Connection connection= null;

        try {
            connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/farmacia","root","");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return connection;
    }
}
