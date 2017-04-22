/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.particleEmitter.particle;

import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import main.Main;
import objects.particleEmitter.ParticleEmitter;
import objects.solution.Solution;
import objects.substance.Substance;

/**
 *
 * @author VIPkiller17
 */
public class Particle {
    
    private ParticleEmitter particleEmitter;
    
    private ArrayList<Substance> substances;
    private double volume;
    private double temperature;
    
    private int state;
    
    private Spatial spatial;
    
    private Material spatialMat;
    
    private Main main;
    
    private Vector3f velocity;
    
    private boolean destroyed;
    
    public Particle(Main main,ParticleEmitter particleEmitter,String modelPath,int state,Substance substance,double volume,double temperature){
        
        this.main=main;
        
        velocity=new Vector3f(particleEmitter.getInitialVelocity());
        
        substances=new ArrayList<>();
        substances.add(substance);
        this.volume=volume;
        this.temperature=temperature;
        
        this.state=state;
        
        this.particleEmitter=particleEmitter;
        
        spatial=particleEmitter.getAssetManager().loadModel(modelPath);
        spatial.setName("particle");
        spatialMat=new Material(main.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        //spatialMat.setColor("Color",substance.getColor());
        spatialMat.setColor("Color",ColorRGBA.Red);
        spatialMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        spatial.setQueueBucket(RenderQueue.Bucket.Translucent);
        spatial.setMaterial(spatialMat);
        
        main.getRootNode().attachChild(spatial);
        
        spatial.setLocalTranslation(particleEmitter.getNode().getWorldTranslation());
        
        spatial.addControl(new ParticleControl(this));
        
    }
    
    public Particle(Main main,ParticleEmitter particleEmitter,String modelPath,int state,Solution solution){
        
        this.main=main;
        
        velocity=new Vector3f(particleEmitter.getInitialVelocity());
        
        System.out.println(solution.getSubstances().get(0).getName());
        
        this.substances=solution.getSubstances();
        volume=solution.getVolume();
        temperature=solution.getTemperature();
        
        this.state=state;
        
        this.particleEmitter=particleEmitter;
        
        spatial=particleEmitter.getAssetManager().loadModel(modelPath);
        spatial.setName("particle");
        System.out.println(main);
        spatialMat=new Material(main.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        //spatialMat.setColor("Color",solution.getStateColor(solution.getMostCommonState()));
        spatialMat.setColor("Color",ColorRGBA.Red);
        spatialMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        spatial.setQueueBucket(RenderQueue.Bucket.Translucent);
        spatial.setMaterial(spatialMat);
        
        main.getRootNode().attachChild(spatial);
        
        spatial.setLocalTranslation(particleEmitter.getNode().getWorldTranslation());
        
        spatial.addControl(new ParticleControl(this));
        
    }
    
    public void destroy(){
        
        spatial.scale(0);
        
        spatial=null;
        
        particleEmitter.getControl().removeParticle(this);
        
        destroyed=true;
        
    }
    
    public Spatial getSpatial(){
        
        return spatial;
        
    }
    
    public ParticleEmitter getParticleEmitter(){
        
        return particleEmitter;
        
    }
    
    public void setVelocity(Vector3f velocity){
        
        this.velocity=velocity;
        
    }
    
    public Vector3f getVelocity(){
        
        return velocity;
        
    }
    
    public boolean isDestroyed(){
        
        return destroyed;
        
    }
    
    public Main getMain(){
        
        return main;
        
    }
    
    public ArrayList<Substance> getSubstances(){
        
        return substances;
        
    }
    
    public double getVolume(){
        
        return volume;
        
    }
    
    public double getTemperature(){
        
        
        return temperature;
        
    }
    
}