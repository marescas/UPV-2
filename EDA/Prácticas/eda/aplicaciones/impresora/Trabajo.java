package aplicaciones.impresora;

/** Clase Trabajo: representa un documento a imprimir.
 *  ATRIBUTOS: 
 *      TIENE UN titulo (String)
 *      TIENE UN numero de paginas (int)
 *      TIENE UN instante de envio a imprimir en segundos (int)
 * @author (profesores EDA 16-17)
 * @version (04 2017)
 **/
 
public class Trabajo implements Comparable<Trabajo> {
    
    private String titulo;
    private int numPaginas;
    private int envio;
    
    /** Crea un Trabajo de titulo t, numero de paginas n
     *  y enviado a imprimir en el intante de envio e.
     *  @param t    String
     *  @param n    int
     *  @param e    int (seg.)
     */
    public Trabajo(String t, int n, int e) {
        titulo = t;
        numPaginas = n;
        envio = e;
    }
    
    /** Devuelve el titulo de un Trabajo.
     *  @return String
     */
    public String getTitulo() { return titulo; }
    
    /** Devuelve el numero de paginas de un Trabajo.
     *  @return int
     */
    public int getNumPaginas() { return numPaginas; }
    
    /** Devuelve el instante de envio a impresion de un Trabajo.
     *  @return int (seg.)
     */
    public int getEnvio() { return envio; }
    
    /** Devuelve el String que representa un Trabajo en un cierto 
     *  formato texto.
     *  @return String
     */
    public String toString() {
        return titulo + " (" + numPaginas + " pag.) Envio: " + envio;
    }
    public int compareTo(Trabajo t){
        return numPaginas-t.getNumPaginas();
   }
}
