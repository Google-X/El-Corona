/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapGui;

import Simulation.Buildings;
import java.util.*;
/**
 *
 * @author acer
 */
public class FormNode {
    
    Cell graphParent;
    List<Cell> allCells;
    List<Cell> addedCells;
    List<Cell> removedCells;
    List<Edge> allEdges;
    List<Edge> addedEdges;
    List<Edge> removedEdges;
    Map<String, Cell> cellMap;
    
    public FormNode(){
        graphParent = new Cell("Root");
        clear();
    }
    
    public void clear(){
        allCells = new ArrayList<>();
        addedCells = new ArrayList<>();
        removedCells = new ArrayList<>();
        allEdges = new ArrayList<>();
        addedEdges = new ArrayList<>();
        removedEdges = new ArrayList<>();
        cellMap = new HashMap<>();
    }
    
    public void clearAddedLists(){
        addedCells.clear();
        addedEdges.clear();
    }

    public List<Cell> getAddedCells() {
        return addedCells;
    }
    
    public List<Cell> getRemovedCells(){
        return removedCells;
    }

    public List<Edge> getAddedEdges() {
        return addedEdges;
    }

    public List<Cell> getAllCells() {
        return allCells;
    }

    public List<Edge> getAllEdges() {
        return allEdges;
    }
    
    public List<Edge> getRemovedEdges(){
        return removedEdges;
    }
    public void addCell(String buildingName, int category){
        CellCustomize formCell = new CellCustomize(buildingName, category);
        addCell(formCell);
    }
    public void addCell(Cell cell){
        addedCells.add(cell);
        cellMap.put(cell.getBuildingName(), cell);
    }
    
    public void addEdge(String sourceName, String targetName){
        Cell sourceCell = cellMap.get(sourceName);
        Cell targetCell = cellMap.get(targetName);
        Edge edge = new Edge(sourceCell, targetCell);
        addedEdges.add(edge);
    }
    
    public void attach(List<Cell> list){
        for (Cell cell: list){
            if (cell.getCellParents().size() == 0){
                graphParent.addCellChild(cell);
            }
        }
    }
    
    public void disconnect(List<Cell> list){
        for (Cell cell: list){
            graphParent.removeCellChild(cell);
        }
    }
    
    public void merge(){
        allCells.addAll(addedCells);
        allCells.removeAll(removedCells);
        
        addedCells.clear();
        removedCells.clear();
        
        allEdges.addAll(addedEdges);
        allEdges.removeAll(removedEdges);
        
        removedEdges.clear();
        addedEdges.clear();
    }
}
