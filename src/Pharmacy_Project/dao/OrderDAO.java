package Pharmacy_Project.dao;

import Pharmacy_Project.connection.ConnectionDB;
import Pharmacy_Project.model.Order;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderDAO {

    private ConnectionDB connectionDB = new ConnectionDB();
    private Order_DetailDAO order_detailDAO = new Order_DetailDAO();

    public void addOrder(Order order)
    {
        Connection con = connectionDB.getConnection();


        String query = "INSERT INTO pedidos(id_cliente, fecha_pedido, total_pedido, metodo_pago, estado) VALUES (?,?,?,?,?)";

        try
        {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, order.getId_cliente());
            pst.setTimestamp(2, order.getFecha_pedido());
            pst.setInt(3, order.getTotal_pedido());
            pst.setString(4, order.getMetodo_pago());
            pst.setString(5, order.getEstado());

            int result = pst.executeUpdate();

            if (result>0)
                JOptionPane.showMessageDialog(null,"Succesfully added");
            else
                JOptionPane.showMessageDialog(null,"Dont added");

        }

        catch (SQLException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Dont added");

        }

    }

    public void delete(int id)
    {
        Connection con = connectionDB.getConnection();
        String query = "DELETE FROM pedidos WHERE id_pedido = ?";

        try
        {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id);

            int result = pst.executeUpdate();

            if (result>0)
                JOptionPane.showMessageDialog(null,"Succesfully delete");
            else
                JOptionPane.showMessageDialog(null,"Dont delete");

        }

        catch (SQLException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Dont delete");

        }
    }

    public void upOrder(Order order)
    {
        Connection con = connectionDB.getConnection();

        int currentId = order_detailDAO.obtenerID();
        String query = "UPDATE pedidos SET total_pedido = ?, metodo_pago = ?, estado = ? WHERE id_pedido = ?";

        try {
            PreparedStatement pst = con.prepareStatement(query);

            pst.setInt(1, order.getTotal_pedido());
            pst.setString(2, order.getMetodo_pago());
            pst.setString(3, order.getEstado());
            pst.setInt(4,currentId);



            int resultado = pst.executeUpdate();
            if(resultado>0)
                JOptionPane.showMessageDialog(null,"Order placed successfully");
            else
                JOptionPane.showMessageDialog(null,"Order not placed");

        }
        catch (SQLException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Order not placed");        }
    }




}
