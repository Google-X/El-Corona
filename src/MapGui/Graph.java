/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapGui;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
/**
 *
 * @author acer
 */
public class Graph {
    private FormNode formNode;
    private Group canvas;
    Layer layer;
    private Scroll scrollPane;
    Mouse mouse;
    
    public Graph(){
        this.formNode = new FormNode();
        canvas = new Group();
        layer = new Layer();
        canvas.getChildren().add(layer);
        mouse = new Mouse(this);
        scrollPane = new Scroll(canvas);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
    }
    
    public Scroll getScrollPane(){
        return this.scrollPane;
    }
    
    public Pane getLayer(){
        return this.layer;
    }
    
    public FormNode getFormNode(){
        return formNode;
    }
    
    public void update(){
        getLayer().getChildren().addAll(formNode.getAddedEdges());
        getLayer().getChildren().addAll(formNode.getAddedCells());
        getLayer().getChildren().removeAll(formNode.getRemovedCells());
        getLayer().getChildren().removeAll(formNode.getRemovedEdges());
        for (Cell cell: formNode.getAddedCells()){
            mouse.makeDraggable(cell);
        }
        getFormNode().attach(formNode.getAddedCells());
        getFormNode().disconnect(formNode.getRemovedCells());
        getFormNode().merge();
    }
    
    public double getScale(){
        return this.scrollPane.getScaleValue();
    }
}
