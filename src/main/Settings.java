/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 *
 * @author Ultoros
 */
public class Settings {
    private String defaultFileName = null; 
    private String defaultPath = null;
    private boolean isSettings = false;
    private FileReader fr;      
    private BufferedReader br;
    private PrintWriter write = null;
    
    public Settings(){
        try {
            // Trying to read settings
            this.fr = new FileReader("settings.txt"); 
            this.br = new BufferedReader(this.fr);  
            String r;
            int i = 0;
            while ((r = this.br.readLine()) != null) {
                if(i == 0){
                    this.defaultPath = r;
                    i++;
                } else {
                    this.defaultFileName = r;
                } 
            }
            this.isSettings = true;
            this.br.close();
        }catch(Exception e){
            System.out.println("Error Occured: "+e);
            // theres no settings or they cant be read
            this.isSettings = false;
        }   
    }
    // save made settings
    public boolean saveSettings(){
        if(this.defaultFileName != null || this.defaultPath != null){
            try{
                //write settings to a txt file
                write = new PrintWriter("settings.txt");
                write.println(this.defaultPath);
                write.println(this.defaultFileName);
                write.close();
                System.out.println("saved successfully");
                return true;
            }catch(Exception e){
                System.out.println("Erro occured: "+e);
                return false;
            }
        } else {
            return false;
        }
    }
    // returns the default save path
    public String getDefaultPath(){
        if(this.isSettings != true){
            return "Not set";
        }else{
            return this.defaultPath;
        }
    }
    // return the default filename
    public String getDefaulthFileName(){
         if(this.isSettings != true){
            return "shape";
        }else{
            return this.defaultFileName;
        }
    }
    // set default filepath
    public void setDefaultPath(String path){
        this.isSettings = true;
        this.defaultPath = path;
    }
    // set default filename
    public void setDefaultFileName(String filename){
        this.isSettings = true;
        this.defaultFileName = filename;
    }
}
