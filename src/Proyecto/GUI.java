
package Proyecto;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.Set;
import javax.swing.JPanel;

/**
* @author Julio Barahona, 141206
* @author Carlos Calderon, 15219
* @author Diego Castaneda, 15151
* Clase para armar la interfaz Grafica.
*/
public class GUI extends JPanel{
    private final Grafo grafo;
    private final int X;
    private final int Y;
    private final int ancho;
    private final int alto;
    private final List<Vertice> camino;
    private final Set<Vertice> VerticesEvaluados;

    public GUI(Grafo grafo, int ancho, int alto, List<Vertice> camino, Set<Vertice> VerticesEvaluados) {
        this.grafo = grafo;
        this.ancho = ancho;
        this.alto = alto;
        this.camino = camino;
        this.X=400/ancho;
        this.Y=400/alto;
        this.VerticesEvaluados=VerticesEvaluados;
        setSize(X * ancho, Y * alto);
        setVisible(true);
        
    }
    
    
    private void fillRect(Graphics graphics, int x, int y) {
          graphics.fill3DRect(X*x, Y*y, X, Y, true);
    }
    public void paintObstacles(Graphics graphics) 
    {
        graphics.setColor(Color.RED);
         
        for (int x = 0; x < alto; ++x) 
        {
            for (int y = 0; y < ancho; ++y) 
                {
                    if (grafo.getVertice(x, y).isObstaculo()) 
                    {
                         fillRect(graphics, x, y);
                    }
                }
        }
    }
    public void paintGrafo(Graphics graphics)
    {
        graphics.setColor(Color.WHITE);
        for (int y = 0; y < alto; y++) 
       {
           for (int x = 0; x < ancho; x++)
           {
             fillRect(graphics,x,y);

           }
       }
    }
      @Override
    public void paint(Graphics graphics) 
    {

         graphics.setColor(Color.DARK_GRAY);
         paintGrafo(graphics);
         paintObstacles(graphics);
         paintcamino(graphics);
    }
      
    private void paintcamino(Graphics graphics) 
    {
       
       graphics.setColor(Color.BLUE);
       for (Vertice n1: VerticesEvaluados)
       {
           fillRect(graphics,n1.getX(),n1.getY());
       }
       graphics.setColor(Color.YELLOW);
       for (Vertice n : camino) 
       {    
           int x = n.getX(); int y = n.getY();
           fillRect(graphics, x, y);   
       }
    }
   
}
