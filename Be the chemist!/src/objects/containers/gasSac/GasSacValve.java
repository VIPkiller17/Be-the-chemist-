/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.containers.gasSac;

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
 * @author VIPkiller17
 */
public class GasSacValve extends PhysicalObject implements Grabbable{

    private Main main;
    private GasSac gasSac;
    
    private Spatial valveClosed,valveOpenned,valveClosedHighlight,valveOpennedHighlight;
    
    private Material valveClosedHighlightMat,valveOpennedHighlightMat;
    
    private boolean highlightVisible;
    private boolean openned;
    
    private Node node;
    
    public GasSacValve(Main main,GasSac gasSac){
        
        super(main,Vector3f.ZERO);
        
        this.main=main;
        this.gasSac=gasSac;
        
        this.main=main;
        
        node=new Node();
        
        valveClosed=main.getAssetManager().loadModel("Models/Static/DistilledWaterContainer/Valve/DistilledWaterContainer_Valve_Closed.j3o");
        valveClosed.setName("Distilled water container closed valve");
        valveClosed.setUserData("correctCollision", true);
        valveClosed.setUserData("correspondingObject", this);
        node.attachChild(valveClosed);
        
        valveOpenned=main.getAssetManager().loadModel("Models/Static/DistilledWaterContainer/Valve/DistilledWaterContainer_Valve_Openned.j3o");
        valveOpenned.setName("Distilled water container openned valve");
        valveOpenned.setUserData("correctCollision", true);
        valveOpenned.setUserData("correspondingObject", this);
        node.attachChild(valveOpenned);
        valveOpenned.setLocalTranslation(new Vector3f(0,-5,0));
        
        valveClosedHighlight=main.getAssetManager().loadModel("Models/Static/DistilledWaterContainer/Valve/Highlight/DistilledWaterContainer_Valve_Closed_Highlight.j3o");
        valveClosedHighlight.setName("Distilled water container");
        valveClosedHighlight.setLocalTranslation(0,-5,0);
        valveClosedHighlightMat=new Material(main.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        valveClosedHighlightMat.setColor("Color",Main.HIGHLIGHT_VISIBLE);
        valveClosedHighlightMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        valveClosedHighlight.setQueueBucket(RenderQueue.Bucket.Translucent);
        valveClosedHighlight.setMaterial(valveClosedHighlightMat);
        node.attachChild(valveClosedHighlight);
        
        valveOpennedHighlight=main.getAssetManager().loadModel("Models/Static/DistilledWaterContainer/Valve/Highlight/DistilledWaterContainer_Valve_Openned_Highlight.j3o");
        valveOpennedHighlight.setName("Distilled water container");
        valveOpennedHighlight.setLocalTranslation(0,-5,0);
        valveOpennedHighlightMat=new Material(main.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        valveOpennedHighlightMat.setColor("Color",Main.HIGHLIGHT_VISIBLE);
        valveOpennedHighlightMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        valveOpennedHighlight.setQueueBucket(RenderQueue.Bucket.Translucent);
        valveOpennedHighlight.setMaterial(valveOpennedHighlightMat);
        node.attachChild(valveOpennedHighlight);
        
        main.getItemsList().add(this);
        
        main.getRootNode().attachChild(node);
        
    }
    
    @Override
    public void highlightVisible(boolean highlightVisible) {
        
        this.highlightVisible=highlightVisible;
        
        if(highlightVisible)
            
            if(openned){
        
                //valveOpennedHighlightMat.setColor("Color",Main.HIGHLIGHT_VISIBLE);
                //valveClosedHighlightMat.setColor("Color",Main.HIGHLIGHT_INVISIBLE);
                
                valveOpennedHighlight.setLocalTranslation(0,0,0);
                valveClosedHighlight.setLocalTranslation(0,-5,0);
            
            }else{
                
                //valveClosedHighlightMat.setColor("Color",Main.HIGHLIGHT_VISIBLE);
                //valveOpennedHighlightMat.setColor("Color",Main.HIGHLIGHT_INVISIBLE);
                
                valveOpennedHighlight.setLocalTranslation(0,-5,0);
                valveClosedHighlight.setLocalTranslation(0,0,0);
                
            }
        
        else{
            
            //valveOpennedHighlightMat.setColor("Color",Main.HIGHLIGHT_INVISIBLE);
            //valveClosedHighlightMat.setColor("Color",Main.HIGHLIGHT_INVISIBLE);
            
            valveOpennedHighlight.setLocalTranslation(0,-5,0);
            valveClosedHighlight.setLocalTranslation(0,-5,0);
                
        }
        
    }

    @Override
    public Vector3f getGrabbablePosition() {
        
        return gasSac.getParticleEmitter().getPosition();
        
    }

    @Override
    public boolean isHighlightVisible() {
        
        return highlightVisible;
        
    }

    @Override
    public String getDescription() {
        
        return "A gas sac's valve";
        
    }
    
}
