/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.containers.beaker;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.export.Savable;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import objects.containers.Container;
import objects.particleEmitter.ParticleEmitter;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import objects.solution.Solution;

/**
 *
 * @author VIPkiller17
 */
public class Beaker extends Container implements Savable{

    private static int index;
    
    private BeakerControl beakerControl;
    private Spatial spatial;
    private RigidBodyControl beaker_phy;
    
    private boolean closeable;
    private double maxQuantity;
    private double maxTemperature;
    private double maxPressureOpenned;
    private double maxPressureClosed;
    private Spatial highlightModel;
    private Material highlightModelMat;
    private Spatial stopperModel;
    private Material stopperModelMat;
    private Spatial holedStopperModel;
    private Material holedStopperModelMat;
    private Spatial liquidModel;
    private Material liquidModelMat;
    private Spatial solidModel;
    private Material solidModelMat;
    private ParticleEmitter particleEmitter;
    
    private boolean isEmitting;
    
    public Beaker(Vector3f position,Node rootNode,AssetManager assetManager,BulletAppState bulletAppState){
        
        super(position);
        
        init(position,rootNode,assetManager,bulletAppState);
        
    }
    
    public Beaker(Vector3f position,Node rootNode,AssetManager assetManager,BulletAppState bulletAppState,Solution solution,double quantity){
        
        super(position,solution,quantity);
        
        init(position,rootNode,assetManager,bulletAppState);
        
        liquidModelMat.setColor("Ambient",solution.getLiquidColor()); 
        liquidModelMat.setColor("Diffuse",solution.getLiquidColor());
        
        solidModelMat.setColor("Ambient",solution.getSolidColor()); 
        solidModelMat.setColor("Diffuse",solution.getSolidColor());
        
    }
    
    public void init(Vector3f position,Node rootNode,AssetManager assetManager,BulletAppState bulletAppState){
        
        closeable=true;
        maxQuantity=1;
        maxTemperature=1773;
        maxPressureOpenned=6;
        maxPressureClosed=3;
        
        addPhysicsControl(beaker_phy);
        beaker_phy=new RigidBodyControl(1f);
        bulletAppState.getPhysicsSpace().add(beaker_phy);
        
        spatial=assetManager.loadModel("Models/Objects/Containers/Beaker/Beaker.j3o");
        highlightModel=assetManager.loadModel("Models/Objects/Containers/Beaker/Highlight/Beaker_Highlight.j3o");
        liquidModel=assetManager.loadModel("Models/Objects/Containers/Beaker/Liquid/Beaker_Liquid.j3o");
        solidModel=assetManager.loadModel("Models/Objects/Containers/Beaker/Solid/Beaker_Solid.j3o");
        
        this.beakerControl=new BeakerControl(this);
        spatial.addControl(beakerControl);
        
        highlightModelMat=new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        highlightModelMat.setBoolean("UseMaterialColors", true); 
        highlightModelMat.setColor("Ambient",new ColorRGBA(0,0,0,0)); 
        highlightModelMat.setColor("Diffuse",new ColorRGBA(0,0,0,0));
        highlightModel.setMaterial(highlightModelMat);
        highlightModel.setLocalTranslation(position);
        highlightModel.setName("Beaker #"+index+"'s highlight");
        highlightModel.setUserData("correctCollision", true);
        highlightModel.setUserData("correspondingObject", this);
        attachObject(highlightModel);
        
        liquidModelMat=new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        liquidModelMat.setBoolean("UseMaterialColors", true); 
        liquidModelMat.setColor("Ambient",new ColorRGBA(0,0,0,0)); 
        liquidModelMat.setColor("Diffuse",new ColorRGBA(0,0,0,0));
        liquidModel.setMaterial(liquidModelMat);
        liquidModel.setLocalTranslation(position);
        liquidModel.setName("Beaker #"+index+"'s liquid");
        liquidModel.setUserData("correctCollision", true);
        liquidModel.setUserData("correspondingObject", this);
        attachObject(liquidModel);
        
        solidModelMat=new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        solidModelMat.setBoolean("UseMaterialColors", true); 
        solidModelMat.setColor("Ambient",new ColorRGBA(0,0,0,0)); 
        solidModelMat.setColor("Diffuse",new ColorRGBA(0,0,0,0));
        solidModel.setMaterial(solidModelMat);
        solidModel.setLocalTranslation(position);
        solidModel.setName("Beaker #"+index+"'s solid");
        solidModel.setUserData("correctCollision", true);
        solidModel.setUserData("correspondingObject", this);
        attachObject(solidModel);
        
        spatial.scale(1f,1f,1f);
        spatial.rotate(0.0f, 0.0f, 0.0f);
        spatial.setLocalTranslation(position);
        spatial.setName("Beaker #"+index++);
        spatial.setUserData("correctCollision", true);
        spatial.setUserData("correspondingObject", this);
        attachObject(spatial);
        
        rootNode.attachChild(getNode());
        
        particleEmitter=new ParticleEmitter(this,getPos(),spatial.getLocalRotation().getRotationColumn(1),new Quaternion().fromAngleAxis((FastMath.PI*5)/180, Vector3f.UNIT_XYZ),0.005,0.005,0.1,0.005,0.3,0.002,new Vector3f(0,-9.806f,0),Vector3f.ZERO);
        
    }
    
    public boolean isCloseable(){
        
        return closeable;
        
    }
    
    public double getMaxQuantity(){
        
        return maxQuantity;
        
    }
    
    public double getMaxTemperature(){
        
        return maxTemperature;
        
    }
    
    public double getMaxPressureOpenned(){
        
        return maxPressureOpenned;
        
    }
    
    public double getMaxPressureClosed(){
        
        return maxPressureClosed;
        
    }
    
    public void startParticleEmission(){
        
        particleEmitter.begin();
        isEmitting=true;
        
    }
    
    public void stopParticleEmission(){
        
        particleEmitter.stop();
        isEmitting=false;
        
    }
    
    public boolean isEmitting(){
        
        return isEmitting;
        
    }
    
    @Override
    public String getDescription() {
        
        return "Beaker:\n  Contains: "+this.getSolution()+"\n  Quantity: "+this.getQuantity();
        
    }
    
}
