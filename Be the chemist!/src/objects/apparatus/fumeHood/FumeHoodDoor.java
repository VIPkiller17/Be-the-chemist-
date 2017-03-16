/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.apparatus.fumeHood;

import com.jme3.asset.AssetManager;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.io.IOException;
import objects.apparatus.Apparatus;

/**
 *
 * @author VIPkiller17
 */
public class FumeHoodDoor extends Apparatus implements Savable{
    
    private FumeHood fumeHood;
    
    private FumeHoodDoorControl fumeHoodDoorControl;
    private double height;
    
    private Spatial spatial;
    private Spatial handleHighlight;
    
    public FumeHoodDoor(FumeHood fumeHood,AssetManager assetManager,Node rootNode){
        
        super(new Vector3f(6.72f,1.02f,10.18f));
        
        this.fumeHood=fumeHood;
        
        spatial=assetManager.loadModel("Models/Static/FumeHood/FumeHoodDoor.j3o");
        //spatial.scale(1f,1f,1f);
        spatial.rotate(new Quaternion().fromAngleAxis(FastMath.PI, Vector3f.UNIT_Y));
        spatial.setLocalTranslation(6.72f,1.02f,10.18f);
        spatial.setName("Fume hood");
        spatial.setUserData("correctCollision", true);
        spatial.setUserData("correspondingObject", this);
        fumeHood.attachObject(spatial);
        
        handleHighlight=assetManager.loadModel("Models/Static/FumeHood/FumeHoodDoor.j3o");
        //spatial.scale(1f,1f,1f);
        handleHighlight.rotate(new Quaternion().fromAngleAxis(FastMath.PI, Vector3f.UNIT_Y));
        handleHighlight.setLocalTranslation(6.72f,1.02f,10.18f);
        handleHighlight.setName("Fume hood");
        handleHighlight.setUserData("correctCollision", true);
        handleHighlight.setUserData("correspondingObject", this);
        fumeHood.attachObject(handleHighlight);
        
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
    
}
