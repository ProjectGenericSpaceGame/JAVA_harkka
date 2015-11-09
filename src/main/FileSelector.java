/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

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

    // konstruktori joka sisltää tiedostojen selailun
    public FileSelector(){
        // lisätään suodatin kuville
        FileFilter filter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()); //bmp, jpg, jpeg, wbmp, pngm, gif
        JFileChooser chooser = new JFileChooser();
        // poistetaan suodatin FileChooserista
        chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
         // lisätään suodatin FileChooseriin
        chooser.addChoosableFileFilter(filter);
        returned = chooser.showOpenDialog(this);    
        if (returned == JFileChooser.APPROVE_OPTION) {     
            try{
                this.file = chooser.getSelectedFile();
                this.image = ImageIO.read(file);
            }catch(IOException e){
                System.out.println("Error while reading the file"); 
            }
        } else if (returned == JFileChooser.CANCEL_OPTION){
            System.out.println("User didnt select a file");
        }
    }
    // Palauttaa  kuvan.
    public BufferedImage getImage(){
        return this.image;
        }
} // class

