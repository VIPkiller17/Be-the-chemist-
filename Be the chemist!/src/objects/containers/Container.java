/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.containers;

import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import objects.PhysicalObject;
import objects.particleEmitter.ParticleEmitter;
import objects.solution.Solution;

/**
 *
 * @author VIPkiller17
 */
public abstract class Container extends PhysicalObject{

    private Solution solution;
    private boolean full;
    private boolean closed;
    private boolean contains;
    private boolean closeable;
    private double quantity;
    private double maxQuantity;
    private double temperature;
    private double maxTemperature;
    private double pressure;
    private double maxPressureOpenned;
    private double maxPressureClosed;
    private Spatial highlightModel;
    private Material highlightModelMat;
    private Spatial stopperModel;
    private Material stopperMat;
    private Spatial holedStopperModel;
    private Material holedSTopperMat;
    private Spatial liquidModel;
    private Material liquidModelMat;
    private Spatial solidModel;
    private Material solidModelMat;
    private ParticleEmitter particleEmitter;
    
    public Container(String modelPath,Vector3f position,double maxQuantity,double maxTemperature,double maxPressureOpenned,double maxPressureClosed,boolean closeable,Spatial stopperModel,Spatial holedStopperModel,Spatial liquidModel,Spatial solidModel,Spatial highlightModel){
        
        super(modelPath,position);
        
        this.maxQuantity=maxQuantity;
        this.maxTemperature=maxTemperature;
        this.maxPressureOpenned=maxPressureOpenned;
        this.maxPressureClosed=maxPressureClosed;
        this.closeable=closeable;
        this.stopperModel=stopperModel;
        this.holedStopperModel=holedStopperModel;
        this.liquidModel=liquidModel;
        this.solidModel=solidModel;
        this.highlightModel=highlightModel;
        
    }
    
    public Container(String modelPath,Vector3f position,double maxQuantity,double maxTemperature,double maxPressureOpenned,double maxPressureClosed,boolean closeable,Spatial stopperModel,Spatial holedStopperModel,Spatial liquidModel,Spatial solidModel,Spatial highlightModel,Solution solution,double quantity){
        
        super(modelPath,position);
        
        this.maxQuantity=maxQuantity;
        this.maxTemperature=maxTemperature;
        this.maxPressureOpenned=maxPressureOpenned;
        this.maxPressureClosed=maxPressureClosed;
        this.closeable=closeable;
        this.stopperModel=stopperModel;
        this.holedStopperModel=holedStopperModel;
        this.liquidModel=liquidModel;
        this.solidModel=solidModel;
        this.highlightModel=highlightModel;
        this.solution=solution;
        this.quantity=quantity;
        
    }
    
    public void setSolution(Solution solution){
        
        this.solution=solution;
        
    }
    
    public Solution getSolution(){
        
        return solution;
        
    }
    
    public void setFull(boolean full){
        
        this.full=full;
        
    }
    
    public boolean isFull(){
        
        return full;
        
    }
    
    public void setClosed(boolean closed){
        
        this.closed=closed;
        
    }
    
    public boolean isClosed(){
        
        return closed;
        
    }
    
    public void setContains(boolean contains){
        
        this.contains=contains;
        
    }
    
    public boolean contains(){
        
        return contains;
        
    }
    
    public boolean isCloseable(){
        
        return closeable;
        
    }
    
    
    
    @Override
    public abstract String getDescription();
    
}
