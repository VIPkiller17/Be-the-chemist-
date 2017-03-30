/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.world;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.PlaneCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.math.Plane;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import java.io.IOException;
import main.Main;
//by Tommy
public class Floor implements Savable{
    
    private Spatial spatial;
    private RigidBodyControl floor_phy;
    
    private Plane plane;
    private PlaneCollisionShape collisionShape;
    
    public Floor(Main main){
        
        spatial = main.getAssetManager().loadModel("Models/Static/Floor/Floor.j3o");
        spatial.setName("Floor");
        spatial.setUserData("correctCollision", true);
        spatial.setUserData("correspondingObject", this);
        
        plane=new Plane(new Vector3f(0,1,0),0);
        collisionShape=new PlaneCollisionShape(plane);
        System.out.println(collisionShape.getScale());
        
        floor_phy=new RigidBodyControl(collisionShape,0);
        spatial.addControl(floor_phy);
        main.getBulletAppState().getPhysicsSpace().add(floor_phy);
        
        main.getRootNode().attachChild(spatial);
        
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
