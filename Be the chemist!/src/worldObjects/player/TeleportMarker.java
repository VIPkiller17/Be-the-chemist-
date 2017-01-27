/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package worldObjects.player;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author etien
 */
public class TeleportMarker {
    
    private AssetManager assetManager;
    private String modelPath="Models/Player/TeleportMarker.obj";
    private Spatial spatial=assetManager.loadModel(modelPath);
    
    public TeleportMarker(Vector3f location, AssetManager assetManager, Node rootNode) {
        
        spatial.setLocalTranslation(location);
        this.assetManager=assetManager;
        rootNode.attachChild(spatial);
        
    }
        
    public void setSpatialLocation(Vector3f location) {
            
        spatial.setLocalTranslation(location);
        
   }
    
}
