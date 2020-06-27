/* [Update V2]
 * - Using HashMap in a HashMap
 * - Might need amendment soon   
 */
package Simulation;

import SourceDS.LinkedList;
import java.util.HashMap;
import java.util.Set;

public class BuildingLog {
    
    // Building name & its log
    private HashMap<String, LinkedList<String>> todayLog = new HashMap<>();
    
    // Date & HashMap above
    private HashMap<String, HashMap<String, LinkedList<String>>> wholeLog = new HashMap<>();
    
    public void generate(Human h, String place, String time, String date) {
        if(todayLog.containsKey(place)){
            todayLog.get(place).addLastNode('[' + h.getHumanID() + "] " + time);
        } else {
            LinkedList<String> tmp = new LinkedList<>(place);
            todayLog.put(place, tmp);
            todayLog.get(place).addLastNode('[' + h.getHumanID() + "] " + time);
        }
    }
    
    // Must new log for each day
    public void newLog(){
        todayLog = new HashMap<>();
    }
    
    // Must save log for each day after all the generation
    public void saveLog(String date){
        wholeLog.put(date, todayLog);
    }
    
    // For TRACER to get the log for that building on a particular date
    public LinkedList getLogBy(String date, String place){
        if(wholeLog.containsKey(date)){
            if(wholeLog.get(date).containsKey(place)){
                return wholeLog.get(date).get(place);
            }
        }
        return null;
    }
    
    public void showListForToday(String today){
        Set<String> keys = todayLog.keySet();
        System.out.println("\n======================================================================\n" + today);
        for(String place : keys) {
            todayLog.get(place).showBuidlingList();
            System.out.println();
        }
    }
}
