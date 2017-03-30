/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.containers.pipette;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author VIPkiller17
 */
public class PipetteControl extends AbstractControl{
    
    private float testTPF;

    private Pipette pipette;
    
    public PipetteControl(Pipette pipette){
        
        this.pipette=pipette;
        
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        
        //TESTING
        
        //SET THE STATE OF THE CONTAINER
        
        //Pipette.setTemperature(Pipette.getSolution().getTemperature());
        
        //ACT BASED ON THE STATE OF THE CONTAINER
        
        //if the temperature of the container is too high
        if(pipette.getTemperature()>pipette.getMaxTemperature()){
            
            pipette.meltDown();
            
        }
        
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
        
        
    }
    
    
    
}
