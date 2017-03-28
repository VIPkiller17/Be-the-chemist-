/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.containers.gasSac;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.export.Savable;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import objects.containers.Container;
import objects.particleEmitter.ParticleEmitter;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import main.Main;
import objects.containers.gasSac.GasSacControl;
import objects.solution.Solution;

/**
 *
 * @author VIPkiller17
 */
public class GasSac extends Container implements Savable{

    private static int index;
    
    private GasSacControl gasSacControl;
    private Spatial spatial;
    private RigidBodyControl gasSac_phy;
    
    private boolean closeable;
    private double maxQuantity;
    private double maxTemperature;
    private double maxPressureOpenned;
    private double maxPressureClosed;
    private Spatial highlightModel;
    private Material highlightModelMat;
    private Spatial gasModel;
    private Material gasModelMat;
    private ParticleEmitter particleEmitter;
    
    private boolean isEmitting;
    
    private Vector3f particleEmitterPosition;
    private Spatial openningSurface;
    private Material openningSurfaceMat;
    
    private GasSacValve gasSacValve;
    
    private Node node;
    
    public GasSac(Main main,Vector3f position){
        
        super(main,position);
        
        init(main,position,main.getRootNode(),main.getAssetManager(),main.getBulletAppState());
        
    }
    
    public GasSac(Main main,Vector3f position,Solution solution,double quantity){
        
        super(main,position,solution,quantity);
        
        init(main,position,main.getRootNode(),main.getAssetManager(),main.getBulletAppState());
        
        gasModelMat.setColor("Color",solution.getGasColor());
        
    }
    
    public void init(Main main,Vector3f position,Node rootNode,AssetManager assetManager,BulletAppState bulletAppState){
        
        node=new Node();
        
        gasSacValve=new GasSacValve(main,this);
        
        closeable=true;
        maxQuantity=1;
        maxTemperature=350;
        maxPressureOpenned=2.5;
        maxPressureClosed=2;
        
        gasSac_phy=new RigidBodyControl(1f);
        node.addControl(gasSac_phy);
        bulletAppState.getPhysicsSpace().add(gasSac_phy);
        
        spatial=assetManager.loadModel("Models/Objects/Containers/GasSac/GasSac.j3o");
        highlightModel=assetManager.loadModel("Models/Objects/Containers/GasSac/Highlight/GasSac_Highlight.j3o");
        gasModel=assetManager.loadModel("Models/Objects/Containers/GasSac/Gas/GasSac_Gas.j3o");
        openningSurface=assetManager.loadModel("Models/Objects/Containers/OpenningSurface/OpenningSurface.j3o");
        
        this.gasSacControl=new GasSacControl(this);
        spatial.addControl(gasSacControl);
        
        openningSurface.setName("GasSac #"+index+"'s openning");
        openningSurfaceMat=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        openningSurfaceMat.setColor("Color",Main.HIGHLIGHT_INVISIBLE);
        openningSurfaceMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        openningSurface.setQueueBucket(RenderQueue.Bucket.Translucent);
        openningSurface.setMaterial(openningSurfaceMat);
        node.attachChild(openningSurface);
        openningSurface.setLocalTranslation(0,0,0);
        openningSurface.scale(0);
        
        highlightModel.setName("GasSac #"+index+"'s highlight");
        highlightModelMat=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        highlightModelMat.setColor("Color",Main.HIGHLIGHT_VISIBLE);
        highlightModelMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        highlightModel.setQueueBucket(RenderQueue.Bucket.Transparent);
        highlightModel.setMaterial(highlightModelMat);
        node.attachChild(highlightModel);
        highlightModel.setLocalTranslation(0,-50,0);
        
        gasModelMat=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        gasModelMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        gasModel.setMaterial(gasModelMat);
        gasModel.setName("GasSac #"+index+"'s liquid");
        gasModel.setUserData("correctCollision", true);
        gasModel.setUserData("correspondingObject", this);
        gasModel.setQueueBucket(RenderQueue.Bucket.Transparent);
        node.attachChild(gasModel);
        
        spatial.setName("GasSac #"+index++);
        spatial.setUserData("correctCollision", true);
        spatial.setUserData("correspondingObject", this);
        spatial.setQueueBucket(RenderQueue.Bucket.Transparent);
        node.attachChild(spatial);
        
        rootNode.attachChild(node);
        
        node.setLocalTranslation(position);
        
        main.getItemsList().add(this);
        
        particleEmitterPosition=new Vector3f(-0.02f,0.22f,0);
        
        particleEmitter=new ParticleEmitter(assetManager,this,particleEmitterPosition,spatial.getLocalRotation().getRotationColumn(1),new Quaternion().fromAngleAxis((FastMath.PI*5)/180, Vector3f.UNIT_XYZ),0.005,0.005,new Vector3f(0,0,0),new Vector3f(0,0,0),0.3,0.002,new Vector3f(0,-9.806f,0),Vector3f.ZERO);
        
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
    
    public ParticleEmitter getParticleEmitter(){
        
        return particleEmitter;
        
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
    
    public void explode(){
        
        
        
    }
    
    public void meltDown(){
        
        
        
    }
    
    public void breakContainer(){
        
        
        
    }
    
    @Override
    public String getDescription() {
        
        return "Gas sac:\n  Contains: "+this.getSolution()+"\n  Quantity: "+this.getQuantity();
        
    }

    @Override
    public Vector3f getGrabbablePosition() {
        
        return node.getWorldTranslation();
        
    }

    @Override
    public void highlightVisible(boolean highlightVisible) {
        
        this.highlightVisible=highlightVisible;
        
        if(highlightVisible)
            
            highlightModel.setLocalTranslation(0,0,0);
        
        else
            
            highlightModel.setLocalTranslation(0,-50,0);
        
    }
    
    @Override
    public void setPosition(Vector3f position){
        
        node.setLocalTranslation(position);
        
    }
    
}
