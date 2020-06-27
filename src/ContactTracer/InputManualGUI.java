/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ContactTracer;

import static ContactTracer.Tracer.placesAndHumanData;
import Simulation.ASimulator;
import Simulation.HumanIDGenerator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import static javax.swing.UIManager.get;

public class InputManualGUI extends Application {
    
    static Tracer tracer = new Tracer();
    static HumanIDGenerator generator = new HumanIDGenerator();
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    private Stage window;
    private Scene inputScene, outputScene;
    public static TreeView<String> tree;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws ParseException  {
        window = primaryStage;
        window.setTitle("Tree of Infected Human IDs");
        
        // Input Scene
        Label prompt = new Label("Manually trace back the possible infected Human IDs\nInput infected human ID: ");
        Label date = new Label("Choose date below");
        
        // Input Human ID Field
        TextField inputInfectedID = new TextField();
        inputInfectedID.setPromptText("Eg. 12345");
        
        // Input DatePicker
        final DatePicker datePicker = new DatePicker();
        datePicker.setEditable(true);
        datePicker.setFocusTraversable(false);
        datePicker.setPromptText("Select Date");
        datePicker.setPrefWidth(170);
        
        // Submit Button
        Button submit = new Button("Submit");
        submit.setMinWidth(460);
        submit.setOnAction( new EventHandler<ActionEvent>()
        {
            public void handle (ActionEvent event){
                
                if(isInt(inputInfectedID.getText())){
                    
                    Date date = null;
                    int humanID = Integer.parseInt(inputInfectedID.getText());
                    generator.humanList.get(humanID).setInfected();
                    
                    try {
                        date = sdf.parse(String.valueOf(datePicker.getValue()));
                    } catch (ParseException ex) {}
                    
                    // Display the possible infected places and human IDs
                    try {
                        List[] possibleInfectedPlacesAndHumanID = placesAndHumanIDS(humanID, date);
                        ASimulator.appLaunch(PossibleInfectedPlacesAndHuman.class);
                    } catch (ParseException ex) {}
                    
                    // Print the tree
                    try {
                        tracer.getTree(humanID, 1.0, date);
                    } catch (ParseException ex) {}
                    
                    // Print the closed relation
                    tracer.getClosedRelationTree(humanID, 1.0);
                    
                } else {
                    // NEW ALERT WINDOW
                    System.err.println("NUMBER NEEDED ONLY");
                }
                
            }
        });
        
        // Input Section - children are laid out in vertical column
        VBox inputSection = new VBox(10);
        inputSection.setPadding(new Insets(20,20,20,20));
        inputSection.getChildren().addAll(prompt, inputInfectedID, date, datePicker, submit);
        inputScene = new Scene(inputSection, 500, 210);
        
        // START
        window.setScene(inputScene);
        window.show();
    
    }
    
    public boolean isInt(String num){
        try{
            Integer.parseInt(num);
            return true;
        } catch (NumberFormatException nef){
            return false;
        }
    }
    
    public List[] placesAndHumanIDS(int infectedHumanID, Date d) throws ParseException{
        tracer.placesAndHumanData = "";
        tracer.placesAndHumanData += "\nCONTACT TRACING STATUS: STARTED\n";
        tracer.placesAndHumanData += "============================================================\n";
        tracer.placesAndHumanData += generator.humanList.get(infectedHumanID).basicInfo() + "\n";
        tracer.placesAndHumanData += "Closed Relationship: " + generator.humanList.get(infectedHumanID).getRelation().toString() + "\n";

        // [0]: Infected places     [1]: Possible infected human ID
        List[] possibleInfectedPlacesAndHumanID = tracer.getPossibleInfectedPlacesAndHumanID(infectedHumanID, d);
        tracer.placesAndHumanData += String.format("\nPlaces Human ID %05d went to for the past 14 days: ", infectedHumanID);
        tracer.placesAndHumanData += possibleInfectedPlacesAndHumanID[0].toString() + "\n";

        tracer.placesAndHumanData += "\nPossible infected Human IDs: ";
        tracer.placesAndHumanData += possibleInfectedPlacesAndHumanID[2].toString() + "\n";
        
        return possibleInfectedPlacesAndHumanID;
    } 
}
