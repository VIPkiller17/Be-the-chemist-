/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import interfaces.Describable;
import java.io.IOException;

/**
 *
 * @author VIPkiller17
 */
public abstract class PhysicalObject implements Savable, Describable{

    private String modelPath;
    private Spatial model;
    private Material mat;
    private Vector3f position;
    private Node node;
    
    PhysicalObject(String modelPath,Vector3f position){
        
        this.modelPath=modelPath;
        this.position=position;
        
    }
    
    public String getModelPath(){
        
        return modelPath;
        
    }
    
    public Spatial getSpatial() {
        
        return model;
        
    }
    
    public Material getMat(){
        
        return mat;
        
    }
    
    public void setColor(String code,ColorRGBA color){
        
        mat.setColor(code, color);
        
    }
    
    public void setPos(Vector3f position){
        
        this.position=position;
        
    }
    
    public Vector3f getPos(){
        
        return position;
        
    }
    
    @Override
    public boolean equals(Object otherPhysicalObject){
        
        if(otherPhysicalObject instanceof PhysicalObject)
            
            return ((PhysicalObject)otherPhysicalObject).position.equals(position)&&((PhysicalObject)otherPhysicalObject).modelPath.equals(modelPath);
            
        else
            
            return false;
        
    }
    
    @Override
    public String toString(){
        
        return "PhysicalObject at pos: "+position+" with modelPath: "+modelPath;
        
    }
    
    @Override
    public void write(JmeExporter je) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void read(JmeImporter ji) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public abstract String getDescription();
    
}
