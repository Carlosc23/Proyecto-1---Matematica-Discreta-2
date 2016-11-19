package Proyecto;

import java.util.ArrayList;
import java.util.Random;
/**
* @author Julio Barahona, 141206
* @author Carlos Calderon, 15219
* @author Diego Castaneda, 15151
* Clase que representa un Vertice.
*/


public class Vertice implements Comparable<Vertice> {
    private boolean Obstaculo;
    private Vertice raiz;
    private Grafo grafo;
    private int x;
    private int y;
    private int distribucion;
    private double funcionHeuristica;//costo del camino Vertice actual al final -> Greedy
    private double distanciaDesdeInicio; //funcion G:costo del mejor camino encontrado->Dijsktra
 
    public Vertice(int x, int y, Grafo grafo) 
    {
        this.x = x;
        this.y = y;
        this.Obstaculo = false;
        this.distanciaDesdeInicio=Integer.MAX_VALUE; //al principio los Vertices tienen costo infinito
        this.grafo = grafo;
    }
    //método para comparar igualdad entre Vertices
    public boolean equals(Vertice Vertice) 
    {
        return (this.x == Vertice.x) && (this.y == Vertice.y);
    }
    //método para calcular los Vertices adyacentes de un Vertice
    
    public void asignarDiagonal(int diagonal)
    {
        distribucion = diagonal;
    }
    
    public ArrayList<Vertice> getVerticesAdyacente(boolean diagonales) 
    {
    //si es con diagonales se crea un número aleatorio con probabilidad 1/2
        
        
        ArrayList<Vertice> VerticesAdyacentes = new ArrayList<>(); //lista para meter los Vertices adyacentes
        //verificar los Vertices con x constantes y y hacia abajo
        if ((y != 0)) 
        {
            VerticesAdyacentes.add(grafo.getVertice(x, (y - 1)));
        }
        
       //verificar los Vertices con x hacia la derecha y y constante
        if ((x != (grafo.getAncho() - 1))) 
        {
            VerticesAdyacentes.add(grafo.getVertice(x + 1, y));
        }
        //verificar los Vertices en diagonal
        if (diagonales)
        {
            if (distribucion ==1)
            {
                //diagonales hacia derecha y arriba
                 if ((y != 0) && !(x == (grafo.getAncho() - 1))) 
                {
                    VerticesAdyacentes.add(grafo.getVertice(x + 1, y - 1));
                }
                //diagonales hacia  izquierda y abajo
                if ((x != 0) && (y != (grafo.getAlto() - 1)))
                {
                    VerticesAdyacentes.add(grafo.getVertice(x - 1, y + 1));
                } 
            }
            if (distribucion ==2)
            {
                //diagonales hacia abajo y derecha
                if ((x != (grafo.getAncho()- 1)) && !(y == (grafo.getAlto() - 1))) 
                {
                    VerticesAdyacentes.add(grafo.getVertice(x + 1, y + 1));
                }
                //diagonales hacia izquierda y arriba
                if ((x != 0) && (y != 0)) 
                {
                    VerticesAdyacentes.add(grafo.getVertice(x - 1, y - 1));
                }
            }
        }
        //verificar los Vertices con x constante y y hacia arriba
        if ((y != (grafo.getAlto() - 1)))
        {
            VerticesAdyacentes.add(grafo.getVertice(x, y + 1));
        }
        
       //verificar los Vertices x hacia abajo y y constante
        if ((x != 0)) 
        {
            VerticesAdyacentes.add(grafo.getVertice(x - 1, y));
        }
        
        return VerticesAdyacentes;
    }
    public boolean isObstaculo() 
    {
        return Obstaculo;
    }

    public void setIsObstaculo(boolean Obstaculo) 
    {
        this.Obstaculo = Obstaculo;
    }

    public Vertice getRaiz() 
    {
        return raiz;
    }

    public void setRaiz(Vertice raiz) 
    {
        this.raiz = raiz;
    }

    public Grafo getGrafo() 
    {
        return grafo;
    }

    public void setGrafo(Grafo grafo) 
    {
        this.grafo = grafo;
    }

    public int getX() 
    {
        return x;
    }

    public void setX(int x) 
    {
        this.x = x;
    }

    public int getY() 
    {
        return y;
    }

    public void setY(int y) 
    {
        this.y = y;
    }

    public double getFuncionHeuristica() 
    {
        return funcionHeuristica;
    }

    public void setFuncionHeuristica(double funcionHeursitica) 
    {
        this.funcionHeuristica = funcionHeursitica;
    }

    public double getFuncionG() 
    {
        return distanciaDesdeInicio;
    }

    public void setFuncionG(double funcionG) 
    {
        this.distanciaDesdeInicio = funcionG;
    }

    @Override//método que sirve para ordenar la cola prioritaria según esta comparación
    public int compareTo(Vertice other) 
    {
        double totalDistanceFromGoal = this.distanciaDesdeInicio + this.funcionHeuristica;
        double otherDistanceFromGoal = other.distanciaDesdeInicio + other.funcionHeuristica;
        if (totalDistanceFromGoal < otherDistanceFromGoal)
        {
            return -1;
        }
                
        if (otherDistanceFromGoal < totalDistanceFromGoal)
        {
            return 1;
        }
        return 0;
    }
    @Override
    public String toString()
    {
        return "("+this.x +" ," + this.y +")";
    }

    public void setDistribucion(int distribuicion) 
    {
        this.distribucion = distribuicion;
    }
}
