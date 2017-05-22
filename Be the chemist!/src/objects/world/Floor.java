/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.world;

import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.PlaneCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.math.Plane;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import java.io.IOException;
import main.Main;
//by Tommy
public class Floor implements Savable{
    
    private Spatial spatial;
    private RigidBodyControl floor_phy;
    
    private BoxCollisionShape collisionShape;
    
    public Floor(Main main){
        
        spatial = main.getAssetManager().loadModel("Models/Static/Floor/Floor.j3o");
        spatial.setName("Floor");
        spatial.setUserData("correctCollision", true);
        spatial.setUserData("correspondingObject", this);
        spatial.setShadowMode(RenderQueue.ShadowMode.Receive);
        
        collisionShape=new BoxCollisionShape(new Vector3f(5f,0.04f,6f));
        
        floor_phy=new RigidBodyControl(collisionShape,0);
        spatial.addControl(floor_phy);
        main.getBulletAppState().getPhysicsSpace().add(floor_phy);
        
        main.getRootNode().attachChild(spatial);
        
        floor_phy.setPhysicsLocation(new Vector3f(0,-0.04f,0));
        
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
    
    @Override
    public String toString(){
        
        return "The floor";
        
    }
    
    @Override
    public boolean equals(Object otherFloor){
        
        if(otherFloor instanceof Floor)
        
            return ((Floor) otherFloor).getSpatial().getLocalTranslation().equals(spatial.getLocalTranslation());
        
        else
            
            return false;
        
    }
    
}
