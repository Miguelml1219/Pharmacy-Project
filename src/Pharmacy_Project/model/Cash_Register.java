package Pharmacy_Project.model;

/**
 * Representa un registro en la caja de la farmacia, incluyendo el concepto y el valor asociado.
 */
public class Cash_Register {

    int id_caja; String concepto; int valor;

    /**
     * Constructor para crear un nuevo registro de caja.
     *
     * @param id_caja  Identificador de la caja.
     * @param concepto Concepto del movimiento financiero en la caja.
     * @param valor    Valor del movimiento en la caja.
     */
    public Cash_Register(int id_caja, String concepto, int valor) {
        this.id_caja = id_caja;
        this.concepto = concepto;
        this.valor = valor;
    }

    /**
     * Obtiene el identificador de la caja.
     *
     * @return El ID de la caja.
     */

    public int getId_caja() {
        return id_caja;
    }

    /**
     * Establece el identificador de la caja.
     *
     * @param id_caja Nuevo ID de la caja.
     */
    public void setId_caja(int id_caja) {
        this.id_caja = id_caja;
    }

    /**
     * Obtiene el concepto del movimiento en la caja.
     *
     * @return El concepto del movimiento financiero.
     */
    public String getConcepto() {
        return concepto;
    }

    /**
     * Establece el concepto del movimiento en la caja.
     *
     * @param concepto Nuevo concepto del movimiento financiero.
     */
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }


    /**
     * Obtiene el valor del movimiento en la caja.
     *
     * @return El valor del movimiento financiero.
     */
    public int getValor() {
        return valor;
    }

    /**
     * Establece el valor del movimiento en la caja.
     *
     * @param valor Nuevo valor del movimiento financiero.
     */
    public void setValor(int valor) {
        this.valor = valor;
    }
}
