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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.net.URL;
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
    private MouseListener pointListener;
    private MainComponents mainComponents;
    private GUI gui;//so that we can escape child class and call repaint on whole window
    private int status; //1 = draw tool, 2 = delete tool
    URL imageURL = getClass().getResource("images/DottSmall.png");
    ImageIcon icon = new ImageIcon(imageURL);

    public DrawArea(BufferedImage img, MainComponents mainComponents,GUI gui) {
        this.img = img;
        this.mainComponents = mainComponents;
        this.gui = gui;
        this.status = 1;
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
    }//reference, shortcut to a points arrayList in currently active shapeFile
    public void setStatus(int status){
        if(status > 0 && status < 3){
            this.status = status;
        }
    }
    
    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;        
        // remove all graphics
        this.removeAll();
        g.drawImage(img, 10, 10, this);
        pointListener = new MouseAdapter(){
        @Override
            public void mouseClicked(MouseEvent e) {
                if(status == 2) {
                    mainComponents.removePoint(e.getSource()); 
                    gui.repaint();
                }
            }
        };
        for(int i = 0;i<points.size();i++){
            if(i != 0) {
                //inner line color and thickness     
                g2d.setColor(Color.black);
                g2d.setStroke(new BasicStroke(3f));
                // get start and end points and draw line between them
                g2d.drawLine(points.get(i-1)[0][0], points.get(i-1)[0][1], points.get(i)[0][0], points.get(i)[0][1]);
                // draw second line
                g2d.setColor(Color.blue);
                g2d.setStroke(new BasicStroke(1f));
                // get start and end points and draw line between them
                g2d.drawLine(points.get(i-1)[0][0], points.get(i-1)[0][1], points.get(i)[0][0], points.get(i)[0][1]);
                // new JLabel
                JLabel point = new JLabel();
                //set blue square as icon
                point.setIcon(this.icon);
                point.setName(""+i);
                point.addMouseListener(pointListener);
                // set JLabel on x and y coordinates
                point.setBounds(points.get(i)[0][0] -5, points.get(i)[0][1] -5, 15, 15);
                this.add(point);
            } else {
                // this needs to be done in order to  get the first square to appear 
                JLabel point = new JLabel();
                point.setIcon(this.icon);
                point.setName(""+i);
                point.addMouseListener(pointListener);
                point.setBounds(points.get(i)[0][0] -5, points.get(i)[0][1] -5, 15, 15);
                this.add(point);
            }
            if(i == points.size()-1){
               // draw the red line
                g2d.setColor(Color.red);
                float[] dashingPattern1 = {4f, 2f};
                g2d.setStroke(new BasicStroke(2f, BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER, 1.0f, dashingPattern1, 2.0f));
                g2d.drawLine(points.get(i)[0][0], points.get(i)[0][1], points.get(0)[0][0], points.get(0)[0][1]); 
            }
        }
        
    }
}
