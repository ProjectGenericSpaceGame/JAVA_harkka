/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author RAndom MC
 */
public class DrawArea extends JPanel{
    private BufferedImage img;
    private ArrayList<int[][]> points;
    private MainComponents mainComponents;

    public DrawArea(BufferedImage img, MainComponents mainComponents) {
        this.img = img;
        this.mainComponents = mainComponents;
        
        this.points = new ArrayList<int[][]>();//obviously empty when creating file //[[],[]]
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public void setPoints(ArrayList<int[][]> points) {
        this.points = points;
    }
    
    
    @Override
    public void paintComponent(Graphics g){
        g.drawImage(img, 10, 10, this);
        for(int i = 0;i<points.size();i++){//vanhan mallinen luuppi koska tässä tutkitaan iteraaattorin arvoa
            if(i != 0) {
                g.drawLine(points.get(i-1)[0][0], points.get(i-1)[0][1], points.get(i)[0][0], points.get(i)[0][1]);
            }
        }
        
    }
}
