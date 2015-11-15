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
public class Project {
    private String project;
    private String JSONname;
    private ArrayList<ShapeFile> files;

    public Project(String project) {
        this.project = project;
        this.JSONname = "default.json";
        this.files = new ArrayList<ShapeFile>();
    }
    public String getName(){
        return this.project;
    }
    public int getAmount(){
        return this.files.size();
    }
    public ShapeFile getFile(int index){
        return files.get(index);
    }
    public ArrayList getAllFiles(){
        return files;
    }
    public int getFileAmount(){
        return files.size();
    }
    public void addFile(ShapeFile f){
        this.files.add(f);
    }
    
    
}
