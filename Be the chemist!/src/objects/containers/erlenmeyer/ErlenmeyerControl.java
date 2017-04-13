/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.containers.erlenmeyer;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author VIPkiller17
 */

//Modify methods for better OOP (put reused ones in container and replace private data fields with setter method)
public class ErlenmeyerControl extends AbstractControl {
    
    private Erlenmeyer erlenmeyer;

    public ErlenmeyerControl(Erlenmeyer erlenmeyer) {
        this.erlenmeyer = erlenmeyer;
    }
    
    @Override
    public String toString() {
        return "Erlenmeyer: " + erlenmeyer;  
    }
    
    @Override
    public boolean equals(Object object) {
        
        if (object instanceof ErlenmeyerControl) 
            return true;
        else
            return false;
    }
    

    @Override
    protected void controlUpdate(float f) {
        
        //SET THE STATE OF THE CONTAINER
        
        erlenmeyer.setTemperature(erlenmeyer.getSolution().getTemperature());
        
        
        //ACT BASED ON THE STATE OF THE CONTAINER
        
        //if the temperature of the container is too high
        if(erlenmeyer.getTemperature()>erlenmeyer.getMaxTemperature()){
            
            //Replace model by breaked models that explodes, set their physics accordingly
            
        }
        
        
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
    }
    
}
