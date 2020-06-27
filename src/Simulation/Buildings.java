/* [NO FURTHER CHANGES NEEDED]
 * To generate list of buildings 
 */
package Simulation;

import java.util.Arrays;
import java.util.List;

public class Buildings {
    
    public final static String[] Grocery = {"SlowMart", "Fibre", "GayaGrocery"};
    public final static String[] Company = {"Orange", "AccSol", "Petrocad"};
    public final static String[] Restaurant = {"MoonBucks", "Ali's Nasi Kandar", "FoodCourt"};
    public final static String[] PublicTransportation = {"Airport", "MRT Station", "KTM Station"};
    public final static String[] Bank = {"JanBank", "JJPTRv2", "RHP"};
    public final static String[] Education = {"Kindergarden", "Primary", "Secondary"};
    public final static String[] Medical = {"Clinic", "Hospital", "Pharmarcy"};
    public final static String[] ShoppingCentre = {"IKEY", "Moonlings", "2 Akhir"};
    public final static String[] PublicPlaces = {"Library", "Park", "Museum"};
    public final static String[] PublicService = {"Police Station", "Fire Station", "Post Office"};
    final static int totalBuildings = 30;
    
    // TRANSPORTATION GOING TO WORK
    final static String[] LocalTransportPlaces = {"MRT Station", "KTM Station"};

    // FREE TIME PLACE
    final static String[] StudentPlaces = {"MoonBucks", "Library", "IKEY", "Moonlings", "2 Akhir"};
    final static String[] TeacherPlaces = {"Library", "SlowMart", "Fibre", "GayaGrocery", "Pharmarcy"};
    final static String[] EntertainmentPlaces = {"Park", "Museum", "Library", "IKEY", "Moonlings", "2 Akhir"};
    final static String[] CommonPlaces = {"SlowMart", "Fibre", "GayaGrocery", "MoonBucks", "Ali's Nasi Kandar", "FoodCourt", "JanBank", "JJPTRv2", "RHP", "Police Station", "Post Office", "Pharmarcy"};

    // WORKING PLACE
    final static String[] ManagerPlaces = {"SlowMart", "Fibre", "GayaGrocery", "Orange", "AccSol", "Petrocad", "MoonBucks", "Ali's Nasi Kandar", "FoodCourt", "Airport", "MRT Station", "KTM Station", "JanBank", "JJPTRv2", "RHP", "Clinic", "Hospital", "Pharmarcy", "IKEY", "Moonlings", "2 Akhir", "Library", "Park", "Museum", "Post Office", };
    final static String[] ClerkPlaces = {"Orange", "AccSol", "Petrocad", "Airport", "MRT Station", "KTM Station", "JanBank", "JJPTRv2", "RHP", "Kindergarden", "Primary", "Secondary", "Clinic", "Hospital", "Pharmarcy", "IKEY", "Moonlings", "2 Akhir", "Library", "Museum", "Post Office"};
    final static String[] CashierPlaces = {"SlowMart", "Fibre", "GayaGrocery", "Orange", "MoonBucks", "Ali's Nasi Kandar", "FoodCourt", "Airport", "MRT Station", "KTM Station", "Clinic", "Hospital", "Pharmarcy", "IKEY", "Moonlings", "2 Akhir", "Museum", "Post Office"};
    final static String[] maskNeededBeforeMCO = {"Clinic", "Pharmarcy", "Hospital"};
    final static List<String> maskNeededBeforeMCOList = Arrays.asList(maskNeededBeforeMCO);
    
}
