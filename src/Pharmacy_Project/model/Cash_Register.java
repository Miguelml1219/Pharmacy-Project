package Pharmacy_Project.model;

public class Cash_Register {

    int id_caja; String concepto; int valor;

    public Cash_Register(int id_caja, String concepto, int valor) {
        this.id_caja = id_caja;
        this.concepto = concepto;
        this.valor = valor;
    }

    public int getId_caja() {
        return id_caja;
    }

    public void setId_caja(int id_caja) {
        this.id_caja = id_caja;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}
