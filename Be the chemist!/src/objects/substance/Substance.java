/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.substance;

import com.jme3.math.ColorRGBA;
import java.util.ArrayList;
import objects.ion.Ion;

/**
 *
 * @author VIPkiller17
 */
public class Substance{
    
    //private Solution parentSolution;
    
    private String equation;
    private String name;
    private double meltingPoint;
    private double boilingPoint;
    private double sublimationPoint;
    private String type;
    private double molarMass;
    private double density;
    private ColorRGBA color;
    private ArrayList<Ion> ions;
    private ArrayList<Integer> ionCounts;
    private boolean soluble;
    
    public Substance(String equation,String name,double meltingPoint,double boilingPoint,double sublimationPoint,String type,double molarMass,double density,ColorRGBA color,ArrayList<Ion> ions,ArrayList<Integer> ionCounts,boolean soluble){
        
        this.equation=equation;
        this.name=name;
        this.meltingPoint=meltingPoint;
        this.boilingPoint=boilingPoint;
        this.sublimationPoint=sublimationPoint;
        this.type=type;
        this.molarMass=molarMass;
        this.density=density;
        this.color=color;
        this.ions=ions;
        this.ionCounts=ionCounts;
        this.soluble=soluble;
        
    }
    
    public String getEquation(){
        
        return equation;
        
    }
    
    public String getName(){
        
        return name;
        
    }
    
    public double getBoilingPoint(){
        
        return boilingPoint;
        
    }
    
    public double getMeltingPoint(){
        
        return meltingPoint;
        
    }
    
    public double getSublimationPoint(){
        
        return sublimationPoint;
        
    }
    
    public String getType(){
        
        return type;
        
    }
    
    public double getMolarMass(){
        
        return molarMass;
        
    }
    
    public double getDensity(){
        
        return density;
        
    }
    
    public ColorRGBA getColor(){
        
        return color;
        
    }
    
    public ArrayList<Ion> getIons(){
        
        return ions;
        
    }
    
    public ArrayList<Integer> getIonCounts(){
        
        return ionCounts;
        
    }
    
    public boolean isSoluble(){
        
        return soluble;
        
    }
    
    public int getStateInteger(double kelvin){
        
        if()
        
    }
    
    @Override
    public boolean equals(Object otherSubstance){
        
        if(otherSubstance instanceof Substance)
        
            return ((Substance)otherSubstance).name.equals(name);
        
        else
            
            return false;
        
    }
    
    @Override
    public String toString(){
        
        return "Substance: "+name;
        
    }
    
}
