package Pharmacy_Project.model;

import java.sql.Timestamp;

/**
 * Clase que representa un pedido en la base de datos.
 */
public class Order {

    private int id_pedido;
    private int id_cliente;
    private Timestamp fecha_pedido;
    private int total_pedido;
    private String metodo_pago;
    private String estado;

    /**
     * Constructor de la clase Order.
     *
     * @param id_pedido Identificador único del pedido.
     * @param id_cliente Identificador del cliente que realizó el pedido.
     * @param fecha_pedido Fecha y hora en que se realizó el pedido.
     * @param total_pedido Monto total del pedido.
     * @param metodo_pago Método de pago utilizado.
     * @param estado Estado actual del pedido.
     */
    public Order(int id_pedido, int id_cliente, Timestamp fecha_pedido, int total_pedido, String metodo_pago, String estado) {
        this.id_pedido = id_pedido;
        this.id_cliente = id_cliente;
        this.fecha_pedido = fecha_pedido;
        this.total_pedido = total_pedido;
        this.metodo_pago = metodo_pago;
        this.estado = estado;
    }

    /**
     * Obtiene el identificador del pedido.
     *
     * @return ID del pedido.
     */
    public int getId_pedido() {
        return id_pedido;
    }

    /**
     * Establece el identificador del pedido.
     *
     * @param id_pedido ID del pedido.
     */
    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    /**
     * Obtiene el identificador del cliente asociado al pedido.
     *
     * @return ID del cliente.
     */
    public int getId_cliente() {
        return id_cliente;
    }

    /**
     * Establece el identificador del cliente asociado al pedido.
     *
     * @param id_cliente ID del cliente.
     */
    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    /**
     * Obtiene la fecha y hora en que se realizó el pedido.
     *
     * @return Fecha y hora del pedido.
     */
    public Timestamp getFecha_pedido() {
        return fecha_pedido;
    }

    /**
     * Establece la fecha y hora en que se realizó el pedido.
     *
     * @param fecha_pedido Fecha y hora del pedido.
     */
    public void setFecha_pedido(Timestamp fecha_pedido) {
        this.fecha_pedido = fecha_pedido;
    }

    /**
     * Obtiene el monto total del pedido.
     *
     * @return Total del pedido.
     */
    public int getTotal_pedido() {
        return total_pedido;
    }

    /**
     * Establece el monto total del pedido.
     *
     * @param total_pedido Total del pedido.
     */
    public void setTotal_pedido(int total_pedido) {
        this.total_pedido = total_pedido;
    }

    /**
     * Obtiene el método de pago utilizado en el pedido.
     *
     * @return Método de pago.
     */
    public String getMetodo_pago() {
        return metodo_pago;
    }

    /**
     * Establece el método de pago utilizado en el pedido.
     *
     * @param metodo_pago Método de pago.
     */
    public void setMetodo_pago(String metodo_pago) {
        this.metodo_pago = metodo_pago;
    }

    /**
     * Obtiene el estado actual del pedido.
     *
     * @return Estado del pedido.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado actual del pedido.
     *
     * @param estado Estado del pedido.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
