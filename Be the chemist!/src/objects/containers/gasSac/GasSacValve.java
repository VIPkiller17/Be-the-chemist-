/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.containers.gasSac;

import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
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
        
        node=gasSac.getNode();
        
        valveClosed=main.getAssetManager().loadModel("Models/Objects/Containers/GasSac/Valve/GasSac_Valve_Closed.j3o");
        valveClosed.setName("Gas sac closed valve");
        valveClosed.setUserData("correctCollision", true);
        valveClosed.setUserData("correspondingObject", this);
        node.attachChild(valveClosed);
        
        valveOpenned=main.getAssetManager().loadModel("Models/Objects/Containers/GasSac/Valve/GasSac_Valve_Openned.j3o");
        valveOpenned.setName("Gas sac openned valve");
        valveOpenned.setUserData("correctCollision", true);
        valveOpenned.setUserData("correspondingObject", this);
        node.attachChild(valveOpenned);
        valveOpenned.setLocalTranslation(new Vector3f(0,-50,0));
        
        valveClosedHighlight=main.getAssetManager().loadModel("Models/Objects/Containers/GasSac/Valve/GasSac_Valve_Closed_Highlight.j3o");
        valveClosedHighlight.setName("Gas sac closed valve highlight");
        valveClosedHighlightMat=new Material(main.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        valveClosedHighlightMat.setColor("Color",Main.HIGHLIGHT_VISIBLE);
        valveClosedHighlightMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        valveClosedHighlight.setQueueBucket(RenderQueue.Bucket.Translucent);
        valveClosedHighlight.setMaterial(valveClosedHighlightMat);
        node.attachChild(valveClosedHighlight);
        valveClosedHighlight.setLocalTranslation(new Vector3f(0,-50,0));
        
        Box boxMesh = new Box(0.01f,0.01f,0.01f); 
        Geometry boxGeo = new Geometry("A Textured Box", boxMesh); 
        Material boxMat = new Material(main.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        boxMat.setColor("Color", ColorRGBA.Blue);
        boxGeo.setMaterial(boxMat); 
        node.attachChild(boxGeo);
        boxGeo.setLocalTranslation(gasSac.getParticleEmitter().getPosition());
        
        valveOpennedHighlight=main.getAssetManager().loadModel("Models/Objects/Containers/GasSac/Valve/GasSac_Valve_Openned_Highlight.j3o");
        valveOpennedHighlight.setName("Gas sac openned valve highlight");
        valveOpennedHighlightMat=new Material(main.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        valveOpennedHighlightMat.setColor("Color",Main.HIGHLIGHT_VISIBLE);
        valveOpennedHighlightMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        valveOpennedHighlight.setQueueBucket(RenderQueue.Bucket.Translucent);
        valveOpennedHighlight.setMaterial(valveOpennedHighlightMat);
        node.attachChild(valveOpennedHighlight);
        valveOpennedHighlight.setLocalTranslation(new Vector3f(0,-50,0));
        
        main.getItemsList().add(this);
        
    }
    
    public void toggle(){
        
        if(openned){
            
            valveOpenned.setLocalTranslation(0,-50,0);
            
            valveClosed.setLocalTranslation(0,0,0);
            
            openned=false;
            
            gasSac.getParticleEmitter().stop();
            
        }else{
            
            valveClosed.setLocalTranslation(0,-50,0);
            
            valveOpenned.setLocalTranslation(0,0,0);
            
            openned=true;
            
            gasSac.getParticleEmitter().begin();
            
        }
        
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
        
        return gasSac.getGrabbablePosition().addLocal(-0.02f, 0.17f, 0);
        
    }

    @Override
    public boolean isHighlightVisible() {
        
        return highlightVisible;
        
    }

    @Override
    public String getDescription() {
        
        return "A gas sac's valve";
        
    }

    @Override
    public void setPos(Vector3f position) {
        
        node.setLocalTranslation(position);
        
    }
    
    @Override
    public Node getNode(){
        
        return node;
        
    }
    
}
