/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.apparatus.fumeHood;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import main.Main;
import objects.PhysicalObject;
import objects.apparatus.Apparatus;

/**
 *
 * @author VIPkiller17
 */
public class FumeHood extends Apparatus{

    private Node node;
    private FumeHoodDoor fumeHoodDoor;
    private FumeHoodControl fumeHoodControl;
    private double efficiency;
    private ArrayList<PhysicalObject> attachedObjects;
    
    private Spatial spatial;
    
    private RigidBodyControl phy;
    
    private Main main;
    
    public FumeHood(Main main,AssetManager assetManager,Node rootNode){
        
        super(main,Vector3f.ZERO);
        
        this.main=main;
        node=new Node();
        
        spatial=assetManager.loadModel("Models/Static/FumeHood/FumeHood.j3o");
        //spatial.scale(1f,1f,1f);
        //spatial.rotate(0.0f, 0.0f, 0.0f);
        //spatial.setLocalTranslation(position);
        spatial.setName("Fume hood");
        spatial.setUserData("correctCollision", true);
        spatial.setUserData("correspondingObject", this);
        spatial.setShadowMode(ShadowMode.CastAndReceive);
        node.attachChild(spatial);
        
        fumeHoodDoor=new FumeHoodDoor(main,this,assetManager,rootNode);
        
        rootNode.attachChild(node);
        
        initCorrecterCollisionShapes();
        
        phy.setPhysicsLocation(new Vector3f(2.97f,1.5f,5.47f));
        
    }
    
    @Override
    public String getDescription() {
        
        return "The fume hood.";
        
    }

    @Override
    public void setPos(Vector3f position) {
        
        node.setLocalTranslation(position);
        
    }
    
    @Override
    public void attachObject(Spatial object){
        
        node.attachChild(object);
        
    }
    
    @Override
    public Node getNode() {
        
        return node;
        
    }
    
    @Override
    public String getName() {
        
        return "Fume hood";
        
    }
    
    public Spatial getSpatial(){
        
        return spatial;
        
    }
    
    public void initCorrecterCollisionShapes(){
        
        CompoundCollisionShape comp=new CompoundCollisionShape();
        
        comp.addChildShape(CollisionShapeFactory.createMeshShape(spatial),Vector3f.ZERO);
        
        comp.addChildShape(new BoxCollisionShape(new Vector3f(1.09f,0.005f,0.47f)),new Vector3f(0f,1.495f,0f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.99f,0.005f,0.375f)),new Vector3f(0f,-0.52f,-0.09f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.99f,0.005f,0.23f)),new Vector3f(0f,-0.95f,-0.2f));
        comp.addChildShape(new BoxCollisionShape(new Vector3f(0.99f,0.005f,0.25f)),new Vector3f(0f,-1.4f,-022f));
        
        phy=new RigidBodyControl(comp,0);
        spatial.addControl(phy);
        main.getBulletAppState().getPhysicsSpace().add(phy);
        
    }
    
}
