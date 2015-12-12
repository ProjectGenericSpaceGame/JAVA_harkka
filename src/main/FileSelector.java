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
        private String zipPath;

    // constructor that includes file browsing
    public FileSelector(int usage){
         // Create new fileChooser
        JFileChooser chooser = new JFileChooser();
        
         // change default size
        chooser.setPreferredSize(new Dimension(600,600));
        // remove default filter
        chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
        // when importing images
        if(usage == 1){
            chooser.setDialogTitle("Choose a image file");
            // add image filter
            FileFilter filter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()); //bmp, jpg, jpeg, wbmp, pngm, gif
             // add filter to  FileChooser
            chooser.addChoosableFileFilter(filter);
            returned = chooser.showOpenDialog(this); 
            // if user selected something
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
            chooser.setDialogTitle("Choose a place to save");
            chooser.setApproveButtonText("Save");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
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
            chooser.setDialogTitle("Choose a project file");
            chooser.addChoosableFileFilter(new FileNameExtensionFilter("Project", "ZIP"));
            chooser.setApproveButtonText("Select");
            chooser.setAcceptAllFileFilterUsed(false);
            returned = chooser.showOpenDialog(this);    
            if (returned == JFileChooser.APPROVE_OPTION) {     
                try{
                    this.path = chooser.getSelectedFile().getParentFile().getAbsolutePath();
                    this.zipPath = chooser.getSelectedFile().getPath();
                }catch(Exception e){
                    System.out.println("Error while reading the path"); 
                }
            } else if (returned == JFileChooser.CANCEL_OPTION){
                System.out.println("User didnt select a path");
            }
        }
    }
    // return image
    public BufferedImage getImage(){
        return this.image;
    }
    // returns abslute path
    public String getPath(){
       if(path == null){
           return "none";
       }    
       else {
           return this.path+"/";
       }   
    }
    
    public String getZipPath(){
        return this.zipPath;
    }
    
} // class
