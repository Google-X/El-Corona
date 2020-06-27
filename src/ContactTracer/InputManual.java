/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ContactTracer;

import Simulation.HumanIDGenerator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class InputManual {
    
    static Tracer tracer = new Tracer();
    static HumanIDGenerator generator = new HumanIDGenerator();
    static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    static SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

    public void find() throws ParseException {
//      generator.getHumanID();
        Scanner s = new Scanner(System.in);
        
        while(true){
            System.out.println("Manually trace back the possible infected Human IDs");
            System.out.print("Eg. Input: humanID dateOfInfection(DD-MM-YYYY): ");
            String get = s.nextLine();
            
            String[] data = get.split("\\s+");
            int humanID = -1;
            
            try{
                humanID = Integer.parseInt(data[0]);
            } catch (NumberFormatException pe){
                System.err.println("Try type slowly on the human ID again.");
                continue;
            }
            
            generator.humanList.get(humanID).setInfected();
            String date = data[1];
            Date check = null;
            
            try{
                check = sdf.parse(date);
            } catch (ParseException pe){
                try{
                    check = sdf2.parse(date);
                } catch (ParseException pe2){
                    System.err.println("Wear a glasses, see the format (DD-MM-YYYY)");
                    continue;
                }
            }

            List[] possibleInfectedPlacesAndHumanID = placesAndHumanIDS(humanID, check);

            System.out.println("Tree of infected Human IDs");
            tracer.getTree(humanID, 1.0, check);
            tracer.getClosedRelationTree(humanID, 1.0);
            break;
        }
        
    }
    
    public List[] placesAndHumanIDS(int infectedHumanID, Date d) throws ParseException{
        
        System.out.println("\nCONTACT TRACING STATUS: STARTED");
        System.out.println("============================================================");
        System.out.println(generator.humanList.get(infectedHumanID).basicInfo());
        try{
            if(generator.relationship.getEdge(infectedHumanID).equals("[]")){
                System.out.println("Closed Relationship: " + generator.humanList.get(infectedHumanID).getRelation().toString());
            } else {
                System.out.println("Closed Relationship: " + generator.relationship.getEdge(infectedHumanID));
            }
        } catch(NullPointerException nep){
            System.out.println("Closed Relationship: N/A");
        }

        // [0]: Infected places     [1]: Possible infected human ID
        List[] possibleInfectedPlacesAndHumanID = tracer.getPossibleInfectedPlacesAndHumanID(infectedHumanID, d);

        System.out.printf("\nPlaces Human ID %05d went to for the past 14 days: ", infectedHumanID);
        System.out.println(possibleInfectedPlacesAndHumanID[0].toString());

        System.out.print("\nPossible infected Human IDs: ");
        System.out.println(possibleInfectedPlacesAndHumanID[2].toString());
        
        return possibleInfectedPlacesAndHumanID;
    }
}
