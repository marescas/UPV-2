package aplicaciones.indices;

import librerias.estructurasDeDatos.modelos.Map;
import librerias.estructurasDeDatos.modelos.ListaConPI;
import librerias.estructurasDeDatos.deDispersion.TablaHash;
import librerias.estructurasDeDatos.lineales.LEGListaConPI;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import graph2D.Graph2D;
import java.awt.Color;
import java.awt.Font;

/**
 * Clase que genera las referencias cruzadas del vocabulario en un conjunto 
 * de libros.
 * Es decir, analiza los documentos para obtener información que permita  
 * consultar de forma eficiente en qué líneas de qué documentos aparece una 
 * determinada palabra.
 * Permite la siguiente funcionalidad:
 * 1.- Construye un Indexador a partir de un listado con los nombres de los
 *     documentos y del directorio para ubicarlos
 * 2.- Actualizar las referencias cruzadas (el Indexador) cuando se analiza un nuevo documento
 * 2.- Consultar en qué líneas de qué documentos aparece una determinada palabra
 * 3.- Muestra las estadisticas de la Tabla Hash que se utiliza: tamaño, factor de carga,
 *     desviacion tipica e histograma
 * 4.- ...
 * 
 * @author (EDA) 
 * @version (Curso 2016-2017)
 */
public class Indexador {
    public static final String SEPARADORES = "[[ ]*|[,]*|[\\.]*|[\t]*|[:]*|[;]*|[(]*|[)]*|[/]*|[!]*|[?]*|[¿]*|[“]*|[”]*|[+]*]+";
    protected static final int NUM_CUBETAS = 100000;
    protected Map<String, ListaConPI<Indice>> indicesBiblioteca;
    protected double tmp_carrega;
    /** Construye un indexador a partir de los documentos cuyos nombres se encuentran
     *  en listaLibros.
     *  @param   String listaLibros, nombre del fichero que contiene la lista de documentos
     *  @param   String dir, nombre del directorio donde se encuentras los documentos
     *  @throws  FileNotFoundException si no se encuentra algun fichero 
     */ 
    public Indexador(String listaLibros, String dir, int numCub) throws FileNotFoundException {
    if (numCub == -1) { numCub=NUM_CUBETAS; }

    boolean res = true;
    long t_total=0;
        indicesBiblioteca = new TablaHash<String, ListaConPI<Indice>>(numCub);      
    Scanner fich = new Scanner(new File(listaLibros));
    System.out.println("Cargando libros...");
    while (fich.hasNext()) {
        String nombreLibro = fich.next();
        long t1 = System.nanoTime();
        if (!cargarLibro(nombreLibro, dir))
        res = false;
        t_total += System.nanoTime() - t1;
    }
    tmp_carrega = t_total / 1000000.0;

    if (!res) {
        throw new FileNotFoundException();
    }
    }
    
    /** Actualiza el Indexador con los datos del documento nombreLibro que
     *  se encuentra en el directorio dir. Si no existe el documento, no hace nada.
     *  @param   String nombreLibro, nombre del fichero que contiene el documento
     *  @param   String dir, nombre del directorio donde se encuentra el documento
     *  @return  boolean, true si el libro se ha leido con exito y falso en caso contrario
     */ 
    private boolean cargarLibro(String nombreLibro, String dir)  {
    boolean res = true;
        try {
            Scanner libro = new Scanner(new File(dir + java.io.File.separator + nombreLibro));
            System.out.println("Cargando ..." + nombreLibro);
            int numLin = 0;
            while (libro.hasNext()) {
                String linea = libro.nextLine().toLowerCase();
                String[] words = linea.split(SEPARADORES);
                numLin++;
                for (int i = 0; i < words.length; i++) {
                    if (words[i].matches("[a-zA-Z]+")) {
                        ListaConPI<Indice> palEnHash = indicesBiblioteca.recuperar(words[i]);
                        Indice ind = new Indice(nombreLibro, numLin);
                        if (palEnHash == null) {
                            ListaConPI<Indice> pal = new LEGListaConPI<Indice>();
                            pal.insertar(ind);
                            indicesBiblioteca.insertar(words[i], pal);
                        } else {
                            palEnHash.insertar(ind);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
        System.err.println("Error "+dir+"/"+nombreLibro+ " no se encuentra");
        res = false;        
        }
    return res;
    }
    
    
    /**
     * Devuelve una ListaConPI con la representación textual
     * del libro y pagina (Indice) en los que aparece el termino
     * @param   String a buscar
     * @return ListaConPI<String>
     */
    public ListaConPI<String> buscarPalabra(String pal) {
        // COMPLETAR
        ListaConPI<String> lista = new LEGListaConPI<String>();
        lista.inicio();
        String aux = pal.toLowerCase();
        // Para la palabra "pal" pasada a minusculas, recuperar de 
        // la tabla la ListaConPI con sus indices.
        ListaConPI<Indice> listilla = indicesBiblioteca.recuperar(aux);
        
        // Tener en cuenta que si la palabra no existe la tabla nos 
        // devuelve null.
        if(listilla != null){
           listilla.inicio();
           while(!listilla.esFin()){
            lista.insertar(listilla.recuperar().toString());
            listilla.siguiente();
            }
        }
        // Recorrer la lista de indices recuperando los indices.
        // Convertir cada indice en un String usando su toString()
        // e insertarlos en la lista a devolver


        return lista;   
    }
    
    /**
     * Devuelve el valor maximo de un array
     * @param int [] 
     * @return int, valor maximo
     */
    private int getMax(int[] vect) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < vect.length; i++) {
            if (vect[i] > max) { max = vect[i]; }
        }
        return max;
    }
    
    /**
     * Genera un grafico con el histograma, factor de carga, desv. tipica y tiempo de carga
     */
    public void statistics() {
        final int PAD = 20;
        TablaHash<String, ListaConPI<Indice>> th = (TablaHash<String, ListaConPI<Indice>>) indicesBiblioteca;

        String histo = th.histograma();
        String[] histoLin = histo.split("\n");
        int[] data = new int[histoLin.length];
        for (int i = 0; i < histoLin.length; i++) {
            String[] lin = histoLin[i].split("\t");
            data[i] = Integer.valueOf(lin[1]);
        }
        final  int W_H = 400;
        final  int W_W = 600;
        final  boolean RESIZE = true;
        final  boolean MOUSE_POS = false;
    
        double xMin = -0.5, xMax = data.length + 1;
        double yMin = -10.0;
        double yMax = 100;
        int maxValue = 88;
        Graph2D gd = new Graph2D(xMin, xMax, yMin, yMax, W_W, W_H, Color.WHITE, "prova");
        gd.setResizable(RESIZE);
        gd.showAxes(false);
        gd.showMousePosition(MOUSE_POS);

        double scale = (yMax - 10) / getMax(data);
        int barWidth = 1;
    
        // Capcalera grafica:
        Font f1 = new Font("SansSerif", Font.BOLD, 13);
        
        // fi capcalera grafica.
        // dibuixar valors:
        gd.drawString(4, 92, Color.BLACK, String.format("Talla       %d", th.talla()), f1);
        gd.drawString(4, 89, Color.BLACK, String.format("Fact. carga %5.3f", th.factorCarga()), f1);
        gd.drawString(4, 86, Color.BLACK, String.format("Desv. tip.  %5.3f", th.desviacionTipica()), f1);
    gd.drawString(4, 83, Color.BLACK, String.format("Tmp. carga  %5.1fs", tmp_carrega/1000), f1);
    
        gd.drawLine(-0.5, 0, 12, 0, Color.BLACK, 1);
        gd.drawString(9.3, -5, Color.BLACK, "Num. Elem", f1);
        // dibuixar els punts i les linies            
        for (int i = 0; i < data.length; i++) {
            int valueY = 0;
            int height = (int) (data[i] * scale);
            gd.drawRect(i - 0.5, valueY, barWidth, -height, Color.BLACK, 1);
            gd.fillRect(i - 0.5, valueY, barWidth, -height);
            gd.drawString(i - 0.1, valueY - 5, Color.BLACK, "" + i, f1);
            if (data[i] > 0) {
                gd.drawString(i - 0.3, height, Color.BLACK, "" + data[i], f1);         
            }        
        }
    }    
}
