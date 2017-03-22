package aplicaciones.indices;
/**
 * Clase que identifica un numero de linea en un libro.
 * 
 * @author (EDA) 
 * @version (Curso 2016-2017)
 */
public class Indice {
    protected String libro;
    protected int linea;
    
    /**
     * Construye un objeto Indice a partir del nombre del libro (lib)
     * y del numero de pagina (lin).
     * @param  lib   nombre del libro
     * @param  lin   número de linea
     */
    public Indice(String lib,  int lin) {
        this.libro = new String(lib);
        this.linea = lin;
    }
    
    /**
     * Devuelve la información textual asociada a un Indice
     * @return  String con el nombre del libro y el número de líneas.
     */
    public String toString() {
        return libro + "             " + linea + "\n";
    }
}
