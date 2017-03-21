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
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import interfaces.Pointable;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author VIPkiller17
 */
public class Display implements Pointable,Savable{
    
    private AssetManager assetManager;
    
    private ArrayList<Button> buttons;
    private ArrayList<BitmapText> texts;
    
    private BitmapFont font;
    
    private Quad quad;
    private Geometry background;
    private Material backgroundMat;
    
    private Node node;
    private Node rootNode;
    
    public static final int MAIN_MENU=0,SUBSTANCE_LIST=1,KEYBOARD=2,MATERIAL_LIST=3,PERIODIC_TABLE=4,SETTINGS_MENU=5;
    
    public Display(AssetManager assetManager,Node rootNode,int preset){
        
        this.assetManager=assetManager;
        this.rootNode=rootNode;
        
        font=assetManager.loadFont("Interface/Fonts/Xolonium/Xolonium.fnt");
        
        texts=new ArrayList<BitmapText>();
        buttons=new ArrayList<Button>();
        
        node=new Node();
        
        setup(preset);
        
    }
    
    public void addButton(Button button){
        
        buttons.add(button);
        
    }
    
    public void setup(int preset){
        
        texts.add(new BitmapText(font));
        texts.get(texts.size()-1).setQueueBucket(Bucket.Transparent);
        node.attachChild(texts.get(texts.size()-1));
        
        //IN HERE SET THE POSITION AND ROTATION OF THE DISPLAY WITH THE NODE CONTAINING THE BUTTONS AND TEXTS
        switch(preset){
            
            case 0://Main menu
                
                createBackground(1,2);
                texts.get(texts.size()-1).setSize(0.08f);
                texts.get(texts.size()-1).setText("Main menu");
                texts.get(texts.size()-1).setLocalTranslation(-(texts.get(texts.size()-1).getLineWidth()/2),0.9f+(texts.get(texts.size()-1).getLineHeight()/2),0.01f);
                buttons.add(new Button(assetManager,this,0));
                buttons.add(new Button(assetManager,this,1));
                buttons.add(new Button(assetManager,this,2));
                node.setLocalTranslation(new Vector3f(-1.1f,1.2f,0.5f));
                node.setLocalRotation(new Quaternion().fromAngleAxis(FastMath.PI/2,Vector3f.UNIT_Y));
                break;
                
            case 1://Substance list
                
                createBackground(1,2);
                
                Quad textFieldQuad=new Quad(0.80f,0.15f);
                Geometry textField=new Geometry("Substance list text field",textFieldQuad);
                Material textFieldMat=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                textFieldMat.setColor("Color",ColorRGBA.BlackNoAlpha);
                textField.setMaterial(textFieldMat);
                node.attachChild(textField);
                textField.setLocalTranslation(-0.40f,0.75f,0.05f);
                
                Quad listBackgroundQuad=new Quad(0.8f,1.2f);
                Geometry listBackground=new Geometry("Substance list background",listBackgroundQuad);
                Material listBackgroundMat=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                listBackgroundMat.setColor("Color",ColorRGBA.Gray);
                listBackground.setMaterial(listBackgroundMat);
                node.attachChild(listBackground);
                listBackground.setLocalTranslation(-0.40f,-0.5f,0.03f);
                
                texts.get(texts.size()-1).setSize(0.08f);
                texts.get(texts.size()-1).setText("Substance list");
                texts.get(texts.size()-1).setLocalTranslation(-(texts.get(texts.size()-1).getLineWidth()/2),0.9f+(texts.get(texts.size()-1).getLineHeight()/2),0.01f);
                buttons.add(new Button(assetManager,this,3));
                node.setLocalTranslation(new Vector3f(-1.1f,1.2f,2f));
                node.setLocalRotation(new Quaternion().fromAngleAxis(FastMath.PI/2,Vector3f.UNIT_Y));
                break;
                
            case 2://Keyboard
                
                
                createBackground(1.13f,0.58f);
                for(int i=8;i<51;i++){
                    
                    if(i!=45)
                    
                        buttons.add(new Button(assetManager,this,i));
                    
                }
                node.setLocalTranslation(new Vector3f(-1.1f,1.2f,3.1f));
                node.setLocalRotation(new Quaternion().fromAngleAxis(FastMath.PI/2,Vector3f.UNIT_Y));
                break;
            
        }
        
        rootNode.attachChild(node);
        
    }
    
    public void createBackground(float width,float height){
        
        quad=new Quad(width,height);
        background=new Geometry("Display background",quad);
        background.setUserData("correctCollision",true);
        background.setUserData("correspondingObject", this);
        backgroundMat=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        backgroundMat.setColor("Color",new ColorRGBA(0,255,0,0.5f));
        backgroundMat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        background.setQueueBucket(Bucket.Translucent);
        background.setMaterial(backgroundMat);
        
        node.attachChild(background);
        
        background.setLocalTranslation(-quad.getWidth()/2,-quad.getHeight()/2,0f);
        
    }
    
    public Node getNode(){
        
        return node;
        
    }
    
    public void addSubstance(){
        
        
        
    }

    @Override
    public void write(JmeExporter je) throws IOException {
    }

    @Override
    public void read(JmeImporter ji) throws IOException {
    }
            
    
}
