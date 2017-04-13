/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.containers.gasSac;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
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
public class GasSac extends Container implements Savable{

    private static int index;
    
    private Main main;
    
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
    private Spatial valveClosed,valveOpenned;
    private ParticleEmitter particleEmitter;
    
    private boolean isEmitting;
    private boolean openned;
    
    private Vector3f particleEmitterPosition;
    private Spatial openningSurface;
    private Material openningSurfaceMat;
    
    private BoxCollisionShape collisionShape;
    
    private Node node;
    
    private Vector3f presentPosition;
    private Quaternion presentRotation;
    
    public GasSac(Main main,Vector3f position){
        
        super(main,position);
        
        init(main,position,main.getRootNode(),main.getAssetManager(),main.getBulletAppState());
        
    }
    
    public GasSac(Main main,Vector3f position,Solution solution){
        
        super(main,position,solution);
        
        init(main,position,main.getRootNode(),main.getAssetManager(),main.getBulletAppState());
        
        //gasModelMat.setColor("Color",solution.getGasColor());
        
    }
    
    public void init(Main main,Vector3f position,Node rootNode,AssetManager assetManager,BulletAppState bulletAppState){
        
        this.main=main;
        
        node=new Node();
        
        closeable=true;
        maxQuantity=1;
        maxTemperature=350;
        maxPressureOpenned=2.5;
        maxPressureClosed=2;
        
        spatial=assetManager.loadModel("Models/Objects/Containers/GasSac/GasSac.j3o");
        highlightModel=assetManager.loadModel("Models/Objects/Containers/GasSac/Highlight/GasSac_Highlight.j3o");
        gasModel=assetManager.loadModel("Models/Objects/Containers/GasSac/Gas/GasSac_Gas.j3o");
        openningSurface=assetManager.loadModel("Models/Objects/Containers/OpenningSurface/OpenningSurface.j3o");
        valveClosed=main.getAssetManager().loadModel("Models/Objects/Containers/GasSac/Valve/GasSac_Valve_Closed.j3o");
        valveOpenned=main.getAssetManager().loadModel("Models/Objects/Containers/GasSac/Valve/GasSac_Valve_Openned.j3o");
        
        this.gasSacControl=new GasSacControl(this);
        spatial.addControl(gasSacControl);
        
        openningSurface.setName("GasSac #"+index+"'s openning");
        openningSurfaceMat=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        openningSurfaceMat.setColor("Color",Main.HIGHLIGHT_INVISIBLE);
        openningSurfaceMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        openningSurface.setQueueBucket(RenderQueue.Bucket.Translucent);
        openningSurface.setMaterial(openningSurfaceMat);
        node.attachChild(openningSurface);
        openningSurface.setLocalTranslation(-0.02f,0.13f,0);
        openningSurface.scale(0.002f);
        
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
        gasModel.setLocalTranslation(0,-50,0);
        
        valveClosed.setName("Gas sac closed valve");
        valveClosed.setUserData("correctCollision", true);
        valveClosed.setUserData("correspondingObject", this);
        node.attachChild(valveClosed);
        
        valveOpenned.setName("Gas sac openned valve");
        valveOpenned.setUserData("correctCollision", true);
        valveOpenned.setUserData("correspondingObject", this);
        node.attachChild(valveOpenned);
        valveOpenned.setLocalTranslation(new Vector3f(0,-50,0));
        
        spatial.setName("GasSac #"+index++);
        spatial.setUserData("correctCollision", true);
        spatial.setUserData("correspondingObject", this);
        spatial.setQueueBucket(RenderQueue.Bucket.Transparent);
        collisionShape=new BoxCollisionShape(new Vector3f(0.18f,0.26f,0.03f));
        
        gasSac_phy=new RigidBodyControl(collisionShape,1f);
        spatial.addControl(gasSac_phy);
        bulletAppState.getPhysicsSpace().add(gasSac_phy);
        
        rootNode.attachChild(node);
        rootNode.attachChild(spatial);
        
        main.getItemsList().add(this);
        
        particleEmitterPosition=new Vector3f(-0.03f,0.17f,0);
        
        particleEmitter=new ParticleEmitter(main,this,particleEmitterPosition,spatial.getLocalRotation().getRotationColumn(1),new Quaternion().fromAngleAxis((FastMath.PI*5)/180, Vector3f.UNIT_XYZ),0.005,0.005,new Vector3f(0,0,0),new Vector3f(0,0,0),0.3,0.002,new Vector3f(0,-9.806f,0),Vector3f.ZERO);
        
        setPos(position);
        
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
        
        return "Gas sac:\n  Contains: "+this.getSolution()+"\n  Quantity: "+this.getVolume();
        
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
    public void setPos(Vector3f position){
        
        spatial.getControl(RigidBodyControl.class).setPhysicsLocation(position);
        node.setLocalTranslation(position);
        
        System.out.println("Gas sac position set to "+position);
        
        presentPosition=position;
        
    }
    
    public Vector3f getPosition(){
        
        return spatial.getControl(RigidBodyControl.class).getPhysicsLocation();
        
    }
    
    public void setRotation(Quaternion rotation){
        
        spatial.getControl(RigidBodyControl.class).setPhysicsRotation(rotation);
        node.setLocalRotation(rotation);
        
        System.out.println("Gas sac rotation set to "+rotation);
        
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
    
    @Override
    public Node getNode(){
        
        return node;
        
    }
    
    @Override
    public boolean canContain(int state){
        
        switch(state){
            
            case 0:
                
                return true;
                
            case 1:
                
                return false;
                
            case 2:
                
                return false;
                
            default:
                
                System.out.println("Invalid state passed to canContain()");
                return false;
            
        }
        
    }
    public void toggle(){
        
        if(openned){
            
            valveOpenned.setLocalTranslation(0,-50,0);
            
            valveClosed.setLocalTranslation(0,0,0);
            
            openned=false;
            
            particleEmitter.stop();
            
        }else{
            
            valveClosed.setLocalTranslation(0,-50,0);
            
            valveOpenned.setLocalTranslation(0,0,0);
            
            openned=true;
            
            particleEmitter.begin();
            
        }
        
    }
    
    @Override
    public String getName() {
        
        return "Gas sac";
        
    }
    
}
