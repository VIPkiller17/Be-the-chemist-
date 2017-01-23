package main;

import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import interfaces.Describable;
import java.util.ArrayList;
import jmevr.app.VRApplication;
import jmevr.input.OpenVR;
import jmevr.input.OpenVRInput;
import jmevr.input.VRAPI;
import jmevr.input.VRInputAPI;
import objects.CustomTestObject;
import objects.DefaultTestObject;
import worldObjects.player.Hand;
import worldObjects.player.Player;
import worldObjects.staticWorld.testing.TestFloor;
import worldObjects.staticWorld.testing.TestRoom;

/**
 * test
 * @author normenhansen
 */
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
    private TestRoom testRoom;
    private TestFloor testFloor;
    
    //Objects
    private CustomTestObject collisionPrism0,collisionPrism1,collisionPrism2,collisionPlain0,collisionPlain1,collisionPlain2;
    
    private ArrayList<Describable> describables=new ArrayList<Describable>();

    public static void main(String[] args) {
        
        Main app = new Main();
        
        //Set the frustrum distances
        app.preconfigureFrustrumNearFar(0.01f,512f);
        
        //set mirror window size
        app.preconfigureMirrorWindowSize(1280, 720);
        
        //Enables the mirror window, displays the game on the computer also
        app.preconfigureVRApp(PRECONFIG_PARAMETER.ENABLE_MIRROR_WINDOW,true);
        
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
        testRoom=new TestRoom(getAssetManager(),rootNode);
        
        testFloor=new TestFloor(getAssetManager(),rootNode);
        //TEST WORLD INIT END
        
        //OBJECTS INIT START
        collisionPrism0=new CustomTestObject(rootNode,getAssetManager(),describables,1f,0.5f,-0.6f,"Models/Testing/Collisions/ColPrism.j3o");
        collisionPrism1=new CustomTestObject(rootNode,getAssetManager(),describables,1f,0.5f,0f,"Models/Testing/Collisions/ColPrismRightHand.j3o");
        collisionPrism2=new CustomTestObject(rootNode,getAssetManager(),describables,1f,0.5f,0.6f,"Models/Testing/Collisions/ColPrismLeftHand.j3o");
        collisionPlain0=new CustomTestObject(rootNode,getAssetManager(),describables,-1f,0.5f,-0.6f,"Models/Testing/Collisions/ColPlain.j3o");
        collisionPlain1=new CustomTestObject(rootNode,getAssetManager(),describables,-1f,0.5f,0f,"Models/Testing/Collisions/ColPlainRightHand.j3o");
        collisionPlain2=new CustomTestObject(rootNode,getAssetManager(),describables,-1f,0.5f,0.6f,"Models/Testing/Collisions/ColPlainLeftHand.j3o");
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
        
        //Quit here is used as exmaple if the need arises for more non-Vive inputs
        getInputManager().addMapping("quit", new KeyTrigger(KeyInput.KEY_ESCAPE));

        ActionListener acl = new ActionListener() {
            
            public void onAction(String name, boolean keyPressed, float tpf) {
                
                if(name.equals("quit")){
                    
                    System.out.println("initInput: quit key pressed, quiting...");
                    
                    System.exit(0);
                    
                    //observer.move(VRApplication.getFinalObserverRotation().getRotationColumn(0).mult(1f));
                    
                }
                
            }
            
        };

        getInputManager().addListener(acl, "quit");
        
    }

    @Override
    public void simpleUpdate(float tpf) {
        
        //TPF COUNTERS START
        
        //every second check if a controller is detected and create it
        if(!rightHandCreated||!leftHandCreated){
        
            controllerConnectionTPF+=tpf;
            
            if(controllerConnectionTPF>=1){
                
                VRHardware.getVRinput()._updateConnectedControllers();

                if(VRHardware.getVRinput().getTrackedControllerCount()>0&&!rightHandCreated){

                    playerLogic.createHand(0);
                    
                    rightHandCreated=true;

                }
                
                if(VRHardware.getVRinput().getTrackedControllerCount()>1&&!leftHandCreated){

                    playerLogic.createHand(1);
                    
                    leftHandCreated=true;

                }
                
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