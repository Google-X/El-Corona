/*
 * To get the places a HUMAN ID(PERSON A) went to 14 days before he discovered he is infected by COVID-19
 * To get other HUMAN ID that went to the places that PERSON A went to
 */
package ContactTracer;

import Simulation.ASimulator;
import Simulation.HumanIDGenerator;
import SourceDS.LinkedList;
import SourceDS.TreeNode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Tracer {
    
    // Important Class
    Random r = new Random();
    Scanner s = new Scanner(System.in);
    HumanIDGenerator ID = new HumanIDGenerator();
    TreeNode<String> root;
    
    // Data to parse in
    public static String treeData = "";
    public static String ClosedTreeData = "";
    public static String placesAndHumanData = "";
    
    // Objects
    List<Integer> printedIDs;
    List<Integer> printedClosedIDs;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    
    // Parameters
    public static boolean GUIMode = false;
    final int dayToCheck = 14;
    
    public void setGUIMode(){
        GUIMode = true;
    }
    
    public void getClosedRelationTree(int infectedID, double rate){
        
        if(HumanIDGenerator.humanList.get(infectedID).getRelation().isEmpty()) return;
        
        if(!GUIMode) System.out.println("\nSTATUS: GENERATING TREE");
        
        // UPDATE TREE
        TreeNode.treeKey = new HashMap<String, TreeNode<String>>();
        root = new TreeNode<String>(String.format("%05d", infectedID), "Infected Rate: 1.000");
        TreeNode.treeKey.put(String.format("%05d", infectedID), root);
        // UPDATE TREE
        
        printedClosedIDs = new ArrayList<>();
        
        ClosedTreeData = "Relatives, friends, etc might go to each other's house. \nFor these cases, the government would not record it down but big data from social media platforms can be used to locate them. \nFor extra care, we list out all the possible infected IDs based on their relationship so we can inform them.\n";
        
        printCloseRelationTree(infectedID, rate);
        
        // UPDATE TREE
        ClosedTreeData += root.toString();
        // UPDATE TREE
        
        if(GUIMode){
            ASimulator.appLaunch(ClosedTree.class);
        } else {
            System.out.println(ClosedTreeData);
        }
        
    }
    
    private void printCloseRelationTree(int parent, double rate){
        if(rate < 0.4) return;
        
        for(int i = 0; i < HumanIDGenerator.humanList.get(parent).getRelation().size(); i++){
            try{
                int contactedID = Integer.parseInt((String)(HumanIDGenerator.humanList.get(parent).getRelation().get(i)));

                    if(!printedClosedIDs.contains(contactedID)) printedClosedIDs.add(contactedID);
                    else continue;

                    if(HumanIDGenerator.humanList.get(contactedID).getMask()){
                        
                        // UPDATE TREE
                        TreeNode.treeKey.get(String.format("%05d", parent)).addChild(String.format("%05d", contactedID), String.format("Infection Rate: %.3f", rate*0.4));
                        // UPDATE TREE
                        
                        printCloseRelationTree(contactedID, rate*0.4*0.9);
                    } else {
                        
                        // UPDATE TREE
                        TreeNode.treeKey.get(String.format("%05d", parent)).addChild(String.format("%05d", contactedID), String.format("Infection Rate: %.3f", rate*0.9));
                        // UPDATE TREE
                        
                        printCloseRelationTree(contactedID, rate*0.9*0.9);
                    }

            } catch(NumberFormatException nfe) {}

        } 
    }
    
    public List[] getPossibleInfectedPlacesAndHumanID(int humanID, Date dateDiscoveredInfected) {
        List<String> infectedPlaces = new ArrayList<>();
        List<String> infectedHumanIDs = new ArrayList<>();
        List[] possibleInfectedPlacesAndHumanID = new List[3];
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateDiscoveredInfected);
        cal.add(Calendar.DAY_OF_MONTH, -14);
        int dayChecked = 0;
        
        while(dayChecked <= 14){
            dayChecked++;
            cal.add(Calendar.DAY_OF_MONTH, 1);
            
            LinkedList<String> processingLog;
            
            try{
                processingLog = ASimulator.GENERATOR.humanList.get(humanID).getLogByDate(sdf.format(cal.getTime()));
                if(processingLog == null) continue;
            } catch (NullPointerException nep){
                continue;
            }
            
            if(GUIMode){
                placesAndHumanData += "_______________________________________________________\n";
                placesAndHumanData += "Date : " + sdf.format(cal.getTime()) + "\n";
            } else {
                System.out.println("_______________________________________________________");
                System.out.println("Date : " + sdf.format(cal.getTime()));
            }
            
            String[] tmpPlace = new String[processingLog.length()];
            for(int i = 0; i < processingLog.length(); i++){
                tmpPlace[i] = processingLog.get(i).substring(0, processingLog.get(i).indexOf(" ["));
                
                if (!infectedPlaces.contains(tmpPlace[i])) infectedPlaces.add(tmpPlace[i]);

                infectedHumanIDs.addAll(getPossibleInfectedHuman(tmpPlace[i], cal.getTime()));
            }
            
        }
        
        // Sort the list
        Collections.sort(infectedHumanIDs);

        List<String> hostID = Arrays.asList(String.format("%05d", humanID));
        infectedHumanIDs.removeAll(hostID); // remove all Covid-19 host ID

        possibleInfectedPlacesAndHumanID[0] = infectedPlaces;
        possibleInfectedPlacesAndHumanID[1] = infectedHumanIDs; 
//      ^^^ Sorted list, might have repeated human ID, we need it to increase the probability of the human getting infected.

        List<String> nonDuplicatedInfectedHumanIDs = removeDuplicates(possibleInfectedPlacesAndHumanID[1]);
        possibleInfectedPlacesAndHumanID[2] = nonDuplicatedInfectedHumanIDs;
        
        return possibleInfectedPlacesAndHumanID;
    }
    
    private List getPossibleInfectedHuman(String infectedPlace, Date currentProcessedDate) {
        
        if(GUIMode){
            placesAndHumanData += "List of Human ID that went to the place: " + infectedPlace + "\n";
        } else {
            System.out.println("List of Human ID that went to the place: " + infectedPlace);
        }
        
        LinkedList<String> possibleInfectedHumanID = new LinkedList<>("");
        List<String> re = new ArrayList<>();
        
        try{
            possibleInfectedHumanID = ASimulator.LOG.bl.getLogBy(sdf.format(currentProcessedDate), infectedPlace);
        } catch (NullPointerException nep){
            return null;
        }

        for (int c = 0; c < possibleInfectedHumanID.length(); c++) {
            String HumanIDStr = possibleInfectedHumanID.get(c).substring(possibleInfectedHumanID.get(c).indexOf("[") + 1, possibleInfectedHumanID.get(c).indexOf("]"));
            re.add(HumanIDStr);
        }
        
        if(GUIMode){
            placesAndHumanData += re.toString() + "\n";
        } else {
            System.out.println(re.toString());
        }

        return re;
    }
    
    private List getPossibleInfectedHumanWOUTINFO(String infectedPlace, Date currentProcessedDate) {
        LinkedList<String> possibleInfectedHumanID = new LinkedList<>("");
        List<String> re = new ArrayList<>();
        
        try{
            possibleInfectedHumanID = ASimulator.LOG.bl.getLogBy(sdf.format(currentProcessedDate), infectedPlace);
        } catch (NullPointerException nep){
            return null;
        }

        for (int c = 0; c < possibleInfectedHumanID.length(); c++) {
            String HumanIDStr = possibleInfectedHumanID.get(c).substring(possibleInfectedHumanID.get(c).indexOf("[") + 1, possibleInfectedHumanID.get(c).indexOf("]"));
            re.add(HumanIDStr);
        }
        
        return re;
    }
    
    private <T> List<T> removeDuplicates(List<T> list) { 
        // Create a new LinkedHashSet 
        Set<T> set = new LinkedHashSet<>(); 
  
        // Add the elements to set 
        set.addAll(list); 
  
        // Clear the list 
        list.clear(); 
  
        // add the elements of set with no duplicates to the list 
        list.addAll(set); 
  
        // return the list 
        return list; 
    }
    
    public void getTree(int infectedID, double rate, Date dateDiscoveredInfected) throws ParseException{
        
        if(!GUIMode) System.out.println("\nSTATUS: GENERATING TREE");
        
        printedIDs = new ArrayList<>();
        printedIDs.add(infectedID);
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateDiscoveredInfected);
        cal.add(Calendar.DAY_OF_MONTH, -14);
        
        // UPDATE TREE
        TreeNode.treeKey = new HashMap<String, TreeNode<String>>();
        root = new TreeNode<String>(String.format("%05d", infectedID), String.format("HOST [%s] %.3f", sdf.format(cal.getTime()), rate));
        TreeNode.treeKey.put(String.format("%05d", infectedID), root);
        // UPDATE TREE
        
        buildTree(infectedID, rate, cal.getTime());
        
        // UPDATE TREE
        treeData = root.toString();
        // UPDATE TREE
        
        if(GUIMode){
            ASimulator.appLaunch(FirstDayContactedTree.class);
        } else {
            System.out.println(treeData);
        }
        
    }
    
    private void buildTree(int parent, double rate, Date firstDateInfected) throws ParseException{
        if(rate < 0.4) return;
        int dayChecked = 0;
        
        while(dayChecked < dayToCheck){
            
            // From the first date infected, +1 day to check
            Calendar cal = Calendar.getInstance();
            cal.setTime(firstDateInfected);
            cal.add(Calendar.DAY_OF_MONTH, dayChecked);
            dayChecked++;
            
            // Get the list of places the infected person went to
            LinkedList<String> processingLog;
            try{
                processingLog = ASimulator.GENERATOR.humanList.get(parent).getLogByDate(sdf.format(cal.getTime()));
                if(processingLog == null) continue;
            } catch (NullPointerException nep){
                continue;
            }
            
            String[] places = new String[processingLog.length()];
            for (int i = 0; i < processingLog.length(); i++) places[i] = processingLog.get(i).substring(0, processingLog.get(i).indexOf(" ["));
            List<String> placeList = Arrays.asList(places);
            
            // Get building logs using date and the infected places
            for(int i = 0; i < placeList.size(); i++){
                
                LinkedList<String> processingBuildingLog;
                try{
                    processingBuildingLog = ASimulator.LOG.bl.getLogBy(sdf.format(cal.getTime()), placeList.get(i));
                    if(processingBuildingLog == null) continue;
                } catch (NullPointerException nep){
                    continue;
                }
                
                // For working contact with customers
                if(processingBuildingLog.getName().equals(HumanIDGenerator.humanList.get(parent).getWorkplace())
                   && (!HumanIDGenerator.humanList.get(parent).getWorkplace().equals("Moonlings")
                   ||  !HumanIDGenerator.humanList.get(parent).getWorkplace().equals("IKEY")
                   ||  !HumanIDGenerator.humanList.get(parent).getWorkplace().equals("2 Akhir"))){

                    List<String> strangerContactIDs = getPossibleInfectedHumanWOUTINFO(processingBuildingLog.getName(), cal.getTime());
                    for(String strangerIDs : strangerContactIDs){
                        int strangerIDsInt = Integer.parseInt(strangerIDs);
                        if(!printedIDs.contains(strangerIDsInt)) printedIDs.add(strangerIDsInt);
                        else continue;

                        int forgetfulness = HumanIDGenerator.humanList.get(strangerIDsInt).getForgetfulness();

                        if(ASimulator.MCO && r.nextInt(101) > forgetfulness){
                            HumanIDGenerator.humanList.get(strangerIDsInt).wearMask();
                        }
                        
                        if(HumanIDGenerator.humanList.get(strangerIDsInt).getMask()){
//                          if(rate*0.4 < 0.4) return;

                            switch(HumanIDGenerator.humanList.get(strangerIDsInt).getAge()){
                                case "Children":
                                    HumanIDGenerator.humanList.get(strangerIDsInt).setInfectionRate(rate*0.4);
                                    
                                    addOrDisplay(sdf.format(cal.getTime()), processingBuildingLog.getName(), strangerIDsInt, parent, HumanIDGenerator.humanList.get(strangerIDsInt).getInfectionRate());
                                            
                                    buildTree(strangerIDsInt, rate*0.4*0.9, cal.getTime());
                                    break;
                                case "Adult":
                                    HumanIDGenerator.humanList.get(strangerIDsInt).setInfectionRate(rate*0.3);
                                    
                                    addOrDisplay(sdf.format(cal.getTime()), processingBuildingLog.getName(), strangerIDsInt, parent, HumanIDGenerator.humanList.get(strangerIDsInt).getInfectionRate());
                                       
                                    buildTree(strangerIDsInt, rate*0.3*0.9, cal.getTime());
                                    break;
                                case "Senior Citizen":
                                    HumanIDGenerator.humanList.get(strangerIDsInt).setInfectionRate(rate*0.5);
                                   
                                    addOrDisplay(sdf.format(cal.getTime()), processingBuildingLog.getName(), strangerIDsInt, parent, HumanIDGenerator.humanList.get(strangerIDsInt).getInfectionRate());
                                       
                                    buildTree(strangerIDsInt, rate*0.5*0.9, cal.getTime());
                                    break;
                                default:
                            }

                        } else {
//                          if(rate*0.9 < 0.4) return;

                            switch(HumanIDGenerator.humanList.get(strangerIDsInt).getAge()){
                                case "Children":
                                    HumanIDGenerator.humanList.get(strangerIDsInt).setInfectionRate(rate*0.7);
                                    
                                    addOrDisplay(sdf.format(cal.getTime()), processingBuildingLog.getName(), strangerIDsInt, parent, HumanIDGenerator.humanList.get(strangerIDsInt).getInfectionRate());
                                       
                                    buildTree(strangerIDsInt, rate*0.7*0.9, cal.getTime());
                                    break;
                                case "Adult":
                                    HumanIDGenerator.humanList.get(strangerIDsInt).setInfectionRate(rate*0.8);
                                    
                                    addOrDisplay(sdf.format(cal.getTime()), processingBuildingLog.getName(), strangerIDsInt, parent, HumanIDGenerator.humanList.get(strangerIDsInt).getInfectionRate());
                                       
                                    buildTree(strangerIDsInt, rate*0.8*0.9, cal.getTime());
                                    break;
                                case "Senior Citizen":
                                    HumanIDGenerator.humanList.get(strangerIDsInt).setInfectionRate(rate*0.9);
                                    
                                    addOrDisplay(sdf.format(cal.getTime()), processingBuildingLog.getName(), strangerIDsInt, parent, HumanIDGenerator.humanList.get(strangerIDsInt).getInfectionRate());
                                       
                                    buildTree(strangerIDsInt, rate*0.9*0.9, cal.getTime());
                                    break;
                                default:
                            }
                        }
                    }
                }
                else if(placeList.contains(processingBuildingLog.getName())){
                    // For close relationship contact
                    for(int re = 0; re < HumanIDGenerator.humanList.get(parent).getRelation().size(); re++){
                        try{
                            int child = Integer.parseInt((String)(HumanIDGenerator.humanList.get(parent).getRelation().get(re)));
                            
                            if(processingBuildingLog.getBuidlingList().contains(String.valueOf(child))){

                                /*
                                Want to implement the check the hours from log as well,
                                only for places like grocery for retired, they might not go at the same time
                                unless is the worker at bank / grocery
                                then the close id has higher chance of getting infected
                                */

                                if(!printedIDs.contains(child)) printedIDs.add(child);
                                else continue;

                                int forgetfulness = HumanIDGenerator.humanList.get(child).getForgetfulness();

                                if(ASimulator.MCO && (r.nextInt(101) > forgetfulness)){
                                    HumanIDGenerator.humanList.get(child).wearMask();
                                }
                                
                                if(HumanIDGenerator.humanList.get(child).getMask()){

                                    switch(HumanIDGenerator.humanList.get(child).getAge()){
                                        case "Children":
                                            HumanIDGenerator.humanList.get(child).setInfectionRate(rate*0.6);
                                            
                                            addOrDisplay(sdf.format(cal.getTime()), processingBuildingLog.getName(), child, parent, HumanIDGenerator.humanList.get(child).getInfectionRate());
                                            
                                            buildTree(child, rate*0.6*0.9, cal.getTime());
                                            break;
                                        case "Adult":
                                            HumanIDGenerator.humanList.get(child).setInfectionRate(rate*0.5);
                                            
                                            addOrDisplay(sdf.format(cal.getTime()), processingBuildingLog.getName(), child, parent, HumanIDGenerator.humanList.get(child).getInfectionRate());

                                            buildTree(child, rate*0.5*0.9, cal.getTime());
                                            break;
                                        case "Senior Citizen":
                                            HumanIDGenerator.humanList.get(child).setInfectionRate(rate*0.7);
                                            
                                            addOrDisplay(sdf.format(cal.getTime()), processingBuildingLog.getName(), child, parent, HumanIDGenerator.humanList.get(child).getInfectionRate());
                                            
                                            buildTree(child, rate*0.7*0.9, cal.getTime());
                                            break;
                                        default:
                                    }

                                } else {
                                    switch(HumanIDGenerator.humanList.get(child).getAge()){
                                        case "Children":
                                            HumanIDGenerator.humanList.get(child).setInfectionRate(rate*0.85);
                                            
                                            addOrDisplay(sdf.format(cal.getTime()), processingBuildingLog.getName(), child, parent, HumanIDGenerator.humanList.get(child).getInfectionRate());
                                            
                                            buildTree(child, rate*0.85*0.9, cal.getTime());
                                            break;
                                        case "Adult":
                                            HumanIDGenerator.humanList.get(child).setInfectionRate(rate*0.8);
                                            
                                            addOrDisplay(sdf.format(cal.getTime()), processingBuildingLog.getName(), child, parent, HumanIDGenerator.humanList.get(child).getInfectionRate());
                                            
                                            buildTree(child, rate*0.8*0.9, cal.getTime());
                                            break;
                                        case "Senior Citizen":
                                            HumanIDGenerator.humanList.get(child).setInfectionRate(rate*0.9);
                                            
                                            addOrDisplay(sdf.format(cal.getTime()), processingBuildingLog.getName(), child, parent, HumanIDGenerator.humanList.get(child).getInfectionRate());
                                            
                                            buildTree(child, rate*0.9*0.9, cal.getTime());
                                            break;
                                        default:
                                    }
                                }
                            }

                        } catch(NumberFormatException nfe) {}
                    }
                }    
            }
        }
    }
    
    public void addOrDisplay(String date, String place, int child, int parent, double infectionRate) {
        // UPDATE TREE
        TreeNode.treeKey.get(String.format("%05d", parent)).addChild(String.format("%05d", child), String.format("[%s @ %s] %.3f", date, place, infectionRate));
        // UPDATE TREE
    }
    
}