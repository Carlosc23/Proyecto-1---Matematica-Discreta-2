package Proyecto;
import java.util.ArrayList;
/**
* @author Julio Barahona, 141206
* @author Carlos Calderon, 15219
* @author Diego Castaneda, 15151
* Clase que representa un Grafo.
*/
public class Grafo {
	
	/*Atributos*/
    private int ancho;
    private int alto;
    private int[] diagonalesVertices = null;
    private ArrayList<ArrayList<Vertice>> grafo; 

    /**
     * @param w	ancho
     * @param h	alto
     * @param diagonalesVertices	diagonales
     * Constructor que inicializa el grafo
     */
    public Grafo(int w, int h, int[] diagonalesVertices) {
        this.ancho = w;
        this.alto = h;
        this.diagonalesVertices = diagonalesVertices;
        crearGrafo();
    }

    /**
     * @param x	ancho 
     * @param y	alto
     * @return	El vertice correspondiente
     */
    public Vertice getVertice(int x, int y) {
        return grafo.get(y).get(x);
    }
    
    /**
     * Metodo que crea el grafo
     */
    private void crearGrafo() {
        Vertice Vertice;
        grafo = new ArrayList<>();
        for (int y = 0; y < alto; y++) 
        {
            ArrayList temp= new ArrayList();
            grafo.add(temp);
            for (int x = 0; x < ancho; x++)
            {
                Vertice = new Vertice(x, y, this);
                grafo.get(y).add(Vertice);
            }
        }
        
        int contador = 0;
        for (int y = 0; y < alto; y++) 
        {
            for (int x = 0; x < ancho; x++)
            {
                this.getVertice(x, y).setDistribucion(diagonalesVertices[contador]);
                contador++;
            }
        }
        
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public ArrayList<ArrayList<Vertice>> getGrafo() {
        return grafo;
    }

    public void setGrafo(ArrayList<ArrayList<Vertice>> grafo) {
        this.grafo = grafo;
    }

    public int[] getDiagonalesVertices() {
        return diagonalesVertices;
    }

    public void setDiagonalesVertices(int[] diagonalesVertices) {
        this.diagonalesVertices = diagonalesVertices;
    }
    
    /**
     * Crea los obstaculos en base a booleanos para pintar o no pintar en
     * el GUI.
     */
    public void crearOstaculo() {       
    	grafo.get(4).get(4).setIsObstaculo(true);
        grafo.get(4).get(5).setIsObstaculo(true);
        grafo.get(4).get(6).setIsObstaculo(true);
        grafo.get(4).get(7).setIsObstaculo(true);
        grafo.get(4).get(8).setIsObstaculo(true);
        grafo.get(4).get(9).setIsObstaculo(true);
        grafo.get(4).get(10).setIsObstaculo(true);
        
        grafo.get(5).get(9).setIsObstaculo(true);
        grafo.get(5).get(4).setIsObstaculo(true);
        
        grafo.get(6).get(8).setIsObstaculo(true);
        grafo.get(7).get(7).setIsObstaculo(true);
       
        

        grafo.get(8).get(6).setIsObstaculo(true);
    
        
        grafo.get(9).get(5).setIsObstaculo(true);
       
        grafo.get(10).get(5).setIsObstaculo(true);
        grafo.get(10).get(6).setIsObstaculo(true);
        grafo.get(10).get(7).setIsObstaculo(true);
        grafo.get(10).get(8).setIsObstaculo(true);
        grafo.get(10).get(9).setIsObstaculo(true);
        grafo.get(10).get(10).setIsObstaculo(true);
        
        grafo.get(9).get(10).setIsObstaculo(true);
    }
     
}
