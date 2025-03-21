package Pharmacy_Project.model;

import java.util.Date;

/**
 * Clase que representa un producto en la base de datos.
 */
public class Products {

    private int id_producto, id_categoria, precio, stock_actual, stock_minimo;
    private String nombre, descripcion, lote;
    private Date fecha_vencimiento;

    /**
     * Constructor de la clase Products.
     *
     * @param id_producto Identificador único del producto.
     * @param id_categoria Identificador de la categoría del producto.
     * @param precio Precio del producto.
     * @param stock_actual Cantidad actual en stock.
     * @param stock_minimo Cantidad mínima en stock antes de requerir reposición.
     * @param nombre Nombre del producto.
     * @param descripcion Descripción del producto.
     * @param lote Número de lote del producto.
     * @param fecha_vencimiento Fecha de vencimiento del producto.
     */
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

    /**
     * Obtiene el ID del producto.
     * @return ID del producto.
     */
    public int getId_producto() {
        return id_producto;
    }

    /**
     * Establece el ID del producto.
     * @param id_producto Nuevo ID del producto.
     */
    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    /**
     * Obtiene el ID de la categoría del producto.
     * @return ID de la categoría.
     */
    public int getId_categoria() {
        return id_categoria;
    }

    /**
     * Establece el ID de la categoría del producto.
     * @param id_categoria Nuevo ID de la categoría.
     */
    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    /**
     * Obtiene el precio del producto.
     * @return Precio del producto.
     */
    public int getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del producto.
     * @param precio Nuevo precio del producto.
     */
    public void setPrecio(int precio) {
        this.precio = precio;
    }

    /**
     * Obtiene la cantidad actual en stock.
     * @return Stock actual.
     */
    public int getStock_actual() {
        return stock_actual;
    }

    /**
     * Establece la cantidad actual en stock.
     * @param stock_actual Nueva cantidad en stock.
     */
    public void setStock_actual(int stock_actual) {
        this.stock_actual = stock_actual;
    }

    /**
     * Obtiene la cantidad mínima en stock.
     * @return Stock mínimo.
     */
    public int getStock_minimo() {
        return stock_minimo;
    }

    /**
     * Establece la cantidad mínima en stock.
     * @param stock_minimo Nueva cantidad mínima en stock.
     */
    public void setStock_minimo(int stock_minimo) {
        this.stock_minimo = stock_minimo;
    }

    /**
     * Obtiene el nombre del producto.
     * @return Nombre del producto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del producto.
     * @param nombre Nuevo nombre del producto.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la descripción del producto.
     * @return Descripción del producto.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del producto.
     * @param descripcion Nueva descripción del producto.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el número de lote del producto.
     * @return Número de lote.
     */
    public String getLote() {
        return lote;
    }

    /**
     * Establece el número de lote del producto.
     * @param lote Nuevo número de lote.
     */
    public void setLote(String lote) {
        this.lote = lote;
    }

    /**
     * Obtiene la fecha de vencimiento del producto.
     * @return Fecha de vencimiento.
     */
    public Date getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    /**
     * Establece la fecha de vencimiento del producto.
     * @param fecha_vencimiento Nueva fecha de vencimiento.
     */
    public void setFecha_vencimiento(Date fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }
}
