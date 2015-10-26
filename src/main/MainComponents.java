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

    public int getActiveProject() {
        return activeProject;
    }

    public void setActiveProject(int activeProject) {
        this.activeProject = activeProject;
    }
    
    
}
