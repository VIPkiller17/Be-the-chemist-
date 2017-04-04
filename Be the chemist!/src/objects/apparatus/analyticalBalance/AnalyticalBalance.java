/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.apparatus.analyticalBalance;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.collision.CollisionResults;
import com.jme3.export.Savable;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import main.Main;
import objects.PhysicalObject;
import objects.apparatus.Apparatus;

/**
 *
 * @author VIPkiller17
 */
public class AnalyticalBalance extends Apparatus implements Savable{
    
    private AnalyticalBalanceControl analyticalBalanceControl;
    private PhysicalObject attachedObject;
    private Spatial spatial;
    private Node node;
    
    private BitmapText text;
    private BitmapFont font;
    private Spatial analyticalBalanceSurface;
    private Spatial highlightModel;
    
    private Vector3f presentPosition;
    private Quaternion presentRotation;
    
    private CollisionResults collisionResults;
    
    private CollisionShape analyticalBalanceCollisionShape;
    private RigidBodyControl analyticalBalance_phy;
    
    public AnalyticalBalance(Main main,Node rootNode,CollisionResults collisionResults, AssetManager assetManager, Vector3f position) {
       
        super(main,position);
        
        node = new Node();
        
        spatial=assetManager.loadModel("Models/Static/AnalyticalBalance/AnalyticalBalance.j3o");
        font=assetManager.loadFont("Interface/Fonts/Xolonium/Xolonium.fnt");
        text = new BitmapText(font);
       
        //Analytical Balance Surface
        analyticalBalanceSurface = assetManager.loadModel("Models/Static/AnalyticalBalance/AnalyticalBalance_Surface.j3o");
        Material analyticalBalanceSurfaceMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        analyticalBalanceSurfaceMat.setColor("Color", new ColorRGBA(0, 0, 0, 0)); //new ColorRGBA(0, 0, 0, 0)
        analyticalBalanceSurface.setMaterial(analyticalBalanceSurfaceMat);
        analyticalBalanceSurfaceMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        analyticalBalanceSurface.setQueueBucket(RenderQueue.Bucket.Translucent);
        node.attachChild(analyticalBalanceSurface);
        
        //Analytical Balance Display
        Quad display = new Quad(0.1f, 0.05f); 
        Geometry displayGeom = new Geometry("Analytical Balance Display", display);
        Material displayMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        displayMat.setColor("Color", ColorRGBA.Red);
        displayGeom.setMaterial(displayMat);
        displayMat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        displayGeom.setQueueBucket(RenderQueue.Bucket.Transparent);
        
        //Test
        displayGeom.rotate(new Quaternion().fromAngleAxis(FastMath.DEG_TO_RAD*270, Vector3f.UNIT_Y));
        displayGeom.rotate(new Quaternion().fromAngleAxis(FastMath.DEG_TO_RAD*-60, Vector3f.UNIT_X));
        displayGeom.setUserData("correctCollision",true);
        displayGeom.setUserData("correspondingObject", this);
        displayGeom.setLocalTranslation(-0.1f, 0.05f, -0.1f);
        
        node.attachChild(displayGeom);
        spatial.setLocalRotation(Quaternion.ZERO); 
        spatial.setUserData("correctCollision", true);
        spatial.setUserData("correspondingObject", this);
        node.attachChild(spatial);
        node.setLocalTranslation(position);
        
        //Collision 
        analyticalBalanceCollisionShape=CollisionShapeFactory.createMeshShape(spatial);
        analyticalBalance_phy=new RigidBodyControl(analyticalBalanceCollisionShape, 0f);
        spatial.addControl(analyticalBalance_phy);
        main.getBulletAppState().getPhysicsSpace().add(analyticalBalance_phy);
        this.collisionResults = collisionResults;
        
        rootNode.attachChild(node);
        
    }
    
    public boolean attachObject(PhysicalObject object) {
        
        if (attachedObject != null) 
            return false;
        else {
            attachedObject = object;
        
            text.setText(object.getMass() + "");//Get mass Not written yet
        
            return true;
        }
        
        
    }
    
    public void detachObject() {
        
        attachedObject = null;
        text.setText("");
        
    }
    
    public PhysicalObject getAttachedObject() {
        
        return attachedObject;
       
    }
    
    public double getMeasuredMass() {
        
        if (attachedObject==null)
            return 0;
        else
            return attachedObject.getMass();
        
        
    }
    
    public Spatial getSpatial() {
        return spatial;
    }
    
    public Spatial getSurface() {
        return analyticalBalanceSurface;
    }
    
    public BitmapText getDisplayText() {
        return text;
    }
    
    @Override
    public String toString() {
        return "An analytical balance";  
    }
    
    @Override
    public boolean equals(Object object) {
        
        if (object instanceof AnalyticalBalance) 
            return spatial.getLocalTranslation().equals(((AnalyticalBalance) object).getSpatial().getLocalTranslation());
        else
            return false;
    }
    
    public Node getNode(){
        
        return node;
        
    }

    @Override
    public String getDescription() {
        
        return "An analytical balance";
        
    }
    
    public void setPosition(Vector3f position){
        
        spatial.getControl(RigidBodyControl.class).setPhysicsLocation(position);
        node.setLocalTranslation(position);
        
        System.out.println("Analytical Balance position set to "+position);
        
        presentPosition=position;
        
    }
    
    public Vector3f getPosition(){
        
        return spatial.getControl(RigidBodyControl.class).getPhysicsLocation();
        
    }
    
    public void setRotation(Quaternion rotation){
        
        spatial.getControl(RigidBodyControl.class).setPhysicsRotation(rotation);
        node.setLocalRotation(rotation);
        
        System.out.println("Analytical Balance rotation set to "+rotation);
        
    }
    
    public Spatial getAnalyticalBalance(){
        
        return spatial;
        
    }
   
    
    
   
    
}
