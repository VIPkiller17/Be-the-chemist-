/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.containers.gasSac;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import jmevr.app.VRApplication;

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
        
        gasSac.updateNodeState();
        
        //ACT BASED ON THE STATE OF THE CONTAINER
        
        //if the temperature of the container is too high
        if(gasSac.getTemperature()>gasSac.getMaxTemperature()){
            
            gasSac.meltDown();
            
        }
        
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
        
        
    }
    
    
    
}
