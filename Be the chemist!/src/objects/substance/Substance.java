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
    
    public int getStateInteger(double temperature){
        
        if(meltingPoint==-1&&boilingPoint==-1){
            
            if(temperature<sublimationPoint){
                
                return 2;//solid
                
            }else if(temperature>sublimationPoint){
                
                return 0;//gas
                
            }
            
        }else if(meltingPoint!=-1&&boilingPoint!=-1){
            
            if(temperature<meltingPoint){
                
                return 2;//solid
                
            }else if(temperature>meltingPoint&&temperature<boilingPoint){
                
                return 1;//liquid
                
            }else if(temperature>boilingPoint){
                
                return 0;//gas
                
            }
            
        }else if(meltingPoint==-1&&boilingPoint==-1&&sublimationPoint==-1){
            
            //for specific exceptions
            if(name.contains("phosphorus")||name.contains("Phosphorus")){
                
                return 2;//solid
                
            }
            
        }
        
        //the following, for soem reason could not be put as an else statement
            
        System.out.println("Invalid return value from getStateInteger("+temperature+") on substance: "+name);

        return -1;
        
    }
    
    public int getTypeInteger(){
        
        if(type.contains("None")){
            
            return 0;
            
        }else if(type.contains("Acid")){
            
            return 1;
            
        }else if(type.contains("Base")){
            
            return 2;
            
        }else if(type.contains("Halogen")){
            
            return 3;
            
        }else if(type.contains("Salt")){
            
            return 4;
            
        }else if(type.contains("Metal")){
            
            return 5;
            
        }else if(type.contains("Other")){
            
            return 6;
            
        }else{
            
            return -1;
            
        }
        
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
