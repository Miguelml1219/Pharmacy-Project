package Pharmacy_Project.model;


/**
 * Representa un detalle de pedido en el sistema de farmacia.
 */

public class Order_Detail {

    int id_detalle, id_pedido, id_producto; String und_medida; int cantidad, precio_unitario, subtotal;

    /**
     * Constructor para crear un nuevo detalle de pedido.
     *
     * @param id_detalle     Identificador único del detalle de pedido.
     * @param id_pedido      Identificador del pedido al que pertenece el detalle.
     * @param id_producto    Identificador del producto.
     * @param und_medida     Unidad de medida del producto.
     * @param cantidad       Cantidad de productos en el pedido.
     * @param precio_unitario Precio unitario del producto.
     */

    public Order_Detail(int id_detalle, int id_pedido, int id_producto, String und_medida, int cantidad, int precio_unitario, int subtotal) {
        this.id_detalle = id_detalle;
        this.id_pedido = id_pedido;
        this.id_producto = id_producto;
        this.und_medida = und_medida;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
        this.subtotal = subtotal;
    }

    /**
     * Obtiene el ID del detalle de pedido.
     *
     * @return ID del detalle.
     */

    public int getId_detalle() {
        return id_detalle;
    }

    /**
     * Establece el identificador del detalle del pedido.
     *
     * @param id_detalle Nuevo ID del detalle del pedido.
     */

    public void setId_detalle(int id_detalle) {
        this.id_detalle = id_detalle;
    }

    /**
     * Obtiene el identificador del pedido al que pertenece el detalle.
     *
     * @return ID del pedido.
     */

    public int getId_pedido() {
        return id_pedido;
    }

    /**
     * Establece el identificador del pedido al que pertenece el detalle.
     *
     * @param id_pedido Nuevo ID del pedido.
     */

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }


    /**
     * Obtiene el identificador del producto en el detalle.
     *
     * @return ID del producto.
     */

    public int getId_producto() {
        return id_producto;
    }

    /**
     * Establece el identificador del producto en el detalle.
     *
     * @param id_producto Nuevo ID del producto.
     */


    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    /**
     * Obtiene la unidad de medida del producto.
     *
     * @return Unidad de medida.
     */

    public String getUnd_medida() {
        return und_medida;
    }

    /**
     * Establece la unidad de medida del producto.
     *
     * @param und_medida Nueva unidad de medida.
     */

    public void setUnd_medida(String und_medida) {
        this.und_medida = und_medida;
    }


    /**
     * Obtiene la cantidad de unidades del producto en el detalle.
     *
     * @return Cantidad de productos.
     */

    public int getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad de unidades del producto en el detalle.
     *
     * @param cantidad Nueva cantidad de productos.
     */

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el precio unitario del producto.
     *
     * @return Precio unitario del producto.
     */

    public int getPrecio_unitario() {
        return precio_unitario;
    }

    /**
     * Establece el precio unitario del producto.
     *
     * @param precio_unitario Nuevo precio unitario.
     */

    public void setPrecio_unitario(int precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    /**
     * Obtiene el subtotal del detalle del pedido (cantidad * precio_unitario).
     *
     * @return Subtotal del detalle del pedido.
     */

    public int getSubtotal() {
        return subtotal;
    }

    /**
     * Establece el subtotal del detalle del pedido.
     *
     * @param subtotal Nuevo subtotal del detalle.
     */

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }
}
