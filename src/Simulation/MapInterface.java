/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulation;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import MapGui.Mouse;
import MapGui.Scroll;
import MapGui.Cell;
import MapGui.CellCustomize;
import MapGui.Layer;
import MapGui.Graph;
import MapGui.FormNode;
import MapGui.Layout;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author acer
 */
public class MapInterface extends Application {

    Graph graph;
    boolean[][] mapList = Map.mapList;
    ArrayList<String> buildingList = Map.buildingList;

    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        graph = new Graph();
        root.setCenter(graph.getScrollPane());
        root.setStyle("-fx-background-color: white;");
        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setTitle("Map");
        primaryStage.setScene(scene);
        primaryStage.show();
        addComponents();
        Layout layout = new Layout(graph);
        layout.display();
    }

    public void addComponents() {
        FormNode form = graph.getFormNode();
        for (int i = 0; i < buildingList.size(); i++) {
            String buildingName = buildingList.get(i);
            int category = 0;
            if (Arrays.asList(Buildings.Grocery).contains(buildingName)) {
                category = 0;
            } else if ((Arrays.asList(Buildings.Company).contains(buildingName))) {
                category = 1;
            } else if ((Arrays.asList(Buildings.Restaurant).contains(buildingName))) {
                category = 2;
            } else if ((Arrays.asList(Buildings.PublicService).contains(buildingName))) {
                category = 3;
            } else if ((Arrays.asList(Buildings.Bank).contains(buildingName))) {
                category = 4;
            } else if ((Arrays.asList(Buildings.Education).contains(buildingName))) {
                category = 5;
            } else if ((Arrays.asList(Buildings.Medical).contains(buildingName))) {
                category = 6;
            } else if ((Arrays.asList(Buildings.ShoppingCentre).contains(buildingName))) {
                category = 7;
            } else if ((Arrays.asList(Buildings.PublicPlaces).contains(buildingName))) {
                category = 8;
            } else if (buildingName.equals("Airport")) {
                category = 9;
            } else if (buildingName.contains("KTM Station")) {
                category = 10;
            } else if (buildingName.contains("MRT Station")) {
                category = 11;
            } else {
                category = 12;
            }
            form.addCell(buildingList.get(i), category);
        }

        for (int i = 0; i < buildingList.size(); i++) {
            for (int j = 0; j < buildingList.size(); j++) {
                if (mapList[i][j]) {
                    form.addEdge(buildingList.get(i), buildingList.get(j));
                    mapList[i][j] = false;
                    mapList[j][i] = false;
                }
            }
        }

        graph.update();

    }
    public static void main(String[] args) {
        Map map = new Map();
        map.createBuildingList();
        map.printList();
        map.showMap();
        launch(args);
    }
}
