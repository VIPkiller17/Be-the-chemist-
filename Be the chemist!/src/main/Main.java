package main;

import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import interfaces.Describable;
import java.util.ArrayList;
import jmevr.app.VRApplication;
import jmevr.input.OpenVR;
import jmevr.input.VRAPI;
import objects.player.Player;
import objects.world.Floor;
import objects.world.Room;
//by Tommy
public class Main extends VRApplication {
    
    //Other/Logic
    private static VRAPI VRHardware=new OpenVR();
    
    private CollisionResults collisionResults=new CollisionResults();
    
    //Player
    Spatial observer;
    //protected Geometry player;
    
    private Player playerLogic;
    
    private boolean rightHandCreated,leftHandCreated;
    
    //TPF counters
    private float controllerCountDispTPF;
    private float CommonTPF;
    private float controllerConnectionTPF;
    
    //World
    private Room testRoom;
    private Floor testFloor;
    
    //Objects
    
    private ArrayList<Describable> describables=new ArrayList<Describable>();

    public static void main(String[] args) {
        
        Main app = new Main();
        
        //Set the frustrum distances
        app.preconfigureFrustrumNearFar(0.01f,512f);
        
        //set mirror window size
        app.preconfigureMirrorWindowSize(1280, 720);
        
        //Enables the mirror window, displays the game on the computer also
        app.preconfigureVRApp(PRECONFIG_PARAMETER.ENABLE_MIRROR_WINDOW,true);
        
        //Makes it so that the game pauses on loss of focus
        app.setPauseOnLostFocus(true);
        
        //starts the game
        app.start();
    }
    
    @Override
    public void simpleInitApp() {
        
        //LOAD SPATIALS START
        
        //OBSERVER INIT (PLAYER,HMD,THE HEADSET) START
        observer = new Node("observer");
        observer.setLocalTranslation(new Vector3f(0.0f, 0.0f, 0.0f));
        VRApplication.setObserver(observer);
        //OBSERVER INIT END
        
        //init playerObject
        playerLogic=new Player(getAssetManager(),rootNode,VRHardware,collisionResults,describables,observer);
        
        //TEST WORLD INIT START
        testRoom=new Room(getAssetManager(),rootNode);
        
        testFloor=new Floor(getAssetManager(),rootNode);
        //TEST WORLD INIT END
        
        //OBJECTS INIT START
        //collisionPrism0=new CustomTestObject(rootNode,getAssetManager(),describables,1f,0.5f,-0.6f,"Models/Testing/Collisions/ColPrism.j3o");
        //collisionPrism1=new CustomTestObject(rootNode,getAssetManager(),describables,1f,0.5f,0f,"Models/Testing/Collisions/ColPrismRightHand.j3o");
        //collisionPrism2=new CustomTestObject(rootNode,getAssetManager(),describables,1f,0.5f,0.6f,"Models/Testing/Collisions/ColPrismLeftHand.j3o");
        //collisionPlain0=new CustomTestObject(rootNode,getAssetManager(),describables,-1f,0.5f,-0.6f,"Models/Testing/Collisions/ColPlain.j3o");
        //collisionPlain1=new CustomTestObject(rootNode,getAssetManager(),describables,-1f,0.5f,0f,"Models/Testing/Collisions/ColPlainRightHand.j3o");
        //collisionPlain2=new CustomTestObject(rootNode,getAssetManager(),describables,-1f,0.5f,0.6f,"Models/Testing/Collisions/ColPlainLeftHand.j3o");
        //OBJECTS INIT END
        
        //LIGHT INIT START
        PointLight light = new PointLight(new Vector3f(0f,0.5f,0f));
        light.setColor(ColorRGBA.White);
        rootNode.addLight(light);
        //LIGHT INIT END
        
        //LOAD SPACIALS END
        
        //INIT THE INPUTS START
        initInputs();
        //INIT THE INPUTS END
        
    }

    //vt add
    private void initInputs() {
        
        //non-VR inputs for testing without VR
        getInputManager().addMapping("quit", new KeyTrigger(KeyInput.KEY_ESCAPE));
        getInputManager().addMapping("forward", new KeyTrigger(KeyInput.KEY_W));
        getInputManager().addMapping("backward", new KeyTrigger(KeyInput.KEY_S));
        getInputManager().addMapping("right", new KeyTrigger(KeyInput.KEY_D));
        getInputManager().addMapping("left", new KeyTrigger(KeyInput.KEY_A));
        getInputManager().addMapping("up", new KeyTrigger(KeyInput.KEY_SPACE));
        getInputManager().addMapping("downControl", new KeyTrigger(KeyInput.KEY_LCONTROL));
        getInputManager().addMapping("downShift", new KeyTrigger(KeyInput.KEY_LSHIFT));

        //inputs' action listener
        ActionListener acl = new ActionListener() {
            
            public void onAction(String name, boolean keyPressed, float tpf) {
                
                if(name.equals("quit")){
                    
                    System.out.println("initInput: quit key pressed, quiting...");
                    
                    System.exit(0);
                    
                    //observer.move(VRApplication.getFinalObserverRotation().getRotationColumn(0).mult(1f));
                    
                }
                
            }
            
        };
        
        //inputs' analog listener
        AnalogListener anl = new AnalogListener() {
            
            public void onAnalog(String name, float value, float tpf) {
                
                if(name.equals("forward")){
                    
                    playerLogic.move(name,0.1f);
                    
                }else if(name.equals("backward")){
                    
                    playerLogic.move(name,0.1f);
                    
                }else if(name.equals("right")){
                    
                    playerLogic.move(name,0.1f);
                    
                }else if(name.equals("left")){
                    
                    playerLogic.move(name,0.1f);
                    
                }else if(name.equals("up")){
                    
                    playerLogic.move(name,0.1f);
                    
                }else if(name.equals("downControl")||name.equals("downShift")){
                    
                    playerLogic.move(name,0.1f);
                    
                }
                
            }
            
        };

        //adding the listener for each mapping to inputManager
        getInputManager().addListener(acl, "quit");
        getInputManager().addListener(anl, "forward");
        getInputManager().addListener(anl, "backward");
        getInputManager().addListener(anl, "right");
        getInputManager().addListener(anl, "left");
        getInputManager().addListener(anl, "up");
        getInputManager().addListener(anl, "downControl");
        getInputManager().addListener(anl, "downShift");
        
    }

    @Override
    public void simpleUpdate(float tpf) {
        
        //TPF COUNTERS START
        
        //every second check if a controller is detected and create it
        //if one of the hands isn't created
        if(!rightHandCreated||!leftHandCreated){
        
            //add to the TPF counter
            controllerConnectionTPF+=tpf;
            
            //if its been a second since last check
            if(controllerConnectionTPF>=1){
                
                //update the number of controllers connected
                VRHardware.getVRinput()._updateConnectedControllers();

                //if there is at least one controller and right hand has not been created yet
                if(VRHardware.getVRinput().getTrackedControllerCount()>0&&!rightHandCreated){

                    //create right hand
                    playerLogic.createHand(0);
                    
                    //set right hand created to true
                    rightHandCreated=true;

                }
                
                //if there aer at least 2 controllers but left hand has not been created yet
                if(VRHardware.getVRinput().getTrackedControllerCount()>1&&!leftHandCreated){

                    //create left hand
                    playerLogic.createHand(1);
                    
                    //set left hand created to true
                    leftHandCreated=true;

                }
                
                //reset the counter
                controllerConnectionTPF=0;

            }
        
        }
        
        //display number of tracked controllers every 10 seconds
        controllerCountDispTPF+=tpf;
        if(controllerCountDispTPF>=10){
            
            System.out.println("Controller count: "+VRHardware.getVRinput().getTrackedControllerCount());
            controllerCountDispTPF=0;
            
        }
        
        //Common TPF counter
        CommonTPF+=tpf;
        if(CommonTPF>=3){
            
            CommonTPF=0;
            
        }
        
        //TPF COUNTERS END
        
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}