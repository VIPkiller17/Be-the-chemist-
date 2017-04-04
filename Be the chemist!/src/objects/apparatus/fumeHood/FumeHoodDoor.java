/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.apparatus.fumeHood;

import com.jme3.asset.AssetManager;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import interfaces.Grabbable;
import java.io.IOException;
import objects.apparatus.Apparatus;
import main.Main;

/**
 *
 * @author VIPkiller17
 */
public class FumeHoodDoor extends Apparatus implements Savable, Grabbable{
    
    private FumeHood fumeHood;
    
    private FumeHoodDoorControl fumeHoodDoorControl;
    private double height;
    
    private Spatial spatial;
    private Spatial handleHighlight;
    private Material handleHighlightMat;
    private boolean isHighlightVisible;
    
    private Node node;
    
    public FumeHoodDoor(Main main,FumeHood fumeHood,AssetManager assetManager,Node rootNode){
        
        super(main,new Vector3f(6.72f,1.02f,10.18f));
        
        node=new Node();
        
        this.fumeHood=fumeHood;
        
        spatial=assetManager.loadModel("Models/Static/FumeHood/FumeHoodDoor.j3o");
        //spatial.scale(1f,1f,1f);
        spatial.rotate(new Quaternion().fromAngleAxis(FastMath.PI, Vector3f.UNIT_Y));
        spatial.setLocalTranslation(6.72f,1.02f,10.18f);
        spatial.setName("Fume hood");
        spatial.setUserData("correctCollision", true);
        spatial.setUserData("correspondingObject", this);
        spatial.setQueueBucket(RenderQueue.Bucket.Translucent);
        node.attachChild(spatial);
        
        handleHighlight=assetManager.loadModel("Models/Static/FumeHood/FumeHoodDoor_Highlight.j3o");
        //spatial.scale(1f,1f,1f);
        handleHighlight.rotate(new Quaternion().fromAngleAxis(FastMath.PI, Vector3f.UNIT_Y));
        handleHighlight.setLocalTranslation(6.72f,1.02f,10.18f);
        handleHighlight.setName("Fume hood door handle highlight");
        handleHighlight.setUserData("correctCollision", true);
        handleHighlight.setUserData("correspondingObject", this);
        handleHighlightMat=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        handleHighlightMat.setColor("Color",Main.HIGHLIGHT_INVISIBLE);
        handleHighlightMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        handleHighlight.setQueueBucket(RenderQueue.Bucket.Transparent);
        handleHighlight.setMaterial(handleHighlightMat);
        node.attachChild(handleHighlight);
        
        fumeHood.getNode().attachChild(node);
        
        main.getItemsList().add(this);
        
    }

    @Override
    public void write(JmeExporter je) throws IOException {
    }

    @Override
    public void read(JmeImporter ji) throws IOException {
    }

    @Override
    public String getDescription() {
        
        return "The fume hood's door.";
        
    }

    @Override
    public void highlightVisible(boolean isHighlightVisible) {
        
        this.isHighlightVisible=isHighlightVisible;
        
        if(isHighlightVisible)
        
            handleHighlightMat.setColor("Color",Main.HIGHLIGHT_VISIBLE);
        
        else
            
            handleHighlightMat.setColor("Color",Main.HIGHLIGHT_INVISIBLE);
        
    }

    @Override
    public boolean isHighlightVisible() {
        
        return isHighlightVisible;
        
    }

    @Override
    public Vector3f getGrabbablePosition() {
        
        return node.getLocalTranslation();
        
    }
    
    @Override
    public void setPos(Vector3f position) {
        
        node.setLocalTranslation(position);
        
    }
    
    @Override
    public Node getNode() {
        
        return node;
        
    }
    
}
