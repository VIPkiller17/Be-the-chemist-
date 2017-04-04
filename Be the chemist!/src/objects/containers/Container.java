/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.containers;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import interfaces.Grabbable;
import main.Main;
import objects.PhysicalObject;
import objects.solution.Solution;

/**
 *
 * @author VIPkiller17
 */
public abstract class Container extends PhysicalObject implements Grabbable{

    private Solution solution;
    private boolean full;
    private boolean closed;
    private boolean contains;
    private double quantity;
    private double temperature;
    private double pressure;
    
    protected boolean highlightVisible;
    
    public Container(Main main,Vector3f position){
        
        super(main,position);
        
    }
    
    public Container(Main main,Vector3f position,Solution solution){
        
        super(main,position);
        
        this.solution=solution;
        this.quantity=solution.getQuantity();
        
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
    
    public void setQuantity(double quantity){
        
        this.quantity=quantity;
        
    }
    
    public double getQuantity(){
        
        if(solution!=null)
        
            return solution.getQuantity();
        
        else
            
            return 0;
        
    }
    
    public void setTemperature(double temperature){
        
        this.temperature=temperature;
        
    }
    
    public double getTemperature(){
        
        return temperature;
        
    }
    
    public void setPressure(double pressure){
        
        this.pressure=pressure;
        
    }
    
    public double getPressure(){
        
        return pressure;
        
    }
    
    public void mergeSolution(Solution solution){
        
        if(this.solution!=null)
            
            this.solution.merge(solution);
        
        else
            
            this.solution=solution;
        
    }
    
    @Override
    public boolean equals(Object otherContainer){
        
        if(otherContainer instanceof Container)
            
            return ((Container)otherContainer).getQuantity()==quantity&&((Container)otherContainer).getTemperature()==temperature&&((Container)otherContainer).getPressure()==pressure;
        
        else
            
            return false;
        
    }
    
    @Override
    public String toString(){
        
        return "Container containing "+quantity+" liters of "+solution+" with temperature "+temperature+" and pressure "+pressure;
        
    }
    
    @Override
    public abstract void highlightVisible(boolean isHighlightVisible);

    @Override
    public boolean isHighlightVisible() {
        
        return highlightVisible;
        
    }
    
    @Override
    public abstract String getDescription();
    
    @Override
    public abstract void setPos(Vector3f position);
    
    @Override
    public abstract Node getNode();
    
    public abstract boolean canContain(int state);
    
}