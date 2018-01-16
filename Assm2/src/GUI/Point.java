/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

/**
 * Structure to hold a cardinal x and y
 * @author chrisrk192
 */
public class Point {
    public String key;
    public double x;//temp
    public double y;//precip

    public Point(double nX, double nY) {
        x = nX;
        y = nY;
    }
    public Point() {
    }
    public Point(double nX, double nY, String s) {
        x = nX;
        y = nY;
        key = s;
    }
    
    /**
     * Calculates the 2 dimensianal distance between the two points 
     * @param s from
     * @param t to
     * @return the distance between
     */
    public static double EuclideanDist(Point s, Point t){
        double deltaX,deltaY;
        if(s == null){
            System.err.println("S Null Distance");
            return -1;
        }
        if(t == null){
            System.err.println("T Null Distance");
            return -1;
        }
        double val;
        deltaX = t.x - s.x;
        deltaY = t.y - s.y;
        //System.out.println("Delta x = " + deltaX+ " Delta y = "+deltaY);
        val = Math.pow(deltaY, 2)+Math.pow(deltaX,2);
        val = Math.sqrt(val);
        //System.out.println(" distance = "+val);
        return val;
        
    } 
}
