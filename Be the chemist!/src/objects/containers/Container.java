/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.containers;

import com.jme3.math.Vector3f;
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
    
    private boolean isHighlightVisible;
    
    public Container(Main main,Vector3f position){
        
        super(main,position);
        
    }
    
    public Container(Main main,Vector3f position,Solution solution,double quantity){
        
        super(main,position);
        
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
    
    public void setQuantity(double quantity){
        
        this.quantity=quantity;
        
    }
    
    public double getQuantity(){
        
        return quantity;
        
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
    public void highlightVisible(boolean isHighlightVisible) {
        
        this.isHighlightVisible=isHighlightVisible;
        //TODO: MAKE THE HIGHLIGHT VISIBLE OR DECIDE IF WE CONTROL THAT IN THE CONTROL DEPENDING ON THE VALUE OF THE VARIABLE
        
    }

    @Override
    public boolean isHighlightVisible() {
        
        return isHighlightVisible;
        
    }
    
    @Override
    public abstract String getDescription();
    
}
