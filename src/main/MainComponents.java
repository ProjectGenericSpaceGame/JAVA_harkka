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
    
    public MainComponents(){
        this.projects = new ArrayList<Project>();
    }
    
    public void newProject(Project pro){
        this.projects.add(pro);
    }
    
}
