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
        
        //if the temperature of the container is too high
        if(reagentBottle.getTemperature()>reagentBottle.getMaxTemperature()){
            
            reagentBottle.meltDown();
            
        }
        
        
        
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
        
        
    }
    
}
