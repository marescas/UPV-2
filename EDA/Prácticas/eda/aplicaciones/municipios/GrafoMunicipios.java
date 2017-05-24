package aplicaciones.municipios;

import librerias.estructurasDeDatos.grafos.GrafoDirigido;
import librerias.estructurasDeDatos.grafos.Adyacente;
import java.util.Scanner;
import librerias.estructurasDeDatos.modelos.ListaConPI;
import librerias.estructurasDeDatos.modelos.Map;
import librerias.estructurasDeDatos.lineales.LEGListaConPI;
import librerias.estructurasDeDatos.deDispersion.TablaHash;
import java.io.File;
import java.io.FileNotFoundException;

/**Grafo de municipios: TIENE UN Grafo en el que cada vertice se asocia
 * con un Municipio y como funcionalidad las operaciones para iniciarlo  
 * a partir de los datos de un fichero de municipios y distancias y la  
 * busqueda de caminos minimos entre dos vertices.
 * 
 * @author (profesores EDA) 
 * @version (Curso 2016/17)
 */    
public class GrafoMunicipios {
    
    public final static String FICH_MUNICIPIOS = "aplicaciones" + File.separator
        + "municipios" + File.separator + "municipios.txt";
        
    private static final int NUM_MAX_MUNICIPIOS = 5000;
    // Numero estimado de municipios
   
    private static final String FICH_DISTS = "aplicaciones" + File.separator
        + "municipios" + File.separator + "distancias.txt";
    
    private static final String NO_ACC_MSG = "El fichero de municipios no es "
        + "accesible para lectura, compruebe su correcta ubicaci\u00f3n";
    private static final String NO_FOR_MSG = "Formato no v\u00e1lido en "
        + "la l\u00ednea: ";
    private static final String NO_FDIS_MSG = "Fichero de distancias "
        + "no encontrado";    
    
    // COMPLETAR:
    // 1. DEFINIR UN Map PARA ASOCIAR A CADA INDICE (VERTICE) UN Municipio
    // 2. DEFINIR UN Map PARA ASOCIAR A CADA Municipio UN INDICE
    protected Map<Integer,Municipio> ver2Muni; 
    protected Map<Municipio,Integer> muni2Ver;
    
    private GrafoDirigido grafo;
   
    /**Constructor: crea el grafo y lo inicializa con los datos contenidos en 
     * los ficheros de municipios y distancias
     */    
    public GrafoMunicipios() {
        // COMPLETAR: inicializa los atributos ver2Muni y muni2Ver
        ver2Muni = new TablaHash<>(NUM_MAX_MUNICIPIOS);
        muni2Ver = new TablaHash<>(NUM_MAX_MUNICIPIOS);
        try {         
            Scanner fent = new Scanner(new File(FICH_MUNICIPIOS), "UTF-8"); 
            int indice = 0;
            while (fent.hasNext()) {
                String linea = fent.nextLine();
                String[] lA = linea.split(";");
                String nombre = lA[0].toLowerCase();
                int poblacion = Integer.parseInt(lA[1]);
                double ext = Double.parseDouble(lA[2]);
                int posX = Integer.parseInt(lA[3]);
                int posY = Integer.parseInt(lA[4]);
                Municipio m = new Municipio(nombre, poblacion, ext, posX, posY);
                //COMPLETAR: insertar (indice, m) en ver2Muni y (m, indice) en muni2Ver
                ver2Muni.insertar(indice, m);
                muni2Ver.insertar(m, indice);

                indice++;
            }
            fent.close();
            grafo = new GrafoDirigido(ver2Muni.talla());
            cargarAristas();
        } catch (java.io.IOException eChecked) {
            System.out.println(NO_ACC_MSG);
        }
    }

    // Inserta las aristas contenidas en el fichero de distancias en el grafo 
    private void cargarAristas() {        
        try {
            Scanner f = new Scanner(new File(FICH_DISTS), "UTF-8");
            while (f.hasNext()) {
                String linea = f.nextLine();
                String[] datosCarretera = linea.split(";");
                if (datosCarretera.length != 3) {
                    System.out.println(NO_FOR_MSG  + linea);
                    break;
                }
                String orig = datosCarretera[0].trim().toLowerCase();
                String dst = datosCarretera[1].trim().toLowerCase();
                double distancia = Double.parseDouble(datosCarretera[2]);
                int origen = obtenerCodigo(new Municipio(orig)),
                    destino = obtenerCodigo(new Municipio(dst));
                grafo.insertarArista(origen, destino, distancia);
                grafo.insertarArista(destino, origen, distancia);
            }
        } catch (FileNotFoundException e) {
            System.err.println(NO_FDIS_MSG);
        }
    }
    
    /**Recupera el codigo de un vertice a partir del municipio. 
     * Devuelve -1 si no existe un vertice con dicho municipio
     */
    public int obtenerCodigo(Municipio m) {
        //COMPLETAR       
        return muni2Ver.recuperar(m);
        
        
    }

    /**Consulta el numero de municipios que contiene el grafo
     * @return     Numero de municipios del grafo
     */    
    public int numMunicipios() { return grafo.numVertices(); }
    
    /**Recupera los datos de un municipio del grafo
     * @param   indice  indice interno del vertice
     * @return          Municipio asociado a dicho vertice
     */    
    public Municipio getMunicipio(int indice) { 
        //COMPLETAR
        return ver2Muni.recuperar(indice);
        
    }
    
    /**Devuelve el numero de aristas recuperadas del fichero de distancias
     * En el grafo el numero de aristas sera el doble pues se insertan 
     * las aristas simetricas
     * @return     Numero de aristas
     */    
    public int numAristas() { return grafo.numAristas(); }

    /**Devuelve la lista de municipios adyacentes
     * @param  indice del municipio
     * @return ListaConPI con los municipios adyacentes al municipio indice
     */  
    public ListaConPI<Adyacente> adyacentesDe(int indice) {
        return grafo.adyacentesDe(indice);
    }
    
    /**Comprueba si existe un municipio en el grafo
     * @param  m   Municipio a buscar
     * @return True si dicho municipio esta en el grafo
     */
    public boolean existeMunicipio(Municipio m) { 
        //COMPLETAR
        return (muni2Ver.recuperar(m)!=null) ? true:false;
        
    }

    /** Recupera el coste de una arista
     * @param  m1   Municipio origen de la arista
     * @param  m2   Municipio destino de la arista
     * @return Coste de la arista (o -1 si dicha arista no existe en el grafo)
     */
    public double distancia(Municipio m1, Municipio m2) {
        int origen = muni2Ver.recuperar(m1);
        int destion = muni2Ver.recuperar(m2);
        
        return grafo.pesoArista(origen, destion);
    }

    /**Calcula el camino minimo entre dos municipios
     * @param   mOrig   Municipio origen del camino
     * @param   mDst    Municipio destino del camino
     * @return  Camino minimo en forma de ListaConPI de municipios
     */ 
    public ListaConPI<Municipio> caminoMinimo(Municipio mOrig, Municipio mDst) {    
        ListaConPI<Municipio> camino = new LEGListaConPI<Municipio>();
        // COMPLETAR
        ListaConPI<Integer> l =grafo.caminoMinimo(obtenerCodigo(mOrig), obtenerCodigo(mDst));
        l.inicio();
        while(!l.esFin()){
            camino.insertar(getMunicipio(l.recuperar()));
            l.siguiente();
        }
        
        
        
        return camino;  
    }
}
