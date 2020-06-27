/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapGui;
import java.util.*;
import javafx.scene.Node;
import Simulation.Buildings;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.*;
/**
 *
 * @author acer
 */
public class Cell extends StackPane{
    String buildingName;
    List<Cell> children = new ArrayList();
    List<Cell> parents = new ArrayList();
    Node node;
    
    public Cell(String buildingName){
        this.buildingName = buildingName;
    }
    
    public void addCellChild(Cell cell){
        children.add(cell);
    }
    
    public void addCellParent(Cell cell){
        parents.add(cell);
    }

    public List<Cell> getCellChild() {
        return children;
    }

    public List<Cell> getCellParents() {
        return parents;
    }
    
    public void removeCellChild(Cell cell){
        children.remove(cell);
    }
    
    public void setNode(StackPane pane){
        this.node = node;
        getChildren().addAll(pane);
    }
    
    public Node getnode(){
        return this.node;
    }
    
    public String getBuildingName(){
        return buildingName;
    }
}
