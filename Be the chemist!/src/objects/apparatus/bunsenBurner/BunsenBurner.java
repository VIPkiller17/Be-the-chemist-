/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.apparatus.bunsenBurner;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
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
    
    private Ray ray;
    
    private Spatial bunsenBurner,bunsenBurnerHighlight;
    
    private Material bunsenBurnerHighlightMat;
    
    private boolean highlightVisible;
    
    private Node node;
    
    public BunsenBurner(Main main,AssetManager assetManager,Node rootNode){
        
        super(main,Vector3f.ZERO);
        
        this.main=main;
        this.assetManager=assetManager;
        this.rootNode=rootNode;
        
        bunsenBurner=assetManager.loadModel("Models/Static/Sink/Sink_Handle_Cold.j3o");
        bunsenBurner.setName("Bunsen burner");
        bunsenBurner.setUserData("correctCollision", true);
        bunsenBurner.setUserData("correspondingObject", this);
        
        node.attachChild(bunsenBurner);
        
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
        
        return bunsenBurner.getWorldTranslation();
        
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
    
    public Spatial getSpatial(){
        
        return bunsenBurner;
        
    }
    
}
