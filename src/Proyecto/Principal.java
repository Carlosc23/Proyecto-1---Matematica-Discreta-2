package Proyecto;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
* @author Julio Barahona, 141206

* @author Carlos Calderon, 15219
* @author Diego Castaneda, 15151
* Clase Principal se encarga de dar la interfaz al usuario para escoger el grafo respectivo
*/
 

public class Principal 
{
    
    public static void main(String[] args) {
        int tamanio=15;
        int decisiont=0;
        boolean diagonales;
        int decision = JOptionPane.showOptionDialog(
        null,
        "Seleccione condicion", 
        "Selector de opciones",
        JOptionPane.YES_NO_CANCEL_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,    // null para icono por defecto.
        new Object[] { "Grafo sin Diagonales", "Grafo con Diagonales"},   // null para YES, NO y CANCEL
        "Diagonales");
       
        /* Condicion para saber si el grafo cuenta con diagonales o */
        if (decision == 1)
        {
            diagonales=true;
        }
        else {
        	diagonales=false;
        }
 
        int decision2 = JOptionPane.showOptionDialog(null,"Seleccione Condicion","Selector de opciones",
            JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,    // null para icono por defecto.
            new Object[] { "Grafo sin obstaculos", "Grafo con obstaculos"},"Grafo sin obstaculos");
        System.out.println(decision2);
        int decisionP = JOptionPane.showOptionDialog(null,"Seleccione Condicion","Selector de opciones",
            JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,    // null para icono por defecto.
            new Object[] { "Aristas constantes", "Aristas con peso"},"Aristas");
        boolean Peso = false;
        if(decisionP==0)
        {
            Peso = false;
        }
        if(decisionP==1)
        {
            Peso = true;
        }
        int[] arregloDistri2 = LeerArchivo(tamanio*tamanio); 
        int algoritmoUtilizar = JOptionPane.showOptionDialog(null,"Seleccione Algortimo","Selector de opciones",
        JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,    // null para icono por defecto.
        new Object[] { "Astar", "Dijkstra"},"Astar");
        /*Dependiendo la eleccion se usa dijkstra o A**/
        switch (algoritmoUtilizar){
            case 0:
               Astar astar = new Astar(tamanio, tamanio, arregloDistri2);
              /* Condicion para saber si el grafo cuenta con obstaculo o no */
               if (decision2==1)
               { 
                   if (decisiont == 0)
                   {
                       astar.getGrafo().crearOstaculo();
                   }
               }      
               astar.calcular(diagonales,false, Peso);
               //Mostrar GUI
               JFrame window = new JFrame();
               window.setSize(405, 425);
               window.setLocationRelativeTo(null);
               window.setVisible(true);
               window.add(new GUI(astar.getGrafo(),tamanio,tamanio,astar.getcamino(),astar.getVerticesEvaluados()));
               window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               break; 
            case 1:
                System.out.println("Algoritmo: Dijkstra");
                astar = new Astar(tamanio, tamanio, arregloDistri2);
                if (decision2==1)
                {
                   if (decisiont == 0)
                   {
                       astar.getGrafo().crearOstaculo();
                   }     
                 
                }
                astar.calcular(diagonales,true, Peso);
                //GUI
                window = new JFrame();
                window.setSize(405, 425);
                window.setLocationRelativeTo(null);
                window.setVisible(true);
                window.add(new GUI(astar.getGrafo(),tamanio,tamanio,astar.getcamino(),astar.getVerticesEvaluados()));
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                break;    
        }
            
    }
    public static int[] LeerArchivo(int tamanio){
        BufferedReader br = null;
        int[] distribucion = new int[tamanio];
        int contador=0; 
        try {
                String sCurrentLine;
                String cm="C:\\Users\\Carlos\\Desktop\\DiagonalesFijas225.txt";
                br = new BufferedReader(new FileReader(cm));
 
                while ((sCurrentLine = br.readLine()) != null) 
                {
                    distribucion[contador] = Integer.parseInt(sCurrentLine);
                  
                    
                    contador++;         
                }
 
        } 
        catch (IOException e) 
        {
        	System.out.println("No leido");
        }
        return distribucion;
    }
     
   
}