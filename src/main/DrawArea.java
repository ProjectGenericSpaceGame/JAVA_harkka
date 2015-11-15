/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JLabel;
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
    public int[] getImgData(){
        int data[] = {0,0};
        data[0]  = img.getWidth();
        data[1] = img.getHeight();
        return data;
    }
    public void setPoints(ArrayList<int[][]> points) {
        this.points = points;
    }
    
    @Override
    public void paintComponent(Graphics g){
        // poistetaan kaikki
        this.removeAll();
        g.drawImage(img, 10, 10, this);
        for(int i = 0;i<points.size();i++){//vanhan mallinen luuppi koska tässä tutkitaan iteraaattorin arvoa
            if(i != 0) {
                // hankitaan pistetaulukosta aloitus ja lopetuspisteet ja piirretään viiva niiden välille
                g.drawLine(points.get(i-1)[0][0], points.get(i-1)[0][1], points.get(i)[0][0], points.get(i)[0][1]);
                // Tehdään uusi label
                JLabel point = new JLabel("asd");
                // asetaan label pisteiden x ja y koordinaatteihin
                point.setBounds(points.get(i)[0][0], points.get(i)[0][1], 100, 20);
                this.add(point);
            } else {
                JLabel point = new JLabel("asd");
                // lisätään ensimmäinen label 
                point.setBounds(points.get(i)[0][0], points.get(i)[0][1], 100, 20);
                this.add(point);
            } 
        }
        
    }
}
