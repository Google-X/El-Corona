/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ContactTracer;

import static ContactTracer.InputManual.generator;
import Simulation.HumanIDGenerator;
import java.text.SimpleDateFormat;
import java.util.Date;
import Simulation.HumanIDGenerator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.stage.Stage;

public class TreeStackOverFlow extends Application {
    
    private String tmpInfo;
    private Stage window;
    private Scene outputScene;
    public static TreeItem<String> root;
    public static TreeView<String> tree;
    public static HashMap<String, TreeItem<String>> roots;
    
    // ONLY RUN THIS APP AFTER DONE ADDING ALL SUBTREES
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Output Section
        window = primaryStage;
        
        // Create the tree and hide the main Root
        tree = new TreeView<>(root);
        tree.setShowRoot(false);
        
        StackPane outputSection = new StackPane();
        outputSection.getChildren().add(tree);
        outputScene = new Scene(outputSection, 800, 600);
        window.setScene(outputScene);
        window.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public TreeStackOverFlow(){
        root = new TreeItem<>();
        root.setExpanded(true);
        roots = new HashMap<>();
    }
    
    public void display(){
        // Create the tree and hide the main Root
        tree = new TreeView<>(root);
        tree.setShowRoot(false);
        
        StackPane outputSection = new StackPane();
        outputSection.getChildren().add(tree);
        outputScene = new Scene(outputSection, 800, 600);
        window.setScene(outputScene);
        window.show();
    }
    
    public void setInfo(String tmp){
        tmpInfo = tmp;
        System.out.println("setted");
    }
    
    public void setSubRoot(String humanID, TreeItem<String> ROOT){
        TreeItem<String> tmp;
        if(ROOT == null){
            tmp = makeBranch(humanID, root);
        } else {
            tmp = makeBranch(humanID, ROOT);
        }
        roots.put(humanID, tmp);
    }
    
    public TreeItem<String> getRoots(String humanID){
        if(roots.containsKey(humanID)){
            return roots.get(humanID);
        }
        return null;
    }
    
    // Create branches
    public TreeItem<String> makeBranch(String child, TreeItem<String> parent) {
        TreeItem<String> item = new TreeItem<>(child + " " + tmpInfo);
        item.setExpanded(true);
        parent.getChildren().add(item);
        return item;
    }
    
}
