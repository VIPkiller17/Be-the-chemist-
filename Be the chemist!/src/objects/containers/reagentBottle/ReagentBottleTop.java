/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.containers.reagentBottle;

import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import interfaces.Grabbable;
import main.Main;
import objects.PhysicalObject;

/**
 *
 * @author etien
 */
public class ReagentBottleTop extends PhysicalObject implements Grabbable {
    
    private Main main;
    private ReagentBottle reagentBottle;
    
    private Spatial top,topHighlight;
    
    private Material topMat,topHighlightMat;
    
    private boolean highlightVisible;
    private boolean openned;
    
    private Node node;
    
    public ReagentBottleTop(Main main,ReagentBottle reagentBottle){
        
        super(main,Vector3f.ZERO);
        
        this.main=main;
        this.reagentBottle=reagentBottle;
        
        this.main=main;
        
        node=new Node();
        
        top=main.getAssetManager().loadModel("Models/Objects/Containers/ReagentBottle/Top/ReagentBottle_Top.j3o");
        top.setName("Reagent bottle top");
        top.setLocalTranslation(0f, -50f, 0f);
        topMat=new Material(main.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        topMat.setColor("Color",Main.HIGHLIGHT_VISIBLE);
        topMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        top.setQueueBucket(RenderQueue.Bucket.Transparent);
        top.setMaterial(topMat);
        node.attachChild(reagentBottle.getNode());
        
        topHighlight=main.getAssetManager().loadModel("Models/Objects/Containers/ReagentBottle/Top/ReagentBottle_Top_Highlight.j3o");
        top.setName("Reagent bottle top highlight");
        top.setLocalTranslation(0f, -50f, 0f);
        topMat=new Material(main.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        topMat.setColor("Color",Main.HIGHLIGHT_VISIBLE);
        topMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        top.setQueueBucket(RenderQueue.Bucket.Transparent);
        top.setMaterial(topMat);
        node.attachChild(reagentBottle.getNode());
        
        main.getItemsList().add(this);
        
        main.getRootNode().attachChild(node);
        
    }
    
    @Override
    public void highlightVisible(boolean highlightVisible) {
        
        this.highlightVisible=highlightVisible;
        
        if(highlightVisible)
            
            if(openned){
                
                topHighlight.setLocalTranslation(0,0,0);
                topHighlight.setLocalTranslation(0,-5,0);
            
            }else{
                
                topHighlight.setLocalTranslation(0,-5,0);
                topHighlight.setLocalTranslation(0,0,0);
                
            }
        
        else{
            
            topHighlight.setLocalTranslation(0,-5,0);
            topHighlight.setLocalTranslation(0,-5,0);
                
        }
        
    }

    @Override
    public Vector3f getGrabbablePosition() {
        
        return reagentBottle.getParticleEmitter().getPosition();
        
    }

    @Override
    public boolean isHighlightVisible() {
        
        return highlightVisible;
        
    }

    @Override
    public String getDescription() {
        
        return "A reagent bottle top";
        
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
        
        return "Reagent bottle top";
        
    }
    
    @Override
    public Spatial getSpatial(){
        
        return top;
        
    }
    
    @Override
    public void destroy() {
        
        main.getRootNode().detachChild(node);
        main.getItemsList().remove(this);
        
    }
    
}
