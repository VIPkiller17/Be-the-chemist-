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
        
        if(timer>particleEmitter.getDelay()&&particleEmitter.isEmitting()){
            
            if(particleEmitter.getParentObject() instanceof Container){
                
                boolean[] possibleStates=((Container)particleEmitter.getParentObject()).getSolution().containsStates();
                
                if(possibleStates[0])
                    
                    activeParticles.add(new Particle(particleEmitter,"Models/Particles/Gas/Gas.j3o",0));
                
                if(possibleStates[1])
                    
                    activeParticles.add(new Particle(particleEmitter,"Models/Particles/Liquid/Liquid.j3o",1));
                
                if(possibleStates[2])
                    
                    activeParticles.add(new Particle(particleEmitter,"Models/Particles/Solid/Solid.j3o",2));
                
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
