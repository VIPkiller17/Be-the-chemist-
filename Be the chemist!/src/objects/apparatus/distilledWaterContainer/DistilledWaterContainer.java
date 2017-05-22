/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.apparatus.distilledWaterContainer;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.export.Savable;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import interfaces.Grabbable;
import main.Main;
import objects.apparatus.Apparatus;
import objects.particleEmitter.ParticleEmitter;

public class DistilledWaterContainer extends Apparatus implements Savable,Grabbable{

    private Main main;
    private AssetManager assetManager;
    private Node rootNode;
    
    private ParticleEmitter particleEmitter;
    
    private boolean highlightVisible;
    private boolean opened;
    
    private Spatial container,valveClosed,valveOpened,valveClosedHighlight,valveOpenedHighlight,liquid;
    
    private Material valveClosedHighlightMat,valveOpenedHighlightMat;
    
    private Node node;
    
    private CollisionShape cs;
    private RigidBodyControl phy;
    
    public DistilledWaterContainer(Main main,AssetManager assetManager,Node rootNode){
        
        super(main,Vector3f.ZERO);
        
        this.main=main;
        this.assetManager=assetManager;
        this.rootNode=rootNode;
        
        node=new Node();
        
        particleEmitter=new ParticleEmitter(main,this);
        
        container=assetManager.loadModel("Models/Static/DistilledWaterContainer/DistilledWaterContainer.j3o");
        container.setName("Distilled water container");
        container.setUserData("correctCollision", true);
        container.setUserData("correspondingObject", this);
        container.setQueueBucket(RenderQueue.Bucket.Transparent);
        node.attachChild(container);
        
        liquid=assetManager.loadModel("Models/Static/DistilledWaterContainer/Liquid/DistilledWaterContainer_Liquid.j3o");
        liquid.setName("Distilled water container");
        liquid.setUserData("correctCollision", true);
        liquid.setUserData("correspondingObject", this);
        node.attachChild(liquid);
        liquid.setLocalTranslation(0.05f,-0.15f,0);
        
        valveClosed=assetManager.loadModel("Models/Static/DistilledWaterContainer/Valve/DistilledWaterContainer_Valve_Closed.j3o");
        valveClosed.setName("Distilled water container closed valve");
        valveClosed.setUserData("correctCollision", true);
        valveClosed.setUserData("correspondingObject", this);
        node.attachChild(valveClosed);
        valveClosed.setLocalTranslation(-0.35f,-0.35f,0.015f);
        
        valveOpened=assetManager.loadModel("Models/Static/DistilledWaterContainer/Valve/DistilledWaterContainer_Valve_Openned.j3o");
        valveOpened.setName("Distilled water container openned valve");
        valveOpened.setUserData("correctCollision", true);
        valveOpened.setUserData("correspondingObject", this);
        node.attachChild(valveOpened);
        valveOpened.setLocalTranslation(new Vector3f(0,-5,0));
        
        valveClosedHighlight=assetManager.loadModel("Models/Static/DistilledWaterContainer/Valve/Highlight/DistilledWaterContainer_Valve_Closed_Highlight.j3o");
        valveClosedHighlight.setName("Distilled water container");
        valveClosedHighlight.setLocalTranslation(0,-5,0);
        valveClosedHighlightMat=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        valveClosedHighlightMat.setColor("Color",Main.HIGHLIGHT_VISIBLE);
        valveClosedHighlightMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        valveClosedHighlight.setQueueBucket(RenderQueue.Bucket.Translucent);
        valveClosedHighlight.setMaterial(valveClosedHighlightMat);
        node.attachChild(valveClosedHighlight);
        
        valveOpenedHighlight=assetManager.loadModel("Models/Static/DistilledWaterContainer/Valve/Highlight/DistilledWaterContainer_Valve_Openned_Highlight.j3o");
        valveOpenedHighlight.setName("Distilled water container");
        valveOpenedHighlight.setLocalTranslation(0,-5,0);
        valveOpenedHighlightMat=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        valveOpenedHighlightMat.setColor("Color",Main.HIGHLIGHT_VISIBLE);
        valveOpenedHighlightMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        valveOpenedHighlight.setQueueBucket(RenderQueue.Bucket.Translucent);
        valveOpenedHighlight.setMaterial(valveOpenedHighlightMat);
        node.attachChild(valveOpenedHighlight);
        
        main.getItemsList().add(this);
        
        rootNode.attachChild(node);
        
        cs=CollisionShapeFactory.createMeshShape(container);
        
        phy=new RigidBodyControl(cs,0);
        container.addControl(phy);
        main.getBulletAppState().getPhysicsSpace().add(phy);
        
        node.setLocalTranslation(4.3f,1.329f,-3);
        phy.setPhysicsLocation(new Vector3f(4.3f,1.329f,-3));
        
    }
    
    public void setOpened(boolean opened){
        
        this.opened=opened;
        
        if(opened)
        
            particleEmitter.begin();
        
        else
            
            particleEmitter.stop();
        
    }
    
    public void toggle(){
        
        if(opened){
            
            valveClosed.setLocalTranslation(-0.35f,-0.35f,0.015f);
            
            valveOpened.setLocalTranslation(0,-5,0);
            
            opened=false;
            
            particleEmitter.stop();
            
        }else{
            
            valveOpened.setLocalTranslation(-0.37f,-0.35f,0f);
            
            valveClosed.setLocalTranslation(0,-5,0);
            
            opened=true;
            
            particleEmitter.begin();
            
        } 
        
    }
    
    @Override
    public Node getNode(){
        
        return node;
        
    }
    
    @Override
    public String getDescription() {
        
        return "The distilled water container";
        
    }

    @Override
    public void highlightVisible(boolean highlightVisible) {
        
        this.highlightVisible=highlightVisible;
        
        if(highlightVisible)
            
            if(opened){
        
                //valveOpennedHighlightMat.setColor("Color",Main.HIGHLIGHT_VISIBLE);
                //valveClosedHighlightMat.setColor("Color",Main.HIGHLIGHT_INVISIBLE);
                
                valveOpenedHighlight.setLocalTranslation(-0.37f,-0.35f,0f);
                valveClosedHighlight.setLocalTranslation(0,-5,0);
            
            }else{
                
                //valveClosedHighlightMat.setColor("Color",Main.HIGHLIGHT_VISIBLE);
                //valveOpennedHighlightMat.setColor("Color",Main.HIGHLIGHT_INVISIBLE);
                
                valveOpenedHighlight.setLocalTranslation(0,-5,0);
                valveClosedHighlight.setLocalTranslation(-0.35f,-0.35f,0.015f);
                
            }
        
        else{
            
            //valveOpennedHighlightMat.setColor("Color",Main.HIGHLIGHT_INVISIBLE);
            //valveClosedHighlightMat.setColor("Color",Main.HIGHLIGHT_INVISIBLE);
            
            valveOpenedHighlight.setLocalTranslation(0,-5,0);
            valveClosedHighlight.setLocalTranslation(0,-5,0);
                
        }
        
    }

    @Override
    public boolean isHighlightVisible() {
        
        return highlightVisible;
        
    }

    @Override
    public Vector3f getGrabbablePosition() {
        
        return node.getWorldTranslation().add(-0.35f,-0.35f,0.015f);
        
    }

    @Override
    public void setPos(Vector3f position) {
        
        node.setLocalTranslation(position);
        
    }
    
    @Override
    public String getName() {
        
        return "Distilled water container";
        
    }
    
    @Override
    public Spatial getSpatial(){
        
        return container;
        
    }
    
    @Override
    public void destroy() {
        
        main.getRootNode().detachChild(node);
        main.getItemsList().remove(this);
        
    }
    
    @Override
    public String toString(){
        
        return "The dstilled water container";
        
    }
    
    @Override
    public boolean equals(Object otherDistilledWaterContainer){
        
        if(otherDistilledWaterContainer instanceof DistilledWaterContainer)
            
            return ((DistilledWaterContainer) otherDistilledWaterContainer).getSpatial().getLocalTranslation().equals(container.getLocalTranslation());
        
        else
            
            return false;
            
    }
    
}
