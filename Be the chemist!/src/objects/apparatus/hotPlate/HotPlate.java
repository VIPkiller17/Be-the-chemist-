/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.apparatus.hotPlate;

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
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import interfaces.Grabbable;
import main.Main;
import objects.PhysicalObject;
import objects.apparatus.Apparatus;

/**
 *
 * @author VIPkiller17
 */

public class HotPlate extends Apparatus implements Savable,Grabbable{
    
    private HotPlateControl hotPlateControl;
    private PhysicalObject attachedObject;
    private Spatial spatial;
    private Node node;
    
    private Material dialHighlightMat;
    
    private BitmapText text;
    private BitmapText tareText;
    private BitmapFont font;
    private Spatial dialHighlight;
    
    private Vector3f presentPosition;
    private Quaternion presentRotation;
    
    private CollisionResults collisionResults;
    
    private CollisionShape cs;
    private RigidBodyControl phy;
    
    private boolean highlightVisible;
    
    private float presentAngle;
    private double temperature;
    
    public HotPlate(Main main,Node rootNode,CollisionResults collisionResults, AssetManager assetManager, Vector3f position) {
       
        super(main,position);
        
        node = new Node();
        
        spatial=assetManager.loadModel("Models/Static/HotPlate/HotPlate.j3o");
        dialHighlight=assetManager.loadModel("Models/Static/HotPlate/HotPlate_Dial_Highlight.j3o");
        font=assetManager.loadFont("Interface/Fonts/Xolonium/Xolonium.fnt");
        text = new BitmapText(font);
        
        //Text initial text to Zero
        text.setText("298.0");
        text.setSize(0.02f);
        text.setColor(ColorRGBA.Red);
        text.setLocalTranslation(-0.09f, 0.037f, -0.085f);
        text.rotate(new Quaternion().fromAngleAxis(FastMath.DEG_TO_RAD*270, Vector3f.UNIT_Y));
        text.rotate(new Quaternion().fromAngleAxis(FastMath.DEG_TO_RAD*-60, Vector3f.UNIT_X));
        node.attachChild(text);
        
        spatial.setName("Hot plate");
        spatial.setUserData("correctCollision", true);
        spatial.setUserData("correspondingObject", this);
        spatial.setQueueBucket(RenderQueue.Bucket.Transparent);
        node.attachChild(spatial);
        
        dialHighlight.setName("Cold sink handle highlight");
        dialHighlight.setLocalTranslation(0,-5,0);
        dialHighlightMat=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        dialHighlightMat.setColor("Color",Main.HIGHLIGHT_VISIBLE);
        dialHighlightMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        dialHighlight.setQueueBucket(RenderQueue.Bucket.Translucent);
        dialHighlight.setMaterial(dialHighlightMat);
        node.attachChild(dialHighlight);
        
        rootNode.attachChild(node);
        
        main.getItemsList().add(this);
        
        cs=CollisionShapeFactory.createMeshShape(spatial);
        
        phy=new RigidBodyControl(cs,0);
        spatial.addControl(phy);
        main.getBulletAppState().getPhysicsSpace().add(phy);
        
        node.setLocalTranslation(position);
        phy.setPhysicsLocation(new Vector3f(position));
        
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
    
    public double getMeasuredMass() {
        
        if (attachedObject==null)
            return 0;
        else
            return attachedObject.getMass();
        
        
    }
    
    @Override
    public Spatial getSpatial() {
        return spatial;
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
        
        if (object instanceof HotPlate) 
            return spatial.getLocalTranslation().equals(((HotPlate) object).getSpatial().getLocalTranslation());
        else
            return false;
    }
    
    @Override
    public Node getNode(){
        
        return node;
        
    }

    @Override
    public String getDescription() {
        
        return "An analytical balance";
        
    }
    
    public Vector3f getPosition(){
        
        return spatial.getControl(RigidBodyControl.class).getPhysicsLocation();
        
    }
    
    public void setRotation(Quaternion rotation){
        
        spatial.getControl(RigidBodyControl.class).setPhysicsRotation(rotation);
        node.setLocalRotation(rotation);
        
        //System.out.println("Analytical Balance rotation set to "+rotation);
        
    }
    
    public void rotateDial(float angle){
        
        //System.out.println("Sink handle rotate() called with angle: "+angle);
        
        if(angle>0&&presentAngle+angle<=360){
            
            //System.out.println("    Angle is higher than 0, and adding it to the preset angle wont make it go over 180, adding to present angle...");
        
            presentAngle+=angle;

            //System.out.println("        Present sink handle angle: "+presentAngle);
            
        }else if(angle>0&&presentAngle+angle>360){
            
            //System.out.println("    Angle is higher than 0, but adding to present angle would make it go over 180, setting present angle to 180...");
        
            presentAngle=360;

            //System.out.println("        Present sink handle angle: "+presentAngle);
            
        }else if(angle<0&&presentAngle>=FastMath.abs(angle)){
            
            //System.out.println("    Angle is lower than 0, but its absolute value is lower than or equal to the present angle, adding to present angle...");
            
            presentAngle+=angle;

            //System.out.println("        Present sink handle angle: "+presentAngle);
            
        }else if(angle<0&&presentAngle<FastMath.abs(angle)){
             
            //System.out.println("    Angle is lower than 0, and its absolute value is higher than the present angle, setting presentangle to 0...");
            
            presentAngle=0;

            //System.out.println("        Present sink handle angle: "+presentAngle);
            
        }
        
        temperature=(presentAngle*2)+298;
        
        if((temperature+"").length()>7){
            
            text.setText((temperature+"").substring(0,6));
            
        }else{
            
            text.setText(temperature+"");
            
        }
        
    }

    @Override
    public void setPos(Vector3f position) {
        
        spatial.getControl(RigidBodyControl.class).setPhysicsLocation(position);
        node.setLocalTranslation(position);
        
        //System.out.println("Analytical Balance position set to "+position);
        
        presentPosition=position;
        
    }

    @Override
    public String getName() {
        
        return "Analytical balance";
        
    }

    @Override
    public void highlightVisible(boolean highlightVisible) {
        
        this.highlightVisible=highlightVisible;
        
        if(highlightVisible)
            
            dialHighlight.setLocalTranslation(0,0,0);
        
        else{
            
            dialHighlight.setLocalTranslation(0,-25,0);
                
        }
        
    }

    @Override
    public Vector3f getGrabbablePosition() {
        
        return node.getWorldTranslation().add(-0.1f,0.03f,0.07f);
        
    }

    @Override
    public boolean isHighlightVisible() {
        
        return highlightVisible;
        
    }
    
    public double getTemperature(){
        
        return temperature;
        
    }
    
    public boolean canHeat(Vector3f heatablePosition){
        
        if(spatial.getWorldTranslation().add(0.02f,0.05f,0).distance(heatablePosition)<0.2){
            
            return true;
            
        }
        
        return false;
        
    }
    
}
