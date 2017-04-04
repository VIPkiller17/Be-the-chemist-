/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.containers.funnel;

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
import objects.containers.funnel.FunnelControl;
import objects.solution.Solution;

/**
 *
 * @author VIPkiller17
 */
public class Funnel extends Container implements Savable{

    private static int index;
    
    private FunnelControl funnelControl;
    private Spatial spatial;
    private RigidBodyControl funnel_phy;
    
    private boolean closeable;
    private double maxQuantity;
    private double maxTemperature;
    private double maxPressureOpenned;
    private double maxPressureClosed;
    private Spatial highlightModel;
    private Material highlightModelMat;
    private Spatial liquidModel;
    private Material liquidModelMat;
    private ParticleEmitter particleEmitter;
    
    private boolean isEmitting;
    
    private Vector3f particleEmitterPosition;
    private Spatial openningSurface;
    private Material openningSurfaceMat;
    
    private Node node;
    
    public Funnel(Main main,Vector3f position){
        
        super(main,position);
        
        init(main,position,main.getRootNode(),main.getAssetManager(),main.getBulletAppState());
        
    }
    
    public Funnel(Main main,Vector3f position,Solution solution){
        
        super(main,position,solution);
        
        init(main,position,main.getRootNode(),main.getAssetManager(),main.getBulletAppState());
        
        liquidModelMat.setColor("Color",solution.getLiquidColor());
        
    }
    
    public void init(Main main,Vector3f position,Node rootNode,AssetManager assetManager,BulletAppState bulletAppState){
        
        node=new Node();
        
        closeable=false;
        maxQuantity=0.5;
        maxTemperature=443;
        maxPressureOpenned=6;
        maxPressureClosed=6;
        
        funnel_phy=new RigidBodyControl(1f);
        node.addControl(funnel_phy);
        bulletAppState.getPhysicsSpace().add(funnel_phy);
        
        spatial=assetManager.loadModel("Models/Objects/Containers/Funnel/Funnel.j3o");
        highlightModel=assetManager.loadModel("Models/Objects/Containers/Funnel/Highlight/Funnel_Highlight.j3o");
        liquidModel=assetManager.loadModel("Models/Objects/Containers/Funnel/Liquid/Funnel_Liquid.j3o");
        openningSurface=assetManager.loadModel("Models/Objects/Containers/OpenningSurface/OpenningSurface.j3o");
        
        this.funnelControl=new FunnelControl(this);
        spatial.addControl(funnelControl);
        
        openningSurface.setName("Funnel #"+index+"'s openning");
        openningSurfaceMat=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        openningSurfaceMat.setColor("Color",Main.HIGHLIGHT_INVISIBLE);
        openningSurfaceMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        openningSurface.setQueueBucket(RenderQueue.Bucket.Translucent);
        openningSurface.setMaterial(openningSurfaceMat);
        node.attachChild(openningSurface);
        openningSurface.setLocalTranslation(0,0.06f,0);
        openningSurface.scale(4);
        
        highlightModel.setName("Funnel #"+index+"'s highlight");
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
        liquidModel.setName("Funnel #"+index+"'s liquid");
        liquidModel.setUserData("correctCollision", true);
        liquidModel.setUserData("correspondingObject", this);
        liquidModel.setQueueBucket(RenderQueue.Bucket.Transparent);
        node.attachChild(liquidModel);
        
        spatial.setName("Funnel #"+index++);
        spatial.setUserData("correctCollision", true);
        spatial.setUserData("correspondingObject", this);
        spatial.setQueueBucket(RenderQueue.Bucket.Transparent);
        node.attachChild(spatial);
        
        rootNode.attachChild(node);
        
        node.getControl(RigidBodyControl.class).setPhysicsLocation(position);
        
        main.getItemsList().add(this);
        
        particleEmitterPosition=new Vector3f(0,0,0);
        
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
    
    public void explode(){
        
        
        
    }
    
    public void meltDown(){
        
        
        
    }
    
    public void breakContainer(){
        
        
        
    }
    
    @Override
    public String getDescription() {
        
        return "Funnel:\n  Contains: "+this.getSolution()+"\n  Quantity: "+this.getQuantity();
        
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
        
        node.getControl(RigidBodyControl.class).setPhysicsLocation(position);
        
    }
    
    @Override
    public boolean canContain(int state){
        
        switch(state){
            
            case 0:
                
                return false;
                
            case 1:
                
                return true;
                
            case 2:
                
                return false;
                
            default:
                
                System.out.println("Invalid state passed to canContain()");
                return false;
            
        }
        
    }
    
    @Override
    public Node getNode() {
        
        return node;
        
    }
    
}
