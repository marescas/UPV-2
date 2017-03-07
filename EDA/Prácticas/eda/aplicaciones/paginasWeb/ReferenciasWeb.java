package aplicaciones.paginasWeb;

import java.net.URL;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
/**
 * Clase que representa el número de referencias http que contiene una 
 * página web.
 * 
 * @author (EDA) 
 * @version (Curso 2016-2017)
 */
public class ReferenciasWeb implements Comparable<ReferenciasWeb>{
  
    private static String URL_FORMAT = "href=\"(.*http[^\"]*)\"";
    //[^\"]*  significa la repetición (0 o más veces) de cualquier
    //caracter que no sea las comillas dobles (").
    private static Pattern PATTERN = Pattern.compile(URL_FORMAT);
    private String url;
    private int nohref;
    private int nolineas;
    
    /**
     * Construye un objeto ReferenciasWeb a partir de la dirección
     * url de una página, del número de líneas (nolineas) y del número 
     * de referencias externas (nohref).
     * @param  nomPag   url de la página
     * @param noLineas  número de lineas de la pagina
     * @param noHRef    número de referencias externas
     */   
    public ReferenciasWeb(String nomPag, int noLineas, int noHRef) {
        url = nomPag; nolineas = noLineas; nohref = noHRef; 
    }
    
    /**
     * Construye un objeto ReferenciasWeb a partir de la dirección
     * url de una página: cuenta el número de líneas (nolineas) y el número 
     * de referencias externas (nohref).
     * @param  nomPag   url de la página
     * @throws MalFormedURLException, si la URL no es válida
     * @throws IOException, si no se puede conectar o acceder a la url
     */   
    public ReferenciasWeb(String nomPag) 
        throws MalformedURLException, IOException {
        url = nomPag; nohref = 0; nolineas = 0;
        URL dir = new URL(nomPag);
        InputStream html = dir.openStream();
        Scanner htmlsc = new Scanner(html);            
        int nol = 1, noref = 0;
        while (htmlsc.hasNext()) {
            String linea = htmlsc.nextLine();
            Matcher matcher = PATTERN.matcher(linea);
            while (matcher.find()) {
                noref++;
            }
            nol++;
        }
        nohref = noref; nolineas = nol;                               
    }

    /**
     * Devuelve la dirección url de la página
     * @return String, la url
     */
    public String getUrl() { return url; }
    
    /**
     * Devuelve el número de referencias externas de la página
     * @return int, el número de referencias externas
     */
    public int getNohref() { return nohref; }
    
    /**
     * Devuelve el número de líneas de la página
     * @return int, el número de líneas
     */
    public int getNolineas() { return nolineas; }
    
    /**
     * Comprueba si una ReferenciaWeb es igual a otra:
     * son iguales si tienen la misma url 
     * @param o, la ReferenciaWeb a comparar
     * @return true, si tienen la misma url y false en caso contrario
     */
    public boolean equals(Object o) {
        boolean res = false;
        if (o instanceof ReferenciasWeb) {
            ReferenciasWeb p = (ReferenciasWeb) o;
            res = url.equals(p);
        }
        return res;
    }
    
    /**
     * Compara dos ReferenciaWeb con el fin de su recomendación: this y ref
     * Criterio: es más recomendable la página que contenga más referencias y,  
     * a igualdad de referencias, la que contenga menos líneas.
     * 
     * @param ref, la ReferenciaWeb a comparar
     * @return  negativo si this es más recomendable que ref, 
     *          positivo si ref es más recomendable que this
     *          0 si son igualmente recomendables
     */
    public int compareTo(ReferenciasWeb ref) {
        if(this.getNohref()>ref.getNohref()){
            return -1;
        }else if(ref.getNohref()>this.getNohref()){
            return 1;
        }else if(ref.getNohref()==this.getNohref()){
            if(this.getNolineas()<ref.getNolineas()){
                return -1;
            }else if(ref.getNolineas()<this.getNolineas()){
                return 1;
            }
        }
        return 0;
    }
    
    /**
     * Devuelve la información textual asociada a una ReferenciasWeb
     * @return  String con la dirección url, el número de líneas y el número de referencias.
     *          Ejemplo: "url:   https://www.upv.es - Total lineas =   571 - Total hrefs =    21"
     */
    public String toString() {
        String res = "";
        if (nolineas != 0) {
            res = String.format("url: %20s - Total lineas = %5d - Total hrefs = %5d\n", 
                                 url, nolineas, nohref);
        } else { res = String.format("url: %20s \n", url); }
        return res;
    }
}

