package Pharmacy_Project.model;

/**
 * Clase que representa el detalle de un pedido en la base de datos.
 */
public class Order_Detail {

    private int id_detalle;
    private int id_pedido;
    private int id_producto;
    private String und_medida;
    private int precio_unitario;
    private int cantidad;
    private int subtotal;

    /**
     * Constructor de la clase Order_Detail.
     *
     * @param id_detalle Identificador único del detalle.
     * @param id_pedido Identificador del pedido asociado.
     * @param id_producto Identificador del producto asociado.
     * @param und_medida Unidad de medida del producto.
     * @param precio_unitario Precio unitario del producto.
     * @param cantidad Cantidad del producto en el pedido.
     * @param subtotal Subtotal calculado (precio_unitario * cantidad).
     */
    public Order_Detail(int id_detalle, int id_pedido, int id_producto, String und_medida, int precio_unitario, int cantidad, int subtotal) {
        this.id_detalle = id_detalle;
        this.id_pedido = id_pedido;
        this.id_producto = id_producto;
        this.und_medida = und_medida;
        this.precio_unitario = precio_unitario;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    /**
     * Obtiene el identificador del detalle del pedido.
     *
     * @return ID del detalle.
     */
    public int getId_detalle() {
        return id_detalle;
    }

    /**
     * Establece el identificador del detalle del pedido.
     *
     * @param id_detalle ID del detalle.
     */
    public void setId_detalle(int id_detalle) {
        this.id_detalle = id_detalle;
    }

    /**
     * Obtiene el identificador del pedido asociado.
     *
     * @return ID del pedido.
     */
    public int getId_pedido() {
        return id_pedido;
    }

    /**
     * Establece el identificador del pedido asociado.
     *
     * @param id_pedido ID del pedido.
     */
    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    /**
     * Obtiene el identificador del producto asociado.
     *
     * @return ID del producto.
     */
    public int getId_producto() {
        return id_producto;
    }

    /**
     * Establece el identificador del producto asociado.
     *
     * @param id_producto ID del producto.
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
     * @param und_medida Unidad de medida.
     */
    public void setUnd_medida(String und_medida) {
        this.und_medida = und_medida;
    }

    /**
     * Obtiene el precio unitario del producto.
     *
     * @return Precio unitario.
     */
    public int getPrecio_unitario() {
        return precio_unitario;
    }

    /**
     * Establece el precio unitario del producto.
     *
     * @param precio_unitario Precio unitario.
     */
    public void setPrecio_unitario(int precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    /**
     * Obtiene la cantidad del producto en el pedido.
     *
     * @return Cantidad del producto.
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad del producto en el pedido.
     *
     * @param cantidad Cantidad del producto.
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el subtotal calculado del detalle del pedido.
     *
     * @return Subtotal.
     */
    public int getSubtotal() {
        return subtotal;
    }

    /**
     * Establece el subtotal del detalle del pedido.
     *
     * @param subtotal Subtotal.
     */
    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }
}
