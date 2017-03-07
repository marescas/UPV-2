package librerias.util;
// Models i TIPUS de dades:
import librerias.estructurasDeDatos.modelos.ColaPrioridad;
import librerias.estructurasDeDatos.lineales.LPIColaPrioridad;
import librerias.estructurasDeDatos.jerarquicos.PriorityQColaPrioridad;
// Per al subsistema grafic:
import graph2D.Graph2D;
import java.awt.Color;
import java.awt.Font;
// Format d'eixida
import java.util.Locale;

/**
 * Temporitzacio de ColaPrioridad implementada mitjancant:
 * 1.- LPIColaPrioridad.
 * 2.- PriorityQColaPrioridad.
 * 
 * Obtencio de dades i representacio grafica per a les operacions:
 * a.- insertar,
 * b.- eliminarMin.
 * 
 * @author (EDA - ETSINF - DSIC - UPV)
 * @version (curso 2016-17)
 */
public class EficienciaCPGUI {
    private final static String RATLLA = "\u2500\u2500\u2500\u2500 ";
    private final static String MU = "\u00B5";
    
    private final static int ILPI = 0;
    private final static int IPQ = 1;
    private final static int ELPI = 2;
    private final static int EPQ = 3;

    private final static int MIN_TALLA = 1000;        
    private final static int MAX_TALLA = 7000;
    private final static int INC_TALLA = 1000;
    
    private final static int MAX_VALOR = MAX_TALLA * 1;
    
        
    private final static int MESURES = (MAX_TALLA - MIN_TALLA) / INC_TALLA + 1;
        
    private final static String[] TIPUS = {"Ins.LPI", "Ins.PQ", "Elim.LPI", "Elim.PQ"};
    
    private final static int REPS_LPI = 125;   // nombre repeticions operacions LPI
    private final static int REPS_PQ = 8000;  // nombre repeticions operacions PQ
    private final static int[] REPETS =   {REPS_LPI, REPS_PQ, REPS_LPI, REPS_PQ}; //
    private final static Color[] COL = {Color.BLUE, Color.RED, Color.DARK_GRAY, Color.MAGENTA};
    
    private final static int W_H = 400;
    private final static int W_W = 600;
    
    private final static boolean RESIZE = true;    
    private final static boolean MOUSE_POS = true;
    
    /** No existeix constructora publica. */
    private EficienciaCPGUI() { }
    
    /**
     * Generacion de un buffer de valores Integer aleatorios
     * en el rango [0 .. max] con distribucion uniforme.
     */
    private static Integer[] genera(int talla, int max) {
        Integer[] aux = new Integer[talla];
        
        for (int i = 0; i < talla; i++) { 
            aux[i] = new Integer((int) (Math.random() * max + 1));
        }        
        return aux;
    }
                
    /**
     * Insereix els elements del buffer en una ColaPrioridad<Integer>
     */
    private static void insertaTodos(Integer[] buf, 
                                    ColaPrioridad<Integer> cP) {        
        for (int i = 0; i < buf.length; i++) { 
            cP.insertar(buf[i]); 
        } 
    }
    
    /**
     * Elimina (un a un) els elements d'una ColaPrioridad<Integer>
     */
    private static void eliminaTodos(ColaPrioridad<Integer> cP) {       
        while (!cP.esVacia()) { cP.eliminarMin(); }
    }                       
        
    /**
     * Execucio temporitzada de les dues operacions.
     */
    private static void tInsertarEliminar() {                      
        // per a emmagatzemar totes les dades:
        double[][] mes = new double[MESURES][TIPUS.length];
        
        
        // Inicialitzacions llistat:
        String titol = "# Tiempo promedio de insertar y eliminarMin en una C.P. (" 
                       + MU + "segs.)";
        String sub =   "# ------------------------------------------------------";
        System.out.println(titol);
        System.out.printf(Locale.US, "# %5s %7s  %7s  %7s  %7s\n", "talla", 
                TIPUS[ILPI], TIPUS[IPQ], TIPUS[ELPI], TIPUS[EPQ]);
        System.out.println(sub);
                             
        Integer[] buff;
        int repTalla = 3;  // repTalla: nombre execucions TALLA_INI       
        // BUCLE PRICIPAL: per a cada talla ...
        for (int t = MIN_TALLA; t <= MAX_TALLA; t += INC_TALLA) {
            // quin nombre de mesura es:
            int numMes = (t - MIN_TALLA) / INC_TALLA;   
             
            for (int k = 1; k <= repTalla; k++) {
                // inicialitzacio acumuladors:
                for (int i = 0; i < TIPUS.length; i++) { mes[numMes][i] = 0; }
                                
                // temporitzacio LPI (insercio i eliminacio):
                for (int rep = 1; rep <= REPETS[ILPI]; rep++) {                      
                    buff = genera(t, 3 * t);
                    ColaPrioridad<Integer> pQ = 
                                        new LPIColaPrioridad<Integer>(); 
                    long t1 = System.nanoTime();
                    insertaTodos(buff, pQ);
                    long t2 = System.nanoTime();
                    eliminaTodos(pQ);
                    long t3 = System.nanoTime();
               
                    mes[numMes][ILPI] += (t2 - t1);
                    mes[numMes][ELPI] += (t3 - t2);
                }                         
                                                  
                // temporitzacio IPQ (insercio i eliminacio):
                for (int rep = 1; rep <= REPETS[IPQ]; rep++) {                   
                    buff = genera(t, 3 * t);
                    ColaPrioridad<Integer> pQ = 
                                        new PriorityQColaPrioridad<Integer>();
                    long t1 = System.nanoTime();
                    insertaTodos(buff, pQ);
                    long t2 = System.nanoTime();
                    eliminaTodos(pQ);
                    long t3 = System.nanoTime();
                    mes[numMes][IPQ] += (t2 - t1);
                    mes[numMes][EPQ] += (t3 - t2);
                }                  
                System.gc();
                try { Thread.sleep(100); } catch (Exception e) { ; }                
                                 
                if (k == repTalla) { repTalla = 1; }
            }
            
            // acumular temps d'execucio:
            for (int i = 0; i < TIPUS.length; i++) {
                mes[numMes][i] = mes[numMes][i] / 1e3 / REPETS[i] / t;
            }    
            // escriure en la taula de temps
            System.out.printf(Locale.US, "%7d %7.3f  %7.3f  %7.3f %7.3f\n", t, 
                              mes[numMes][ILPI], mes[numMes][IPQ], mes[numMes][ELPI], 
                              mes[numMes][EPQ]);
            // xAnt = t;                        
        } // calculs fets
               
        grafTot(mes);    // Graficacio de totes les corbes.
        grafInser(mes);  // Graficacio solament insercio.           
        grafElim(mes);   // Graficacio solament eliminacio.
    }
    
    /** Representacio grafica de tot ...*/
    private static void grafTot(double[][] mes) {
        double xMin = -10.0;
        double xMax = MAX_TALLA * 1.1;
        double yMin = -1.0;
        
        // Calcul del maxim:
        int pMax = 0;
        for (int i = 1; i < mes.length; i++) {
            if (mes[i][ILPI] > mes[pMax][ILPI]) { pMax = i; }
        }
        
        double yMax = mes[pMax][ILPI] * 1.05;
        
        String titol = "Tiempo promedio de insertar y eliminarMin en una C.P. (" 
                       + MU + "segs.)";     
                       
        Graph2D gd = new Graph2D(xMin, xMax , yMin, yMax, W_W, W_H, 
                                                            Color.WHITE, titol);
        gd.setResizable(RESIZE); 
        gd.showMousePosition(MOUSE_POS);        
        
        // Capcalera grafica:
        // Capcalera grafica:
        double posX1 = (xMax - xMin) / 20 + xMin;
        double posY1 = yMax - (yMax - yMin) / 30;
        Font f1 = new Font("SansSerif", Font.BOLD, 13);
        
        for (int i = 0; i < TIPUS.length; i++) {
            gd.drawString(posX1, posY1 - ((i * yMax) / 30.0), COL[i], 
                                                         RATLLA + TIPUS[i], f1);
        }
        // fi capcalera grafica.
        
        double xAnt = 0;
        // dibuixar valors:
        double t = MIN_TALLA;
        int numMes = 0;
        for (t = MIN_TALLA, numMes = 0; t <= MAX_TALLA; t += INC_TALLA, numMes++) {       
            // dibuixar els punts i les linies            
            for (int i = 0; i < 4; i++) {
                gd.drawPoint(t, mes[numMes][i], COL[i], 4);                    
                if (numMes != 0) { 
                    gd.drawLine(xAnt, mes[numMes - 1][i], 
                                t, mes[numMes][i], COL[i], 1);
                }
            }
            xAnt = t;  // mantindre la darrera mesura        
        }    
    }
                
    /** Representacio grafica solament insercio: */
    private static void grafInser(double[][] mes) {
        double xMin = -10.0;
        double xMax = MAX_TALLA * 1.1;
        double yMin = -1.0;
        
        // Calcul del maxim:
        int pMax = 0;
        for (int i = 1; i < mes.length; i++) {
            if (mes[i][ILPI] > mes[pMax][ILPI]) { pMax = i; }
        }
        
        double yMax = mes[pMax][ILPI] * 1.05;
                  
        String titol = "Tiempo promedio insertar en C.P. (" + MU + "segs.)";
        Graph2D gd = new Graph2D(xMin, xMax , yMin, yMax, W_W, W_H, 
                                                            Color.WHITE, titol);
        gd.setResizable(RESIZE); 
        gd.showMousePosition(MOUSE_POS);        
        
        // Capcalera grafica:
        double posX1 = (xMax - xMin) / 20 + xMin;
        double posY1 = yMax - (yMax - yMin) / 30;
        Font f1 = new Font("SansSerif", Font.BOLD, 13);
        
        for (int i = 0; i < 2; i++) {
            gd.drawString(posX1, posY1 - ((i * yMax) / 30.0), COL[i], 
                                                       RATLLA + TIPUS[i], f1);
        }
        // fi capcalera grafica.        
                
        double xAnt = 0;
        // dibuixar valors:
        double t = MIN_TALLA;
        int numMes = 0;
        for (t = MIN_TALLA, numMes = 0; t <= MAX_TALLA; t += INC_TALLA, numMes++) {       
            // dibuixar els punts i les linies            
            for (int i = 0; i < 2; i++) {
                gd.drawPoint(t, mes[numMes][i], COL[i], 4);                    
                if (numMes != 0) { 
                    gd.drawLine(xAnt, mes[numMes - 1][i], 
                                t, mes[numMes][i], COL[i], 1);
                }
            }
            xAnt = t;  // mantindre la darrera mesura        
        }          
    }
                    
    /** Representacio grafica solament eliminacio: */
    private static void grafElim(double[][] mes) {
        double xMin = -10.0;
        double xMax = MAX_TALLA * 1.1;
        double yMin = 0;
        
        // Calcul del maxim:
        int pMax = 0;
        for (int i = 1; i < mes.length; i++) {
            if (mes[i][EPQ] > mes[pMax][EPQ]) { pMax = i; }
        }
        
        double yMax = mes[pMax][EPQ] * 1.05;        
                  
        String titol = "Tiempo prom. eliminarMin en C.P. (" + MU + "segs.). Gráfica reescalada.";
        Graph2D gd = new Graph2D(xMin, xMax , yMin, yMax, W_W, W_H, 
                                                          Color.WHITE, titol);
        gd.setResizable(RESIZE); 
        gd.showMousePosition(MOUSE_POS);                
        
        // Capcalera grafica:
        double posX1 = (xMax - xMin) / 20 + xMin;
        double posY1 = yMax - (yMax - yMin) / 30;
        Font f1 = new Font("SansSerif", Font.BOLD, 13);
        
        for (int i = 0; i < 2; i++) {
            gd.drawString(posX1, posY1 - ((i * yMax) / 30.0), COL[i + 2], 
                                                     RATLLA + TIPUS[i + 2], f1);
        }
        // fi capcalera grafica.        
        
        double xAnt = 0;
        // dibuixar valors:
        double t = MIN_TALLA;
        int numMes = 0;
        for (t = MIN_TALLA, numMes = 0; t <= MAX_TALLA; t += INC_TALLA, numMes++) {       
            // dibuixar els punts i les linies            
            for (int i = 2; i < 4; i++) {
                gd.drawPoint(t, mes[numMes][i], COL[i], 4);                    
                if (numMes != 0) { 
                    gd.drawLine(xAnt, mes[numMes - 1][i], 
                                t, mes[numMes][i], COL[i], 1);
                }
            }
            xAnt = t;  // mantindre la darrera mesura        
        }          
    }
    
    public static void main(String[] args) {
        tInsertarEliminar();
    }
}
