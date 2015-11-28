/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Image;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author RAndom MC
 */
public class MainComponents {
    private ArrayList<Project> projects;
    private int activeFile;
    private int activeProject;
    private Image img;
    
    public MainComponents(){
        this.projects = new ArrayList<Project>();
        this.activeFile = -1;
        this.activeProject = -1;
    }
    
    public void newProject(Project pro){
        this.projects.add(pro);
    }
    public String newFile(DrawArea drawArea, DefaultListModel availableFilesModel,JList availableFiles){
        if(drawArea != null){
            FileSelector tiedosto = new FileSelector(1);
            if(tiedosto.getImage() != null){
                drawArea.setImg(tiedosto.getImage());
                ArrayList<int[][]> emptyAr = new ArrayList<int[][]>();
                drawArea.setPoints(emptyAr);
                Settings setting = new Settings();
                ShapeFile file = new ShapeFile(tiedosto.getImage(),this.getFilesAmount()+1,setting.getDefaulthFileName());
                setting = null;
                availableFilesModel.addElement(file.getShapeName());
                projects.get(activeProject).addFile(file);
                availableFiles.setSelectedIndex(this.getFilesAmount()-1);
                activeFile = this.getFilesAmount()-1;
                return "";
            }
            else {
                return "";
            }
        } else {
            return "";
         // ime paskaa
        }
    }
    
    public int getProjectAmount(){
        return this.projects.size();
    }
    public String getProjectName(){
        return projects.get(activeProject).getName();
    }
    public int getFilesAmount(){
        return this.projects.get(activeProject).getFileAmount();
    }
    public int getActiveFile() {
        return activeFile;
    }
    public ShapeFile getActualActiveFile() {//If for some reason, file is needed in GUI instead of here, this is handly shortcut
        if(activeFile != -1){
        return projects.get(activeProject).getFile(activeFile);
        }
        return null;
    }
    public void setActiveFile(int activeFile) {
        this.activeFile = activeFile;
    }
    public void renameFile(String name,DefaultListModel model){
        if(activeFile != -1){
        projects.get(activeProject).getFile(activeFile).setShapeName(name);
        model.set(activeFile, name);
        }
        
    }
    public int getActiveProject() {
        return activeProject;
    }
    public void renameProject(JDialog upload, JLabel textField, String newName, DefaultListModel availableProjectsModel){
        projects.get(activeProject).setName(newName);
        textField.setText("Please give a JSON reference name for this file");
        upload.setName("");
        availableProjectsModel.set(activeProject,newName);
    }
    public void setActiveProject(int activeProject) {
        this.activeProject = activeProject;
    }
    public void changeFile(int selectedIndex, DrawArea drawArea){//tässä kutsutaan drawArean funktioita joissa piirretään uusi kuva valitun tiedoston mukaan ja vaihdetaan tarvittavat tiedostot
        activeFile = selectedIndex;
        drawArea.setImg(projects.get(activeProject).getFile(selectedIndex).getImgSrc());
        drawArea.setPoints(projects.get(activeProject).getFile(selectedIndex).getPoints());
    }
    public void changeProject(int selectedIndex,DrawArea drawArea,DefaultListModel availableFilesModel){
        //*
        //vaihdetaan files listan sisältö
        //Vaihdetaan activeProject muuttuja
        //piirretään files listan eka tiedosto
        //*
        activeProject = selectedIndex;
        availableFilesModel.clear();
        if(projects.get(activeProject).getFileAmount() != 0){
        for(int i = 0;i < projects.get(activeProject).getFileAmount();i++){
            ShapeFile file = projects.get(activeProject).getFile(i);
            availableFilesModel.addElement(file.getShapeName());
        }
        this.changeFile(0, drawArea);
        } else {
            drawArea.setImg(null);
            ArrayList<int[][]> emptyAr = new ArrayList<int[][]>();
            drawArea.setPoints(emptyAr);
        }
    }
    
    public void setCoord(int x, int y, Graphics g,DrawArea drawArea){
        int y1;
        int x1;
        int x2 = x;
        int y2 = y;
       
        //ImageIcon icon = new ImageIcon(getClass().getResource("\\DottSmall.jpg"));
        //ImageIcon icon = new ImageIcon("DottSmall.png");
        JLabel point = new JLabel();
                //icon.paintIcon(this, g, 10, 200);
       // point.setIcon(icon);
        //point.setIcon(new ImageIcon("dotSmall.PNG"));
        
        point.setBounds(x2 -5, y2 -5, 15, 15);
        point.setName((this.getActualActiveFile().getPoints().size()-1)+"");
            //label.setBounds(x, y, width, height);
        System.out.println(point.getName());
        
        ArrayList<int[][]> pointsArray = projects.get(activeProject).getFile(activeFile).getPoints();
        if(!pointsArray.isEmpty()){
            int[][] coords = pointsArray.get(pointsArray.size()-1);
            x1 = coords[0][0];
            y1 = coords[0][1];
            g.drawLine(x1, y1, x2, y2);
            int[][] newPoints = new int[1][2];
            newPoints[0][0] = x2;
            newPoints[0][1] = y2;
            projects.get(activeProject).getFile(activeFile).setPoints(newPoints);
        } else {
            int[][] newPoints = new int[1][2];
            newPoints[0][0] = x2;
            newPoints[0][1] = y2;
            projects.get(activeProject).getFile(activeFile).setPoints(newPoints);
        }
        drawArea.setPoints(projects.get(activeProject).getFile(activeFile).getPoints());
        System.out.println("x "+x2+" y "+y2);
        //return point;
        drawArea.add(point);
    }
    public void removeFile(DrawArea drawArea,DefaultListModel listModel,JList filesList){
        //Here we first remove file object, then set list and finally make program to change file. changeFile must be last call because it also changes activeFile
        if(listModel.getSize() != 1){
            if(filesList.getSelectedIndex() > 0){
                filesList.setSelectedIndex(activeFile-1);
            } else {
                 filesList.setSelectedIndex(activeFile+1);    
            }
            projects.get(activeProject).getAllFiles().remove(activeFile);
            listModel.remove(activeFile);
            this.changeFile(filesList.getSelectedIndex(), drawArea);
        } else {
            projects.get(activeProject).getAllFiles().remove(activeFile);
            listModel.clear();
            activeFile = -1;
        }
    }
    public void removeProject(DrawArea drawArea,DefaultListModel listModel,JList projectsList, DefaultListModel filesListModel){
        
        if(listModel.getSize() != 1){
            int i = projectsList.getSelectedIndex();
            if(i > 0){
                projectsList.setSelectedIndex(activeProject-1);
            } else {
                projectsList.setSelectedIndex(activeProject+1);
            }
            int j = projectsList.getSelectedIndex();
            listModel.remove(activeProject);
            projects.remove(activeProject);
            this.changeProject(j, drawArea, filesListModel);
        } else {
            listModel.clear();
            filesListModel.clear();
            drawArea.setImg(null);
            ArrayList<int[][]> emptyAr = new ArrayList<int[][]>();
            drawArea.setPoints(emptyAr);
            projects.remove(activeProject);
            activeProject = -1;
            activeFile = -1;
        }
    }
    public void startWrite(DrawArea drawArea,String path, String fileName){
        ShapeSplitter shapeSplitter = new ShapeSplitter(drawArea.getImgData()[0],drawArea.getImgData()[1],projects.get(activeProject).getAllFiles(),fileName,path);
        if(projects.get(activeProject).getFile(activeFile).isSlice()){
            shapeSplitter.splitToSmaller();
        } else 
            shapeSplitter.jumpToWrite();
    }
   /* public void checkPoints(){
        ArrayList pointten =  this.projects.get(activeProject).getFile(activeFile).getPoints();
        if (pointten.isEmpty()){
            this.projects.get(activeProject).getFile(activeFile).setPoints([][]);
        }
        
    }
    */
    
    
    
}
