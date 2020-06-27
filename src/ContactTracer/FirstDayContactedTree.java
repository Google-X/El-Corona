
package ContactTracer;

import java.text.ParseException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import static javafx.application.Application.launch;

public class FirstDayContactedTree extends Application{
    
    private Text data;
    public static Tracer TRACER;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public void start(Stage primaryStage) throws ParseException{
        primaryStage.setWidth(300);
        primaryStage.setHeight(500);
        data = new Text();
        data.setText(TRACER.treeData);
        TextFlow textFlow = new TextFlow();
        textFlow.getChildren().add(data);
        ScrollPane pane = new ScrollPane(textFlow);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
}