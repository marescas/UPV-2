package aplicaciones.municipios;

/**Clase municpio: representa poblaciones de Espanya
 * @author (profesores EDA) 
 * @version (Curso 2016/17)
 */
public class Municipio {
    
    private String nombre;    
    private int poblacion;    // en numero de habitantes    
    private double extension; // en km2   
    private int posX, posY;   // Coordenadas en el mapa
    
    /**Constructor: crea un municipio del que solo se conoce su nombre
     * @param  nombre   Nombre del municipio
     */    
    public Municipio(String nombreM) { this(nombreM, 0, 0.0, 0, 0); }

    /**Crea un municipio del que se conocen todos sus atributos
     * @param  nombre   Nombre del municipio
     * @param pobl, numero de habitantes
     * @param ex, extensión en km2
     * @param pX, pY, coordenadas en el mapa
     */    
    public Municipio(String nom, int pobl, double ext, int pX, int pY) {
        this.nombre = nom;
        this.poblacion = pobl;
        this.extension = ext;
        this.posX = pX; this.posY = pY;
    }
    
    /**Devuelve el nombre del municipio
     * @return     Nombre el municipio
     */
    public String getNombre() { return nombre; }
    
    /** Devuelve la poblacion del municipio
     * @return poblacion del municipio (en numero de habitantes)
     */
    public int getPoblacion() { return poblacion; }
    
    /**Devuelve la extension del municipio
     * @return  extension el municipio (en kilometros cuadrados)
     */
    public double getExtension() { return extension; }
    
    /**Devuelve la coordenada horizontal del municipio en el mapa
     * @return  coordenada horizontal del municipio (en pixels)
     */
    public int getPosX() { return posX; }
    
    /**Devuelve la coordenada vertical del municipio en el mapa
     * @return  coordenada vertical del municipio (en pixels)
     */
    public int getPosY() { return posY; }
    
    
    /**Devuelve una descripcion del municipio actual
     * NO modificar el metodo pues sigue el formato de los ficheros de datos
     * @return     Cadena que describe el municipio actual
     */
    public String toString() {
        String res = nombre + ";" + poblacion + ";" 
            + extension + ";" + posX + ";" + posY;
        return res;
    }
    //para poder utilizarlos en una tabla Hash es necesario implementar los métodos equals y hasCode
    public boolean equals(Object c){
        return c instanceof Municipio && nombre.equals(((Municipio)c).getNombre());
    
    }
    public int hashCode(){
        return this.getNombre().hashCode();
    }
    //COMPLETAR LA CLASE CON LOS METODOS NECESARIOS PARA QUE
    //Municipio PUEDA INSTANCIAR LA CLASE DE LAS CLAVES DE UN
    //Map IMPLEMENTADO MEDIANTE UNA TablaHash
    
    
}
