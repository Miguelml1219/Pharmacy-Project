package Pharmacy_Project.model;

/**
 * Clase que representa la caja registradora en la base de datos.
 */
public class Cash_Register {

    private int id_caja;
    private String concepto;
    private int valor;

    /**
     * Constructor de la clase Cash_Register.
     *
     * @param id_caja Identificador único de la caja.
     * @param concepto Descripción del concepto registrado en la caja.
     * @param valor Monto asociado al concepto.
     */
    public Cash_Register(int id_caja, String concepto, int valor) {
        this.id_caja = id_caja;
        this.concepto = concepto;
        this.valor = valor;
    }

    /**
     * Obtiene el identificador de la caja.
     *
     * @return ID de la caja.
     */
    public int getId_caja() {
        return id_caja;
    }

    /**
     * Establece el identificador de la caja.
     *
     * @param id_caja ID de la caja.
     */
    public void setId_caja(int id_caja) {
        this.id_caja = id_caja;
    }

    /**
     * Obtiene el concepto de la caja.
     *
     * @return Concepto registrado en la caja.
     */
    public String getConcepto() {
        return concepto;
    }

    /**
     * Establece el concepto de la caja.
     *
     * @param concepto Concepto registrado en la caja.
     */
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    /**
     * Obtiene el valor asociado al concepto de la caja.
     *
     * @return Valor registrado en la caja.
     */
    public int getValor() {
        return valor;
    }

    /**
     * Establece el valor asociado al concepto de la caja.
     *
     * @param valor Valor registrado en la caja.
     */
    public void setValor(int valor) {
        this.valor = valor;
    }
}
