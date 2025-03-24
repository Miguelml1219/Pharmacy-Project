package Pharmacy_Project.model;

/**
 * Clase que representa un cliente con sus datos personales y categoría.
 */
public class Customers {
    private int id_cliente;
    private String cedula;
    private String nombre_completo;
    private String telefono;
    private String correo_electronico;
    private String direccion;
    private String categoria;

    /**
     * Constructor para inicializar un objeto Cliente con todos sus atributos.
     *
     * @param id_cliente        Identificador único del cliente.
     * @param cedula            Número de cédula del cliente.
     * @param nombre_completo   Nombre completo del cliente.
     * @param telefono          Número de teléfono del cliente.
     * @param correo_electronico Correo electrónico del cliente.
     * @param direccion         Dirección del cliente.
     * @param categoria         Categoría del cliente (ejemplo: frecuente, nuevo, etc.).
     */
    public Customers(int id_cliente, String cedula, String nombre_completo, String telefono, String correo_electronico, String direccion, String categoria) {
        this.id_cliente = id_cliente;
        this.cedula = cedula;
        this.nombre_completo = nombre_completo;
        this.telefono = telefono;
        this.correo_electronico = correo_electronico;
        this.direccion = direccion;
        this.categoria = categoria;
    }

    /**
     * Obtiene el ID del cliente.
     *
     * @return ID del cliente.
     */
    public int getId_cliente() {
        return id_cliente;
    }

    /**
     * Establece el ID del cliente.
     *
     * @param id_cliente Nuevo ID del cliente.
     */
    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    /**
     * Obtiene la cédula del cliente.
     *
     * @return Cédula del cliente.
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * Establece la cédula del cliente.
     *
     * @param cedula Nueva cédula del cliente.
     */
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    /**
     * Obtiene el nombre completo del cliente.
     *
     * @return Nombre completo del cliente.
     */
    public String getNombre_completo() {
        return nombre_completo;
    }

    /**
     * Establece el nombre completo del cliente.
     *
     * @param nombre_completo Nuevo nombre completo del cliente.
     */
    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    /**
     * Obtiene el número de teléfono del cliente.
     *
     * @return Número de teléfono del cliente.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el número de teléfono del cliente.
     *
     * @param telefono Nuevo número de teléfono del cliente.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene el correo electrónico del cliente.
     *
     * @return Correo electrónico del cliente.
     */
    public String getCorreo_electronico() {
        return correo_electronico;
    }

    /**
     * Establece el correo electrónico del cliente.
     *
     * @param correo_electronico Nuevo correo electrónico del cliente.
     */
    public void setCorreo_electronico(String correo_electronico) {
        this.correo_electronico = correo_electronico;
    }

    /**
     * Obtiene la dirección del cliente.
     *
     * @return Dirección del cliente.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección del cliente.
     *
     * @param direccion Nueva dirección del cliente.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene la categoría del cliente.
     *
     * @return Categoría del cliente.
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Establece la categoría del cliente.
     *
     * @param categoria Nueva categoría del cliente.
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
