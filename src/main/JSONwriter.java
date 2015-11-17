/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author RAndom MC
 */
public class JSONwriter {
    private PrintWriter write = null;
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
                for(int l = 0;l<points.size();l++){
                        if(l != points.size()-1){
                            write.print(points.get(l)[0][0]+",");
                            write.print(points.get(l)[0][1]+",");
                        } else {
                            write.print(points.get(l)[0][0]+",");
                            write.print(points.get(l)[0][1]+"");
                        }
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
