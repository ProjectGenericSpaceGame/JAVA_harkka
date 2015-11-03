/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author RAndom MC
 */
public class MainComponents {
    private ArrayList<Project> projects;
    private int activeFile;
    private int activeProject;
    
    public MainComponents(){
        this.projects = new ArrayList<Project>();
        this.activeFile = -1;
        this.activeProject = -1;
    }
    
    public void newProject(Project pro){
        this.projects.add(pro);
    }
    public int getProjectAmount(){
        return this.projects.size();
    }
    public int getActiveFile() {
        return activeFile;
    }

    public void setActiveFile(int activeFile) {
        this.activeFile = activeFile;
    }
    public void newFile(ShapeFile file){
        this.projects.get(this.getActiveProject()).addFile(file);
    }
    public int getActiveProject() {
        return activeProject;
    }

    public void setActiveProject(int activeProject) {
        this.activeProject = activeProject;
    }
    public void changeFile(int selectedIndex,Graphics g){//tässä piirretään uusi kuva valitun tiedoston mukaan ja vaihdetaan tarvittavat tiedostot
        ArrayList points = projects.get(activeProject).getFile(selectedIndex).getPoints();
        for(int i = 0;i<points.size();i++){
            //drawing goes here
        }
    }
    public void changeProject(int selectedIndex,Graphics g){
        //*
        //vaihdetaan files listan sisältö
        //Vaihdetaan activeProject muuttuja
        //piirretään files listan eka tiedosto
        //*
    }
    
     public void setCoord(int x, int y, Graphics g){
        int y1;
        int x1;
        int x2 = x;
        int y2 = y;
        ArrayList<int[][]> pointsArray = projects.get(activeProject).getFile(activeFile).getPoints();
        if(!pointsArray.isEmpty()){
            int[][] coords = pointsArray.get(pointsArray.size()-1);
            x1 = coords[0][0];
            y1 = coords[0][1];
            g.drawLine(x1, y1, x2, y2);
            int[][] newPoints = new int[1][2];
            newPoints[0][0] = x2;
            newPoints[0][1] = y2;
            projects.get(activeProject).getFile(activeFile).setPoints(newPoints);
        } else {
            int[][] newPoints = new int[1][2];
            newPoints[0][0] = x2;
            newPoints[0][1] = y2;
            projects.get(activeProject).getFile(activeFile).setPoints(newPoints);
        } 
        System.out.println("x "+x2+" y "+y2);
    }
    
   /* public void checkPoints(){
        ArrayList pointten =  this.projects.get(activeProject).getFile(activeFile).getPoints();
        if (pointten.isEmpty()){
            this.projects.get(activeProject).getFile(activeFile).setPoints([][]);
        }
        
    }
    */
    
    
    
}
