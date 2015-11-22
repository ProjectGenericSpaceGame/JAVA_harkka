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
import javax.accessibility.Accessible;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Ultoros
 */
public class FileSelector extends GUI implements Accessible {
        private File file;
        private BufferedImage image = null;
        private int returned;
        private String path;

    // konstruktori joka sisltää tiedostojen selailun
    public FileSelector(int usage){
        if(usage == 1){ //jos halutaan ladata kuva
            // lisätään suodatin kuville
            FileFilter filter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()); //bmp, jpg, jpeg, wbmp, pngm, gif
            // luodaan uusi JFileChooser
            JFileChooser chooser = new JFileChooser();
            // muutetaan chooserin oletuskokoa
            chooser.setPreferredSize(new Dimension(600,600));
            // poistetaan suodatin FileChooserista
            chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
             // lisätään suodatin FileChooseriin
            chooser.addChoosableFileFilter(filter);
            returned = chooser.showOpenDialog(this); 
            // mikäli käyttäjä valitsi jonkun tiedoston
            if (returned == JFileChooser.APPROVE_OPTION) {     
                try{
                    this.file = chooser.getSelectedFile();
                    this.image = ImageIO.read(file);
                }catch(IOException e){
                    System.out.println("Error while reading the file "+e); 
                }
            } else if (returned == JFileChooser.CANCEL_OPTION){
                System.out.println("User didnt select a file");
            }
        } else if(usage == 2){//jos halutaan tallennussijainti
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);
            
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
        // haluttuun kansioon saadaan tallennettua tiedosto lisäämällä kenoviiva
        return this.path+"/";
    }
} // class

