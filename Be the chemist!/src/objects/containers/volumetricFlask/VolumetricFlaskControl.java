/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.containers.volumetricFlask;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author VIPkiller17
 */

//Modify methods for better OOP (put reused ones in container and replace private data fields with setter method)
public class VolumetricFlaskControl extends AbstractControl {
    
    private VolumetricFlask volumetricFlask;

    public VolumetricFlaskControl(VolumetricFlask volumetricFlask) {
        this.volumetricFlask = volumetricFlask;
    }
    
    @Override
    public String toString() {
        return "VolumetricFlask: " + volumetricFlask;  
    }
    
    @Override
    public boolean equals(Object object) {
        
        if (object instanceof VolumetricFlaskControl) 
            return true;
        else
            return false;
    }
    

    @Override
    protected void controlUpdate(float f) {
        
        //SET THE STATE OF THE CONTAINER
        
        volumetricFlask.setTemperature(volumetricFlask.getSolution().getTemperature());
        
        
        //ACT BASED ON THE STATE OF THE CONTAINER
        
        //if container is rotated 45 degrees to one side, start particle emission and if not closed
        if((spatial.getLocalRotation().getX()>=0.382f||spatial.getLocalRotation().getZ()>=0.382f)&&!volumetricFlask.isEmitting() && !volumetricFlask.isClosed()) {
            
            volumetricFlask.startParticleEmission();
            
            
        }else if(spatial.getLocalRotation().getX()<0.382f&&spatial.getLocalRotation().getZ()<0.382f&&volumetricFlask.isEmitting()){
            //if neither of the angles are higher than 45 degrees, stop the particle emission
            
            volumetricFlask.stopParticleEmission();
            
        }
        
        //if the temperature of the container is too high
        if(volumetricFlask.getTemperature()>volumetricFlask.getMaxTemperature()){
            
            //Replace model by breaked models that explodes, set their physics accordingly
            
        }
        
        
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
    }
    
}
