/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResults;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Line;
import interfaces.Describable;
import java.util.ArrayList;
import jmevr.input.OpenVRInput;
import jmevr.input.VRAPI;
import worldObjects.displays.DescDisplay;
import worldObjects.player.Hand;
import worldObjects.player.Player;
import worldObjects.staticWorld.testing.TestFloor;
//by Tommy
public class HandControl extends AbstractControl{
    
    private static VRAPI VRHardware;
    private static CollisionResults collisionResults;
    private static int controllerCount;
    private static Node rootNode;
    private static ArrayList<Describable> describables;
    private static int presentCorrectCollisionIndex;
    private static Spatial observer;
    
    private Hand hand;
    private int handSide;
    private String collisionToExclude;
    private Spatial correctCollisionSpatial;
    
    private float touchPadX,touchPadY;
    private double radTouchPadXAngle,degTouchPadXAngle;
    
    private DescDisplay handDescriptionDisplay;
    private boolean descriptionMovedOut;
    
    private boolean laserMovedOut,laserPointingAtDescribable;
    private boolean teleLaserMovedOut,teleLaserPointingValidSurface,teleportationPrimed,touchPadDown;
    
    private Line testLaser;
    private Geometry testLaserGeom;
    private Material testLaserMat;
    
    private Box collisionPoint;
    private Geometry collisionPointGeom;
    private Material collisionPointMat;
    
    private Player player;
    
    public HandControl(Node newRootNode,AssetManager assetManager,ArrayList<Describable> newDescribables,CollisionResults newCollisionResults,Hand hand,Spatial newObserver,Player player){
        
        this.hand=hand;
        this.player=player;
        observer=newObserver;
        rootNode=newRootNode;
        VRHardware=Hand.VRHardware;
        handSide=hand.getSide();
        describables=newDescribables;
        collisionResults=newCollisionResults;
        
        collisionToExclude=handSide==0 ? "RightHand" : "LeftHand";
        
        testLaser=new Line(new Vector3f(0f,0.5f,0f),new Vector3f(0f,0.5f,0.1f));
        testLaserGeom=new Geometry("HandLaser",testLaser);
        testLaserMat=new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
        testLaserGeom.setMaterial(testLaserMat);
        testLaserGeom.setCullHint(Spatial.CullHint.Never);
        //rootNode.attachChild(testLaserGeom);
        
        collisionPoint = new Box(0.01f,0.01f,0.01f); 
        collisionPointGeom = new Geometry("TestCollisionPoint", collisionPoint); 
        collisionPointMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        collisionPointMat.setColor("Color",ColorRGBA.Blue);
        collisionPointGeom.setMaterial(collisionPointMat); 
        rootNode.attachChild(collisionPointGeom);
        
    }

    @Override
    protected void controlUpdate(float tpf) {

        //Update hand
        if(VRHardware.getVRinput().getRawControllerState(handSide)!=null){
            
            //Put the ray to correct position and direction
            hand.setRayCoords(hand.getWorldTranslation(),new Vector3f(VRHardware.getVRinput().getOrientation(handSide).getRotationColumn(2)));
            
            testLaser.updatePoints(hand.getRay().getOrigin(),hand.getRay().getDirection().mult(1000));

            //Update location of Geom
            hand.setLocation(VRHardware.getVRinput().getPosition(handSide));

            //Update Rotation of Geom
            hand.setRotation(VRHardware.getVRinput().getOrientation(handSide));

            //Update due to trigger axis
            //rightHandSpatial.setLocalScale(VRHardware.getVRinput().getAxis(0,VRINPUT_TYPE.ViveTriggerAxis).x+0.1f);

            //Update due to menu button being pressed
            if(VRHardware.getVRinput().isButtonDown(handSide, OpenVRInput.VRINPUT_TYPE.ViveMenuButton)){//while its being held

                //check if there is an object in the hand
                if(hand.isHoldingObject()){

                    //TODO display held object's details

                }else{

                    //System.out.println("STARTING LASER LOOP");

                    //check if the collisions array actually has anything in it
                    if(collisionResults.size()>0)

                        //if it does clear it because there could be left-overs from last loop
                        collisionResults.clear();

                    //find the collisions between the ray and other geoms
                    rootNode.collideWith(hand.getRay(),collisionResults);

                    //Finds the correct collision in the collision list
                    //System.out.println("COLLISIONS PROCESSING START");
                    
                    //display list of collisions
                    //System.out.println("\tCollisions:");
                    //for(int i=0;i<collisionResults.size();i++){

                        //System.out.println("\t\tCollision result geom name: "+collisionResults.getCollision(i).getGeometry().getName()+", its parent's name: "+collisionResults.getCollision(i).getGeometry().getParent().getName()+" and the name of the geometry cested to a spatial: "+((Spatial)(collisionResults.getCollision(i).getGeometry())).getName());

                    //}
                    
                    //System.out.println("\tFinding correct collision:");

                    for(int i=0;i<collisionResults.size();i++){
                        
                        //System.out.println("\t\tChecking if collision geom of collision "+i+" has the correctCollision userData");
                        
                        //Check if the collision geom is the correct collision
                        //if Not, check if its parent is the correct collision
                        //if not, continue to the next element in the list of collisions
                        if(collisionResults.getCollision(i).getGeometry().getUserData("correctCollision")!=null){
                            
                            //System.out.println("\t\t\tCollision geom has correctCollision userData, checking if its name "+collisionResults.getCollision(i).getGeometry().getName()+" contains "+collisionToExclude);
                            
                            if(!collisionResults.getCollision(i).getGeometry().getName().contains(collisionToExclude)){

                                //System.out.println("\t\t\t\tFirst collision of the list not containing "+collisionToExclude+" in name \""+collisionResults.getCollision(i).getGeometry().getName()+"\" found, correct colision found on spatial "+collisionResults.getCollision(i).getGeometry().getName()+" with parent: "+collisionResults.getCollision(i).getGeometry().getParent().getName());

                                correctCollisionSpatial=collisionResults.getCollision(i).getGeometry();
                                
                                presentCorrectCollisionIndex=i;
                                
                                System.out.println("Found correct collision at index: "+presentCorrectCollisionIndex+"\nCollision point: "+collisionResults.getCollision(presentCorrectCollisionIndex).getContactPoint());

                                break;

                            }
                            
                        }else if(collisionResults.getCollision(i).getGeometry().getParent().getUserData("correctCollision")!=null){
                            
                            //System.out.println("\t\t\tCollision geom does not have correctCollision userData but parent does, checking if the collision geom's parent's name: "+collisionResults.getCollision(i).getGeometry().getParent().getName()+" contains "+collisionToExclude);
                            
                            if(!collisionResults.getCollision(i).getGeometry().getParent().getName().contains(collisionToExclude)){

                                //System.out.println("\t\t\t\tFirst collision of the list not containing "+collisionToExclude+" in name \""+collisionResults.getCollision(i).getGeometry().getParent().getName()+"\" found, correct colision found on spatial "+collisionResults.getCollision(i).getGeometry().getParent().getName()+" with parent: "+collisionResults.getCollision(i).getGeometry().getParent().getParent().getName());

                                correctCollisionSpatial=collisionResults.getCollision(i).getGeometry().getParent();
                                
                                presentCorrectCollisionIndex=i;
                                
                                System.out.println("Found correct collision at index: "+presentCorrectCollisionIndex+"\nCollision point: "+collisionResults.getCollision(presentCorrectCollisionIndex).getContactPoint());
                             
                                break;

                            }
                            
                        }else{
                            
                            //System.out.println("\t\t\tCollision geom and its parents do not have correctCollision userData, skipping to next collision");
                            
                            continue;
                            
                        }

                    }
                    
                    //System.out.println("\tUpdating laser position...");

                    //update start and end points of laser to display it correctly
                    collisionPointGeom.setLocalTranslation(collisionResults.getCollision(presentCorrectCollisionIndex).getContactPoint());
                    hand.setLaserGeometryPoints(hand.getWorldTranslation(),collisionPointGeom.getWorldTranslation());

                    //set laserMovedOut to false because it has been displayed
                    laserMovedOut=false;

                    //set isPointingDescribable to false initially because this is checked in the next lines
                    laserPointingAtDescribable=false;
                    
                    descriptionMovedOut=false;

                    //System.out.println("\tFinding collision spatial's corresponding object..");
                    
                    if(correctCollisionSpatial.getUserData("correspondingObject")!=null&&correctCollisionSpatial.getUserData("correspondingObject") instanceof Describable){
                        
                        //System.out.println("\tCorrectCollisionSpatial's corresponding object is describable, setting laser accordingly...");
                        
                        laserPointingAtDescribable=true;
                        
                    }else if(correctCollisionSpatial.getUserData("correspondingObject")!=null&&!(correctCollisionSpatial.getUserData("correspondingObject") instanceof Describable)){
                        
                        //System.out.println("\tCorrectCollisionSpatial's corresponding object is not describable, setting laser accordingly...");
                        
                        laserPointingAtDescribable=false;
                        
                    }else{
                        
                        //System.out.println("\tERROR: CorrectCollisionSpatial does not have a corresponding object!");
                        
                    }

                    //if it has found to be pointing at a describable object
                    if(laserPointingAtDescribable){

                        //make laser green
                        hand.setLaserMaterialColor("Color", ColorRGBA.Green);

                        hand.setDescriptionText(((Describable)(correctCollisionSpatial.getUserData("correspondingObject"))).getDescription());
                        hand.setDescriptionMovedOut(false);
                        
                        descriptionMovedOut=false;

                    }else{

                        //otherwise make it red
                        hand.setLaserMaterialColor("Color", ColorRGBA.Red);

                        hand.setDescriptionText("");
                        hand.setDescriptionMovedOut(true);
                        
                        descriptionMovedOut=true;

                    }

                }

            }else if(!VRHardware.getVRinput().isButtonDown(handSide, OpenVRInput.VRINPUT_TYPE.ViveMenuButton)){

                //check if the laser is already out of sight
                if(!laserMovedOut){

                    //if not already out of sight, move laser out of sight
                    hand.setLaserCoords(new Vector3f(0f,-1f,0f),new Vector3f(0f,-1f,0f));

                    //set laserMovedOut to true once the laser has been moved out
                    laserMovedOut=true;

                }
                
                //check if description is already out of sight
                if(!descriptionMovedOut){

                    hand.setDescriptionMovedOut(true);
                    hand.setDescriptionText("");

                    descriptionMovedOut=true;
                
                }
            }

            //Update due to Grip button being pressed or clicked in this case
            if(VRHardware.getVRinput().wasButtonPressedSinceLastCall(handSide, OpenVRInput.VRINPUT_TYPE.ViveGripButton)){

                //Toggles between true or false for holding the object in place
                if(hand.hasStaticHold()){

                    hand.setStaticHold(false);
                    //System.out.println("Hand static hold set to false");

                }else{

                    hand.setStaticHold(true);
                    //System.out.println("Hand static hold set to true");

                }
                
            }
            
            //Update depending on where the touchpad is being pressed, not touched
            if(VRHardware.getVRinput().isButtonDown(handSide, OpenVRInput.VRINPUT_TYPE.ViveTouchpadAxis)){
                
                touchPadDown=true;
                
                touchPadX=VRHardware.getVRinput().getAxis(handSide, OpenVRInput.VRINPUT_TYPE.ViveTouchpadAxis).getX();
                touchPadY=VRHardware.getVRinput().getAxis(handSide, OpenVRInput.VRINPUT_TYPE.ViveTouchpadAxis).getY();
                
                //System.out.println("Touchpad pressed:\n\tAxis: "+VRHardware.getVRinput().getAxis(handSide, OpenVRInput.VRINPUT_TYPE.ViveTouchpadAxis));
                
                radTouchPadXAngle=Math.abs(Math.atan(touchPadY/touchPadX));
                degTouchPadXAngle=Math.toDegrees(radTouchPadXAngle);
                
                //System.out.println("\t\tAngle in rads compared to X axis: "+radTouchPadXAngle);
                //System.out.println("\t\tAngle in degrees compared to X axis: "+degTouchPadXAngle);
                
                if(touchPadX>0&&touchPadY>0){
                    
                    if(degTouchPadXAngle>45){
                        
                        //System.out.println("\t\tTouchPad: UP");
                        
                        conditionalMoveTeleLaserOut();
                        
                        teleportationPrimed=false;
                        
                    }else{
                        
                        //System.out.println("\t\tTouchPad: RIGHT");
                        
                        conditionalMoveTeleLaserOut();
                        
                        teleportationPrimed=false;
                        
                    }
                    
                }else if(touchPadX<0&&touchPadY>0){
                    
                    if(degTouchPadXAngle>45){
                        
                        //System.out.println("\t\tTouchPad: UP");
                        
                        conditionalMoveTeleLaserOut();
                        
                        teleportationPrimed=false;
                        
                        
                    }else{
                        
                        //System.out.println("\t\tTouchPad: LEFT");
                        
                        conditionalMoveTeleLaserOut();
                        
                        teleportationPrimed=false;
                        
                    }
                    
                }else if(touchPadX<0&&touchPadY<0){
                    
                    if(degTouchPadXAngle>45){
                        
                        //System.out.println("\t\tTouchPad: DOWN");
                        
                        processTeleportation();
                        
                    }else{
                        
                        //System.out.println("\t\tTouchPad: LEFT");
                        
                        conditionalMoveTeleLaserOut();
                        
                        teleportationPrimed=false;
                        
                    }
                    
                }else if(touchPadX>0&&touchPadY<0){
                    
                    if(degTouchPadXAngle>45){
                        
                        //System.out.println("\t\tTouchPad: DOWN");
                        
                        processTeleportation();
                        
                    }else{
                        
                        //System.out.println("\t\tTouchPad: RIGHT");
                        
                        conditionalMoveTeleLaserOut();
                        
                        teleportationPrimed=false;
                        
                    }
                    
                }
                
            }
            
            if(touchPadDown&&!VRHardware.getVRinput().isButtonDown(handSide, OpenVRInput.VRINPUT_TYPE.ViveTouchpadAxis)){
                
                //System.out.println("Touchpad released");
                
                if(teleportationPrimed){
                    
                    player.teleportArea(collisionResults.getCollision(presentCorrectCollisionIndex).getContactPoint());
                    
                    /*
                    observer.setLocalTranslation(collisionResults.getCollision(presentCorrectCollisionIndex).getContactPoint());
                    hand.setHandNodeLocation(collisionResults.getCollision(presentCorrectCollisionIndex).getContactPoint());
                    */
                    
                }
                
                conditionalMoveTeleLaserOut();
                
                touchPadDown=false;
                
            }

        }
    
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
        
        
    }
    
    private void processTeleportation(){

        //check if the collisions array actually has anything in it
        if(collisionResults.size()>0)

            //if it does clear it because there could be left-overs from last loop
            collisionResults.clear();

        //find the collisions between the ray and other geoms
        rootNode.collideWith(hand.getRay(),collisionResults);

        //Finds the correct collision in the collision list
        //System.out.println("COLLISIONS PROCESSING START");

        //display list of collisions
        //System.out.println("\tCollisions:");
        //for(int i=0;i<collisionResults.size();i++){

            //System.out.println("\t\tCollision result geom name: "+collisionResults.getCollision(i).getGeometry().getName()+", its parent's name: "+collisionResults.getCollision(i).getGeometry().getParent().getName()+" and the name of the geometry cested to a spatial: "+((Spatial)(collisionResults.getCollision(i).getGeometry())).getName());

        //}

        //System.out.println("\tFinding correct collision:");
        
        for(int i=0;i<collisionResults.size();i++){
                        
            //System.out.println("\t\tChecking if collision geom of collision "+i+" has the correctCollision userData");

            //Check if the collision geom is the correct collision
            //if Not, check if its parent is the correct collision
            //if not, continue to the next element in the list of collisions
            if(collisionResults.getCollision(i).getGeometry().getUserData("correctCollision")!=null){

                //System.out.println("\t\t\tCollision geom has correctCollision userData, checking if its name "+collisionResults.getCollision(i).getGeometry().getName()+" contains "+collisionToExclude);

                if(!collisionResults.getCollision(i).getGeometry().getName().contains(collisionToExclude)){

                    //System.out.println("\t\t\t\tFirst collision of the list not containing "+collisionToExclude+" in name \""+collisionResults.getCollision(i).getGeometry().getName()+"\" found, correct colision found on spatial "+collisionResults.getCollision(i).getGeometry().getName()+" with parent: "+collisionResults.getCollision(i).getGeometry().getParent().getName());

                    correctCollisionSpatial=collisionResults.getCollision(i).getGeometry();

                    presentCorrectCollisionIndex=i;

                    break;

                }

            }else if(collisionResults.getCollision(i).getGeometry().getParent().getUserData("correctCollision")!=null){

                //System.out.println("\t\t\tCollision geom does not have correctCollision userData but parent does, checking if the collision geom's parent's name: "+collisionResults.getCollision(i).getGeometry().getParent().getName()+" contains "+collisionToExclude);

                if(!collisionResults.getCollision(i).getGeometry().getParent().getName().contains(collisionToExclude)){

                    //System.out.println("\t\t\t\tFirst collision of the list not containing "+collisionToExclude+" in name \""+collisionResults.getCollision(i).getGeometry().getParent().getName()+"\" found, correct colision found on spatial "+collisionResults.getCollision(i).getGeometry().getParent().getName()+" with parent: "+collisionResults.getCollision(i).getGeometry().getParent().getParent().getName());

                    correctCollisionSpatial=collisionResults.getCollision(i).getGeometry().getParent();

                    presentCorrectCollisionIndex=i;

                    break;

                }

            }else{

                System.out.println("\t\t\tCollision geom and its parents do not have correctCollision userData, skipping to next collision");

                continue;

            }

        }
        
        //System.out.println("\tUpdating laser position...");

        //update start and end points of laser to display it correctly
        hand.setLaserCoords(hand.getWorldTranslation(),collisionResults.getCollision(presentCorrectCollisionIndex).getContactPoint());

        System.out.println("Set laser coords to start: "+VRHardware.getVRinput().getPosition(handSide)+" and end: "+collisionResults.getCollision(presentCorrectCollisionIndex).getContactPoint());
        
        //set laserMovedOut to false because it has been displayed
        teleLaserMovedOut=false;

        //set isPointingValidSurface to false initially because this is checked in the next lines
        teleLaserPointingValidSurface=false;

        //System.out.println("\tFinding collision spatial's corresponding object..");

        if(correctCollisionSpatial.getUserData("correspondingObject")!=null&&correctCollisionSpatial.getUserData("correspondingObject") instanceof TestFloor){

            //System.out.println("\tCorrectCollisionSpatial's corresponding object is describable, setting laser accordingly...");

            teleLaserPointingValidSurface=true;

        }else if(correctCollisionSpatial.getUserData("correspondingObject")!=null&&!(correctCollisionSpatial.getUserData("correspondingObject") instanceof TestFloor)){

            //System.out.println("\tCorrectCollisionSpatial's corresponding object is not describable, setting laser accordingly...");

            teleLaserPointingValidSurface=false;

        }else{

            //System.out.println("\tERROR: CorrectCollisionSpatial does not have a corresponding object!");

        }

        //if it has found to be pointing at a describable object
        if(teleLaserPointingValidSurface){

            //make laser green
            hand.setLaserMaterialColor("Color", ColorRGBA.Green);
            
            //Set location tp hexagon
            hand.setTeleportMarkerLocation(collisionResults.getCollision(presentCorrectCollisionIndex).getContactPoint().add(0,0.001f,0));

            teleportationPrimed=true;

        }else{

            //otherwise make it red
            hand.setLaserMaterialColor("Color", ColorRGBA.Red);
            
            hand.setTeleportMarkerLocation(new Vector3f(0f,-1f,0f));

            teleportationPrimed=false;

        }
        
    }
    
    private void conditionalMoveTeleLaserOut(){
        
        if(!teleLaserMovedOut){

            //if not already out of sight, move laser out of sight
            hand.setLaserCoords(new Vector3f(0f,-1f,0f),new Vector3f(0f,-1f,0f));
            
            hand.setTeleportMarkerLocation(new Vector3f(0f,-1f,0f));

            //set laserMovedOut to true once the laser has been moved out
            teleLaserMovedOut=true;

        }
        
    }
    
}
