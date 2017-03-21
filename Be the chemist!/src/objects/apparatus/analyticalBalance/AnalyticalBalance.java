/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.apparatus.analyticalBalance;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
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
    
    public AnalyticalBalance(Node rootNode, AssetManager assetManager, Vector3f position) {
        
        super(position);
        
        spatial=assetManager.loadModel("Models/Objects/Apparatus/AnalyticalBalance/AnalyticalBalance.j3o");
        font=assetManager.loadFont("Interface/Fonts/Xolonium/Xolonium.fnt");
        text = new BitmapText(font);
        
        Quad display = new Quad(0.05f, 0.02f);
        Geometry geom = new Geometry("Analytical Balance Display", display);
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        material.setColor("Color", ColorRGBA.Black);
        geom.setMaterial(material);
        
        node.attachChild(geom);
        geom.setLocalTranslation(position); //*
        geom.setLocalRotation(Quaternion.ZERO); //*
        
        spatial.setLocalRotation(Quaternion.ZERO); //*
        spatial.setLocalTranslation(position); //*
        spatial.setUserData("correctCollision", true);
        spatial.setUserData("correspondingObject", this);
        node.attachChild(spatial);
        
        rootNode.attachChild(node);
        
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
    
    public double getMass() {
        
        if (attachedObject.getMass == null)
            return 0.0;
        else
            return attachedObject.getMass();
        
        
    }
    
    public AnalyticalBalanceDisplay getAnalyticalBalanceDisplay() {
        
        return analyticalBalanceDisplay;
        
    }
    
    public Spatial getSpatial() {
        return spatial;
    }
    
    @Override
    public String toString() {
        return analyticalBalance;  
    }
    
    @Override
    public boolean equals(Object object) {
        
        if (object instanceof AnalyticalBalance) 
            return spatial.getLocalTranslation().equals(((AnalyticalBalance) object).getSpatial().getLocalTranslation());
        else
            return false;
    }
   
    
}
