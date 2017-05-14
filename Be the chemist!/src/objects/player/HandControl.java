/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.player;

import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResults;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.shape.Box;
import interfaces.Describable;
import interfaces.Grabbable;
import jmevr.input.OpenVRInput;
import jmevr.input.OpenVRInput.VRINPUT_TYPE;
import jmevr.input.VRAPI;
import main.Main;
import objects.PhysicalObject;
import objects.apparatus.distilledWaterContainer.DistilledWaterContainer;
import objects.apparatus.fumeHood.FumeHoodDoor;
import objects.apparatus.hotPlate.HotPlate;
import objects.containers.beaker.Beaker;
import objects.containers.erlenmeyer.Erlenmeyer;
import objects.containers.gasSac.GasSac;
import objects.containers.gasSac.GasSacValve;
import objects.containers.testTube.TestTube;
import objects.world.Floor;
import objects.world.SinkHandle;
import objects.world.display.Button;
import objects.world.display.Display;
import objects.world.display.ElementButton;
import objects.world.display.MaterialButton;
import objects.world.display.PeriodicTableDisplay;
import objects.world.display.SubstanceButton;
//by Tommy
public class HandControl extends AbstractControl{
    
    private static VRAPI VRHardware;
    private static CollisionResults collisionResults;
    private static int controllerCount;
    private static Node rootNode;
    private static int presentCorrectCollisionIndex;
    private static Spatial observer;
    
    private boolean foundPresentCorrectCollision;
    private Hand hand;
    private int handSide;
    private String collisionToExclude;
    private Spatial correctCollisionSpatial;
    private PhysicalObject possibleItemToGrab;
    private boolean itemInRange;
    
    private float touchPadX,touchPadY;
    private double radTouchPadXAngle,degTouchPadXAngle;
    
    private DescDisplay handDescriptionDisplay;
    private boolean descriptionMovedOut;
    
    private boolean laserMovedOut,laserPointingAtDescribable;
    private boolean teleLaserMovedOut,teleLaserPointingValidSurface,teleportationPrimed,touchPadDown;
    private boolean touchPadPressedDown,touchPadPressedUp,touchPadPressedRight,touchPadPressedLeft;
    private boolean menuPressed,touchpadPressed,triggerPressed,gripPressed;
    private boolean menuWasPressed,touchPadWasPressed,triggerWasPressed,gripWasPressed;
    private boolean laserActivatedByTeleportation,laserActivatedByDescription,laserActivatedByDisplay,laserActivated;
    
    private Geometry testLaserGeom;
    private Material testLaserMat;
    
    private Box collisionPoint;
    private Geometry collisionPointGeom;
    private Material collisionPointMat;
    
    private Button presentPointedButton;
    private boolean pointingDisplay;
    private boolean pointingButton;
    
    private ElementButton presentPointedElementButton;
    private boolean pointingPeriodicTableDisplay;
    private boolean pointingElementButton;
    
    private SubstanceButton presentPointedSubstanceButton;
    private boolean pointingSubstanceButton;
    
    private MaterialButton presentPointedMaterialButton;
    private boolean pointingMaterialButton;
    
    private static final Vector3f OUT_OF_MAP=new Vector3f(0,-1,0);
    private static final ColorRGBA GREEN_LASER=ColorRGBA.Green,RED_LASER=ColorRGBA.Red;
    
    private Quaternion rotationWhenGrabbed;
    private Quaternion rotationOnLastFrame=new Quaternion();
    
    private boolean triggerReleasedAfterHold;
    
    private Player player;
    
    private Main main;
    
    public HandControl(Main main,Node newRootNode,AssetManager assetManager,CollisionResults newCollisionResults,Hand hand,Spatial newObserver,Player player){
        
        this.main=main;
        
        //init variables
        this.hand=hand;
        this.player=player;
        observer=newObserver;
        rootNode=newRootNode;
        VRHardware=Hand.VRHardware;
        handSide=hand.getSide();
        collisionResults=newCollisionResults;
        
        collisionToExclude=handSide==0 ? "RightHand" : "LeftHand";
        
        //collision point is used t put the laser's end at the correct location
        //this was added because the laser would not point at the correct location after a teleportation
        //this still requires testing, as it has not been added to the teleportation laser
        //but only to the description laser
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
        //check if controller exists
        if(VRHardware.getVRinput().getRawControllerState(handSide)!=null){
            
            //setup the hand's position and all for this frame
            setupHandForFrame();
            
            //Update due to static hold
            updateHold();
            
            //Check the closest grabbable item to the hand and see if it is grabbable
            grabProcess();

            //Init. check to get is any button is being pressed this frame
            getPressedButtons();
            
            //Update due to trigger input
            updateTrigger();

            //Update due to menu button being pressed
            updateMenuButton();

            //Update due to Grip button being pressed or clicked in this case
            updateGripButton();
            
            //Update depending on where the touchpad is being pressed, NOT TOUCHED
            updateTouchpad();
            
            updateLaser();
            
            rotationOnLastFrame=VRHardware.getVRinput().getFinalObserverRotation(handSide);

        }
    
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
    }
    
    private void processTeleportation(){
        
        //System.out.println("\tUpdating laser position...");

        //if it found a new corect collision index, update start and end points of laser to display it correctly
        if(foundPresentCorrectCollision)
        
            hand.setLaserCoords(hand.getWorldTranslation(),collisionResults.getCollision(presentCorrectCollisionIndex).getContactPoint());
        
        laserActivatedByTeleportation=true;
        laserActivated=true;
        
        //System.out.println("Set laser coords to start: "+VRHardware.getVRinput().getPosition(handSide)+" and end: "+collisionResults.getCollision(presentCorrectCollisionIndex).getContactPoint());
        
        //set laserMovedOut to false because it has been displayed
        teleLaserMovedOut=false;

        //set isPointingValidSurface to false initially because this is checked in the next lines
        teleLaserPointingValidSurface=false;

        //System.out.println("\tFinding collision spatial's corresponding object..");

        if(correctCollisionSpatial.getUserData("correspondingObject")!=null&&correctCollisionSpatial.getUserData("correspondingObject") instanceof Floor){

            //System.out.println("\tCorrectCollisionSpatial's corresponding object is describable, setting laser accordingly...");

            teleLaserPointingValidSurface=true;

        }else if(correctCollisionSpatial.getUserData("correspondingObject")!=null&&!(correctCollisionSpatial.getUserData("correspondingObject") instanceof Floor)){

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
            if(foundPresentCorrectCollision)
            
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
        
        //if laser isnt being used by something else, move it out
        if(!laserActivatedByDescription&&!laserActivatedByDisplay){
        
            if(!teleLaserMovedOut){

                //if not already out of sight, move laser out of sight
                makeLaserDisappear();

                hand.setTeleportMarkerLocation(new Vector3f(0f,-1f,0f));

                //set laserMovedOut to true once the laser has been moved out
                teleLaserMovedOut=true;
                
                laserActivatedByTeleportation=false;
                laserActivated=false;

            }
            
        }
        
    }
    
    private void getPressedButtons(){
        
        menuPressed = VRHardware.getVRinput().isButtonDown(handSide, OpenVRInput.VRINPUT_TYPE.ViveMenuButton);
        touchpadPressed = VRHardware.getVRinput().isButtonDown(handSide, OpenVRInput.VRINPUT_TYPE.ViveTouchpadAxis);
        triggerPressed = VRHardware.getVRinput().isButtonDown(handSide, OpenVRInput.VRINPUT_TYPE.ViveTriggerAxis);
        gripPressed = VRHardware.getVRinput().isButtonDown(handSide, OpenVRInput.VRINPUT_TYPE.ViveGripButton);
        
    }
    
    private void setupHandForFrame(){
        
        //Update location of Geom
        hand.setLocation(VRHardware.getVRinput().getPosition(handSide));
        
        //System.out.println("Hand position: "+hand.getWorldTranslation());
        
        //Update Rotation of Geom
        hand.setRotation(VRHardware.getVRinput().getOrientation(handSide));
        
        //System.out.println("Angle of last frame rotation in rads: "+FastMath.asin(rotationOnLastFrame.getRotationColumn(1).getX())+", in degs: "+FastMath.RAD_TO_DEG*FastMath.asin(rotationOnLastFrame.getRotationColumn(1).getX()));
        //System.out.println("Angle of present frame rotation in rads: "+FastMath.asin(VRHardware.getVRinput().getOrientation(handSide).getRotationColumn(1).getX())+", in degs: "+FastMath.RAD_TO_DEG*FastMath.asin(VRHardware.getVRinput().getOrientation(handSide).getRotationColumn(1).getX()));
        
        System.out.println("Angle difference of hand rotation since last frame in deg: "+FastMath.RAD_TO_DEG*(FastMath.asin(VRHardware.getVRinput().getOrientation(handSide).getRotationColumn(1).getX())-FastMath.asin(rotationOnLastFrame.getRotationColumn(1).getX())));
        
        //System.out.println("Hand rotation (Quaternion): "+VRHardware.getVRinput().getOrientation(handSide)+", (Matrix):\nCol 0: "+VRHardware.getVRinput().getOrientation(handSide).getRotationColumn(0)+"\nCol 1: "+VRHardware.getVRinput().getOrientation(handSide).getRotationColumn(1)+"\nCol 2: "+VRHardware.getVRinput().getOrientation(handSide).getRotationColumn(0));
        
        //System.out.println("Updating ray position...");
        
        //Put the ray to correct position and direction
        hand.setRayCoords(hand.getWorldTranslation(),new Vector3f(VRHardware.getVRinput().getOrientation(handSide).getRotationColumn(2)));

        //System.out.println("Clearing collisions list...");
        //check if the collisions array actually has anything in it
        if(collisionResults.size()>0)

            //if it does clear it because there could be left-overs from last loop
            collisionResults.clear();
        
        //System.out.println("Processing collisions between ray and world...");
        //find the collisions between the ray and other geoms
        rootNode.collideWith(hand.getRay(),collisionResults);

        //System.out.println("This frame's collisions list:");

        /*
        for(int i=0;i<collisionResults.size();i++){

            //System.out.println(i+".: "+collisionResults.getCollision(i).getGeometry().getName());
            
        }
        */
        
        //System.out.println("HAND ROTATION: "+hand.getRotation());
        
        
        //Find the correct collision for this frame
        foundPresentCorrectCollision=false;
        
        presentCorrectCollisionIndex=-1;
        
        for(int i=0;i<collisionResults.size();i++){
                        
            //System.out.println("\t\tChecking if collision geom of collision "+i+" has the correctCollision userData");

            //Check if the collision geom is the correct collision
            //if Not, check if its parent is the correct collision
            //if not, continue to the next element in the list of collisions
            if(collisionResults.getCollision(i).getGeometry().getUserData("correctCollision")!=null){

                //System.out.println("\t\t\tCollision geom has correctCollision userData, checking if its name "+collisionResults.getCollision(i).getGeometry().getName()+" contains "+collisionToExclude);

                if(!collisionResults.getCollision(i).getGeometry().getName().contains(collisionToExclude)&&!collisionResults.getCollision(i).getGeometry().getUserData("correspondingObject").equals(hand.getHeldObject())){
                    
                    foundPresentCorrectCollision=true;

                    //System.out.println("\t\t\t\tFirst collision of the list not containing "+collisionToExclude+" in name \""+collisionResults.getCollision(i).getGeometry().getName()+"\" found, correct colision found on spatial "+collisionResults.getCollision(i).getGeometry().getName()+" with parent: "+collisionResults.getCollision(i).getGeometry().getParent().getName());

                    correctCollisionSpatial=collisionResults.getCollision(i).getGeometry();

                    presentCorrectCollisionIndex=i;

                    break;

                }

            }else if(collisionResults.getCollision(i).getGeometry().getParent().getUserData("correctCollision")!=null){

                //System.out.println("\t\t\tCollision geom does not have correctCollision userData but parent does, checking if the collision geom's parent's name: "+collisionResults.getCollision(i).getGeometry().getParent().getName()+" contains "+collisionToExclude);

                if(!collisionResults.getCollision(i).getGeometry().getParent().getName().contains(collisionToExclude)&&!collisionResults.getCollision(i).getGeometry().getParent().getUserData("correspondingObject").equals(hand.getHeldObject())){
                    
                    foundPresentCorrectCollision=true;

                    //System.out.println("\t\t\t\tFirst collision of the list not containing "+collisionToExclude+" in name \""+collisionResults.getCollision(i).getGeometry().getParent().getName()+"\" found, correct colision found on spatial "+collisionResults.getCollision(i).getGeometry().getParent().getName()+" with parent: "+collisionResults.getCollision(i).getGeometry().getParent().getParent().getName());

                    correctCollisionSpatial=collisionResults.getCollision(i).getGeometry().getParent();

                    presentCorrectCollisionIndex=i;

                    break;

                }

            }else{

                //System.out.println("\t\t\tCollision geom and its parents do not have correctCollision userData, skipping to next collision");

            }

        }
        
    }
    
    private void grabProcess(){
        
        //if the distance between the possibble to grab item is now more than 15cm, set the possible to grab item to null
        //also set its highliahgt model to invisible
        if(possibleItemToGrab!=null&&((Grabbable)possibleItemToGrab).getGrabbablePosition().distance(hand.getWorldTranslation())>0.15f){
            
            //System.out.println("Distance between hand and grabbale object is now more than 15 cm, setting grabbable object to null and its highlight to invisible");
            
            ((Grabbable)possibleItemToGrab).highlightVisible(false);
            
            possibleItemToGrab=null;
            
        }
        
        //if the distance between the held item is now more than 15cm, set the held item to null
        if(hand.getHeldObject()!=null&&((Grabbable)hand.getHeldObject()).getGrabbablePosition().distance(hand.getWorldTranslation())>0.15f){
            
            //System.out.println("Distance between hand and held object is now more than 15 cm, setting held object to null and static hold to false");
            /*
            if(((Grabbable)possibleItemToGrab).getSpatial().getControl(RigidBodyControl.class)!=null){
            
                ((Grabbable)possibleItemToGrab).getSpatial().getControl(RigidBodyControl.class).setMass(1);
                ((Grabbable)possibleItemToGrab).getSpatial().getControl(RigidBodyControl.class).setKinematic(false);
                //possibleItemToGrab.getSpatial().getControl(RigidBodyControl.class).setKinematicSpatial(true);
            
            }
            */
            
            //Do a final action on the object being forcible dropped
            if(hand.getHeldObject() instanceof Beaker){
                
                //System.out.println("Original velocity to apply to thrown object: "+VRHardware.getVRinput().getVelocity(handSide));
                //System.out.println("Modified velocity to apply to thrown object: "+VRHardware.getVRinput().getVelocity(handSide).multLocal(new Vector3f(-1,1,-1)));
                        
                ((Beaker)hand.getHeldObject()).setVelocity(VRHardware.getVRinput().getVelocity(handSide).multLocal(new Vector3f(-1,1,-1)));
                
            }else if(hand.getHeldObject() instanceof Erlenmeyer){
                
                ((Erlenmeyer)hand.getHeldObject()).setVelocity(VRHardware.getVRinput().getVelocity(handSide).multLocal(new Vector3f(-1,1,-1)));
                
            }else if(hand.getHeldObject() instanceof TestTube){
                
                ((TestTube)hand.getHeldObject()).setVelocity(VRHardware.getVRinput().getVelocity(handSide).multLocal(new Vector3f(-1,1,-1)));
                
            }else if(hand.getHeldObject() instanceof GasSac){
                
                //System.out.println("Original velocity to apply to thrown object: "+VRHardware.getVRinput().getVelocity(handSide));
                //System.out.println("Modified velocity to apply to thrown object: "+VRHardware.getVRinput().getVelocity(handSide).multLocal(new Vector3f(-1,1,-1)));
                        
                ((GasSac)hand.getHeldObject()).setVelocity(VRHardware.getVRinput().getVelocity(handSide).multLocal(new Vector3f(-1,1,-1)));
                
            }
            
            if(((Grabbable)hand.getHeldObject()).getGrabbablePosition().distance(hand.getWorldTranslation())<0.15f){
                        
                ((Grabbable)hand.getHeldObject()).highlightVisible(true);
                        
            }
            
            hand.setStaticHold(false);
            
            hand.setHeldObject(null);
            
        }
        
        //System.out.println("Grab process method called with main item list size: "+main.getItemsList().size());
        
        //System.out.println("Searching for grabbable object in hand's vicinity...");
        
        if(hand.getHeldObject()==null)
        
            //for each item in the item list
            for(PhysicalObject p: main.getItemsList()){

                //System.out.println("Object: "+p);

                //System.out.println("Grabbable: "+(p instanceof Grabbable)+", its pos: "+p.getPos()+", Hand pos: "+hand.getWorldTranslation()+", distance: "+p.getPos().distance(hand.getWorldTranslation()));

                //check if the item is grabbable
                //check if the item's distance compared to the hand is less than 15cm
                //check if the last grabbable item is null or if the item's distance compared to the hand is less than the last grabbable item
                if(p instanceof Grabbable&&((Grabbable)p).getGrabbablePosition().distance(hand.getWorldTranslation())<=0.15f&&(possibleItemToGrab==null||((Grabbable)p).getGrabbablePosition().distance(hand.getWorldTranslation())<((Grabbable)possibleItemToGrab).getGrabbablePosition().distance(hand.getWorldTranslation()))){

                    //System.out.println("This is the new better grabbable item");

                    //set the last grabbable item's highlight to invisible
                    //set the grabbable item to the item
                    //set the grabbable item's highlight to visible
                    if(possibleItemToGrab!=null)

                        ((Grabbable)possibleItemToGrab).highlightVisible(false);

                    possibleItemToGrab=p;

                    ((Grabbable)p).highlightVisible(true);

                    //System.out.println("Item's highlight set to visible");

                }

            }
        
    }
    
    private void updateTrigger(){
        
        updateTriggerByPress();
        
        updateTriggerByAxis();
        
    }
    
    private void updateTriggerByPress(){
        
        if(VRHardware.getVRinput().isButtonDown(handSide, OpenVRInput.VRINPUT_TYPE.ViveTriggerAxis)){
                
                if(triggerWasPressed){
                    
                    //add what happens when the player continues to hold the trigger
                    
                    //System.out.println("The trigger is still being pressed");
                    
                    setGrabbedItemPosition();
                    
                }else{//trigger was not being pressed
                    
                    //System.out.println("The trigger has just started being pressed");
                    
                    if(!pointingDisplay&&!pointingButton&&!pointingElementButton&&!pointingPeriodicTableDisplay&&!pointingSubstanceButton&&!pointingMaterialButton){
                        
                        hand.setOpenned(false);
                        
                        //System.out.println("Hand set to closed");
                        
                        //if there is an item possible to grab
                        if(possibleItemToGrab!=null){

                            //System.out.println("Item prossible to grab not null, calling method to grab...");

                            grabGrabbableItem();

                        }
                        
                    }else if(pointingElementButton){
                        
                        presentPointedElementButton.activate(hand);
                        
                    }else if(pointingSubstanceButton){
                        
                        presentPointedSubstanceButton.activate();
                        
                    }else if(pointingMaterialButton){
                        
                        presentPointedMaterialButton.activate();
                        
                    }else if (pointingButton){
                        
                        presentPointedButton.activate(hand);
                        
                    }
                    
                    //add what happens when the player starts pressing the trigger
                    //if a button (display) is being pointed, trigger that button's action
                    //make the player grab an objet if it is available to be grabbed
                    
                }
                    
                triggerWasPressed=true;
                
        }else{//trigger is not being pressed

            if(triggerWasPressed){
                
                //System.out.println("The trigger has just been released");
                
                if(!hand.hasStaticHold()){
                    /*
                    if(((Grabbable)possibleItemToGrab).getSpatial().getControl(RigidBodyControl.class)!=null){
            
                        ((Grabbable)possibleItemToGrab).getSpatial().getControl(RigidBodyControl.class).setMass(1);
                        ((Grabbable)possibleItemToGrab).getSpatial().getControl(RigidBodyControl.class).setKinematic(false);
                        //possibleItemToGrab.getSpatial().getControl(RigidBodyControl.class).setKinematicSpatial(true);

                    }
                    */
                    if(hand.getHeldObject()!=null&&((Grabbable)hand.getHeldObject()).getGrabbablePosition().distance(hand.getWorldTranslation())<0.15f){
                        
                        ((Grabbable)hand.getHeldObject()).highlightVisible(true);
                        
                    }
                    
                    //Do a final action on the held object before it is dropped by the player
                    if(hand.getHeldObject()!=null&&hand.getHeldObject() instanceof Beaker){
                        
                        ((Beaker)hand.getHeldObject()).setVelocity(VRHardware.getVRinput().getVelocity(handSide).multLocal(-1,1,-1));
                        
                    }else if(hand.getHeldObject()!=null&&hand.getHeldObject() instanceof Erlenmeyer){
                        
                        ((Erlenmeyer)hand.getHeldObject()).setVelocity(VRHardware.getVRinput().getVelocity(handSide).multLocal(-1,1,-1));
                        
                    }else if(hand.getHeldObject()!=null&&hand.getHeldObject() instanceof TestTube){
                        
                        ((TestTube)hand.getHeldObject()).setVelocity(VRHardware.getVRinput().getVelocity(handSide).multLocal(-1,1,-1));
                        
                    }else if(hand.getHeldObject()!=null&&hand.getHeldObject() instanceof GasSac){
                        
                        ((GasSac)hand.getHeldObject()).setVelocity(VRHardware.getVRinput().getVelocity(handSide).multLocal(-1,1,-1));
                        
                    }
                
                    hand.setHeldObject(null);
                    
                    hand.setOpenned(true);
                    
                }else{
                    
                    triggerReleasedAfterHold=true;
                    
                }

                //add what happens when the player releases the trigger
                //if the player was holding an object and if statichold is not currently active, make them drop the item

            }else{//trigger was not being pressed
                
                //System.out.println("The trigger is still not being pressed");

                //add what happens when the player is still not pressing the trigger

            }

            triggerWasPressed=false;
            
            if(!hand.hasStaticHold()){
                    
                hand.setOpenned(true);
                    
            }

        }
        
    }
    
    private void updateTriggerByAxis(){
        
        if(hand.isHoldingObject()&&hand.getHeldObject() instanceof GasSac&&triggerReleasedAfterHold){
            
            if(FastMath.RAD_TO_DEG*VRHardware.getVRinput().getAxis(0,VRINPUT_TYPE.ViveTriggerAxis).x>10)
            
                ((GasSac)hand.getHeldObject()).getEvaporationParticleEmitter().setDelay(10/(FastMath.RAD_TO_DEG*VRHardware.getVRinput().getAxis(0,VRINPUT_TYPE.ViveTriggerAxis).x));
            
        }
        
        //rightHandSpatial.setLocalScale(VRHardware.getVRinput().getAxis(0,VRINPUT_TYPE.ViveTriggerAxis).x+0.1f);
        
    }
    
    private void updateMenuButton(){
        
        if(VRHardware.getVRinput().isButtonDown(handSide, OpenVRInput.VRINPUT_TYPE.ViveMenuButton)){//while its being held

            //System.out.println("Menu button pressed.");

            //check if there is an object in the hand
            if(hand.isHoldingObject()){

                hand.setDescriptionText(hand.getHeldObject().getDescription());
                hand.setDescriptionMovedOut(false);

                descriptionMovedOut=false;

            }else if(!hand.isHoldingObject()&&!laserActivatedByDisplay&&!laserActivatedByTeleportation){

                //System.out.println("Laser contact position: "+collisionResults.getCollision(presentCorrectCollisionIndex).getContactPoint());

                //update start and end points of laser to display it correctly
                if(foundPresentCorrectCollision)
                
                    hand.setLaserCoords(hand.getWorldTranslation(),collisionResults.getCollision(presentCorrectCollisionIndex).getContactPoint());

                laserActivatedByDescription=true;
                laserActivated=true;

                //set laserMovedOut to false because it has been displayed
                laserMovedOut=false;

                //set isPointingDescribable to false initially because this is checked in the next lines
                laserPointingAtDescribable=false;

                descriptionMovedOut=false;

                //System.out.println("Finding collision spatial's corresponding object..");

                if(correctCollisionSpatial.getUserData("correspondingObject")!=null&&correctCollisionSpatial.getUserData("correspondingObject") instanceof Describable){

                    //System.out.println("CorrectCollisionSpatial's corresponding object is describable, setting laser accordingly...");

                    laserPointingAtDescribable=true;

                }else if(correctCollisionSpatial.getUserData("correspondingObject")!=null&&!(correctCollisionSpatial.getUserData("correspondingObject") instanceof Describable)){

                    //System.out.println("CorrectCollisionSpatial's corresponding object is not describable, setting laser accordingly...");

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
            //Update due to menu button not being pressed
            //Keep in mind that this is on every loop
        }else if(!VRHardware.getVRinput().isButtonDown(handSide, OpenVRInput.VRINPUT_TYPE.ViveMenuButton)){

            //check if the laser is being used by something else
            //if it is, dont move it out of sight
            //if it isnt, move it out of sight
            if(!laserActivatedByTeleportation&&!laserActivatedByDisplay){

                //check if the laser is already out of sight
                if(!laserMovedOut){

                    //if not already out of sight, move laser out of sight
                    makeLaserDisappear();

                    //set laserMovedOut to true once the laser has been moved out
                    laserMovedOut=true;

                    laserActivatedByDescription=false;
                    laserActivated=false;

                }

            }

            //check if description is already out of sight
            if(!descriptionMovedOut){

                hand.setDescriptionMovedOut(true);
                hand.setDescriptionText("");

                descriptionMovedOut=true;

            }

        }
        
    }
    
    private void updateGripButton(){
        
        //This only checks if teh grip button has been clicked, not held or whatever
        //this is because it is only used to toggle the statichold
        
        if(VRHardware.getVRinput().wasButtonPressedSinceLastCall(handSide, OpenVRInput.VRINPUT_TYPE.ViveGripButton)){

            //Toggles between true or false for holding the object in place
            if(hand.hasStaticHold()){
                
                hand.setStaticHold(false);
                //System.out.println("Hand static hold set to false");

                if(!VRHardware.getVRinput().isButtonDown(handSide, OpenVRInput.VRINPUT_TYPE.ViveTriggerAxis)){

                    if(hand.getHeldObject()!=null&&((Grabbable)hand.getHeldObject()).getGrabbablePosition().distance(hand.getWorldTranslation())<0.15f){

                        ((Grabbable)hand.getHeldObject()).highlightVisible(true);

                    }

                    //Do a final action on the held object before it is dropped by the player
                    if(hand.getHeldObject()!=null&&hand.getHeldObject() instanceof Beaker){

                        ((Beaker)hand.getHeldObject()).setVelocity(VRHardware.getVRinput().getVelocity(handSide).multLocal(-1,1,-1));

                    }else if(hand.getHeldObject()!=null&&hand.getHeldObject() instanceof Erlenmeyer){

                        ((Erlenmeyer)hand.getHeldObject()).setVelocity(VRHardware.getVRinput().getVelocity(handSide).multLocal(-1,1,-1));

                    }else if(hand.getHeldObject()!=null&&hand.getHeldObject() instanceof TestTube){

                        ((TestTube)hand.getHeldObject()).setVelocity(VRHardware.getVRinput().getVelocity(handSide).multLocal(-1,1,-1));

                    }else if(hand.getHeldObject()!=null&&hand.getHeldObject() instanceof GasSac){

                        ((GasSac)hand.getHeldObject()).setVelocity(VRHardware.getVRinput().getVelocity(handSide).multLocal(-1,1,-1));

                    }

                    hand.setHeldObject(null);

                    hand.setOpenned(true);
                
                }

            }else{

                hand.setStaticHold(true);
                //System.out.println("Hand static hold set to true");
                
                triggerReleasedAfterHold=false;

            }

        }
        
    }
    
    private void updateTouchpad(){
        
        if(VRHardware.getVRinput().isButtonDown(handSide, OpenVRInput.VRINPUT_TYPE.ViveTouchpadAxis)){
                
            //The touchpad is now being pressed
            //Used later
            //should be changed to the new touchpadBeingPressed
            touchPadDown=true;

            //Get x and y position of the player's finger on the touchpad
            touchPadX=VRHardware.getVRinput().getAxis(handSide, OpenVRInput.VRINPUT_TYPE.ViveTouchpadAxis).getX();
            touchPadY=VRHardware.getVRinput().getAxis(handSide, OpenVRInput.VRINPUT_TYPE.ViveTouchpadAxis).getY();

            //System.out.println("Touchpad pressed:\n\tAxis: "+VRHardware.getVRinput().getAxis(handSide, OpenVRInput.VRINPUT_TYPE.ViveTouchpadAxis));

            //get the angle in rad between the x axis of the touchpad and the player's finger
            //convert the angle to degrees
            radTouchPadXAngle=Math.abs(Math.atan(touchPadY/touchPadX));
            degTouchPadXAngle=Math.toDegrees(radTouchPadXAngle);

            //System.out.println("\t\tAngle in rads compared to X axis: "+radTouchPadXAngle);
            //System.out.println("\t\tAngle in degrees compared to X axis: "+degTouchPadXAngle);


            if(touchPadX>0&&touchPadY>0){//Touchpad being pressed on quadrant #1

                if(degTouchPadXAngle>45){//Touchpad being pressed UP 

                    if(!touchPadPressedUp){//Touchpad has just started being pressed up
                        
                        System.out.println("touchpad has just started being pressed up");
                        
                        if(hand.isHoldingObject()&&hand.getHeldObject() instanceof GasSac){

                            ((GasSac)hand.getHeldObject()).getValve().toggle();

                        }else if(hand.isHoldingObject()&&hand.getHeldObject() instanceof Erlenmeyer){

                            System.out.println("    Hand is holding an item and the item is an erlenmeyer");
                            
                            ((Erlenmeyer)hand.getHeldObject()).toggleOpennedClosed();

                        }else if(hand.isHoldingObject()&&hand.getHeldObject() instanceof TestTube){

                            ((TestTube)hand.getHeldObject()).toggleOpennedClosed();

                        }

                    }
                    
                    //System.out.println("\t\tTouchPad: UP");
                    
                    touchPadPressedUp=true;
                    touchPadPressedDown=false;
                    touchPadPressedRight=false;
                    touchPadPressedLeft=false;

                    conditionalMoveTeleLaserOut();

                    teleportationPrimed=false;

                }else{//Touchpad being pressed RIGHT

                    if(!touchPadPressedRight){//Touchpad has just started being pressed right
                
                        

                    }
                    
                    //System.out.println("\t\tTouchPad: RIGHT");
                    touchPadPressedUp=false;
                    touchPadPressedDown=false;
                    touchPadPressedRight=true;
                    touchPadPressedLeft=false;

                    conditionalMoveTeleLaserOut();

                    teleportationPrimed=false;

                }

            }else if(touchPadX<0&&touchPadY>0){//Touchpad being pressed on quadrant #2

                if(degTouchPadXAngle>45){//Touchpad being pressed UP

                    if(!touchPadPressedUp){//touchpad has just started being pressed up
                
                        System.out.println("touchpad has just started being pressed up");
                        
                        if(hand.isHoldingObject()&&hand.getHeldObject() instanceof GasSac){

                            ((GasSac)hand.getHeldObject()).getValve().toggle();

                        }else if(hand.isHoldingObject()&&hand.getHeldObject() instanceof Erlenmeyer){

                            System.out.println("    Hand is holding an item and the item is an erlenmeyer");
                            
                            ((Erlenmeyer)hand.getHeldObject()).toggleOpennedClosed();

                        }else if(hand.isHoldingObject()&&hand.getHeldObject() instanceof TestTube){

                            ((TestTube)hand.getHeldObject()).toggleOpennedClosed();

                        }

                    }
                    
                    //System.out.println("\t\tTouchPad: UP");
                    touchPadPressedUp=true;
                    touchPadPressedDown=false;
                    touchPadPressedRight=false;
                    touchPadPressedLeft=false;

                    conditionalMoveTeleLaserOut();

                    teleportationPrimed=false;


                }else{//Touchpad being pressed LEFT

                    if(!touchPadPressedLeft){//Touchpad has just started being pressed left
                
                        

                    }
                    
                    //System.out.println("\t\tTouchPad: LEFT");
                    touchPadPressedUp=false;
                    touchPadPressedDown=false;
                    touchPadPressedRight=false;
                    touchPadPressedLeft=true;

                    conditionalMoveTeleLaserOut();

                    teleportationPrimed=false;

                }

            }else if(touchPadX<0&&touchPadY<0){//Touchpad being pressed in quadrant #3

                if(degTouchPadXAngle>45){//Touchpad being presse DOWN

                    if(!touchPadPressedDown){//Touchpad has just started being pressed down
                
                        

                    }
                    
                    //System.out.println("\t\tTouchPad: DOWN");
                    touchPadPressedUp=false;
                    touchPadPressedDown=true;
                    touchPadPressedRight=false;
                    touchPadPressedLeft=false;

                    processTeleportation();

                }else{//Touchpad being pressed LEFT

                    if(!touchPadPressedLeft){//Touchpad has just started being pressed left
                
                        

                    }
                    
                    //System.out.println("\t\tTouchPad: LEFT");
                    touchPadPressedUp=false;
                    touchPadPressedDown=false;
                    touchPadPressedRight=false;
                    touchPadPressedLeft=true;

                    conditionalMoveTeleLaserOut();

                    teleportationPrimed=false;

                }

            }else if(touchPadX>0&&touchPadY<0){//Touchpad being pressed on quadrant #4

                if(degTouchPadXAngle>45){//Touchpad being pressed DOWN

                    if(!touchPadPressedDown){//Touchpad has just started being pressed down
                
                        

                    }
                    
                    //System.out.println("\t\tTouchPad: DOWN");
                    touchPadPressedUp=false;
                    touchPadPressedDown=true;
                    touchPadPressedRight=false;
                    touchPadPressedLeft=false;

                    processTeleportation();

                }else{//Touchpad being pressed RIGHT

                    if(!touchPadPressedRight){//Touchpad has just started being pressed right
                
                        

                    }
                    
                    //System.out.println("\t\tTouchPad: RIGHT");
                    touchPadPressedUp=false;
                    touchPadPressedDown=false;
                    touchPadPressedRight=true;
                    touchPadPressedLeft=false;

                    conditionalMoveTeleLaserOut();

                    teleportationPrimed=false;

                }

            }

        }

        //Checks if the touchpad was being pressed but isn't anymore\
        //Meaning that the touchpad has been released
        if(touchPadDown&&!VRHardware.getVRinput().isButtonDown(handSide, OpenVRInput.VRINPUT_TYPE.ViveTouchpadAxis)){

            //System.out.println("Touchpad released");
            if(touchPadPressedUp){
                
                
                
            }else if(touchPadPressedDown){

                if(teleportationPrimed){

                    if(player.getHand(0)!=null&&player.getHand(0).getHeldObject()!=null)

                        player.getHand(0).getHeldObject().setPos(player.getHand(0).getWorldTranslation());

                    if(player.getHand(1)!=null&&player.getHand(1).getHeldObject()!=null)

                        player.getHand(1).getHeldObject().setPos(player.getHand(1).getWorldTranslation());

                    player.teleportArea(collisionResults.getCollision(presentCorrectCollisionIndex).getContactPoint());

                }
                    
            }else if(touchPadPressedRight){
                
                
                
            }else if(touchPadPressedLeft){
                
                
                
            }
            
            conditionalMoveTeleLaserOut();
            
            touchPadPressedUp=false;
            touchPadPressedDown=false;
            touchPadPressedRight=false;
            touchPadPressedLeft=false;

            touchPadDown=false;

        }
        
    }
    
    private void updateLaser(){
        
        //System.out.println(laserMovedOut);
        
        //check if laser is being used
        //if it isn't check if player is pointing a display
        if(!laserActivatedByDescription&&!laserActivatedByTeleportation){

            //System.out.println("Laser not in use, checking is player is pointing at a display...");

            //We have the correct collision in the list
            //Now we check to see if that colision is a display or button
            if(foundPresentCorrectCollision){
            
                if(collisionResults.getCollision(presentCorrectCollisionIndex).getGeometry().getUserData("correspondingObject") instanceof Button){

                    if(presentPointedButton!=null&&!((Button)collisionResults.getCollision(presentCorrectCollisionIndex).getGeometry().getUserData("correspondingObject")).equals(presentPointedButton)){

                        presentPointedButton.setPointed(false);

                    }

                    pointingElementButton=false;
                    pointingPeriodicTableDisplay=false;
                    pointingButton=true;
                    pointingDisplay=false;
                    pointingSubstanceButton=false;
                    pointingMaterialButton=false;

                    if(!((Button)collisionResults.getCollision(presentCorrectCollisionIndex).getGeometry().getUserData("correspondingObject")).isGrayedOut())
                    
                        makeLaserAppear(collisionResults.getCollision(presentCorrectCollisionIndex).getContactPoint(),GREEN_LASER);
                    
                    else
                        
                        makeLaserAppear(collisionResults.getCollision(presentCorrectCollisionIndex).getContactPoint(),RED_LASER);

                    presentPointedButton=((Button)collisionResults.getCollision(presentCorrectCollisionIndex).getGeometry().getUserData("correspondingObject"));
                    presentPointedElementButton=null;
                    presentPointedSubstanceButton=null;
                    presentPointedMaterialButton=null;

                    presentPointedButton.setPointed(true);
                    
                    laserActivatedByDisplay=true;

                }else if(collisionResults.getCollision(presentCorrectCollisionIndex).getGeometry().getUserData("correspondingObject") instanceof Display){

                    pointingElementButton=false;
                    pointingPeriodicTableDisplay=false;
                    pointingButton=false;
                    pointingDisplay=true;
                    pointingSubstanceButton=false;
                    pointingMaterialButton=false;

                    makeLaserAppear(collisionResults.getCollision(presentCorrectCollisionIndex).getContactPoint(),RED_LASER);

                    if(presentPointedButton!=null){

                        presentPointedButton.setPointed(false);

                        presentPointedButton=null;

                    }

                    presentPointedElementButton=null;
                    presentPointedSubstanceButton=null;
                    presentPointedMaterialButton=null;
                    
                    laserActivatedByDisplay=true;

                }else if(collisionResults.getCollision(presentCorrectCollisionIndex).getGeometry().getUserData("correspondingObject") instanceof ElementButton){

                    pointingElementButton=true;
                    pointingPeriodicTableDisplay=false;
                    pointingButton=false;
                    pointingDisplay=false;
                    pointingSubstanceButton=false;
                    pointingMaterialButton=false;

                    if(!((ElementButton)collisionResults.getCollision(presentCorrectCollisionIndex).getGeometry().getUserData("correspondingObject")).isGrayedOut())
                    
                        makeLaserAppear(collisionResults.getCollision(presentCorrectCollisionIndex).getContactPoint(),GREEN_LASER);
                    
                    else
                        
                        makeLaserAppear(collisionResults.getCollision(presentCorrectCollisionIndex).getContactPoint(),RED_LASER);

                    presentPointedElementButton=((ElementButton)collisionResults.getCollision(presentCorrectCollisionIndex).getGeometry().getUserData("correspondingObject"));
                    presentPointedButton=null;
                    presentPointedSubstanceButton=null;
                    presentPointedMaterialButton=null;
                    
                    laserActivatedByDisplay=true;

                }else if(collisionResults.getCollision(presentCorrectCollisionIndex).getGeometry().getUserData("correspondingObject") instanceof PeriodicTableDisplay){

                    pointingElementButton=false;
                    pointingPeriodicTableDisplay=true;
                    pointingButton=false;
                    pointingDisplay=false;
                    pointingSubstanceButton=false;
                    pointingMaterialButton=false;

                    makeLaserAppear(collisionResults.getCollision(presentCorrectCollisionIndex).getContactPoint(),RED_LASER);

                    presentPointedButton=null;
                    presentPointedElementButton=null;
                    presentPointedSubstanceButton=null;
                    presentPointedMaterialButton=null;
                    
                    laserActivatedByDisplay=true;

                }else if(collisionResults.getCollision(presentCorrectCollisionIndex).getGeometry().getUserData("correspondingObject") instanceof SubstanceButton){

                    pointingElementButton=false;
                    pointingPeriodicTableDisplay=false;
                    pointingButton=false;
                    pointingDisplay=false;
                    pointingSubstanceButton=true;
                    pointingMaterialButton=false;

                    makeLaserAppear(collisionResults.getCollision(presentCorrectCollisionIndex).getContactPoint(),GREEN_LASER);
                    
                    if(presentPointedButton!=null){

                        presentPointedButton.setPointed(false);

                        presentPointedButton=null;

                    }

                    presentPointedElementButton=null;
                    presentPointedSubstanceButton=((SubstanceButton)collisionResults.getCollision(presentCorrectCollisionIndex).getGeometry().getUserData("correspondingObject"));
                    presentPointedMaterialButton=null;
                    
                    laserActivatedByDisplay=true;

                }else if(collisionResults.getCollision(presentCorrectCollisionIndex).getGeometry().getUserData("correspondingObject") instanceof MaterialButton){

                    pointingElementButton=false;
                    pointingPeriodicTableDisplay=false;
                    pointingButton=false;
                    pointingDisplay=false;
                    pointingSubstanceButton=false;
                    pointingMaterialButton=true;

                    makeLaserAppear(collisionResults.getCollision(presentCorrectCollisionIndex).getContactPoint(),GREEN_LASER);
                    
                    if(presentPointedButton!=null){

                        presentPointedButton.setPointed(false);

                        presentPointedButton=null;

                    }

                    presentPointedElementButton=null;
                    presentPointedSubstanceButton=null;
                    presentPointedMaterialButton=((MaterialButton)collisionResults.getCollision(presentCorrectCollisionIndex).getGeometry().getUserData("correspondingObject"));
                    
                    laserActivatedByDisplay=true;

                }else{

                    pointingButton=false;
                    pointingDisplay=false;
                    pointingElementButton=false;
                    pointingPeriodicTableDisplay=false;
                    pointingSubstanceButton=false;
                    pointingMaterialButton=false;

                    if(presentPointedButton!=null){

                        presentPointedButton.setPointed(false);

                        presentPointedButton=null;

                    }
                    
                    presentPointedSubstanceButton=null;
                    presentPointedElementButton=null;
                    presentPointedMaterialButton=null;
                    
                    laserActivatedByDisplay=false;
                    
                    makeLaserDisappear();

                }
            
            }

        }
        
    }
    
    private void makeLaserAppear(Vector3f endPoint,ColorRGBA color){
        
        //System.out.println("makeLaserAppear has been called");
        
        hand.setLaserMaterialColor("Color",color);
        
        hand.setLaserCoords(hand.getWorldTranslation(),endPoint);
        
        laserMovedOut=false;
        
    }
    
    private void makeLaserDisappear(){
        
        hand.setLaserCoords(OUT_OF_MAP,OUT_OF_MAP);
        
        laserMovedOut=true;
        
    }
    
    private void grabGrabbableItem(){
        
        rotationWhenGrabbed=VRHardware.getVRinput().getFinalObserverRotation(handSide);
        
        //System.out.println("Grab grabbable item called, setting hand's held object to possible item to grab...");
        
        hand.setHeldObject(possibleItemToGrab);
        
        /*
        if(((Grabbable)possibleItemToGrab).getSpatial().getControl(RigidBodyControl.class)!=null){
            
            ((Grabbable)possibleItemToGrab).getSpatial().getControl(RigidBodyControl.class).setMass(1);
            ((Grabbable)possibleItemToGrab).getSpatial().getControl(RigidBodyControl.class).setKinematic(true);
            //possibleItemToGrab.getSpatial().getControl(RigidBodyControl.class).setKinematicSpatial(true);
            
        }
        */
        
        ((Grabbable)hand.getHeldObject()).highlightVisible(false);
        
        //System.out.println("calling method to inittially position grabebd item...");
        
        setGrabbedItemPosition();
        
    }
    
    public void setGrabbedItemPosition(){
        
        //System.out.println("Present held item: "+hand.getHeldObject());
        
        //System.out.println("setting the item's position depending on what it is...");
        
        //case by case behavior
        if(hand.getHeldObject()!=null&&hand.getHeldObject() instanceof FumeHoodDoor&&hand.getWorldTranslation().getY()<=1.96&&hand.getWorldTranslation().getY()>=1.02f){
            
            //System.out.println("Held ojbject is not null, it is a fume hood door, the hand's Y position is less or equal to 2 and higher or equal to 1.02");
            
            ((FumeHoodDoor)hand.getHeldObject()).setPos(new Vector3f(2.97f,hand.getWorldTranslation().getY(),5.04f));
            
        }else if(hand.getHeldObject()!=null&&hand.getHeldObject() instanceof DistilledWaterContainer){
            
            //System.out.println("Held object is not null, it is a distilled water container");
            
            ((DistilledWaterContainer)hand.getHeldObject()).toggle();
            
            ((DistilledWaterContainer)hand.getHeldObject()).highlightVisible(true);
            
            hand.setHeldObject(null);
            
        }else if(hand.getHeldObject()!=null&&hand.getHeldObject() instanceof SinkHandle){
            
            //System.out.println("Setting SinkHandle rotation to +"+FastMath.RAD_TO_DEG*(FastMath.asin(VRHardware.getVRinput().getOrientation(handSide).getRotationColumn(1).getX())-FastMath.asin(rotationOnLastFrame.getRotationColumn(1).getX())));
            
            if(VRHardware.getVRinput().getOrientation(handSide).getRotationColumn(1).getY()>0&&rotationOnLastFrame.getRotationColumn(1).getY()>0){
            
                ((SinkHandle)hand.getHeldObject()).rotate(FastMath.RAD_TO_DEG*(FastMath.asin(VRHardware.getVRinput().getOrientation(handSide).getRotationColumn(1).getX())-FastMath.asin(rotationOnLastFrame.getRotationColumn(1).getX())));
            
            }else if(VRHardware.getVRinput().getOrientation(handSide).getRotationColumn(1).getY()<0&&rotationOnLastFrame.getRotationColumn(1).getY()<0){
                
                ((SinkHandle)hand.getHeldObject()).rotate(FastMath.RAD_TO_DEG*(FastMath.asin(rotationOnLastFrame.getRotationColumn(1).getX())-FastMath.asin(VRHardware.getVRinput().getOrientation(handSide).getRotationColumn(1).getX())));
                
            }else if(VRHardware.getVRinput().getOrientation(handSide).getRotationColumn(1).getY()<0&&rotationOnLastFrame.getRotationColumn(1).getY()>0){
                
                ((SinkHandle)hand.getHeldObject()).rotate(FastMath.RAD_TO_DEG*(FastMath.asin(rotationOnLastFrame.getRotationColumn(1).getY())+FastMath.abs(FastMath.asin(VRHardware.getVRinput().getOrientation(handSide).getRotationColumn(1).getY()))));
                
            }else if(VRHardware.getVRinput().getOrientation(handSide).getRotationColumn(1).getY()>0&&rotationOnLastFrame.getRotationColumn(1).getY()<0){
                
                ((SinkHandle)hand.getHeldObject()).rotate(FastMath.RAD_TO_DEG*(FastMath.asin(rotationOnLastFrame.getRotationColumn(1).getY())+(-1*FastMath.asin(VRHardware.getVRinput().getOrientation(handSide).getRotationColumn(1).getY()))));
                
            }else if(VRHardware.getVRinput().getOrientation(handSide).getRotationColumn(1).getY()==0){
                
                ((SinkHandle)hand.getHeldObject()).rotate(FastMath.RAD_TO_DEG*(FastMath.asin(rotationOnLastFrame.getRotationColumn(1).getY())));
                
            }else if(rotationOnLastFrame.getRotationColumn(1).getY()==0){
                
                ((SinkHandle)hand.getHeldObject()).rotate(FastMath.RAD_TO_DEG*((-1*FastMath.asin(rotationOnLastFrame.getRotationColumn(1).getY()))));
                
            }
            
            rotationWhenGrabbed=VRHardware.getVRinput().getFinalObserverRotation(handSide);
            
        }else if(hand.getHeldObject()!=null&&hand.getHeldObject() instanceof Beaker){
            
            //System.out.println("Object is a beaker, setting its position to hand position: "+hand.getWorldTranslation()+" and rotation to hand rotation: "+hand.getRotation());
            
            if(hand.getSide()==0){
                
                ((Beaker)hand.getHeldObject()).setPos(hand.getWorldTranslation().add(hand.getRotation().mult(new Vector3f(0.05f,0,0.02f))));
                ((Beaker)hand.getHeldObject()).setRotation(hand.getRotation());
                
            }else{
                
                ((Beaker)hand.getHeldObject()).setRotation(hand.getRotation().mult(new Quaternion().fromAngleAxis(FastMath.PI,Vector3f.UNIT_Y)));
                ((Beaker)hand.getHeldObject()).setPos(hand.getWorldTranslation().add(hand.getRotation().mult(new Vector3f(-0.05f,0,0.02f))));
                
            }
            
            //((Beaker)hand.getHeldObject()).clearForces(); Doesnt remove the velocity of the object
            
            //((Beaker)hand.getHeldObject()).updatePhysicsLocation();
            ((Beaker)hand.getHeldObject()).clearVelocity();
            
            //System.out.println("All forces have been cleared on beaker being held");
            
        }else if(hand.getHeldObject()!=null&&hand.getHeldObject() instanceof Erlenmeyer){
            
            if(hand.getSide()==0){
                
                ((Erlenmeyer)hand.getHeldObject()).setPos(hand.getWorldTranslation().add(hand.getRotation().mult(new Vector3f(0.05f,0,0.02f))));
                ((Erlenmeyer)hand.getHeldObject()).setRotation(hand.getRotation());
                
            }else{
                
                ((Erlenmeyer)hand.getHeldObject()).setRotation(hand.getRotation());
                ((Erlenmeyer)hand.getHeldObject()).setPos(hand.getWorldTranslation().add(hand.getRotation().mult(new Vector3f(-0.05f,0,0.02f))));
                
            }
            
            ((Erlenmeyer)hand.getHeldObject()).clearVelocity();
            
        }else if(hand.getHeldObject()!=null&&hand.getHeldObject() instanceof TestTube){
            
            if(hand.getSide()==0){
                
                ((TestTube)hand.getHeldObject()).setPos(hand.getWorldTranslation().add(hand.getRotation().mult(new Vector3f(0.05f,0,0.04f))));
                ((TestTube)hand.getHeldObject()).setRotation(hand.getRotation());
                
            }else{
                
                ((TestTube)hand.getHeldObject()).setRotation(hand.getRotation());
                ((TestTube)hand.getHeldObject()).setPos(hand.getWorldTranslation().add(hand.getRotation().mult(new Vector3f(-0.05f,0,0.04f))));
                
            }
            
            ((TestTube)hand.getHeldObject()).clearVelocity();
            
        }else if(hand.getHeldObject()!=null&&hand.getHeldObject() instanceof GasSac){
            
            //System.out.println("Object is a GasSac, setting its position to hand position: "+hand.getWorldTranslation()+" and rotation to hand rotation: "+hand.getRotation());
            
            if(hand.getSide()==0){
                
                ((GasSac)hand.getHeldObject()).setPos(hand.getWorldTranslation().add(hand.getRotation().mult(new Vector3f(0.1f,0,0.045f))));
                ((GasSac)hand.getHeldObject()).setRotation(hand.getRotation());
                
            }else{
                
                ((GasSac)hand.getHeldObject()).setRotation(hand.getRotation().mult(new Quaternion().fromAngleAxis(FastMath.PI,Vector3f.UNIT_Y)));
                ((GasSac)hand.getHeldObject()).setPos(hand.getWorldTranslation().add(hand.getRotation().mult(new Vector3f(-0.1f,0,0.045f))));
                
            }
            
            ((GasSac)hand.getHeldObject()).clearVelocity();
            
            //System.out.println("All forces have been cleared on GasSac being held");
            
        }else if(hand.getHeldObject()!=null&&hand.getHeldObject() instanceof GasSacValve){
            
            ((GasSacValve)hand.getHeldObject()).toggle();
            
            ((GasSacValve)hand.getHeldObject()).highlightVisible(true);
            
            hand.setHeldObject(null);
            
        }else if(hand.getHeldObject()!=null&&hand.getHeldObject() instanceof HotPlate){
            
            //System.out.println("Setting SinkHandle rotation to +"+FastMath.RAD_TO_DEG*(FastMath.asin(VRHardware.getVRinput().getOrientation(handSide).getRotationColumn(1).getX())-FastMath.asin(rotationOnLastFrame.getRotationColumn(1).getX())));
            
            if(VRHardware.getVRinput().getOrientation(handSide).getRotationColumn(1).getY()>0&&rotationOnLastFrame.getRotationColumn(1).getY()>0){
            
                ((HotPlate)hand.getHeldObject()).rotateDial(FastMath.RAD_TO_DEG*(FastMath.asin(VRHardware.getVRinput().getOrientation(handSide).getRotationColumn(1).getX())-FastMath.asin(rotationOnLastFrame.getRotationColumn(1).getX())));
            
            }else if(VRHardware.getVRinput().getOrientation(handSide).getRotationColumn(1).getY()<0&&rotationOnLastFrame.getRotationColumn(1).getY()<0){
                
                ((HotPlate)hand.getHeldObject()).rotateDial(FastMath.RAD_TO_DEG*(FastMath.asin(rotationOnLastFrame.getRotationColumn(1).getX())-FastMath.asin(VRHardware.getVRinput().getOrientation(handSide).getRotationColumn(1).getX())));
                
            }else if(VRHardware.getVRinput().getOrientation(handSide).getRotationColumn(1).getY()<0&&rotationOnLastFrame.getRotationColumn(1).getY()>0){
                
                ((HotPlate)hand.getHeldObject()).rotateDial(FastMath.RAD_TO_DEG*(FastMath.asin(rotationOnLastFrame.getRotationColumn(1).getY())+FastMath.abs(FastMath.asin(VRHardware.getVRinput().getOrientation(handSide).getRotationColumn(1).getY()))));
                
            }else if(VRHardware.getVRinput().getOrientation(handSide).getRotationColumn(1).getY()>0&&rotationOnLastFrame.getRotationColumn(1).getY()<0){
                
                ((HotPlate)hand.getHeldObject()).rotateDial(FastMath.RAD_TO_DEG*(FastMath.asin(rotationOnLastFrame.getRotationColumn(1).getY())+(-1*FastMath.asin(VRHardware.getVRinput().getOrientation(handSide).getRotationColumn(1).getY()))));
                
            }else if(VRHardware.getVRinput().getOrientation(handSide).getRotationColumn(1).getY()==0){
                
                ((HotPlate)hand.getHeldObject()).rotateDial(FastMath.RAD_TO_DEG*(FastMath.asin(rotationOnLastFrame.getRotationColumn(1).getY())));
                
            }else if(rotationOnLastFrame.getRotationColumn(1).getY()==0){
                
                ((HotPlate)hand.getHeldObject()).rotateDial(FastMath.RAD_TO_DEG*((-1*FastMath.asin(rotationOnLastFrame.getRotationColumn(1).getY()))));
                
            }
            
            rotationWhenGrabbed=VRHardware.getVRinput().getFinalObserverRotation(handSide);
            
        }
        
    }
    
    private void updateHold(){
        
        if(hand.hasStaticHold())
        
            setGrabbedItemPosition();
        
    }
    
}
