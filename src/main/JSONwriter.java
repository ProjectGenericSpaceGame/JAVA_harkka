/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author RAndom MC
 */
public class JSONwriter {
    private PrintWriter write = null;
    private boolean reverse = false;
    private final String preShape1 = "\"density\": 2, \"friction\": 0, \"bounce\": 0,";
    private final String preShape2 = "\"filter\": { \"categoryBits\": 1, \"maskBits\": 65535 },";
    
    public boolean write(ArrayList<ShapeFile> shapes,String fileName, String filePaths){
        try {
            write = new PrintWriter(filePaths+""+fileName+".json");
        }catch(IOException e) {
            System.out.println("disks mistakes where made(can't create new file)");
            return false;
        }
        write.println("{");
        int i = 0;
        for (ShapeFile shape : shapes) {
            write.println("\"" + shape.getShapeName() + "\"" + ": [");
            if(shape.getSmallerShapes().size() > 0){
                for(int k = 0;k<shape.getSmallerShapes().size();k++){
                    write.println("     {");
                    write.println("         "+preShape1);
                    write.println("         "+preShape2);
                    write.print("");
                    write.print("         \"shape\":[");
                    int[] ar = (int[])shape.getSmallerShapes().get(k);
                    //here we use algebra to calculate which direction points are going, in order to work in Phaser convex vertices must be given in clockwise winding.
                    int signedArea = 0;    
                    for(int t = 0;t<ar.length;t++){
                        int x1 = ar[i];
                        int y1 = ar[i+1];
                        int x2;
                        int y2;
                        if(t >= ar.length-3)//is last
                        {
                            x2 = ar[0];
                            y2 = ar[1];
                        }
                        else 
                        {
                            x2 = ar[t+2];
                            y2 = ar[t+3];
                        }
                        signedArea += (x1*y2 - x2*y1);
                        t++;//because next value is y of current we rise t by 2 to go to next x
                    }
                    if(signedArea > 0){//this means that points are in wrong order and now we flip the order
                        for(int l = 0; l < ar.length / 2; l++)
                        {
                            int temp = ar[l];
                            ar[l] = ar[ar.length - l - 1];
                            ar[ar.length - l - 1] = temp;
                        }
                        int temp = ar[0];
                        ar[0] = ar[ar.length - 1];
                        ar[ar.length - 1] = temp;
                    }
                    //and for writing
                    for(int t = 0;t<ar.length;t++){
                        if(t != ar.length-1){
                            write.print(ar[t]+",");
                        } else {
                            write.print(ar[t]+"");
                        }
                    }
                    write.print("]");
                    write.println("     },");
                        
                }
            } 
                write.println("     {");
                write.println("         "+preShape1);
                write.println("         "+preShape2);
                write.print("         \"shape\":[");
                ArrayList <int[][]>points = shape.getSlicePoints();
                //here we use algebra to calculate which direction points are going, in order to work in Phaser convex vertices must be given in clockwise winding.
                    int signedArea = 0;    
                    for(int t = 0;t<points.size();t++){
                        int x1 = points.get(i)[0][0];
                        int y1 = points.get(i)[0][1];
                        int x2;
                        int y2;
                        if(t == points.size()-1)//is last
                        {
                            x2 = points.get(0)[0][0];
                            y2 = points.get(0)[0][1];
                        }
                        else 
                        {
                            x2 = points.get(t+1)[0][0];
                            y2 = points.get(t+1)[0][1];
                        }
                        signedArea += (x1*y2 - x2*y1);
                        t++;//because next vaule is y of current we rise t by 2 to go to next x
                    }
                    if(signedArea > 0){//this means that points are in wrong order and now we flip the order
                       Collections.reverse(points);
                        //this.reverse = true;
                       /*int [][]temp = points.get(0);
                       points.set(0, points.get(points.size()-1));
                       points.set(points.size()-1, temp);*/
                    }
                for(int l = 0;l<points.size();l++){
                        if(l != points.size()-1){
                            write.print(points.get(l)[0][0]+",");
                            write.print(points.get(l)[0][1]+",");
                        } else {
                            write.print(points.get(l)[0][0]+",");
                            write.print(points.get(l)[0][1]+"");
                        }
                        /*if(this.reverse){
                            if(l == 0){
                                l = points.size()-1;
                            } else {
                                l--;
                            }
                        } else {
                            l++;
                        }*/
                }
                write.print("]");
                write.println("     }");
            
            if(i != shapes.size()-1){
                write.println("],");
            } else {
                write.println("]");
            }
            i++;
        }
        
        write.println("}");
        write.close();
        return true;
    }
}
