/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.containers.beaker;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author VIPkiller17
 */
public class BeakerControl extends AbstractControl{

    private Beaker beaker;
    
    public BeakerControl(Beaker beaker){
        
        this.beaker=beaker;
        
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        
        //SET THE STATE OF THE CONTAINER
        
        //beaker.setTemperature(beaker.getSolution().getTemperature());
        
        //ACT BASED ON THE STATE OF THE CONTAINER
        
        //if container is rotated 45 degrees to one side, start particle emission
        if((spatial.getLocalRotation().getX()>=0.382f||spatial.getLocalRotation().getZ()>=0.382f)&&!beaker.isEmitting()){
            
            System.out.println("*Beaker starts emitting particles*");
            
            //beaker.startParticleEmission();
            
        }else if(spatial.getLocalRotation().getX()<0.382f&&spatial.getLocalRotation().getZ()<0.382f&&beaker.isEmitting()){
            //if neither of the angles are higher than 45 degrees, stop the particle emission
            
            System.out.println("*Beaker stops emitting particles*");
            
            //beaker.stopParticleEmission();
            
        }
        
        //if the temperature of the container is too high
        if(beaker.getTemperature()>beaker.getMaxTemperature()){
            
            beaker.meltDown();
            
        }
        
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
        
        
    }
    
    
    
}
