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
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import java.io.IOException;
//by Tommy
public class Room implements Savable{
    
    private AssetManager assetManager;
    private Node rootNode;
    
    private Spatial spatial;
    private Material mat;
    private RigidBodyControl room_phy;
    
    public Room(AssetManager assetManager,Node rootNode,BulletAppState bulletAppState){
        
        this.assetManager=assetManager;
        this.rootNode=rootNode;
        
        spatial = assetManager.loadModel("Models/Static/Room/Room.j3o");
        spatial.setName("Room");
        spatial.scale(1f,1f,1f);
        spatial.rotate(0.0f, 0.0f, 0.0f);
        spatial.setLocalTranslation(0f,0f,0f);
        spatial.setUserData("correctCollision", true);
        spatial.setUserData("correspondingObject", this);
        
        //mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md"); 
        //mat.setBoolean("UseMaterialColors",true); 
        //mat.setColor("Ambient",ColorRGBA.Gray); 
        //mat.setColor("Diffuse", ColorRGBA.Gray);
        //mat.setColor("Specular", ColorRGBA.Gray);
        //spatial.setMaterial(mat);
        
        room_phy=new RigidBodyControl(0);
        spatial.addControl(room_phy);
        bulletAppState.getPhysicsSpace().add(room_phy);
        
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
