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
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import interfaces.Grabbable;
import java.io.IOException;
import main.Main;
import objects.containers.Container;
import objects.containers.beaker.Beaker;
import objects.containers.gasSac.GasSac;
import objects.player.Hand;
import objects.solution.Solution;
import objects.substance.Substance;

/**
 *
 * @author VIPkiller17
 */
public class ElementButton implements Savable{
    
    private static final ColorRGBA GRAYED_OUT=ColorRGBA.Gray;
    
    private AssetManager assetManager;
    private Main main;
    
    private PeriodicTableDisplay periodicTableDisplay;
    
    private String mass;
    private int number;
    private String symbol;
    private String name;
    
    private Substance substance;
    
    private BitmapText massText,numberText,symbolText,nameText;
    
    private BitmapFont font;
    
    private Quad quad;
    private Geometry background;
    private Material backgroundMat;
    private ColorRGBA backgroundColor;
    
    private boolean grayedOut;
    
    private Node node;
    
    
    public ElementButton(Main main,PeriodicTableDisplay periodicTableDisplay,String mass,int number,String symbol,String name,Vector3f upperLeftPosition,Substance substance,ColorRGBA backgroundColor){
        
        this.main=main;
        this.assetManager=main.getAssetManager();
        this.periodicTableDisplay=periodicTableDisplay;
        this.mass=mass;
        this.number=number;
        this.symbol=symbol;
        this.name=name;
        this.substance=substance;
        this.backgroundColor=backgroundColor;
        
        node=new Node();
        font=assetManager.loadFont("Interface/Fonts/Hack/Hack.fnt");
        
        createBackground(0.25f,0.25f);
        
        massText=new BitmapText(font);
        massText.setSize(0.04f);
        massText.setQueueBucket(RenderQueue.Bucket.Translucent);
        massText.setText(mass);
        
        numberText=new BitmapText(font);
        numberText.setSize(0.04f);
        numberText.setQueueBucket(RenderQueue.Bucket.Translucent);
        numberText.setText(number+"");
        
        symbolText=new BitmapText(font);
        symbolText.setSize(0.1f);
        symbolText.setQueueBucket(RenderQueue.Bucket.Translucent);
        symbolText.setText(symbol);
        
        nameText=new BitmapText(font);
        nameText.setSize(0.03f);
        nameText.setQueueBucket(RenderQueue.Bucket.Translucent);
        nameText.setText(name);
        
        node.attachChild(this.massText);
        massText.setLocalTranslation(0.02f,-quad.getHeight()+massText.getLineHeight()+0.01f, 0.01f);
        node.attachChild(this.numberText);
        numberText.setLocalTranslation(0.02f,-0.01f, 0.01f);
        node.attachChild(this.symbolText);
        symbolText.setLocalTranslation(0.02f,(-quad.getHeight()/2)+(massText.getLineHeight()/2)+0.06f,0.01f);
        node.attachChild(this.nameText);
        nameText.setLocalTranslation(0.02f,(-quad.getHeight()/2)-(symbolText.getLineHeight()/2)+0.025f, 0.01f);
        
        periodicTableDisplay.getNode().attachChild(node);
        
        node.setLocalTranslation(upperLeftPosition);
        
    }
    
    public ElementButton(Main main,PeriodicTableDisplay periodicTableDisplay,String mass,int number,String symbol,String name,Vector3f upperLeftPosition){
        
        this.main=main;
        this.assetManager=main.getAssetManager();
        this.periodicTableDisplay=periodicTableDisplay;
        this.mass=mass;
        this.number=number;
        this.symbol=symbol;
        this.name=name;
        
        node=new Node();
        font=assetManager.loadFont("Interface/Fonts/Hack/Hack.fnt");
        
        createBackground(0.25f,0.25f);
        
        massText=new BitmapText(font);
        massText.setSize(0.04f);
        massText.setQueueBucket(RenderQueue.Bucket.Translucent);
        massText.setText(mass);
        
        numberText=new BitmapText(font);
        numberText.setSize(0.04f);
        numberText.setQueueBucket(RenderQueue.Bucket.Translucent);
        numberText.setText(number+"");
        
        symbolText=new BitmapText(font);
        symbolText.setSize(0.1f);
        symbolText.setQueueBucket(RenderQueue.Bucket.Translucent);
        symbolText.setText(symbol);
        
        nameText=new BitmapText(font);
        nameText.setSize(0.03f);
        nameText.setQueueBucket(RenderQueue.Bucket.Translucent);
        nameText.setText(name);
        
        node.attachChild(this.massText);
        massText.setLocalTranslation(0.02f,-quad.getHeight()+massText.getLineHeight()+0.01f, 0.01f);
        node.attachChild(this.numberText);
        numberText.setLocalTranslation(0.02f,-0.01f, 0.01f);
        node.attachChild(this.symbolText);
        symbolText.setLocalTranslation(0.02f,(-quad.getHeight()/2)+(massText.getLineHeight()/2)+0.06f,0.01f);
        node.attachChild(this.nameText);
        nameText.setLocalTranslation(0.02f,(-quad.getHeight()/2)-(symbolText.getLineHeight()/2)+0.025f, 0.01f);
        
        periodicTableDisplay.getNode().attachChild(node);
        
        node.setLocalTranslation(upperLeftPosition);
        
        setGrayedOut(true);
        
    }
    
    private void createBackground(float width,float height){
        
        quad=new Quad(width,height);
        background=new Geometry("Element button background",quad);
        background.setUserData("correctCollision",true);
        background.setUserData("correspondingObject", this);
        backgroundMat=new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
        backgroundMat.setColor("Color",backgroundColor);
        backgroundMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        background.setQueueBucket(RenderQueue.Bucket.Transparent);
        background.setMaterial(backgroundMat);
        
        node.attachChild(background);
        
        background.setLocalTranslation(0,-height,0);
        
    }
    
    public void activate(Hand hand){
        
        if(!grayedOut)
        
            if(hand.isHoldingObject()&&hand.getHeldObject() instanceof Container){

                if(substance.getStateInteger(298)==0&&((Container)hand.getHeldObject()).canContain(0)){

                    ((Container)hand.getHeldObject()).mergeSolution(new Solution(((Container)hand.getHeldObject()),substance));

                }else if(substance.getStateInteger(298)==1&&((Container)hand.getHeldObject()).canContain(1)){

                    ((Container)hand.getHeldObject()).mergeSolution(new Solution(((Container)hand.getHeldObject()),substance));

                }else if(substance.getStateInteger(298)==2&&((Container)hand.getHeldObject()).canContain(2)){

                    ((Container)hand.getHeldObject()).mergeSolution(new Solution(((Container)hand.getHeldObject()),substance));

                }

            }else if(!hand.isHoldingObject()){

                switch (substance.getStateInteger(298)) {
                    case 0:
                        hand.setHeldObject(new GasSac(main,hand.getWorldTranslation(),new Solution(((Container)hand.getHeldObject()),substance)));
                        break;
                    case 1:
                        hand.setHeldObject(new Beaker(main,hand.getWorldTranslation(),new Solution(((Container)hand.getHeldObject()),substance)));
                        break;
                    case 2:
                        hand.setHeldObject(new Beaker(main,hand.getWorldTranslation(),new Solution(((Container)hand.getHeldObject()),substance)));
                        break;
                    default:
                        System.out.println("ERROR: Invalid getGetStateInteger() return value in activate() of element button with name: "+name);
                        break;
                }

                ((Grabbable)hand.getHeldObject()).highlightVisible(false);

            }
        
    }
    
    public void setGrayedOut(boolean grayedOut){
        
        this.grayedOut=grayedOut;
        
        backgroundMat.setColor("Color", GRAYED_OUT);
        
    }
    
    public boolean isGrayedOut(){
        
        return grayedOut;
        
    }

    @Override
    public void write(JmeExporter je) throws IOException {
    }

    @Override
    public void read(JmeImporter ji) throws IOException {
    }
    
}
