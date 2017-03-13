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
public class Room implements Savable{
    
    private AssetManager assetManager;
    private Node rootNode;
    
    private Spatial room,furniture;
    private Material mat;
    private RigidBodyControl room_phy;
    
    public Room(AssetManager assetManager,Node rootNode,BulletAppState bulletAppState){
        
        this.assetManager=assetManager;
        this.rootNode=rootNode;
        
        room = assetManager.loadModel("Models/Static/Room/Room.j3o");
        room.setName("Room");
        room.scale(1f,1f,1f);
        room.rotate(0.0f, 0.0f, 0.0f);
        room.setLocalTranslation(0f,0f,0f);
        room.setUserData("correctCollision", true);
        room.setUserData("correspondingObject", this);
        room.setShadowMode(ShadowMode.Receive);
        
        furniture = assetManager.loadModel("Models/Static/Room/Room.j3o");
        furniture.setName("Room");
        furniture.scale(1f,1f,1f);
        furniture.rotate(0.0f, 0.0f, 0.0f);
        furniture.setLocalTranslation(0f,0f,0f);
        furniture.setUserData("correctCollision", true);
        furniture.setUserData("correspondingObject", this);
        furniture.setShadowMode(ShadowMode.CastAndReceive);
        
        mat = new Material(assetManager, "jmevr/shaders/Unshaded.j3md"); 
        mat.setBoolean("UseMaterialColors",true); 
        room.setMaterial(mat);
        furniture.setMaterial(mat);
        
        room_phy=new RigidBodyControl(0);
        room.addControl(room_phy);
        bulletAppState.getPhysicsSpace().add(room_phy);
        
        rootNode.attachChild(room);
        rootNode.attachChild(furniture);
        
    }
    
    public Spatial getSpatial(){
        
        return room;
        
    }
    
    public void addControl(Control control){
        
        room.addControl(control);
        
    }

    public void write(JmeExporter je) throws IOException {
        
        
    }

    public void read(JmeImporter ji) throws IOException {
        
        
    }
    
}
