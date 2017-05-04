/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.substance;

import com.jme3.math.ColorRGBA;
import java.util.ArrayList;
import main.Main;
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
    
    private Main main;
    
    public Substance(Main main,String equation,String name,double meltingPoint,double boilingPoint,double sublimationPoint,String type,double molarMass,double density,ColorRGBA color,ArrayList<Ion> ions,ArrayList<Integer> ionCounts,boolean soluble){
        
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
        this.main=main;
        
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
        
        System.out.println("Checking "+getName()+"'s state at temperature: "+temperature+" with mp+"+meltingPoint+", bp: "+boilingPoint+" and sp: "+sublimationPoint);
        
        if(meltingPoint==-1&&boilingPoint==-1){
            
            System.out.println(getName()+" does NOT have a melting point and boiling point, meaning it has a sublimation point");
            
            if(temperature<sublimationPoint){
                
                return 2;//solid
                
            }else if(temperature>sublimationPoint){
                
                return 0;//gas
                
            }
            
        }else if(meltingPoint!=-1&&boilingPoint!=-1){
            
            System.out.println(getName()+" has a melting point and boiling point");
            
            if(temperature<meltingPoint){
                
                System.out.println(getName()+" is a solid at temperature: "+temperature);
                
                return 2;//solid
                
            }else if(temperature>meltingPoint&&temperature<boilingPoint){
                
                System.out.println(getName()+" is a liquid at temperature: "+temperature);
                
                return 1;//liquid
                
            }else if(temperature>boilingPoint){
                
                System.out.println(getName()+" is a gas at temperature: "+temperature);
                
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
        
        if(type.contains("Acidic")){
            
            return 1;
            
        }else if(type.contains("Basic")){
            
            return 2;
            
        }else if(type.contains("Halogen")){
            
            return 3;
            
        }else if(type.contains("Salt")){
            
            return 4;
            
        }else if(type.contains("Metal")){
            
            return 5;
            
        }else if(type.contains("None")){
            
            return 6;
            
        }else{
            
            return 6;
            
        }
        
    }
    
    public boolean hasIon(int index){
        
        for(int i=0;i<ions.size();i++){
            
            if(ions.get(i).equals(main.getIons().get(index))){
                
                return true;
                
            }
            
        }
        
        return false;
        
    }
    
    public int[] containsReactiveIon(){
        
        int[] presentIonicInfo=new int[3];
        
        for(int i=0;i<ions.size();i++){
        
            for(int j=0;j<main.getReactivitySeries().size();j++){
                
                if(ions.get(i).getElements().size()==1&&ions.get(i).getElements().get(0).equals(main.getReactivitySeries().get(j))){
                    
                    presentIonicInfo[0]=i;
                    presentIonicInfo[1]=j;
                    
                    if(ions.size()>1){
                    
                        if(i==0)

                            presentIonicInfo[2]=1;

                        else if(i==1)

                            presentIonicInfo[2]=0;

                        else

                            System.out.println("Substance: "+name+"'s reactive ion is at an index !=(0||1)");
                        
                    }else
                        
                        presentIonicInfo[2]=-1;
                    
                    return presentIonicInfo;
                    
                }
                
            }
            
        }
        
        presentIonicInfo[0]=-1;
        presentIonicInfo[1]=-1;
        presentIonicInfo[2]=-1;
        
        return presentIonicInfo;
        
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
