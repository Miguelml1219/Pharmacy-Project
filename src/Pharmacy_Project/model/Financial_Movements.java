package Pharmacy_Project.model;

import java.sql.Timestamp;

public class Financial_Movements {

    int id_movimiento; String tipo_movimiento, categoria; int monto; Timestamp fecha_hora; String descripcion, metodo_pago;

    public Financial_Movements(int id_movimiento, String tipo_movimiento, String categoria, int monto, Timestamp fecha_hora, String descripcion, String metodo_pago) {
        this.id_movimiento = id_movimiento;
        this.tipo_movimiento = tipo_movimiento;
        this.categoria = categoria;
        this.monto = monto;
        this.fecha_hora = fecha_hora;
        this.descripcion = descripcion;
        this.metodo_pago = metodo_pago;
    }

    public int getId_movimiento() {
        return id_movimiento;
    }

    public void setId_movimiento(int id_movimiento) {
        this.id_movimiento = id_movimiento;
    }

    public String getTipo_movimiento() {
        return tipo_movimiento;
    }

    public void setTipo_movimiento(String tipo_movimiento) {
        this.tipo_movimiento = tipo_movimiento;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public Timestamp getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(Timestamp fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMetodo_pago() {
        return metodo_pago;
    }

    public void setMetodo_pago(String metodo_pago) {
        this.metodo_pago = metodo_pago;
    }
}
