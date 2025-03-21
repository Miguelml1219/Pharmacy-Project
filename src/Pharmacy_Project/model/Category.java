package Pharmacy_Project.model;

/**
 * Clase que representa una categoría en la base de datos.
 */
public class Category {
    private int id_categoria;
    private String categoria;

    /**
     * Constructor de la clase Category.
     *
     * @param id_categoria Identificador único de la categoría.
     * @param categoria Nombre de la categoría.
     */
    public Category(int id_categoria, String categoria) {
        this.id_categoria = id_categoria;
        this.categoria = categoria;
    }

    /**
     * Obtiene el identificador de la categoría.
     *
     * @return ID de la categoría.
     */
    public int getId_categoria() {
        return id_categoria;
    }

    /**
     * Establece el identificador de la categoría.
     *
     * @param id_categoria ID de la categoría.
     */
    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    /**
     * Obtiene el nombre de la categoría.
     *
     * @return Nombre de la categoría.
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Establece el nombre de la categoría.
     *
     * @param categoria Nombre de la categoría.
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
