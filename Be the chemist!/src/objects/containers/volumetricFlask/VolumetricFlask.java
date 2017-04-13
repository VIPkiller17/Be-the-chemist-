/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.containers.volumetricFlask;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.export.Savable;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import objects.containers.Container;
import objects.particleEmitter.ParticleEmitter;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.renderer.queue.RenderQueue;
import main.Main;
import objects.solution.Solution;

/**
 *
 * @author VIPkiller17
 */
public class VolumetricFlask extends Container implements Savable{

    private static int index;
    
    private VolumetricFlaskControl control;
    private Spatial spatial;
    private RigidBodyControl volumetricFlask_phy;
    private Node node;
    private Main main;
    
    private boolean closeable;
    private boolean isEmitting;
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
    
    private Vector3f particleEmitterPosition;
    private Spatial openningSurface;
    private Material openningSurfaceMat;
    
    public VolumetricFlask(Main main,Vector3f position){
        
        super(main,position);
        
        init(main,position,main.getRootNode(),main.getAssetManager(),main.getBulletAppState());
        
    }
    
    public VolumetricFlask(Main main,Vector3f position,Solution solution,double quantity){
        
        super(main,position);
        
        init(main,position,main.getRootNode(),main.getAssetManager(),main.getBulletAppState());
        
        liquidModelMat.setColor("Color",solution.getLiquidColor());
        
        solidModelMat.setColor("Color",solution.getSolidColor());
        
    }
    
    public void init(Main main,Vector3f position,Node rootNode,AssetManager assetManager,BulletAppState bulletAppState){
        
        closeable=true;
        maxQuantity=1;
        maxTemperature=1773;
        maxPressureOpenned=4;
        maxPressureClosed=3;
        
        node=new Node();
        
        volumetricFlask_phy=new RigidBodyControl(1f);
        node.addControl(volumetricFlask_phy);
        bulletAppState.getPhysicsSpace().add(volumetricFlask_phy);
        
        spatial=assetManager.loadModel("Models/Objects/Containers/VolumetricFlask/VolumetricFlask.j3o");
        highlightModel=assetManager.loadModel("Models/Objects/Containers/VolumetricFlask/Highlight/VolumetricFlask_Highlight.j3o");
        stopperModel=assetManager.loadModel("Models/Objects/Containers/VolumetricFlask/Stopper/VolumetricFlask_Normal_Stopper.j3o");
        holedStopperModel=assetManager.loadModel("Models/Objects/Containers/VolumetricFlask/Stopper/VolumetricFlask_Holed_Stopper.j3o");
        liquidModel=assetManager.loadModel("Models/Objects/Containers/VolumetricFlask/Liquid/VolumetricFlask_Liquid.j3o");
        solidModel=assetManager.loadModel("Models/Objects/Containers/VolumetricFlask/Solid/VolumetricFlask_Solid.j3o");
        openningSurface=assetManager.loadModel("Models/Objects/Containers/OpenningSurface/OpenningSurface.j3o");
        
        openningSurface.setName("VolumetricFlask #"+index+"'s openning");
        openningSurfaceMat=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        openningSurfaceMat.setColor("Color",Main.HIGHLIGHT_INVISIBLE);
        openningSurfaceMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        openningSurface.setQueueBucket(RenderQueue.Bucket.Translucent);
        openningSurface.setMaterial(openningSurfaceMat);
        node.attachChild(openningSurface);
        openningSurface.setLocalTranslation(0,0.15f,0);
        
        highlightModel.setName("VolumetricFlask #"+index+"'s highlight");
        highlightModelMat=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        highlightModelMat.setColor("Color",Main.HIGHLIGHT_VISIBLE);
        highlightModelMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        highlightModel.setQueueBucket(RenderQueue.Bucket.Transparent);
        highlightModel.setMaterial(highlightModelMat);
        node.attachChild(highlightModel);
        highlightModel.setLocalTranslation(0,-50,0);
        
        stopperModel.setName("VolumetricFlask #"+index+"'s stopper");
        stopperModelMat=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        stopperModelMat.setColor("Color",Main.HIGHLIGHT_VISIBLE);
        stopperModelMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        stopperModel.setQueueBucket(RenderQueue.Bucket.Transparent);
        stopperModel.setMaterial(stopperModelMat);
        node.attachChild(stopperModel);
        stopperModel.setLocalTranslation(0,-50,0);
        
        holedStopperModel.setName("VolumetricFlask #"+index+"'s holed stopper");
        holedStopperModelMat=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        holedStopperModelMat.setColor("Color",Main.HIGHLIGHT_VISIBLE);
        holedStopperModelMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        holedStopperModel.setQueueBucket(RenderQueue.Bucket.Transparent);
        holedStopperModel.setMaterial(holedStopperModelMat);
        node.attachChild(holedStopperModel);
        holedStopperModel.setLocalTranslation(0,-50,0);
        
        liquidModelMat=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        liquidModelMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        liquidModel.setMaterial(liquidModelMat);
        liquidModel.setName("VolumetricFlask #"+index+"'s liquid");
        liquidModel.setUserData("correctCollision", true);
        liquidModel.setUserData("correspondingObject", this);
        liquidModel.setQueueBucket(RenderQueue.Bucket.Transparent);
        node.attachChild(liquidModel);
        
        solidModelMat=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        solidModel.setMaterial(solidModelMat);
        solidModel.setName("VolumetricFlask #"+index+"'s solid");
        solidModel.setUserData("correctCollision", true);
        solidModel.setUserData("correspondingObject", this);
        node.attachChild(solidModel);
        
        spatial.setName("VolumetricFlask #"+index++);
        spatial.setUserData("correctCollision", true);
        spatial.setUserData("correspondingObject", this);
        spatial.setQueueBucket(RenderQueue.Bucket.Transparent);
        node.attachChild(spatial);
        
        rootNode.attachChild(node);
        
        node.getControl(RigidBodyControl.class).setPhysicsLocation(position);

        main.getItemsList().add(this);

        particleEmitterPosition = new Vector3f(0, 0.15f, 0);
        
        particleEmitter=new ParticleEmitter(main,this,particleEmitterPosition,spatial.getLocalRotation().getRotationColumn(1),new Quaternion().fromAngleAxis((FastMath.PI*5)/180, Vector3f.UNIT_XYZ),0.005,0.005,new Vector3f(0,0,0),new Vector3f(0,0,0),0.3,0.002,new Vector3f(0,-9.806f,0),Vector3f.ZERO);
        
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
        
        return "Volumetric lask:\n  Contains: "+this.getSolution()+"\n  Quantity: "+this.getVolume();
        
    }

    @Override
    public Vector3f getGrabbablePosition() {
        
        return node.getLocalTranslation();
        
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
    public void setPos(Vector3f position){
        
        node.getControl(RigidBodyControl.class).setPhysicsLocation(position);
        
    }
    
    @Override
    public boolean canContain(int state){
        
        switch(state){
            
            case 0:
                
                return true;
                
            case 1:
                
                return true;
                
            case 2:
                
                return true;
                
            default:
                
                System.out.println("Invalid state passed to canContain()");
                return false;
            
        }
        
    }
    
    @Override
    public Node getNode() {
        
        return node;
        
    }
    
    @Override
    public String getName() {
        
        return "Volumetric flask";
        
    }
    
}
