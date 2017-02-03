/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package worldObjects.player;

import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResults;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Line;
import control.HandControl;
import interfaces.Describable;
import java.io.IOException;
import java.util.ArrayList;
import jmevr.input.VRAPI;
import worldObjects.displays.DescDisplay;
//by Tommy
public class Hand implements Describable,Savable{
    
    public static VRAPI VRHardware;
    public static Spatial observer;
    public static Node handNode;
    
    private AssetManager assetManager;
    private Node rootNode;
    
    private Spatial spatial;
    private int side;
    private boolean staticHold;
    
    private Ray ray;//ray
    private Laser laser;//spatial
    private Geometry laserGeom;//geom
    private Material laserMat;//hand laser material
    
    private DescDisplay descriptionDisplay;
    
    private TeleportMarker teleportMarker;
    
    private HandControl handControl;
    
    private Object object;
    
    private Player player;
    
    public Hand(AssetManager assetManager,Node rootNode,VRAPI newVRHardware,CollisionResults collisionResults,ArrayList<Describable> describables,int side,Spatial newObserver,Node playerNode,Player player){
        
        //if right hand, create the handNode
        if(side==0)
            
            handNode=new Node();
        
        //assign variables
        this.assetManager=assetManager;
        this.rootNode=rootNode;
        this.side=side;
        this.player=player;
        observer=newObserver;
        VRHardware=newVRHardware;
        
        //create the ray
        ray=new Ray();
        
        //create hand model, scale it, rotate it and move it then attach it to the hand node
        spatial = side==0 ? assetManager.loadModel("Models/Player/RightHand/RightHand.j3o") : assetManager.loadModel("Models/Player/LeftHand/LeftHand.j3o");
        //spatial.scale(0.1f,0.1f,0.1f);
        
        //setup the hand spatial
        spatial.setLocalTranslation(0f,0.01f,0f);
        spatial.setName(side==0 ? "RightHand" : "LeftHand");
        spatial.setCullHint(Spatial.CullHint.Never);
        spatial.setUserData("correctCollision", true);
        spatial.setUserData("correspondingObject", this);
        handNode.attachChild(spatial);
        
        //System.out.println("Created hand model spatial with name "+spatial.getName());
        
        //create laser
        laser=new Laser(new Vector3f(0f,0.5f,0f),new Vector3f(0f,0.5f,0.1f));
        laserGeom=new Geometry("HandLaser",laser);
        laserGeom.setCullHint(Spatial.CullHint.Never);
        laserMat=new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
        laserGeom.setMaterial(laserMat);
        rootNode.attachChild(laserGeom);
        
        //add the hand object to the describables list in Main
        describables.add(this);
        
        //create and add the hand control to the hand spatial
        this.handControl=new HandControl(rootNode,assetManager,describables,collisionResults,this,observer,player);
        spatial.addControl(handControl);
        
        //create descriptionDisplay and add its control
        descriptionDisplay=new DescDisplay(assetManager,rootNode,VRHardware,side,"",observer);
        rootNode.attachChild(descriptionDisplay.getDescriptionNode());
        
        //create the teleport marker
        teleportMarker=new TeleportMarker(new Vector3f(0,-1,0), assetManager, rootNode);
        
        //attach the hand node, with child hand, to the rootNode
        playerNode.attachChild(handNode);
        
    }
    
    //description
    @Override
    public String getDescription(){
        
        //return description of a hand
        return "One of your hands";
        
    }
    
    public void setDescriptionText(String text){
        
        descriptionDisplay.setText(text);
        
    }
    
    public void setDescriptionMovedOut(boolean movedOut){
        
        descriptionDisplay.setMoveOut(movedOut);
        
    }
    
    //physical object of hand
    @Override
    public Spatial getSpatial(){
        
        //return the spatial of the hand 
        return spatial;
        
    }
    
    //side of hand, right=0 left=1
    public int getSide(){
        
        //return the hand side, 0 for right,1 for left;
        return side;
        
    }
    
    //Laser
    public void setLaserCoords(Vector3f start,Vector3f end){
        
        laser.updatePoints(start,end);
        
    }
    
    public void setLaserGeometryPoints(Vector3f start,Vector3f end){
        
        laser.updateGeometry(start, end);
        
    }
    
    public void setLaserMaterialColor(String code,ColorRGBA color){
        
        laserMat.setColor(code,color);
        
    }
    
    public Line getLine(){
        
        return laser;
        
    }
    
    //location and space/rotational details of physical hand object
    public void setLocation(Vector3f location){
        
        spatial.setLocalTranslation(location);
        
    }
    
    public Vector3f getLocalTranslation(){
        
        return spatial.getLocalTranslation();
        
    }
    
    public Vector3f getWorldTranslation(){
        
        return spatial.getWorldTranslation();
        
    }
    
    public void setRotation(Quaternion rotation){
        
        spatial.setLocalRotation(rotation);
        
    }
    
    //hand node
    public void setHandNodeLocation(Vector3f location){
        
        handNode.setLocalTranslation(location);
        
    }
    
    //ray
    public void setRayCoords(Vector3f origin,Vector3f direction){
        
        ray.setOrigin(origin);
        ray.setDirection(direction);
        
    }
    
    public Ray getRay(){
        
        return ray;
        
    }
    
    //object being held and holding logic
    public boolean isHoldingObject(){
        
        return object!=null;
        
    }
    
    public boolean hasStaticHold(){
        
        return staticHold;
        
    }
    
    public void setStaticHold(boolean staticHold){
        
        this.staticHold=staticHold;
        
    }
    
    public void setTeleportMarkerLocation(Vector3f location) {
        
        teleportMarker.setSpatialLocation(location);
     
    }
    
    //convention
    @Override
    public boolean equals(Object otherHand){
        
        if(otherHand instanceof Hand)
            
            return this.side==((Hand)(otherHand)).getSide();
        
        else
            
            return false;
        
    }
    
    public String toString(){
        
        return side==0 ? "Right hand" : "Left hand";
        
    }

    public void write(JmeExporter je) throws IOException {
        
        
    }

    public void read(JmeImporter ji) throws IOException {
        
        
    }
    
}
