/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;
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
    ImageIcon icon = new ImageIcon("DottSmall.png");

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
        Graphics2D g2d = (Graphics2D) g;        
        // poistetaan kaikki
        this.removeAll();
        g.drawImage(img, 10, 10, this);
        for(int i = 0;i<points.size();i++){//vanhan mallinen luuppi koska tässä tutkitaan iteraaattorin arvoa
            if(i != 0) {
                //asetetaan sisemmälle viivalle väri ja paksuus     
                g2d.setColor(Color.black);
                g2d.setStroke(new BasicStroke(3f));
                // hankitaan pistetaulukosta aloitus ja lopetuspisteet ja piirretään viiva niiden välille
                g2d.drawLine(points.get(i-1)[0][0], points.get(i-1)[0][1], points.get(i)[0][0], points.get(i)[0][1]);
                // piirretään toinen viiva
                g2d.setColor(Color.blue);
                g2d.setStroke(new BasicStroke(1f));
                // hankitaan pistetaulukosta aloitus ja lopetuspisteet ja piirretään viiva niiden välille
                g2d.drawLine(points.get(i-1)[0][0], points.get(i-1)[0][1], points.get(i)[0][0], points.get(i)[0][1]);
                // Tehdään uusi label
                //ImageIcon icon = new ImageIcon(getClass().getResource("DottSmall.jpg"));
                JLabel point = new JLabel();
               // icon.paintIcon(this, g, 10, 200);
                point.setIcon(this.icon);
                // asetaan label pisteiden x ja y koordinaatteihin
                point.setBounds(points.get(i)[0][0] -5, points.get(i)[0][1] -5, 15, 15);
                this.add(point);
            } else {
               // ImageIcon icon = new ImageIcon("DottSmall.png");
                JLabel point = new JLabel();
               // icon.paintIcon(this, g, 10, 200);
                point.setIcon(this.icon);
                // lisätään ensimmäinen label 
                point.setBounds(points.get(i)[0][0] -5, points.get(i)[0][1] -5, 15, 15);
                this.add(point);
            } 
        }
        
    }
}
