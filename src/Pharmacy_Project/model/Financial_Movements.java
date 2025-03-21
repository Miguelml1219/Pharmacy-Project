package Pharmacy_Project.model;

import java.sql.Timestamp;

/**
 * Clase que representa los movimientos financieros en la base de datos.
 */
public class Financial_Movements {

    private int id_movimiento;
    private String tipo_movimiento;
    private String categoria;
    private int monto;
    private Timestamp fecha_hora;
    private String descripcion;
    private String metodo_pago;

    /**
     * Constructor de la clase Financial_Movements.
     *
     * @param id_movimiento Identificador único del movimiento financiero.
     * @param tipo_movimiento Tipo de movimiento (ingreso o egreso).
     * @param categoria Categoría del movimiento.
     * @param monto Monto del movimiento.
     * @param fecha_hora Fecha y hora del movimiento.
     * @param descripcion Descripción del movimiento.
     * @param metodo_pago Método de pago utilizado.
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
     * @return ID del movimiento.
     */
    public int getId_movimiento() {
        return id_movimiento;
    }

    /**
     * Establece el identificador del movimiento financiero.
     *
     * @param id_movimiento ID del movimiento.
     */
    public void setId_movimiento(int id_movimiento) {
        this.id_movimiento = id_movimiento;
    }

    /**
     * Obtiene el tipo de movimiento financiero.
     *
     * @return Tipo de movimiento (ingreso o egreso).
     */
    public String getTipo_movimiento() {
        return tipo_movimiento;
    }

    /**
     * Establece el tipo de movimiento financiero.
     *
     * @param tipo_movimiento Tipo de movimiento.
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
     * @param categoria Categoría del movimiento.
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
     * @param monto Monto del movimiento.
     */
    public void setMonto(int monto) {
        this.monto = monto;
    }

    /**
     * Obtiene la fecha y hora del movimiento financiero.
     *
     * @return Fecha y hora del movimiento.
     */
    public Timestamp getFecha_hora() {
        return fecha_hora;
    }

    /**
     * Establece la fecha y hora del movimiento financiero.
     *
     * @param fecha_hora Fecha y hora del movimiento.
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
     * @param descripcion Descripción del movimiento.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el método de pago utilizado en el movimiento financiero.
     *
     * @return Método de pago.
     */
    public String getMetodo_pago() {
        return metodo_pago;
    }

    /**
     * Establece el método de pago utilizado en el movimiento financiero.
     *
     * @param metodo_pago Método de pago.
     */
    public void setMetodo_pago(String metodo_pago) {
        this.metodo_pago = metodo_pago;
    }
}
