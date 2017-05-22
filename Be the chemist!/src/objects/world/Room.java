/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.world;

import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
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
    
    private RigidBodyControl room_phy,furniture_phy;
    
    private Spatial room,furniture;
    
    private Main main;
    
    public Room(Main main){
        
        this.main=main;
        
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
        
        room_phy=new RigidBodyControl(roomCollisionShape,0);
        room.addControl(room_phy);
        main.getBulletAppState().getPhysicsSpace().add(room_phy);
        
        main.getRootNode().attachChild(room);
        main.getRootNode().attachChild(furniture);
        
        room_phy.setPhysicsLocation(new Vector3f(0,1.525f,0));
        
        initCorrecterCollisionShapes();
        
        furniture_phy.setPhysicsLocation(new Vector3f(0,0.6f,1.25f));
        
    }
    
    public Spatial getSpatial(){
        
        return room;
        
    }
    
    public void addControl(Control control){
        
        room.addControl(control);
        
    }

    @Override
    public void write(JmeExporter je) throws IOException {
        
        
    }

    @Override
    public void read(JmeImporter ji) throws IOException {
        
        
    }
    
    public void initCorrecterCollisionShapes(){
        
        CompoundCollisionShape comp=new CompoundCollisionShape();
        
        comp.addChildShape(CollisionShapeFactory.createMeshShape(furniture), Vector3f.ZERO);
        
        comp.addChildShape(new BoxCollisionShape(new Vector3f(1f,0.02f,0.875f)),new Vector3f(-0.4f,0.31f,-0.42f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(1f,0.02f,0.875f)),new Vector3f(-0.4f,0.31f,-2.66f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.085f,0.02f,0.25f)),new Vector3f(0.51f,0.31f,-1.54f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.085f,0.02f,0.25f)),new Vector3f(-1.32f,0.31f,-1.54f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.355f,0.02f,0.25f)),new Vector3f(-0.4f,0.31f,-1.54f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.235f,0.005f,0.25f)),new Vector3f(0.19f,0.06f,-1.54f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.235f,0.005f,0.25f)),new Vector3f(-1f,0.06f,-1.54f));
        
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.5f,0.02f,4.75f)),new Vector3f(4.5f,0.31f,0f));
        
        comp.addChildShape(new BoxCollisionShape(new Vector3f(3.44f,0.02f,0.5f)),new Vector3f(-1.56f,0.31f,4.25f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.5f,0.02f,0.7f)),new Vector3f(-4.5f,0.31f,3.05f));
        
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.23f,0.01f,0.25f)),new Vector3f(-1.07f,-0.16f,-3.13f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.23f,0.01f,0.25f)),new Vector3f(0.27f,-0.16f,-3.13f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.23f,0.01f,0.25f)),new Vector3f(-1.07f,-0.16f,-2.53f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.23f,0.01f,0.25f)),new Vector3f(0.27f,-0.16f,-2.53f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.23f,0.01f,0.25f)),new Vector3f(-1.07f,-0.16f,-0.55f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.23f,0.01f,0.25f)),new Vector3f(0.27f,-0.16f,-0.55f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.23f,0.01f,0.25f)),new Vector3f(-1.07f,-0.16f,0.05f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.23f,0.01f,0.25f)),new Vector3f(0.27f,-0.16f,0.05f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.25f,0.005f,0.25f)),new Vector3f(-1.08f,-0.505f,-3.13f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.25f,0.005f,0.25f)),new Vector3f(0.28f,-0.505f,-3.13f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.25f,0.005f,0.25f)),new Vector3f(-1.08f,-0.505f,-2.53f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.25f,0.005f,0.25f)),new Vector3f(0.28f,-0.505f,-2.53f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.25f,0.005f,0.25f)),new Vector3f(-1.08f,-0.505f,-0.55f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.25f,0.005f,0.25f)),new Vector3f(0.28f,-0.505f,-0.55f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.25f,0.005f,0.25f)),new Vector3f(-1.08f,-0.505f,0.05f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.25f,0.005f,0.25f)),new Vector3f(0.28f,-0.505f,0.05f));
        
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.25f,0.01f,0.23f)),new Vector3f(-3.71f,-0.16f,4.08f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.25f,0.01f,0.23f)),new Vector3f(-3.11f,-0.16f,4.08f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.25f,0.01f,0.23f)),new Vector3f(-2.51f,-0.16f,4.08f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.25f,0.01f,0.23f)),new Vector3f(-1.87f,-0.16f,4.08f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.25f,0.01f,0.23f)),new Vector3f(-1.27f,-0.16f,4.08f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.25f,0.01f,0.23f)),new Vector3f(1.53f,-0.16f,4.08f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.5f,0.01f,0.23f)),new Vector3f(-0.42f,-0.16f,4.08f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.5f,0.01f,0.23f)),new Vector3f(0.68f,-0.16f,4.08f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.25f,0.005f,0.25f)),new Vector3f(-3.71f,-0.505f,4.06f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.25f,0.005f,0.25f)),new Vector3f(-3.11f,-0.505f,4.06f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.25f,0.005f,0.25f)),new Vector3f(-2.51f,-0.505f,4.06f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.25f,0.005f,0.25f)),new Vector3f(-1.87f,-0.505f,4.06f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.25f,0.005f,0.25f)),new Vector3f(-1.27f,-0.505f,4.06f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.25f,0.005f,0.25f)),new Vector3f(1.53f,-0.505f,4.06f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.5f,0.005f,0.25f)),new Vector3f(-0.42f,-0.505f,4.06f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.5f,0.005f,0.25f)),new Vector3f(0.68f,-0.505f,4.06f));
        
        furniture_phy=new RigidBodyControl(comp,0);
        furniture.addControl(furniture_phy);
        main.getBulletAppState().getPhysicsSpace().add(furniture_phy);
        
    }
    
}
