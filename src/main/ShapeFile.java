/*
 * Licenced under LGPL
 * Licence terms can be read here
 * https://www.gnu.org/copyleft/lesser.html
 */
package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Virtuasity Inc.
 */
public class ShapeFile implements Serializable{
    private BufferedImage img;//where to find picture used as drawing template
    private String imgPath;
    private ArrayList<int[][]> points; //Array holding user defined points
    private ArrayList<int[][]> pointsToSliceFrom;
    private ArrayList<int[]> smallerShapes;
    private boolean slice; //Should intelligent shapetracker be used for splicing the body to smaller parts
    private String shapeName; //Actual name of this body, this will be written to JSON and will work as a reference name when using JSON file
    private ArrayList<Graphics> graphics;
    
    //private ShapeFile self = this; //this is used if function scope prevents using "this" keyword
    
    public ShapeFile(BufferedImage imgSrc,int fileAmount,String defaultName) {
        if(imgSrc != null){
        this.img = imgSrc;
        }else{
        this.img = null;
        }//New file is created by uploading an image, therefore there will always be this value when creating new file
        this.shapeName = ""+defaultName+fileAmount; //this is also required field and when creating file, a default name is used. Default name is generated based on how many files exists in project
        
        this.slice = true; //default value is true
        this.points = new ArrayList<int[][]>();//obviously empty when creating file //[[],[]]
        //this.pointsToSliceFrom = new ArrayList<int[][]>();//obviously empty when creating file //[[],[]]
        this.graphics = new ArrayList<Graphics>();
        this.smallerShapes = new ArrayList<int[]>();
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
    public ArrayList getSlicePoints() {
        if(pointsToSliceFrom == null){
        pointsToSliceFrom = new <int[][]>ArrayList(points);
        }
        return pointsToSliceFrom;
    }
    public void slicePoints(ArrayList newPoints){
        this.pointsToSliceFrom = newPoints;
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
    public void setNewSmallerShape(int[] newShape){
        this.smallerShapes.add(newShape);
    }
    public ArrayList getSmallerShapes(){
        return this.smallerShapes;
    }
    
    public String getImgPath(){
        return this.imgPath;
    }
    
    public void setImgPath(String path){
        this.imgPath = path;
    }
    
    
}
