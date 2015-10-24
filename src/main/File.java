/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javax.accessibility.Accessible;
import javax.swing.JFileChooser;


/**
 *
 * @author Ultoros
 */
public class File extends GUI implements Accessible {
    private int returnvalue;
    
    public File(){
   JFileChooser chooser = new JFileChooser();
   returnvalue = chooser.showOpenDialog(this);
    if (returnvalue == JFileChooser.APPROVE_OPTION){
       System.out.println("You chose to open this file: " +
            chooser.getSelectedFile().getName());
        }
    }
    
   
}
