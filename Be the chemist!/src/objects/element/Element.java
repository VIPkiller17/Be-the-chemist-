/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.element;

/**
 *
 * @author VIPkiller17
 */
public class Element {
    
    private final String name;
    private final String symbol;
    private final int number;
    private final double electronegativity;
    private final double molarMass;
    
    public Element(int number,String name,String symbol,double electronegativity,double molarMass){
        
        this.number=number;
        this.name=name;
        this.symbol=symbol;
        this.electronegativity=electronegativity;
        this.molarMass=molarMass;
        
    }
    
    public double getElectronegativity(){
        
        return electronegativity;
        
    }
    
    public double getMolarMass(){
        
        return molarMass;
        
    }
    
    public String getName(){
        
        return name;
        
    }
    
    public int getNumber(){
        
        return number;
        
    }
    
    public String getSymbol(){
        
        return symbol;
        
    }
    
}
