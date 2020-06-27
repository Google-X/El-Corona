/* [Update V2]
 * - Removed the saving file methods
 */
package Simulation;

import java.util.ArrayList;
import java.util.Random;

public class HumanIDGenerator {

    public static final int population = 10000, male = 4500, female = 5500, children = 3500, adult = 4200, seniorCitizen = 2300;
    public static ArrayList<Human> humanList = new ArrayList<>(population);
    public static RelationGraph.Graph relationship = new RelationGraph.Graph(population);
    Random r = new Random();
    
    public void generateID() {
        
        int[] nGender = {male, female};
        String[] gender = {"Male", "Female"};
        int[] nAge = {children, adult, seniorCitizen};
        String[] age = {"Children", "Adult", "Senior Citizen"};

        String[] occupations = {"Student", "Retired", "Teacher", "Housemaker", "Engineer", "Technician", "Accountant", "Chef", "Manager", "Policeman", "Firefighter", "Barista", "Baker", "Clerk", "Medic", "Shopping Centre Retailer", "Cashier", "Waiter"};
        int[] job = {3500, 1800, 500, 1000, 150, 250, 350, 300, 250, 200, 200, 150, 200, 300, 250, 200, 200, 200};
        
        System.out.println("Generating sprites...");
        
    // HUMAN ID
        for (int i = 0; i < population; i++) {
            humanList.add(new Human(String.format("%05d", i)));
        }
        
    // GENDER
        int countM = 0, countF = 0;
        while ((countM + countF) < population) {
            int temp = r.nextInt(gender.length);
            if (temp == 0 && countM < nGender[0]) {
                humanList.get(countM + countF).setGender(gender[temp]);
                countM++;
            } 
            
            if (temp == 1 && countF < nGender[1]) {
                humanList.get(countM + countF).setGender(gender[temp]);
                countF++;
            }
        }
        
    // CHILD, ADULT & SENIOR
        int countC = 0, countA = 0, countS = 0;

        while ((countC + countA + countS) < population) {
            int temp = r.nextInt(age.length);
            // CHILD: 0 - 30%
            if (temp == 0 && countC < nAge[0]) {
                humanList.get(countC + countA + countS).setAge(age[temp]);
                humanList.get(countC + countA + countS).setForgetfulness(r.nextInt(31));
                countC++;
                
            // ADULT: 5 - 50%
            } else if (temp == 1 && countA < nAge[1]) {
                humanList.get(countC + countA + countS).setAge(age[temp]);
                humanList.get(countC + countA + countS).setForgetfulness(r.nextInt(46) + 5);
                countA++;

            // SENIOR: 10 - 70%
            } else if (temp == 2 && countS < nAge[2]) {
                humanList.get(countC + countA + countS).setAge(age[temp]);
                humanList.get(countC + countA + countS).setForgetfulness(r.nextInt(61) + 10);
                countS++;
            }
        }
    // OCCUPATION
    // ALL CHILD = STUDENT
        int index = 0;
        int[] countO = new int[job.length];
        while (index < population) {
            if (humanList.get(index).getAge().equals("Children") && countO[0] < job[0]) {
                humanList.get(index).setOccupation(occupations[0]);
                countO[0]++;
                
                int tempStudent = r.nextInt(10) + 1;
                // Kindergarden
                if(tempStudent <= 1) humanList.get(index).setWorkplace(Buildings.Education[0]);
                // Primary
                else if(tempStudent > 1 && tempStudent <= 4) humanList.get(index).setWorkplace(Buildings.Education[1]);
                // Secondary
                else if(tempStudent > 4) humanList.get(index).setWorkplace(Buildings.Education[2]);
                
            } else if (humanList.get(index).getAge().equals("Senior Citizen") && countO[1] < job[1]) {
                humanList.get(index).setOccupation(occupations[1]);
                countO[1]++;
                // SC no workplace, default: null
                
            } else {
                boolean flag = false;
                do {
                    int temp = r.nextInt(occupations.length - 2) + 2; // -2 and +2 to skip student and retired
                    
                    if (countO[temp] < job[temp]) {                       
                        flag = true;
                        humanList.get(index).setOccupation(occupations[temp]);
                        countO[temp]++;
                        
                        switch(temp){
                            // Teacher
                            case 2:
                                int tempTeacher = r.nextInt(10) + 1;
                                // Kindergarden
                                if(tempTeacher <= 1) humanList.get(index).setWorkplace(Buildings.Education[0]);
                                // Primary
                                else if(tempTeacher > 1 && tempTeacher <= 4) humanList.get(index).setWorkplace(Buildings.Education[1]);
                                // Secondary
                                else if(tempTeacher > 4) humanList.get(index).setWorkplace(Buildings.Education[2]);
                                
                                break;
                                
//                            Housemaker
//                            case 3:
//                                break;
                                
                            // Engineer
                            case 4:
                                int tempEng = r.nextInt(10) + 1;
                                // Orange not Apple Store
                                if(tempEng <= 3) humanList.get(index).setWorkplace(Buildings.Company[0]);
                                // AccSol
                                else if(tempEng == 4) humanList.get(index).setWorkplace(Buildings.Company[1]);
                                // Petrocad
                                else if(tempEng > 4 && tempEng <= 7) humanList.get(index).setWorkplace(Buildings.Company[2]);
                                // Anything in PublicTransportation
                                else humanList.get(index).setWorkplace(Buildings.PublicTransportation[r.nextInt(Buildings.PublicTransportation.length)]);
                                
                                break;
                                
                            // Technician
                            case 5:
                                int tempTech = r.nextInt(10) + 1;
                                // Orange not Apple Store
                                if(tempTech <= 2) humanList.get(index).setWorkplace(Buildings.Company[0]);
                                // AccSol
                                else if(tempTech == 3) humanList.get(index).setWorkplace(Buildings.Company[1]);
                                // Petrocad
                                else if(tempTech > 3 && tempTech <= 5) humanList.get(index).setWorkplace(Buildings.Company[2]);
                                // Anything in PublicTransportation
                                else humanList.get(index).setWorkplace(Buildings.PublicTransportation[r.nextInt(Buildings.PublicTransportation.length)]);
                                
                                break;
                                
                            // Accountant
                            case 6:
                                int tempAcc = r.nextInt(10) + 1;
                                // AccSol
                                if(tempAcc <= 4) humanList.get(index).setWorkplace(Buildings.Company[1]);
                                // JanBank
                                else if(tempAcc > 4 && tempAcc <= 6) humanList.get(index).setWorkplace(Buildings.Bank[0]);
                                // JJPTRv2
                                else if(tempAcc > 6 && tempAcc <= 8) humanList.get(index).setWorkplace(Buildings.Bank[1]);
                                // RHP
                                else if(tempAcc > 8 && tempAcc <= 10) humanList.get(index).setWorkplace(Buildings.Bank[2]);
                                
                                break;
                                
                            // Chef
                            case 7:
                                int tempChef = r.nextInt(10) + 1;
                                
                                if(tempChef <= 3) humanList.get(index).setWorkplace(Buildings.Restaurant[r.nextInt(Buildings.Restaurant.length)]);
                                else humanList.get(index).setWorkplace(Buildings.ShoppingCentre[r.nextInt(Buildings.ShoppingCentre.length)]);
                                break;
                                
                            // Manager
                            case 8:
                                humanList.get(index).setWorkplace(Buildings.ManagerPlaces[r.nextInt(Buildings.ManagerPlaces.length)]);
                                
                                break;
                                
                            // Policeman
                            case 9:
                                humanList.get(index).setWorkplace(Buildings.PublicService[0]);
                                break;
                                
                            // Firefighter
                            case 10:
                                humanList.get(index).setWorkplace(Buildings.PublicService[1]);
                                break;
                                
                            // Barista
                            case 11:
                                int tempBarista = r.nextInt(10) + 1;
                                // Moonbucks
                                if(tempBarista <= 2) humanList.get(index).setWorkplace(Buildings.Restaurant[0]);
                                else humanList.get(index).setWorkplace(Buildings.ShoppingCentre[r.nextInt(Buildings.ShoppingCentre.length)]);
                                break;
                                
                            // Baker
                            case 12:
                                int tempBaker = r.nextInt(10) + 1;
                                // Moonbucks
                                if(tempBaker <= 1) humanList.get(index).setWorkplace(Buildings.Restaurant[0]);
                                else humanList.get(index).setWorkplace(Buildings.ShoppingCentre[r.nextInt(Buildings.ShoppingCentre.length)]);
                                break;
                                
                            // Clerk
                            case 13:
                                humanList.get(index).setWorkplace(Buildings.ClerkPlaces[r.nextInt(Buildings.ClerkPlaces.length)]);
                                break;
                                
                            // Medic
                            case 14:
                                humanList.get(index).setWorkplace(Buildings.Medical[r.nextInt(Buildings.Medical.length)]);
                                break;
                                
                            // Shopping Centre Retailer
                            case 15:
                                humanList.get(index).setWorkplace(Buildings.ShoppingCentre[r.nextInt(Buildings.ShoppingCentre.length)]);
                                break;
                                
                            // Cashier
                            case 16:
                                humanList.get(index).setWorkplace(Buildings.CashierPlaces[r.nextInt(Buildings.CashierPlaces.length)]);
                                break;
                                
                            // Waiter
                            case 17:
                                int tempWaiter = r.nextInt(10) + 1;
                                // Restaurants
                                if(tempWaiter <= 4) humanList.get(index).setWorkplace(Buildings.Restaurant[r.nextInt(Buildings.Restaurant.length)]);
                                else humanList.get(index).setWorkplace(Buildings.ShoppingCentre[r.nextInt(Buildings.ShoppingCentre.length)]);
                                break;
                                
                            default:
                        }
                    }
                } while (flag == false);
            }
            index++;
        }
//      Create their relationship, get the relatives, closed friend ID
        createRelationship();
        
        System.out.println("GENERATING STATUS: [COMPLETED]");
        System.out.println("TOTAL CITIZENS: " + population);
    }
    
    public void createRelationship() {
        for (int i = 0; i < population; i++) {
            int numberOfCloseRelation = r.nextInt(5);
            try{
                while (relationship.getNumOfEdge(i) < numberOfCloseRelation) {
                    int rand = r.nextInt(population);
                    if (humanList.get(i).getOccupation().equals("Retired") 
                     || humanList.get(i).getOccupation().equals("Housemaker")) {
                        if( humanList.get(rand).getOccupation().equals("Retired") 
                         || humanList.get(rand).getOccupation().equals("Housemaker") ){
                            relationship.addEgde(humanList.get(i), humanList.get(rand));
                        }
                    } else if (humanList.get(i).getWorkplace().equals(humanList.get(rand).getWorkplace()) && 
                        humanList.get(i).getOccupation().equals(humanList.get(rand).getOccupation()) ) {
                        relationship.addEgde(humanList.get(i), humanList.get(rand));
                    } else {
                        int choice = r.nextInt(10);
                        if(choice <= 3){
                            relationship.addEgde(humanList.get(i), humanList.get(rand));
                        }
                    }
                }
            } catch (NullPointerException npe){ }
        }
        
        for(int i = 0; i < population; i++){
            String IDs = relationship.getEdge(i);
            humanList.get(i).addRelation(IDs);
        }
        
    }
}