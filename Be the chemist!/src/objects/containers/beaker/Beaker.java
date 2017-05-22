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
import com.jme3.math.ColorRGBA;
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
    private double maxPressureOpened;
    private double maxPressureClosed;
    private Spatial highlightModel;
    private Material highlightModelMat;
    private Spatial liquidModel;
    private Material liquidModelMat;
    private Spatial solidModel;
    private Material solidModelMat;
    
    private ParticleEmitter pourParticleEmitter,evaporationParticleEmitter,reactionParticleEmitter;
    
    private boolean emitting;
    
    private CylinderCollisionShape collisionShape;
    
    private Vector3f presentPosition;
    private Quaternion presentRotation;
    
    private Node node;
    
    private Main main;
    
    public Beaker(Main main,Vector3f position){
        
        super(main,position);
        
        this.main=main;
        
        setSolution(new Solution(main,this,null,0,0));
        
        init(main,position,main.getRootNode(),main.getAssetManager(),main.getBulletAppState());
        
    }
    
    public Beaker(Main main,Vector3f position,Solution solution){
        
        super(main,position,solution);
        
        this.main=main;
        
        init(main,position,main.getRootNode(),main.getAssetManager(),main.getBulletAppState());
        
    }
    
    private void init(Main main,Vector3f position,Node rootNode,AssetManager assetManager,BulletAppState bulletAppState){
        
        node=new Node();
        
        presentPosition=position;
        
        closeable=false;
        maxQuantity=1;
        maxTemperature=1773;
        maxPressureOpened=6;
        maxPressureClosed=3;
        
        spatial=assetManager.loadModel("Models/Objects/Containers/Beaker/Beaker.j3o");
        highlightModel=assetManager.loadModel("Models/Objects/Containers/Beaker/Highlight/Beaker_Highlight.j3o");
        liquidModel=assetManager.loadModel("Models/Objects/Containers/Beaker/Liquid/Beaker_Liquid.j3o");
        solidModel=assetManager.loadModel("Models/Objects/Containers/Beaker/Solid/Beaker_Solid.j3o");
        //openningSurface=assetManager.loadModel("Models/Objects/Containers/OpenningSurface/OpenningSurface.j3o");
        
        this.beakerControl=new BeakerControl(this);
        spatial.addControl(beakerControl);
        
        /*
        openningSurface.setName("Beaker #"+index+"'s openning surface");
        openningSurfaceMat=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        openningSurfaceMat.setColor("Color",ColorRGBA.Blue);
        openningSurfaceMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        openningSurface.setQueueBucket(RenderQueue.Bucket.Translucent);
        openningSurface.setMaterial(openningSurfaceMat);
        spatial.setUserData("correspondingObject", this);
        node.attachChild(openningSurface);
        openningSurface.setLocalTranslation(position.add(0,-0.02f,0));
        openningSurface.scale(4);
        */
        
        highlightModel.setName("Beaker #"+index+"'s highlight");
        highlightModelMat=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        highlightModelMat.setColor("Color",Main.HIGHLIGHT_VISIBLE);
        highlightModelMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        highlightModel.setQueueBucket(RenderQueue.Bucket.Translucent);
        highlightModel.setMaterial(highlightModelMat);
        node.attachChild(highlightModel);
        highlightModel.setLocalTranslation(0,-50,0);
        
        liquidModelMat=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        liquidModelMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        liquidModelMat.setColor("Color",ColorRGBA.White);
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
        spatial.setUserData("correctCollision", true);
        spatial.setUserData("correspondingObject", this);
        spatial.setQueueBucket(RenderQueue.Bucket.Transparent);
        collisionShape=new CylinderCollisionShape(new Vector3f(0.05f,0.06f,0),1);
        
        beaker_phy=new RigidBodyControl(collisionShape,1f);
        beaker_phy.setFriction(1f);
        beaker_phy.setDamping(0.75f, 0.75f);
        beaker_phy.setSleepingThresholds(30f,60f);
        spatial.addControl(beaker_phy);
        bulletAppState.getPhysicsSpace().add(beaker_phy);
        
        rootNode.attachChild(node);
        rootNode.attachChild(spatial);
        
        main.getItemsList().add(this);
        main.getHeatables().add(this);
        main.getBeakers().add(this);
        
        pourParticleEmitter=new ParticleEmitter(main,this,new Vector3f(0.05f,0.06f,0),Vector3f.ZERO,new Quaternion().fromAngleAxis(FastMath.DEG_TO_RAD*1,Vector3f.UNIT_Z),0,0,new Vector3f(0.01f,0.0075f,0),new Vector3f(0,0,0),0.05,0,new Vector3f(0,-9.806f,0),Vector3f.ZERO,"Beaker's pourParticleEmitter");
        evaporationParticleEmitter=new ParticleEmitter(main,this,new Vector3f(0,0.06f,0),Vector3f.ZERO,Quaternion.ZERO,0.05,0.01,new Vector3f(0,0.02f,0),new Vector3f(0,0,0),0.1,0.09,new Vector3f(0,0.05f,0),Vector3f.ZERO,"Beaker's evaporationParticleEmitter");
        
        evaporationParticleEmitter.setVolume(0.001);
        
        setPos(position);
        
        main.getParticleReceivers().add(this);
        
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
        
        return maxPressureOpened;
        
    }
    
    public double getMaxPressureClosed(){
        
        return maxPressureClosed;
        
    }
    
    public void startPouring(){
        
        pourParticleEmitter.begin();
        
        emitting=true;
        
    }
    
    public void stopPouring(){
        
        pourParticleEmitter.stop();
        emitting=false;
        
    }
    
    public void startEvaporating(){
        
        evaporationParticleEmitter.begin();
        emitting=true;
        
    }
    
    public void stopEvaporating(){
        
        evaporationParticleEmitter.stop();
        emitting=false;
        
    }
    
    public void startReacting(){
        
        reactionParticleEmitter.begin();
        emitting=true;
        
    }
    
    public void stopReacting(){
        
        reactionParticleEmitter.stop();
        emitting=false;
        
    }
    
    public boolean isEmitting(){
        
        return emitting;
        
    }
    
    public ParticleEmitter getPourParticleEmitter(){
        
        return pourParticleEmitter;
        
    }
    
    public ParticleEmitter getEvaporationParticleEmitter(){
        
        return evaporationParticleEmitter;
        
    }
    
    @Override
    public String getDescription() {
        
        //System.out.println("getDescription has been called on a beaker.");
        
        if(getSolution().getVolume()<0.001){
            
            return "Beaker:\n   Empty.";
            
        }else{
        
            return "Beaker:\n   Contains:\n   "+getSolution()+"\n  Total volume: "+getFormattedVolume()+" L;\n  Average temperature: "+getFormattedTemperature()+" K.";
            
        }
        
    }

    @Override
    public Vector3f getGrabbablePosition() {
        
        return node.getLocalTranslation();
        
        //return spatial.getControl(RigidBodyControl.class).getPhysicsLocation();
        
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
        
        spatial.getControl(RigidBodyControl.class).setPhysicsLocation(position);
        node.setLocalTranslation(position);
        
        //System.out.println("Beaker position set to "+position);
        
        presentPosition=position;
        
    }
    
    @Override
    public Vector3f getPosition(){
        
        return spatial.getControl(RigidBodyControl.class).getPhysicsLocation();
        
    }
    
    public void setRotation(Quaternion rotation){
        
        spatial.getControl(RigidBodyControl.class).setPhysicsRotation(rotation);
        node.setLocalRotation(rotation);
        
        //System.out.println("Beaker rotation set to "+rotation);
        
    }
    
    public void updateNodeState(){
        
        node.setLocalTranslation(spatial.getControl(RigidBodyControl.class).getPhysicsLocation());
            
        node.setLocalRotation(spatial.getControl(RigidBodyControl.class).getPhysicsRotation());
        
    }
    
    public Spatial getBeaker(){
        
        return spatial;
        
    }
    
    public void clearVelocity(){
        
        spatial.getControl(RigidBodyControl.class).setLinearVelocity(Vector3f.ZERO);
        
    }
    
    public void setVelocity(Vector3f velocity){
        
        spatial.getControl(RigidBodyControl.class).setLinearVelocity(velocity);
        
    }
    
    public void setLiquidVisible(boolean liquidVisible,ColorRGBA color){
        
        if(liquidVisible){
            
            liquidModelMat.setColor("Color",color);
            liquidModel.setLocalTranslation(Vector3f.ZERO);
            
        }else{
            
            liquidModel.setLocalTranslation(0,-50,0);
            
        }
        
    }
    
    public void setSolidVisible(boolean solidVisible,ColorRGBA color){
        
        if(solidVisible){
            
            solidModelMat.setColor("Color",color);
            solidModel.setLocalTranslation(Vector3f.ZERO);
            
        }else{
            
            solidModel.setLocalTranslation(0,-700,0);
            
        }
        
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
        
        return "Beaker";
        
    }
    
    @Override
    public Spatial getSpatial(){
        
        return spatial;
        
    }
    
    @Override
    public void destroy() {
        
        main.getRootNode().detachChild(node);
        main.getRootNode().detachChild(spatial);
        
        main.getItemsList().remove(this);
        main.getHeatables().remove(this);
        main.getBeakers().remove(this);
        main.getParticleReceivers().remove(this);
        
        destroyed=true;
        
    }
    
}
