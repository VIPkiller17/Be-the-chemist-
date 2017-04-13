package main;

import com.jme3.bullet.BulletAppState;
import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import interfaces.Describable;
import java.util.ArrayList;
import java.util.Collections;
import jmevr.app.VRApplication;
import jmevr.input.OpenVR;
import jmevr.input.VRAPI;
import objects.PhysicalObject;
import objects.apparatus.analyticalBalance.AnalyticalBalance;
import objects.apparatus.chemichalWasteDisposalContainer.ChemicalWasteDisposalContainer;
import objects.apparatus.distilledWaterContainer.DistilledWaterContainer;
import objects.apparatus.fumeHood.FumeHood;
import objects.apparatus.trashBin.TrashBin;
import objects.containers.beaker.Beaker;
import objects.containers.erlenmeyer.Erlenmeyer;
import objects.containers.funnel.Funnel;
import objects.containers.gasSac.GasSac;
import objects.containers.measuringCylinder.MeasuringCylinder;
import objects.containers.pipette.Pipette;
import objects.element.Element;
import objects.ion.Ion;
import objects.player.Player;
import objects.substance.Substance;
import objects.world.Floor;
import objects.world.Room;
import objects.world.Sink;
import objects.world.display.Display;
import objects.world.display.PeriodicTableDisplay;

//by Tommy
public class Main extends VRApplication {
    
    //Other/Logic
    private static VRAPI VRHardware=new OpenVR();
    
    private CollisionResults collisionResults=new CollisionResults();
    
    private BulletAppState bulletAppState;
    
    private ArrayList<PhysicalObject> items;
    
    public static final ColorRGBA HIGHLIGHT_VISIBLE=new ColorRGBA(0,255,0,0.5f);
    public static final ColorRGBA HIGHLIGHT_INVISIBLE=new ColorRGBA(0,255,0,0);
    
    private ArrayList<Element> presentElementList;
    private ArrayList<Integer> presentCountList;
    private ArrayList<Ion> presentIonList;
    
    //Player
    Spatial observer;
    //protected Geometry player;
    
    private Player playerLogic;
    
    private boolean rightHandCreated,leftHandCreated;
    
    //TPF counters
    private float controllerCountDispTPF;
    private float CommonTPF;
    private float controllerConnectionTPF;
    private float testBeakerSpawnTPF;
    
    //World
    private Room room;
    private Floor floor;
    private AnalyticalBalance analyticalBalance;
    private DistilledWaterContainer distilledWaterContainer;
    private FumeHood fumeHood;
    private TrashBin trashBin;
    private ChemicalWasteDisposalContainer chemicalWasteDisposalContainer;
    private Sink sink0,sink1;
    
    private Display mainMenu;
    private Display settingsMenu;
    private Display substanceList;
    private Display filters;
    private Display keyBoard;
    private Display materialList;
    private PeriodicTableDisplay periodicTableDisplay;
    
    //Objects
    private ArrayList<Describable> describables=new ArrayList<Describable>();
    private ArrayList<Element> elements=new ArrayList<Element>();
    private ArrayList<Ion> ions=new ArrayList<Ion>();
    private ArrayList<Substance> substances=new ArrayList<Substance>();
    
    private Beaker beaker;
    private Erlenmeyer erlenmeyer;
    private Funnel funnel;
    private GasSac gasSac;
    private MeasuringCylinder measuringCylinder;
    private Pipette pipette;
    //private VolumetricFlask volumetricFlask;
    
    private ArrayList<Beaker> testBeakers;

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
        initChemistryStructure();
        
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
        room=new Room(this);
        floor=new Floor(this);
        fumeHood=new FumeHood(this,getAssetManager(),rootNode);
        analyticalBalance = new AnalyticalBalance(this, rootNode, collisionResults, getAssetManager(), new Vector3f(8.25f, .95f, 5.60f));
        chemicalWasteDisposalContainer=new ChemicalWasteDisposalContainer(this,getAssetManager(),rootNode);
        distilledWaterContainer=new DistilledWaterContainer(this,getAssetManager(),rootNode);
        sink0=new Sink(this,getAssetManager(),rootNode,0);
        sink1=new Sink(this,getAssetManager(),rootNode,1);
        
        mainMenu=new Display(this,0);
        substanceList=new Display(this,1);
        filters=new Display(this,4);
        keyBoard=new Display(this,2);
        materialList=new Display(this,3);
        periodicTableDisplay=new PeriodicTableDisplay(this);
        //WORLD INIT END
        
        //OBJECTS INIT START
        /*
        Box testBox=new Box(0.1f,0.1f,0.1f);
        testCube=new Geometry("Test cube",testBox);
        testCube.setShadowMode(ShadowMode.CastAndReceive);
        Material testCubeMat=new Material(getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        testCubeMat.setColor("Color",ColorRGBA.Blue);
        testCube.setMaterial(testCubeMat);
        rootNode.attachChild(testCube);
        */
        
        beaker=new Beaker(this,new Vector3f(0f,0.061f,0));
        erlenmeyer=new Erlenmeyer(this,new Vector3f(0.7f,0.5f,0));
        funnel=new Funnel(this,new Vector3f(0.2f,0.5f,0));
        gasSac=new GasSac(this,new Vector3f(0.4f,0.5f,0));
        measuringCylinder=new MeasuringCylinder(this,new Vector3f(0.9f,0.5f,0));
        pipette=new Pipette(this,new Vector3f(0.11f,0.5f,0));
        //volumetricFlask=new VolumetricFlask(this,new Vector3f(0.13f,0.5f,0));
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
        
        //TESTING SECTION
        testBeakers=new ArrayList<Beaker>();
        
        bulletAppState.setDebugEnabled(true);
        
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
                    
                    //testCube.move(0,0,-0.1f);
                    
                }else if(name.equals("cubePositiveZ")){
                    
                    //testCube.move(0,0,0.1f);
                    
                }else if(name.equals("cubeNegativeX")){
                    
                    //testCube.move(-0.1f,0,0);
                    
                }else if(name.equals("cubePositiveX")){
                    
                    //testCube.move(0.1f,0,0);
                    
                }else if(name.equals("cubeNegativeY")){
                    
                    //testCube.move(0,-0.1f,0);
                    
                }else if(name.equals("cubePositiveY")){
                    
                    //testCube.move(0,0.1f,0);
                    
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
    
    public void removeItem(PhysicalObject item){
        
        items.remove(item);
        
        items.removeAll(Collections.singleton(null)); 
        
    }
    
    public BulletAppState getBulletAppState(){
        
        return bulletAppState;
        
    }
    
    private void initChemistryStructure(){
        
        initElements();
        
        initIons();
        
        initSubstances();
        
    }
    
    private void initElements(){
        
        //elements.add(new Element(atomicNumber,"","",electronegativity,molarMass));
        
        elements.add(new Element(1,"Hydrogen","H",2.2,1.008));
        elements.add(new Element(2,"Helium","He",-1,4.003));
        elements.add(new Element(3,"Lithium","Li",0.98,6.94));
        elements.add(new Element(4,"Beryllium","Be",1.57,9.012));
        elements.add(new Element(5,"Boron","B",2.04,10.81));
        elements.add(new Element(6,"Carbon","C",2.55,12.011));
        elements.add(new Element(7,"Nitrogen","N",3.04,14.007));
        elements.add(new Element(8,"Oxygen","O",3.44,15.999));
        elements.add(new Element(9,"Fluorine","F",3.98,18.998));
        elements.add(new Element(10,"Neon","Ne",-1,20.179));
        elements.add(new Element(11,"Sodium","Na",0.93,22.989));
        elements.add(new Element(12,"Magnesium","Mg",1.31,24.305));
        elements.add(new Element(13,"Aluminium","Al",1.61,26.982));
        elements.add(new Element(14,"Solicon","Si",1.90,28.085));
        elements.add(new Element(15,"Phosphorus","P",2.19,30.974));
        elements.add(new Element(16,"Sulfur","S",2.58,32.06));
        elements.add(new Element(17,"Chlorine","Cl",3.16,35.45));
        elements.add(new Element(18,"Argon","Ar",-1,39.948));
        elements.add(new Element(19,"Potassium","K",0.82,39.098));
        elements.add(new Element(20,"Calcium","Ca",1,40.078));
        elements.add(new Element(21,"Scandium","Sc",1.36,44.956));
        elements.add(new Element(22,"Titanium","Ti",1.54,47.867));
        elements.add(new Element(23,"Vanadium","V",1.63,50.942));
        elements.add(new Element(24,"Chromium","Cr",1.66,51.996));
        elements.add(new Element(25,"Manganese","Mn",1.55,54.938));
        elements.add(new Element(26,"Iron","Fe",1.83,55.845));
        elements.add(new Element(27,"Cobalt","Co",1.88,58.933));
        elements.add(new Element(28,"Nickel","Ni",1.91,58.693));
        elements.add(new Element(29,"Copper","Cu",1.9,63.546));
        elements.add(new Element(30,"Zinc","Zn",1.65,65.38));
        elements.add(new Element(31,"Gallium","Ga",1.81,69.723));
        elements.add(new Element(32,"Germanium","Ge",2.01,72.63));
        elements.add(new Element(33,"Arsenic","As",2.18,74.922));
        elements.add(new Element(34,"Selenium","Se",2.55,78.96));
        elements.add(new Element(35,"Bromine","Br",2.96,79.904));
        elements.add(new Element(36,"Krypton","Kr",3,833.798));
        elements.add(new Element(37,"Rubidium","Rb",0.82,85.468));
        elements.add(new Element(38,"Strontium","Sr",0.95,87.62));
        elements.add(new Element(39,"Yttrium","Y",1.22,88.906));
        elements.add(new Element(40,"Zirconium","Zr",1.33,91.224));
        elements.add(new Element(41,"Niobium","Nb",1.6,92.906));
        elements.add(new Element(42,"Molybdenum","Mo",2.16,95.96));
        elements.add(new Element(43,"Technetium","Tc",1.9,98));
        elements.add(new Element(44,"Ruthenium","Ru",2.2,101.07));
        elements.add(new Element(45,"Rhodium","Rh",2.28,102.906));
        elements.add(new Element(46,"Palladium","Pd",2.2,106.42));
        elements.add(new Element(47,"Silver","Ag",1.93,107.868));
        elements.add(new Element(48,"Cadmium","Cd",1.69,112.411));
        elements.add(new Element(49,"Indium","In",1.78,114.818));
        elements.add(new Element(50,"Tin","Sn",1.96,118.71));
        elements.add(new Element(51,"Antimony","Sb",2.05,121.76));
        elements.add(new Element(52,"Tellurium","Te",2.1,127.6));
        elements.add(new Element(53,"Iodine","I",2.66,126.904));
        elements.add(new Element(54,"Xenon","Xe",2.6,131.293));
        elements.add(new Element(55,"Caesium","Cs",0.79,132.905));
        elements.add(new Element(56,"Barium","Ba",0.89,137.327));
        elements.add(new Element(57,"Lanthanum","La",1.1,138.905));
        elements.add(new Element(58,"Cerium","Ce",1.12,140.116));
        elements.add(new Element(59,"Praseodymium","Pr",1.13,140.908));
        elements.add(new Element(60,"Neodymium","Nd",1.14,144.242));
        elements.add(new Element(61,"Prometheum","Pm",-1,145));
        elements.add(new Element(62,"Samarium","Sm",1.17,150.36));
        elements.add(new Element(63,"Europium","Eu",-1,151.964));
        elements.add(new Element(64,"Gadolinium","Gd",1.2,157.25));
        elements.add(new Element(65,"Terbium","Tb",-1,158.925));
        elements.add(new Element(66,"Dysprosium","Dy",1.22,162.5));
        elements.add(new Element(67,"Holmium","Ho",1.23,164.930));
        elements.add(new Element(68,"Erbium","Er",1.24,167.259));
        elements.add(new Element(69,"Thulium","Tm",1.25,168.934));
        elements.add(new Element(70,"Ytterbium","Yb",-1,173.054));
        elements.add(new Element(71,"Lutetium","Lu",1.27,174.967));
        elements.add(new Element(72,"Hafnium","Hf",1.3,178.49));
        elements.add(new Element(73,"Tantalum","Ta",1.5,180.948));
        elements.add(new Element(74,"Tungsten","W",2.36,183.84));
        elements.add(new Element(75,"Rhenium","Re",1.9,186.207));
        elements.add(new Element(76,"Osmium","Os",2.2,190.23));
        elements.add(new Element(77,"Iridium","Ir",2.2,192.217));
        elements.add(new Element(78,"Platinum","Pt",2.28,195.084));
        elements.add(new Element(79,"Gold","Au",2.54,196.967));
        elements.add(new Element(80,"Mercury","Hg",2,200.59));
        elements.add(new Element(81,"Thallium","Ti",1.62,204.38));
        elements.add(new Element(82,"Lead","Pb",2.33,207.2));
        elements.add(new Element(83,"Bismuth","Bi",2.02,208.98));
        elements.add(new Element(84,"Polonium","Po",2,209));
        elements.add(new Element(85,"Astatine","At",2.2,210));
        elements.add(new Element(86,"Radon","Rn",-1,222));
        elements.add(new Element(87,"Francium","Fr",0.7,223));
        elements.add(new Element(88,"Radium","Ra",0.9,226));
        elements.add(new Element(89,"Actinium","Ac",1.1,227));
        elements.add(new Element(90,"Thorium","Th",1.3,232.038));
        elements.add(new Element(91,"Protactinium","Pa",1.5,231.036));
        elements.add(new Element(92,"Uranium","U",1.38,238.029));
        elements.add(new Element(93,"Neptunium","Np",1.36,237));
        elements.add(new Element(94,"Plutonium","Pu",1.28,244));
        elements.add(new Element(95,"Americium","Am",1.3,243));
        elements.add(new Element(96,"Curium","Cm",1.3,247));
        elements.add(new Element(97,"Berkelium","Bk",1.3,247));
        elements.add(new Element(98,"Californium","Cf",1.3,251));
        elements.add(new Element(99,"Einsteinium","Es",1.3,252));
        elements.add(new Element(100,"Fermium","Fm",1.3,257));
        elements.add(new Element(101,"Mendelevium","Md",1.3,258));
        elements.add(new Element(102,"Nobelium","No",1.3,259));
        elements.add(new Element(103,"Lawrencium","Lr",-1,262));
        elements.add(new Element(104,"Rutherfordium","Rf",-1,267));
        elements.add(new Element(105,"Dubnium","Db",-1,268));
        elements.add(new Element(106,"Seaborgium","Sg",-1,271));
        elements.add(new Element(107,"Bohrium","Bh",-1,272));
        elements.add(new Element(108,"Hassium","Hs",-1,270));
        elements.add(new Element(109,"Meitnerium","Mt",-1,276));
        elements.add(new Element(110,"Darmstadtium","Ds",-1,281));
        elements.add(new Element(111,"Roentgenium","Rg",-1,280));
        elements.add(new Element(112,"Copernicium","Cn",-1,285));
        elements.add(new Element(113,"Nihonium","Nh",-1,284));
        elements.add(new Element(114,"Flerovium","Fl",-1,289));
        elements.add(new Element(115,"Moscovium","Mc",-1,288));
        elements.add(new Element(116,"Livermorium","Lv",-1,293));
        elements.add(new Element(117,"Tennessine","Ts",-1,294));
        elements.add(new Element(118,"Oganesson","Og",-1,294));
        
    }
    
    private void initIons(){
        
        //halides from fluorine to iodine
        ions.add(new Ion(1,-1,elements.get(8),1));//0
        
        ions.add(new Ion(1,-1,elements.get(16),1));//1
        
        ions.add(new Ion(1,-1,elements.get(24),1));//2
        
        ions.add(new Ion(1,-1,elements.get(32),1));//3
        
        //H + 4
        ions.add(new Ion(1,1,elements.get(0),1));
        
        //NO3 - 5
        presentElementList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentElementList.add(elements.get(6));
        presentCountList.add(1);
        presentElementList.add(elements.get(7));
        presentCountList.add(3);
        ions.add(new Ion(1,-1,presentElementList,presentCountList));
        
        //SO4 2- 6
        presentElementList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentElementList.add(elements.get(15));
        presentCountList.add(1);
        presentElementList.add(elements.get(7));
        presentCountList.add(4);
        ions.add(new Ion(2,-1,presentElementList,presentCountList));
        
        //PO4 3- 7
        presentElementList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentElementList.add(elements.get(14));
        presentCountList.add(1);
        presentElementList.add(elements.get(7));
        presentCountList.add(4);
        ions.add(new Ion(3,-1,presentElementList,presentCountList));
        
        //C2H3O2 - 8
        presentElementList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentElementList.add(elements.get(5));
        presentCountList.add(2);
        presentElementList.add(elements.get(0));
        presentCountList.add(3);
        presentElementList.add(elements.get(7));
        presentCountList.add(2);
        ions.add(new Ion(1,-1,presentElementList,presentCountList));
        
        //CO3 2- 9
        presentElementList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentElementList.add(elements.get(5));
        presentCountList.add(1);
        presentElementList.add(elements.get(7));
        presentCountList.add(3);
        ions.add(new Ion(2,-1,presentElementList,presentCountList));
        
        //OH - 10
        presentElementList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentElementList.add(elements.get(7));
        presentCountList.add(1);
        presentElementList.add(elements.get(0));
        presentCountList.add(1);
        ions.add(new Ion(1,-1,presentElementList,presentCountList));
        
        //Li + 11
        ions.add(new Ion(1,1,elements.get(2),1));
        
        //Na +  12
        ions.add(new Ion(1,1,elements.get(10),1));
        
        //K + 13
        ions.add(new Ion(1,1,elements.get(18),1));
        
        //Mg 2+ 14
        ions.add(new Ion(2,1,elements.get(11),1));
        
        //Ca 2+ 15
        ions.add(new Ion(2,1,elements.get(19),1));
        
        //Al 3+ 16
        ions.add(new Ion(3,1,elements.get(12),1));
        
        //NH4 + 17
        presentElementList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentElementList.add(elements.get(6));
        presentCountList.add(1);
        presentElementList.add(elements.get(0));
        presentCountList.add(4);
        ions.add(new Ion(1,1,presentElementList,presentCountList));
        
        //Fe 2+ 18
        ions.add(new Ion(2,1,elements.get(25),1));
        
        //Fe 3+ 19
        ions.add(new Ion(3,1,elements.get(25),1));
        
        //Cu 1+ 20
        ions.add(new Ion(1,1,elements.get(28),1));
        
        //Cu 2+ 21
        ions.add(new Ion(2,1,elements.get(28),1));
        
        //H3O + 22
        presentElementList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentElementList.add(elements.get(0));
        presentCountList.add(3);
        presentElementList.add(elements.get(7));
        presentCountList.add(0);
        ions.add(new Ion(1,1,presentElementList,presentCountList));
        
    }
    
    private void initSubstances(){
        
        //substances.add(new Substance("equation","name",meltingPoint,boilingPoint,sublimationPoint,"TYPE",molarMass,density,new ColorRGBA(,,,),presentIonList,presentCountList,soluble));
        
        //F2 0
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(0));
        presentCountList.add(2);
        substances.add(new Substance("F\u2082","Fluorine gas",53.48,85.03,-1,"Halogen",37.996,1.696,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //Cl2 1
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(1));
        presentCountList.add(2);
        substances.add(new Substance("Cl\u2082","Chlorine gas",171.6,239.11,-1,"Halogen",70.9,3.2,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //Br2 2
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(2));
        presentCountList.add(2);
        substances.add(new Substance("Br\u2082","Bromine",265.8,332,-1,"Halogen",159.808,3.103,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //I2 3
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(3));
        presentCountList.add(2);
        substances.add(new Substance("I\u2082","Iodine",386.85,457.4,-1,"Halogen",253.808,4.933,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //HF 4
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(4));
        presentCountList.add(1);
        presentIonList.add(ions.get(0));
        presentCountList.add(1);
        substances.add(new Substance("HF","Hydrogen fluoride",273,373,-1,"Acidic",20.01,1.15,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //HCl 5
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(4));
        presentCountList.add(1);
        presentIonList.add(ions.get(1));
        presentCountList.add(1);
        substances.add(new Substance("HCl","Hydrogen chloride",273,373,-1,"Acidic",36.46,1.49,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //HBr 6
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(4));
        presentCountList.add(1);
        presentIonList.add(ions.get(2));
        presentCountList.add(1);
        substances.add(new Substance("HBr","Hydrogen bromide",273,373,-1,"Acidic",80.91,3.645,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //HI 7
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(4));
        presentCountList.add(1);
        presentIonList.add(ions.get(3));
        presentCountList.add(1);
        substances.add(new Substance("HI","Hydrogen iodide",273,373,-1,"Acidic",127.904,2.85,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //HNO3 8
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(4));
        presentCountList.add(1);
        presentIonList.add(ions.get(5));
        presentCountList.add(1);
        substances.add(new Substance("HNO\u2083","Hydrogen nitrate",273,373,-1,"Acidic",63.01,3.645,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //H2SO4 9
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(4));
        presentCountList.add(2);
        presentIonList.add(ions.get(6));
        presentCountList.add(1);
        substances.add(new Substance("H\u2082SO\u2084","Hydrogen sulfate",273,373,-1,"Acidic",98.079,1.84,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //H3PO4 10
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(4));
        presentCountList.add(3);
        presentIonList.add(ions.get(7));
        presentCountList.add(1);
        substances.add(new Substance("H\u2083PO\u2084","Hydrogen phosphate",273,373,-1,"Acidic",97.99,1.885,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //C2H4O2 11
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(4));
        presentCountList.add(1);
        presentIonList.add(ions.get(8));
        presentCountList.add(1);
        substances.add(new Substance("C\u2082H\u2084O\u2082","Acetic acid",273,373,-1,"Acidic",60.05,1.049,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //H2CO3 12
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(4));
        presentCountList.add(2);
        presentIonList.add(ions.get(6));
        presentCountList.add(1);
        substances.add(new Substance("H\u2082CO\u2083","Carbonic acid",273,373,-1,"Acidic",62.03,1.668,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //LiOH 13
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(11));
        presentCountList.add(1);
        presentIonList.add(ions.get(10));
        presentCountList.add(1);
        substances.add(new Substance("LiOH","Lithium hydroxyde",273,373,-1,"Basic",23.95,1.46,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //NaOH 14
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(12));
        presentCountList.add(1);
        presentIonList.add(ions.get(10));
        presentCountList.add(1);
        substances.add(new Substance("NaOH","Sodium hydroxyde",273,373,-1,"Basic",39.997,2.13,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //KOH 15
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(13));
        presentCountList.add(1);
        presentIonList.add(ions.get(10));
        presentCountList.add(1);
        substances.add(new Substance("KOH","Potassium hydroxyde",273,373,-1,"Basic",56.11,2.12,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //Mg(OH)2 16
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(14));
        presentCountList.add(1);
        presentIonList.add(ions.get(10));
        presentCountList.add(2);
        substances.add(new Substance("Mg(OH)\u2082","Magnesium hydroxyde",273,373,-1,"Basic",58.32,2.345,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //Ca(OH)2 17
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(15));
        presentCountList.add(1);
        presentIonList.add(ions.get(10));
        presentCountList.add(2);
        substances.add(new Substance("Ca(OH)\u2082","Calcium hydroxyde",273,373,-1,"Basic",74.093,2.211,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //Al(OH)3 18
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(16));
        presentCountList.add(1);
        presentIonList.add(ions.get(10));
        presentCountList.add(3);
        substances.add(new Substance("Al(OH)\u2083","Aluminium hydroxyde",273,373,-1,"Basic",78,2.42,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //NH3 19
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(17));
        presentCountList.add(1);
        presentIonList.add(ions.get(22));
        presentCountList.add(2);
        substances.add(new Substance("NH\u2083","Ammonia",273,373,-1,"Basic",17.031,0.88,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //LiF 20
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(11));
        presentCountList.add(1);
        presentIonList.add(ions.get(0));
        presentCountList.add(1);
        substances.add(new Substance("LiF","Lithium fluoride",1118,1949,-1,"Salt",25.934,2.635,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //LiCl 21
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(11));
        presentCountList.add(1);
        presentIonList.add(ions.get(1));
        presentCountList.add(1);
        substances.add(new Substance("LiCl","Lithium chloride",880,1655,-1,"Salt",42.39,2.068,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //LiBr 22
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(11));
        presentCountList.add(1);
        presentIonList.add(ions.get(2));
        presentCountList.add(1);
        substances.add(new Substance("LiBr","Lithium bromide",825,1538,-1,"Salt",86.845,3.464,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //LiI 23
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(11));
        presentCountList.add(1);
        presentIonList.add(ions.get(3));
        presentCountList.add(1);
        substances.add(new Substance("LiI","Lithium iodide",742,1444,-1,"Salt",133.85,4.76,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //NaF 24
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(12));
        presentCountList.add(1);
        presentIonList.add(ions.get(0));
        presentCountList.add(1);
        substances.add(new Substance("NaF","Sodium fluoride",1266,1977,-1,"Salt",41.988,2.558,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //NaCl 25
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(12));
        presentCountList.add(1);
        presentIonList.add(ions.get(1));
        presentCountList.add(1);
        substances.add(new Substance("NaCl","Sodium chloride",1074,1686,-1,"Salt",58.44,2.165,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //NaBr 26
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(12));
        presentCountList.add(1);
        presentIonList.add(ions.get(2));
        presentCountList.add(1);
        substances.add(new Substance("NaBr","Sodium bromide",1020,1660,-1,"Salt",102.89,3.21,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //NaI 27
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(12));
        presentCountList.add(1);
        presentIonList.add(ions.get(3));
        presentCountList.add(1);
        substances.add(new Substance("NaI","Sodium iodide",934,1577,-1,"Salt",149.89,3.67,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //KF 28
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(13));
        presentCountList.add(1);
        presentIonList.add(ions.get(0));
        presentCountList.add(1);
        substances.add(new Substance("KF","Potassium fluoride",1131,1775,-1,"Salt",58.097,2.48,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //KCl 29
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(13));
        presentCountList.add(1);
        presentIonList.add(ions.get(1));
        presentCountList.add(1);
        substances.add(new Substance("KCl","Potassium chloride",1040,1690,-1,"Salt",74.551,1.984,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //KBr 30
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(13));
        presentCountList.add(1);
        presentIonList.add(ions.get(2));
        presentCountList.add(1);
        substances.add(new Substance("KBr","Potassium bromide",1007,1708,-1,"Salt",119.002,2.74,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //KI 31
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(13));
        presentCountList.add(1);
        presentIonList.add(ions.get(3));
        presentCountList.add(1);
        substances.add(new Substance("KI","Potassium iodide",954.2,1603,-1,"Salt",166.003,3.12,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //MgF2 32
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(14));
        presentCountList.add(1);
        presentIonList.add(ions.get(0));
        presentCountList.add(2);
        substances.add(new Substance("MgF\u2082","Magnesium fluoride",1536,2530,-1,"Salt",62.302,3.148,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //MgCl2 33
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(14));
        presentCountList.add(1);
        presentIonList.add(ions.get(1));
        presentCountList.add(2);
        substances.add(new Substance("MgCl\u2082","Magnesium chloride",987,1685,-1,"Salt",95.211,2.32,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //MgBr2 34
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(14));
        presentCountList.add(1);
        presentIonList.add(ions.get(2));
        presentCountList.add(2);
        substances.add(new Substance("MgBr\u2082","Magnesium bromide",984,1520,-1,"Salt",184.113,3.74,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //MgI2 35
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(14));
        presentCountList.add(1);
        presentIonList.add(ions.get(3));
        presentCountList.add(2);
        substances.add(new Substance("MgI\u2082","Magnesium iodide",910,1287,-1,"Salt",278.114,4.43,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //CaF2 36
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(15));
        presentCountList.add(1);
        presentIonList.add(ions.get(0));
        presentCountList.add(2);
        substances.add(new Substance("CaF\u2082","Calcium fluoride",1691,2806,-1,"Salt",78.07,3.18,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //CaCl2 37
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(15));
        presentCountList.add(1);
        presentIonList.add(ions.get(1));
        presentCountList.add(2);
        substances.add(new Substance("CaCl\u2082","Calcium chloride",1045,2208,-1,"Salt",110.98,2.15,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //CaBr2 38
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(15));
        presentCountList.add(1);
        presentIonList.add(ions.get(2));
        presentCountList.add(2);
        substances.add(new Substance("CaBr\u2082","Calcium bromide",1000,2208,-1,"Salt",199.89,3.359,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //CaI2 39
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(15));
        presentCountList.add(1);
        presentIonList.add(ions.get(3));
        presentCountList.add(2);
        substances.add(new Substance("CaI\u2082","Calcium iodide",1058,1370,-1,"Salt",293.887,3.956,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //AlF3 40
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(16));
        presentCountList.add(1);
        presentIonList.add(ions.get(0));
        presentCountList.add(3);
        substances.add(new Substance("AlF\u2083","Aluminium fluoride",1564,2806,-1,"Salt",83.977,3.1,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //AlCl3 41
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(16));
        presentCountList.add(1);
        presentIonList.add(ions.get(1));
        presentCountList.add(3);
        substances.add(new Substance("AlCl\u2083","Aluminium chloride",-1,-1,453,"Salt",133.341,2.48,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //AlBr3 42
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(16));
        presentCountList.add(1);
        presentIonList.add(ions.get(2));
        presentCountList.add(3);
        substances.add(new Substance("AlBr\u2083","Aluminium bromide",370.9,538,-1,"Salt",266.69,3.205,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //AlI3 43
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(16));
        presentCountList.add(1);
        presentIonList.add(ions.get(3));
        presentCountList.add(3);
        substances.add(new Substance("AlI\u2082","Aluminium iodide",462.5,633,-1,"Salt",407.695,3.98,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //H2O 44
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        substances.add(new Substance("H\u2092O","Water",273,373,-1,"None",18.015,1,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //CO2 45
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        substances.add(new Substance("CO\u2092","Carbon dioxide",-1,-1,194.7,"None",44.01,1.977,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //Li 46
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(11));
        presentCountList.add(1);
        substances.add(new Substance("Li","Lithium",453.65,1603,-1,"Metal",6.94,0.534,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //Na 47
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(12));
        presentCountList.add(1);
        substances.add(new Substance("Na","Sodium",370.944,1156.090,-1,"Metal",22.989,0.968,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //K 48
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(13));
        presentCountList.add(1);
        substances.add(new Substance("K","Potassium",336.7,1032,-1,"Metal",39.098,0.862,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //Mg 49
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(14));
        presentCountList.add(1);
        substances.add(new Substance("Mg","Magnesium",923,1363,-1,"Metal",24.305,1.738,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //Al 50
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(16));
        presentCountList.add(1);
        substances.add(new Substance("Al","Aluminium",933.47,2743,-1,"Metal",26.981,2.70,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //Ca 51
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(15));
        presentCountList.add(1);
        substances.add(new Substance("Ca","Calcium",1115,1757,-1,"Metal",40.078,1.55,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //Fe 52
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(18));
        presentCountList.add(1);
        presentIonList.add(ions.get(19));
        presentCountList.add(1);
        substances.add(new Substance("Fe","Iron",1811,3134,-1,"Metal",55.845,7.874,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //Cu 53
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(20));
        presentCountList.add(1);
        presentIonList.add(ions.get(21));
        presentCountList.add(1);
        substances.add(new Substance("Cu","Copper",1357.77,2835,-1,"Metal",63.546,8.96,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //H2 54
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        presentIonList.add(ions.get(4));
        presentCountList.add(1);
        substances.add(new Substance("H\u2082","Hydrogen gas",13.99,20.271,-1,"None",4.002,0.1786,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //O2 55
        presentIonList=new ArrayList<>();
        presentCountList=new ArrayList<>();
        substances.add(new Substance("O\u2082","Oxygen gas",54.361,90.188,-1,"None",31.998,1.429,new ColorRGBA(0,0,0,0),presentIonList,presentCountList,true));
        
        //index 0
        /*
        substances.add(new Substance("He","Helium",1,"Gas",0,298,0.95,4.222,-1,"None",4.002,0.1786));
        substances.add(new Substance("Be","Beryllium",1,"Solid",2,298,1560,2742,-1,"Metal",9.012,1.85));
        substances.add(new Substance("B","Boron",1,"Solid",2,298,2349,4200,-1,"Metal",10.81,2.08));
        substances.add(new Substance("C","Carbon",1,"Solid",2,298,-1,-1,-1,"None",12.011,2));
        substances.add(new Substance("N\u2082","Nitrogen gas",1,"Gas",0,298,63.15,77.355,-1,"None",28.014,1.251));
        substances.add(new Substance("Ne","Neon",1,"Gas",0,298,24.56,27.104,-1,"None",20.18,0.9));
        substances.add(new Substance("Si","Silicon",1,"Solid",2,298,1687,3538,-1,"Metal",28.085,2.329));
        substances.add(new Substance("P\u2084","Red phosphorus",1,"Solid",2,298,-1,-1,-1,"None",123.892,2.3));
        substances.add(new Substance("S\u2088","Sulfur",1,"Solid",2,298,388.36,717.8,-1,"None",256.48,2.07));
        substances.add(new Substance("Ar","Argon",1,"Gas",0,298,83.81,87.302,-1,"None",39.948,1.784));
        substances.add(new Substance("Sc","Scandium",1,"Solid",2,298,1814,3109,-1,"Metal",44.955,2.985));
        substances.add(new Substance("Ti","Titanium",1,"Solid",2,298,1941,3560,-1,"Metal",47.867,4.506));
        substances.add(new Substance("V","Vanadium",1,"Solid",2,298,2183,3680,-1,"Metal",50.942,6));
        substances.add(new Substance("Cr","Chromium",1,"Solid",2,298,2180,2944,-1,"Metal",51.996,7.19));
        substances.add(new Substance("Mn","Manganese",1,"Solid",2,298,1519,2334,-1,"Metal",54.938,7.21));
        substances.add(new Substance("Co","Cobalt",1,"Solid",2,298,1768,3200,-1,"Metal",58.933,8.9));
        substances.add(new Substance("Ni","Nickel",1,"Solid",2,298,1728,3003,-1,"Metal",58.693,8.908));
        substances.add(new Substance("Zn","Zinc",1,"Solid",2,298,692.68,1180,-1,"Metal",65.38,7.14));
        substances.add(new Substance("Ga","Gallium",1,"Solid",2,298,302.915,2673,-1,"Metal",69.723,5.91));
        substances.add(new Substance("Ge","Germanium",1,"Solid",2,298,1211,3106,-1,"Metal",72.63,5.323));
        substances.add(new Substance("As","Arsenic",1,"Solid",2,298,-1,-1,887,"None",74.922,5.727));
        substances.add(new Substance("Se","Selenium",1,"Solid",2,298,494,958,-1,"None",78.96,4.81));
        substances.add(new Substance("Kr","Krypton",1,"Gas",0,298,115.78,119.93,-1,"None",83.798,3.749));
        substances.add(new Substance("Rb","Rubidium",1,"Solid",2,298,312.45,961,-1,"Metal",85.468,1.532));
        substances.add(new Substance("Sr","Strontium",1,"Solid",2,298,1050,1650,-1,"Metal",87.62,2.64));
        substances.add(new Substance("Y","Yttrium",1,"Solid",2,298,1799,3203,-1,"Metal",88.906,4.472));
        substances.add(new Substance("Zr","Zirconium",1,"Solid",2,298,2128,4650,-1,"Metal",91.224,6.52));
        substances.add(new Substance("Nb","Niobium",1,"Solid",2,298,2750,5017,-1,"Metal",92.906,8.57));
        substances.add(new Substance("Mo","Molybdenum",1,"Solid",2,298,2896,4912,-1,"Metal",95.96,10.28));
        substances.add(new Substance("Tc","Technetium",1,"Solid",2,298,2430,4538,-1,"Metal",98,11));
        substances.add(new Substance("Ru","Ruthenium",1,"Solid",2,298,2607,4423,-1,"Metal",101.07,10.65));
        substances.add(new Substance("Rh","Rhodium",1,"Solid",2,298,2237,3968,-1,"Metal",102.905,12.41));
        substances.add(new Substance("Pd","Palladium",1,"Solid",2,298,1828.05,2830.82,-1,"Metal",106.42,12.023));
        substances.add(new Substance("Ag","Silver",1,"Solid",2,298,1234.93,2435,-1,"Metal",107.868,10.49));
        substances.add(new Substance("Cd","Cadmium",1,"Solid",2,298,594.22,1040,-1,"Metal",112.411,8.65));
        substances.add(new Substance("In","Indium",1,"Solid",2,298,429.749,2345,-1,"Metal",114.818,7.31));
        substances.add(new Substance("Sn","Tin",1,"Solid",2,298,505.08,2875,-1,"Metal",118.71,7.265));
        substances.add(new Substance("Sb","Gray antimony",1,"Solid",2,298,903.78,1908,-1,"None",121.76,6.697));
        substances.add(new Substance("Te","Tellurium",1,"Solid",2,298,722.66,1261,-1,"Metal",127.6,6.24));
        substances.add(new Substance("Xe","Xenon",1,"Gas",0,298,161.40,165.051,-1,"None",131.293,5.894));
        substances.add(new Substance("Cs","Caesium",1,"Solid",2,298,301.7,944,-1,"Metal",132.905,1.93));
        substances.add(new Substance("Ba","Barium",1,"Solid",2,298,1000,2118,-1,"Metal",137.327,3.51));
        substances.add(new Substance("Hf","Hafnium",1,"Solid",2,298,2506,4876,-1,"Metal",178.49,13.31));
        substances.add(new Substance("Ta","Tantalum",1,"Solid",2,298,3290,5731,-1,"Metal",180.947,16.69));
        substances.add(new Substance("W","Tungsten",1,"Solid",2,298,3695,6203,-1,"Metal",183.84,19.25));
        substances.add(new Substance("Re","Rhenium",1,"Solid",2,298,3459,5903,-1,"Metal",186.207,21.02));
        substances.add(new Substance("Os","Osmium",1,"Solid",2,298,3306,5285,-1,"Metal",190.23,22.59));
        substances.add(new Substance("Ir","Iridium",1,"Solid",2,298,2719,4403,-1,"Metal",192.217,22.56));
        substances.add(new Substance("Pt","Platinum",1,"Solid",2,298,2041.4,4098,-1,"Metal",195.084,21.45));
        substances.add(new Substance("Au","Gold",1,"Solid",2,298,1337.33,3243,-1,"Metal",196.966,19.3));
        substances.add(new Substance("Hg","Mercury",1,"Liquid",1,298,234.321,629.88,-1,"Metal",200.59,13.534));
        substances.add(new Substance("Tl","Thallium",1,"Solid",2,298,577,1746,-1,"Metal",204.38,11.85));
        substances.add(new Substance("Pb","Lead",1,"Solid",2,298,600.61,2022,-1,"Metal",207.2,11.34));
        substances.add(new Substance("Bi","Bismuth",1,"Solid",2,298,544.7,1837,-1,"Metal",208.98,9.78));
        //bismuth index 67
        
        substances.add(new Substance("La","Lanthanum",1,"Solid",2,298,1193,3737,-1,"Metal",138.905,6.162));
        substances.add(new Substance("Ce","Cerium",1,"Solid",2,298,1068,3716,-1,"Metal",140.116,6.770));
        substances.add(new Substance("Pr","Praseodymium",1,"Solid",2,298,1208,3403,-1,"Metal",140.907,6.77));
        substances.add(new Substance("Nd","Neodymium",1,"Solid",2,298,1297,3347,-1,"Metal",144.242,7.01));
        substances.add(new Substance("Sm","Samarium",1,"Solid",2,298,1345,2173,-1,"Metal",150.36,7.52));
        substances.add(new Substance("Eu","Europium",1,"Solid",2,298,1099,1802,-1,"Metal",151.964,5.264));
        substances.add(new Substance("Gd","Gadolinium",1,"Solid",2,298,1585,3273,-1,"Metal",157.25,7.9));
        substances.add(new Substance("Tb","Terbium",1,"Solid",2,298,1629,3396,-1,"Metal",158.925,8.23));
        substances.add(new Substance("Dy","Dysprosium",1,"Solid",2,298,1680,2840,-1,"Metal",162.5,8.540));
        substances.add(new Substance("Ho","Holmium",1,"Gas",0,298,1734,2873,-1,"Metal",164.93,8.79));
        substances.add(new Substance("Er","Erbium",1,"Solid",2,298,1802,3141,-1,"Metal",167.259,9.066));
        substances.add(new Substance("Tm","Thulium",1,"Solid",2,298,1818,2223,-1,"Metal",168.934,9.32));
        substances.add(new Substance("Yb","Ytterbium",1,"Solid",2,298,1097,1469,-1,"Metal",173.054,6.9));
        substances.add(new Substance("Lu","Lutetium",1,"Solid",2,298,1925,3675,-1,"Metal",174.967,9.841));
        */
        
    }
    
    public ArrayList<Substance> getSubstances(){
        
        return substances;
        
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
    
    public ArrayList<Element> getElements(){
        
        return elements;
        
    }
}