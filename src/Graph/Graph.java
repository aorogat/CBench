package Graph;

import java.util.ArrayList;
import java.util.List;

public class Graph 
{
    
    public ArrayList<Edge> edges = new ArrayList<>();
    public ArrayList<String> vertices = new ArrayList<>();

    private ArrayList<Integer> verticesDegrees = new ArrayList();

    public ArrayList<Integer> getVerticesDegrees() {
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < edges.size(); j++) {
                if (vertices.get(i).equals(edges.get(j).source)
                        || vertices.get(i).equals(edges.get(j).destination)) {
                    try {
                        verticesDegrees.set(i, verticesDegrees.get(i) + 1);
                    } catch (Exception e) {
                        verticesDegrees.add(1);
                    }
                }
            }
        }
        return verticesDegrees;
    }

   
    // adjacency list 
    private ArrayList<String>[] adjList;

    // add edge from u to v 
    public void addEdge(String u, String v) {
        // Add v to u's list. 
        adjList[vertices.indexOf(u)].add(v);
    }

    private void buildAdjList() {
        if (adjList == null) {
            adjList = new ArrayList[vertices.size()];
        }

        for (int i = 0; i < adjList.length; i++) {
            if (adjList[i] == null) {
                adjList[i] = new ArrayList<>();
            }
        }

        for (Edge edge : edges) {
            if(!adjList[vertices.indexOf(edge.source)].contains(edge.destination))
                addEdge(edge.source, edge.destination);
            if(!adjList[vertices.indexOf(edge.destination)].contains(edge.source))
                addEdge(edge.destination, edge.source);
        }
    }

    // Prints all paths from 
    // 's' to 'd' 
    public ArrayList<List<String>> getAllPaths(String s, String d) {
        buildAdjList();

        ArrayList<List<String>> paths = new ArrayList<>();

        boolean[] isVisited = new boolean[vertices.size()];
        ArrayList<String> pathList = new ArrayList<>();

        //add source to path[] 
        pathList.add(s);

        //Call recursive utility 
        getAllPathsUtil(s, d, isVisited, pathList, paths);
       
        return paths;
    }

    // A recursive function to print 
    // all paths from 'u' to 'd'. 
    // isVisited[] keeps track of 
    // vertices in current path. 
    // localPathList<> stores actual 
    // vertices in the current path 
    private ArrayList<List<String>> getAllPathsUtil(String u, String d, boolean[] isVisited, 
            List<String> localPathList, ArrayList<List<String>> paths) {
        
        // Mark the current node 
        isVisited[vertices.indexOf(u)] = true;

        if (u.equals(d)) {
            List<String> l = new ArrayList<>();
            for (int i = 0; i < localPathList.size(); i++) {
                l.add(localPathList.get(i));
            }
            if(!paths.contains(l))
                paths.add(l);
            // if match found then no need to traverse more till depth 
            isVisited[vertices.indexOf(u)] = false;
            return paths;
        }

        // Recur for all the vertices 
        // adjacent to current vertex 
        for (String i : adjList[vertices.indexOf(u)]) {
            if (!isVisited[vertices.indexOf(i)]) {
                // store current node 
                // in path[] 
                localPathList.add(i);
                getAllPathsUtil(i, d, isVisited, localPathList, paths);

                // remove current node 
                // in path[] 
                localPathList.remove(i);
            }
        }

        // Mark the current node 
        isVisited[vertices.indexOf(u)] = false;
        return paths;
    }

}
