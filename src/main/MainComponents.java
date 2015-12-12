/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Image;
import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.CopyOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.*;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

/**
 *
 * @author RAndom MC
 */
public class MainComponents {
    private ArrayList<Project> projects;
    private int activeFile;
    private int activeProject;
    private Image img;
    private ArrayList<String> toZip;
    
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
            /*if(projects.get(activeProject).getSaved()){
                String p = projects.get(activeProject).getFile(activeFile).getImgPath();
                BufferedImage img;
                try {
                    img = ImageIO.read(new File(p));
                    drawArea.setImg(img);
                } catch (IOException ex) {
                    System.out.println("Error "+ex);
                }
                return "";
            }*/
            if (tiedosto.getImage() != null){
                drawArea.setImg(tiedosto.getImage());
                ArrayList<int[][]> emptyAr = new ArrayList<int[][]>();
                drawArea.setPoints(emptyAr);
                Settings setting = new Settings();
                ShapeFile file = new ShapeFile(tiedosto.getImage(), tiedosto.getPath(), this.getFilesAmount()+1,setting.getDefaulthFileName());
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
        drawArea.setPoints(projects.get(activeProject).getFile(activeFile).getPoints());//here we have to update drawAreas point manually for some reason (unlike in removing)
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
        } else{
            shapeSplitter.jumpToWrite();
            }
    }
    // Saving the project 
    public void saveProject(){
        this.toZip = new ArrayList<>();
        FileSelector selector = new FileSelector(2);
        FileOutputStream fileOut = null;
        String extension = "";
        ObjectOutputStream objOut = null;
        FileOutputStream fileZipOut = null;
        ZipOutputStream zipOut = null;
        // if user selected a path
        if(selector.getPath() != "none"){
            try{
                // get the active projects name
                String projectname = projects.get(activeProject).getName();
                int nmb = projects.get(activeProject).getFileAmount()-1;
                // kopioidaan kaikkien kuvien polku taulukkoon
                for(int i = 0; i<=nmb; i++){
                
                    String path = projects.get(activeProject).getFile(i).getImgPath();
                    String imgName = projects.get(activeProject).getFile(i).getShapeName();

                    int idx = path.lastIndexOf('.');
                    if (idx > 0) {
                        // removes / -symbol at the end so it doesnt become a folder
                        extension = path.substring(idx).substring(0, path.substring(idx).length()-1);
                    }
                    String fileName = (selector.getPath()+"/"+imgName+extension);
                    this.toZip.add(i, fileName);
                    Path FROM = Paths.get(path);
                    Path TO = Paths.get(fileName);
                
                    CopyOption[] options = new CopyOption[]{
                        //StandardCopyOption.REPLACE_EXISTING // vaarallinen vaihtoehto
                        StandardCopyOption.COPY_ATTRIBUTES
                    };
                    java.nio.file.Files.copy(FROM, TO, options);
                }
                String datName = selector.getPath()+projectname+".dat";
                this.toZip.add(this.toZip.size()-1, datName);
                fileOut = new FileOutputStream(new File(datName));
                // write into stream
                objOut = new ObjectOutputStream(fileOut);
                // write project
                objOut.writeObject(projects.get(activeProject));
                objOut.flush();
            
                fileZipOut = new FileOutputStream(selector.getPath()+projectname+".zip");
                zipOut = new ZipOutputStream(fileZipOut);
                
                // Zip the project
                addToZip(zipOut);
            }catch(Exception e){
                System.out.println("Error ocurred: "+e);
            } finally {
                try {
                    if (objOut != null && zipOut != null && fileZipOut != null && fileOut != null ){
                        fileOut.close();
                        objOut.close();
                        zipOut.close();
                        fileZipOut.close();
                        System.out.println("closing files");
                        System.gc();

                    }
                    // call for folder cleaner
                    try {
                        //dirty fix to make sure that dat is actually closed. dat file stram is so slow to write that without this cleaning fill fail
                        Thread.sleep(200);
                        cleanFolder();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (IOException ioe) {
                    System.out.println("Error occured while closing file: "+ioe);
                }
            }
        }
    }
    // send project to zip file
    public void addToZip(ZipOutputStream zipOut) throws FileNotFoundException, IOException {
        FileInputStream fileIn = null;
        byte[] buffer = new byte[1024];
        for(int i = 0; i<= this.toZip.size()-1; i++){
            File file = new File(this.toZip.get(i));
            String filename = this.toZip.get(i);
            System.out.println("tiedoston nimi "+filename);
            String name = "";
            int idx = filename.lastIndexOf('/');
                if (idx > 0) {
                    name = filename.substring(idx+1);
                }
            ZipEntry zipEntry = new ZipEntry(name);
            zipOut.putNextEntry(zipEntry);
            fileIn = new FileInputStream(file);
            int len;
            while ((len = fileIn.read(buffer)) > 0) {
                zipOut.write(buffer, 0, len);
            }
            
        }
        zipOut.closeEntry();
        if(fileIn != null){
            fileIn.close();
        }
        System.out.println("done");
    }
    
    //clean folder after zipping is done
    public void cleanFolder(){
        // delete all the files but the zip
        //cant delete the dat file...
        for(int i = 0; i<= this.toZip.size()-1; i++){
            File file = new File(this.toZip.get(i));
            System.out.println("Deleting " +file.getName());
            if (file.exists()){
                if(file.delete()){
                    System.out.println(file.getName() + " is deleted");
                } else{
                    System.out.println("Delete operation is failed.");
                }
            }
        }
    }
    
    // Open saved project
    public Project openProject() throws IOException{
        FileInputStream fileIn;
        ObjectInputStream objIn = null;
        FileSelector selector = new FileSelector(3);
        String dest = "D:/dontKillMe/";
        System.out.println("Zipin polku on: "+selector.getPath());
          try {
        ZipFile zipFile = new ZipFile(selector.getPath());
        zipFile.extractAll(dest);
        } catch (ZipException e) {
              System.out.println("Zip error: "+e);
        }
       // if user selected a path
       if(selector.getPath() != null){
            try {
                //open selected .dat file 
                fileIn = new FileInputStream(new File(selector.getPath()));
                objIn = new ObjectInputStream(fileIn);
                Project p = (Project)objIn.readObject();
                return p;
            } catch (IOException io){
                System.out.println("Error ocurred: "+io);
                return null;
            } catch (ClassNotFoundException n) {
                System.out.println("Error while opening saved project: "+n);
                return null;
            } finally {
                try { if (objIn != null) objIn.close();
                } catch (IOException ioe) {
                    System.out.println("Error occured while closing file: "+ioe);
                }
            }
        } else{
            System.out.println("cannot open");
            return null;
        }
          
    }
    
    public void removePoint(Object e){ 
        JLabel point = (JLabel) e;
        this.getActualActiveFile().removePoint(Integer.parseInt(point.getName()));
        System.out.println(this.getActualActiveFile().getPoints().size());
    }
}
