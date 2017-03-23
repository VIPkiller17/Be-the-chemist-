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
        buttons.add(new ElementButton(assetManager,this,"1.008",1,"H","Hydrogen",new Vector3f((-quad.getWidth()/2)+0.01f,(-quad.getHeight()/2)-0.01f,0.05f),new Solution(new Substance("H\u2082","Hydrogen gas",1,"Gas",0,273,13.99,20.271,-1,"None",2.016,0.07)),ColorRGBA.Green));
        buttons.add(new ElementButton(assetManager,this,"4.003",2,"He","Helium",new Vector3f((quad.getWidth()/2)-0.26f,(-quad.getHeight()/2)-0.01f,0.05f),new Solution(new Substance("He","Helium",1,"Gas",0,273,0.95,4.222,-1,"None",4.002,0.1786)),ColorRGBA.Blue));
        //row 2
        buttons.add(new ElementButton(assetManager,this,"6.94",3,"Li","Lithium",new Vector3f((-quad.getWidth()/2)+0.01f,(-quad.getHeight()/2)-0.26f,0.05f),new Solution(new Substance("Li","Lithium",1,"Solid",2,273,453.65,1603,-1,"None",6.94,0.534)),ColorRGBA.Orange));
        buttons.add(new ElementButton(assetManager,this,"9.012",4,"Be","Beryllium",new Vector3f((-quad.getWidth()/2)+0.26f,(-quad.getHeight()/2)-0.26f,0.05f),new Solution(new Substance("Be","Beryllium",1,"Solid",2,273,1560,2742,-1,"None",9.012,1.85)),ColorRGBA.Yellow));
        buttons.add(new ElementButton(assetManager,this,"10.81",5,"B","Boron",new Vector3f((-quad.getWidth()/2)+3.12f,(-quad.getHeight()/2)-0.26f,0.05f),new Solution(new Substance("B","Boron",1,"Solid",2,273,2349,4200,-1,"None",10.81,2.08)),ColorRGBA.Red));
        buttons.add(new ElementButton(assetManager,this,"12.011",6,"C","Carbon",new Vector3f((-quad.getWidth()/2)+3.38f,(-quad.getHeight()/2)-0.26f,0.05f),new Solution(new Substance("C","Carbon",1,"Solid",2,273,-1,-1,-1,"None",12.011,2)),ColorRGBA.Green));
        buttons.add(new ElementButton(assetManager,this,"14.007",7,"N","Nitrogen",new Vector3f((-quad.getWidth()/2)+3.64f,(-quad.getHeight()/2)-0.26f,0.05f),new Solution(new Substance("N\u2082","Nitrogen gas",1,"Gas",0,273,63.15,77.355,-1,"None",28.014,1.251)),ColorRGBA.Green));
        buttons.add(new ElementButton(assetManager,this,"15.999",8,"O","Oxygen",new Vector3f((-quad.getWidth()/2)+3.90f,(-quad.getHeight()/2)-0.26f,0.05f),new Solution(new Substance("O\u2082","Oxygen gas",1,"Gas",0,273,54.361,90.188,-1,"None",31.998,1.429)),ColorRGBA.Green));
        buttons.add(new ElementButton(assetManager,this,"18.998",9,"F","Fluorine",new Vector3f((-quad.getWidth()/2)+4.16f,(-quad.getHeight()/2)-0.26f,0.05f),new Solution(new Substance("F\u2082","Fluorine gas",1,"Gas",0,273,53.48,85.03,-1,"None",37.996,1.696)),ColorRGBA.Cyan));
        buttons.add(new ElementButton(assetManager,this,"20.18",10,"Ne","Neon",new Vector3f((-quad.getWidth()/2)+4.42f,(-quad.getHeight()/2)-0.26f,0.05f),new Solution(new Substance("Ne","Neon",1,"Gas",0,273,24.56,27.104,-1,"None",20.18,0.9)),ColorRGBA.Blue));
        //row 3
        
        
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
