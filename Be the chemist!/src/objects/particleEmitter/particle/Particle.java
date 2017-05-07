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
    
    private ColorRGBA presentColor;
    
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
        
        spatial.setLocalTranslation(particleEmitter.getNode().getWorldTranslation().add(((float)((Math.random()*(particleEmitter.getRandomHorizontalOutputOffset()*2))-particleEmitter.getRandomHorizontalOutputOffset())),((float)((Math.random()*(particleEmitter.getRandomHorizontalOutputOffset()*2))-particleEmitter.getRandomVerticalOutputOffset())),((float)((Math.random()*(particleEmitter.getRandomHorizontalOutputOffset()*2))-particleEmitter.getRandomHorizontalOutputOffset()))));
        
        spatial.addControl(new ParticleControl(this));
        
    }
    
    public Particle(Main main,ParticleEmitter particleEmitter,String modelPath,int state,Solution solution,double volume){
        
        this.main=main;
        
        velocity=new Vector3f(particleEmitter.getInitialVelocity());
        
        this.substances=solution.getSubstances();
        this.volume=volume;
        temperature=solution.getTemperature();
        
        this.state=state;
        
        this.particleEmitter=particleEmitter;
        
        spatial=particleEmitter.getAssetManager().loadModel(modelPath);
        spatial.setName("particle");
        spatialMat=new Material(main.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        
        //System.out.println(solution.getMostCommonState()+", "+solution.getStateColor(solution.getMostCommonState()));
        
        spatialMat.setColor("Color",solution.getStateColor(solution.getMostCommonState()));
        spatialMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        spatial.setQueueBucket(RenderQueue.Bucket.Translucent);
        spatial.setMaterial(spatialMat);
        
        main.getRootNode().attachChild(spatial);
        
        spatial.setLocalTranslation(particleEmitter.getNode().getWorldTranslation().add(((float)((Math.random()*(particleEmitter.getRandomHorizontalOutputOffset()*2))-particleEmitter.getRandomHorizontalOutputOffset())),((float)((Math.random()*(particleEmitter.getRandomHorizontalOutputOffset()*2))-particleEmitter.getRandomVerticalOutputOffset())),((float)((Math.random()*(particleEmitter.getRandomHorizontalOutputOffset()*2))-particleEmitter.getRandomHorizontalOutputOffset()))));
        
        spatial.addControl(new ParticleControl(this));
        
    }
    
    public Particle(Main main,ParticleEmitter particleEmitter,String modelPath,int state,ArrayList<Substance> substances,double volume,double temperature){
        
        this.main=main;
        
        velocity=new Vector3f(particleEmitter.getInitialVelocity());
        
        this.substances=substances;
        this.volume=volume;
        this.temperature=temperature;
        
        this.state=state;
        
        this.particleEmitter=particleEmitter;
        
        spatial=particleEmitter.getAssetManager().loadModel(modelPath);
        spatial.setName("particle");
        spatialMat=new Material(main.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        
        presentColor=substances.get(0).getColor();
        
        for(int i=1;i<substances.size();i++){
                
            presentColor.set((float)Math.sqrt(((presentColor.getRed()*presentColor.getRed())+(substances.get(i).getColor().getRed()*substances.get(i).getColor().getRed()))/2),(float)Math.sqrt(((presentColor.getGreen()*presentColor.getGreen())+(substances.get(i).getColor().getGreen()*substances.get(i).getColor().getGreen()))/2),(float)Math.sqrt(((presentColor.getBlue()*presentColor.getBlue())+(substances.get(i).getColor().getBlue()*substances.get(i).getColor().getBlue()))/2),(presentColor.getAlpha()+substances.get(i).getColor().getAlpha())/2);
            
        }
        
        spatialMat.setColor("Color",presentColor);
        //spatialMat.setColor("Color",ColorRGBA.Red);
        spatialMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        spatial.setQueueBucket(RenderQueue.Bucket.Translucent);
        spatial.setMaterial(spatialMat);
        
        main.getRootNode().attachChild(spatial);
        
        spatial.setLocalTranslation(particleEmitter.getNode().getWorldTranslation().add(((float)((Math.random()*(particleEmitter.getRandomHorizontalOutputOffset()*2))-particleEmitter.getRandomHorizontalOutputOffset())),((float)((Math.random()*(particleEmitter.getRandomHorizontalOutputOffset()*2))-particleEmitter.getRandomVerticalOutputOffset())),((float)((Math.random()*(particleEmitter.getRandomHorizontalOutputOffset()*2))-particleEmitter.getRandomHorizontalOutputOffset()))));
        
        spatial.addControl(new ParticleControl(this));
        
    }
    
    public void destroy(){
        
        spatial.scale(0);
        
        spatial.setLocalTranslation(0,-30,0);
        
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
    
    public int getState(){
        
        return state;
        
    }
    
}