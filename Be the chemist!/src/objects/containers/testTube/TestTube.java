/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.containers.testTube;

import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
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
public class TestTube extends Container implements Savable{

    private static int index;
    
    private TestTubeControl testTubeControl;
    private Spatial spatial;
    private RigidBodyControl testTube_phy;
    
    private boolean closeable;
    private double maxQuantity;
    private double maxTemperature;
    private double maxPressureOpenned;
    private double maxPressureClosed;
    private Spatial highlightModel;
    private Material highlightModelMat;
    private Spatial stopperModel;
    private Spatial liquidModel;
    private Material liquidModelMat;
    private Spatial solidModel;
    private Material solidModelMat;
    
    private ParticleEmitter pourParticleEmitter,evaporationParticleEmitter,reactionParticleEmitter;
    
    private boolean isEmitting;
    
    private CapsuleCollisionShape collisionShape;
    
    private Vector3f presentPosition;
    private Quaternion presentRotation;
    
    private Node node;
    
    private Main main;
    
    public TestTube(Main main,Vector3f position){
        
        super(main,position);
        
        setSolution(new Solution(main,this,null,0,0));
        
        init(main,position);
        
    }
    
    public TestTube(Main main,Vector3f position,Solution solution){
        
        super(main,position,solution);
        
        init(main,position);
        
    }
    
    private void init(Main main,Vector3f position){
        
        node=new Node();
        
        this.main=main;
        
        presentPosition=position;
        
        closeable=false;
        maxQuantity=1;
        maxTemperature=1773;
        maxPressureOpenned=6;
        maxPressureClosed=3;
        
        spatial=main.getAssetManager().loadModel("Models/Objects/Containers/TestTube/TestTube.j3o");
        highlightModel=main.getAssetManager().loadModel("Models/Objects/Containers/TestTube/Highlight/TestTube_Highlight.j3o");
        liquidModel=main.getAssetManager().loadModel("Models/Objects/Containers/TestTube/Liquid/TestTube_Liquid.j3o");
        solidModel=main.getAssetManager().loadModel("Models/Objects/Containers/TestTube/Solid/TestTube_Solid.j3o");
        stopperModel=main.getAssetManager().loadModel("Models/Objects/Containers/TestTube/Stopper/TestTube_Normal_Stopper.j3o");
        
        this.testTubeControl=new TestTubeControl(this);
        spatial.addControl(testTubeControl);
        
        highlightModel.setName("TestTube #"+index+"'s highlight");
        highlightModelMat=new Material(main.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        highlightModelMat.setColor("Color",Main.HIGHLIGHT_VISIBLE);
        highlightModelMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        highlightModel.setQueueBucket(RenderQueue.Bucket.Translucent);
        highlightModel.setMaterial(highlightModelMat);
        node.attachChild(highlightModel);
        highlightModel.setLocalTranslation(0,-50,0);
        
        liquidModelMat=new Material(main.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        liquidModelMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        liquidModelMat.setColor("Color",ColorRGBA.White);
        liquidModel.setMaterial(liquidModelMat);
        liquidModel.setName("Erlenmeyer #"+index+"'s liquid");
        liquidModel.setUserData("correctCollision", true);
        liquidModel.setUserData("correspondingObject", this);
        liquidModel.setQueueBucket(RenderQueue.Bucket.Transparent);
        node.attachChild(liquidModel);
        liquidModel.setLocalTranslation(0,-50,0);
        
        solidModelMat=new Material(main.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        solidModel.setMaterial(solidModelMat);
        solidModel.setName("Erlenmeyer #"+index+"'s solid");
        solidModel.setUserData("correctCollision", true);
        solidModel.setUserData("correspondingObject", this);
        node.attachChild(solidModel);
        solidModel.setLocalTranslation(0,-50,0);
        
        stopperModel.setName("Erlenmeyer #"+index+"'s solid");
        stopperModel.setUserData("correctCollision", true);
        stopperModel.setUserData("correspondingObject", this);
        node.attachChild(stopperModel);
        stopperModel.setLocalTranslation(0,-50,0);
        
        spatial.setName("Erlenmeyer #"+index++);
        spatial.setUserData("correctCollision", true);
        spatial.setUserData("correspondingObject", this);
        spatial.setQueueBucket(RenderQueue.Bucket.Transparent);
        collisionShape=new CapsuleCollisionShape(0.02f,0.25f,1);
        
        testTube_phy=new RigidBodyControl(collisionShape,1f);
        testTube_phy.setFriction(1f);
        testTube_phy.setDamping(0.75f, 0.75f);
        testTube_phy.setSleepingThresholds(30f,60f);
        spatial.addControl(testTube_phy);
        main.getBulletAppState().getPhysicsSpace().add(testTube_phy);
        
        main.getRootNode().attachChild(node);
        main.getRootNode().attachChild(spatial);
        
        main.getItemsList().add(this);
        main.getHeatables().add(this);
        main.getTestTubes().add(this);
        main.getParticleReceivers().add(this);
        
        pourParticleEmitter=new ParticleEmitter(main,this,new Vector3f(0,0.12f,0),Vector3f.ZERO,new Quaternion().fromAngleAxis(FastMath.DEG_TO_RAD*1,Vector3f.UNIT_Z),0,0,new Vector3f(0.01f,0.0075f,0),new Vector3f(0,0,0),0.05,0,new Vector3f(0,-9.806f,0),Vector3f.ZERO,"Erlenmeyer's pourParticleEmitter");
        evaporationParticleEmitter=new ParticleEmitter(main,this,new Vector3f(0,0.12f,0),Vector3f.ZERO,Quaternion.ZERO,0,0,new Vector3f(0,0.02f,0),new Vector3f(0,0,0),0.1,0.09,new Vector3f(0,0.05f,0),Vector3f.ZERO,"Erlenmeyer's evaporationParticleEmitter");
        
        evaporationParticleEmitter.setVolume(0.001);
        
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
    
    public void startPouring(){
        
        pourParticleEmitter.begin();
        
        isEmitting=true;
        
    }
    
    public void stopPouring(){
        
        pourParticleEmitter.stop();
        isEmitting=false;
        
    }
    
    public void startEvaporating(){
        
        evaporationParticleEmitter.begin();
        isEmitting=true;
        
    }
    
    public void stopEvaporating(){
        
        evaporationParticleEmitter.stop();
        isEmitting=false;
        
    }
    
    public void startReacting(){
        
        reactionParticleEmitter.begin();
        isEmitting=true;
        
    }
    
    public void stopReacting(){
        
        reactionParticleEmitter.stop();
        isEmitting=false;
        
    }
    
    public boolean isEmitting(){
        
        return isEmitting;
        
    }
    
    public ParticleEmitter getPourParticleEmitter(){
        
        return pourParticleEmitter;
        
    }
    
    public ParticleEmitter getEvaporationParticleEmitter(){
        
        return evaporationParticleEmitter;
        
    }
    
    @Override
    public String getDescription() {
        
        if(getSolution().getVolume()<0.001){
            
            return "Test tube:\n   Empty.";
            
        }else{
        
            return "Test tube:\n   Contains:\n   "+getSolution()+"\n  Total volume: "+getFormattedVolume()+" L;\n  Average temperature: "+getFormattedTemperature()+" K.";
            
        }
        
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
        
        spatial.getControl(RigidBodyControl.class).setPhysicsLocation(position);
        node.setLocalTranslation(position);
        
        presentPosition=position;
        
    }
    
    @Override
    public Vector3f getPosition(){
        
        return spatial.getControl(RigidBodyControl.class).getPhysicsLocation();
        
    }
    
    public void setRotation(Quaternion rotation){
        
        spatial.getControl(RigidBodyControl.class).setPhysicsRotation(rotation);
        node.setLocalRotation(rotation);
        
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
            
            solidModel.setLocalTranslation(0,-50,0);
            
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
    
    public void updatePhysicsLocation(){
        
        spatial.getControl(RigidBodyControl.class).setPhysicsLocation(node.getLocalTranslation());
        
    }
    
    public void toggleOpenedClosed(){
        
        System.out.println("        toggleOpennedClosed() called ans isclosed: "+isClosed());
        
        if(isClosed()){
            
            System.out.println("            erlenmeyer was closed, openning, setting stoppeModel's translation to -50");
            
            stopperModel.setLocalTranslation(0,-50,0);
        
        }else{
            
            System.out.println("            erlenmeyer was closed, openning, setting stoppeModel's translation to 0");
            
            stopperModel.setLocalTranslation(0,0,0);
            
        }
        
        setClosed(!isClosed());
        
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
        main.getTestTubes().remove(this);
        main.getParticleReceivers().remove(this);
        
        destroyed=true;
        
    }
    
}
