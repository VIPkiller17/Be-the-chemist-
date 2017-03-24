/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.world.display;

import com.jme3.asset.AssetManager;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import interfaces.Pointable;
import java.io.IOException;
import java.util.ArrayList;
import objects.solution.Solution;
import objects.substance.Substance;

/**
 *
 * @author VIPkiller17
 */
public class PeriodicTableDisplay implements Pointable,Savable{
    
    private AssetManager assetManager;
    
    private ArrayList<ElementButton> buttons;
    private ArrayList<BitmapText> texts;
    
    private BitmapFont font;
    
    private Spatial frame;
    private Quad quad;
    private Geometry background;
    private Material backgroundMat;
    
    private Node node;
    private Node rootNode;
    
    public PeriodicTableDisplay(AssetManager assetManager,Node rootNode){
        
        this.assetManager=assetManager;
        this.rootNode=rootNode;
        
        font=assetManager.loadFont("Interface/Fonts/Xolonium/Xolonium.fnt");
        
        texts=new ArrayList<BitmapText>();
        buttons=new ArrayList<ElementButton>();
        
        node=new Node();
        
        frame=assetManager.loadModel("Models/Static/Frames/Periodic_Table_Frame.j3o");
        //spatial.scale(1f,1f,1f);
        //spatial.rotate(0.0f, 0.0f, 0.0f);
        //spatial.setLocalTranslation(position);
        frame.setName("Periodic table's frame");
        frame.setUserData("correctCollision", true);
        frame.setUserData("correspondingObject", this);
        node.attachChild(frame);
        
        createBackground(4.71f,2.65f);
        
        texts.add(new BitmapText(font));
        
        texts.get(texts.size()-1).setSize(0.08f);
        texts.get(texts.size()-1).setText("Periodic table");
        texts.get(texts.size()-1).setLocalTranslation(-(texts.get(texts.size()-1).getLineWidth()/2),0.9f+(texts.get(texts.size()-1).getLineHeight()/2),0.01f);
        texts.get(texts.size()-1).setQueueBucket(RenderQueue.Bucket.Translucent);
        
        addButtons();
        
        rootNode.attachChild(node);
        
        node.setLocalTranslation(new Vector3f(2f,0.10f,-0.81f));
        
    }
    
    private void addButtons(){
        
        //the difference in position of the buttons compared to eachother is 0.26m
        //buttons.add(new ElementButton(assetManager,this,"MASS",atomicNumber,"SYMBOL","NAME",new Vector3f((-quad.getWidth()/2)+0.01f,(-quad.getHeight()/2)-0.01f,0.05f),new Solution(new Substance("EQUATION","NAME",quantity,"STATESTRING",stateInteger,temperature,meltingPoint,boilingPoint,sublimationPoint,"TYPE",molarMass,desnity)),ColorRGBA.COLOR));
        
        //row 1
        buttons.add(new ElementButton(assetManager,this,"1.008",1,"H","Hydrogen",new Vector3f(0.02f,quad.getHeight()-0.02f,0.05f),new Solution(new Substance("H\u2082","Hydrogen gas",1,"Gas",0,298,13.99,20.271,-1,"None",2.016,0.07)),ColorRGBA.Green));
        buttons.add(new ElementButton(assetManager,this,"4.003",2,"He","Helium",new Vector3f(4.44f,quad.getHeight()-0.02f,0.05f),new Solution(new Substance("He","Helium",1,"Gas",0,298,0.95,4.222,-1,"None",4.002,0.1786)),ColorRGBA.Blue));
        //row 2
        buttons.add(new ElementButton(assetManager,this,"6.94",3,"Li","Lithium",new Vector3f(0.02f,quad.getHeight()-0.28f,0.05f),new Solution(new Substance("Li","Lithium",1,"Solid",2,298,453.65,1603,-1,"None",6.94,0.534)),ColorRGBA.Orange));
        buttons.add(new ElementButton(assetManager,this,"9.012",4,"Be","Beryllium",new Vector3f(0.28f,quad.getHeight()-0.28f,0.05f),new Solution(new Substance("Be","Beryllium",1,"Solid",2,298,1560,2742,-1,"None",9.012,1.85)),ColorRGBA.Yellow));
        buttons.add(new ElementButton(assetManager,this,"10.81",5,"B","Boron",new Vector3f(3.14f,quad.getHeight()-0.28f,0.05f),new Solution(new Substance("B","Boron",1,"Solid",2,298,2349,4200,-1,"None",10.81,2.08)),ColorRGBA.Red));
        buttons.add(new ElementButton(assetManager,this,"12.011",6,"C","Carbon",new Vector3f(3.4f,quad.getHeight()-0.28f,0.05f),new Solution(new Substance("C","Carbon",1,"Solid",2,298,-1,-1,-1,"None",12.011,2)),ColorRGBA.Green));
        buttons.add(new ElementButton(assetManager,this,"14.007",7,"N","Nitrogen",new Vector3f(3.66f,quad.getHeight()-0.28f,0.05f),new Solution(new Substance("N\u2082","Nitrogen gas",1,"Gas",0,298,63.15,77.355,-1,"None",28.014,1.251)),ColorRGBA.Green));
        buttons.add(new ElementButton(assetManager,this,"15.999",8,"O","Oxygen",new Vector3f(3.92f,quad.getHeight()-0.28f,0.05f),new Solution(new Substance("O\u2082","Oxygen gas",1,"Gas",0,298,54.361,90.188,-1,"None",31.998,1.429)),ColorRGBA.Green));
        buttons.add(new ElementButton(assetManager,this,"18.998",9,"F","Fluorine",new Vector3f(4.18f,quad.getHeight()-0.28f,0.05f),new Solution(new Substance("F\u2082","Fluorine gas",1,"Gas",0,298,53.48,85.03,-1,"None",37.996,1.696)),ColorRGBA.Cyan));
        buttons.add(new ElementButton(assetManager,this,"20.18",10,"Ne","Neon",new Vector3f(4.44f,quad.getHeight()-0.28f,0.05f),new Solution(new Substance("Ne","Neon",1,"Gas",0,298,24.56,27.104,-1,"None",20.18,0.9)),ColorRGBA.Blue));
        //row 3
        buttons.add(new ElementButton(assetManager,this,"22.989",11,"Na","Sodium",new Vector3f(0.02f,quad.getHeight()-0.54f,0.05f),new Solution(new Substance("Na","Sodium",1,"Solid",2,298,370.944,1156.090,-1,"None",22.989,0.968)),ColorRGBA.Orange));
        buttons.add(new ElementButton(assetManager,this,"24.305",12,"Mg","Magnesium",new Vector3f(0.28f,quad.getHeight()-0.54f,0.05f),new Solution(new Substance("Mg","Magnesium",1,"Solid",2,298,923,1363,-1,"None",24.305,1.738)),ColorRGBA.Yellow));
        buttons.add(new ElementButton(assetManager,this,"26.981",13,"Al","Aluminium",new Vector3f(3.14f,quad.getHeight()-0.54f,0.05f),new Solution(new Substance("Al","Aluminium",1,"Solid",2,298,933.47,2743,-1,"None",26.981,2.70)),ColorRGBA.Magenta));
        buttons.add(new ElementButton(assetManager,this,"28.085",14,"Si","Silicon",new Vector3f(3.4f,quad.getHeight()-0.54f,0.05f),new Solution(new Substance("Si","Silicon",1,"Solid",2,298,1687,3538,-1,"None",28.085,2.329)),ColorRGBA.Red));
        buttons.add(new ElementButton(assetManager,this,"30.973",15,"P","Phosphorus",new Vector3f(3.66f,quad.getHeight()-0.54f,0.05f),new Solution(new Substance("P\u2084","Red phosphorus",1,"Solid",2,298,-1,-1,-1,"None",123.892,2.3)),ColorRGBA.Green));
        buttons.add(new ElementButton(assetManager,this,"32.06",16,"S","Sulfur",new Vector3f(3.92f,quad.getHeight()-0.54f,0.05f),new Solution(new Substance("S\u2088","Sulfur",1,"Solid",2,298,388.36,717.8,-1,"None",256.48,2.07)),ColorRGBA.Green));
        buttons.add(new ElementButton(assetManager,this,"35.45",17,"Cl","Chlorine",new Vector3f(4.18f,quad.getHeight()-0.54f,0.05f),new Solution(new Substance("Cl\u2082","Chlorine gas",1,"Gas",0,298,171.6,239.11,-1,"None",70.9,3.2)),ColorRGBA.Cyan));
        buttons.add(new ElementButton(assetManager,this,"39.948",18,"Ar","Argon",new Vector3f(4.44f,quad.getHeight()-0.54f,0.05f),new Solution(new Substance("Ar","Argon",1,"Gas",0,298,83.81,87.302,-1,"None",39.948,1.784)),ColorRGBA.Blue));
        //row 4
        buttons.add(new ElementButton(assetManager,this,"39.098",19,"K","Potassium",new Vector3f(0.02f,quad.getHeight()-0.8f,0.05f),new Solution(new Substance("K","Potassium",1,"Solid",2,298,336.7,1032,-1,"None",39.098,0.862)),ColorRGBA.Orange));
        buttons.add(new ElementButton(assetManager,this,"40.078",20,"Ca","Calcium",new Vector3f(0.28f,quad.getHeight()-0.8f,0.05f),new Solution(new Substance("Ca","Calcium",1,"Solid",2,298,1115,1757,-1,"None",40.078,1.55)),ColorRGBA.Yellow));
        buttons.add(new ElementButton(assetManager,this,"44.955",21,"Sc","Scandium",new Vector3f(0.54f,quad.getHeight()-0.8f,0.05f),new Solution(new Substance("Sc","Scandium",1,"Solid",2,298,1814,3109,-1,"None",44.955,2.985)),ColorRGBA.Brown));
        buttons.add(new ElementButton(assetManager,this,"47.867",22,"Ti","Titanium",new Vector3f(0.80f,quad.getHeight()-0.8f,0.05f),new Solution(new Substance("Ti","Titanium",1,"Solid",2,298,1941,3560,-1,"None",47.867,4.506)),ColorRGBA.Brown));
        buttons.add(new ElementButton(assetManager,this,"50.942",23,"V","Vanadium",new Vector3f(1.06f,quad.getHeight()-0.8f,0.05f),new Solution(new Substance("V","Vanadium",1,"Solid",2,298,2183,3680,-1,"None",50.942,6)),ColorRGBA.Brown));
        buttons.add(new ElementButton(assetManager,this,"51.996",24,"Cr","Chromium",new Vector3f(1.32f,quad.getHeight()-0.8f,0.05f),new Solution(new Substance("Cr","Chromium",1,"Solid",2,298,2180,2944,-1,"None",51.996,7.19)),ColorRGBA.Brown));
        buttons.add(new ElementButton(assetManager,this,"54.938",25,"Mn","Manganese",new Vector3f(1.58f,quad.getHeight()-0.8f,0.05f),new Solution(new Substance("Mn","Manganese",1,"Solid",2,298,1519,2334,-1,"None",54.938,7.21)),ColorRGBA.Brown));
        buttons.add(new ElementButton(assetManager,this,"55.845",26,"Fe","Iron",new Vector3f(1.84f,quad.getHeight()-0.8f,0.05f),new Solution(new Substance("Fe","Iron",1,"Solid",2,298,1811,3134,-1,"None",55.845,7.874)),ColorRGBA.Brown));
        buttons.add(new ElementButton(assetManager,this,"58.933",27,"Co","Cobalt",new Vector3f(2.1f,quad.getHeight()-0.8f,0.05f),new Solution(new Substance("Co","Cobalt",1,"Solid",2,298,1768,3200,-1,"None",58.933,8.9)),ColorRGBA.Brown));
        buttons.add(new ElementButton(assetManager,this,"58.693",28,"Ni","Nickel",new Vector3f(2.36f,quad.getHeight()-0.8f,0.05f),new Solution(new Substance("Ni","Nickel",1,"Solid",2,298,1728,3003,-1,"None",58.693,8.908)),ColorRGBA.Brown));
        buttons.add(new ElementButton(assetManager,this,"63.546",29,"Cu","Copper",new Vector3f(2.62f,quad.getHeight()-0.8f,0.05f),new Solution(new Substance("Cu","Copper",1,"Solid",2,298,1357.77,2835,-1,"None",63.546,8.96)),ColorRGBA.Brown));
        buttons.add(new ElementButton(assetManager,this,"65.38",30,"Zn","Zinc",new Vector3f(2.88f,quad.getHeight()-0.8f,0.05f),new Solution(new Substance("Zn","Zinc",1,"Solid",2,298,692.68,1180,-1,"None",65.38,7.14)),ColorRGBA.Brown));
        buttons.add(new ElementButton(assetManager,this,"69.723",31,"Ga","Gallium",new Vector3f(3.14f,quad.getHeight()-0.8f,0.05f),new Solution(new Substance("Ga","Gallium",1,"Solid",2,298,302.915,2673,-1,"None",69.723,5.91)),ColorRGBA.Magenta));
        buttons.add(new ElementButton(assetManager,this,"72.63",32,"Ge","Germanium",new Vector3f(3.4f,quad.getHeight()-0.8f,0.05f),new Solution(new Substance("Ge","Germanium",1,"Solid",2,298,1211,3106,-1,"None",72.63,5.323)),ColorRGBA.Red));
        buttons.add(new ElementButton(assetManager,this,"74.922",33,"As","Arsenic",new Vector3f(3.66f,quad.getHeight()-0.8f,0.05f),new Solution(new Substance("As","Arsenic",1,"Solid",2,298,-1,-1,887,"None",74.922,5.727)),ColorRGBA.Red));
        buttons.add(new ElementButton(assetManager,this,"78.96",34,"Se","Selenium",new Vector3f(3.92f,quad.getHeight()-0.8f,0.05f),new Solution(new Substance("Se","Selenium",1,"Solid",2,298,494,958,-1,"None",78.96,4.81)),ColorRGBA.Green));
        buttons.add(new ElementButton(assetManager,this,"79.904",35,"Br","Bromine",new Vector3f(4.18f,quad.getHeight()-0.8f,0.05f),new Solution(new Substance("Br\u2082","Bromine",1,"Liquid",1,298,265.8,332,-1,"None",159.808,3.103)),ColorRGBA.Cyan));
        buttons.add(new ElementButton(assetManager,this,"83.798",36,"Kr","Krypton",new Vector3f(4.44f,quad.getHeight()-0.8f,0.05f),new Solution(new Substance("Kr","Krypton",1,"Gas",0,298,115.78,119.93,-1,"None",83.798,3.749)),ColorRGBA.Blue));
        //row 5
        buttons.add(new ElementButton(assetManager,this,"85.468",37,"Rb","Rubidium",new Vector3f(0.02f,quad.getHeight()-1.06f,0.05f),new Solution(new Substance("Rb","Rubidium",1,"Solid",2,298,312.45,961,-1,"None",85.468,1.532)),ColorRGBA.Orange));
        buttons.add(new ElementButton(assetManager,this,"87.62",38,"Sr","Strontium",new Vector3f(0.28f,quad.getHeight()-1.06f,0.05f),new Solution(new Substance("Sr","Strontium",1,"Solid",2,298,1050,1650,-1,"None",87.62,2.64)),ColorRGBA.Yellow));
        buttons.add(new ElementButton(assetManager,this,"88.906",39,"Y","Yttrium",new Vector3f(0.54f,quad.getHeight()-1.06f,0.05f),new Solution(new Substance("Y","Yttrium",1,"Solid",2,298,1799,3203,-1,"None",88.906,4.472)),ColorRGBA.Brown));
        buttons.add(new ElementButton(assetManager,this,"91.224",40,"Zr","Zirconium",new Vector3f(0.80f,quad.getHeight()-1.06f,0.05f),new Solution(new Substance("Zr","Zirconium",1,"Solid",2,298,2128,4650,-1,"None",91.224,6.52)),ColorRGBA.Brown));
        buttons.add(new ElementButton(assetManager,this,"92.906",41,"Nb","Niobium",new Vector3f(1.06f,quad.getHeight()-1.06f,0.05f),new Solution(new Substance("Nb","Niobium",1,"Solid",2,298,2750,5017,-1,"None",92.906,8.57)),ColorRGBA.Brown));
        buttons.add(new ElementButton(assetManager,this,"95.96",42,"Mo","Molybdenum",new Vector3f(1.32f,quad.getHeight()-1.06f,0.05f),new Solution(new Substance("Mo","Molybdenum",1,"Solid",2,298,2896,4912,-1,"None",95.96,10.28)),ColorRGBA.Brown));
        buttons.add(new ElementButton(assetManager,this,"98",43,"Tc","Technetium",new Vector3f(1.58f,quad.getHeight()-1.06f,0.05f),new Solution(new Substance("Tc","Technetium",1,"Solid",2,298,2430,4538,-1,"None",98,11)),ColorRGBA.Brown));
        buttons.add(new ElementButton(assetManager,this,"101.07",44,"Ru","Ruthenium",new Vector3f(1.84f,quad.getHeight()-1.06f,0.05f),new Solution(new Substance("Ru","Ruthenium",1,"Solid",2,298,2607,4423,-1,"None",101.07,10.65)),ColorRGBA.Brown));
        buttons.add(new ElementButton(assetManager,this,"102.905",45,"Rh","Rhodium",new Vector3f(2.1f,quad.getHeight()-1.06f,0.05f),new Solution(new Substance("Rh","Rhodium",1,"Solid",2,298,2237,3968,-1,"None",102.905,12.41)),ColorRGBA.Brown));
        buttons.add(new ElementButton(assetManager,this,"106.42",46,"Pd","Palladium",new Vector3f(2.36f,quad.getHeight()-1.06f,0.05f),new Solution(new Substance("Pd","Palladium",1,"Solid",2,298,1828.05,2830.82,-1,"None",106.42,12.023)),ColorRGBA.Brown));
        buttons.add(new ElementButton(assetManager,this,"107.868",47,"Ag","Silver",new Vector3f(2.62f,quad.getHeight()-1.06f,0.05f),new Solution(new Substance("Ag","Silver",1,"Solid",2,298,1234.93,2435,-1,"None",107.868,10.49)),ColorRGBA.Brown));
        buttons.add(new ElementButton(assetManager,this,"112.411",48,"Cd","Cadmium",new Vector3f(2.88f,quad.getHeight()-1.06f,0.05f),new Solution(new Substance("Cd","Cadmium",1,"Solid",2,298,594.22,1040,-1,"None",112.411,8.65)),ColorRGBA.Brown));
        buttons.add(new ElementButton(assetManager,this,"114.818",49,"In","Indium",new Vector3f(3.14f,quad.getHeight()-1.06f,0.05f),new Solution(new Substance("In","Indium",1,"Solid",2,298,429.749,2345,-1,"None",114.818,7.31)),ColorRGBA.Magenta));
        buttons.add(new ElementButton(assetManager,this,"118.71",50,"Sn","Tin",new Vector3f(3.4f,quad.getHeight()-1.06f,0.05f),new Solution(new Substance("Sn","Tin",1,"Solid",2,298,505.08,2875,-1,"None",118.71,7.265)),ColorRGBA.Magenta));
        buttons.add(new ElementButton(assetManager,this,"121.76",51,"Sb","Antimony",new Vector3f(3.66f,quad.getHeight()-1.06f,0.05f),new Solution(new Substance("Sb","Gray antimony",1,"Solid",2,298,903.78,1908,-1,"None",121.76,6.697)),ColorRGBA.Red));
        buttons.add(new ElementButton(assetManager,this,"127.6",52,"Te","Tellurium",new Vector3f(3.92f,quad.getHeight()-1.06f,0.05f),new Solution(new Substance("Te","Tellurium",1,"Solid",2,298,722.66,1261,-1,"None",127.6,6.24)),ColorRGBA.Red));
        buttons.add(new ElementButton(assetManager,this,"126.904",53,"I","Iodine",new Vector3f(4.18f,quad.getHeight()-1.06f,0.05f),new Solution(new Substance("I\u2082","Iodine",1,"Solid",2,298,386.85,457.4,-1,"None",253.808,4.933)),ColorRGBA.Cyan));
        buttons.add(new ElementButton(assetManager,this,"131.293",54,"Xe","Xenon",new Vector3f(4.44f,quad.getHeight()-1.06f,0.05f),new Solution(new Substance("Xe","Xenon",1,"Gas",0,298,161.40,165.051,-1,"None",131.293,5.894)),ColorRGBA.Blue));
        //row 6
        buttons.add(new ElementButton(assetManager,this,"132.905",55,"Cs","Caesium",new Vector3f(0.02f,quad.getHeight()-1.32f,0.05f),new Solution(new Substance("Cs","Caesium",1,"Solid",2,298,301.7,944,-1,"None",132.905,1.93)),ColorRGBA.Orange));
        buttons.add(new ElementButton(assetManager,this,"137.327",56,"Ba","Barium",new Vector3f(0.28f,quad.getHeight()-1.32f,0.05f),new Solution(new Substance("Ba","Barium",1,"Solid",2,298,1000,2118,-1,"None",137.327,3.51)),ColorRGBA.Yellow));
        buttons.add(new ElementButton(assetManager,this,"178.49",72,"Hf","Hafnium",new Vector3f(0.80f,quad.getHeight()-1.32f,0.05f),new Solution(new Substance("Hf","Hafnium",1,"Solid",2,298,2506,4876,-1,"None",178.49,13.31)),ColorRGBA.Brown));
        buttons.add(new ElementButton(assetManager,this,"180.947",73,"Ta","Tantalum",new Vector3f(1.06f,quad.getHeight()-1.32f,0.05f),new Solution(new Substance("Ta","Tantalum",1,"Solid",2,298,3290,5731,-1,"None",180.947,16.69)),ColorRGBA.Brown));
        buttons.add(new ElementButton(assetManager,this,"183.84",74,"W","Tungsten",new Vector3f(1.32f,quad.getHeight()-1.32f,0.05f),new Solution(new Substance("W","Tungsten",1,"Solid",2,298,3695,6203,-1,"None",183.84,19.25)),ColorRGBA.Brown));
        buttons.add(new ElementButton(assetManager,this,"186.207",75,"Re","Rhenium",new Vector3f(1.58f,quad.getHeight()-1.32f,0.05f),new Solution(new Substance("Re","Rhenium",1,"Solid",2,298,3459,5903,-1,"None",186.207,21.02)),ColorRGBA.Brown));
        buttons.add(new ElementButton(assetManager,this,"190.23",76,"Os","Osmium",new Vector3f(1.84f,quad.getHeight()-1.32f,0.05f),new Solution(new Substance("Os","Osmium",1,"Solid",2,298,3306,5285,-1,"None",190.23,22.59)),ColorRGBA.Brown));
        buttons.add(new ElementButton(assetManager,this,"192.217",77,"Ir","Iridium",new Vector3f(2.1f,quad.getHeight()-1.32f,0.05f),new Solution(new Substance("Ir","Iridium",1,"Solid",2,298,2719,4403,-1,"None",192.217,22.56)),ColorRGBA.Brown));
        buttons.add(new ElementButton(assetManager,this,"195.084",78,"Pt","Platinum",new Vector3f(2.36f,quad.getHeight()-1.32f,0.05f),new Solution(new Substance("Pt","Platinum",1,"Solid",2,298,2041.4,4098,-1,"None",195.084,21.45)),ColorRGBA.Brown));
        buttons.add(new ElementButton(assetManager,this,"196.966",79,"Au","Gold",new Vector3f(2.62f,quad.getHeight()-1.32f,0.05f),new Solution(new Substance("Au","Gold",1,"Solid",2,298,1337.33,3243,-1,"None",196.966,19.3)),ColorRGBA.Brown));
        buttons.add(new ElementButton(assetManager,this,"200.59",80,"Hg","Mercury",new Vector3f(2.88f,quad.getHeight()-1.32f,0.05f),new Solution(new Substance("Hg","Mercury",1,"Liquid",1,298,234.321,629.88,-1,"None",200.59,13.534)),ColorRGBA.Brown));
        buttons.add(new ElementButton(assetManager,this,"204.38",81,"Tl","Thallium",new Vector3f(3.14f,quad.getHeight()-1.32f,0.05f),new Solution(new Substance("Tl","Thallium",1,"Solid",2,298,577,1746,-1,"None",204.38,11.85)),ColorRGBA.Magenta));
        buttons.add(new ElementButton(assetManager,this,"207.2",82,"Pb","Lead",new Vector3f(3.4f,quad.getHeight()-1.32f,0.05f),new Solution(new Substance("Pb","Lead",1,"Solid",2,298,600.61,2022,-1,"None",207.2,11.34)),ColorRGBA.Magenta));
        buttons.add(new ElementButton(assetManager,this,"208.98",83,"Bi","Bismuth",new Vector3f(3.66f,quad.getHeight()-1.32f,0.05f),new Solution(new Substance("Bi","Bismuth",1,"Solid",2,298,544.7,1837,-1,"None",208.98,9.78)),ColorRGBA.Magenta));
        buttons.add(new ElementButton(assetManager,this,"(209)",84,"Po","Polonium",new Vector3f(3.92f,quad.getHeight()-1.32f,0.05f),new Solution(new Substance("Po","Polonium",1,"Solid",2,298,527,1235,-1,"None",209,9.196)),ColorRGBA.Red));
        buttons.add(new ElementButton(assetManager,this,"(210)",85,"At","Astatine",new Vector3f(4.18f,quad.getHeight()-1.32f,0.05f),new Solution(new Substance("At","Astatine",1,"Solid",2,298,575,610,-1,"None",210,6.4)),ColorRGBA.Cyan));
        buttons.add(new ElementButton(assetManager,this,"(222)",86,"Rn","Radon",new Vector3f(4.44f,quad.getHeight()-1.32f,0.05f),new Solution(new Substance("Rn","Radon",1,"Gas",0,298,202,211.5,-1,"None",222,9.73)),ColorRGBA.Blue));
        //row 7
        buttons.add(new ElementButton(assetManager,this,"(223)",87,"Fr","Francium",new Vector3f(0.02f,quad.getHeight()-1.58f,0.05f),new Solution(new Substance("Fr","Francium",1,"Solid",2,298,300,950,-1,"None",223,2.9)),ColorRGBA.Orange));
        buttons.add(new ElementButton(assetManager,this,"(226)",88,"Ra","Radium",new Vector3f(0.28f,quad.getHeight()-1.58f,0.05f),new Solution(new Substance("Ra","Radium",1,"Solid",2,298,973,2010,-1,"None",226,5.5)),ColorRGBA.Yellow));
        buttons.add(new ElementButton(assetManager,this,"(267)",104,"Rf","Rutherfordium",new Vector3f(0.80f,quad.getHeight()-1.58f,0.05f),new Solution(new Substance("Rf","Rutherfordium",1,"Solid",2,298,2400,5800,-1,"None",267,23.2)),ColorRGBA.Brown));
        buttons.add(new ElementButton(assetManager,this,"(268)",105,"Db","Dubnium",new Vector3f(1.06f,quad.getHeight()-1.58f,0.05f),new Solution(new Substance("Db","Dubnium",1,"Solid",2,298,3000,6800,-1,"None",268,29.3)),ColorRGBA.Brown));
        buttons.add(new ElementButton(assetManager,this,"(271)",106,"Sg","Seaborgium",new Vector3f(1.32f,quad.getHeight()-1.58f,0.05f),new Solution(new Substance("Sg","Seaborgium",1,"Solid",2,298,3400,7300,-1,"None",271,35)),ColorRGBA.Brown));
        buttons.add(new ElementButton(assetManager,this,"(272)",107,"Bh","Bohrium",new Vector3f(1.58f,quad.getHeight()-1.58f,0.05f),new Solution(new Substance("Bh","Bohrium",1,"Solid",2,298,3200,7000,-1,"None",272,37.1)),ColorRGBA.Brown));
        buttons.add(new ElementButton(assetManager,this,"(270)",108,"Hs","Hassium",new Vector3f(1.84f,quad.getHeight()-1.58f,0.05f),new Solution(new Substance("Hs","Hassium",1,"Solid",2,298,3100,6400,-1,"None",270,41)),ColorRGBA.Brown));
        buttons.add(new ElementButton(assetManager,this,"(276)",109,"Mt","Meitnerium",new Vector3f(2.1f,quad.getHeight()-1.58f,0.05f),new Solution(new Substance("Mt","Meitnerium",1,"Solid",2,298,5000,5600,-1,"None",276,37.4)),ColorRGBA.Brown));
        buttons.add(new ElementButton(assetManager,this,"(281)",110,"Ds","Darmstadtium",new Vector3f(2.36f,quad.getHeight()-1.58f,0.05f),new Solution(new Substance("Ds","Darmstadtium",1,"Solid",2,298,5100,5200,-1,"None",281,34.8)),ColorRGBA.Brown));
        buttons.add(new ElementButton(assetManager,this,"(280)",111,"Rg","Roentgenium",new Vector3f(2.62f,quad.getHeight()-1.58f,0.05f),new Solution(new Substance("Rg","Roentgenium",1,"Solid",2,298,4400,4600,-1,"None",280,28.7)),ColorRGBA.Brown));
        buttons.add(new ElementButton(assetManager,this,"(285)",112,"Cn","Copernicium",new Vector3f(2.88f,quad.getHeight()-1.58f,0.05f),new Solution(new Substance("Cn","Copernicium",1,"Gas",0,298,100,250,-1,"None",285,23.7)),ColorRGBA.Brown));
        buttons.add(new ElementButton(assetManager,this,"(284)",113,"Nh","Nihonium",new Vector3f(3.14f,quad.getHeight()-1.58f,0.05f),new Solution(new Substance("Nh","Nihonium",1,"Solid",2,298,700,1430,-1,"None",284,16)),ColorRGBA.Magenta));
        buttons.add(new ElementButton(assetManager,this,"(289)",114,"Fl","Flerovium",new Vector3f(3.4f,quad.getHeight()-1.58f,0.05f),new Solution(new Substance("Fl","Flerovium",1,"Solid",2,298,340,420,-1,"None",289,14)),ColorRGBA.Magenta));
        buttons.add(new ElementButton(assetManager,this,"(288)",115,"Mc","Moscovium",new Vector3f(3.66f,quad.getHeight()-1.58f,0.05f),new Solution(new Substance("Mc","Moscovium",1,"Solid",2,298,670,1400,-1,"None",288,13.5)),ColorRGBA.Magenta));
        buttons.add(new ElementButton(assetManager,this,"(293)",116,"Lv","Livermorium",new Vector3f(3.92f,quad.getHeight()-1.58f,0.05f),new Solution(new Substance("Lv","Livermorium",1,"Solid",2,298,700,1085,-1,"None",293,12.9)),ColorRGBA.Magenta));
        buttons.add(new ElementButton(assetManager,this,"(294)",117,"Ts","Tennessine",new Vector3f(4.18f,quad.getHeight()-1.58f,0.05f),new Solution(new Substance("Ts","Tennessine",1,"Solid",2,298,723,883,-1,"None",294,7.2)),ColorRGBA.Cyan));
        buttons.add(new ElementButton(assetManager,this,"(294)",118,"Og","Oganesson",new Vector3f(4.44f,quad.getHeight()-1.58f,0.05f),new Solution(new Substance("Og","Oganesson",1,"Solid",2,298,300,380,-1,"None",294,5)),ColorRGBA.Blue));
        //row 8
        buttons.add(new ElementButton(assetManager,this,"138.905",57,"La","Lanthanum",new Vector3f(0.80f,quad.getHeight()-2.12f,0.05f),new Solution(new Substance("La","Lanthanum",1,"Solid",2,298,1193,3737,-1,"None",138.905,6.162)),new ColorRGBA(127,63,191,1)));
        buttons.add(new ElementButton(assetManager,this,"140.116",58,"Ce","Cerium",new Vector3f(1.06f,quad.getHeight()-2.12f,0.05f),new Solution(new Substance("Ce","Cerium",1,"Solid",2,298,1068,3716,-1,"None",140.116,6.770)),new ColorRGBA(127,63,191,1)));
        buttons.add(new ElementButton(assetManager,this,"140.907",59,"Pr","Praseodymium",new Vector3f(1.32f,quad.getHeight()-2.12f,0.05f),new Solution(new Substance("Pr","Praseodymium",1,"Solid",2,298,1208,3403,-1,"None",140.907,6.77)),new ColorRGBA(127,63,191,1)));
        buttons.add(new ElementButton(assetManager,this,"144.242",60,"Nd","Neodymium",new Vector3f(1.58f,quad.getHeight()-2.12f,0.05f),new Solution(new Substance("Nd","Neodymium",1,"Solid",2,298,1297,3347,-1,"None",144.242,7.01)),new ColorRGBA(127,63,191,1)));
        buttons.add(new ElementButton(assetManager,this,"(145)",61,"Pm","Promethium",new Vector3f(1.84f,quad.getHeight()-2.12f,0.05f),new Solution(new Substance("Pm","Promethium",1,"Solid",2,298,1315,3273,-1,"None",145,7.26)),new ColorRGBA(127,63,191,1)));
        buttons.add(new ElementButton(assetManager,this,"150.36",62,"Sm","Samarium",new Vector3f(2.1f,quad.getHeight()-2.12f,0.05f),new Solution(new Substance("Sm","Samarium",1,"Solid",2,298,1345,2173,-1,"None",150.36,7.52)),new ColorRGBA(127,63,191,1)));
        buttons.add(new ElementButton(assetManager,this,"151.964",63,"Eu","Europium",new Vector3f(2.36f,quad.getHeight()-2.12f,0.05f),new Solution(new Substance("Eu","Europium",1,"Solid",2,298,1099,1802,-1,"None",151.964,5.264)),new ColorRGBA(127,63,191,1)));
        buttons.add(new ElementButton(assetManager,this,"157.25",64,"Gd","Gadolinium",new Vector3f(2.62f,quad.getHeight()-2.12f,0.05f),new Solution(new Substance("Gd","Gadolinium",1,"Solid",2,298,1585,3273,-1,"None",157.25,7.9)),new ColorRGBA(127,63,191,1)));
        buttons.add(new ElementButton(assetManager,this,"158.925",65,"Tb","Terbium",new Vector3f(2.88f,quad.getHeight()-2.12f,0.05f),new Solution(new Substance("Tb","Terbium",1,"Solid",2,298,1629,3396,-1,"None",158.925,8.23)),new ColorRGBA(127,63,191,1)));
        buttons.add(new ElementButton(assetManager,this,"162.5",66,"Dy","Dysprosium",new Vector3f(3.14f,quad.getHeight()-2.12f,0.05f),new Solution(new Substance("Dy","Dysprosium",1,"Solid",2,298,1680,2840,-1,"None",162.5,8.540)),new ColorRGBA(127,63,191,1)));
        buttons.add(new ElementButton(assetManager,this,"164.93",67,"Ho","Holmium",new Vector3f(3.4f,quad.getHeight()-2.12f,0.05f),new Solution(new Substance("Ho","Holmium",1,"Gas",0,298,1734,2873,-1,"None",164.93,8.79)),new ColorRGBA(127,63,191,1)));
        buttons.add(new ElementButton(assetManager,this,"167.259",68,"Er","Erbium",new Vector3f(3.66f,quad.getHeight()-2.12f,0.05f),new Solution(new Substance("Er","Erbium",1,"Solid",2,298,1802,3141,-1,"None",167.259,9.066)),new ColorRGBA(127,63,191,1)));
        buttons.add(new ElementButton(assetManager,this,"168.934",69,"Tm","Thulium",new Vector3f(3.92f,quad.getHeight()-2.12f,0.05f),new Solution(new Substance("Tm","Thulium",1,"Solid",2,298,1818,2223,-1,"None",168.934,9.32)),new ColorRGBA(127,63,191,1)));
        buttons.add(new ElementButton(assetManager,this,"173.054",70,"Yb","Ytterbium",new Vector3f(4.18f,quad.getHeight()-2.12f,0.05f),new Solution(new Substance("Yb","Ytterbium",1,"Solid",2,298,1097,1469,-1,"None",173.054,6.9)),new ColorRGBA(127,63,191,1)));
        buttons.add(new ElementButton(assetManager,this,"174.967",71,"Lu","Lutelium",new Vector3f(4.44f,quad.getHeight()-2.12f,0.05f),new Solution(new Substance("Lu","Lutetium",1,"Solid",2,298,1925,3675,-1,"None",174.967,9.841)),new ColorRGBA(127,63,191,1)));
        //row 9
        buttons.add(new ElementButton(assetManager,this,"(227)",89,"Ac","Actinium",new Vector3f(0.80f,quad.getHeight()-2.38f,0.05f),new Solution(new Substance("Ac","Actinium",1,"Solid",2,298,1500,3500,-1,"None",227,10)),ColorRGBA.Pink));
        buttons.add(new ElementButton(assetManager,this,"232.038",90,"Th","Thorium",new Vector3f(1.06f,quad.getHeight()-2.38f,0.05f),new Solution(new Substance("Th","Thorium",1,"Solid",2,298,2023,5061,-1,"None",232.038,11.7)),ColorRGBA.Pink));
        buttons.add(new ElementButton(assetManager,this,"231.035",91,"Pa","Protactinium",new Vector3f(1.32f,quad.getHeight()-2.38f,0.05f),new Solution(new Substance("Pa","Protactinium",1,"Solid",2,298,1841,4300,-1,"None",231.035,15.37)),ColorRGBA.Pink));
        buttons.add(new ElementButton(assetManager,this,"238.028",92,"U","Uranium",new Vector3f(1.58f,quad.getHeight()-2.38f,0.05f),new Solution(new Substance("U","Uranium",1,"Solid",2,298,1405.3,4404,-1,"None",238.028,19.1)),ColorRGBA.Pink));
        buttons.add(new ElementButton(assetManager,this,"(237)",93,"Np","Neptunium",new Vector3f(1.84f,quad.getHeight()-2.38f,0.05f),new Solution(new Substance("Np","Neptunium",1,"Solid",2,298,912,4447,-1,"None",237,20.45)),ColorRGBA.Pink));
        buttons.add(new ElementButton(assetManager,this,"(244)",94,"Pu","Plutonium",new Vector3f(2.1f,quad.getHeight()-2.38f,0.05f),new Solution(new Substance("Pu","Plutonium",1,"Solid",2,298,912.5,3505,-1,"None",244,19.816)),ColorRGBA.Pink));
        buttons.add(new ElementButton(assetManager,this,"(243)",95,"Am","Americium",new Vector3f(2.36f,quad.getHeight()-2.38f,0.05f),new Solution(new Substance("Am","Americium",1,"Solid",2,298,1449,2880,-1,"None",243,12)),ColorRGBA.Pink));
        buttons.add(new ElementButton(assetManager,this,"(247)",96,"Cm","Curium",new Vector3f(2.62f,quad.getHeight()-2.38f,0.05f),new Solution(new Substance("Cm","Curium",1,"Solid",2,298,1613,3383,-1,"None",247,13.51)),ColorRGBA.Pink));
        buttons.add(new ElementButton(assetManager,this,"(247)",97,"Bk","Berkelium",new Vector3f(2.88f,quad.getHeight()-2.38f,0.05f),new Solution(new Substance("Bk","Berkelium",1,"Solid",2,298,1259,2900,-1,"None",247,13.25)),ColorRGBA.Pink));
        buttons.add(new ElementButton(assetManager,this,"(251)",98,"Cf","Californium",new Vector3f(3.14f,quad.getHeight()-2.38f,0.05f),new Solution(new Substance("Cf","Californium",1,"Solid",2,298,1173,1743,-1,"None",251,15.1)),ColorRGBA.Pink));
        buttons.add(new ElementButton(assetManager,this,"(252)",99,"Es","Einsteinium",new Vector3f(3.4f,quad.getHeight()-2.38f,0.05f),new Solution(new Substance("Es","Einsteinium",1,"Solid",2,298,1133,1269,-1,"None",252,8.84)),ColorRGBA.Pink));
        buttons.add(new ElementButton(assetManager,this,"(257)",100,"Fm","Fermium",new Vector3f(3.66f,quad.getHeight()-2.38f,0.05f),new Solution(new Substance("Fm","Fermium",1,"Solid",2,298,1800,2000,-1,"None",257,9.7)),ColorRGBA.Pink));
        buttons.add(new ElementButton(assetManager,this,"(258)",101,"Md","Mendelevium",new Vector3f(3.92f,quad.getHeight()-2.38f,0.05f),new Solution(new Substance("Md","Mendelevium",1,"Solid",2,298,1100,1900,-1,"None",258,10.3)),ColorRGBA.Pink));
        buttons.add(new ElementButton(assetManager,this,"(259)",102,"No","Nobelium",new Vector3f(4.18f,quad.getHeight()-2.38f,0.05f),new Solution(new Substance("No","Nobel",1,"Solid",2,298,1100,1700,-1,"None",259,9.9)),ColorRGBA.Pink));
        buttons.add(new ElementButton(assetManager,this,"(262)",103,"Lr","Lawrencium",new Vector3f(4.44f,quad.getHeight()-2.38f,0.05f),new Solution(new Substance("Lr","Lawrencium",1,"Solid",2,298,1900,2500,-1,"None",262,15.9)),ColorRGBA.Pink));
        
    }
    
    public void createBackground(float width,float height){
        
        quad=new Quad(width,height);
        background=new Geometry("Periodic table display background",quad);
        background.setUserData("correctCollision",true);
        background.setUserData("correspondingObject", this);
        backgroundMat=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        backgroundMat.setColor("Color",new ColorRGBA(0,255,0,0.5f));
        backgroundMat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        background.setQueueBucket(Bucket.Transparent);
        background.setMaterial(backgroundMat);
        
        node.attachChild(background);
        
        background.setLocalTranslation(0,0,0f);
        
    }
    
    public Node getNode(){
        
        return node;
        
    }
    
    public float getHeightDimension(){
        
        return quad.getHeight();
        
    }
    
    public float getWidthDimension(){
        
        return quad.getWidth();
        
    }

    @Override
    public void write(JmeExporter je) throws IOException {
    }

    @Override
    public void read(JmeImporter ji) throws IOException {
    }
            
    
}
