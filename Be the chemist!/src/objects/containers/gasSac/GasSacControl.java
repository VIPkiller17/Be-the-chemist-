/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.containers.gasSac;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author VIPkiller17
 */
public class GasSacControl extends AbstractControl{

    private GasSac gasSac;
    
    public GasSacControl(GasSac gasSac){
        
        this.gasSac=gasSac;
        
    }
    
    @Override
    protected void controlUpdate(float f) {
        
        //SET THE STATE OF THE CONTAINER
        
        //gasSac.setTemperature(beaker.getSolution().getTemperature());
        
        //ACT BASED ON THE STATE OF THE CONTAINER
        
        //if container is rotated 45 degrees to one side, start particle emission
        if((spatial.getLocalRotation().getX()>=0.382f||spatial.getLocalRotation().getZ()>=0.382f)&&!gasSac.isEmitting()){
            
            System.out.println("*Beaker starts emitting particles*");
            
            //gasSac.startParticleEmission();
            
        }else if(spatial.getLocalRotation().getX()<0.382f&&spatial.getLocalRotation().getZ()<0.382f&&gasSac.isEmitting()){
            //if neither of the angles are higher than 45 degrees, stop the particle emission
            
            System.out.println("*Gas Sac stops emitting particles*");
            
            //gasSac.stopParticleEmission();
            
        }
        
        //if the temperature of the container is too high
        if(gasSac.getTemperature()>gasSac.getMaxTemperature()){
            
            gasSac.meltDown();
            
        }
        
        
        
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
        
        
    }
    
    
    
}
