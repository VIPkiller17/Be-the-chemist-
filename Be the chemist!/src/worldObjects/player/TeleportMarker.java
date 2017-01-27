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
    private String modelPath;
    private Spatial spatial;
    
    public TeleportMarker(Vector3f location, AssetManager assetManager, Node rootNode) {
        
        modelPath="Models/Logic/TeleportMarker/TeleportMarker.j3o";
        spatial=assetManager.loadModel(modelPath);
        spatial.setLocalTranslation(location);
        this.assetManager=assetManager;
        rootNode.attachChild(spatial);
        
    }
        
    public void setSpatialLocation(Vector3f location) {
            
        spatial.setLocalTranslation(location);
        
   }
    
}
