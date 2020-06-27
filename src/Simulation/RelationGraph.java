// [No amendment needed. Yet]

package Simulation;

import java.util.LinkedList;

public class RelationGraph {
    
    public static class Edge {

        Human source;
        Human destination;

        public Edge(Human source, Human destination) {
            this.source = source;
            this.destination = destination;
        }
    }

    public static class Graph {

        int vertices;
        public static LinkedList<Edge>[] adjacencylist;
        
        Graph(int vertices) {
            this.vertices = vertices;
            adjacencylist = new LinkedList[vertices];
            //initialize adjacency lists for all the vertices
            for (int i = 0; i < vertices; i++) {
                adjacencylist[i] = new LinkedList<>();
            }
        }
        
        public void addEgde(Human source, Human destination) {
            Edge edge1 = new Edge(source, destination);
            Edge edge2 = new Edge(destination, source);
            adjacencylist[Integer.parseInt(source.getHumanID())].addFirst(edge1); //for directed graph
            adjacencylist[Integer.parseInt(destination.getHumanID())].addFirst(edge2);
        }

        public int getNumOfEdge(int humanID) {
            return adjacencylist[humanID].size();
        }

        public void showGraph() {
            for (int i = 0; i < vertices; i++) {
                LinkedList<Edge> list = adjacencylist[i];
                System.out.printf("Human ID %05d : ", i);
                for (int j = 0; j < list.size(); j++) {
                    System.out.print(list.get(j).destination.getHumanID() + " ");
                }
                System.out.println();
            }
        }

        public String getEdge(int humanID) {
            LinkedList<Edge> edge = adjacencylist[humanID];
            String temp = "[";
            for (int j = 0; j < edge.size(); j++) {
                if(j == edge.size()-1){
                    temp += edge.get(j).destination.getHumanID();
                } else {
                    temp += edge.get(j).destination.getHumanID() + " | ";
                }
            }
            return temp + ']';
        }
    }
}
