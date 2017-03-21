/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.particleEmitter.particle;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author VIPkiller17
 */
public class ParticleControl extends AbstractControl{

    private Particle particle;
    
    public ParticleControl(Particle particle){
        
        this.particle=particle;
        
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        
        
        
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
        
        
    }
    
}
