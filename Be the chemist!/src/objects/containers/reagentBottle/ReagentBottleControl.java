/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.containers.reagentBottle;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author VIPkiller17
 */
public class ReagentBottleControl extends AbstractControl{
    
    private ReagentBottle reagentBottle;
    
    public ReagentBottleControl(ReagentBottle reagentBottle){
        
        this.reagentBottle=reagentBottle;
        
    }
    
    @Override
    protected void controlUpdate(float f) {
        
        //TO ADD: Seal particle emission when reagentBottleTop is in contact with the reagentBottle particle emitter (in handControll)
        
        //SET THE STATE OF THE CONTAINER
        
        //reagentBottle.setTemperature(reagentBottle.getSolution().getTemperature());
        
        //ACT BASED ON THE STATE OF THE CONTAINER
        
        //if container is rotated 45 degrees to one side, start particle emission
        if((spatial.getLocalRotation().getX()>=0.382f||spatial.getLocalRotation().getZ()>=0.382f)&&!reagentBottle.isEmitting()){
            
            System.out.println("*Reagent Bottle starts emitting particles*");
            
            //reagentBottle.startParticleEmission();
            
        }else if(spatial.getLocalRotation().getX()<0.382f&&spatial.getLocalRotation().getZ()<0.382f&&reagentBottle.isEmitting()){
            //if neither of the angles are higher than 45 degrees, stop the particle emission
            
            System.out.println("*Reagent Bottle stops emitting particles*");
            
            //reagentBottle.stopParticleEmission();
            
        }
        
        //if the temperature of the container is too high
        if(reagentBottle.getTemperature()>reagentBottle.getMaxTemperature()){
            
            reagentBottle.meltDown();
            
        }
        
        
        
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
        
        
    }
    
}
