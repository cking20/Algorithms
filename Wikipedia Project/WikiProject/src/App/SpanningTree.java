/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author chrisrk192
 */
public class SpanningTree {
    private int[] parent;
    private int[] size;
    private int count;

    public SpanningTree(int m) {
        count = m;
        parent = new int[m];
        size = new int[m];
        //initialize arrays
        for (int i = 0; i < m; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }
    
    /**
     * Tells if two indexes are connected 
     * @param p
     * @param q
     * @return 
     */   
    private boolean Connected(int p, int q) {
        return Find(p) == Find(q);
    }
    
    /**
     * Locates the parent index of p and compresses the path
     * @param p
     * @return 
     */
    private int Find(int p) {
        int aRoot = p;
        //probably could use one loop
        while (aRoot != parent[aRoot]){
            aRoot = parent[aRoot];
        }
        while (p != aRoot) {
            int pp = parent[p];//p's parent
            parent[p] = aRoot;
            p = pp;
        }
        return aRoot;
    }
     
    /**
     * Connects two indexes together with the larger on top so path 
     *      compression can work properly
     * @param p
     * @param q 
     */
    private void Merge(int p, int q) {
        int pRoot = Find(p);
        int qRoot = Find(q);
        if (pRoot == qRoot) //dont union something with itself
            return;
        if (size[pRoot] < size[qRoot]) {//attatch larger to top
            parent[pRoot] = qRoot;
            size[qRoot] += size[pRoot];
        }
        else {
            parent[qRoot] = pRoot;
            size[pRoot] += size[qRoot];
        }
        count--;
    }
   
    /**
     * Precondition: Edges should be heapified.
     * Counts the number of spanning trees that can be created using a 
     *      connectivity check and path compression
     * @param cashe
     * @param f
     * @return 
     */
    public static int CountSpanningTrees(File []f){
        ArrayList<String> fNames = new ArrayList();
        for(int i = 0; i < f.length;i++){
            fNames.add(f[i].getName());//sets the names to a parallel array for easy name loop up
            System.out.println("Adding "+f[i].getName());
        }
        SpanningTree st = new SpanningTree(f.length);
        Edge temp;
        //while (pq.count > 0 && st.count > 1) {            
        //for each page
        for (int i = 0; i < f.length; i++) {
            Page p = App.peristance.Persist.LoadFile(f[i]);
            //for each link in that page
            for(int j = 0; j < p.links.size();j++){
                temp = p.links.get(j);
                //System.out.println("Link: "+temp.src+","+temp.dest);
                int pIndex = fNames.indexOf(temp.src+".ser");
                int qIndex = fNames.indexOf(temp.dest+".ser");//retuns a -1;
                if(pIndex < 0 || qIndex < 0)
                    ;//System.out.println("Destination not yet downloaded");
                else{//both sites are downloaded, and there exists a link between theme 
                    System.out.println("FOUND pIndex of "+pIndex+" and qIndex "+qIndex+"");
                    if(!st.Connected(pIndex,qIndex)){
                        System.out.println("Merging pIndex of "+pIndex+" and qIndex "+qIndex);//not finding them corrctly, bad parallel array?;
                        st.Merge(pIndex,qIndex);
                    }
                }
            } 
        }
        return st.count;
    } 
}
