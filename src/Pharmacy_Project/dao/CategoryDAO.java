package Pharmacy_Project.dao;

import Pharmacy_Project.connection.ConnectionDB;
import Pharmacy_Project.model.Category;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CategoryDAO {

    private ConnectionDB connectionDB = new ConnectionDB();


    public void add(Category category)
    {
        Connection con = connectionDB.getConnection();
        String query = "INSERT INTO categoria(nombre_categoria) VALUES (?)";

        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, category.getNombre_categoria());

            int result = pst.executeUpdate();

            if (result > 0){
                JOptionPane.showMessageDialog(null, "Succesfully added");

            }else{
                JOptionPane.showMessageDialog(null,"Dont added");
            }

        }

        catch (SQLException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Dont added");

        }
    }

    public void update(Category category){
        Connection con = connectionDB.getConnection();

        String query = "UPDATE categoria SET nombre_categoria = ? WHERE id_categoria = ?";

        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1,category.getNombre_categoria());
            stmt.setInt(2,category.getId_categoria());


            int result = stmt.executeUpdate();

            if (result>0)
                JOptionPane.showMessageDialog(null,"Update category successfully");
            else
                JOptionPane.showMessageDialog(null,"Category not found");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    public void delete(int id)
    {
        Connection con = connectionDB.getConnection();
        String query = "DELETE FROM categoria WHERE id_categoria = ?";

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

}
