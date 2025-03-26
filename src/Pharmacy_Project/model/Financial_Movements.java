package Pharmacy_Project.model;

import java.sql.Timestamp;


/**
 * Representa un movimiento financiero en el sistema de farmacia.
 */
public class Financial_Movements {

    int id_movimiento; String tipo_movimiento, categoria; int monto; Timestamp fecha_hora; String descripcion, metodo_pago;

    /**
     * Constructor para crear un nuevo movimiento financiero.
     *
     * @param id_movimiento  Identificador único del movimiento.
     * @param tipo_movimiento Tipo de movimiento (Ingreso o Egreso).
     * @param categoria      Categoría del movimiento (Ej: Devolución, Gastos fijos).
     * @param monto         Monto del movimiento.
     * @param fecha_hora    Fecha y hora en que se registró el movimiento.
     * @param descripcion   Descripción del movimiento.
     * @param metodo_pago   Método de pago utilizado (Ej: Efectivo, Transferencia).
     */

    public Financial_Movements(int id_movimiento, String tipo_movimiento, String categoria, int monto, Timestamp fecha_hora, String descripcion, String metodo_pago) {
        this.id_movimiento = id_movimiento;
        this.tipo_movimiento = tipo_movimiento;
        this.categoria = categoria;
        this.monto = monto;
        this.fecha_hora = fecha_hora;
        this.descripcion = descripcion;
        this.metodo_pago = metodo_pago;
    }

    /**
     * Obtiene el identificador del movimiento financiero.
     *
     * @return El ID del movimiento.
     */

    public int getId_movimiento() {
        return id_movimiento;
    }

    /**
     * Establece el identificador del movimiento financiero.
     *
     * @param id_movimiento Nuevo ID del movimiento.
     */

    public void setId_movimiento(int id_movimiento) {
        this.id_movimiento = id_movimiento;
    }

    /**
     * Obtiene el tipo de movimiento financiero.
     *
     * @return Tipo de movimiento (Ingreso o Egreso).
     */

    public String getTipo_movimiento() {
        return tipo_movimiento;
    }

    /**
     * Establece el tipo de movimiento financiero.
     *
     * @param tipo_movimiento Nuevo tipo de movimiento.
     */

    public void setTipo_movimiento(String tipo_movimiento) {
        this.tipo_movimiento = tipo_movimiento;
    }

    /**
     * Obtiene la categoría del movimiento financiero.
     *
     * @return Categoría del movimiento.
     */

    public String getCategoria() {
        return categoria;
    }

    /**
     * Establece la categoría del movimiento financiero.
     *
     * @param categoria Nueva categoría del movimiento.
     */

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * Obtiene el monto del movimiento financiero.
     *
     * @return Monto del movimiento.
     */

    public int getMonto() {
        return monto;
    }

    /**
     * Establece el monto del movimiento financiero.
     *
     * @param monto Nuevo monto del movimiento.
     */

    public void setMonto(int monto) {
        this.monto = monto;
    }


    /**
     * Obtiene la fecha y hora del movimiento financiero.
     *
     * @return Timestamp del movimiento.
     */

    public Timestamp getFecha_hora() {
        return fecha_hora;
    }

    /**
     * Establece la fecha y hora del movimiento financiero.
     *
     * @param fecha_hora Nueva fecha y hora del movimiento.
     */

    public void setFecha_hora(Timestamp fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    /**
     * Obtiene la descripción del movimiento financiero.
     *
     * @return Descripción del movimiento.
     */

    public String getDescripcion() {
        return descripcion;
    }


    /**
     * Establece la descripción del movimiento financiero.
     *
     * @param descripcion Nueva descripción del movimiento.
     */

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el método de pago del movimiento financiero.
     *
     * @return Método de pago utilizado.
     */

    public String getMetodo_pago() {
        return metodo_pago;
    }

    /**
     * Establece el método de pago del movimiento financiero.
     *
     * @param metodo_pago Nuevo método de pago.
     */

    public void setMetodo_pago(String metodo_pago) {
        this.metodo_pago = metodo_pago;
    }
}
