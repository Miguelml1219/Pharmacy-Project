package Pharmacy_Project.dao;

import Pharmacy_Project.connection.ConnectionDB;
import Pharmacy_Project.model.Financial_Movements;
import Pharmacy_Project.model.Order_Detail;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO para gestionar los movimientos financieros en la base de datos.
 */
public class Financial_MovementsDAO {

    private ConnectionDB connectionDB = new ConnectionDB();

    /**
     * Agrega un nuevo movimiento financiero a la base de datos.
     * Verifica si hay saldo suficiente antes de agregar un gasto.
     *
     * @param financial_movements Objeto Financial_Movements a agregar.
     */
    public void add(Financial_Movements financial_movements)
    {
        Connection con = connectionDB.getConnection();

        if(financial_movements.getTipo_movimiento().equalsIgnoreCase("Egresos"))
        {
            if(!hasSufficientBalance(financial_movements.getMetodo_pago(), financial_movements.getMonto()))
            {
                JOptionPane.showMessageDialog(null, "Fondos insuficientes en " + financial_movements.getMetodo_pago() + " para completar esta transacción");
                return;
            }
        }

        String query = "INSERT INTO movimientos_financieros (tipo_movimiento, categoria, monto, fecha_hora, descripcion, metodo_pago) VALUES (?,?,?,?,?,?)";

        try
        {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,financial_movements.getTipo_movimiento());
            pst.setString(2,financial_movements.getCategoria());
            pst.setInt(3,financial_movements.getMonto());
            pst.setTimestamp(4,financial_movements.getFecha_hora());
            pst.setString(5,financial_movements.getDescripcion());
            pst.setString(6,financial_movements.getMetodo_pago());

            int result = pst.executeUpdate();

            if (result>0) {
                updateCashRegister(financial_movements.getMetodo_pago(), financial_movements.getMonto(), financial_movements.getTipo_movimiento());
                JOptionPane.showMessageDialog(null, "Agregado exitosamente");
            }else
                JOptionPane.showMessageDialog(null,"Dont added");
        }

        catch (SQLException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Dont added");

        }
    }

    /**
     * Actualiza un movimiento financiero en la base de datos.
     * Antes de actualizar, se ajusta el saldo en la caja.
     *
     * @param financial_movements Objeto Financial_Movements con los datos actualizados.
     */

    public void update(Financial_Movements financial_movements){
        Connection con = connectionDB.getConnection();

        String getPreviousQuery = "SELECT monto, metodo_pago, tipo_movimiento FROM movimientos_financieros WHERE id_movimiento = ?";

        try {
            PreparedStatement getPreviousStmt = con.prepareStatement(getPreviousQuery);
            getPreviousStmt.setInt(1, financial_movements.getId_movimiento());
            ResultSet rs = getPreviousStmt.executeQuery();

            if (rs.next()) {
                int montoAnterior = rs.getInt("monto");
                String metodoPagoAnterior = rs.getString("metodo_pago");
                String tipoMovimientoAnterior = rs.getString("tipo_movimiento");

                if(financial_movements.getTipo_movimiento().equalsIgnoreCase("Egresos"))
                {
                    int diference = financial_movements.getMonto() - montoAnterior;
                    if(diference>0 && !hasSufficientBalance(financial_movements.getMetodo_pago(), diference))
                    {
                        JOptionPane.showMessageDialog(null, "Fondos insuficientes en " + financial_movements.getMetodo_pago() + " para completar la actualización.");
                    }
                }

                // Restar el monto del método de pago anterior
                updateCashRegister(metodoPagoAnterior, -montoAnterior, tipoMovimientoAnterior);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error retrieving previous movement data");
            return;
        }

        String query = "UPDATE movimientos_financieros SET tipo_movimiento = ?, categoria = ?, monto = ?, fecha_hora = ?, descripcion = ?, metodo_pago = ? WHERE id_movimiento = ?";

        try {
            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setString(1,financial_movements.getTipo_movimiento());
            stmt.setString(2,financial_movements.getCategoria());
            stmt.setInt(3,financial_movements.getMonto());
            stmt.setTimestamp(4,financial_movements.getFecha_hora());
            stmt.setString(5,financial_movements.getDescripcion());
            stmt.setString(6,financial_movements.getMetodo_pago());
            stmt.setInt(7,financial_movements.getId_movimiento());


            int result = stmt.executeUpdate();

            if (result>0) {
                updateCashRegister(financial_movements.getMetodo_pago(), financial_movements.getMonto(), financial_movements.getTipo_movimiento());

                JOptionPane.showMessageDialog(null, "Actualizado exitosamente");
            }else
                JOptionPane.showMessageDialog(null,"Movement not found");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un movimiento financiero de la base de datos.
     * Ajusta el saldo en la caja después de la eliminación.
     *
     * @param id            ID del movimiento a eliminar.
     * @param metodo_pago   Método de pago utilizado.
     * @param monto         Monto del movimiento.
     * @param tipo_movimiento Tipo de movimiento (Income o Expense).
     */

    public void delete(int id, String metodo_pago, int monto, String tipo_movimiento)
    {
        Connection con = connectionDB.getConnection();
        String query = "DELETE FROM movimientos_financieros WHERE id_movimiento = ?";

        try
        {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id);

            int result = pst.executeUpdate();

            if (result>0) {
                updateCashRegister(metodo_pago, -monto, tipo_movimiento);
                JOptionPane.showMessageDialog(null, "Eliminado exitosamente");
            }else
                JOptionPane.showMessageDialog(null,"Dont delete");

        }
        catch (SQLException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Dont delete");

        }
    }

    /**
     * Actualiza el saldo de la caja según el método de pago y el tipo de movimiento.
     *
     * @param metodo_pago   Método de pago afectado.
     * @param monto         Monto a modificar.
     * @param tipo_movimiento Tipo de movimiento (Income o Expense).
     */

    public void updateCashRegister(String metodo_pago, int monto, String tipo_movimiento)
    {
        Connection con = connectionDB.getConnection();
        String query = "UPDATE caja SET valor = valor + ? WHERE concepto = ?";

        try{
            PreparedStatement pst = con.prepareStatement(query);

            if(tipo_movimiento.equalsIgnoreCase("Egresos")){
                monto = -monto;
            }

            pst.setInt(1, monto);
            pst.setString(2, metodo_pago);

            int result = pst.executeUpdate();

            if(result<=0){
                JOptionPane.showMessageDialog(null, "Error updating cash register");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    /**
     * Verifica si hay suficiente saldo en la caja para realizar un gasto.
     *
     * @param metodo_pago Método de pago utilizado.
     * @param monto       Monto del gasto.
     * @return true si hay suficiente saldo, false en caso contrario.
     */

    public boolean hasSufficientBalance(String metodo_pago, int monto)
    {
        Connection con = connectionDB.getConnection();
        String query = "SELECT valor FROM caja WHERE concepto = ?";

        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, metodo_pago);
            ResultSet rs = pst.executeQuery();

            if(rs.next())
            {
                int currentBalance = rs.getInt("valor");
                return currentBalance >= monto;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }



}
