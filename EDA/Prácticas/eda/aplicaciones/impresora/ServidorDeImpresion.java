package aplicaciones.impresora;

/** Interfaz ServidorDeImpresion: especifica los metodos que  
 *  ha de implementar la cola (print queue), con o sin  
 *  prioridades, de un servidor de impresion ("print server).
 *  
 *  @author (profesores EDA 16-17)
 *  @version (04 2017)
 **/

public interface ServidorDeImpresion {
    // Paginas por minuto que puede imprimir la impresora
    // asociada al servidor
    int PAGINAS_POR_MINUTO = 30;
    
    /** Inserta el Trabajo t en la cola de un ServidorDeImpresion.
     *  @param t   Trabajo
     */
    void insertar(Trabajo t);
    
    /** Comprueba si hay algun Trabajo a imprimir en la cola de
     *  un ServidorDeImpresion.
     *  @return boolean
     */
    boolean hayTrabajos();
    
    /** SII hayTrabajos(): devuelve el primer Trabajo en la 
     *  cola de un ServidorDeImpresion, o trabajo a imprimir.
     *  @return Trabajo
     */
    Trabajo getTrabajo();
    
   /** SII hayTrabajos(): elimina el primer trabajo en la cola 
    *  de un ServidorDeImpresion para imprimirlo y devuelve el 
    *  tiempo que tardara en imprimirse.
    *  @return int (seg.)
    */
    int imprimirTrabajo();
}
