/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;

/**
 *
 * @author RAndom MC
 */
public class ShapeSplitter extends JSONwriter{
    private ArrayList<int[][]> points;
    private ArrayList<ShapeFile> files;
    private ArrayList<int[][]> removeThese = new <int[][]>ArrayList();
    private int imageWidth;
    private int imageHeight;
    private final String projectName; 
    private final String filePath; 
    
    public ShapeSplitter(int width,int height, ArrayList files,String projectName,String filePath){
        this.imageWidth = width;
        this.imageHeight = height;
        this.files = files;
        this.projectName = projectName;
        this.filePath = filePath;
    }
    
    public void splitToSmaller(){
        for(ShapeFile sf:this.files){
            this.points = sf.getSlicePoints();
            for(int i = 0;i<points.size();i++){
                int[][] point1;
                int[][] point2;
                int[][] point3;
                int removeIndex;
                if(i < points.size()-2){
                    point1 = points.get(i);
                    point2 = points.get(i+1);
                    point3 = points.get(i+2);
                    removeIndex = i+1;
                } else if(i < points.size()-1){
                    point1 = points.get(i);
                    point2 = points.get(i+1);
                    point3 = points.get(0);
                    removeIndex = i+1;
                } else {
                    point1 = points.get(i);
                    point2 = points.get(0);
                    point3 = points.get(1);
                    removeIndex = 0;
                }
                
                if(point1[0][0] > imageWidth/2){
                    if(point2[0][0] > point1[0][0]+5 && point3[0][0] < point2[0][0]-5){
                        int[] newShape = {point1[0][0],point1[0][1] , point2[0][0],point2[0][1] , point3[0][0],point3[0][1]};
                        sf.setNewSmallerShape(newShape);
                        removeThese.add(points.get(removeIndex));
                        //i+=1;//add one as it also gets +1 after this cycle
                    } 
                } else if(point1[0][1] > imageHeight/2){
                    if(point2[0][1] > point1[0][1]+5 && point3[0][1] < point2[0][1]-5){
                        int[] newShape = {point1[0][0],point1[0][1] , point2[0][0],point2[0][1] , point3[0][0],point3[0][1]};
                        sf.setNewSmallerShape(newShape);
                        removeThese.add(points.get(removeIndex));

                        //i+=1;
                    }
                } else if(point1[0][0] < imageWidth/2){
                    if(point2[0][0] < point1[0][0]-5 && point3[0][0] > point2[0][0]+5){
                        int[] newShape = {point1[0][0],point1[0][1] , point2[0][0],point2[0][1] , point3[0][0],point3[0][1]};
                        sf.setNewSmallerShape(newShape);
                        removeThese.add(points.get(removeIndex));
                        //i+=1;//add one as it also gets +1 after this cycle
                    } 
                } else if(point1[0][1] < imageHeight/2){
                     if(point2[0][1] < point1[0][1]-5 && point3[0][1] > point2[0][1]+5){
                        int[] newShape = {point1[0][0],point1[0][1] , point2[0][0],point2[0][1] , point3[0][0],point3[0][1]};
                        sf.setNewSmallerShape(newShape);
                        removeThese.add(points.get(removeIndex));

                        //i+=1;
                    }
                }
                else {
                }
            }
            for (int[][] block : removeThese) {
                points.remove(block);
            }
            removeThese.clear();
            sf.slicePoints(points);
        }
        super.write(files, projectName, filePath);
    }
    public void jumpToWrite(){
        super.write(files, projectName, filePath);
    }
    
}

