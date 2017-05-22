/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.apparatus.fumeHood;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
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
    
    private CompoundCollisionShape cs;
    private RigidBodyControl phy;
    
    private Node node;
    
    private Main main;
    
    public FumeHoodDoor(Main main,FumeHood fumeHood,AssetManager assetManager,Node rootNode){
        
        super(main,new Vector3f(6.72f,1.02f,10.18f));
        
        node=new Node();
        
        this.main=main;
        
        this.fumeHood=fumeHood;
        
        spatial=assetManager.loadModel("Models/Static/FumeHood/FumeHoodDoor.j3o");
        spatial.rotate(new Quaternion().fromAngleAxis(FastMath.PI, Vector3f.UNIT_Y));
        spatial.setName("Fume hood");
        spatial.setUserData("correctCollision", true);
        spatial.setUserData("correspondingObject", this);
        spatial.setQueueBucket(RenderQueue.Bucket.Translucent);
        fumeHood.getNode().attachChild(spatial);
        //spatial.setLocalTranslation(0.0f,-0.5f, -0.485f);
        
        handleHighlight=assetManager.loadModel("Models/Static/FumeHood/FumeHoodDoor_Highlight.j3o");
        handleHighlight.rotate(new Quaternion().fromAngleAxis(FastMath.PI, Vector3f.UNIT_Y));
        handleHighlight.setLocalTranslation(0,-20,0);
        handleHighlight.setName("Fume hood door handle highlight");
        handleHighlight.setUserData("correctCollision", true);
        handleHighlight.setUserData("correspondingObject", this);
        handleHighlightMat=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        handleHighlightMat.setColor("Color",Main.HIGHLIGHT_VISIBLE);
        handleHighlightMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        handleHighlight.setQueueBucket(RenderQueue.Bucket.Transparent);
        handleHighlight.setMaterial(handleHighlightMat);
        fumeHood.getNode().attachChild(handleHighlight);
        
        main.getItemsList().add(this);
        
        cs=new CompoundCollisionShape();
        
        cs.addChildShape(new BoxCollisionShape(new Vector3f(0.99f,0.53f,0.065f)),new Vector3f(0,0.49f,-0.03f));
        
        phy=new RigidBodyControl(cs,0);
        spatial.addControl(phy);
        main.getBulletAppState().getPhysicsSpace().add(phy);
        
        phy.setPhysicsLocation(new Vector3f(2.97f,1f,5.04f));
        
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
        
        if(isHighlightVisible){
            
            handleHighlight.setLocalTranslation(phy.getPhysicsLocation().subtract(0, 0.001f, 0));
        
            //handleHighlightMat.setColor("Color",Main.HIGHLIGHT_VISIBLE);
        
        }else{
            
            handleHighlight.setLocalTranslation(0,-20,0);
            
            //handleHighlightMat.setColor("Color",Main.HIGHLIGHT_INVISIBLE);
            
        }
        
    }

    @Override
    public boolean isHighlightVisible() {
        
        return isHighlightVisible;
        
    }

    @Override
    public Vector3f getGrabbablePosition() {
        
        return phy.getPhysicsLocation();
        
    }
    
    @Override
    public void setPos(Vector3f position) {
        
        phy.setPhysicsLocation(position);
        
    }
    
    @Override
    public Node getNode() {
        
        return node;
        
    }
    
    @Override
    public String getName() {
        
        return "Fume hood door";
        
    }

    @Override
    public Spatial getSpatial() {
        
        return spatial;
        
    }
    
    @Override
    public void destroy() {
        
        main.getItemsList().remove(this);
        
    }
    
}
