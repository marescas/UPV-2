package librerias.estructurasDeDatos.lineales;

// comprueba que esta en el lugar correcto
// package librerias.estructurasDeDatos.lineales;

import librerias.estructurasDeDatos.modelos.ListaConPI;

/** Implementa la interfaz ListaConPI mediante una LEG ...
 *  (a) Con Nodo ficticio cabecera .
 *  (b) Una referencia al primer Nodo  .
 *  (c) Una referencia al ultimo Nodo.
 *  (d) Para representar el Punto de Interes, una referencia al Nodo 
 *       anterior al que ocupa el punto de interes.
 *  (e) Un int talla que representa la talla de la LEG.
 *
 *  @version Febrero 2014 *  
  * @param <E> tipo de datos de la estructura
 */ 


public class LEGListaConPI<E> implements ListaConPI<E> {

    protected NodoLEG<E> pri, ant, ult; 
    
    // En principio, una LEGListaConPI NO tendria una talla ...
    // PERO se incluye pensando en la futura reutilizacion del Modelo
    protected int talla;
    
    /** construye una Lista Con PI vacia **/
    public LEGListaConPI() {
        pri = ult = ant = new NodoLEG<E>(null);
        talla = 0;
    }
    
    /** inserta e en una Lista antes del Elemento que ocupa su PI, 
      * que permanece inalterado.
      *
      * @param e Elememto a insertar.
      **/
    public void insertar(E e) { 
        NodoLEG<E> nuevo = new NodoLEG<E>(e); talla++;
        // Se inserta nuevo DETRAS de ant, 
        // i.e. ANTES del Dato que ocupa el PI
        /*COMPLETAR*/
        nuevo.siguiente = ant.siguiente;
        ant.siguiente = nuevo;
        
        // OJO: al insertar en fin de Lista ... actualizar ult!
        /*COMPLETAR*/
        if(nuevo.siguiente == null){
          ult = nuevo;
          }
        // OJO: tras la insercion el PI de la Lista permanece 
        // inalterado
        /*COMPLETAR*/       
        ant = nuevo;
    } 
    

    /** SII !esFin(): elimina de una Lista el Elemento que ocupa 
      * su PI que permanece inalterado. 
      **/  
    public void eliminar() {
        talla--;
        // OJO: al eliminar el ultimo Elemento ... actualizar ult!; 
        // el PI esta en fin()
        /*COMPLETAR*/
       
        ant.siguiente = ant.siguiente.siguiente;
        
       
         if(ant.siguiente == null){
             
             ult = ant;
            }
        
    }
    
    /** situa el PI de una Lista en su inicio, sobre su primer 
      * elemento si no esta vacia.
      **/
    public void inicio() { if(!esVacia()) {ant = pri;}}
    
    /** SII !esFin(): situa sobre el siguiente Elemento el PI 
      * de una Lista.  
      **/   
    public void siguiente() { ant = ant.siguiente;}
    
    /** situa el PI de una Lista en su fin, detras de su ultimo 
      * Elemento si no esta vacia. 
     **/
    public void fin() { 
       if(!esVacia()){
        ant = ult;
        }
       }
    
    /** SII !esFin(): obtiene el Elemento que ocupa el PI de 
      * una Lista.
      *
      * @return E, el Elemento que ocupa el PI de una Lista.
     */
    public E recuperar() { 
       if(!esFin()){
        return ant.siguiente.dato;
        }
        return null;
    }
    
    /** comprueba si el PI de una Lista esta en su fin.
      *
      *  @return true si el PI de una Lista esta en su fin y 
      * false en caso contrario
      **/
    public boolean esFin() { 
        boolean res = false;
        res = ant.siguiente == null;
        return res;
    }
    /** comprueba si una Lista Con PI esta vacia
      *
      *  @return true si una Lista Con PI esta vacia y 
      * false en caso contrario
      **/
    public boolean esVacia() { 
        boolean res = false;
        res = talla == 0;
        return res;
    }
    
    /** devuelve la talla de una Lista Con PI, i.e. su 
      * numero de elementos.
      *
      * @return int, el numero de Elementos de una Lista con PI.
      **/
     public int talla() { return talla; }
    
    /** devuelve el String con los Elementos de una Lista con PI 
      * en orden de insercion.
      * 
      * @return String que contiene los Elementos de una Lista con 
      * PI, en el mismo formato que usa el estandar de Java para 
      * los arrays.
      **/
    public String toString() {
        // NOTA: se usa la clase StringBuilder, en lugar de String, 
        // por motivos de eficiencia 
        StringBuilder s = new StringBuilder();
        s.append("[");
        NodoLEG<E> aux = pri.siguiente;         
        for (int i = 1; i < talla; i++, aux = aux.siguiente) {            
            s.append(aux.dato.toString() + ", ");
        }
        if (talla != 0) {
            s.append(aux.dato.toString() + "]"); 
        } else { s.append("]"); }
        return s.toString();
    }
}
