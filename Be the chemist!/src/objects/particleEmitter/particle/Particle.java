/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.particleEmitter.particle;

import com.jme3.scene.Spatial;
import java.util.ArrayList;
import objects.particleEmitter.ParticleEmitter;
import objects.substance.Substance;

/**
 *
 * @author VIPkiller17
 */
public class Particle {
    
    private ParticleEmitter particleEmitter;
    
    private ArrayList<Substance> substances;
    
    private int state;
    
    private Spatial spatial;
    
    public Particle(ParticleEmitter particleEmitter,String modelPath,int state,ArrayList<Substance> substances){
        
        this.substances=substances;
        
        this.state=state;
        
        this.particleEmitter=particleEmitter;
        
        spatial=particleEmitter.getAssetManager().loadModel(modelPath);
        
        spatial.addControl(new ParticleControl(this));
        
    }
    
    public void destroy(){
        
        spatial.scale(0);
        
        spatial=null;
        
        particleEmitter.getControl().removeParticle(this);
        
    }
    
}
