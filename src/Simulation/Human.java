/* [Update V2]
 * - Using HashMap to store whole logs
 * - Might need amendment soon   
 */

package Simulation;

import SourceDS.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class Human {

    private String humanID;
    private String gender;
    private String age;
    private String occupation;
    private String workplace;
    private int forgetfulness;
    private double infectionRate;
    private boolean mask;
    private boolean infected;
    private List<String> relative;
    private LinkedList<String> log;
    private HashMap<String, LinkedList<String>> wholeLog;
    
    // Must new log for each day
    public void newLog(){
        log = new LinkedList<>(humanID);
    }
    
    // Must save log for each day after all the generation
    public void saveLog(String date){
        wholeLog.put(date, log);
    }
    
    public LinkedList<String> getLog() {
        return log;
    }
    
    public LinkedList<String> getLogByDate(String date){
        if(wholeLog.containsKey(date)){
            return wholeLog.get(date);
        }
        return null;
    }
    
    public Human(String HumanID) {
        relative = new ArrayList();
        wholeLog = new HashMap<>();
        humanID = HumanID;
        infectionRate = 0;
        infected = false;
    }

    public Human(String humanID, String gender, String age, int forgetfulness, String occupation, String workplace, String mask, String closedID) {
        this.humanID = humanID;
        this.gender = gender;
        this.age = age;
        this.forgetfulness = forgetfulness;
        this.occupation = occupation;
        this.workplace = workplace;
        if(mask.equals("true")) this.mask = true;
        closedID = closedID.replace("[", "").replace("]", "");
        relative = Arrays.asList(closedID.split(Pattern.quote(" | ")));
        infectionRate = 0;
        infected = false;
        wholeLog = new HashMap<>();
    }
    
    public void setInfectionRate(double rate){
        if(rate > infectionRate) infectionRate = rate;
    }
    
    public double getInfectionRate(){
        return infectionRate;
    }
    
    public void setInfected(){
        infectionRate = 1.0;
        infected = true;
    }
    
    public boolean getIfInfected(){
        return infected;
    }
    public void addRelation(String IDs){
        IDs = IDs.replace("[", "").replace("]", "");
        relative = Arrays.asList(IDs.split(Pattern.quote(" | ")));
    }
    
    public List getRelation(){
        return relative;
    }
    
    public String getHumanID() {
        return humanID;
    }

    public void setHumanID(String humanID) {
        this.humanID = humanID;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public int getForgetfulness() {
        return forgetfulness;
    }

    public void setForgetfulness(int forgetfulness) {
        this.forgetfulness = forgetfulness;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
        if(Buildings.maskNeededBeforeMCOList.contains(workplace)) mask = true;
    }
    
    public boolean getMask(){
        return mask;
    }
    
    public void wearMask(){
        mask = true;
    }
    
    public String basicInfo(){
        return "HUMAN ID: " + humanID + "     GENDER: " + gender + "     AGE CATEGORY: " + age + "     ROLE: " + occupation;
    }
    
}
