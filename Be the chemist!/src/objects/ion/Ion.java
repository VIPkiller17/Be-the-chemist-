/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.ion;

import java.util.ArrayList;
import objects.element.Element;

/**
 *
 * @author VIPkiller17
 */
public class Ion{
    
    private final int charge;
    private final int sign;
    private final ArrayList<Element> elements;
    private final ArrayList<Integer> counts;
    
    public Ion(int charge,int sign,ArrayList<Element> elements,ArrayList<Integer> counts){
        
        this.charge=charge;
        this.sign=sign;
        this.elements=elements;
        this.counts=counts;
        
    }
    
    public Ion(int charge,int sign,Element element,int count){
        
        elements=new ArrayList<>();
        counts=new ArrayList<>();
        
        this.charge=charge;
        this.sign=sign;
        elements.add(element);
        counts.add(count);
        
    }
    
    public int getCharge(){
        
        return charge;
        
    }
    
    public int getSign(){
        
        return sign;
        
    }
    
    public ArrayList<Element> getElements(){
        
        return elements;
        
    }
    
    @Override
    public boolean equals(Object otherIon){
        
        if(otherIon instanceof Ion){
            
            return ((Ion) otherIon).getCharge()==charge&&((Ion) otherIon).getSign()==sign&&((Ion) otherIon).getElements().equals(elements);
            
        }else{
            
            return false;
            
        }
        
    }
    
    @Override
    public String toString(){
        
        return super.toString();
        
    }
    
}
