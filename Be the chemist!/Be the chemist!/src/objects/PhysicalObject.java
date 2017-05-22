/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import interfaces.Describable;
import java.io.IOException;
import main.Main;

/**
 *
 * @author VIPkiller17
 */
public abstract class PhysicalObject implements Savable, Describable{

    private Main main;
    
    private Vector3f position;
    private Node node;
    
    private boolean insideFumeHood;
    
    private double mass;
    
    protected boolean destroyed;
    
    public PhysicalObject(Main main,Vector3f position){
        
        this.position=position;
        this.main=main;
        
        node=new Node();
        
    }
    
    public PhysicalObject(Main main,Vector3f position, double mass){
        
        this.position=position;
        this.mass=mass;
        this.main=main;
        
        node=new Node();
        
    }
    
    public abstract void setPos(Vector3f position);
    
    public Vector3f getPos(){
        
        return position;
        
    }
    
    public void attachObject(Spatial object){
        
        node.attachChild(object);
        
    }
    
    public void detachObject(int index){
        
        node.detachChildAt(index);
        
    }
    
    public abstract Node getNode();
    
    public void addPhysicsControl(RigidBodyControl control){
        
        node.addControl(control);
        
    }
    
    public void setInsideFumeHood(boolean insideFumeHood){
        
        this.insideFumeHood=insideFumeHood;
        
    }
    
    public boolean isInsideFumeHood(){
        
        return insideFumeHood;
        
    }
    
    public double getMass(){
        
        return mass;
        
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
        
    }

    @Override
    public void read(JmeImporter ji) throws IOException {
        
    }

    @Override
    public abstract String getDescription();
    
    public abstract String getName();
    
    public abstract void destroy();
    
    public boolean isDestroyed(){
        
        return destroyed;
        
    }
    
}
