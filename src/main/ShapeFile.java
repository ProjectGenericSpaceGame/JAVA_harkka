/*
 * Licenced under LGPL
 * Licence terms can be read here
 * https://www.gnu.org/copyleft/lesser.html
 */
package main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Virtuasity Inc.
 */
public class ShapeFile {
    private BufferedImage img; //where to find picture used as drawing template
    private ArrayList<int[][]> points; //Array holding user defined points
    private boolean slice; //Should intelligent shapetracker be used for splicing the body to smaller parts
    private String shapeName; //Actual name of this body, this will be written to JSON and will work as a reference name when using JSON file
    private ArrayList<Graphics> graphics;
    
    //private ShapeFile self = this; //this is used if function scope prevents using "this" keyword
    
    public ShapeFile(BufferedImage imgSrc,int fileAmount) {
        this.img = null; //New file is created by uploading an image, therefore there will always be this value when creating new file
        this.shapeName = "file"+fileAmount; //this is also required field and when creating file, a default name is used. Default name is generated based on how many files exists in project
        
        this.slice = true; //default value is true
        this.points = new ArrayList<int[][]>();//obviously empty when creating file
        this.graphics = new ArrayList<Graphics>();
    }

    public BufferedImage getImgSrc() {
        return img;
    }
    
    //getters and setters
    public void setImgSrc(BufferedImage img) {
        this.img = img;
    }

    public ArrayList getPoints() {
        return points;
    }

    public void setPoints(int[][] points) { //int points is two-dimensional array holding x & y coordinates of clicked points. For example int points =[23][15]
        this.points.add(points);
    }

    public boolean isSlice() {
        return slice;
    }

    public void setSlice(boolean slice) {
        this.slice = slice;
    }

    public String getShapeName() {
        return shapeName;
    }

    public void setShapeName(String shapeName) {
        this.shapeName = shapeName;
    }
    public void addGraphics(Graphics grap){
        this.graphics.add(grap);
    }
    //removing points is done so that function iterates through array of points and check if clicked point is within coordinates of any point. If it is, this point is removed
    public int removePoint(int mouseX, int mouseY){
       int iter = 0;
       int coordXrangeMin;
       int coordXrangeMax;
       int coordYrangeMin;
       int coordYrangeMax;
       while(iter < points.size()){
           int pointArray[][] = points.get(iter);
           coordXrangeMin = pointArray[0][0]-5;
           coordXrangeMax = pointArray[0][0]+5;
           coordYrangeMin = pointArray[0][1]-5;
           coordYrangeMax = pointArray[0][1]+5;
           if(coordXrangeMin < mouseX && mouseX < coordXrangeMax){
               if(coordYrangeMin < mouseY && mouseY < coordYrangeMax){
                   points.remove(iter);
                   return iter;
                }
           }
       }
       return -1;
    }
    
    
}
