/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.apparatus.trashBin;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.export.Savable;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import main.Main;
import objects.apparatus.Apparatus;

/**
 *
 * @author VIPkiller17
 */
public class TrashBin extends Apparatus implements Savable{

    private Main main;
    private Spatial spatial;
    private Node node;
    
    private CollisionShape cs;
    private RigidBodyControl phy;
    
    
    public TrashBin(Main main){
        
        super(main,Vector3f.ZERO);
        
        this.main=main;
        node=new Node();
        
        spatial=main.getAssetManager().loadModel("Models/Static/TrashBin/TrashBin.j3o");
        spatial.setName("Trash bin");
        spatial.setUserData("correctCollision", true);
        spatial.setUserData("correspondingObject", this);
        spatial.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        node.attachChild(spatial);
        
        main.getRootNode().attachChild(node);
        
        cs=CollisionShapeFactory.createMeshShape(spatial);
        
        phy=new RigidBodyControl(cs,0);
        spatial.addControl(phy);
        main.getBulletAppState().getPhysicsSpace().add(phy);
        
        phy.setPhysicsLocation(new Vector3f(4.5f,0.36f,-4));
        
    }
    
    @Override
    public String getDescription() {
        
        return "The trash bin";
        
    }

    @Override
    public void setPos(Vector3f position) {
        
        spatial.setLocalTranslation(position);
        
    }
    
    @Override
    public Node getNode() {
        
        return node;
        
    }
    
    @Override
    public String getName() {
        
        return "Trash bin";
        
    }
    
    public Spatial getSpatial(){
        
        return spatial;
        
    }
    
    @Override
    public void destroy() {
        
        main.getRootNode().detachChild(node);        
    }
    
}
