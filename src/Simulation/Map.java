/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulation;

import java.util.*;

/**
 *
 * @author acer
 */
public class Map {

    Random r = new Random();
    int count;
    int link;
    static boolean[][] mapList;
    int currentPlace;
    int currentCategory;
    int transportPlacesNum;
    final int totalBuildings;
    static ArrayList buildingList;
    ArrayList transportation = new ArrayList();

    public Map() {
        count = 0;
        link = 0;
        Collections.addAll(transportation, Buildings.LocalTransportPlaces);
        transportPlacesNum = Buildings.totalBuildings / 15;
        totalBuildings = Buildings.totalBuildings + transportation.size() * transportPlacesNum;
        mapList = new boolean[totalBuildings][totalBuildings];
        buildingList = new ArrayList();
    }
    public void createBuildingList() {
        Collections.addAll(buildingList, Buildings.Grocery);
        Collections.addAll(buildingList, Buildings.Company);
        Collections.addAll(buildingList, Buildings.Restaurant);
        Collections.addAll(buildingList, Buildings.PublicTransportation);
        Collections.addAll(buildingList, Buildings.Bank);
        Collections.addAll(buildingList, Buildings.Education);
        Collections.addAll(buildingList, Buildings.Medical);
        Collections.addAll(buildingList, Buildings.ShoppingCentre);
        Collections.addAll(buildingList, Buildings.PublicPlaces);
        Collections.addAll(buildingList, Buildings.PublicService);
        for (int i = 0; i < transportPlacesNum; i++) {
            Collections.addAll(buildingList, Buildings.LocalTransportPlaces);
        }
        Collections.shuffle(buildingList);
        build();
    }

    public void build() {
        int[] transportCross = new int[transportation.size()];
        int[] transportIndex = new int[transportPlacesNum + 1];
        //link same type local transportation place
        for (int type = 0; type < transportation.size(); type++) {
            for (int i = 0, j = 0; i < transportIndex.length; j++) {
                if (buildingList.get(j).equals(transportation.get(type))) {
                    transportIndex[i] = j;
                    i++;
                }
            }
            transportCross[type] = transportIndex[0];
            createTransportLink(transportIndex);
        }
        //link different transport 
        createTransportLink(transportCross);
        rename();
        int[] buildingLinkNum = new int[totalBuildings];
        int linkTransport = transportPlacesNum + 1;
        int createBuildingLink = 0;
        //random link the building
        while(createBuildingLink < totalBuildings){
            int link = 2 + r.nextInt(2);
            if (transportation.contains(buildingList.get(createBuildingLink))){
                link = linkTransport;
            }
            int marked = 0;
            for (int i = 0; i < totalBuildings; i++){
                if (mapList[createBuildingLink][i]){
                    marked++;
                }
            }
            if (marked >= link){
                createBuildingLink++;
                continue;
            }
            buildingLinkNum[createBuildingLink] = link;
            boolean[] mapTransport = new boolean[totalBuildings];
            for (int i = 0; i < (link - marked); ){
                int buildingIndex = r.nextInt(totalBuildings);
                if (transportation.contains(buildingList.get(buildingIndex))){
                    mapTransport[buildingIndex] = true;
                }
                if (mapList[createBuildingLink][buildingIndex]){
                    //avoid repeat link
                } else if (buildingIndex == createBuildingLink){
                    //avoid self link
                } else if (transportation.contains(buildingList.get(buildingIndex)) && mapTransport[buildingIndex]){
                    //fix building can only link one transport place
                } else if (buildingLinkNum[buildingIndex] != 0 && buildingLinkNum[buildingIndex] >= 3) {
                    //avoid a building have too much link
                } else {
                    if (transportation.contains(buildingList.get(buildingIndex))){
                        mapTransport[buildingIndex] = true;
                    }
                    buildingLinkNum[buildingIndex] += 1;
                    mapList[createBuildingLink][buildingIndex] = true;
                    mapList[buildingIndex][createBuildingLink] = true;
                    i++;
                }
            }
            createBuildingLink++;
        }
        
    }
    
    public void rename(){
        Object transport;
        for (int i = 0; i < Buildings.LocalTransportPlaces.length; i++){
            for (int j = 0; j < buildingList.size(); j++){
                if (Arrays.stream(Buildings.LocalTransportPlaces).anyMatch(buildingList.get(j)::equals)){
                    transport = buildingList.get(j);
                    for (int k = j + 1, index = 1; k < buildingList.size(); k++){
                        if (buildingList.get(k).equals(transport)){
                            buildingList.remove(k);
                            buildingList.add(k, transport + " " + index);
                            index++;
                        }
                    }
                }
            }
        }
    }

    public void createTransportLink(int[] indexList) {
        for (int i = 0; i < indexList.length; i++) {
            for (int j = 1; j < indexList.length; j++) {
                if (i == j){
                    continue;
                }
                mapList[indexList[i]][indexList[j]] = true;
                mapList[indexList[j]][indexList[i]] = true;
            }
        }
    }
    
    public void showMap(){
        for (int i = 0; i < Buildings.totalBuildings; i++){
            System.out.println(buildingList.get(i) + " -->");
            for (int j = 0; j < totalBuildings; j++){
                if (mapList[i][j] == true){
                    System.out.print(" --> " + buildingList.get(j));
                }
            }
            System.out.println();
        }
    }

    public void printList() {
        //for test print use
        System.out.println();
        for (int i = 0; i < totalBuildings; i++) {
            //System.out.print("| ");
            for (int j = 0; j < totalBuildings; j++) {
                if (mapList[i][j]){
                    System.out.print(1 + " ");
                } else {
                    System.out.print(0 + " ");
                }
            }
            System.out.println();
        }
    }
    
    public void showGUI(){
        
    }
}
