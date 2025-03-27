package Pharmacy_Project.model;

import java.sql.Timestamp;

/**
 * Representa un pedido en el sistema de farmacia.
 */
public class Order {

    int id_pedido, id_cliente; Timestamp fecha_pedido; int total_pedido; String metodo_pago, estado;

    /**
     * Constructor para crear un nuevo pedido.
     *
     * @param id_pedido   Identificador único del pedido.
     * @param id_cliente  Identificador del cliente asociado al pedido.
     * @param fecha_pedido Fecha y hora en que se realizó el pedido.
     * @param total_pedido Total del pedido.
     * @param metodo_pago Método de pago utilizado (Ej: Efectivo, Transferencia).
     * @param estado      Estado del pedido (Ej: Pendiente, Completado, Cancelado).
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
     * @param id_pedido Nuevo ID del pedido.
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
     * @param id_cliente Nuevo ID del cliente.
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
     * @param fecha_pedido Nueva fecha y hora del pedido.
     */

    public void setFecha_pedido(Timestamp fecha_pedido) {
        this.fecha_pedido = fecha_pedido;
    }

    /**
     * Obtiene el total del pedido.
     *
     * @return Total del pedido.
     */

    public int getTotal_pedido() {return total_pedido;}

    /**
     * Establece el total del pedido.
     *
     * @param total_pedido Nuevo total del pedido.
     */

    public void setToal_pedido(int total_pedido) {
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
     * @param metodo_pago Nuevo método de pago.
     */

    public void setMetodo_pago(String metodo_pago) {
        this.metodo_pago = metodo_pago;
    }

    /**
     * Obtiene el estado del pedido.
     *
     * @return Estado del pedido.
     */

    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado del pedido.
     *
     * @param estado Nuevo estado del pedido.
     */

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
