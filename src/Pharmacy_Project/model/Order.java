package Pharmacy_Project.model;

import java.sql.Timestamp;

public class Order {

    int id_pedido, id_cliente; Timestamp fecha_pedido; int total_pedido; String metodo_pago, estado;

    public Order(int id_pedido, int id_cliente, Timestamp fecha_pedido, int total_pedido, String metodo_pago, String estado) {
        this.id_pedido = id_pedido;
        this.id_cliente = id_cliente;
        this.fecha_pedido = fecha_pedido;
        this.total_pedido = total_pedido;
        this.metodo_pago = metodo_pago;
        this.estado = estado;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public Timestamp getFecha_pedido() {
        return fecha_pedido;
    }

    public void setFecha_pedido(Timestamp fecha_pedido) {
        this.fecha_pedido = fecha_pedido;
    }

    public int getTotal_pedido() {return total_pedido;}

    public void setToal_pedido(int total_pedido) {
        this.total_pedido = total_pedido;
    }

    public String getMetodo_pago() {
        return metodo_pago;
    }

    public void setMetodo_pago(String metodo_pago) {
        this.metodo_pago = metodo_pago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
