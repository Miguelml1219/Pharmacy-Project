package Pharmacy_Project.model;

import java.util.Date;

public class Products {

    int id_producto, id_categoria, precio, stock_actual, stock_minimo;
    String nombre, descripcion, lote;
    Date fecha_vencimiento;

    public Products(int id_producto, int id_categoria, int precio, int stock_actual, int stock_minimo, String nombre, String descripcion, String lote, Date fecha_vencimiento) {
        this.id_producto = id_producto;
        this.id_categoria = id_categoria;
        this.precio = precio;
        this.stock_actual = stock_actual;
        this.stock_minimo = stock_minimo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.lote = lote;
        this.fecha_vencimiento = fecha_vencimiento;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getStock_actual() {
        return stock_actual;
    }

    public void setStock_actual(int stock_actual) {
        this.stock_actual = stock_actual;
    }

    public int getStock_minimo() {
        return stock_minimo;
    }

    public void setStock_minimo(int stock_minimo) {
        this.stock_minimo = stock_minimo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Date getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    public void setFecha_vencimiento(Date fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }
}
