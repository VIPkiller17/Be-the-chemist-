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
    
    public PeriodicTableDisplay(Main main){
        
        this.main=main;
        this.assetManager=main.getAssetManager();
        
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
        frame.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        node.attachChild(frame);
        frame.setLocalTranslation(2.355f,1.325f,0);
        
        createBackground(4.71f,2.65f);
        
        texts.add(new BitmapText(font));
        
        texts.get(texts.size()-1).setSize(0.08f);
        texts.get(texts.size()-1).setText("Periodic table");
        texts.get(texts.size()-1).setLocalTranslation(-(texts.get(texts.size()-1).getLineWidth()/2),0.9f+(texts.get(texts.size()-1).getLineHeight()/2),0.01f);
        texts.get(texts.size()-1).setQueueBucket(RenderQueue.Bucket.Translucent);
        
        addButtons();
        
        main.getRootNode().attachChild(node);
        
        node.setLocalTranslation(new Vector3f(-0.95f,0.21f,-5.95f));
        
    }
    
    private void addButtons(){
        
        //the difference in position of the buttons compared to eachother is 0.26m
        //buttons.add(new ElementButton(assetManager,this,"MASS",atomicNumber,"SYMBOL","NAME",new Vector3f((-quad.getWidth()/2)+0.01f,(-quad.getHeight()/2)-0.01f,0.05f),new Solution(new Substance("EQUATION","NAME",quantity,"STATESTRING",stateInteger,temperature,meltingPoint,boilingPoint,sublimationPoint,"TYPE",molarMass,desnity)),ColorRGBA.COLOR));
        
        //row 1
        buttons.add(new ElementButton(main,this,main.getElements().get(0).getMolarMass()+"",main.getElements().get(0).getNumber(),main.getElements().get(0).getSymbol(),main.getElements().get(0).getName(),new Vector3f(0.02f,quad.getHeight()-0.02f,0.05f),main.getSubstances().get(54),ColorRGBA.Green));
        buttons.add(new ElementButton(main,this,main.getElements().get(1).getMolarMass()+"",main.getElements().get(1).getNumber(),main.getElements().get(1).getSymbol(),main.getElements().get(1).getName(),new Vector3f(4.44f,quad.getHeight()-0.02f,0.05f)));//blue
        
        //row 2
        buttons.add(new ElementButton(main,this,main.getElements().get(2).getMolarMass()+"",main.getElements().get(2).getNumber(),main.getElements().get(2).getSymbol(),main.getElements().get(2).getName(),new Vector3f(0.02f,quad.getHeight()-0.28f,0.05f),main.getSubstances().get(46),ColorRGBA.Orange));
        buttons.add(new ElementButton(main,this,main.getElements().get(3).getMolarMass()+"",main.getElements().get(3).getNumber(),main.getElements().get(3).getSymbol(),main.getElements().get(3).getName(),new Vector3f(0.28f,quad.getHeight()-0.28f,0.05f)));//yellow
        buttons.add(new ElementButton(main,this,main.getElements().get(4).getMolarMass()+"",main.getElements().get(4).getNumber(),main.getElements().get(4).getSymbol(),main.getElements().get(4).getName(),new Vector3f(3.14f,quad.getHeight()-0.28f,0.05f)));//red
        buttons.add(new ElementButton(main,this,main.getElements().get(5).getMolarMass()+"",main.getElements().get(5).getNumber(),main.getElements().get(5).getSymbol(),main.getElements().get(5).getName(),new Vector3f(3.4f,quad.getHeight()-0.28f,0.05f)));//green
        buttons.add(new ElementButton(main,this,main.getElements().get(6).getMolarMass()+"",main.getElements().get(6).getNumber(),main.getElements().get(6).getSymbol(),main.getElements().get(6).getName(),new Vector3f(3.66f,quad.getHeight()-0.28f,0.05f)));//green
        buttons.add(new ElementButton(main,this,main.getElements().get(7).getMolarMass()+"",main.getElements().get(7).getNumber(),main.getElements().get(7).getSymbol(),main.getElements().get(7).getName(),new Vector3f(3.92f,quad.getHeight()-0.28f,0.05f),main.getSubstances().get(55),ColorRGBA.Green));
        buttons.add(new ElementButton(main,this,main.getElements().get(8).getMolarMass()+"",main.getElements().get(8).getNumber(),main.getElements().get(8).getSymbol(),main.getElements().get(8).getName(),new Vector3f(4.18f,quad.getHeight()-0.28f,0.05f),main.getSubstances().get(0),ColorRGBA.Cyan));
        buttons.add(new ElementButton(main,this,main.getElements().get(9).getMolarMass()+"",main.getElements().get(9).getNumber(),main.getElements().get(9).getSymbol(),main.getElements().get(9).getName(),new Vector3f(4.44f,quad.getHeight()-0.28f,0.05f)));//Blue
        
        //row 3
        buttons.add(new ElementButton(main,this,main.getElements().get(10).getMolarMass()+"",main.getElements().get(10).getNumber(),main.getElements().get(10).getSymbol(),main.getElements().get(10).getName(),new Vector3f(0.02f,quad.getHeight()-0.54f,0.05f),main.getSubstances().get(47),ColorRGBA.Orange));
        buttons.add(new ElementButton(main,this,main.getElements().get(11).getMolarMass()+"",main.getElements().get(11).getNumber(),main.getElements().get(11).getSymbol(),main.getElements().get(11).getName(),new Vector3f(0.28f,quad.getHeight()-0.54f,0.05f),main.getSubstances().get(49),ColorRGBA.Yellow));
        buttons.add(new ElementButton(main,this,main.getElements().get(12).getMolarMass()+"",main.getElements().get(12).getNumber(),main.getElements().get(12).getSymbol(),main.getElements().get(12).getName(),new Vector3f(3.14f,quad.getHeight()-0.54f,0.05f),main.getSubstances().get(50),ColorRGBA.Magenta));
        buttons.add(new ElementButton(main,this,main.getElements().get(13).getMolarMass()+"",main.getElements().get(13).getNumber(),main.getElements().get(13).getSymbol(),main.getElements().get(13).getName(),new Vector3f(3.4f,quad.getHeight()-0.54f,0.05f)));//Red
        buttons.add(new ElementButton(main,this,main.getElements().get(14).getMolarMass()+"",main.getElements().get(14).getNumber(),main.getElements().get(14).getSymbol(),main.getElements().get(14).getName(),new Vector3f(3.66f,quad.getHeight()-0.54f,0.05f)));//Green
        buttons.add(new ElementButton(main,this,main.getElements().get(15).getMolarMass()+"",main.getElements().get(15).getNumber(),main.getElements().get(15).getSymbol(),main.getElements().get(15).getName(),new Vector3f(3.92f,quad.getHeight()-0.54f,0.05f)));//Green
        buttons.add(new ElementButton(main,this,main.getElements().get(16).getMolarMass()+"",main.getElements().get(16).getNumber(),main.getElements().get(16).getSymbol(),main.getElements().get(16).getName(),new Vector3f(4.18f,quad.getHeight()-0.54f,0.05f),main.getSubstances().get(1),ColorRGBA.Cyan));
        buttons.add(new ElementButton(main,this,main.getElements().get(17).getMolarMass()+"",main.getElements().get(17).getNumber(),main.getElements().get(17).getSymbol(),main.getElements().get(17).getName(),new Vector3f(4.44f,quad.getHeight()-0.54f,0.05f)));//Blue
        
        //row 4
        buttons.add(new ElementButton(main,this,main.getElements().get(18).getMolarMass()+"",main.getElements().get(18).getNumber(),main.getElements().get(18).getSymbol(),main.getElements().get(18).getName(),new Vector3f(0.02f,quad.getHeight()-0.8f,0.05f),main.getSubstances().get(48),ColorRGBA.Orange));
        buttons.add(new ElementButton(main,this,main.getElements().get(19).getMolarMass()+"",main.getElements().get(19).getNumber(),main.getElements().get(19).getSymbol(),main.getElements().get(19).getName(),new Vector3f(0.28f,quad.getHeight()-0.8f,0.05f),main.getSubstances().get(51),ColorRGBA.Yellow));
        buttons.add(new ElementButton(main,this,main.getElements().get(20).getMolarMass()+"",main.getElements().get(20).getNumber(),main.getElements().get(20).getSymbol(),main.getElements().get(20).getName(),new Vector3f(0.54f,quad.getHeight()-0.8f,0.05f)));//Brown
        buttons.add(new ElementButton(main,this,main.getElements().get(21).getMolarMass()+"",main.getElements().get(21).getNumber(),main.getElements().get(21).getSymbol(),main.getElements().get(21).getName(),new Vector3f(0.80f,quad.getHeight()-0.8f,0.05f)));//Brown
        buttons.add(new ElementButton(main,this,main.getElements().get(22).getMolarMass()+"",main.getElements().get(22).getNumber(),main.getElements().get(22).getSymbol(),main.getElements().get(22).getName(),new Vector3f(1.06f,quad.getHeight()-0.8f,0.05f)));//Brown
        buttons.add(new ElementButton(main,this,main.getElements().get(23).getMolarMass()+"",main.getElements().get(23).getNumber(),main.getElements().get(23).getSymbol(),main.getElements().get(23).getName(),new Vector3f(1.32f,quad.getHeight()-0.8f,0.05f)));//Brown
        buttons.add(new ElementButton(main,this,main.getElements().get(24).getMolarMass()+"",main.getElements().get(24).getNumber(),main.getElements().get(24).getSymbol(),main.getElements().get(24).getName(),new Vector3f(1.58f,quad.getHeight()-0.8f,0.05f)));//Brown
        buttons.add(new ElementButton(main,this,main.getElements().get(25).getMolarMass()+"",main.getElements().get(25).getNumber(),main.getElements().get(25).getSymbol(),main.getElements().get(25).getName(),new Vector3f(1.84f,quad.getHeight()-0.8f,0.05f),main.getSubstances().get(52),ColorRGBA.Brown));
        buttons.add(new ElementButton(main,this,main.getElements().get(26).getMolarMass()+"",main.getElements().get(26).getNumber(),main.getElements().get(26).getSymbol(),main.getElements().get(26).getName(),new Vector3f(2.1f,quad.getHeight()-0.8f,0.05f)));//Brown
        buttons.add(new ElementButton(main,this,main.getElements().get(27).getMolarMass()+"",main.getElements().get(27).getNumber(),main.getElements().get(27).getSymbol(),main.getElements().get(27).getName(),new Vector3f(2.36f,quad.getHeight()-0.8f,0.05f)));//Brown
        buttons.add(new ElementButton(main,this,main.getElements().get(28).getMolarMass()+"",main.getElements().get(28).getNumber(),main.getElements().get(28).getSymbol(),main.getElements().get(28).getName(),new Vector3f(2.62f,quad.getHeight()-0.8f,0.05f),main.getSubstances().get(53),ColorRGBA.Brown));
        buttons.add(new ElementButton(main,this,main.getElements().get(29).getMolarMass()+"",main.getElements().get(29).getNumber(),main.getElements().get(29).getSymbol(),main.getElements().get(29).getName(),new Vector3f(2.88f,quad.getHeight()-0.8f,0.05f)));//Brown
        buttons.add(new ElementButton(main,this,main.getElements().get(30).getMolarMass()+"",main.getElements().get(30).getNumber(),main.getElements().get(30).getSymbol(),main.getElements().get(30).getName(),new Vector3f(3.14f,quad.getHeight()-0.8f,0.05f)));//Magenta
        buttons.add(new ElementButton(main,this,main.getElements().get(31).getMolarMass()+"",main.getElements().get(31).getNumber(),main.getElements().get(31).getSymbol(),main.getElements().get(31).getName(),new Vector3f(3.4f,quad.getHeight()-0.8f,0.05f)));//Red
        buttons.add(new ElementButton(main,this,main.getElements().get(32).getMolarMass()+"",main.getElements().get(32).getNumber(),main.getElements().get(32).getSymbol(),main.getElements().get(32).getName(),new Vector3f(3.66f,quad.getHeight()-0.8f,0.05f)));//Red
        buttons.add(new ElementButton(main,this,main.getElements().get(33).getMolarMass()+"",main.getElements().get(33).getNumber(),main.getElements().get(33).getSymbol(),main.getElements().get(33).getName(),new Vector3f(3.92f,quad.getHeight()-0.8f,0.05f)));//Green
        buttons.add(new ElementButton(main,this,main.getElements().get(34).getMolarMass()+"",main.getElements().get(34).getNumber(),main.getElements().get(34).getSymbol(),main.getElements().get(34).getName(),new Vector3f(4.18f,quad.getHeight()-0.8f,0.05f),main.getSubstances().get(2),ColorRGBA.Cyan));
        buttons.add(new ElementButton(main,this,main.getElements().get(35).getMolarMass()+"",main.getElements().get(35).getNumber(),main.getElements().get(35).getSymbol(),main.getElements().get(35).getName(),new Vector3f(4.44f,quad.getHeight()-0.8f,0.05f)));//Blue
        
        //row 5
        buttons.add(new ElementButton(main,this,main.getElements().get(36).getMolarMass()+"",main.getElements().get(36).getNumber(),main.getElements().get(36).getSymbol(),main.getElements().get(36).getName(),new Vector3f(0.02f,quad.getHeight()-1.06f,0.05f)));//Orange
        buttons.add(new ElementButton(main,this,main.getElements().get(37).getMolarMass()+"",main.getElements().get(37).getNumber(),main.getElements().get(37).getSymbol(),main.getElements().get(37).getName(),new Vector3f(0.28f,quad.getHeight()-1.06f,0.05f)));//Yellow
        buttons.add(new ElementButton(main,this,main.getElements().get(38).getMolarMass()+"",main.getElements().get(38).getNumber(),main.getElements().get(38).getSymbol(),main.getElements().get(38).getName(),new Vector3f(0.54f,quad.getHeight()-1.06f,0.05f)));//Brown
        buttons.add(new ElementButton(main,this,main.getElements().get(39).getMolarMass()+"",main.getElements().get(39).getNumber(),main.getElements().get(39).getSymbol(),main.getElements().get(39).getName(),new Vector3f(0.80f,quad.getHeight()-1.06f,0.05f)));//Brown
        buttons.add(new ElementButton(main,this,main.getElements().get(40).getMolarMass()+"",main.getElements().get(40).getNumber(),main.getElements().get(40).getSymbol(),main.getElements().get(40).getName(),new Vector3f(1.06f,quad.getHeight()-1.06f,0.05f)));//Brown
        buttons.add(new ElementButton(main,this,main.getElements().get(41).getMolarMass()+"",main.getElements().get(41).getNumber(),main.getElements().get(41).getSymbol(),main.getElements().get(41).getName(),new Vector3f(1.32f,quad.getHeight()-1.06f,0.05f)));//Brown
        buttons.add(new ElementButton(main,this,main.getElements().get(42).getMolarMass()+"",main.getElements().get(42).getNumber(),main.getElements().get(42).getSymbol(),main.getElements().get(42).getName(),new Vector3f(1.58f,quad.getHeight()-1.06f,0.05f)));//Brown
        buttons.add(new ElementButton(main,this,main.getElements().get(43).getMolarMass()+"",main.getElements().get(43).getNumber(),main.getElements().get(43).getSymbol(),main.getElements().get(43).getName(),new Vector3f(1.84f,quad.getHeight()-1.06f,0.05f)));//Brown
        buttons.add(new ElementButton(main,this,main.getElements().get(44).getMolarMass()+"",main.getElements().get(44).getNumber(),main.getElements().get(44).getSymbol(),main.getElements().get(44).getName(),new Vector3f(2.1f,quad.getHeight()-1.06f,0.05f)));//Brown
        buttons.add(new ElementButton(main,this,main.getElements().get(45).getMolarMass()+"",main.getElements().get(45).getNumber(),main.getElements().get(45).getSymbol(),main.getElements().get(45).getName(),new Vector3f(2.36f,quad.getHeight()-1.06f,0.05f)));//Brown
        buttons.add(new ElementButton(main,this,main.getElements().get(46).getMolarMass()+"",main.getElements().get(46).getNumber(),main.getElements().get(46).getSymbol(),main.getElements().get(46).getName(),new Vector3f(2.62f,quad.getHeight()-1.06f,0.05f)));//Brown
        buttons.add(new ElementButton(main,this,main.getElements().get(47).getMolarMass()+"",main.getElements().get(47).getNumber(),main.getElements().get(47).getSymbol(),main.getElements().get(47).getName(),new Vector3f(2.88f,quad.getHeight()-1.06f,0.05f)));//Brown
        buttons.add(new ElementButton(main,this,main.getElements().get(48).getMolarMass()+"",main.getElements().get(48).getNumber(),main.getElements().get(48).getSymbol(),main.getElements().get(48).getName(),new Vector3f(3.14f,quad.getHeight()-1.06f,0.05f)));//Magenta
        buttons.add(new ElementButton(main,this,main.getElements().get(49).getMolarMass()+"",main.getElements().get(49).getNumber(),main.getElements().get(49).getSymbol(),main.getElements().get(49).getName(),new Vector3f(3.4f,quad.getHeight()-1.06f,0.05f)));//Magenta
        buttons.add(new ElementButton(main,this,main.getElements().get(50).getMolarMass()+"",main.getElements().get(50).getNumber(),main.getElements().get(50).getSymbol(),main.getElements().get(50).getName(),new Vector3f(3.66f,quad.getHeight()-1.06f,0.05f)));//Red
        buttons.add(new ElementButton(main,this,main.getElements().get(51).getMolarMass()+"",main.getElements().get(51).getNumber(),main.getElements().get(51).getSymbol(),main.getElements().get(51).getName(),new Vector3f(3.92f,quad.getHeight()-1.06f,0.05f)));//Red
        buttons.add(new ElementButton(main,this,main.getElements().get(52).getMolarMass()+"",main.getElements().get(52).getNumber(),main.getElements().get(52).getSymbol(),main.getElements().get(52).getName(),new Vector3f(4.18f,quad.getHeight()-1.06f,0.05f),main.getSubstances().get(3),ColorRGBA.Cyan));
        buttons.add(new ElementButton(main,this,main.getElements().get(53).getMolarMass()+"",main.getElements().get(53).getNumber(),main.getElements().get(53).getSymbol(),main.getElements().get(53).getName(),new Vector3f(4.44f,quad.getHeight()-1.06f,0.05f)));//Blue
        
        //row 6
        buttons.add(new ElementButton(main,this,main.getElements().get(54).getMolarMass()+"",main.getElements().get(54).getNumber(),main.getElements().get(54).getSymbol(),main.getElements().get(54).getName(),new Vector3f(0.02f,quad.getHeight()-1.32f,0.05f)));//Orange
        buttons.add(new ElementButton(main,this,main.getElements().get(55).getMolarMass()+"",main.getElements().get(55).getNumber(),main.getElements().get(55).getSymbol(),main.getElements().get(55).getName(),new Vector3f(0.28f,quad.getHeight()-1.32f,0.05f)));//Yellow
        buttons.add(new ElementButton(main,this,main.getElements().get(71).getMolarMass()+"",main.getElements().get(71).getNumber(),main.getElements().get(71).getSymbol(),main.getElements().get(71).getName(),new Vector3f(0.8f,quad.getHeight()-1.32f,0.05f)));//Yellow
        buttons.add(new ElementButton(main,this,main.getElements().get(72).getMolarMass()+"",main.getElements().get(72).getNumber(),main.getElements().get(72).getSymbol(),main.getElements().get(72).getName(),new Vector3f(1.06f,quad.getHeight()-1.32f,0.05f)));//Brown
        buttons.add(new ElementButton(main,this,main.getElements().get(73).getMolarMass()+"",main.getElements().get(73).getNumber(),main.getElements().get(73).getSymbol(),main.getElements().get(73).getName(),new Vector3f(1.32f,quad.getHeight()-1.32f,0.05f)));//Brown
        buttons.add(new ElementButton(main,this,main.getElements().get(74).getMolarMass()+"",main.getElements().get(74).getNumber(),main.getElements().get(74).getSymbol(),main.getElements().get(74).getName(),new Vector3f(1.58f,quad.getHeight()-1.32f,0.05f)));//Brown
        buttons.add(new ElementButton(main,this,main.getElements().get(75).getMolarMass()+"",main.getElements().get(75).getNumber(),main.getElements().get(75).getSymbol(),main.getElements().get(75).getName(),new Vector3f(1.84f,quad.getHeight()-1.32f,0.05f)));//Brown
        buttons.add(new ElementButton(main,this,main.getElements().get(76).getMolarMass()+"",main.getElements().get(76).getNumber(),main.getElements().get(76).getSymbol(),main.getElements().get(76).getName(),new Vector3f(2.1f,quad.getHeight()-1.32f,0.05f)));//Brown
        buttons.add(new ElementButton(main,this,main.getElements().get(77).getMolarMass()+"",main.getElements().get(77).getNumber(),main.getElements().get(77).getSymbol(),main.getElements().get(77).getName(),new Vector3f(2.36f,quad.getHeight()-1.32f,0.05f)));//Brown
        buttons.add(new ElementButton(main,this,main.getElements().get(78).getMolarMass()+"",main.getElements().get(78).getNumber(),main.getElements().get(78).getSymbol(),main.getElements().get(78).getName(),new Vector3f(2.62f,quad.getHeight()-1.32f,0.05f)));//Brown
        buttons.add(new ElementButton(main,this,main.getElements().get(79).getMolarMass()+"",main.getElements().get(79).getNumber(),main.getElements().get(79).getSymbol(),main.getElements().get(79).getName(),new Vector3f(2.88f,quad.getHeight()-1.32f,0.05f)));//Brown
        buttons.add(new ElementButton(main,this,main.getElements().get(80).getMolarMass()+"",main.getElements().get(80).getNumber(),main.getElements().get(80).getSymbol(),main.getElements().get(80).getName(),new Vector3f(3.14f,quad.getHeight()-1.32f,0.05f)));//Magenta
        buttons.add(new ElementButton(main,this,main.getElements().get(81).getMolarMass()+"",main.getElements().get(81).getNumber(),main.getElements().get(81).getSymbol(),main.getElements().get(81).getName(),new Vector3f(3.4f,quad.getHeight()-1.32f,0.05f)));//Magenta
        buttons.add(new ElementButton(main,this,main.getElements().get(82).getMolarMass()+"",main.getElements().get(82).getNumber(),main.getElements().get(82).getSymbol(),main.getElements().get(82).getName(),new Vector3f(3.66f,quad.getHeight()-1.32f,0.05f)));//Magenta
        buttons.add(new ElementButton(main,this,main.getElements().get(83).getMolarMass()+"",main.getElements().get(83).getNumber(),main.getElements().get(83).getSymbol(),main.getElements().get(83).getName(),new Vector3f(3.92f,quad.getHeight()-1.32f,0.05f)));//Red
        buttons.add(new ElementButton(main,this,main.getElements().get(84).getMolarMass()+"",main.getElements().get(84).getNumber(),main.getElements().get(84).getSymbol(),main.getElements().get(84).getName(),new Vector3f(4.18f,quad.getHeight()-1.32f,0.05f)));//Cyan
        buttons.add(new ElementButton(main,this,main.getElements().get(85).getMolarMass()+"",main.getElements().get(85).getNumber(),main.getElements().get(85).getSymbol(),main.getElements().get(85).getName(),new Vector3f(4.44f,quad.getHeight()-1.32f,0.05f)));//Blue
        
        //row 7
        buttons.add(new ElementButton(main,this,main.getElements().get(86).getMolarMass()+"",main.getElements().get(86).getNumber(),main.getElements().get(86).getSymbol(),main.getElements().get(86).getName(),new Vector3f(0.02f,quad.getHeight()-1.58f,0.05f)));//Orange
        buttons.add(new ElementButton(main,this,main.getElements().get(87).getMolarMass()+"",main.getElements().get(87).getNumber(),main.getElements().get(87).getSymbol(),main.getElements().get(87).getName(),new Vector3f(0.28f,quad.getHeight()-1.58f,0.05f)));//Yellow
        buttons.add(new ElementButton(main,this,main.getElements().get(103).getMolarMass()+"",main.getElements().get(103).getNumber(),main.getElements().get(103).getSymbol(),main.getElements().get(103).getName(),new Vector3f(0.80f,quad.getHeight()-1.58f,0.05f)));//Brown
        buttons.add(new ElementButton(main,this,main.getElements().get(104).getMolarMass()+"",main.getElements().get(104).getNumber(),main.getElements().get(104).getSymbol(),main.getElements().get(104).getName(),new Vector3f(1.06f,quad.getHeight()-1.58f,0.05f)));//Brown
        buttons.add(new ElementButton(main,this,main.getElements().get(105).getMolarMass()+"",main.getElements().get(105).getNumber(),main.getElements().get(105).getSymbol(),main.getElements().get(105).getName(),new Vector3f(1.32f,quad.getHeight()-1.58f,0.05f)));//Brown
        buttons.add(new ElementButton(main,this,main.getElements().get(106).getMolarMass()+"",main.getElements().get(106).getNumber(),main.getElements().get(106).getSymbol(),main.getElements().get(106).getName(),new Vector3f(1.58f,quad.getHeight()-1.58f,0.05f)));//Brown
        buttons.add(new ElementButton(main,this,main.getElements().get(107).getMolarMass()+"",main.getElements().get(107).getNumber(),main.getElements().get(107).getSymbol(),main.getElements().get(107).getName(),new Vector3f(1.84f,quad.getHeight()-1.58f,0.05f)));//Brown
        buttons.add(new ElementButton(main,this,main.getElements().get(108).getMolarMass()+"",main.getElements().get(108).getNumber(),main.getElements().get(108).getSymbol(),main.getElements().get(108).getName(),new Vector3f(2.1f,quad.getHeight()-1.58f,0.05f)));//Brown
        buttons.add(new ElementButton(main,this,main.getElements().get(109).getMolarMass()+"",main.getElements().get(109).getNumber(),main.getElements().get(109).getSymbol(),main.getElements().get(109).getName(),new Vector3f(2.36f,quad.getHeight()-1.58f,0.05f)));//Brown
        buttons.add(new ElementButton(main,this,main.getElements().get(110).getMolarMass()+"",main.getElements().get(110).getNumber(),main.getElements().get(110).getSymbol(),main.getElements().get(110).getName(),new Vector3f(2.62f,quad.getHeight()-1.58f,0.05f)));//Brown
        buttons.add(new ElementButton(main,this,main.getElements().get(111).getMolarMass()+"",main.getElements().get(111).getNumber(),main.getElements().get(111).getSymbol(),main.getElements().get(111).getName(),new Vector3f(2.88f,quad.getHeight()-1.58f,0.05f)));//Brown
        buttons.add(new ElementButton(main,this,main.getElements().get(112).getMolarMass()+"",main.getElements().get(112).getNumber(),main.getElements().get(112).getSymbol(),main.getElements().get(112).getName(),new Vector3f(3.14f,quad.getHeight()-1.58f,0.05f)));//Magenta
        buttons.add(new ElementButton(main,this,main.getElements().get(113).getMolarMass()+"",main.getElements().get(113).getNumber(),main.getElements().get(113).getSymbol(),main.getElements().get(113).getName(),new Vector3f(3.4f,quad.getHeight()-1.58f,0.05f)));//Magenta
        buttons.add(new ElementButton(main,this,main.getElements().get(114).getMolarMass()+"",main.getElements().get(114).getNumber(),main.getElements().get(114).getSymbol(),main.getElements().get(114).getName(),new Vector3f(3.66f,quad.getHeight()-1.58f,0.05f)));//Magenta
        buttons.add(new ElementButton(main,this,main.getElements().get(115).getMolarMass()+"",main.getElements().get(115).getNumber(),main.getElements().get(115).getSymbol(),main.getElements().get(115).getName(),new Vector3f(3.92f,quad.getHeight()-1.58f,0.05f)));//Magenta
        buttons.add(new ElementButton(main,this,main.getElements().get(116).getMolarMass()+"",main.getElements().get(116).getNumber(),main.getElements().get(116).getSymbol(),main.getElements().get(116).getName(),new Vector3f(4.18f,quad.getHeight()-1.58f,0.05f)));//Cyan
        buttons.add(new ElementButton(main,this,main.getElements().get(117).getMolarMass()+"",main.getElements().get(117).getNumber(),main.getElements().get(117).getSymbol(),main.getElements().get(117).getName(),new Vector3f(4.44f,quad.getHeight()-1.58f,0.05f)));//Blue
        
        //row 8
        buttons.add(new ElementButton(main,this,main.getElements().get(56).getMolarMass()+"",main.getElements().get(56).getNumber(),main.getElements().get(56).getSymbol(),main.getElements().get(56).getName(),new Vector3f(0.80f,quad.getHeight()-2.12f,0.05f)));//new ColorRGBA(127,63,191,1)
        buttons.add(new ElementButton(main,this,main.getElements().get(57).getMolarMass()+"",main.getElements().get(57).getNumber(),main.getElements().get(57).getSymbol(),main.getElements().get(57).getName(),new Vector3f(1.06f,quad.getHeight()-2.12f,0.05f)));//
        buttons.add(new ElementButton(main,this,main.getElements().get(58).getMolarMass()+"",main.getElements().get(58).getNumber(),main.getElements().get(58).getSymbol(),main.getElements().get(58).getName(),new Vector3f(1.32f,quad.getHeight()-2.12f,0.05f)));//
        buttons.add(new ElementButton(main,this,main.getElements().get(59).getMolarMass()+"",main.getElements().get(59).getNumber(),main.getElements().get(59).getSymbol(),main.getElements().get(59).getName(),new Vector3f(1.58f,quad.getHeight()-2.12f,0.05f)));//
        buttons.add(new ElementButton(main,this,main.getElements().get(60).getMolarMass()+"",main.getElements().get(60).getNumber(),main.getElements().get(60).getSymbol(),main.getElements().get(60).getName(),new Vector3f(1.84f,quad.getHeight()-2.12f,0.05f)));//
        buttons.add(new ElementButton(main,this,main.getElements().get(61).getMolarMass()+"",main.getElements().get(61).getNumber(),main.getElements().get(61).getSymbol(),main.getElements().get(61).getName(),new Vector3f(2.1f,quad.getHeight()-2.12f,0.05f)));//
        buttons.add(new ElementButton(main,this,main.getElements().get(62).getMolarMass()+"",main.getElements().get(62).getNumber(),main.getElements().get(62).getSymbol(),main.getElements().get(62).getName(),new Vector3f(2.36f,quad.getHeight()-2.12f,0.05f)));//
        buttons.add(new ElementButton(main,this,main.getElements().get(63).getMolarMass()+"",main.getElements().get(63).getNumber(),main.getElements().get(63).getSymbol(),main.getElements().get(63).getName(),new Vector3f(2.62f,quad.getHeight()-2.12f,0.05f)));//
        buttons.add(new ElementButton(main,this,main.getElements().get(64).getMolarMass()+"",main.getElements().get(64).getNumber(),main.getElements().get(64).getSymbol(),main.getElements().get(64).getName(),new Vector3f(2.88f,quad.getHeight()-2.12f,0.05f)));//
        buttons.add(new ElementButton(main,this,main.getElements().get(65).getMolarMass()+"",main.getElements().get(65).getNumber(),main.getElements().get(65).getSymbol(),main.getElements().get(65).getName(),new Vector3f(3.14f,quad.getHeight()-2.12f,0.05f)));//
        buttons.add(new ElementButton(main,this,main.getElements().get(66).getMolarMass()+"",main.getElements().get(66).getNumber(),main.getElements().get(66).getSymbol(),main.getElements().get(66).getName(),new Vector3f(3.4f,quad.getHeight()-2.12f,0.05f)));//
        buttons.add(new ElementButton(main,this,main.getElements().get(67).getMolarMass()+"",main.getElements().get(67).getNumber(),main.getElements().get(67).getSymbol(),main.getElements().get(67).getName(),new Vector3f(3.66f,quad.getHeight()-2.12f,0.05f)));//
        buttons.add(new ElementButton(main,this,main.getElements().get(68).getMolarMass()+"",main.getElements().get(68).getNumber(),main.getElements().get(68).getSymbol(),main.getElements().get(68).getName(),new Vector3f(3.92f,quad.getHeight()-2.12f,0.05f)));//
        buttons.add(new ElementButton(main,this,main.getElements().get(69).getMolarMass()+"",main.getElements().get(69).getNumber(),main.getElements().get(69).getSymbol(),main.getElements().get(69).getName(),new Vector3f(4.18f,quad.getHeight()-2.12f,0.05f)));//
        buttons.add(new ElementButton(main,this,main.getElements().get(70).getMolarMass()+"",main.getElements().get(70).getNumber(),main.getElements().get(70).getSymbol(),main.getElements().get(70).getName(),new Vector3f(4.44f,quad.getHeight()-2.12f,0.05f)));//
        
        //row 9
        buttons.add(new ElementButton(main,this,main.getElements().get(88).getMolarMass()+"",main.getElements().get(88).getNumber(),main.getElements().get(88).getSymbol(),main.getElements().get(88).getName(),new Vector3f(0.80f,quad.getHeight()-2.38f,0.05f)));//Pink
        buttons.add(new ElementButton(main,this,main.getElements().get(89).getMolarMass()+"",main.getElements().get(89).getNumber(),main.getElements().get(89).getSymbol(),main.getElements().get(89).getName(),new Vector3f(1.06f,quad.getHeight()-2.38f,0.05f)));//
        buttons.add(new ElementButton(main,this,main.getElements().get(90).getMolarMass()+"",main.getElements().get(90).getNumber(),main.getElements().get(90).getSymbol(),main.getElements().get(90).getName(),new Vector3f(1.32f,quad.getHeight()-2.38f,0.05f)));//
        buttons.add(new ElementButton(main,this,main.getElements().get(91).getMolarMass()+"",main.getElements().get(91).getNumber(),main.getElements().get(91).getSymbol(),main.getElements().get(91).getName(),new Vector3f(1.58f,quad.getHeight()-2.38f,0.05f)));//
        buttons.add(new ElementButton(main,this,main.getElements().get(92).getMolarMass()+"",main.getElements().get(92).getNumber(),main.getElements().get(92).getSymbol(),main.getElements().get(92).getName(),new Vector3f(1.84f,quad.getHeight()-2.38f,0.05f)));//
        buttons.add(new ElementButton(main,this,main.getElements().get(93).getMolarMass()+"",main.getElements().get(93).getNumber(),main.getElements().get(93).getSymbol(),main.getElements().get(93).getName(),new Vector3f(2.1f,quad.getHeight()-2.38f,0.05f)));//
        buttons.add(new ElementButton(main,this,main.getElements().get(94).getMolarMass()+"",main.getElements().get(94).getNumber(),main.getElements().get(94).getSymbol(),main.getElements().get(94).getName(),new Vector3f(2.36f,quad.getHeight()-2.38f,0.05f)));//
        buttons.add(new ElementButton(main,this,main.getElements().get(95).getMolarMass()+"",main.getElements().get(95).getNumber(),main.getElements().get(95).getSymbol(),main.getElements().get(95).getName(),new Vector3f(2.62f,quad.getHeight()-2.38f,0.05f)));//
        buttons.add(new ElementButton(main,this,main.getElements().get(96).getMolarMass()+"",main.getElements().get(96).getNumber(),main.getElements().get(96).getSymbol(),main.getElements().get(96).getName(),new Vector3f(2.88f,quad.getHeight()-2.38f,0.05f)));//
        buttons.add(new ElementButton(main,this,main.getElements().get(97).getMolarMass()+"",main.getElements().get(97).getNumber(),main.getElements().get(97).getSymbol(),main.getElements().get(97).getName(),new Vector3f(3.14f,quad.getHeight()-2.38f,0.05f)));//
        buttons.add(new ElementButton(main,this,main.getElements().get(98).getMolarMass()+"",main.getElements().get(98).getNumber(),main.getElements().get(98).getSymbol(),main.getElements().get(98).getName(),new Vector3f(3.4f,quad.getHeight()-2.38f,0.05f)));//
        buttons.add(new ElementButton(main,this,main.getElements().get(99).getMolarMass()+"",main.getElements().get(99).getNumber(),main.getElements().get(99).getSymbol(),main.getElements().get(99).getName(),new Vector3f(3.66f,quad.getHeight()-2.38f,0.05f)));//
        buttons.add(new ElementButton(main,this,main.getElements().get(100).getMolarMass()+"",main.getElements().get(100).getNumber(),main.getElements().get(100).getSymbol(),main.getElements().get(100).getName(),new Vector3f(3.92f,quad.getHeight()-2.38f,0.05f)));//
        buttons.add(new ElementButton(main,this,main.getElements().get(101).getMolarMass()+"",main.getElements().get(101).getNumber(),main.getElements().get(101).getSymbol(),main.getElements().get(101).getName(),new Vector3f(4.18f,quad.getHeight()-2.38f,0.05f)));//
        buttons.add(new ElementButton(main,this,main.getElements().get(102).getMolarMass()+"",main.getElements().get(102).getNumber(),main.getElements().get(102).getSymbol(),main.getElements().get(102).getName(),new Vector3f(4.44f,quad.getHeight()-2.38f,0.05f)));//
        
        
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
        
    @Override
    public boolean equals(Object otherPeriodicTableDisplay){
        
        return otherPeriodicTableDisplay instanceof PeriodicTableDisplay;
        
    }
    
    @Override
    public String toString(){
        
        return "The periodic table";
        
    }
    
}
