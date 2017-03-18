/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.containers.erlenmeyer;

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
import main.Main;
import objects.solution.Solution;

/**
 *
 * @author VIPkiller17
 */
public class Erlenmeyer extends Container implements Savable{

    private static int index;
    
    private ErlenmeyerControl control;
    private Spatial spatial;
    private RigidBodyControl erlenmeyer_phy;
    private Node node;
    private Main main;
    
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
    
    public Erlenmeyer(Main main,Vector3f position,Node rootNode,AssetManager assetManager,BulletAppState bulletAppState){
        
        super(main,position);
        
        init(position,rootNode,assetManager,bulletAppState);
        
    }
    
    public Erlenmeyer(Main main,Vector3f position,Node rootNode,AssetManager assetManager,BulletAppState bulletAppState,Solution solution,double quantity){
        
        super(main,position);
        
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
        
        node.addControl(erlenmeyer_phy);
        erlenmeyer_phy=new RigidBodyControl(1f);
        bulletAppState.getPhysicsSpace().add(erlenmeyer_phy);
        
        //particleEmitter=new ParticleEmitter();
        
        spatial=assetManager.loadModel("Models/Objects/Containers/Erlenmeyer/Erlenmeyer.j3o");
        highlightModel=assetManager.loadModel("Models/Objects/Containers/Erlenmeyer/Highlight/Erlenmeyer_Highlight.j3o");
        stopperModel=assetManager.loadModel("Models/Objects/Containers/Erlenmeyer/Stopper/Erlenmeyer_Normal_Stopper.j3o");
        holedStopperModel=assetManager.loadModel("Models/Objects/Containers/Erlenmeyer/Stopper/Erlenmeyer_Holed_Stopper.j3o");
        liquidModel=assetManager.loadModel("Models/Objects/Containers/Erlenmeyer/Liquid/Erlenmeyer_Liquid.j3o");
        solidModel=assetManager.loadModel("Models/Objects/Containers/Erlenmeyer/Solid/Erlenmeyer_Solid.j3o");
        
        highlightModelMat=new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        highlightModelMat.setBoolean("UseMaterialColors", true); 
        highlightModelMat.setColor("Ambient",new ColorRGBA(0,0,0,0)); 
        highlightModelMat.setColor("Diffuse",new ColorRGBA(0,0,0,0));
        highlightModel.setMaterial(highlightModelMat);
        highlightModel.setLocalTranslation(position);
        highlightModel.setName("Erlenmeyer #"+index+"'s highlight");
        highlightModel.setUserData("correctCollision", true);
        highlightModel.setUserData("correspondingObject", this);
        node.attachChild(highlightModel);
        
        stopperModelMat=new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        stopperModelMat.setBoolean("UseMaterialColors", true); 
        stopperModelMat.setColor("Ambient",new ColorRGBA(0,0,0,0)); 
        stopperModelMat.setColor("Diffuse",new ColorRGBA(0,0,0,0));
        stopperModel.setMaterial(stopperModelMat);
        stopperModel.setLocalTranslation(position);
        stopperModel.setName("Erlenmeyer #"+index+"'s normal stopper");
        stopperModel.setUserData("correctCollision", true);
        stopperModel.setUserData("correspondingObject", this);
        node.attachChild(stopperModel);
        
        holedStopperModelMat=new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        holedStopperModelMat.setBoolean("UseMaterialColors", true); 
        holedStopperModelMat.setColor("Ambient",new ColorRGBA(0,0,0,0)); 
        holedStopperModelMat.setColor("Diffuse",new ColorRGBA(0,0,0,0));
        holedStopperModel.setMaterial(holedStopperModelMat);
        holedStopperModel.setLocalTranslation(position);
        holedStopperModel.setName("Erlenmeyer #"+index+"'s holed stopper");
        holedStopperModel.setUserData("correctCollision", true);
        holedStopperModel.setUserData("correspondingObject", this);
        node.attachChild(holedStopperModel);
        
        liquidModelMat=new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        liquidModelMat.setBoolean("UseMaterialColors", true); 
        liquidModelMat.setColor("Ambient",new ColorRGBA(0,0,0,0)); 
        liquidModelMat.setColor("Diffuse",new ColorRGBA(0,0,0,0));
        liquidModel.setMaterial(liquidModelMat);
        liquidModel.setLocalTranslation(position);
        liquidModel.setName("Erlenmeyer #"+index+"'s liquid");
        liquidModel.setUserData("correctCollision", true);
        liquidModel.setUserData("correspondingObject", this);
        node.attachChild(liquidModel);
        
        solidModelMat=new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        solidModelMat.setBoolean("UseMaterialColors", true); 
        solidModelMat.setColor("Ambient",new ColorRGBA(0,0,0,0)); 
        solidModelMat.setColor("Diffuse",new ColorRGBA(0,0,0,0));
        solidModel.setMaterial(solidModelMat);
        solidModel.setLocalTranslation(position);
        solidModel.setName("Erlenmeyer #"+index+"'s solid");
        solidModel.setUserData("correctCollision", true);
        solidModel.setUserData("correspondingObject", this);
        node.attachChild(solidModel);
        
        spatial.scale(1f,1f,1f);
        spatial.rotate(0.0f, 0.0f, 0.0f);
        spatial.setLocalTranslation(position);
        spatial.setName("Erlenmeyer #"+index++);
        spatial.setUserData("correctCollision", true);
        spatial.setUserData("correspondingObject", this);
        node.attachChild(spatial);
        
        rootNode.attachChild(node);
        
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
    
    @Override
    public String getDescription() {
        
        return "Erlenmeyer:\n  Contains: "+this.getSolution()+"\n  Quantity: "+this.getQuantity();
        
    }
    
}
