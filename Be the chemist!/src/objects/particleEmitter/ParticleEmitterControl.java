/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.particleEmitter;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author VIPkiller17
 */
public class ParticleEmitterControl extends AbstractControl{

    private ParticleEmitter particleEmitter;
    
    private float timer;
    
    public ParticleEmitterControl(ParticleEmitter particleEmitter){
        
        this.particleEmitter=particleEmitter;
        
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        
        timer+=tpf;
        
        if(timer>particleEmitter.getDelay()){
            
            //MAKE A NEW PARTICLE WITH THE GIVEN PARAMETERS
            
        }
        
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
        
        
    }
    
    
    
}
