/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.apparatus.fumeHood;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import objects.PhysicalObject;
import objects.apparatus.Apparatus;

/**
 *
 * @author VIPkiller17
 */
public class FumeHood extends Apparatus{

    private Node node;
    private FumeHoodDoor fumeHoodDoor;
    private FumeHoodControl fumeHoodControl;
    private double efficiency;
    private ArrayList<PhysicalObject> attachedObjects;
    
    private Spatial spatial;
    
    public FumeHood(AssetManager assetManager,Node rootNode){
        
        super(Vector3f.ZERO);
        
        spatial=assetManager.loadModel("Models/Static/FumeHood/FumeHood.j3o");
        //spatial.scale(1f,1f,1f);
        //spatial.rotate(0.0f, 0.0f, 0.0f);
        //spatial.setLocalTranslation(position);
        spatial.setName("Fume hood");
        spatial.setUserData("correctCollision", true);
        spatial.setUserData("correspondingObject", this);
        attachObject(spatial);
        
        fumeHoodDoor=new FumeHoodDoor(this,assetManager,rootNode);
        
        rootNode.attachChild(getNode());
        
    }
    
    @Override
    public String getDescription() {
        
        return "The fume hood.";
        
    }
    
}
