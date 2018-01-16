/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import App.peristance.ShortestPathGraph;
import com.sun.org.apache.xpath.internal.operations.Equals;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;
//import java.util.Vector;

/**
 *
 * @author chrisrk192
 */
public class Graph {
    private static long visitNum = 0;
    private App.peristance.Cashe myCashe;

    public Graph(App.peristance.Cashe aCashe) {
        myCashe = aCashe;
    }
    
    /**
     * Implementation of Dijkstra's shortest path algorithm to find the fastest
     *      path between two pages. 
     * @param from
     * @param to
     * @return 
     */
    public ArrayList<Page> FindDijkstra(String from, String to){
        
       
        ShortestPathGraph path;
        File dir = new File(App.peristance.Persist.getDir());
        File [] f = dir.listFiles();
        ArrayList<String> fNames = new ArrayList();
        for(int i = 0; i < f.length;i++){
            fNames.add(f[i].getName());//sets the names to a parallel array for easy name loop up
            //System.out.println("Adding "+f[i].getName());
        }
       
        int strtIndex = fNames.indexOf(from+".ser");
        int endIndex = fNames.indexOf(to+".ser");
        //check and see if the cashe has a shortest path 
        if((path = myCashe.getSavedPath(from)) == null){
            path = new ShortestPathGraph(from, f.length);
            for (int i = 0; i < f.length; i++) {
                path.dist[i] = Double.MAX_VALUE;
            }

            path.dist[strtIndex] = 0;//initial distance to 0
            int c = 0;
            for (int i = 0; i < path.dist.length; i++) {
                System.out.println("App.Graph.FindDijkstra() main for loop "+i+" of "+path.dist.length);
                int next = FindMinVertex(path.dist, path.visited); 
                if(next == -1)
                    break;
                path.visited[next] = true;
                ArrayList<SimStructure> n = FindLinks(fNames, fNames.get(next));//G.neighbors (next);
                if(n==null)
                    n = new ArrayList<>();
                for (int j=0; j<n.size(); j++) {
                    //System.out.println("App.Graph.FindDijkstra() Link for loop"+j + " of " + n.size());
                    SimStructure v = n.get(j);
                    Page p = myCashe.getDownloadedPage(fNames.get(next).replace(".ser", ""));
                    Page q = null;
                    Double sim;
                    //checks to see if the similarity is already calculated
                    if((sim = p.links.get(v.yCount).weight) <= 0)   {     
                        System.out.println("p="+p.title+"HAD TO CALCULATE A SIMILARITY");
                        q = myCashe.getDownloadedPage(fNames.get(v.xCount).replace(".ser", ""));
                        //System.out.println("q="+q.title);                  

                        sim = Similarity(p.text,q.text);
                    }
                    double d = path.dist[next] + (1-sim);
                    if (path.dist[v.xCount] > d) {
                        path.dist[v.xCount] = d;
                        path.pred[v.xCount] = next;
                    }
                }
            }
            //save the arrays
            myCashe.savePath(path);
        }//else(it has been loaded)
    
        //System.out.println("Done. Finding Path Back");
        //return the arraylist of pages
        ArrayList<Page> pages = new ArrayList<>();
        int i = endIndex;

        while (i != strtIndex) {

            Page temp = myCashe.getDownloadedPage(fNames.get(i).replace(".ser", ""));
            //System.out.println("back +="+temp.title);
            pages.add(0, temp);
            i = path.pred[i];
            if(i == endIndex){//can cause errors if the paths dont connect
                break;
            }
        }
        if(path.pred[endIndex] == 0)
            pages.remove(0);
        pages.add(0,myCashe.getDownloadedPage(fNames.get(strtIndex).replace(".ser", "")));
        //System.out.println(pages);
        System.out.println("//////the path/////");
        for (int j = 0; j < pages.size(); j++) {
            System.out.println(pages.get(j).title);            
        }
        //System.out.println("//////the path/////");
        return pages;
        
    }
    
    /**
     * Locates the smallest vertex 
     * @param dist
     * @param visited
     * @return 
     */
    private int FindMinVertex(double [] dist, boolean [] visited){
        //System.out.println("App.Graph.FindMinVertex()");
        double smallest = Double.MAX_VALUE;//initially at max
        int minIndex = -1;//error on -1
        for (int i=0; i < dist.length; i++) {
            if (!visited[i] && dist[i] < smallest) {
                minIndex = i; smallest = dist[i];
            }
        }
        return minIndex;
    }
    
    /**
     * Finds the neighbors of the passed page title src
     * where sim structures hold x= neighbor index for graph
     * and y = index of that page in the pages links arraylist
     * @param fNames
     * @param src
     * @return 
     */
    private ArrayList<SimStructure> FindLinks(ArrayList<String> fNames,String src){
        Page p = myCashe.getDownloadedPage(src.replace(".ser", ""));
        if(p == null)
            return null;
        ArrayList<SimStructure> neighbors = new ArrayList();
        for (int i = 0; i < p.links.size(); i++) {
            Edge e = p.links.get(i);
            int ind = fNames.indexOf(e.dest+".ser");
            if(ind > -1){
                SimStructure s = new SimStructure(src);
                s.xCount = ind;
                s.yCount = i;
                neighbors.add(s);
            }    
        }
        //System.out.println("Num NeighBors: " +neighbors.size());
        return neighbors;
    }
    //returns the page to find, and all the parents are set leading back to the from page
    
    public int BuildSpanningTrees(){
        File dir = new File(App.peristance.Persist.getDir());
        File [] pages = dir.listFiles();
        return SpanningTree.CountSpanningTrees(pages);//pq);
    }
    
    /**
     * Similarity Metric. This function implements a cosign similarity algorithm
     *      to find the similarity of word usage in string x and y
     * @param x
     * @param y
     * @return between -1 and 1
     */
    public double Similarity(String x, String y){
        ArrayList<SimStructure> wordlist = new ArrayList<>();
        if(x == null || y == null)
            return 0.0;
        //Step-1: Identify all distinct words in both texts.
        //Step-2: identify the frequency of occurrences of these words in both text and treat it as vector.
        //Step-3: apply cosine similarity function
        
        String[] xA = x.split(" ");
        String[] yA = y.split(" ");
        
        //identify all distinct words in X and count their frequency
        SimStructure tempSim;
        for(int i = 0; i < xA.length; i++){
            tempSim = new SimStructure(xA[i]);
            if(wordlist.contains(tempSim)){
                wordlist.get(wordlist.indexOf(tempSim)).xCount++;
            } else{
                tempSim.xCount++;
                wordlist.add(tempSim);
            }
        }
        //identify all distinct words in Y not already added and count their frequency
        for(int i = 0; i < yA.length; i++){
            tempSim = new SimStructure(yA[i]);
            if(wordlist.contains(tempSim)){
                wordlist.get(wordlist.indexOf(tempSim)).yCount++;
            } else{
                tempSim.yCount++;
                wordlist.add(tempSim);
            }
        }
        //perform the cosign similarity
        //(sum of _x * _y)/(sqrRoot(pow(sum _x,2)* pow(sum _x,2)))
        double numerator = 0;
        double denom_X = 0;
        double denom_Y = 0;
        double similariy;
        SimStructure c;
        for(int i = 0; i < wordlist.size(); i++){
            c = wordlist.get(i);
            numerator += c.xCount*c.yCount;
            denom_X += Math.pow(c.xCount,2.0);
            denom_Y += Math.pow(c.yCount,2.0);
        }
        similariy = numerator/Math.sqrt(denom_X*denom_Y);
        similariy = Math.pow(similariy, 2);
        return similariy;  
    }
    
    /**
     * Helper class for the similarity function
     * Keeps track of the count of word, key, in each of 2 strings
     */
    private class SimStructure{
        public String key;
        public int xCount;
        public int yCount;

        public SimStructure(String arg) {
            key = arg;
            xCount = 1;
            yCount = 1;
        }
        @Override
        public boolean equals(Object o){
            if(o == null)
                return false;
            if(key.compareTo(((SimStructure)o).key) == 0)
                return true;
            return false; 
        }  
    }
}
