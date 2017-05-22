/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.apparatus.bunsenBurner;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResults;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import interfaces.Grabbable;
import main.Main;
import objects.apparatus.Apparatus;

/**
 *
 * @author VIPkiller17
 */
public class BunsenBurner extends Apparatus implements Grabbable{

    private Main main;
    private AssetManager assetManager;
    private Node rootNode;
    
    private Spatial spatial;
    private Node node;
    
    private Ray ray;
    
    private Spatial bunsenBurnerHighlight;
    private Material bunsenBurnerHighlightMat;
    
    private boolean highlightVisible;
    
     private CollisionResults collisionResults;
    
    private CollisionShape analyticalBalanceCollisionShape;
    private RigidBodyControl analyticalBalance_phy;
    
    
    public BunsenBurner(Main main, Node rootNode, CollisionResults collisionResults, AssetManager assetManager, Vector3f position){
        
        super(main, position);
        
        this.main=main;
        this.assetManager=assetManager;
        this.rootNode=rootNode;
        
        node = new Node();
        
        spatial=assetManager.loadModel("Models/Static/BunsenBurner/BunsenBurner.obj");
        
        /*//BunsenBurner Model
        bunsenBurner=assetManager.loadModel("Models/Static/BunsenBurner/BunsenBurner.obj");
        Material bunsenBurnerMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        bunsenBurner.setName("Bunsen burner");
        bunsenBurner.setUserData("correctCollision", true);
        bunsenBurner.setUserData("correspondingObject", this);
        node.attachChild(bunsenBurner);*/
        
        /*valveOpennedHighlight=assetManager.loadModel("Models/Static/DistilledWaterContainer/Valve/Highlight/DistilledWaterContainer_Valve_Openned_Highlight.j3o");
        valveOpennedHighlight.setName("Distilled water container");
        valveOpennedHighlight.setLocalTranslation(0,-5,0);
        valveOpennedHighlightMat=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        valveOpennedHighlightMat.setColor("Color",Main.HIGHLIGHT_VISIBLE);
        valveOpennedHighlightMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        valveOpennedHighlight.setQueueBucket(RenderQueue.Bucket.Translucent);
        valveOpennedHighlight.setMaterial(valveOpennedHighlightMat);
        node.attachChild(valveOpennedHighlight);*/
        
        //Highlight Model
        bunsenBurnerHighlight=assetManager.loadModel("Models/Static/BunsenBurner/BunsenBurner_Wheel_Highlight.j3o");
        bunsenBurnerHighlight.setName("Bunsen burner wheel highlight");
        bunsenBurnerHighlight.setLocalTranslation(0,-700,0);
        bunsenBurnerHighlightMat=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        bunsenBurnerHighlightMat.setColor("Color",Main.HIGHLIGHT_VISIBLE);
        bunsenBurnerHighlightMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        bunsenBurnerHighlight.setQueueBucket(RenderQueue.Bucket.Translucent);
        bunsenBurnerHighlight.setMaterial(bunsenBurnerHighlightMat);
        node.attachChild(bunsenBurnerHighlight);
        
        spatial.setLocalRotation(Quaternion.ZERO); 
        spatial.setUserData("correctCollision", true);
        spatial.setUserData("correspondingObject", this);
        node.attachChild(spatial);
        node.setLocalTranslation(position);
        
        rootNode.attachChild(node);
        
        main.getItemsList().add(this);
        
    }
    
    //ray
    public void setRayCoords(Vector3f origin,Vector3f direction){
        
        ray.setOrigin(origin);
        ray.setDirection(direction);
        
    }
    
    public Vector3f getLocalTranslation() {
        
        return node.getLocalTranslation();
       
    }
    
    public Ray getRay() {
        return ray;
    }
    
    @Override
    public String getDescription() {
        
        return "A bunsen burner";
        
    }

    @Override
    public void highlightVisible(boolean highlightVisible) {
        
        this.highlightVisible=highlightVisible;
        
    }

    @Override
    public Vector3f getGrabbablePosition() {
        
        return spatial.getWorldTranslation();
        
    }

    @Override
    public boolean isHighlightVisible() {
        
        return highlightVisible;
        
    }

    @Override
    public void setPos(Vector3f position) {
        
        node.setLocalTranslation(position);
        
    }
    
    @Override
    public Node getNode() {
        
        return node;
        
    }
    
    @Override
    public String getName() {
        
        return "Bunsen burner";
        
    }
    
    @Override
    public Spatial getSpatial(){
        
        return spatial;
        
    }

    @Override
    public void destroy() {
        
        main.getRootNode().detachChild(node);
        main.getItemsList().remove(this);
        
    }
    
}
