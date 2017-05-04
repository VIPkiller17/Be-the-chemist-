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
import java.util.ArrayList;
import main.Main;
import objects.PhysicalObject;
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
public class SubstanceButton implements Savable{
    
    private static final ColorRGBA SELECTED_COLOR=new ColorRGBA(0,255,255,1f);
    private static final ColorRGBA NORMAL_COLOR=new ColorRGBA(0,0,255,1f);
    
    private AssetManager assetManager;
    private Main main;
    
    private Display substanceList;
    
    private Substance substance;
    
    private BitmapText text;
    
    private BitmapFont font;
    
    private Quad quad;
    private Geometry background;
    private Material backgroundMat;
    private ColorRGBA backgroundColor;
    
    private boolean grayedOut;
    
    private Node node;
    
    private boolean selected;
    
    
    public SubstanceButton(Main main,Display substanceList,Substance substance,Vector3f upperLeftPosition){
        
        this.main=main;
        this.assetManager=main.getAssetManager();
        this.substanceList=substanceList;
        this.substance=substance;
        
        node=new Node();
        font=assetManager.loadFont("Interface/Fonts/Hack/Hack.fnt");
        
        text=new BitmapText(font);
        text.setText(substance.getName());
        text.setSize(0.05f);
        text.setQueueBucket(RenderQueue.Bucket.Translucent);
        node.attachChild(text);
        text.setLocalTranslation(0.02f,-0.075f+(text.getLineHeight()/2),0.005f);
        
        createBackground(0.70f,0.15f);
        
        substanceList.getNode().attachChild(node);
        
        node.setLocalTranslation(upperLeftPosition);
        
    }
    
    private void createBackground(float width,float height){
        
        quad=new Quad(width,height);
        background=new Geometry("Substance button background",quad);
        background.setUserData("correctCollision",true);
        background.setUserData("correspondingObject", this);
        backgroundMat=new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
        backgroundMat.setColor("Color",NORMAL_COLOR);
        background.setMaterial(backgroundMat);
        
        node.attachChild(background);
        
        background.setLocalTranslation(0,-height,0);
        
    }
    
    public void activate(){
        
        //System.out.println("Substance button acitavted");
        
        setSelected(!selected);
        
    }
    
    public void setSelected(boolean selected){
        
        this.selected=selected;
        
        //System.out.println("Substance button selected set to: "+selected);
        
        if(selected){
            
            backgroundMat.setColor("Color", SELECTED_COLOR);
            
            for(SubstanceButton b: substanceList.getSubstanceButtonList()){
                
                if(!b.equals(this)){
                    
                    b.setSelected(false);
                    
                }
                
            }
            
            substanceList.setSelectedSubstanceButton(this);
            
        }else{
            
            backgroundMat.setColor("Color", NORMAL_COLOR);
            
            substanceList.setSelectedSubstanceButton(null);
            
        }
        
    }
    
    public Substance getSubstance(){
        
        return substance;
        
    }
    
    public void setPosition(int positionIndex){
        
        switch(positionIndex){
            
            case -1:
                node.setLocalTranslation(-0.35f,-700f,0.05f);
                break;
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                node.setLocalTranslation(-0.35f,0.5f-positionIndex*0.2f,0.05f);
                break;
            default:
                node.setLocalTranslation(-0.35f,-700f,0.05f);
            
        }
        
    }

    @Override
    public void write(JmeExporter je) throws IOException {
    }

    @Override
    public void read(JmeImporter ji) throws IOException {
    }
    
    @Override
    public boolean equals(Object otherSubstanceButton){
        
        if(otherSubstanceButton instanceof SubstanceButton){
            
            return ((SubstanceButton)otherSubstanceButton).getSubstance().equals(substance);
            
        }else{
            
            return false;
            
        }
        
    }
    
}
