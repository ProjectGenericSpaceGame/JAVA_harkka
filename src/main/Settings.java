/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Ultoros
 */
public class Settings {
    private String defaultFileName = null; 
    private String defaultPath = null;
    private ArrayList<String> s;
    private boolean isSettings = false;
    private FileReader fr;      
    private BufferedReader br;
    private PrintWriter write = null;
    
    public Settings(){
        try {
            // yritetään luokea asetuksia
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
            System.out.println("jotain tapahtui "+e);
            // asetuksia ei ole tai niitä ei voi lukea.
            this.isSettings = false;
        }   
    }
    
    public boolean saveSettings(){
        if(this.defaultFileName != null || this.defaultPath != null){
            try{
                write = new PrintWriter("settings.txt");
                write.println(this.defaultPath);
                write.println(this.defaultFileName);
                write.close();
                System.out.println("onnistui");
                return true;
            }catch(Exception e){
                // Something went wrong
                return false;
            }
        } else {
            return false;
        }
    }
    
    public String getDefaultPath(){
        if(this.isSettings != true){
            return "Not set";
        }else{
            return this.defaultPath;
        }
    }
    
    public String getDefaulthFileName(){
         if(this.isSettings != true){
            return "";
        }else{
            return this.defaultFileName;
        }
    }
    
    public void setDefaultPath(String path){
        this.isSettings = true;
        this.defaultPath = path;
    }
    
    public void setDefaultFileName(String filename){
        this.isSettings = true;
        this.defaultFileName = filename;
    }
}
