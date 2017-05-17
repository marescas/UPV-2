package librerias.estructurasDeDatos.grafos;


/**
 * Write a description of class ParVC here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ParVC implements Comparable<ParVC>
{
    private int vertice;
    private double coste;
    public ParVC(int v, double c){
        vertice = v;
        coste = c;
    }
    public int getV(){
        return vertice;
    }
    public double getCoste(){
        return coste;
    }
    public int compareTo(ParVC e){
        if(coste > e.getCoste())return 1;
        else if(e.getCoste()<coste)return -1;
        else return 0;
    }
}
