/* [Update V2]
 * - Removed saving log file
 */ 

package Simulation;

import java.util.Random;

public class HumanActivityLog {

    Random r = new Random();
    Buildings li = new Buildings();
    public static BuildingLog bl = new BuildingLog();
    
    public void newBuildingLog(){
        bl.newLog();
    }
    
    public void saveBuildingLog(){
        bl.saveLog(ASimulator.TIME.getDate());
    }
    
    public void showBuildingList() {
        bl.showListForToday(ASimulator.TIME.getDate());
    }

    private void setPlaceToGo(Human h, String place, String time) {
        bl.generate(h, place, time, ASimulator.TIME.getDate());
        h.getLog().addLastNode(place + " [" + time + ']');
    }

    public void generateLog(Human h, int day, String time) {
        if(h.getIfInfected()) return;
        int HOUR = ASimulator.TIME.getH();
        String placeToGo;

        switch (h.getOccupation()) {
            // STUDENT
            case "Student":
                switch (day) {
                    case 5:
                        if (h.getWorkplace().equals("Secondary")) {
                            if (HOUR > 15 && HOUR < 17) {
                                int ranNum = r.nextInt(10);
                                if (ranNum < 3) {
                                    placeToGo = li.LocalTransportPlaces[0];
                                    setPlaceToGo(h, placeToGo, time);
                                    placeToGo = li.StudentPlaces[r.nextInt(li.StudentPlaces.length)];
                                    setPlaceToGo(h, placeToGo, time);
                                }
                            }
                        }
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        if (HOUR == 5) {
                            if (!h.getWorkplace().equals("Kindergarden")) {
                                int ranNum = r.nextInt(10);
                                if (ranNum < 3) {
                                    placeToGo = li.LocalTransportPlaces[0];
                                    setPlaceToGo(h, placeToGo, time);
                                }
                            }
                        }
                        if (HOUR >= 6 && HOUR < 8) {
                            int ranNum = r.nextInt(10);
                            if (ranNum <= 8) {
                                placeToGo = h.getWorkplace();
                                setPlaceToGo(h, placeToGo, time);
                            }
                        }
                        break;
                    case 6:
                    case 7:
                        if (h.getWorkplace().equals("Secondary")) {
                            if (HOUR > 10 && HOUR <= 12) {
                                int ranNum = r.nextInt(10);
                                if (ranNum == 0) {
                                    placeToGo = li.EntertainmentPlaces[r.nextInt(li.EntertainmentPlaces.length)];
                                    setPlaceToGo(h, placeToGo, time);
                                }
                            }
                            if (HOUR > 17 && HOUR <= 19) {
                                int ranNum = r.nextInt(10);
                                if (ranNum < 2) {
                                    placeToGo = li.EntertainmentPlaces[r.nextInt(li.EntertainmentPlaces.length)];
                                    setPlaceToGo(h, placeToGo, time);
                                }
                            }
                        }
                        break;
                }
                break;

            // TEACHER
            case "Teacher":
                switch (day) {
                    case 5:
                        if (HOUR >= 10 && HOUR < 12){
                            int ranNum = r.nextInt(10);
                            if (ranNum == 9) {
                                placeToGo = li.Restaurant[r.nextInt(li.Restaurant.length)];
                                setPlaceToGo(h, placeToGo, time);
                            }
                        }
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        if (HOUR >= 6 && HOUR < 8) {
                            placeToGo = h.getWorkplace();
                            setPlaceToGo(h, placeToGo, time);
                        }
                        if (HOUR >= 10 && HOUR < 12){
                            int ranNum = r.nextInt(10);
                            if (ranNum <= 3) {
                                placeToGo = li.CommonPlaces[r.nextInt(li.CommonPlaces.length)];
                                setPlaceToGo(h, placeToGo, time);
                            } else if (ranNum == 9) {
                                placeToGo = li.Restaurant[r.nextInt(li.Restaurant.length)];
                                setPlaceToGo(h, placeToGo, time);
                            }
                        }
                        if (HOUR >= 16 && HOUR < 18){
                            int ranNum = r.nextInt(10);
                            if (ranNum <= 2) {
                                placeToGo = li.CommonPlaces[r.nextInt(li.CommonPlaces.length)];
                                setPlaceToGo(h, placeToGo, time);
                            }
                        }
                        break;
                    case 6:
                    case 7:
                        if (HOUR >= 10 && HOUR < 12){
                            int ranNum = r.nextInt(10);
                            if (ranNum <= 3) {
                                placeToGo = li.CommonPlaces[r.nextInt(li.CommonPlaces.length)];
                                setPlaceToGo(h, placeToGo, time);
                            }
                        }
                        if (HOUR > 17 && HOUR <= 19) {
                            int ranNum = r.nextInt(10);
                            if (ranNum <= 2) {
                                placeToGo = li.EntertainmentPlaces[r.nextInt(li.EntertainmentPlaces.length)];
                                setPlaceToGo(h, placeToGo, time);
                            } else if (ranNum >= 5){
                                placeToGo = li.PublicPlaces[r.nextInt(li.PublicPlaces.length)];
                                setPlaceToGo(h, placeToGo, time);
                            }
                        }
                        break;
                }
                break;

            // RETIRED & HOUSEMAKER
            case "Retired":
            case "Housemaker":
                switch (day) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        if (HOUR >= 10 && HOUR < 12){
                            int ranNum = r.nextInt(10);
                            if (ranNum <= 3) {
                                placeToGo = li.CommonPlaces[r.nextInt(li.CommonPlaces.length)];
                                setPlaceToGo(h, placeToGo, time);
                            }
                        }
                        if (HOUR >= 12 && HOUR < 14) {
                            int ranNum = r.nextInt(10);
                            if (ranNum <= 4) {
                                placeToGo = li.ShoppingCentre[r.nextInt(li.ShoppingCentre.length)];
                                setPlaceToGo(h, placeToGo, time);
                            }
                        }
                        if (HOUR >= 16 && HOUR < 18){
                            int ranNum = r.nextInt(10);
                            if (ranNum <= 3) {
                                placeToGo = li.Grocery[r.nextInt(li.Grocery.length)];
                                setPlaceToGo(h, placeToGo, time);
                            }
                        }
                        break;
                    case 6:
                    case 7:
                        if (HOUR >= 10 && HOUR < 12){
                            int ranNum = r.nextInt(10);
                            if (ranNum <= 7) {
                                placeToGo = li.Grocery[r.nextInt(li.Grocery.length)];
                                setPlaceToGo(h, placeToGo, time);
                            }
                        }
                        if (HOUR >= 14 && HOUR < 16) {
                            int ranNum = r.nextInt(10);
                            if (ranNum <= 6) {
                                placeToGo = li.ShoppingCentre[r.nextInt(li.ShoppingCentre.length)];
                                setPlaceToGo(h, placeToGo, time);
                            }
                        }
                        
                        break;
                }
                break;

            // ENGINEER, TECHNICIAN, ACCOUNTANT, CLERK
            case "Clerk":
            case "Engineer":
            case "Technician":
            case "Accountant":
                switch (day) {
                    case 5:
                        if (HOUR >= 19 && HOUR < 22) {
                            int ranNum = r.nextInt(10);
                            if (ranNum >= 6) {
                                placeToGo = li.EntertainmentPlaces[r.nextInt(li.EntertainmentPlaces.length)];
                                setPlaceToGo(h, placeToGo, time);
                            }
                        }
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        if (HOUR >= 5 && HOUR < 7) {
                            if (!h.getWorkplace().equals("KTM Station") && !h.getWorkplace().equals("MRT Station")) {
                                int ranNum = r.nextInt(10);
                                if (ranNum < 5) {
                                    placeToGo = li.LocalTransportPlaces[r.nextInt(li.LocalTransportPlaces.length)];
                                    setPlaceToGo(h, placeToGo, time);
                                }
                            }
                        }
                        if (HOUR >= 8 && HOUR < 10) {
                            placeToGo = h.getWorkplace();
                            setPlaceToGo(h, placeToGo, time);
                        }
                        if (HOUR >= 12 && HOUR < 14) {
                            int ranNum = r.nextInt(10);
                            if (ranNum < 6) {
                                placeToGo = li.Restaurant[r.nextInt(li.Restaurant.length)];
                                setPlaceToGo(h, placeToGo, time);
                            }
                        }
                        if (HOUR == 10 || HOUR == 18) {
                            int ranNum = r.nextInt(10);
                            if (ranNum <= 3) {
                                placeToGo = li.CommonPlaces[r.nextInt(li.CommonPlaces.length)];
                                setPlaceToGo(h, placeToGo, time);
                            }
                        }
                        break;
                    case 6:
                    case 7:
                        if (HOUR == 10 || HOUR == 18) {
                            int ranNum = r.nextInt(10);
                            if (ranNum <= 1) {
                                placeToGo = li.CommonPlaces[r.nextInt(li.CommonPlaces.length)];
                                setPlaceToGo(h, placeToGo, time);
                            }
                        }
                        if (HOUR >= 12 && HOUR < 14) {
                            int ranNum = r.nextInt(10);
                            if (ranNum <= 2) {
                                placeToGo = li.EntertainmentPlaces[r.nextInt(li.EntertainmentPlaces.length)];
                                setPlaceToGo(h, placeToGo, time);
                            }
                        }
                        if (HOUR >= 19 && HOUR < 21) {
                            int ranNum = r.nextInt(10);
                            if (ranNum <= 5) {
                                placeToGo = li.EntertainmentPlaces[r.nextInt(li.EntertainmentPlaces.length)];
                                setPlaceToGo(h, placeToGo, time);
                            }
                        }
                        break;
                }
                break;

            // CHEF, WAITER, CASHIER, MANAGER, BARISTA, BAKER, SHOPPING CENTRE RETAILER
            case "Chef":
            case "Waiter":
            case "Baker":
            case "Barista":
            case "Cashier":
            case "Manager":
            case "Shopping Centre Retailer":
                switch (day) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                        if (HOUR >= 6 && HOUR < 8) {
                            int ranNum = r.nextInt(10);
                            if (ranNum < 4) {
                                placeToGo = li.LocalTransportPlaces[r.nextInt(li.LocalTransportPlaces.length)];
                                setPlaceToGo(h, placeToGo, time);
                            }
                        }
                        if (HOUR >= 8 && HOUR < 10) {
                            int ranNum = r.nextInt(10);
                            if (ranNum <= 8) {
                                placeToGo = h.getWorkplace();
                                setPlaceToGo(h, placeToGo, time);
                            }
                        }
                        if (HOUR == 13 || HOUR == 17) {
                            int ranNum = r.nextInt(10);
                            if (ranNum <= 3) {
                                placeToGo = li.CommonPlaces[r.nextInt(li.CommonPlaces.length)];
                                setPlaceToGo(h, placeToGo, time);
                            }
                        }
                        break;
                }
                break;

            // POLICEMAN, FIREFIGHTER
            case "Firefighter":
            case "Policeman":
                switch (day) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        if (HOUR >= 8 && HOUR < 10) {
                            int ranNum = r.nextInt(10);
                            if (ranNum <= 8) {
                                placeToGo = h.getWorkplace();
                                setPlaceToGo(h, placeToGo, time);
                            }
                        }
                        if (HOUR == 13) {
                            int ranNum = r.nextInt(10);
                            if (ranNum <= 2) {
                                placeToGo = li.CommonPlaces[r.nextInt(li.CommonPlaces.length)];
                                setPlaceToGo(h, placeToGo, time);
                            }
                        }
                        break;
                    case 6:
                    case 7:
                        if (HOUR >= 8 && HOUR < 10) {
                            int ranNum = r.nextInt(10);
                            if (ranNum <= 8) {
                                placeToGo = h.getWorkplace();
                                setPlaceToGo(h, placeToGo, time);
                            }
                        }
                        if (HOUR >= 17 && HOUR < 19) {
                            int ranNum = r.nextInt(10);
                            if (ranNum <= 1) {
                                placeToGo = li.CommonPlaces[r.nextInt(li.CommonPlaces.length)];
                                setPlaceToGo(h, placeToGo, time);
                            }
                        }
                        break;
                }
                break;

            // MEDIC
            case "Medic":
                switch (day) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        if (HOUR >= 12 && HOUR < 14) {
                            int ranNum = r.nextInt(10);
                            if (ranNum <= 2) {
                                placeToGo = li.Grocery[r.nextInt(li.Grocery.length)];
                                setPlaceToGo(h, placeToGo, time);
                            }
                        }
                    case 6:
                    case 7:
                        if (HOUR >= 7 && HOUR < 9) {
                            int ranNum = r.nextInt(10);
                            if (ranNum <= 8) {
                                placeToGo = h.getWorkplace();
                                setPlaceToGo(h, placeToGo, time);
                            }
                        }
                        
                        break;
                }
                break;
        }
    }
}