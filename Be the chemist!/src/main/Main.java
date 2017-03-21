package main;

import com.jme3.bullet.BulletAppState;
import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import interfaces.Describable;
import java.util.ArrayList;
import jmevr.app.VRApplication;
import jmevr.input.OpenVR;
import jmevr.input.VRAPI;
import objects.PhysicalObject;
import objects.apparatus.analyticalBalance.AnalyticalBalance;
import objects.apparatus.distilledWaterContainer.DistilledWaterContainer;
import objects.apparatus.fumeHood.FumeHood;
import objects.apparatus.trashBin.TrashBin;
import objects.player.Player;
import objects.world.Floor;
import objects.world.Room;
import objects.world.display.Display;

//by Tommy
public class Main extends VRApplication {
    
    //Other/Logic
    private static VRAPI VRHardware=new OpenVR();
    
    private CollisionResults collisionResults=new CollisionResults();
    
    private BulletAppState bulletAppState;
    
    private ArrayList<PhysicalObject> items;
    
    public static final ColorRGBA HIGHLIGHT_VISIBLE=new ColorRGBA(0,255,0,0.7f);
    public static final ColorRGBA HIGHLIGHT_INVISIBLE=new ColorRGBA(0,255,0,0);
    
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
    private Room room;
    private Floor floor;
    private AnalyticalBalance analyticalBalance;
    private DistilledWaterContainer distilledWaterContainer;
    private FumeHood fumeHood;
    private TrashBin trashBin;
    
    private Display mainMenu;
    private Display settingsMenu;
    private Display substanceList;
    private Display keyBoard;
    private Display materialList;
    private Display periodicTable;
    
    //Objects
    
    private ArrayList<Describable> describables=new ArrayList<Describable>();
    
    private Geometry testCube;

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
        
        //Make the game use VR instancing
        //app.preconfigureVRApp(PRECONFIG_PARAMETER.INSTANCE_VR_RENDERING,true);
        
        //starts the game
        app.start();
    }
    
    @Override
    public void simpleInitApp() {
        
        //var init
        items=new ArrayList<>();
        
        //AmbientLight al = new AmbientLight();
        //al.setColor(ColorRGBA.White.mult(1.3f));
        //rootNode.addLight(al);
        
        //Lights
        initLights();
        
        //Physics
        bulletAppState = new BulletAppState();
        getStateManager().attach(bulletAppState);
        
        //LOAD SPATIALS START
        
        //OBSERVER INIT (PLAYER,HMD,THE HEADSET) START
        observer = new Node("observer");
        observer.setLocalTranslation(new Vector3f(0.0f, 0.0f, 0.0f));
        VRApplication.setObserver(observer);
        //OBSERVER INIT END
        
        //init playerObject
        playerLogic=new Player(this,getAssetManager(),rootNode,VRHardware,collisionResults,describables,observer);
        
        //WORLD INIT START
        room=new Room(getAssetManager(),rootNode,bulletAppState);
        
        floor=new Floor(getAssetManager(),rootNode,bulletAppState);
        
        fumeHood=new FumeHood(this,getAssetManager(),rootNode);
        
        mainMenu=new Display(getAssetManager(),rootNode,0);
        substanceList=new Display(getAssetManager(),rootNode,1);
        keyBoard=new Display(getAssetManager(),rootNode,2);
        //WORLD INIT END
        
        //OBJECTS INIT START
        Box testBox=new Box(0.1f,0.1f,0.1f);
        testCube=new Geometry("Test cube",testBox);
        testCube.setShadowMode(ShadowMode.CastAndReceive);
        Material testCubeMat=new Material(getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        testCubeMat.setColor("Color",ColorRGBA.Blue);
        testCube.setMaterial(testCubeMat);
        rootNode.attachChild(testCube);
        //OBJECTS INIT END
        
        //LIGHT INIT START
        //PointLight light = new PointLight(new Vector3f(0f,0.5f,0f));
        //light.setColor(ColorRGBA.White);
        //rootNode.addLight(light);
        //LIGHT INIT END
        
        //LOAD SPACIALS END
        
        //INIT THE INPUTS START
        initInputs();
        //INIT THE INPUTS END
        
    }

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
        
        //test cube controls
        getInputManager().addMapping("cubeNegativeZ", new KeyTrigger(KeyInput.KEY_NUMPAD8));
        getInputManager().addMapping("cubePositiveZ", new KeyTrigger(KeyInput.KEY_NUMPAD2));
        getInputManager().addMapping("cubePositiveX", new KeyTrigger(KeyInput.KEY_NUMPAD6));
        getInputManager().addMapping("cubeNegativeX", new KeyTrigger(KeyInput.KEY_NUMPAD4));
        getInputManager().addMapping("cubePositiveY", new KeyTrigger(KeyInput.KEY_NUMPAD9));
        getInputManager().addMapping("cubeNegativeY", new KeyTrigger(KeyInput.KEY_NUMPAD7));
        
        //inputs' action listener
        ActionListener acl = new ActionListener() {
            
            public void onAction(String name, boolean keyPressed, float tpf) {
                
                if(name.equals("quit")){
                    
                    System.out.println("initInput: quit key pressed, quiting...");
                    
                    System.exit(0);
                    
                    //observer.move(VRApplication.getFinalObserverRotation().getRotationColumn(0).mult(1f));
                    
                }else if(name.equals("cubeNegativeZ")){
                    
                    testCube.move(0,0,-0.1f);
                    
                }else if(name.equals("cubePositiveZ")){
                    
                    testCube.move(0,0,0.1f);
                    
                }else if(name.equals("cubeNegativeX")){
                    
                    testCube.move(-0.1f,0,0);
                    
                }else if(name.equals("cubePositiveX")){
                    
                    testCube.move(0.1f,0,0);
                    
                }else if(name.equals("cubeNegativeY")){
                    
                    testCube.move(0,-0.1f,0);
                    
                }else if(name.equals("cubePositiveY")){
                    
                    testCube.move(0,0.1f,0);
                    
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
        
        
        getInputManager().addListener(acl, "cubePositiveZ");
        getInputManager().addListener(acl, "cubeNegativeZ");
        getInputManager().addListener(acl, "cubePositiveX");
        getInputManager().addListener(acl, "cubeNegativeX");
        getInputManager().addListener(acl, "cubePositiveY");
        getInputManager().addListener(acl, "cubeNegativeY");
        
        
    }
    
    public void initLights(){
        
        
        //DL's simulate the ambient light coming from all 6 directions
        DirectionalLight dl0 = new DirectionalLight();
        dl0.setDirection((new Vector3f(0.5f,0,0)).normalizeLocal());
        dl0.setColor(ColorRGBA.White.mult(0.3f));
        rootNode.addLight(dl0); 
        
        DirectionalLight dl1 = new DirectionalLight();
        dl1.setDirection((new Vector3f(-0.5f,0,0)).normalizeLocal());
        dl1.setColor(ColorRGBA.White.mult(0.3f));
        rootNode.addLight(dl1); 
        
        DirectionalLight dl2 = new DirectionalLight();
        dl2.setDirection((new Vector3f(0,0.5f,0)).normalizeLocal());
        dl2.setColor(ColorRGBA.White.mult(0.3f));
        rootNode.addLight(dl2); 
        
        DirectionalLight dl3 = new DirectionalLight();
        dl3.setDirection((new Vector3f(0,-0.5f,0)).normalizeLocal());
        dl3.setColor(ColorRGBA.White.mult(0.3f));
        rootNode.addLight(dl3); 
        
        DirectionalLight dl4 = new DirectionalLight();
        dl4.setDirection((new Vector3f(0,0,0.5f)).normalizeLocal());
        dl4.setColor(ColorRGBA.White.mult(0.3f));
        rootNode.addLight(dl4); 
        
        DirectionalLight dl5 = new DirectionalLight();
        dl5.setDirection((new Vector3f(0,0,-0.5f)).normalizeLocal());
        dl5.setColor(ColorRGBA.White.mult(0.3f));
        rootNode.addLight(dl5);
        
        /*
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-0.5f, -0.5f, -0.5f)).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun); 
        
        VRDirectionalLightShadowRenderer dlsr = new VRDirectionalLightShadowRenderer(getAssetManager(), 2048, 4);
        dlsr.setLight(sun);
        //dlsr.setEdgeFilteringMode(EdgeFilteringMode.PCF4);
        //getViewPort().addProcessor(dlsr);
        getLeftViewPort().addProcessor(dlsr);
        getRightViewPort().addProcessor(dlsr);
        */
        
        //adds the point lights
        for(int i=0;i<4;i++){
            
            for(int j=0;j<5;j++){
                
                PointLight light = new PointLight();
                light.setPosition(new Vector3f((i*2)+0.75f,2.75f,(j*2)+1.14f));
                light.setColor(ColorRGBA.White.mult(0.03f));
                rootNode.addLight(light); 
                
                /*Adds marker cubes to show the pointlight positions
                Box box=new Box(0.1f,0.1f,0.1f);
                Geometry boxGeom=new Geometry("Light marker",box);
                boxGeom.setLocalTranslation((i*2)+0.75f,2.5f,(j*2)+1.14f);
                boxGeom.setShadowMode(ShadowMode.CastAndReceive);
                Material testCubeMat=new Material(getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
                testCubeMat.setColor("Color",ColorRGBA.Blue);
                boxGeom.setMaterial(testCubeMat);
                rootNode.attachChild(boxGeom);
                */
                
                System.out.println("Light placed at "+light.getPosition());
                
            }
            
        }
        
        
    }
    
    public ArrayList<PhysicalObject> getItemsList(){
        
        return items;
        
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