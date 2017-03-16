/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.world;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import control.DescriptionControl;
import java.util.ArrayList;
import java.util.Arrays;
import jmevr.input.VRAPI;
//by Tommy
public class DescDisplay {
    
    public static Spatial observer;
    
    private BitmapFont font;
    private BitmapText description;
    
    private Node centeredNode;
    
    private String rawText;
    private ArrayList<String> lines;
    private String displayedText="";
    
    private DescriptionControl facingControl;
    
    public DescDisplay(AssetManager assetManager,Node rootNode,VRAPI VRHardware,int controllerIndex,String text,Spatial newObserver){
        
        observer=newObserver;
        
        //init the font of the text
        font=new BitmapFont();
        font=assetManager.loadFont("Interface/Fonts/Xolonium/Xolonium.fnt");
        
        //init the control for the description to always face the player
        facingControl=new DescriptionControl(VRHardware,controllerIndex,observer);
        
        //init the description text
        description=new BitmapText(font,false);
        
        //set the initial size of the etxt to 0.05 times its orginal
        description.setSize(0.035f);
        
        //make the node of the description
        centeredNode=new Node();
        
        //attach the description text to the description node
        centeredNode.attachChild(description);
        
        //add the control to the node for ti to always face the player
        centeredNode.addControl(facingControl);
        
        //init the raw text
        this.rawText=text;
        
        //process the raw text
        processText();
        
        //make a test cube marking the center of the node, its rotation axis
        /*
        Material mat = new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        Box centerMark = new Box(0.005f,0.005f,0.005f);
        Geometry geom = new Geometry("CenterMark",centerMark);
        geom.setMaterial(mat);
        
        //attach the text cube ot the node
        centeredNode.attachChild(geom);
        */
        
        //attach the description to the rootNode, making it visible
        //rootNode.attachChild(centeredNode);
        //THIS IS NOW BEING DONE IN HAND
        
    }
    
    public DescDisplay(){
        
        
        
    }
    
    public void processText(){
        
        displayedText="";
        
        String[] words=rawText.split(" ");
        
        lines=new ArrayList<String>(Arrays.asList(words));
        lines.remove(null);
        
        for(int i=0;i<lines.size();i++){
            
            if(lines.get(i)!=null&&(i+1)<lines.size()&&lines.get(i+1)!=null){
                
                System.out.println("Adding \""+lines.get(i)+"\" to displayed tex");
                
                displayedText+=lines.get(i)+" ";
            
            }else if(lines.get(i)!=null){
                
                System.out.println("Adding \""+lines.get(i)+"\" to displayed tex");
                
                displayedText+=lines.get(i);
                
            }
            
        }
        
        description.setText(displayedText);
        
        description.setLocalTranslation(-1*description.getLineWidth()/2,description.getHeight(),0);
        
    }
    
    public void setText(String text){
        
        if(!text.equals(rawText)){
        
            rawText=text;
        
            if(!text.equals(""))
            
                processText();
            
        }
        
    }
    
    public void setMoveOut(boolean moveOut){
        
        facingControl.moveOut(moveOut);
        
    }
    
    public Node getDescriptionNode(){
        
        return centeredNode;
        
    }
    
}
