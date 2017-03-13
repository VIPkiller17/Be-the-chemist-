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
import com.jme3.material.Material;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import java.io.IOException;
//by Tommy
public class Floor implements Savable{
    
    private AssetManager assetManager;
    private Node rootNode;
    
    private Spatial spatial;
    private Material mat;
    private RigidBodyControl floor_phy;
    
    public Floor(AssetManager assetManager,Node rootNode,BulletAppState bulletAppState){
        
        this.assetManager=assetManager;
        this.rootNode=rootNode;
        
        spatial = assetManager.loadModel("Models/Static/Floor/Floor.j3o");
        spatial.scale(1f,1f,1f);
        spatial.rotate(0.0f, 0.0f, 0.0f);
        spatial.setLocalTranslation(0f,0f,0f);
        spatial.setName("Floor");
        spatial.setUserData("correctCollision", true);
        spatial.setUserData("correspondingObject", this);
        spatial.setShadowMode(ShadowMode.Receive);
        
        mat = new Material(assetManager, "jmevr/shaders/unshaded.j3md"); 
        mat.setBoolean("UseMaterialColors", true);
        spatial.setMaterial(mat);
        
        floor_phy=new RigidBodyControl(0);
        spatial.addControl(floor_phy);
        bulletAppState.getPhysicsSpace().add(floor_phy);
        
        rootNode.attachChild(spatial);
        
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
