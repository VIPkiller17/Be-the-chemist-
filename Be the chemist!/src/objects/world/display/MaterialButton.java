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

/**
 *
 * @author VIPkiller17
 */
public class MaterialButton implements Savable{
    
    private static final ColorRGBA SELECTED_COLOR=new ColorRGBA(0,255,255,1f);
    private static final ColorRGBA NORMAL_COLOR=new ColorRGBA(0,0,255,1f);
    
    private AssetManager assetManager;
    private Main main;
    
    private Display materialList;
    
    private BitmapText text;
    
    private BitmapFont font;
    
    private Quad quad;
    private Geometry background;
    private Material backgroundMat;
    private ColorRGBA backgroundColor;
    
    private boolean grayedOut;
    
    private Node node;
    
    private boolean selected;
    
    private String name;
    
    
    public MaterialButton(Main main,Display materialList,String name,Vector3f upperLeftPosition){
        
        this.main=main;
        this.assetManager=main.getAssetManager();
        this.materialList=materialList;
        this.name=name;
        
        node=new Node();
        font=assetManager.loadFont("Interface/Fonts/Hack/Hack.fnt");
        
        text=new BitmapText(font);
        text.setText(name);
        text.setSize(0.05f);
        text.setQueueBucket(RenderQueue.Bucket.Translucent);
        node.attachChild(text);
        text.setLocalTranslation(0.02f,-0.075f+(text.getLineHeight()/2),0.005f);
        
        createBackground(0.70f,0.15f);
        
        materialList.getNode().attachChild(node);
        
        node.setLocalTranslation(upperLeftPosition);
        
    }
    
    private void createBackground(float width,float height){
        
        quad=new Quad(width,height);
        background=new Geometry("Material button background",quad);
        background.setUserData("correctCollision",true);
        background.setUserData("correspondingObject", this);
        backgroundMat=new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
        backgroundMat.setColor("Color",NORMAL_COLOR);
        background.setMaterial(backgroundMat);
        
        node.attachChild(background);
        
        background.setLocalTranslation(0,-height,0);
        
    }
    
    public void activate(){
        
        System.out.println("Material button acitavted");
        
        setSelected(!selected);
        
    }
    
    public void setSelected(boolean selected){
        
        this.selected=selected;
        
        System.out.println("Material button selected set to: "+selected);
        
        if(selected){
            
            backgroundMat.setColor("Color", SELECTED_COLOR);
            
            for(MaterialButton b: materialList.getMaterialButtonList()){
                
                if(!b.equals(this)){
                    
                    b.setSelected(false);
                    
                }
                
            }
            
            materialList.setSelectedMaterialButton(this);
            
        }else{
            
            backgroundMat.setColor("Color", NORMAL_COLOR);
            
            materialList.setSelectedMaterialButton(null);
            
        }
        
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
                System.out.println("ERROR: invalid position index:"+positionIndex+", sent to setPosition() of MaterialButton");
            
        }
        
    }
    
    public String getTextText(){
        
        return text.getText();
        
    }
    
    public String getName(){
        
        return name;
        
    }
    
    public int getClassInteger(){
        
        switch(name){
            
            case "Beaker":
                return 1;
            case "Gas sac":
                return 1;
            default:
                return 0;
            
        }
        
    }

    @Override
    public void write(JmeExporter je) throws IOException {
    }

    @Override
    public void read(JmeImporter ji) throws IOException {
    }
    
    @Override
    public boolean equals(Object otherMaterialButton){
        
        if(otherMaterialButton instanceof MaterialButton){
            
            return ((MaterialButton)otherMaterialButton).getTextText().equals(getTextText());
            
        }else{
            
            return false;
            
        }
        
    }
    
}
