/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.particleEmitter;

import com.jme3.asset.AssetManager;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import objects.PhysicalObject;
import objects.containers.Container;

/**
 *
 * @author VIPkiller17
 */
public class ParticleEmitter {
    
    private AssetManager assetManager;
    
    private PhysicalObject parentObject;
    
    private Vector3f position;
    private Vector3f outDirection;
    private Quaternion randomOutDirectionOffset;
    private double randomHorizontalOutputOffset;
    private double randomVerticalOutputOffset;
    private double initialSpeed;
    private double randomInitialSpeedOffset;
    private double delay;
    private double randomDelayOffset;
    private Vector3f acceleration;
    private Vector3f randomAccelerationOffset;
    private String gasParticleModelPath,liquidParticleModelPath,solidParticleModelPath;
    
    public boolean emitting;
    
    public Node node;
    
    public ParticleEmitter(AssetManager assetManager,PhysicalObject parentObject,Vector3f position,Vector3f outDirection,Quaternion randomOutDirectionOffset,double randomHorizontalOutputOffset,double randomVerticalOutputOffset,double initialSpeed,double randomInitialSpeedOffset,double delay,double randomDelayOffset,Vector3f acceleration,Vector3f randomAccelerationOffset){
        
        this.parentObject=parentObject;
        this.position=position;
        this.outDirection=outDirection;
        this.randomOutDirectionOffset=randomOutDirectionOffset;
        this.randomHorizontalOutputOffset=randomHorizontalOutputOffset;
        this.randomVerticalOutputOffset=randomVerticalOutputOffset;
        this.initialSpeed=initialSpeed;
        this.randomInitialSpeedOffset=randomInitialSpeedOffset;
        this.delay=delay;
        this.randomDelayOffset=randomDelayOffset;
        this.acceleration=acceleration;
        this.randomAccelerationOffset=randomAccelerationOffset;
        
        gasParticleModelPath="Models/Particles/Gas/Gas.j3o";
        liquidParticleModelPath="Models/Particles/Liquid/Liquid.j3o";
        solidParticleModelPath="Models/Particles/Solid/Solid.j3o";
        
        node.addControl(new ParticleEmitterControl(this));
        
        parentObject.attachObject(node);
        
    }
    
    public void setPosition(Vector3f position){
        
        this.position=position;
        
    }
    
    public Vector3f getPosition(){
        
        return position;
        
    }
    
    public void setOutDirection(Vector3f outDirection){
        
        this.outDirection=outDirection;
        
    }
    
    public Vector3f getOutDirection(){
        
        return outDirection;
        
    }
    
    public void setRandomOutDirectionOffset(Quaternion randomOutDirectionOffset){
        
        this.randomOutDirectionOffset=randomOutDirectionOffset;
        
    }
    
    public Quaternion getRandomOutDirectionOffset(){
        
        return randomOutDirectionOffset;
        
    }
    
    public void setRandomHorizontalOutputOffset(double randomHorizontalOutputOffset){
        
        this.randomHorizontalOutputOffset=randomHorizontalOutputOffset;
        
    }
    
    public double getRandomHorizontalOutputOffset(){
        
        return randomHorizontalOutputOffset;
        
    }
    
    public void setRandomVerticalOutputOffset(double randomVerticalOutputOffset){
        
        this.randomVerticalOutputOffset=randomVerticalOutputOffset;
        
    }
    
    public double getRandomVerticalutputOffset(){
        
        return randomVerticalOutputOffset;
        
    }
    
    public void setInitialSpeed(double initialSpeed){
        
        this.initialSpeed=initialSpeed;
        
    }
    
    public double getInitialSpeed(){
        
        return initialSpeed;
        
    }
    
    public void setRandomInitialSpeedOffset(double randomInitialSpeedOffset){
        
        this.randomInitialSpeedOffset=randomInitialSpeedOffset;
        
    }
    
    public double getRandomInitialSpeedOffset(){
        
        return randomInitialSpeedOffset;
        
    }
    
    public void setDelay(double delay){
        
        this.delay=delay;
        
    }
    
    public double getDelay(){
        
        return delay;
        
    }
    
    public void setRandomDelayoffset(double randomDelayOffset){
        
        this.randomDelayOffset=randomDelayOffset;
        
    }
    
    public double getRandomDelayOffset(){
        
        return randomDelayOffset;
        
    }
    
    public void setAcceleration(Vector3f acceleration){
        
        this.acceleration=acceleration;
        
    }
    
    public Vector3f getAcceleration(){
        
        return acceleration;
        
    }
    
    public void setrandomAccelerationOffset(Vector3f randomAccelerationOffset){
        
        this.randomAccelerationOffset=randomAccelerationOffset;
        
    }
    
    public Vector3f getRandomAccelerationOffset(){
        
        return randomAccelerationOffset;
        
    }
    
    public void setGasParticleModelPath(String gasParticleModelPath){
        
        this.gasParticleModelPath=gasParticleModelPath;
        
    }
    
    public String getGasParticleModelPath(){
        
        return gasParticleModelPath;
        
    }
    
    public void setLiquidParticleModelPath(String liquidParticleModelPath){
        
        this.liquidParticleModelPath=liquidParticleModelPath;
        
    }
    
    public String getLiquidParticleModelPath(){
        
        return liquidParticleModelPath;
        
    }
    
    public void setSolidParticleModelPath(String solidParticleModelPath){
        
        this.solidParticleModelPath=solidParticleModelPath;
        
    }
    
    public String getSolidParticleModelPath(){
        
        return solidParticleModelPath;
        
    }
    
    public void begin(){
        
        emitting=true;
        
    }
    
    public void stop(){
        
        emitting=false;
        
    }
    
    public PhysicalObject getParentObject(){
        
        return parentObject;
        
    }
    
    public Container ifParentObjectContainerGetIt(){
        
        if(parentObject instanceof Container)
            
            return ((Container)parentObject);
        
        else 
            
            return null;
        
    }
    
    public AssetManager getAssetManager(){
        
        return assetManager;
        
    }
    
    @Override
    public boolean equals(Object otherParticleEmitter){
        
        if(otherParticleEmitter instanceof ParticleEmitter)
            
            return parentObject==((ParticleEmitter)otherParticleEmitter).getParentObject();
            
        else
            
            return false;
        
    }
    
    @Override
    public String toString(){
        
        return "ParticleEmitter of object: "+parentObject.getDescription();
        
    }
    
}
