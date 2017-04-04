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
    
    private static ArrayList<SubstanceButton> substanceButtonList=new ArrayList<SubstanceButton>();
    
    private static final ColorRGBA POINTED_COLOR=new ColorRGBA(0,255,255,1f);
    private static final ColorRGBA NORMAL_COLOR=new ColorRGBA(0,0,255,1f);
    
    private AssetManager assetManager;
    private Main main;
    
    private Display substanceList;
    
    private Solution solution;
    
    private BitmapText text;
    
    private BitmapFont font;
    
    private Quad quad;
    private Geometry background;
    private Material backgroundMat;
    private ColorRGBA backgroundColor;
    
    private boolean grayedOut;
    
    private Node node;
    
    
    public SubstanceButton(Main main,Display substanceList,Substance substance,Vector3f upperLeftPosition){
        
        this.main=main;
        this.assetManager=main.getAssetManager();
        this.substanceList=substanceList;
        
        solution=new Solution(substance);
        
        node=new Node();
        font=assetManager.loadFont("Interface/Fonts/Hack/Hack.fnt");
        
        text=new BitmapText(font);
        text.setText(substance.getName());
        node.attachChild(text);
        text.setLocalTranslation(0.02f,-0.075f+(text.getLineHeight()/2),0.005f);
        
        createBackground(0.70f,0.15f);
        
        substanceList.getNode().attachChild(node);
        
        node.setLocalTranslation(upperLeftPosition);
        
        substanceButtonList.add(this);
        
    }
    
    private void createBackground(float width,float height){
        
        quad=new Quad(width,height);
        background=new Geometry("Element button background",quad);
        backgroundMat=new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
        backgroundMat.setColor("Color",NORMAL_COLOR);
        background.setMaterial(backgroundMat);
        
        node.attachChild(background);
        
        background.setLocalTranslation(0,-height,0);
        
    }
    
    public void activate(Hand hand){
        
        if(hand.isHoldingObject()&&hand.getHeldObject() instanceof Container){

                if(solution.containsStates()[0]&&((Container)hand.getHeldObject()).canContain(0)){

                    ((Container)hand.getHeldObject()).mergeSolution(solution);

                }else if(solution.containsStates()[1]&&((Container)hand.getHeldObject()).canContain(1)){

                    ((Container)hand.getHeldObject()).mergeSolution(solution);

                }else if(solution.containsStates()[2]&&((Container)hand.getHeldObject()).canContain(2)){

                    ((Container)hand.getHeldObject()).mergeSolution(solution);

                }

            }else if(!hand.isHoldingObject()){

                if(solution.containsStates()[0]){

                    hand.setHeldObject(new GasSac(main,hand.getWorldTranslation(),solution));

                }else if(solution.containsStates()[1]){

                    hand.setHeldObject(new Beaker(main,hand.getWorldTranslation(),solution));

                }else if(solution.containsStates()[2]){

                    hand.setHeldObject(new Beaker(main,hand.getWorldTranslation(),solution));

                }

                ((Grabbable)hand.getHeldObject()).highlightVisible(false);

            }
        
    }

    @Override
    public void write(JmeExporter je) throws IOException {
    }

    @Override
    public void read(JmeImporter ji) throws IOException {
    }
    
}
