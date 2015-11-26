/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 *
 * @author Ultoros
 */
public class Settings {
    private String defaultFileName; 
    private String defaultPath;
    private ArrayList<String> settings;
    private boolean isSettings = false;
    private FileReader fr;      
    private BufferedReader br;  
    
    public Settings(){
        try {
            fr = new FileReader("settings.txt");  
            br = new BufferedReader(fr);  
            String r;
            int i = 0;
            while ((r = br.readLine()) != null) {
                settings.add(i, r);
                i++;
            }
            this.defaultPath = settings.get(0);
            this.defaultFileName = settings.get(1);
            this.isSettings = true;
            br.close();
        }catch(Exception e){
            // asetuksia ei ole tai niitä ei voi lukea.
            this.isSettings = false;
        }   
    }
    
    public String getDefaultPath(){
        if(isSettings != true){
            return "noSettings";
        }else{
            return this.defaultPath;
        }
    }
    
    public String getDefaulthFileName(){
         if(isSettings != true){
            return "noSettings";
        }else{
            return this.defaultFileName;
        }
    }
    
    public void setDefaultPath(String path){
        this.defaultPath = path;
    }
    
    public void setDefaultFileName(String filename){
        this.defaultFileName = filename;
    }
}
