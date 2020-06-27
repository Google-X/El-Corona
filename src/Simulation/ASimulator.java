/* [Update V2]
 * - Overall changes for the hashmap
 */

package Simulation;

// Main program simulator
import ContactTracer.InputManual;
import ContactTracer.InputManualGUI;
import ContactTracer.Tracer;
import ContactTracer.Charts;
import ContactTracer.PossibleInfectedPlacesAndHuman;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.application.Application;
import SourceDS.LinkedList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import javafx.application.Platform;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ASimulator {
    
    // List of classes
    static Random r = new Random();
    static Scanner s = new Scanner(System.in);
    public static TimeGenerator TIME = new TimeGenerator();
    public static HumanIDGenerator GENERATOR = new HumanIDGenerator();
    public static HumanActivityLog LOG = new HumanActivityLog();
    public static Tracer TRACER = new Tracer();
    public static Charts CHARTS = new Charts();
    public static InputManual FINDER;
    
    // Important parameters
    final static int startDayHour = 5;
    final static int endDayHour = 22;
    final static int dayToSimulate = 30; // When demo, put more than 14, contact tracer will be carried out after 14
    final static int r0Metric = 4;
    final static int fourteenDay = 14;
    public static int simulationDay;
    public static String[] options = {"Yes", "No", "Stop for all"};
    public static boolean patientFound = false;
    public static boolean MCO = false;
    public static boolean checkManually = true;
    public static boolean openChart = true;
    public static boolean GUIMode = false;
    private static boolean javaFxLaunched = false;
    
    // HashMap for DATE : 1234, 2345, 3456
    static HashMap<String, LinkedList<String>> infectedIDwithDate = new HashMap<>();
    static HashMap<String, Integer> recoveredNumberwithDate = new HashMap<>();
    static HashMap<String, Integer> deadNumberwithDate = new HashMap<>();
    
    public static void appLaunch(Class<? extends Application> applicationClass) {
        if (!javaFxLaunched) { // First time
            Platform.setImplicitExit(false);
            new Thread(() -> Application.launch(applicationClass)).start();
            javaFxLaunched = true;
        } else { // Next times
            Platform.runLater(() -> {
                try {
                    Application application = applicationClass.newInstance();
                    Stage primaryStage = new Stage();
                    application.start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
    
    public static void main(String[] args) throws ParseException {
        
        int ops = JOptionPane.showConfirmDialog(null, "FIRST THING FIRST, GUI MODE?", "El-Corona Setup", JOptionPane.YES_NO_OPTION);
        if(ops == JOptionPane.YES_OPTION){
            GUIMode = true;
            TRACER.setGUIMode();
        } else if (ops == JOptionPane.CLOSED_OPTION) {
            JOptionPane.showMessageDialog(null, "Simulation stopped.");
            System.exit(0);
        }
        
        // Generate map first
        Map map = new Map();
        map.createBuildingList();
        
        if(GUIMode){
            appLaunch(MapInterface.class);
        } else {
            map.showMap();
        }
        
        // Generate or retrieve Human Information
        GENERATOR.generateID();
        simulationDay = dayToSimulate;
        List<String> infectedList = new ArrayList<>();
        List<String> recoveredList = new ArrayList<>();
        List<String> deadList = new ArrayList<>();
        List<String> todayInfectedList = new ArrayList<>();
        List<String> todayRecoveredList = new ArrayList<>();
        List<String> todayDeadList = new ArrayList<>();
        
        while (simulationDay-- > 0) {
            System.out.print("Generating activity logs file for [" + TIME.getDate() + "] : ");
            
            // Initiate a new log for a new log for each of the human & buildings
            newHumanLog();
            newBuildingLog();
            
            // Set the starting hour for the day
            TIME.setH(startDayHour);
            
            GENERATE:
            while (TIME.getH() <= endDayHour) {

                int c = 0;
                while (c < GENERATOR.population) {
                    if(TIME.getH() > endDayHour) break GENERATE;
                    LOG.generateLog(GENERATOR.humanList.get(c), TIME.getDay(), TIME.getTime());
                    c++;
                }
                TIME.plusHour();
                
            }
            
            // Start infecting the people Muahahahhaha
            if (simulationDay < dayToSimulate - fourteenDay - 1 || patientFound) {
                todayInfectedList = new ArrayList<>();
                todayRecoveredList = new ArrayList<>();
                todayDeadList = new ArrayList<>();
                for(int i = 0; i < GENERATOR.population; i++){
                    if(GENERATOR.humanList.get(i).getInfectionRate() >= 0.7 
                       && (GENERATOR.humanList.get(i).getAge().equals("Children")
                       ||  GENERATOR.humanList.get(i).getAge().equals("Senior Citizen") )){
                        String strID = String.format("%05d", i);
                        if(!infectedList.contains(strID)){
                            infectedList.add(strID);
                            todayInfectedList.add(strID);
                            GENERATOR.humanList.get(i).setInfected();
                            
                            if(infectedIDwithDate.containsKey(TIME.getDate())){
                                infectedIDwithDate.get(TIME.getDate()).addLastNode(String.format("%05d", i));
                            } else {
                                LinkedList<String> tmp = new LinkedList<>(TIME.getDate());
                                infectedIDwithDate.put(TIME.getDate(), tmp);
                                infectedIDwithDate.get(TIME.getDate()).addLastNode(String.format("%05d", i));
                            }
                            
//                          tracer.getTree(i, 1.0, TIME.getDateInFormat());
//                          tracer.getClosedRelationTree(i, 1.0);
//                          List[] possibleInfectedPlacesAndHumanID = placesAndHumanIDS(i);
//                          List<String> InfectedPlaces = possibleInfectedPlacesAndHumanID[0];
//                          List<String> PossibleInfectedHumanIDs = possibleInfectedPlacesAndHumanID[1];
//                          List<String> nonDuplicatedInfectedHumanIDs = possibleInfectedPlacesAndHumanID[2];
//                          showAllList(LOG, ID);
                        }
                    } else if(GENERATOR.humanList.get(i).getInfectionRate() >= 0.8 
                            && (GENERATOR.humanList.get(i).getAge().equals("Adult"))){
                        String strID = String.format("%05d", i);
                        if(!infectedList.contains(strID)){
                            infectedList.add(strID);
                            todayInfectedList.add(strID);
                            GENERATOR.humanList.get(i).setInfected();
                            
                            if(infectedIDwithDate.containsKey(TIME.getDate())){
                                infectedIDwithDate.get(TIME.getDate()).addLastNode(String.format("%05d", i));
                            } else {
                                LinkedList<String> tmp = new LinkedList<>(TIME.getDate());
                                infectedIDwithDate.put(TIME.getDate(), tmp);
                                infectedIDwithDate.get(TIME.getDate()).addLastNode(String.format("%05d", i));
                            }

//                          tracer.getTree(i, 1.0, TIME.getDateInFormat());
//                          tracer.getClosedRelationTree(i, 1.0);
//                          List[] possibleInfectedPlacesAndHumanID = placesAndHumanIDS(i);
//                          List<String> InfectedPlaces = possibleInfectedPlacesAndHumanID[0];
//                          List<String> PossibleInfectedHumanIDs = possibleInfectedPlacesAndHumanID[1];
//                          List<String> nonDuplicatedInfectedHumanIDs = possibleInfectedPlacesAndHumanID[2];
//                          showAllList(LOG, ID);
                        }
                    }
                }
                
                // Aiya, not enough patient for today, let's infect the unfortunate one hehehe
                if(todayInfectedList.size() < 5){
                    int choice = r.nextInt(10);
                    if(choice >= 2){
                        int todayInfection = r.nextInt(infectedList.size()*choice);
                        while(true){
                            int rand = r.nextInt(GENERATOR.population);
                            if(GENERATOR.humanList.get(rand).getInfectionRate() >= 0.2){ 
                                String strID = String.format("%05d", rand);
                                if(!infectedList.contains(strID)){
                                    infectedList.add(strID);
                                    todayInfectedList.add(strID);
                                    GENERATOR.humanList.get(rand).setInfected();
                                    
                                    if(infectedIDwithDate.containsKey(TIME.getDate())){
                                        infectedIDwithDate.get(TIME.getDate()).addLastNode(String.format("%05d", rand));
                                    } else {
                                        LinkedList<String> tmp = new LinkedList<>(TIME.getDate());
                                        infectedIDwithDate.put(TIME.getDate(), tmp);
                                        infectedIDwithDate.get(TIME.getDate()).addLastNode(String.format("%05d", rand));
                                    }
                                    
                                }
                            } else todayInfection--;
                            if(todayInfectedList.size() >= todayInfection) break;
                        }
                    }
                }
                
                // Aiyo, seems like many patients liao, Saitama: "Time to kill some virus"
                if(infectedList.size() >= 30){
                    int num = r.nextInt(infectedList.size()/3);
                    int c = 0;
                    while(c < num){
                        int choice = r.nextInt(10);
                        // Recovered
                        if(choice <= 4){

                            String recoveredID = infectedList.get(r.nextInt(infectedList.size()));

                            if(!todayInfectedList.contains(recoveredID)){
                                recoveredList.add(recoveredID);
                                todayRecoveredList.add(recoveredID);
                                infectedList.remove(recoveredID);
                            }

                        }
                        // Dead
                        else if(choice == 6 || choice == 9){
                            String deadID = infectedList.get(r.nextInt(infectedList.size()));

                            if(!todayInfectedList.contains(deadID)){
                                deadList.add(deadID);
                                todayDeadList.add(deadID);
                                infectedList.remove(deadID);
                            }
                        }
                        c++;
                    }
                }
            }
            
//          showAllList(LOG, GENERATOR);
            System.out.println("DONE");
            
            // Put all logs into its hashmap
            saveHumanLog();
            saveBuildingLog();
            
            // Get the very first batch possible covid-19 patient from airport, do only once
            if (simulationDay == dayToSimulate - fourteenDay - 1 && !patientFound) {

                int c = 0;
                while(c < r0Metric){
                    try{
                        int firstInfected = r.nextInt(GENERATOR.population);
                        String strID = String.format("%05d", firstInfected);
                        if(GENERATOR.humanList.get(firstInfected).getWorkplace().equals("Airport")) {
                            infectedList.add(strID);
                            todayInfectedList.add(strID);
                            
                            if(infectedIDwithDate.containsKey(TIME.getDate())){
                                infectedIDwithDate.get(TIME.getDate()).addLastNode(String.format("%05d", firstInfected));
                            } else {
                                LinkedList<String> tmp = new LinkedList<>(TIME.getDate());
                                infectedIDwithDate.put(TIME.getDate(), tmp);
                                infectedIDwithDate.get(TIME.getDate()).addLastNode(String.format("%05d", firstInfected));
                            }
                            
//                            List[] possibleInfectedPlacesAndHumanID = placesAndHumanIDS(firstInfected);
//                            TRACER.getTree(firstInfected, 1.0, TIME.getDateInFormat());
//                            TRACER.getClosedRelationTree(firstInfected, 1.0);
                            c++;
                        }
                    } catch (NullPointerException nep){}
                }
                
                patientFound = true;
//              List<String> InfectedPlaces = possibleInfectedPlacesAndHumanID[0];
//              List<String> PossibleInfectedHumanIDs = possibleInfectedPlacesAndHumanID[1];
//              List<String> nonDuplicatedInfectedHumanIDs = possibleInfectedPlacesAndHumanID[2];
//              showAllList(LOG, GENERATOR);
            }
            
            // Display today list
            if(patientFound){
                recoveredNumberwithDate.put(TIME.getDate(), todayRecoveredList.size());
                deadNumberwithDate.put(TIME.getDate(), todayDeadList.size());
                
                int total = infectedList.size() + recoveredList.size() + deadList.size();
                System.out.println("\n#######################################################################");
                System.out.println("#               !!! NEWS TODAY ABOUT COVID-19 !!!");
                System.out.println("# Date: " + TIME.getDate());
                System.out.println("# Today infected number : " + todayInfectedList.size());
                System.out.println("# Today infected human IDs : " + todayInfectedList.toString());
                System.out.println("# ______________________________________________________________________");
                System.out.println("# Today recovered number : " + todayRecoveredList.size());
                System.out.println("# Total recovered number : " + recoveredList.size());
                System.out.println("# ______________________________________________________________________");
                System.out.println("# Today dead number : " + todayDeadList.size());
                System.out.println("# Total dead number : " + deadList.size());
                System.out.println("# ______________________________________________________________________");
                System.out.println("# Currently warded number up to now : " + infectedList.size());
                System.out.println("# Total Covid-19 patients up to now : " + total);
                
                if(MCO == false && infectedList.size() >= 50) {
                    System.out.println("#\n# Government announces that MCO started");
                    MCO = true;
                }
                System.out.println("#\n#######################################################################\n");
                
                // GRAPH
                Set<String> keys = infectedIDwithDate.keySet();
                List<String> list = new ArrayList<>(keys); 
                Collections.sort(list);

                if(total > GENERATOR.population - (5*GENERATOR.population/10)) {
                    
                    if(GUIMode){
                        CHARTS.start(TIME.getDate(), list, infectedIDwithDate, recoveredNumberwithDate, deadNumberwithDate, todayInfectedList.size(), todayRecoveredList.size(), todayDeadList.size(), infectedList.size(), total);
                    } else {
                        System.out.println("Your whole city is half infected. Simulation ends.");
                        System.exit(0);
                    }
                }
                
                if(GUIMode){
                    if(openChart){
                        CHARTS.start(TIME.getDate(), list, infectedIDwithDate, recoveredNumberwithDate, deadNumberwithDate, todayInfectedList.size(), todayRecoveredList.size(), todayDeadList.size(), infectedList.size(), total);
                    } else {
                        if(simulationDay == 0){
                            // BUG BUG BUG BUG BUG BUG BUG BUG BUG BUG BUG BUG BUG BUG
                            CHARTS.start(TIME.getDate(), list, infectedIDwithDate, recoveredNumberwithDate, deadNumberwithDate, todayInfectedList.size(), todayRecoveredList.size(), todayDeadList.size(), infectedList.size(), total);
                            checkManually = true;
                            // TO DO CONFIRMATION BOX IN CHART CLASS
                        }
                    }
                } else {
                    if(simulationDay == 0){
                        s.nextLine();
                        System.out.print("To shutdown [Press any key, Y/y to continue to continue the simulation]: ");
                        switch(s.nextLine()){
                            case "y":
                            case "Y":
                                simulationDay += 30;
                                checkManually = true;
                                break;
                            case "\n":
                            default:
                                System.exit(0);
                        }
                    }
                }
                
//              Ask user whether they want to check manualy
                ASK:
                while(checkManually){
                    
                    if(GUIMode){
                        // BUG BUG BUG BUG BUG BUG BUG BUG BUG BUG BUG BUG BUG BUG
                        // TO DO IN CHARTS
                        if(CHARTS.checkManuallyReturn == false) {
                            CHARTS.checkManuallyReturn = true;
                            break;
                        }
                    } else {
                    
                        System.out.print("\nDo you want to check manually? X: Stop for all (Y/N): ");
                        char choice = s.next().charAt(0);

                        switch(choice){
                            case 'Y':
                            case 'y':
                                FINDER = new InputManual();
                                FINDER.find();
                                break;
                            case 'X':
                            case 'x':
                                checkManually = false;
                                openChart = false;
                                break;
                            default:
                                break ASK;
                        }
                    }
                }
            }
            
            // Next day
            TIME.nextDay();
        }

    }
    
    public static List[] placesAndHumanIDS(int infectedHumanID) throws ParseException{
        
        if(GUIMode){
            TRACER.placesAndHumanData = "";
            TRACER.placesAndHumanData += "\n!!! Covid-19 Patient detected !!!\nCONTACT TRACING STATUS: STARTED\n";
            TRACER.placesAndHumanData += "============================================================\n";
            TRACER.placesAndHumanData += GENERATOR.humanList.get(infectedHumanID).basicInfo() + "\n";
            TRACER.placesAndHumanData += "Closed Relationship: " + GENERATOR.relationship.getEdge(infectedHumanID) + "\n";
            
            // [0]: Infected places     [1]: Possible infected human ID
            List[] possibleInfectedPlacesAndHumanID = TRACER.getPossibleInfectedPlacesAndHumanID(infectedHumanID, TIME.getDateInFormat());
            TRACER.placesAndHumanData += String.format("\nPlaces Human ID %05d went to for the past 14 days: ", infectedHumanID);
            TRACER.placesAndHumanData += possibleInfectedPlacesAndHumanID[0].toString() + "\n";
            
            TRACER.placesAndHumanData += "\nPossible infected Human IDs: ";
            TRACER.placesAndHumanData += possibleInfectedPlacesAndHumanID[2].toString() + "\n";
            
            ASimulator.appLaunch(PossibleInfectedPlacesAndHuman.class);
            
            return possibleInfectedPlacesAndHumanID;
        } else {
            System.out.println("\n!!! Covid-19 Patient detected !!!\nCONTACT TRACING STATUS: STARTED");
            System.out.println("============================================================");
            System.out.println(GENERATOR.humanList.get(infectedHumanID).basicInfo());

            System.out.println("Closed Relationship: " + GENERATOR.relationship.getEdge(infectedHumanID));

            // [0]: Infected places     [1]: Possible infected human ID
            List[] possibleInfectedPlacesAndHumanID = TRACER.getPossibleInfectedPlacesAndHumanID(infectedHumanID, TIME.getDateInFormat());

            System.out.printf("\nPlaces Human ID %05d went to for the past 14 days: ", infectedHumanID);
            System.out.println(possibleInfectedPlacesAndHumanID[0].toString());

            System.out.print("\nPossible infected Human IDs: ");
            System.out.println(possibleInfectedPlacesAndHumanID[2].toString());
            return possibleInfectedPlacesAndHumanID;
        }
    }
    
    // During demo, show using the files faster because pc might lags out
    public static void showAllList(HumanActivityLog LOG, HumanIDGenerator ID) {
        // BUILDING LIST
        LOG.showBuildingList();
        System.out.println("\n");

        // HUMAN LIST
        for (int i = 0; i < ID.population; i++) {
            ID.humanList.get(i).getLog().showList();
            System.out.println();
        }
    }
    
    public static void newHumanLog() {
        for (int i = 0; i < GENERATOR.population; i++) {
            GENERATOR.humanList.get(i).newLog();
        }
    }
    
    public static void saveHumanLog(){
        for (int i = 0; i < GENERATOR.population; i++) {
            GENERATOR.humanList.get(i).saveLog(TIME.getDate());
        }
    }
    
    public static void newBuildingLog(){
        LOG.newBuildingLog();
    }
    
    public static void saveBuildingLog(){
        LOG.saveBuildingLog();
    }
}
