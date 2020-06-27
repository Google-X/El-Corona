/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapGui;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author acer
 */
public class Layout {
    Random r = new Random();
    Graph graph;
    List<Double> coorX;
    List<Double> coorY;
    double currentX;
    double currentY;
    
    public Layout(Graph graph){
        this.graph = graph;
        coorX = new ArrayList<>();
        coorY = new ArrayList<>();
        currentX = 0;
        currentY = 0;
    }
    
    public void display(){
        List<Cell> c = graph.getFormNode().getAllCells();
        
        
        for (int i = 0; i < c.size(); i++){
            c.get(i).relocate(r.nextDouble() * 900, r.nextDouble() * 500);
            boolean overlap = true;
            while(overlap){
                overlap = false;
                for (int j = 0; j < i; j++){
                    if (c.get(i).getBoundsInParent().intersects(c.get(j).getBoundsInParent())){
                        c.get(i).relocate(r.nextDouble() * 900, r.nextDouble() * 500);
                        overlap = true;
                    }
                }
            }
        }
    }
    
    public boolean checkOverlap(double x, double y){
        for (int i = 0; i < coorX.size(); i++){
            if (x == coorX.get(i) || y == coorX.get(i)){
                return false;
            }
        }
        return true;
    }
}
