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
    JFileChooser chooser = new JFileChooser();
    returned = chooser.showOpenDialog(this);    
    if (returned == JFileChooser.APPROVE_OPTION) {     
        try{
            this.file = chooser.getSelectedFile();
            this.image = ImageIO.read(file);
        }catch(IOException e){
            // tässä ei  tapahdu mitään  
        }
    }
}
 // Palauttaa  kuvan.
public BufferedImage getImage(){
        return this.image;
    }
} // class

