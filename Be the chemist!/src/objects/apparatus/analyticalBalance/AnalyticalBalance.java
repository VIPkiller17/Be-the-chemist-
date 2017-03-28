/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.apparatus.analyticalBalance;

import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import main.Main;
import objects.PhysicalObject;
import objects.apparatus.Apparatus;

/**
 *
 * @author VIPkiller17
 */
public class AnalyticalBalance extends Apparatus {
    
    private AnalyticalBalanceControl analyticalBalanceControl;
    private PhysicalObject attachedObject;
    private Spatial spatial;
    private Node node;
    
    private BitmapText text;
    private BitmapFont font;
    private Spatial analyticalBalanceSurface;
    
    private CollisionResults collisionResults;
    
    
    public AnalyticalBalance(Main main,Node rootNode,CollisionResults collisionResults, AssetManager assetManager, Vector3f position) {
       
        super(main,position);
        
        node = new Node();
        
        spatial=assetManager.loadModel("Models/Static/AnalyticalBalance/AnalyticalBalance.j3o");
        font=assetManager.loadFont("Interface/Fonts/Xolonium/Xolonium.fnt");
        text = new BitmapText(font);
        
        Quad display = new Quad(0.05f, 0.02f);
        Geometry geom = new Geometry("Analytical Balance Display", display);
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        material.setColor("Color", ColorRGBA.Black);
        geom.setMaterial(material);
       
        analyticalBalanceSurface = assetManager.loadModel("Models/Static/AnalyticalBalance/AnalyticalBalance_Surface.j3o");
        Material analyticalBalanceSurfaceMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        analyticalBalanceSurfaceMat.setColor("Color", new ColorRGBA(0, 0, 0, 0));
        analyticalBalanceSurface.setMaterial(analyticalBalanceSurfaceMat);
        analyticalBalanceSurfaceMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        analyticalBalanceSurface.setQueueBucket(RenderQueue.Bucket.Translucent);
        node.attachChild(analyticalBalanceSurface);
        
        node.attachChild(geom);
        
        geom.setLocalRotation(Quaternion.ZERO); //*
        
        spatial.setLocalRotation(Quaternion.ZERO); //*
        spatial.setUserData("correctCollision", true);
        spatial.setUserData("correspondingObject", this);
        node.attachChild(spatial);
        
        node.setLocalTranslation(position);
        rootNode.attachChild(node);
        node.setLocalRotation(new Quaternion().fromAngleAxis(FastMath.PI*3/4, Vector3f.UNIT_Y));
        
        this.collisionResults = collisionResults;
        
    }
    
    public boolean attachObject(PhysicalObject object) {
        
        if (attachedObject != null) 
            return false;
        else {
            attachedObject = object;
        
            text.setText(object.getMass() + "");//Get mass Not written yet
        
            return true;
        }
        
        
    }
    
    public void detachObject() {
        
        attachedObject = null;
        text.setText("");
        
    }
    
    public PhysicalObject getAttachedObject() {
        
        return attachedObject;
       
    }
    
    public double getMeasuredMass() {
        
        if (attachedObject==null)
            return 0;
        else
            return attachedObject.getMass();
        
        
    }
    
    public Spatial getSpatial() {
        return spatial;
    }
    
    public Spatial getSurface() {
        return analyticalBalanceSurface;
    }
    
    @Override
    public String toString() {
        return "An analytical balance";  
    }
    
    public BitmapText getDisplayText() {
        return text;
    }
    
    @Override
    public boolean equals(Object object) {
        
        if (object instanceof AnalyticalBalance) 
            return spatial.getLocalTranslation().equals(((AnalyticalBalance) object).getSpatial().getLocalTranslation());
        else
            return false;
    }

    @Override
    public String getDescription() {
        
        return "An analytical balance";
        
    }
   
    
}
