/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.particleEmitter;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import objects.containers.Container;
import objects.particleEmitter.particle.Particle;

/**
 *
 * @author VIPkiller17
 */
public class ParticleEmitterControl extends AbstractControl{

    private ParticleEmitter particleEmitter;
    
    private float timer;
    
    private int gasEmissionCounter,liquidEmissionCounter,solidEmissionCounter;
    
    public ParticleEmitterControl(ParticleEmitter particleEmitter){
        
        this.particleEmitter=particleEmitter;
        
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        
        timer+=tpf;
        
        if(timer>particleEmitter.getDelay()){
            
            Container presentParent=particleEmitter.ifParentObjectContainerGetIt();
            
            if(presentParent!=null){
                
                boolean[] possibleStates=presentParent.getSolution().containsStates();
                
                if(gasEmissionCounter<=liquidEmissionCounter&&gasEmissionCounter<=solidEmissionCounter){
                    
                    new Particle(particleEmitter,particleEmitter.getGasParticleModelPath(),0);
                    
                }
                
            }
            
        }
        
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
        
        
    }
    
    
    
}
