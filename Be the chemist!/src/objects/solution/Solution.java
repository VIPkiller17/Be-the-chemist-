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
    
    public int getRandomContainedState(int minState,int maxState){
        
        boolean containsGas=false;
        boolean containsLiquid=false;
        boolean containsSolid=false;
        
        for(Substance s: substances){
            
            switch(s.getStateInteger()){
                
                case 0:
                    containsGas=true;
                    break;
                case 1:
                    containsLiquid=true;
                    break;
                case 2:
                    containsSolid=true;
                    break;
                default:
                    System.out.println("ERROR: @ getting random contained state, a substance in substance list: "+substances.toString()+" has an invalid integer state.");
                
            }
            
        }
        
        int result;
        
        while(true){
            
            result=((int)(Math.random()*(maxState+1)))+minState;
            
            switch(result){
                
                case 0:
                    if(containsGas)
                        return 0;
                    break;
                case 1:
                    if(containsLiquid)
                        return 1;
                    break;
                case 2:
                    if(containsSolid)
                        return 2;
                    break;
                default:
                    System.out.println("ERROR: @ getting random contained state, result generated invalid.");
                
            }
            
        }
        
    }
    
    @Override
    public boolean equals(Object otherSolution){
        
        if(otherSolution instanceof Solution){
            
            if(substances.size()==((Solution)otherSolution).getSubstances().size()){
                
                for(int i=0;i<substances.size();i++){

                    if(!substances.get(i).equals(((Solution)otherSolution).getSubstances().get(i))){
                        
                        return false;
                        
                    }

                }
                
                return true;
            
            }else
                
                return false;
            
        }else
            
            return false;
        
    }
    
    @Override
    public String toString(){
        
        String t="Solution containing: ";
        
        for(Substance s: substances){
            
            t+=s.getName()+", ";
            
        }
        
        return t;
        
    }
    
}
