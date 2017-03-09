/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.world;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import java.io.IOException;
//by Tommy
public class Floor implements Savable{
    
    private AssetManager assetManager;
    private Node rootNode;
    
    private Spatial spatial;
    private RigidBodyControl floor_phy;
    
    public Floor(AssetManager assetManager,Node rootNode,BulletAppState bulletAppState){
        
        this.assetManager=assetManager;
        this.rootNode=rootNode;
        
        spatial = assetManager.loadModel("Models/Static/Floor/Floor_Final.j3o");
        spatial.scale(1f,1f,1f);
        spatial.rotate(0.0f, 0.0f, 0.0f);
        spatial.setLocalTranslation(0f,0f,0f);
        spatial.setName("Floor");
        spatial.setUserData("correctCollision", true);
        spatial.setUserData("correspondingObject", this);
        rootNode.attachChild(spatial);
        
        floor_phy=new RigidBodyControl(0);
        spatial.addControl(floor_phy);
        bulletAppState.getPhysicsSpace().add(floor_phy);
        
    }
    
    public Spatial getSpatial(){
        
        return spatial;
        
    }
    
    public void addControl(Control control){
        
        spatial.addControl(control);
        
    }

    public void write(JmeExporter je) throws IOException {
        
        
    }

    public void read(JmeImporter ji) throws IOException {
        
        
    }
    
}
