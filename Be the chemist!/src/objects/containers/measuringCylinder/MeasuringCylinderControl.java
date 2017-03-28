/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.containers.measuringCylinder;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author VIPkiller17
 */
public class MeasuringCylinderControl extends AbstractControl{

    private MeasuringCylinder measuringCylinder;
    
    public MeasuringCylinderControl(MeasuringCylinder measuringCylinder){
        
        this.measuringCylinder=measuringCylinder;
        
    }
    
    @Override
    protected void controlUpdate(float f) {
        
        
        
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
        
        
    }
    
    
    
}
