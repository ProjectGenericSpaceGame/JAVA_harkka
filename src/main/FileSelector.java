/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.BorderLayout;
import java.io.File;
import javax.accessibility.Accessible;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;


/**
 *
 * @author Ultoros
 */
public class FileSelector extends GUI implements Accessible {
    private int returnvalue;
    private String filePath;
    
    public FileSelector(){
   JFileChooser chooser = new JFileChooser();
   returnvalue = chooser.showOpenDialog(this);
    if (returnvalue == JFileChooser.APPROVE_OPTION){
       this.filePath = chooser.getSelectedFile().getPath();
       }
    }
    
   public String returnFileName(){
       return this.filePath;
   }
}
