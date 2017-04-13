/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.world;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
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
public class SinkHandle extends PhysicalObject implements Grabbable{
    
    private Main main;
    private AssetManager assetManager;
    private int index;
    private Sink parentSink;
    
    private Spatial handle,handleHighlight;
    
    private Material handleHighlightMat;
    
    private boolean highlightVisible;
    
    private float[] angles;
    
    private Node node;

    public SinkHandle(Main main,AssetManager assetManager,int index,Sink parentSink){
        
        super(main,Vector3f.ZERO);
        
        this.main=main;
        this.assetManager=assetManager;
        this.index=index;
        this.parentSink=parentSink;
        
        node=new Node();
        
        handleHighlight=assetManager.loadModel("Models/Static/Sink/Sink_Handle_Highlight.j3o");
        handleHighlight.setName("Cold sink handle highlight");
        handleHighlight.setLocalTranslation(0,-5,0);
        handleHighlightMat=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        handleHighlightMat.setColor("Color",Main.HIGHLIGHT_VISIBLE);
        handleHighlightMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        handleHighlight.setQueueBucket(RenderQueue.Bucket.Translucent);
        handleHighlight.setMaterial(handleHighlightMat);
        node.attachChild(handleHighlight);
        
        parentSink.getNode().attachChild(node);
        
        if(index==0){
            
            handle=assetManager.loadModel("Models/Static/Sink/Sink_Handle_Cold.j3o");
            handle.setName("Cold sink handle");
            handle.setUserData("correctCollision", true);
            handle.setUserData("correspondingObject", this);
            node.attachChild(handle);
            
            if(parentSink.getIndex()==0)
            
                node.setLocalTranslation(0.3f,0.03f,0.06f);
            
            else if(parentSink.getIndex()==1)
                
                node.setLocalTranslation(-0.3f,0.03f,0.06f);
                
        }else if(index==1){
            
            handle=assetManager.loadModel("Models/Static/Sink/Sink_Handle_Hot.j3o");
            handle.setName("Cold sink handle");
            handle.setUserData("correctCollision", true);
            handle.setUserData("correspondingObject", this);
            node.setLocalRotation(new Quaternion().fromAngleAxis(FastMath.DEG_TO_RAD*180, Vector3f.UNIT_Y));
            node.attachChild(handle);
            
            
            if(parentSink.getIndex()==0)
            
                node.setLocalTranslation(0.3f,0.03f,-0.06f);
            
            else if(parentSink.getIndex()==1)
                
                node.setLocalTranslation(-0.3f,0.03f,-0.06f);
            
        }
        
        main.getItemsList().add(this);
        
    }
    
    public void setRotation(Quaternion angle){
        
        //angles=new float[4];
        
        angle.toAngles(angles);
        
        //SET THE WATER OUTPUT FROM THE PARTICLEEMITTER ACCORDINGLY
        
    }
    
    @Override
    public String getDescription() {
        
        return "A sink handle";
        
    }

    @Override
    public void highlightVisible(boolean highlightVisible) {
        
        this.highlightVisible=highlightVisible;
        
        if(highlightVisible){
            
            handleHighlight.setLocalTranslation(Vector3f.ZERO);
            
        }else{
            
            handleHighlight.setLocalTranslation(0,-5,0);
            
        }
        
    }

    @Override
    public Vector3f getGrabbablePosition() {
        
        return node.getWorldTranslation().add(0,0.04f,0);
        
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
        
        return "Sink handle";
        
    }
    
}
