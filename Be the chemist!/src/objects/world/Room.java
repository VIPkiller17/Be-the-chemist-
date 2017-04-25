/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.world;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import java.io.IOException;
import main.Main;
//by Tommy
public class Room implements Savable{
    
    private CollisionShape roomCollisionShape;
    private CollisionShape furnitureCollisionShape;
    
    private RigidBodyControl room_phy,furniture_phy;
    
    private Spatial room,furniture;
    
    public Room(Main main){
        
        room = main.getAssetManager().loadModel("Models/Static/Room/Room.j3o");
        room.setName("Room");
        room.setUserData("correctCollision", true);
        room.setUserData("correspondingObject", this);
        room.setShadowMode(ShadowMode.Receive);
        
        furniture = main.getAssetManager().loadModel("Models/Static/Room/Furniture/Room_Furniture.j3o");
        furniture.setName("Furniture");
        furniture.setUserData("correctCollision", true);
        furniture.setUserData("correspondingObject", this);
        furniture.setShadowMode(ShadowMode.CastAndReceive);
        
        roomCollisionShape=CollisionShapeFactory.createMeshShape(room);
        furnitureCollisionShape=CollisionShapeFactory.createMeshShape(furniture);
        
        room_phy=new RigidBodyControl(roomCollisionShape,0);
        room.addControl(room_phy);
        main.getBulletAppState().getPhysicsSpace().add(room_phy);
        
        furniture_phy=new RigidBodyControl(furnitureCollisionShape,0);
        furniture.addControl(furniture_phy);
        main.getBulletAppState().getPhysicsSpace().add(furniture_phy);
        
        main.getRootNode().attachChild(room);
        main.getRootNode().attachChild(furniture);
        
        room_phy.setPhysicsLocation(new Vector3f(0,1.525f,0));
        furniture_phy.setPhysicsLocation(new Vector3f(0,0.6f,1.25f));
        
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
