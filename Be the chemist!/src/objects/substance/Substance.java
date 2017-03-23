/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.substance;

import objects.solution.Solution;

/**
 *
 * @author VIPkiller17
 */
public class Substance {
    
    //private Solution parentSolution;
    
    private double quantity;
    private String stateString;
    private int stateInteger;
    private String equation;
    private String name;
    private double temperature;
    private double meltingPoint;
    private double boilingPoint;
    private double sublimationPoint;
    private String type;
    private double aqueousConcentrationM;
    private double aqueousConcentrationPercent;
    private double densityGramPerML;
    private double molarMass;
    
    public Substance(String equation,String name,double quantity,String stateString,int stateInteger,double temperature,double meltingPoint,double boilingPoint,double sublimationPoint,String type,double molarMass,double density){
        
        this.equation=equation;
        this.name=name;
        this.quantity=quantity;
        this.stateString=stateString;
        this.stateInteger=stateInteger;
        this.temperature=temperature;
        this.meltingPoint=meltingPoint;
        this.boilingPoint=boilingPoint;
        this.sublimationPoint=sublimationPoint;
        this.type=type;
        this.molarMass=molarMass;
        
    }
    
    public Substance(String equation,String name,double quantity,String stateString,int stateInteger,double temperature,double meltingPoint,double boilingPoint,double sublimationPoint,String type,double molarMass,double aqueousConcentrationM,double aqueousConcentrationPercent,double densityGramPerML){
        
        this.equation=equation;
        this.name=name;
        this.quantity=quantity;
        this.stateString=stateString;
        this.stateInteger=stateInteger;
        this.temperature=temperature;
        this.meltingPoint=meltingPoint;
        this.boilingPoint=boilingPoint;
        this.sublimationPoint=sublimationPoint;
        this.type=type;
        this.molarMass=molarMass;
        this.aqueousConcentrationM=aqueousConcentrationM;
        this.aqueousConcentrationPercent=aqueousConcentrationPercent;
        this.densityGramPerML=densityGramPerML;
        
    }
    
    public void setQuantity(double quantity){
        
        this.quantity=quantity;
        
    }
    
    public double getQuantity(){
        
        return quantity;
        
    }
    
    public void setState(String state){
        
        if(state.equalsIgnoreCase("Gas")){
            
            stateInteger=0;
            stateString=state;
        
        }else if(state.equalsIgnoreCase("Liquid")){
            
            stateInteger=1;
            stateString=state;
        
        }else if(state.equalsIgnoreCase("Solid")){
            
            stateInteger=2;
            stateString=state;
        
        }else{
            
            System.out.println("ERROR: State "+state+" set to substance: "+name+", is not valid");
            
            stateInteger=-1;
            stateString="Invalid";
            
        }
        
    }
    
    public void setState(int state){
        
        switch(state){
            
            case 0:
                stateString="Gas";
                stateInteger=state;
                break;
            case 1:
                stateString="Liquid";
                stateInteger=state;
                break;
            case 2:
                stateString="Solid";
                stateInteger=state;
                break;
            default:
                System.out.println("ERROR: Integer state "+state+" set to substance: "+name+", is not valid");
                stateString="Invalid";
                stateInteger=-1;
            
        }
        
    }
    
    public String getStateString(){
        
        return stateString;
        
    }
    
    public int getStateInteger(){
        
        return stateInteger;
        
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
    
    public void setAqueousConcentrationM(double aqueousConcentrationM){
        
        this.aqueousConcentrationM=aqueousConcentrationM;
        
        aqueousConcentrationPercent=aqueousConcentrationM*100*(molarMass/densityGramPerML);
        
    }
    
    public double getAqueousConcentrationM(){
        
        return aqueousConcentrationM;
        
    }
    
    public void setAqueousConcentrationPercent(double aqueousConcentrationPercent){
        
        this.aqueousConcentrationPercent=aqueousConcentrationPercent;
        
        /*old way, need to test if both give the same thing
        double massPerLiterOfSolution=(1000*densityGramPerML);
        double massOfSubstancePerLiterOfSolution=(massPerLiterOfSolution*aqueousConcentrationPercent)/100;
        double molesOfSubstancePerLiterOfSolution=massOfSubstancePerLiterOfSolution/molarMass;
        aqueousConcentrationM=molesOfSubstancePerLiterOfSolution/quantity;
        */
        
        aqueousConcentrationM=aqueousConcentrationPercent*(densityGramPerML/(100*molarMass));
        
    }
    
    public double getAqueousConcentrationPercent(){
        
        return aqueousConcentrationPercent;
        
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
