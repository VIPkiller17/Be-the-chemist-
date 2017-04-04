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
import main.Main;
import objects.solution.Solution;
import objects.substance.Substance;

/**
 *
 * @author VIPkiller17
 */
public class PeriodicTableDisplay implements Pointable,Savable{
    
    private AssetManager assetManager;
    private Main main;
    
    private ArrayList<ElementButton> buttons;
    private ArrayList<BitmapText> texts;
    
    private BitmapFont font;
    
    private Spatial frame;
    private Quad quad;
    private Geometry background;
    private Material backgroundMat;
    
    private Node node;
    private Node rootNode;
    
    public PeriodicTableDisplay(Main main){
        
        this.main=main;
        this.assetManager=main.getAssetManager();
        this.rootNode=main.getRootNode();
        
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
        buttons.add(new ElementButton(main,this,"1.008",1,"H","Hydrogen",new Vector3f(0.02f,quad.getHeight()-0.02f,0.05f),new Solution(main.getSubstances().get(0)),ColorRGBA.Green));
        buttons.add(new ElementButton(main,this,"4.003",2,"He","Helium",new Vector3f(4.44f,quad.getHeight()-0.02f,0.05f),new Solution(main.getSubstances().get(1)),ColorRGBA.Blue));
        
        //row 2
        buttons.add(new ElementButton(main,this,"6.94",3,"Li","Lithium",new Vector3f(0.02f,quad.getHeight()-0.28f,0.05f),new Solution(main.getSubstances().get(2)),ColorRGBA.Orange));
        buttons.add(new ElementButton(main,this,"9.012",4,"Be","Beryllium",new Vector3f(0.28f,quad.getHeight()-0.28f,0.05f),new Solution(main.getSubstances().get(3)),ColorRGBA.Yellow));
        buttons.add(new ElementButton(main,this,"10.81",5,"B","Boron",new Vector3f(3.14f,quad.getHeight()-0.28f,0.05f),new Solution(main.getSubstances().get(4)),ColorRGBA.Red));
        buttons.add(new ElementButton(main,this,"12.011",6,"C","Carbon",new Vector3f(3.4f,quad.getHeight()-0.28f,0.05f),new Solution(main.getSubstances().get(5)),ColorRGBA.Green));
        buttons.add(new ElementButton(main,this,"14.007",7,"N","Nitrogen",new Vector3f(3.66f,quad.getHeight()-0.28f,0.05f),new Solution(main.getSubstances().get(6)),ColorRGBA.Green));
        buttons.add(new ElementButton(main,this,"15.999",8,"O","Oxygen",new Vector3f(3.92f,quad.getHeight()-0.28f,0.05f),new Solution(main.getSubstances().get(7)),ColorRGBA.Green));
        buttons.add(new ElementButton(main,this,"18.998",9,"F","Fluorine",new Vector3f(4.18f,quad.getHeight()-0.28f,0.05f),new Solution(main.getSubstances().get(8)),ColorRGBA.Cyan));
        buttons.add(new ElementButton(main,this,"20.18",10,"Ne","Neon",new Vector3f(4.44f,quad.getHeight()-0.28f,0.05f),new Solution(main.getSubstances().get(9)),ColorRGBA.Blue));
        //row 3
        buttons.add(new ElementButton(main,this,"22.989",11,"Na","Sodium",new Vector3f(0.02f,quad.getHeight()-0.54f,0.05f),new Solution(main.getSubstances().get(10)),ColorRGBA.Orange));
        buttons.add(new ElementButton(main,this,"24.305",12,"Mg","Magnesium",new Vector3f(0.28f,quad.getHeight()-0.54f,0.05f),new Solution(main.getSubstances().get(11)),ColorRGBA.Yellow));
        buttons.add(new ElementButton(main,this,"26.981",13,"Al","Aluminium",new Vector3f(3.14f,quad.getHeight()-0.54f,0.05f),new Solution(main.getSubstances().get(12)),ColorRGBA.Magenta));
        buttons.add(new ElementButton(main,this,"28.085",14,"Si","Silicon",new Vector3f(3.4f,quad.getHeight()-0.54f,0.05f),new Solution(main.getSubstances().get(13)),ColorRGBA.Red));
        buttons.add(new ElementButton(main,this,"30.973",15,"P","Phosphorus",new Vector3f(3.66f,quad.getHeight()-0.54f,0.05f),new Solution(main.getSubstances().get(14)),ColorRGBA.Green));
        buttons.add(new ElementButton(main,this,"32.06",16,"S","Sulfur",new Vector3f(3.92f,quad.getHeight()-0.54f,0.05f),new Solution(main.getSubstances().get(15)),ColorRGBA.Green));
        buttons.add(new ElementButton(main,this,"35.45",17,"Cl","Chlorine",new Vector3f(4.18f,quad.getHeight()-0.54f,0.05f),new Solution(main.getSubstances().get(16)),ColorRGBA.Cyan));
        buttons.add(new ElementButton(main,this,"39.948",18,"Ar","Argon",new Vector3f(4.44f,quad.getHeight()-0.54f,0.05f),new Solution(main.getSubstances().get(17)),ColorRGBA.Blue));
        //row 4
        buttons.add(new ElementButton(main,this,"39.098",19,"K","Potassium",new Vector3f(0.02f,quad.getHeight()-0.8f,0.05f),new Solution(main.getSubstances().get(18)),ColorRGBA.Orange));
        buttons.add(new ElementButton(main,this,"40.078",20,"Ca","Calcium",new Vector3f(0.28f,quad.getHeight()-0.8f,0.05f),new Solution(main.getSubstances().get(19)),ColorRGBA.Yellow));
        buttons.add(new ElementButton(main,this,"44.955",21,"Sc","Scandium",new Vector3f(0.54f,quad.getHeight()-0.8f,0.05f),new Solution(main.getSubstances().get(20)),ColorRGBA.Brown));
        buttons.add(new ElementButton(main,this,"47.867",22,"Ti","Titanium",new Vector3f(0.80f,quad.getHeight()-0.8f,0.05f),new Solution(main.getSubstances().get(21)),ColorRGBA.Brown));
        buttons.add(new ElementButton(main,this,"50.942",23,"V","Vanadium",new Vector3f(1.06f,quad.getHeight()-0.8f,0.05f),new Solution(main.getSubstances().get(22)),ColorRGBA.Brown));
        buttons.add(new ElementButton(main,this,"51.996",24,"Cr","Chromium",new Vector3f(1.32f,quad.getHeight()-0.8f,0.05f),new Solution(main.getSubstances().get(23)),ColorRGBA.Brown));
        buttons.add(new ElementButton(main,this,"54.938",25,"Mn","Manganese",new Vector3f(1.58f,quad.getHeight()-0.8f,0.05f),new Solution(main.getSubstances().get(24)),ColorRGBA.Brown));
        buttons.add(new ElementButton(main,this,"55.845",26,"Fe","Iron",new Vector3f(1.84f,quad.getHeight()-0.8f,0.05f),new Solution(main.getSubstances().get(25)),ColorRGBA.Brown));
        buttons.add(new ElementButton(main,this,"58.933",27,"Co","Cobalt",new Vector3f(2.1f,quad.getHeight()-0.8f,0.05f),new Solution(main.getSubstances().get(26)),ColorRGBA.Brown));
        buttons.add(new ElementButton(main,this,"58.693",28,"Ni","Nickel",new Vector3f(2.36f,quad.getHeight()-0.8f,0.05f),new Solution(main.getSubstances().get(27)),ColorRGBA.Brown));
        buttons.add(new ElementButton(main,this,"63.546",29,"Cu","Copper",new Vector3f(2.62f,quad.getHeight()-0.8f,0.05f),new Solution(main.getSubstances().get(28)),ColorRGBA.Brown));
        buttons.add(new ElementButton(main,this,"65.38",30,"Zn","Zinc",new Vector3f(2.88f,quad.getHeight()-0.8f,0.05f),new Solution(main.getSubstances().get(29)),ColorRGBA.Brown));
        buttons.add(new ElementButton(main,this,"69.723",31,"Ga","Gallium",new Vector3f(3.14f,quad.getHeight()-0.8f,0.05f),new Solution(main.getSubstances().get(30)),ColorRGBA.Magenta));
        buttons.add(new ElementButton(main,this,"72.63",32,"Ge","Germanium",new Vector3f(3.4f,quad.getHeight()-0.8f,0.05f),new Solution(main.getSubstances().get(31)),ColorRGBA.Red));
        buttons.add(new ElementButton(main,this,"74.922",33,"As","Arsenic",new Vector3f(3.66f,quad.getHeight()-0.8f,0.05f),new Solution(main.getSubstances().get(32)),ColorRGBA.Red));
        buttons.add(new ElementButton(main,this,"78.96",34,"Se","Selenium",new Vector3f(3.92f,quad.getHeight()-0.8f,0.05f),new Solution(main.getSubstances().get(33)),ColorRGBA.Green));
        buttons.add(new ElementButton(main,this,"79.904",35,"Br","Bromine",new Vector3f(4.18f,quad.getHeight()-0.8f,0.05f),new Solution(main.getSubstances().get(34)),ColorRGBA.Cyan));
        buttons.add(new ElementButton(main,this,"83.798",36,"Kr","Krypton",new Vector3f(4.44f,quad.getHeight()-0.8f,0.05f),new Solution(main.getSubstances().get(35)),ColorRGBA.Blue));
        //row 5
        buttons.add(new ElementButton(main,this,"85.468",37,"Rb","Rubidium",new Vector3f(0.02f,quad.getHeight()-1.06f,0.05f),new Solution(main.getSubstances().get(36)),ColorRGBA.Orange));
        buttons.add(new ElementButton(main,this,"87.62",38,"Sr","Strontium",new Vector3f(0.28f,quad.getHeight()-1.06f,0.05f),new Solution(main.getSubstances().get(37)),ColorRGBA.Yellow));
        buttons.add(new ElementButton(main,this,"88.906",39,"Y","Yttrium",new Vector3f(0.54f,quad.getHeight()-1.06f,0.05f),new Solution(main.getSubstances().get(38)),ColorRGBA.Brown));
        buttons.add(new ElementButton(main,this,"91.224",40,"Zr","Zirconium",new Vector3f(0.80f,quad.getHeight()-1.06f,0.05f),new Solution(main.getSubstances().get(39)),ColorRGBA.Brown));
        buttons.add(new ElementButton(main,this,"92.906",41,"Nb","Niobium",new Vector3f(1.06f,quad.getHeight()-1.06f,0.05f),new Solution(main.getSubstances().get(40)),ColorRGBA.Brown));
        buttons.add(new ElementButton(main,this,"95.96",42,"Mo","Molybdenum",new Vector3f(1.32f,quad.getHeight()-1.06f,0.05f),new Solution(main.getSubstances().get(41)),ColorRGBA.Brown));
        buttons.add(new ElementButton(main,this,"98",43,"Tc","Technetium",new Vector3f(1.58f,quad.getHeight()-1.06f,0.05f),new Solution(main.getSubstances().get(42)),ColorRGBA.Brown));
        buttons.add(new ElementButton(main,this,"101.07",44,"Ru","Ruthenium",new Vector3f(1.84f,quad.getHeight()-1.06f,0.05f),new Solution(main.getSubstances().get(43)),ColorRGBA.Brown));
        buttons.add(new ElementButton(main,this,"102.905",45,"Rh","Rhodium",new Vector3f(2.1f,quad.getHeight()-1.06f,0.05f),new Solution(main.getSubstances().get(44)),ColorRGBA.Brown));
        buttons.add(new ElementButton(main,this,"106.42",46,"Pd","Palladium",new Vector3f(2.36f,quad.getHeight()-1.06f,0.05f),new Solution(main.getSubstances().get(45)),ColorRGBA.Brown));
        buttons.add(new ElementButton(main,this,"107.868",47,"Ag","Silver",new Vector3f(2.62f,quad.getHeight()-1.06f,0.05f),new Solution(main.getSubstances().get(46)),ColorRGBA.Brown));
        buttons.add(new ElementButton(main,this,"112.411",48,"Cd","Cadmium",new Vector3f(2.88f,quad.getHeight()-1.06f,0.05f),new Solution(main.getSubstances().get(47)),ColorRGBA.Brown));
        buttons.add(new ElementButton(main,this,"114.818",49,"In","Indium",new Vector3f(3.14f,quad.getHeight()-1.06f,0.05f),new Solution(main.getSubstances().get(48)),ColorRGBA.Magenta));
        buttons.add(new ElementButton(main,this,"118.71",50,"Sn","Tin",new Vector3f(3.4f,quad.getHeight()-1.06f,0.05f),new Solution(main.getSubstances().get(49)),ColorRGBA.Magenta));
        buttons.add(new ElementButton(main,this,"121.76",51,"Sb","Antimony",new Vector3f(3.66f,quad.getHeight()-1.06f,0.05f),new Solution(main.getSubstances().get(50)),ColorRGBA.Red));
        buttons.add(new ElementButton(main,this,"127.6",52,"Te","Tellurium",new Vector3f(3.92f,quad.getHeight()-1.06f,0.05f),new Solution(main.getSubstances().get(51)),ColorRGBA.Red));
        buttons.add(new ElementButton(main,this,"126.904",53,"I","Iodine",new Vector3f(4.18f,quad.getHeight()-1.06f,0.05f),new Solution(main.getSubstances().get(52)),ColorRGBA.Cyan));
        buttons.add(new ElementButton(main,this,"131.293",54,"Xe","Xenon",new Vector3f(4.44f,quad.getHeight()-1.06f,0.05f),new Solution(main.getSubstances().get(53)),ColorRGBA.Blue));
        //row 6
        buttons.add(new ElementButton(main,this,"132.905",55,"Cs","Caesium",new Vector3f(0.02f,quad.getHeight()-1.32f,0.05f),new Solution(main.getSubstances().get(54)),ColorRGBA.Orange));
        buttons.add(new ElementButton(main,this,"137.327",56,"Ba","Barium",new Vector3f(0.28f,quad.getHeight()-1.32f,0.05f),new Solution(main.getSubstances().get(55)),ColorRGBA.Yellow));
        buttons.add(new ElementButton(main,this,"178.49",72,"Hf","Hafnium",new Vector3f(0.80f,quad.getHeight()-1.32f,0.05f),new Solution(main.getSubstances().get(56)),ColorRGBA.Brown));
        buttons.add(new ElementButton(main,this,"180.947",73,"Ta","Tantalum",new Vector3f(1.06f,quad.getHeight()-1.32f,0.05f),new Solution(main.getSubstances().get(57)),ColorRGBA.Brown));
        buttons.add(new ElementButton(main,this,"183.84",74,"W","Tungsten",new Vector3f(1.32f,quad.getHeight()-1.32f,0.05f),new Solution(main.getSubstances().get(58)),ColorRGBA.Brown));
        buttons.add(new ElementButton(main,this,"186.207",75,"Re","Rhenium",new Vector3f(1.58f,quad.getHeight()-1.32f,0.05f),new Solution(main.getSubstances().get(59)),ColorRGBA.Brown));
        buttons.add(new ElementButton(main,this,"190.23",76,"Os","Osmium",new Vector3f(1.84f,quad.getHeight()-1.32f,0.05f),new Solution(main.getSubstances().get(60)),ColorRGBA.Brown));
        buttons.add(new ElementButton(main,this,"192.217",77,"Ir","Iridium",new Vector3f(2.1f,quad.getHeight()-1.32f,0.05f),new Solution(main.getSubstances().get(61)),ColorRGBA.Brown));
        buttons.add(new ElementButton(main,this,"195.084",78,"Pt","Platinum",new Vector3f(2.36f,quad.getHeight()-1.32f,0.05f),new Solution(main.getSubstances().get(62)),ColorRGBA.Brown));
        buttons.add(new ElementButton(main,this,"196.966",79,"Au","Gold",new Vector3f(2.62f,quad.getHeight()-1.32f,0.05f),new Solution(main.getSubstances().get(63)),ColorRGBA.Brown));
        buttons.add(new ElementButton(main,this,"200.59",80,"Hg","Mercury",new Vector3f(2.88f,quad.getHeight()-1.32f,0.05f),new Solution(main.getSubstances().get(64)),ColorRGBA.Brown));
        buttons.add(new ElementButton(main,this,"204.38",81,"Tl","Thallium",new Vector3f(3.14f,quad.getHeight()-1.32f,0.05f),new Solution(main.getSubstances().get(65)),ColorRGBA.Magenta));
        buttons.add(new ElementButton(main,this,"207.2",82,"Pb","Lead",new Vector3f(3.4f,quad.getHeight()-1.32f,0.05f),new Solution(main.getSubstances().get(66)),ColorRGBA.Magenta));
        buttons.add(new ElementButton(main,this,"208.98",83,"Bi","Bismuth",new Vector3f(3.66f,quad.getHeight()-1.32f,0.05f),new Solution(main.getSubstances().get(67)),ColorRGBA.Magenta));
        buttons.add(new ElementButton(main,this,"(209)",84,"Po","Polonium",new Vector3f(3.92f,quad.getHeight()-1.32f,0.05f),new Solution(new Substance("Po","Polonium",1,"Solid",2,298,527,1235,-1,"None",209,9.196)),ColorRGBA.Red));
        buttons.get(buttons.size()-1).setGrayedOut(true);
        buttons.add(new ElementButton(main,this,"(210)",85,"At","Astatine",new Vector3f(4.18f,quad.getHeight()-1.32f,0.05f),new Solution(new Substance("At","Astatine",1,"Solid",2,298,575,610,-1,"None",210,6.4)),ColorRGBA.Cyan));
        buttons.get(buttons.size()-1).setGrayedOut(true);
        buttons.add(new ElementButton(main,this,"(222)",86,"Rn","Radon",new Vector3f(4.44f,quad.getHeight()-1.32f,0.05f),new Solution(new Substance("Rn","Radon",1,"Gas",0,298,202,211.5,-1,"None",222,9.73)),ColorRGBA.Blue));
        buttons.get(buttons.size()-1).setGrayedOut(true);
        
        //row 7
        buttons.add(new ElementButton(main,this,"(223)",87,"Fr","Francium",new Vector3f(0.02f,quad.getHeight()-1.58f,0.05f),new Solution(new Substance("Fr","Francium",1,"Solid",2,298,300,950,-1,"None",223,2.9)),ColorRGBA.Orange));
        buttons.get(buttons.size()-1).setGrayedOut(true);
        buttons.add(new ElementButton(main,this,"(226)",88,"Ra","Radium",new Vector3f(0.28f,quad.getHeight()-1.58f,0.05f),new Solution(new Substance("Ra","Radium",1,"Solid",2,298,973,2010,-1,"None",226,5.5)),ColorRGBA.Yellow));
        buttons.get(buttons.size()-1).setGrayedOut(true);
        buttons.add(new ElementButton(main,this,"(267)",104,"Rf","Rutherfordium",new Vector3f(0.80f,quad.getHeight()-1.58f,0.05f),new Solution(new Substance("Rf","Rutherfordium",1,"Solid",2,298,2400,5800,-1,"None",267,23.2)),ColorRGBA.Brown));
        buttons.get(buttons.size()-1).setGrayedOut(true);
        buttons.add(new ElementButton(main,this,"(268)",105,"Db","Dubnium",new Vector3f(1.06f,quad.getHeight()-1.58f,0.05f),new Solution(new Substance("Db","Dubnium",1,"Solid",2,298,3000,6800,-1,"None",268,29.3)),ColorRGBA.Brown));
        buttons.get(buttons.size()-1).setGrayedOut(true);
        buttons.add(new ElementButton(main,this,"(271)",106,"Sg","Seaborgium",new Vector3f(1.32f,quad.getHeight()-1.58f,0.05f),new Solution(new Substance("Sg","Seaborgium",1,"Solid",2,298,3400,7300,-1,"None",271,35)),ColorRGBA.Brown));
        buttons.get(buttons.size()-1).setGrayedOut(true);
        buttons.add(new ElementButton(main,this,"(272)",107,"Bh","Bohrium",new Vector3f(1.58f,quad.getHeight()-1.58f,0.05f),new Solution(new Substance("Bh","Bohrium",1,"Solid",2,298,3200,7000,-1,"None",272,37.1)),ColorRGBA.Brown));
        buttons.get(buttons.size()-1).setGrayedOut(true);
        buttons.add(new ElementButton(main,this,"(270)",108,"Hs","Hassium",new Vector3f(1.84f,quad.getHeight()-1.58f,0.05f),new Solution(new Substance("Hs","Hassium",1,"Solid",2,298,3100,6400,-1,"None",270,41)),ColorRGBA.Brown));
        buttons.get(buttons.size()-1).setGrayedOut(true);
        buttons.add(new ElementButton(main,this,"(276)",109,"Mt","Meitnerium",new Vector3f(2.1f,quad.getHeight()-1.58f,0.05f),new Solution(new Substance("Mt","Meitnerium",1,"Solid",2,298,5000,5600,-1,"None",276,37.4)),ColorRGBA.Brown));
        buttons.get(buttons.size()-1).setGrayedOut(true);
        buttons.add(new ElementButton(main,this,"(281)",110,"Ds","Darmstadtium",new Vector3f(2.36f,quad.getHeight()-1.58f,0.05f),new Solution(new Substance("Ds","Darmstadtium",1,"Solid",2,298,5100,5200,-1,"None",281,34.8)),ColorRGBA.Brown));
        buttons.get(buttons.size()-1).setGrayedOut(true);
        buttons.add(new ElementButton(main,this,"(280)",111,"Rg","Roentgenium",new Vector3f(2.62f,quad.getHeight()-1.58f,0.05f),new Solution(new Substance("Rg","Roentgenium",1,"Solid",2,298,4400,4600,-1,"None",280,28.7)),ColorRGBA.Brown));
        buttons.get(buttons.size()-1).setGrayedOut(true);
        buttons.add(new ElementButton(main,this,"(285)",112,"Cn","Copernicium",new Vector3f(2.88f,quad.getHeight()-1.58f,0.05f),new Solution(new Substance("Cn","Copernicium",1,"Gas",0,298,100,250,-1,"None",285,23.7)),ColorRGBA.Brown));
        buttons.get(buttons.size()-1).setGrayedOut(true);
        buttons.add(new ElementButton(main,this,"(284)",113,"Nh","Nihonium",new Vector3f(3.14f,quad.getHeight()-1.58f,0.05f),new Solution(new Substance("Nh","Nihonium",1,"Solid",2,298,700,1430,-1,"None",284,16)),ColorRGBA.Magenta));
        buttons.get(buttons.size()-1).setGrayedOut(true);
        buttons.add(new ElementButton(main,this,"(289)",114,"Fl","Flerovium",new Vector3f(3.4f,quad.getHeight()-1.58f,0.05f),new Solution(new Substance("Fl","Flerovium",1,"Solid",2,298,340,420,-1,"None",289,14)),ColorRGBA.Magenta));
        buttons.get(buttons.size()-1).setGrayedOut(true);
        buttons.add(new ElementButton(main,this,"(288)",115,"Mc","Moscovium",new Vector3f(3.66f,quad.getHeight()-1.58f,0.05f),new Solution(new Substance("Mc","Moscovium",1,"Solid",2,298,670,1400,-1,"None",288,13.5)),ColorRGBA.Magenta));
        buttons.get(buttons.size()-1).setGrayedOut(true);
        buttons.add(new ElementButton(main,this,"(293)",116,"Lv","Livermorium",new Vector3f(3.92f,quad.getHeight()-1.58f,0.05f),new Solution(new Substance("Lv","Livermorium",1,"Solid",2,298,700,1085,-1,"None",293,12.9)),ColorRGBA.Magenta));
        buttons.get(buttons.size()-1).setGrayedOut(true);
        buttons.add(new ElementButton(main,this,"(294)",117,"Ts","Tennessine",new Vector3f(4.18f,quad.getHeight()-1.58f,0.05f),new Solution(new Substance("Ts","Tennessine",1,"Solid",2,298,723,883,-1,"None",294,7.2)),ColorRGBA.Cyan));
        buttons.get(buttons.size()-1).setGrayedOut(true);
        buttons.add(new ElementButton(main,this,"(294)",118,"Og","Oganesson",new Vector3f(4.44f,quad.getHeight()-1.58f,0.05f),new Solution(new Substance("Og","Oganesson",1,"Solid",2,298,300,380,-1,"None",294,5)),ColorRGBA.Blue));
        buttons.get(buttons.size()-1).setGrayedOut(true);
        
        //row 8
        buttons.add(new ElementButton(main,this,"138.905",57,"La","Lanthanum",new Vector3f(0.80f,quad.getHeight()-2.12f,0.05f),new Solution(main.getSubstances().get(68)),new ColorRGBA(127,63,191,1)));
        buttons.add(new ElementButton(main,this,"140.116",58,"Ce","Cerium",new Vector3f(1.06f,quad.getHeight()-2.12f,0.05f),new Solution(main.getSubstances().get(69)),new ColorRGBA(127,63,191,1)));
        buttons.add(new ElementButton(main,this,"140.907",59,"Pr","Praseodymium",new Vector3f(1.32f,quad.getHeight()-2.12f,0.05f),new Solution(main.getSubstances().get(70)),new ColorRGBA(127,63,191,1)));
        buttons.add(new ElementButton(main,this,"144.242",60,"Nd","Neodymium",new Vector3f(1.58f,quad.getHeight()-2.12f,0.05f),new Solution(main.getSubstances().get(71)),new ColorRGBA(127,63,191,1)));
        buttons.add(new ElementButton(main,this,"(145)",61,"Pm","Promethium",new Vector3f(1.84f,quad.getHeight()-2.12f,0.05f),new Solution(new Substance("Pm","Promethium",1,"Solid",2,298,1315,3273,-1,"None",145,7.26)),new ColorRGBA(127,63,191,1)));
        buttons.get(buttons.size()-1).setGrayedOut(true);
        buttons.add(new ElementButton(main,this,"150.36",62,"Sm","Samarium",new Vector3f(2.1f,quad.getHeight()-2.12f,0.05f),new Solution(main.getSubstances().get(72)),new ColorRGBA(127,63,191,1)));
        buttons.add(new ElementButton(main,this,"151.964",63,"Eu","Europium",new Vector3f(2.36f,quad.getHeight()-2.12f,0.05f),new Solution(main.getSubstances().get(73)),new ColorRGBA(127,63,191,1)));
        buttons.add(new ElementButton(main,this,"157.25",64,"Gd","Gadolinium",new Vector3f(2.62f,quad.getHeight()-2.12f,0.05f),new Solution(main.getSubstances().get(74)),new ColorRGBA(127,63,191,1)));
        buttons.add(new ElementButton(main,this,"158.925",65,"Tb","Terbium",new Vector3f(2.88f,quad.getHeight()-2.12f,0.05f),new Solution(main.getSubstances().get(75)),new ColorRGBA(127,63,191,1)));
        buttons.add(new ElementButton(main,this,"162.5",66,"Dy","Dysprosium",new Vector3f(3.14f,quad.getHeight()-2.12f,0.05f),new Solution(main.getSubstances().get(76)),new ColorRGBA(127,63,191,1)));
        buttons.add(new ElementButton(main,this,"164.93",67,"Ho","Holmium",new Vector3f(3.4f,quad.getHeight()-2.12f,0.05f),new Solution(main.getSubstances().get(77)),new ColorRGBA(127,63,191,1)));
        buttons.add(new ElementButton(main,this,"167.259",68,"Er","Erbium",new Vector3f(3.66f,quad.getHeight()-2.12f,0.05f),new Solution(main.getSubstances().get(78)),new ColorRGBA(127,63,191,1)));
        buttons.add(new ElementButton(main,this,"168.934",69,"Tm","Thulium",new Vector3f(3.92f,quad.getHeight()-2.12f,0.05f),new Solution(main.getSubstances().get(79)),new ColorRGBA(127,63,191,1)));
        buttons.add(new ElementButton(main,this,"173.054",70,"Yb","Ytterbium",new Vector3f(4.18f,quad.getHeight()-2.12f,0.05f),new Solution(main.getSubstances().get(80)),new ColorRGBA(127,63,191,1)));
        buttons.add(new ElementButton(main,this,"174.967",71,"Lu","Lutelium",new Vector3f(4.44f,quad.getHeight()-2.12f,0.05f),new Solution(new Substance("Lu","Lutetium",1,"Solid",2,298,1925,3675,-1,"None",174.967,9.841)),new ColorRGBA(127,63,191,1)));
        //row 9
        buttons.add(new ElementButton(main,this,"(227)",89,"Ac","Actinium",new Vector3f(0.80f,quad.getHeight()-2.38f,0.05f),new Solution(new Substance("Ac","Actinium",1,"Solid",2,298,1500,3500,-1,"None",227,10)),ColorRGBA.Pink));
        buttons.get(buttons.size()-1).setGrayedOut(true);
        buttons.add(new ElementButton(main,this,"232.038",90,"Th","Thorium",new Vector3f(1.06f,quad.getHeight()-2.38f,0.05f),new Solution(new Substance("Th","Thorium",1,"Solid",2,298,2023,5061,-1,"None",232.038,11.7)),ColorRGBA.Pink));
        buttons.get(buttons.size()-1).setGrayedOut(true);
        buttons.add(new ElementButton(main,this,"231.035",91,"Pa","Protactinium",new Vector3f(1.32f,quad.getHeight()-2.38f,0.05f),new Solution(new Substance("Pa","Protactinium",1,"Solid",2,298,1841,4300,-1,"None",231.035,15.37)),ColorRGBA.Pink));
        buttons.get(buttons.size()-1).setGrayedOut(true);
        buttons.add(new ElementButton(main,this,"238.028",92,"U","Uranium",new Vector3f(1.58f,quad.getHeight()-2.38f,0.05f),new Solution(new Substance("U","Uranium",1,"Solid",2,298,1405.3,4404,-1,"None",238.028,19.1)),ColorRGBA.Pink));
        buttons.get(buttons.size()-1).setGrayedOut(true);
        buttons.add(new ElementButton(main,this,"(237)",93,"Np","Neptunium",new Vector3f(1.84f,quad.getHeight()-2.38f,0.05f),new Solution(new Substance("Np","Neptunium",1,"Solid",2,298,912,4447,-1,"None",237,20.45)),ColorRGBA.Pink));
        buttons.get(buttons.size()-1).setGrayedOut(true);
        buttons.add(new ElementButton(main,this,"(244)",94,"Pu","Plutonium",new Vector3f(2.1f,quad.getHeight()-2.38f,0.05f),new Solution(new Substance("Pu","Plutonium",1,"Solid",2,298,912.5,3505,-1,"None",244,19.816)),ColorRGBA.Pink));
        buttons.get(buttons.size()-1).setGrayedOut(true);
        buttons.add(new ElementButton(main,this,"(243)",95,"Am","Americium",new Vector3f(2.36f,quad.getHeight()-2.38f,0.05f),new Solution(new Substance("Am","Americium",1,"Solid",2,298,1449,2880,-1,"None",243,12)),ColorRGBA.Pink));
        buttons.get(buttons.size()-1).setGrayedOut(true);
        buttons.add(new ElementButton(main,this,"(247)",96,"Cm","Curium",new Vector3f(2.62f,quad.getHeight()-2.38f,0.05f),new Solution(new Substance("Cm","Curium",1,"Solid",2,298,1613,3383,-1,"None",247,13.51)),ColorRGBA.Pink));
        buttons.get(buttons.size()-1).setGrayedOut(true);
        buttons.add(new ElementButton(main,this,"(247)",97,"Bk","Berkelium",new Vector3f(2.88f,quad.getHeight()-2.38f,0.05f),new Solution(new Substance("Bk","Berkelium",1,"Solid",2,298,1259,2900,-1,"None",247,13.25)),ColorRGBA.Pink));
        buttons.get(buttons.size()-1).setGrayedOut(true);
        buttons.add(new ElementButton(main,this,"(251)",98,"Cf","Californium",new Vector3f(3.14f,quad.getHeight()-2.38f,0.05f),new Solution(new Substance("Cf","Californium",1,"Solid",2,298,1173,1743,-1,"None",251,15.1)),ColorRGBA.Pink));
        buttons.get(buttons.size()-1).setGrayedOut(true);
        buttons.add(new ElementButton(main,this,"(252)",99,"Es","Einsteinium",new Vector3f(3.4f,quad.getHeight()-2.38f,0.05f),new Solution(new Substance("Es","Einsteinium",1,"Solid",2,298,1133,1269,-1,"None",252,8.84)),ColorRGBA.Pink));
        buttons.get(buttons.size()-1).setGrayedOut(true);
        buttons.add(new ElementButton(main,this,"(257)",100,"Fm","Fermium",new Vector3f(3.66f,quad.getHeight()-2.38f,0.05f),new Solution(new Substance("Fm","Fermium",1,"Solid",2,298,1800,2000,-1,"None",257,9.7)),ColorRGBA.Pink));
        buttons.get(buttons.size()-1).setGrayedOut(true);
        buttons.add(new ElementButton(main,this,"(258)",101,"Md","Mendelevium",new Vector3f(3.92f,quad.getHeight()-2.38f,0.05f),new Solution(new Substance("Md","Mendelevium",1,"Solid",2,298,1100,1900,-1,"None",258,10.3)),ColorRGBA.Pink));
        buttons.get(buttons.size()-1).setGrayedOut(true);
        buttons.add(new ElementButton(main,this,"(259)",102,"No","Nobelium",new Vector3f(4.18f,quad.getHeight()-2.38f,0.05f),new Solution(new Substance("No","Nobel",1,"Solid",2,298,1100,1700,-1,"None",259,9.9)),ColorRGBA.Pink));
        buttons.get(buttons.size()-1).setGrayedOut(true);
        buttons.add(new ElementButton(main,this,"(262)",103,"Lr","Lawrencium",new Vector3f(4.44f,quad.getHeight()-2.38f,0.05f),new Solution(new Substance("Lr","Lawrencium",1,"Solid",2,298,1900,2500,-1,"None",262,15.9)),ColorRGBA.Pink));
        buttons.get(buttons.size()-1).setGrayedOut(true);
        
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
