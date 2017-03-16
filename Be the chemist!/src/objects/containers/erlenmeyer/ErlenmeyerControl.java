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
        
        //if container is rotated 45 degrees to one side, start particle emission
        if((spatial.getLocalRotation().getX()>=0.382f||spatial.getLocalRotation().getZ()>=0.382f)&&!erlenmeyer.isEmitting()){
            
            erlenmeyer.startParticleEmission();
            
        }else if(spatial.getLocalRotation().getX()<0.382f&&spatial.getLocalRotation().getZ()<0.382f&&erlenmeyer.isEmitting()){
            //if neither of the angles are higher than 45 degrees, stop the particle emission
            
            erlenmeyer.stopParticleEmission();
            
        }
        
        //if the temperature of the container is too high
        if(erlenmeyer.getTemperature()>erlenmeyer.getMaxTemperature()){
            
            
            
        }
        
        
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
    }
    
}
