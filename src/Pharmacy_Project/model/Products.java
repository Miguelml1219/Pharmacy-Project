package Pharmacy_Project.model;

import java.util.Date;

/**
 * Clase que representa un producto en el sistema de farmacia.
 */
public class Products {

    int id_producto, id_categoria; String nombre, descripcion; int precio, stock_actual, stock_minimo; Date fecha_vencimiento; String lote;

    /**
     * Constructor para crear un nuevo producto.
     *
     * @param id_producto       Identificador único del producto.
     * @param id_categoria      Identificador de la categoría a la que pertenece el producto.
     * @param nombre            Nombre del producto.
     * @param descripcion       Descripción del producto.
     * @param precio            Precio del producto.
     * @param stock_actual      Cantidad actual en stock.
     * @param stock_minimo      Stock mínimo permitido antes de notificar reabastecimiento.
     * @param fecha_vencimiento Fecha de vencimiento del producto.
     * @param lote              Número de lote del producto.
     */

    public Products(int id_producto, int id_categoria, String nombre, String descripcion, int precio, int stock_actual, int stock_minimo, Date fecha_vencimiento, String lote) {
        this.id_producto = id_producto;
        this.id_categoria = id_categoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock_actual = stock_actual;
        this.stock_minimo = stock_minimo;
        this.fecha_vencimiento = fecha_vencimiento;
        this.lote = lote;
    }

    /**
     * Obtiene el identificador del producto.
     *
     * @return ID del producto.
     */

    public int getId_producto() {
        return id_producto;
    }

    /**
     * Establece el identificador del producto.
     *
     * @param id_producto Nuevo ID del producto.
     */

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    /**
     * Obtiene el identificador de la categoría del producto.
     *
     * @return ID de la categoría.
     */

    public int getId_categoria() {
        return id_categoria;
    }

    /**
     * Establece el identificador de la categoría del producto.
     *
     * @param id_categoria Nuevo ID de la categoría.
     */

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    /**
     * Obtiene el nombre del producto.
     *
     * @return Nombre del producto.
     */

    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del producto.
     *
     * @param nombre Nuevo nombre del producto.
     */

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la descripción del producto.
     *
     * @return Descripción del producto.
     */

    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del producto.
     *
     * @param descripcion Nueva descripción del producto.
     */

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el precio del producto.
     *
     * @return Precio del producto.
     */

    public int getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del producto.
     *
     * @param precio Nuevo precio del producto.
     */

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    /**
     * Obtiene la cantidad actual en stock del producto.
     *
     * @return Stock actual.
     */

    public int getStock_actual() {
        return stock_actual;
    }

    /**
     * Establece la cantidad actual en stock del producto.
     *
     * @param stock_actual Nuevo stock actual.
     */

    public void setStock_actual(int stock_actual) {
        this.stock_actual = stock_actual;
    }

    /**
     * Obtiene el stock mínimo permitido antes de notificar reabastecimiento.
     *
     * @return Stock mínimo.
     */

    public int getStock_minimo() {
        return stock_minimo;
    }

    /**
     * Establece el stock mínimo permitido antes de notificar reabastecimiento.
     *
     * @param stock_minimo Nuevo stock mínimo.
     */

    public void setStock_minimo(int stock_minimo) {
        this.stock_minimo = stock_minimo;
    }

    /**
     * Obtiene la fecha de vencimiento del producto.
     *
     * @return Fecha de vencimiento.
     */

    public java.sql.Date getFecha_vencimiento() {
        return (java.sql.Date) fecha_vencimiento;
    }

    /**
     * Establece la fecha de vencimiento del producto.
     *
     * @param fecha_vencimiento Nueva fecha de vencimiento.
     */

    public void setFecha_vencimiento(Date fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }

    /**
     * Obtiene el número de lote del producto.
     *
     * @return Número de lote.
     */

    public String getLote() {
        return lote;
    }

    /**
     * Establece el número de lote del producto.
     *
     * @param lote Nuevo número de lote.
     */

    public void setLote(String lote) {
        this.lote = lote;
    }
}
