package Proyecto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
/**
* @author Julio Barahona, 141206
* @author Carlos Calderon, 15219
* @author Diego Castaneda, 15151
* Inspirado en :  http://theory.stanford.edu/~amitp/GameProgramming/Heuristics.html#diagonal-distance
* Clase que representa implementa algoritmos A* y Dijktra. 
*/


public class Astar {
	/*Atributos*/
    private final Grafo grafo;
    private final Vertice inicio;
    private final Vertice destino;
    private final List<Vertice> camino = new ArrayList<>();
    private  Set<Vertice> VerticesEvaluados;
    private PriorityQueue<Vertice> VerticesPorEvaluar;
    public Astar(int ancho, int alto, int[] arregloDistri) 
    {
        this.grafo = new Grafo(ancho, alto, arregloDistri);
        this.inicio = new Vertice(0, alto-1, grafo);
        this.destino = new Vertice(ancho-1, 0, grafo);
        
        
    }

    /**
     * @param diagonales boolean para ver si si el laberinto tiene diagonales
     * @param dijkstra	boolean para ver si hace dijsktra o A*
     * @param Peso		boolean para saber si es un grafo con peso
     * Funcion con 2 colas con prioridad de vertices evaluados y los que faltan por evaluar.
     * Luego se efectua un ciclo, mientras los vertices por evaluar no esten vacios. Se obtiene el vertice con
     * menor funcion f y en caso de encontrar el destino se termina. Sino, entonces se cambia la estructura
     * para evaluar el vertice actual
     * Si dijkstra fue seleccionado, la funcion heuristica es 0.
     * 
     */
    public void calcular(boolean diagonales,boolean dijkstra, boolean Peso) 
    {
        VerticesEvaluados= new HashSet<>(); 
        VerticesPorEvaluar = new PriorityQueue<>();
        inicio.setFuncionG(0); 
        VerticesPorEvaluar.add(inicio); 
        int contadorIteraciones=0;
        while (!VerticesPorEvaluar.isEmpty())
        {
            Vertice actual = VerticesPorEvaluar.poll();
            if (actual.equals(destino)){  
                System.out.println("Iteraciones totales-> " + contadorIteraciones);
                System.out.println("Costo Total-> " + actual.getFuncionG()+actual.getFuncionHeuristica());
                reconstruirCamino(actual);
                break;
            }
            VerticesPorEvaluar.remove(actual);
            VerticesEvaluados.add(actual);

            /*Ciclo para obtener los Vertices adyacentes del Vertice actual*/
            for (Vertice adyacente : actual.getVerticesAdyacente(diagonales)) {
            	
            	/* En caso de que un vertice ya fue evaluado, se omite el ciclo*/
                if (VerticesEvaluados.contains(adyacente))
                    continue; 
                if (!adyacente.isObstaculo()) {
                    double nuevoCosto = actual.getFuncionG() + getDistanciaEntre(actual, adyacente, Peso);
                    //se evalua si el Vertice adyacente tiene un menor costo
                    
                    if (!VerticesPorEvaluar.contains(adyacente)||nuevoCosto < adyacente.getFuncionG()){
                        adyacente.setRaiz(actual); //anadir el camino nuevo
                        adyacente.setFuncionG(nuevoCosto); 
                        adyacente.setFuncionHeuristica(calcularHeuristica(adyacente, destino,diagonales, Peso));
                        
                        if (dijkstra){
                            adyacente.setFuncionHeuristica(0);
                        }
                        //la cola prioritaria ordena los Vertices cada vez que se inserta un Vertice
                        //el Vertice con menor funcion f es el primero
                        VerticesPorEvaluar.add(adyacente);//se anade hasta de ultimo el Vertice adyacente
                    }
                   
                }
            }
            contadorIteraciones++;
        }
    }
    /**
     * @param 	Vertice inicial
     */
    public void reconstruirCamino(Vertice Vertice){
        //grafo.getGUI(); 
        while (!(Vertice.getRaiz() == null)) 
        {
            camino.add(Vertice);
            Vertice = Vertice.getRaiz();
        }
        camino.add(Vertice);
        Collections.reverse(camino); 
        System.out.println("");
        System.out.println(camino.toString() + " ->Camino mas corto");
        
    }
    
    /**
     * @param n1	Vertice 1
     * @param n2	Vertice 2
     * @param Peso	Peso entre vertices
     * @return		La distancia entre vertices
     */
    public double getDistanciaEntre(Vertice n1, Vertice n2, boolean Peso) {
        if(n1.getX() == n2.getX()){
            if(Peso)
            {
                System.out.println("y Vertice 1: " + n1.getY());
                System.out.println("y Vertice 2: " + n2.getY());
                System.out.println("Distancia: " + String.valueOf(n1.getX()+n2.getY()));
                return n1.getX()+n2.getY();
            }
            else
            {
                return 1;
            }
        }
        if(n1.getY() == n2.getY())
        {
            if(Peso)
            {
                
                return 1.0;
            }
            else
            {
                return 1;
            }
        }
           
        else
        {
            return Math.sqrt(2); 
        }
            
    }
    
    
    /**
     * @param actual	vertice actual
     * @param destino	vertice objetivo
     * @param diagonales boolean para ver si el grafo tiene diagonales
     * @param Peso		boolean para ver si el grafo tiene peso
     * @return	
     */
    public double calcularHeuristica(Vertice actual, Vertice destino, boolean diagonales, boolean Peso) {
       
        double D = 1.0; //Peso de aristas adyacentes
        if(Peso)//Si el peso es variable
        {
           
            D = actual.getX()+destino.getY();
        }
        double D2 = Math.sqrt(2); //peso de arista diagonales
        //Manhattan de 4
        double dx = Math.abs(actual.getX()-destino.getX());
        double dy= Math.abs(actual.getY()-destino.getY());
        double dx1 = actual.getX()-destino.getX();
        double dy1 = actual.getY()-destino.getY();
        double p = 1/1000;
        double dw = inicio.getX()-destino.getX(); 
        double dz = inicio.getY()-destino.getY(); 
    
  
        double promedio = ((D*(dx+dy)+(D2-2*D)*Math.min(dx,dy))+(D*(dx+dy)))/2;
        if (diagonales)
        {
            return promedio;
        }
        return (D*(dx+dy))+Math.abs(dx1*dz-dw*dy1); 
       
    }

    /**
     * @return	Vertices evaluados 
     */
    public Set<Vertice> getVerticesEvaluados() {
        return VerticesEvaluados;
    }

    /**
     * @return	grafo
     */
    public Grafo getGrafo() {
        return grafo;
    }
    /**
     * @return el camino de vertices
     */
    public List<Vertice> getcamino() {
        return camino;
    }
}

