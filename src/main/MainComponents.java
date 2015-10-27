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
    
}
