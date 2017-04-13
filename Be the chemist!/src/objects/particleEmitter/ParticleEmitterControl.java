/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.particleEmitter;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import java.util.ArrayList;
import java.util.Collections;
import objects.containers.Container;
import objects.particleEmitter.particle.Particle;

/**
 *
 * @author VIPkiller17
 */
public class ParticleEmitterControl extends AbstractControl{

    private ArrayList<Particle> activeParticles;
    
    private ParticleEmitter particleEmitter;
    
    private float timer;
    
    private int gasEmissionCounter,liquidEmissionCounter,solidEmissionCounter;
    
    private Particle lastParticle;
    
    public ParticleEmitterControl(ParticleEmitter particleEmitter){
        
        this.particleEmitter=particleEmitter;
        
        activeParticles=new ArrayList<Particle>();
        
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        
        timer+=tpf;
        
        //System.out.println("Particlemitter timer now at "+timer+", and particle emitter delay is at "+particleEmitter.getDelay());
        
        if(timer>particleEmitter.getDelay()){
            
            //System.out.println("Particlemitter timer higher than delay, emitting particle if particle emitter is emitting: "+particleEmitter.isEmitting());
            
            if(particleEmitter.isEmitting()){
                
                //System.out.println("Particlemitter is emitting, emitting partcile if parent object of emitter is a container: "+(particleEmitter.getParentObject() instanceof Container)+" and if it has a solution: "+((Container)particleEmitter.getParentObject()).getSolution());
            
                particleEmitter.emit();
            
            }
            
            timer=0;
            
        }
        
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
        
        
    }
    
    public ArrayList<Particle> getActiveParticles(){
        
        return activeParticles;
        
    }
    
    public void removeParticle(Particle particle){
        
        activeParticles.remove(particle);
        
        activeParticles.removeAll(Collections.singleton(null)); 
        
    }
    
}
