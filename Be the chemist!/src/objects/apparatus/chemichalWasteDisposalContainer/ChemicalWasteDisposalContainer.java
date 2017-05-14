/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.apparatus.chemichalWasteDisposalContainer;

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
public class ChemicalWasteDisposalContainer extends Apparatus implements Savable{

    private Main main;
    private Spatial spatial;
    private Node rootNode;
    private Node node;
    
    private CollisionShape cs;
    private RigidBodyControl phy;
    
    public static final Vector3f OPENNING_POSITION=new Vector3f(4.19f,1.4f,-2);
    
    
    public ChemicalWasteDisposalContainer(Main main,AssetManager assetManager,Node rootNode){
        
        super(main,Vector3f.ZERO);
        
        this.main=main;
        this.rootNode=rootNode;
        node=new Node();
        
        spatial=assetManager.loadModel("Models/Static/ChemicalWasteDisposalContainer/ChemicalWasteDisposalContainer.j3o");
        spatial.setName("Chemical waste disposal container");
        spatial.setUserData("correctCollision", true);
        spatial.setUserData("correspondingObject", this);
        spatial.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        node.attachChild(spatial);
        
        rootNode.attachChild(node);
        
        cs=CollisionShapeFactory.createMeshShape(spatial);
        
        phy=new RigidBodyControl(cs,0);
        spatial.addControl(phy);
        main.getBulletAppState().getPhysicsSpace().add(phy);
        
        phy.setPhysicsLocation(new Vector3f(4.5f,1.18f,-2));
        phy.setPhysicsRotation(new Quaternion().fromAngleAxis(FastMath.DEG_TO_RAD*-90, Vector3f.UNIT_Y));
        
    }
    
    @Override
    public String getDescription() {
        
        return "The chemical waste disposal container";
        
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
        
        return "Chemical waste disposal container";
        
    }
    
    public Spatial getSpatial(){
        
        return spatial;
        
    }
    
    public Vector3f getOpenningPosition(){
        
        return OPENNING_POSITION;
        
    }
    
    @Override
    public void destroy() {
        
        main.getRootNode().detachChild(node);        
    }
    
}
