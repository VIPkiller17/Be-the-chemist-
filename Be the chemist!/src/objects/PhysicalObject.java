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

    private Vector3f position;
    private Node node;
    
    public PhysicalObject(Vector3f position){
        
        this.position=position;
        
    }
    
    public void setPos(Vector3f position){
        
        this.position=position;
        
    }
    
    public Vector3f getPos(){
        
        return position;
        
    }
    
    public void attachObject(Spatial object){
        
        node.attachChild(object);
        
    }
    
    public void detachObject(int index){
        
        node.detachChildAt(index);
        
    }
    
    @Override
    public boolean equals(Object otherPhysicalObject){
        
        if(otherPhysicalObject instanceof PhysicalObject)
            
            return ((PhysicalObject)otherPhysicalObject).position.equals(position);
            
        else
            
            return false;
        
    }
    
    @Override
    public String toString(){
        
        return "PhysicalObject at pos: "+position;
        
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
