/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.containers.beaker;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CylinderCollisionShape;
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
    private Spatial liquidModel;
    private Material liquidModelMat;
    private Spatial solidModel;
    private Material solidModelMat;
    private ParticleEmitter particleEmitter;
    
    private boolean isEmitting;
    
    private Vector3f particleEmitterPosition;
    private Spatial openningSurface;
    private Material openningSurfaceMat;
    
    private CylinderCollisionShape collisionShape;
    
    private Vector3f presentPosition;
    private Quaternion presentRotation;
    
    private Node node;
    
    public Beaker(Main main,Vector3f position){
        
        super(main,position);
        
        init(main,position,main.getRootNode(),main.getAssetManager(),main.getBulletAppState());
        
    }
    
    public Beaker(Main main,Vector3f position,Solution solution,double quantity){
        
        super(main,position,solution,quantity);
        
        init(main,position,main.getRootNode(),main.getAssetManager(),main.getBulletAppState());
        
        liquidModelMat.setColor("Color",solution.getLiquidColor()); 
        
        solidModelMat.setColor("Color",solution.getSolidColor());
        
    }
    
    public void init(Main main,Vector3f position,Node rootNode,AssetManager assetManager,BulletAppState bulletAppState){
        
        node=new Node();
        
        presentPosition=position;
        
        closeable=false;
        maxQuantity=1;
        maxTemperature=1773;
        maxPressureOpenned=6;
        maxPressureClosed=3;
        
        spatial=assetManager.loadModel("Models/Objects/Containers/Beaker/Beaker.j3o");
        highlightModel=assetManager.loadModel("Models/Objects/Containers/Beaker/Highlight/Beaker_Highlight.j3o");
        liquidModel=assetManager.loadModel("Models/Objects/Containers/Beaker/Liquid/Beaker_Liquid.j3o");
        solidModel=assetManager.loadModel("Models/Objects/Containers/Beaker/Solid/Beaker_Solid.j3o");
        openningSurface=assetManager.loadModel("Models/Objects/Containers/OpenningSurface/OpenningSurface.j3o");
        
        this.beakerControl=new BeakerControl(this);
        spatial.addControl(beakerControl);
        
        openningSurface.setName("Beaker #"+index+"'s openning");
        openningSurfaceMat=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        openningSurfaceMat.setColor("Color",Main.HIGHLIGHT_INVISIBLE);
        openningSurfaceMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        openningSurface.setQueueBucket(RenderQueue.Bucket.Translucent);
        openningSurface.setMaterial(openningSurfaceMat);
        node.attachChild(openningSurface);
        openningSurface.setLocalTranslation(position.add(0,0.6f,0));
        openningSurface.scale(4);
        
        highlightModel.setName("Beaker #"+index+"'s highlight");
        highlightModelMat=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        highlightModelMat.setColor("Color",Main.HIGHLIGHT_VISIBLE);
        highlightModelMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        highlightModel.setQueueBucket(RenderQueue.Bucket.Transparent);
        highlightModel.setMaterial(highlightModelMat);
        node.attachChild(highlightModel);
        highlightModel.setLocalTranslation(0,-50,0);
        
        liquidModelMat=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        liquidModelMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        liquidModel.setMaterial(liquidModelMat);
        liquidModel.setName("Beaker #"+index+"'s liquid");
        liquidModel.setUserData("correctCollision", true);
        liquidModel.setUserData("correspondingObject", this);
        liquidModel.setQueueBucket(RenderQueue.Bucket.Transparent);
        node.attachChild(liquidModel);
        liquidModel.setLocalTranslation(0,-50,0);
        
        solidModelMat=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        solidModel.setMaterial(solidModelMat);
        solidModel.setName("Beaker #"+index+"'s solid");
        solidModel.setUserData("correctCollision", true);
        solidModel.setUserData("correspondingObject", this);
        node.attachChild(solidModel);
        solidModel.setLocalTranslation(0,-50,0);
        
        spatial.setName("Beaker #"+index++);
        spatial.setLocalTranslation(0,0.061f,0);
        spatial.setUserData("correctCollision", true);
        spatial.setUserData("correspondingObject", this);
        spatial.setQueueBucket(RenderQueue.Bucket.Transparent);
        collisionShape=new CylinderCollisionShape(new Vector3f(0.05f,0.06f,0),1);
        
        beaker_phy=new RigidBodyControl(collisionShape,1);
        spatial.addControl(beaker_phy);
        bulletAppState.getPhysicsSpace().add(beaker_phy);
        
        //beaker_phy.setCollisionShape(collisionShape); Seemingly unneeded (See test program)
        
        rootNode.attachChild(node);
        rootNode.attachChild(spatial);
        
        main.getItemsList().add(this);
        
        particleEmitterPosition=position.add(0.05f,0,0);
        
        particleEmitter=new ParticleEmitter(assetManager,this,particleEmitterPosition,spatial.getControl(RigidBodyControl.class).getPhysicsRotation().getRotationColumn(1),new Quaternion().fromAngleAxis((FastMath.PI*5)/180, Vector3f.UNIT_XYZ),0.005,0.005,new Vector3f(0,0,0),new Vector3f(0,0,0),0.3,0.002,new Vector3f(0,-9.806f,0),Vector3f.ZERO);
        
        setPosition(position);
        
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
    
    public void explode(){
        
        
        
    }
    
    public void meltDown(){
        
        
        
    }
    
    public void breakContainer(){
        
        
        
    }
    
    @Override
    public String getDescription() {
        
        return "Beaker:\n  Contains: "+this.getSolution()+"\n  Quantity: "+this.getQuantity();
        
    }

    @Override
    public Vector3f getGrabbablePosition() {
        
        return spatial.getControl(RigidBodyControl.class).getPhysicsLocation();
        
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
        
        spatial.getControl(RigidBodyControl.class).setPhysicsLocation(position);
        node.setLocalTranslation(position);
        
        System.out.println("Beaker position set to "+position);
        
        presentPosition=position;
        
    }
    
    public Vector3f getPosition(){
        
        return spatial.getControl(RigidBodyControl.class).getPhysicsLocation();
        
    }
    
    public void setRotation(Quaternion rotation){
        
        spatial.getControl(RigidBodyControl.class).setPhysicsRotation(rotation);
        node.setLocalRotation(rotation);
        
        System.out.println("Beaker rotation set to "+rotation);
        
    }
    
    public void updateNodeState(){
        
        node.setLocalTranslation(spatial.getControl(RigidBodyControl.class).getPhysicsLocation());
            
        node.setLocalRotation(spatial.getControl(RigidBodyControl.class).getPhysicsRotation());
        
    }
    
    public Spatial getBeaker(){
        
        return spatial;
        
    }
    
    public void setEnabled(boolean enabled){
            
        spatial.getControl(RigidBodyControl.class).setEnabled(enabled);
        
    }
    
    public void clearForces(){
        
        spatial.getControl(RigidBodyControl.class).clearForces();
        
    }
    
    public void clearVelocity(){
        
        spatial.getControl(RigidBodyControl.class).setLinearVelocity(Vector3f.ZERO);
        
    }
    
    public void setVelocity(Vector3f velocity){
        
        spatial.getControl(RigidBodyControl.class).setLinearVelocity(velocity);
        
    }
    
}
