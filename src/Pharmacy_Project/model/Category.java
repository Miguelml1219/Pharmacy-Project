package Pharmacy_Project.model;

/**
 * Representa una categoría de productos en la farmacia.
 */
public class Category {

    int id_categoria; String nombre_categoria;

    /**
     * Constructor para crear una nueva categoría.
     *
     * @param id_categoria      Identificador único de la categoría.
     * @param nombre_categoria  Nombre de la categoría.
     */

    public Category(int id_categoria, String nombre_categoria) {
        this.id_categoria = id_categoria;
        this.nombre_categoria = nombre_categoria;
    }

    /**
     * Obtiene el identificador de la categoría.
     *
     * @return El ID de la categoría.
     */

    public int getId_categoria() {
        return id_categoria;
    }

    /**
     * Establece el identificador de la categoría.
     *
     * @param id_categoria Nuevo ID de la categoría.
     */

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }


    /**
     * Obtiene el nombre de la categoría.
     *
     * @return El nombre de la categoría.
     */

    public String getNombre_categoria() {
        return nombre_categoria;
    }

    /**
     * Establece el nombre de la categoría.
     *
     * @param nombre_categoria Nuevo nombre de la categoría.
     */

    public void setNombre_categoria(String nombre_categoria) {
        this.nombre_categoria = nombre_categoria;
    }
}
