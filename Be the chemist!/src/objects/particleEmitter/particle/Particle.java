/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.particleEmitter.particle;

import com.jme3.scene.Spatial;
import objects.containers.Container;
import objects.particleEmitter.ParticleEmitter;
import objects.solution.Solution;

/**
 *
 * @author VIPkiller17
 */
public class Particle {
    
    private ParticleEmitter particleEmitter;
    
    private Solution solution;
    
    private int state;
    
    private Spatial model;
    
    public Particle(ParticleEmitter particleEmitter,String modelPath,int state){
        
        this.particleEmitter=particleEmitter;
        
        
        
    }
    
}
