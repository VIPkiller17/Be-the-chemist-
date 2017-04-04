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
    
    private double electronegativity;
    private double molarMass;
    
    public Element(double electronegativity,double molarMass){
        
        this.electronegativity=electronegativity;
        this.molarMass=molarMass;
        
    }
    
    public double getElectronegativity(){
        
        return electronegativity;
        
    }
    
    public double getMolarMass(){
        
        return molarMass;
        
    }
    
}
