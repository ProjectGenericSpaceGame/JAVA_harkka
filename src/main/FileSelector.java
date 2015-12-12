/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Ultoros
 */
public class FileSelector extends GUI {
        private File file;
        private BufferedImage image = null;
        private int returned;
        private String path;

    // konstruktori joka sisltää tiedostojen selailun
    public FileSelector(int usage){
         // luodaan uusi JFileChooser
        JFileChooser chooser = new JFileChooser();
         // muutetaan chooserin oletuskokoa
        chooser.setPreferredSize(new Dimension(600,600));
        // poistetaan suodatin FileChooserista
        chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
        //jos halutaan ladata kuva
        if(usage == 1){ 
            // lisätään suodatin kuville
            FileFilter filter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()); //bmp, jpg, jpeg, wbmp, pngm, gif
             // lisätään suodatin FileChooseriin
            chooser.addChoosableFileFilter(filter);
            returned = chooser.showOpenDialog(this); 
            // mikäli käyttäjä valitsi jonkun tiedoston
            if (returned == JFileChooser.APPROVE_OPTION) {     
                try{
                    this.file = chooser.getSelectedFile();
                    this.path = chooser.getSelectedFile().getAbsolutePath();
                    System.out.println(this.path);
                    this.image = ImageIO.read(file);
                }catch(IOException e){
                    System.out.println("Error while reading the file "+e); 
                }
            } else if (returned == JFileChooser.CANCEL_OPTION){
                System.out.println("User didnt select a file");
            }
    
        // if choosing a folder to save the file to     
        } else if(usage == 2){
            chooser.setApproveButtonText("Save");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            //chooser.setAcceptAllFileFilterUsed(false);
            returned = chooser.showOpenDialog(this);    
            if (returned == JFileChooser.APPROVE_OPTION) {     
                try{
                    this.path = chooser.getSelectedFile().getAbsolutePath();
                    System.out.println("Valitsemasi polku on "+this.path);
                }catch(Exception e){
                    System.out.println("Error while reading the path"); 
                }
            } else if (returned == JFileChooser.CANCEL_OPTION){
                System.out.println("User didnt select a path");
            }
        // if choosing a project file    
        } else if(usage == 3){
            //FileFilter filter//chooser.addChoosableFileFilter(new FileNameExtensionFilter("Project", "DAT".);
           // chooser.addChoosableFileFilter(new FileNameExtensionFilter("Project files", "dat"));
            chooser.setAcceptAllFileFilterUsed(true);
            returned = chooser.showOpenDialog(this);    
            if (returned == JFileChooser.APPROVE_OPTION) {     
                try{
                    this.path = chooser.getSelectedFile().getAbsolutePath();
                }catch(Exception e){
                    System.out.println("Error while reading the path"); 
                }
            } else if (returned == JFileChooser.CANCEL_OPTION){
                System.out.println("User didnt select a path");
            }
        }
    }
    // Palauttaa  kuvan
    public BufferedImage getImage(){
        return this.image;
    }
    // Palauttaa absoluuttisen polun tiedostoon
    public String getPath(){
       if(path == null){
           return "none";
       }    // haluttuun kansioon saadaan tallennettua tiedosto lisäämällä kenoviiva
       else {
           return this.path+"/";
       }   
    }
} // class
