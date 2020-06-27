/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapGui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.*;

/**
 *
 * @author acer
 */
public class CellCustomize extends Cell{
    StackPane stackPane = new StackPane();
    public CellCustomize(String buildingName, int category){
        super(buildingName);
        
        Rectangle node = new Rectangle(100,30);
        switch(category){
            case 0:
                node.setStroke(Color.LIGHTBLUE);
                node.setFill(Color.LIGHTBLUE);
                break;
            case 1:
                node.setStroke(Color.LIGHTGREEN);
                node.setFill(Color.LIGHTGREEN);
                break;
            case 2:
                node.setStroke(Color.LIGHTPINK);
                node.setFill(Color.LIGHTPINK);
                break;
            case 3:
                node.setStroke(Color.LIGHTYELLOW);
                node.setFill(Color.LIGHTYELLOW);
                break;
            case 4:
                node.setStroke(Color.LAVENDER);
                node.setFill(Color.LAVENDER);
                break;
            case 5:
                node.setStroke(Color.ORANGE);
                node.setFill(Color.ORANGE);
                break;
            case 6:
                node.setStroke(Color.LIGHTGRAY);
                node.setFill(Color.LIGHTGRAY);
                break;
            case 7:
                node.setStroke(Color.HONEYDEW);
                node.setFill(Color.HONEYDEW);
                break;
            case 8:
                node.setStroke(Color.LIGHTCORAL);
                node.setFill(Color.LIGHTCORAL);
                break;
            case 9:
                node.setStroke(Color.LIGHTGREY);
                node.setFill(Color.LIGHTGREY);
                break;
            case 10:
                node.setStroke(Color.LIGHTSTEELBLUE);
                node.setFill(Color.LIGHTSTEELBLUE);
                break;
            case 11:
                node.setStroke(Color.LIGHTCYAN);
                node.setFill(Color.LIGHTCYAN);
                break;
            case 12:
                node.setStroke(Color.VIOLET);
                node.setFill(Color.VIOLET);
                break;
        }
        Label label = new Label(buildingName);
        label.setTextFill(Color.BLACK);
        label.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 15));
        label.setAlignment(Pos.CENTER);
        stackPane.getChildren().addAll(node, label);
        setNode(stackPane);
    }
    
    public Text createText(String buildingName){
        Text text = new Text(buildingName);
        text.setBoundsType(TextBoundsType.VISUAL);
        text.setStyle("-fx-font-family: \"Times New Roman\";" +
                "-fx-font-style: italic;" +
                "-fx-font-size: 10px;"
        );
        return text;
    }
}
