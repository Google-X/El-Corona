/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ContactTracer;

import java.text.ParseException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import static javafx.application.Application.launch;

public class PossibleInfectedPlacesAndHuman extends Application{
    
    private Text data;
    public static Tracer TRACER;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public void start(Stage primaryStage) throws ParseException{
        primaryStage.setWidth(1200);
        primaryStage.setHeight(600);
        data = new Text();
        data.setText(TRACER.placesAndHumanData);
        TextFlow textFlow = new TextFlow();
        textFlow.getChildren().add(data);
        ScrollPane pane = new ScrollPane(textFlow);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
}