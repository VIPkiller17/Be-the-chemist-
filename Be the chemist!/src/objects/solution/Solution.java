/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.solution;

import com.jme3.math.ColorRGBA;
import java.util.ArrayList;
import objects.substance.Substance;

/**
 *
 * @author VIPkiller17
 */
public class Solution {
    
    private ArrayList<Substance> substances;
    private double quantity;
    private ColorRGBA liquidColor,solidColor;
    
    public Solution(Substance substance){
        
        this.substances.add(substance);
        
    }
    
    public Solution(ArrayList<Substance> substances){
        
        this.substances=substances;
        
    }
    
    public ArrayList<Substance> getSubstances(){
        
        return substances;
        
    }
    
    public Substance getSubstance(int index){
        
        return substances.get(index);
        
    }
    
    public void addSubstance(Substance substance){
        
        substances.add(substance);
        
    }
    
    public void removeSusbtance(int index){
        
        substances.remove(index);
        
        for(int i=index+1;i<substances.size();i++){
        
            substances.set(i-1,substances.get(i));
        
        }
        
        substances.remove(substances.size()-1);
        
    }
    
    public double getQuantity(){
        
        return quantity;
        
    }
    
    public void setQuantity(double quantity){
        
        this.quantity=quantity;
        
    }
    
    public ColorRGBA getLiquidColor(){
        
        return liquidColor;
        
    }
    
    public ColorRGBA getSolidColor(){
        
        return solidColor;
        
    }
    
}
