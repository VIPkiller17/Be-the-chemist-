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
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import interfaces.Describable;
import java.util.ArrayList;
import jmevr.app.VRApplication;
import jmevr.input.OpenVR;
import jmevr.input.VRAPI;
import objects.player.Player;
import objects.world.Floor;
import objects.world.Room;
import com.jme3.shadow.DirectionalLightShadowRenderer;

//by Tommy
public class Main extends VRApplication {
    
    //Other/Logic
    private static VRAPI VRHardware=new OpenVR();
    
    private CollisionResults collisionResults=new CollisionResults();
    
    private BulletAppState bulletAppState;
    
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
        
        //starts the game
        app.start();
    }
    
    @Override
    public void simpleInitApp() {
        
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
        playerLogic=new Player(getAssetManager(),rootNode,VRHardware,collisionResults,describables,observer);
        
        //TEST WORLD INIT START
        room=new Room(getAssetManager(),rootNode,bulletAppState);
        
        floor=new Floor(getAssetManager(),rootNode,bulletAppState);
        //TEST WORLD INIT END
        
        //OBJECTS INIT START
        //collisionPrism0=new CustomTestObject(rootNode,getAssetManager(),describables,1f,0.5f,-0.6f,"Models/Testing/Collisions/ColPrism.j3o");
        //collisionPrism1=new CustomTestObject(rootNode,getAssetManager(),describables,1f,0.5f,0f,"Models/Testing/Collisions/ColPrismRightHand.j3o");
        //collisionPrism2=new CustomTestObject(rootNode,getAssetManager(),describables,1f,0.5f,0.6f,"Models/Testing/Collisions/ColPrismLeftHand.j3o");
        //collisionPlain0=new CustomTestObject(rootNode,getAssetManager(),describables,-1f,0.5f,-0.6f,"Models/Testing/Collisions/ColPlain.j3o");
        //collisionPlain1=new CustomTestObject(rootNode,getAssetManager(),describables,-1f,0.5f,0f,"Models/Testing/Collisions/ColPlainRightHand.j3o");
        //collisionPlain2=new CustomTestObject(rootNode,getAssetManager(),describables,-1f,0.5f,0.6f,"Models/Testing/Collisions/ColPlainLeftHand.j3o");
        Box testBox=new Box(0.1f,0.1f,0.1f);
        testCube=new Geometry("Test cube",testBox);
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
        dl0.setColor(ColorRGBA.White.mult(0.5f));
        rootNode.addLight(dl0); 
        
        DirectionalLight dl1 = new DirectionalLight();
        dl1.setDirection((new Vector3f(-0.5f,0,0)).normalizeLocal());
        dl1.setColor(ColorRGBA.White.mult(0.5f));
        rootNode.addLight(dl1); 
        
        DirectionalLight dl2 = new DirectionalLight();
        dl2.setDirection((new Vector3f(0,0.5f,0)).normalizeLocal());
        dl2.setColor(ColorRGBA.White.mult(0.5f));
        rootNode.addLight(dl2); 
        
        DirectionalLight dl3 = new DirectionalLight();
        dl3.setDirection((new Vector3f(0,-0.5f,0)).normalizeLocal());
        dl3.setColor(ColorRGBA.White.mult(0.5f));
        rootNode.addLight(dl3); 
        
        DirectionalLight dl4 = new DirectionalLight();
        dl4.setDirection((new Vector3f(0,0,0.5f)).normalizeLocal());
        dl4.setColor(ColorRGBA.White.mult(0.5f));
        rootNode.addLight(dl4); 
        
        DirectionalLight dl5 = new DirectionalLight();
        dl5.setDirection((new Vector3f(0,0,-0.5f)).normalizeLocal());
        dl5.setColor(ColorRGBA.White.mult(0.5f));
        rootNode.addLight(dl5);
        
        DirectionalLightShadowRenderer dlsr = new DirectionalLightShadowRenderer(getAssetManager(), 1024, 3);
        dlsr.setLight(dl3);
        getLeftViewPort().addProcessor(dlsr);
        getRightViewPort().addProcessor(dlsr);
        
        //this loop doesn't seem to work, need to make it work, temporarily replaced with the stuff after
        /*
        for(int i=0;i<4;i++){
            
            for(int j=0;j<5;j++){
                
                PointLight light = new PointLight();
                light.setPosition(new Vector3f((j*-2)-1.14f,2.75f,(i*2)+0.75f));
                light.setColor(ColorRGBA.White.mult(0.1f));
                rootNode.addLight(light); 
                
                Box boxMesh = new Box((j*-2)-1.14f,2.5f,(i*2)+0.75f);
                Geometry boxGeo = new Geometry("Colored Box", boxMesh);
                boxGeo.setLocalScale(0.01f);
                Material boxMat = new Material(getAssetManager(), "Common/MatDefs/Light/Lighting.j3md"); 
                boxMat.setBoolean("UseMaterialColors", true); 
                boxMat.setColor("Ambient", ColorRGBA.Green); 
                boxMat.setColor("Diffuse", ColorRGBA.Green); 
                boxGeo.setMaterial(boxMat); 
                rootNode.attachChild(boxGeo);
                
                System.out.println("Light placed at "+light.getPosition());
                
            }
            
        }
        */
        
        PointLight pl0 = new PointLight();
        pl0.setPosition(new Vector3f(0.75f,2.75f,1.14f));
        pl0.setColor(ColorRGBA.White.mult(0.05f));
        rootNode.addLight(pl0);
        
        Box boxMesh = new Box(pl0.getPosition(),0.01f,0.01f,0.01f);
        Geometry boxGeo = new Geometry("Colored Box", boxMesh);
        Material boxMat = new Material(getAssetManager(), "Common/MatDefs/Light/Lighting.j3md"); 
        boxMat.setBoolean("UseMaterialColors", true); 
        boxMat.setColor("Ambient", ColorRGBA.Green); 
        boxMat.setColor("Diffuse", ColorRGBA.Green); 
        boxGeo.setMaterial(boxMat); 
        rootNode.attachChild(boxGeo);
        
        PointLight pl1 = new PointLight();
        pl1.setPosition(new Vector3f(0.75f,2.75f,3.14f));
        pl1.setColor(ColorRGBA.White.mult(0.05f));
        rootNode.addLight(pl1);
        
        Box boxMesh0 = new Box(pl1.getPosition(),0.01f,0.01f,0.01f);
        Geometry boxGeo0 = new Geometry("Colored Box", boxMesh0);
        Material boxMat0 = new Material(getAssetManager(), "Common/MatDefs/Light/Lighting.j3md"); 
        boxMat0.setBoolean("UseMaterialColors", true); 
        boxMat0.setColor("Ambient", ColorRGBA.Green); 
        boxMat0.setColor("Diffuse", ColorRGBA.Green); 
        boxGeo0.setMaterial(boxMat0); 
        rootNode.attachChild(boxGeo0);
        
        PointLight pl2 = new PointLight();
        pl2.setPosition(new Vector3f(0.75f,2.75f,5.14f));
        pl2.setColor(ColorRGBA.White.mult(0.05f));
        rootNode.addLight(pl2);
        
        PointLight pl3 = new PointLight();
        pl3.setPosition(new Vector3f(0.75f,2.75f,7.14f));
        pl3.setColor(ColorRGBA.White.mult(0.05f));
        rootNode.addLight(pl3);
        
        PointLight pl4 = new PointLight();
        pl4.setPosition(new Vector3f(0.75f,2.75f,9.14f));
        pl4.setColor(ColorRGBA.White.mult(0.05f));
        rootNode.addLight(pl4);
        
        PointLight pl5 = new PointLight();
        pl5.setPosition(new Vector3f(2.75f,2.75f,1.14f));
        pl5.setColor(ColorRGBA.White.mult(0.05f));
        rootNode.addLight(pl5);
        
        PointLight pl6 = new PointLight();
        pl6.setPosition(new Vector3f(2.75f,2.75f,3.14f));
        pl6.setColor(ColorRGBA.White.mult(0.05f));
        rootNode.addLight(pl6);
        
        PointLight pl7 = new PointLight();
        pl7.setPosition(new Vector3f(2.75f,2.75f,5.14f));
        pl7.setColor(ColorRGBA.White.mult(0.05f));
        rootNode.addLight(pl7);
        
        PointLight pl8 = new PointLight();
        pl8.setPosition(new Vector3f(2.75f,2.75f,7.14f));
        pl8.setColor(ColorRGBA.White.mult(0.05f));
        rootNode.addLight(pl8);
        
        PointLight pl9 = new PointLight();
        pl9.setPosition(new Vector3f(2.75f,2.75f,9.14f));
        pl9.setColor(ColorRGBA.White.mult(0.05f));
        rootNode.addLight(pl9);
        
        PointLight pl10 = new PointLight();
        pl10.setPosition(new Vector3f(5.75f,2.75f,1.14f));
        pl10.setColor(ColorRGBA.White.mult(0.05f));
        rootNode.addLight(pl10);
        
        PointLight pl11 = new PointLight();
        pl11.setPosition(new Vector3f(5.75f,2.75f,3.14f));
        pl11.setColor(ColorRGBA.White.mult(0.05f));
        rootNode.addLight(pl11);
        
        PointLight pl12 = new PointLight();
        pl12.setPosition(new Vector3f(5.75f,2.75f,5.14f));
        pl12.setColor(ColorRGBA.White.mult(0.05f));
        rootNode.addLight(pl12);
        
        PointLight pl13 = new PointLight();
        pl13.setPosition(new Vector3f(5.75f,2.75f,7.14f));
        pl13.setColor(ColorRGBA.White.mult(0.05f));
        rootNode.addLight(pl13);
        
        PointLight pl14 = new PointLight();
        pl14.setPosition(new Vector3f(5.75f,2.75f,9.14f));
        pl14.setColor(ColorRGBA.White.mult(0.05f));
        rootNode.addLight(pl14);
        
        PointLight pl15 = new PointLight();
        pl15.setPosition(new Vector3f(7.75f,2.75f,1.14f));
        pl15.setColor(ColorRGBA.White.mult(0.05f));
        rootNode.addLight(pl15);
        
        PointLight pl16 = new PointLight();
        pl16.setPosition(new Vector3f(7.75f,2.75f,3.14f));
        pl16.setColor(ColorRGBA.White.mult(0.05f));
        rootNode.addLight(pl16);
        
        PointLight pl17 = new PointLight();
        pl17.setPosition(new Vector3f(7.75f,2.75f,5.14f));
        pl17.setColor(ColorRGBA.White.mult(0.05f));
        rootNode.addLight(pl17);
        
        PointLight pl18 = new PointLight();
        pl18.setPosition(new Vector3f(7.75f,2.75f,7.14f));
        pl18.setColor(ColorRGBA.White.mult(0.05f));
        rootNode.addLight(pl18);
        
        PointLight pl19 = new PointLight();
        pl19.setPosition(new Vector3f(7.75f,2.75f,9.14f));
        pl19.setColor(ColorRGBA.White.mult(0.05f));
        rootNode.addLight(pl19);
        
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