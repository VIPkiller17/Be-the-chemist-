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
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import java.io.IOException;
import objects.PhysicalObject;

/**
 *
 * @author VIPkiller17
 */
public class Button implements Savable{
    
    private AssetManager assetManager;
    
    private Display parentDisplay;
    
    private PhysicalObject item;
    
    private BitmapText text;
    
    private BitmapFont font;
    
    private Quad quad;
    private Geometry background;
    private Material backgroundMat;
    
    private Node node;
    
    private boolean pointed;
    
    private static final ColorRGBA POINTED_COLOR=new ColorRGBA(0,255,255,0.7f);
    private static final ColorRGBA NORMAL_COLOR=new ColorRGBA(0,0,255,0.7f);
    
    //Main menu
    public static final int TOGGLE_MODE=0,CREDITS=1,EXIT_GAME=2;
        //Settings TODO
    //Substance list, material list
    public static final int GET_ITEM=3;
        //Filters
    public static final int OPEN_KEYBOARD=4,GAS=5,LIQUID=6,SOLID=7;
            //Keyboard
    public static final int KEY_A=8,KEY_B=9,KEY_C=10,KEY_D=11,KEY_E=12,KEY_F=13,KEY_G=14,KEY_H=15,KEY_I=16,KEY_J=17;
    public static final int KEY_K=18,KEY_L=19,KEY_M=20,KEY_N=21,KEY_O=22,KEY_P=23,KEY_Q=24,KEY_R=25,KEY_S=26,KEY_T=27;
    public static final int KEY_U=28,KEY_V=29,KEY_W=30,KEY_X=31,KEY_Y=32,KEY_Z=33,KEY_1=34,KEY_2=35,KEY_3=36,KEY_4=37;
    public static final int KEY_5=38,KEY_6=39,KEY_7=40,KEY_8=41,KEY_9=42,KEY_BACKSPACE=43;
    
    public Button(AssetManager assetManager,Display parentDisplay,int preset){
        
        this.assetManager=assetManager;
        this.parentDisplay=parentDisplay;
        node=new Node();
        font=assetManager.loadFont("Interface/Fonts/Xolonium/Xolonium.fnt");
        text=new BitmapText(font);
        node.attachChild(text);
        
        setup(preset);
        
    }
    
    public void setup(int preset){
        
        switch(preset){
            
            case 0:
                
                text.setSize(0.08f);
                text.setText("Toggle mode");
                createBackground(0.2f+text.getLineWidth(),0.2f+text.getLineHeight());
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.01f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0,0.25f,0.1f);
                break;
                
                
            case 1:
                
                text.setSize(0.08f);
                text.setText("Credits");
                createBackground(0.2f+text.getLineWidth(),0.2f+text.getLineHeight());
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.01f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0,-0.2f,0.1f);
                break;
                
            case 2:
                
                text.setSize(0.08f);
                text.setText("Exit");
                createBackground(0.2f+text.getLineWidth(),0.2f+text.getLineHeight());
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.01f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0,-0.65f,0.1f);
                break;
                
            case 3:
                
                text.setSize(0.08f);
                text.setText("Get selected item");
                createBackground(0.2f+text.getLineWidth(),0.2f+text.getLineHeight());
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.01f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0,-0.85f,0.1f);
                break;
            
        }
        
        if(background!=null&&text!=null){
        
            node.attachChild(background);
            node.attachChild(text);
            
        }
        
        parentDisplay.getNode().attachChild(node);
        
    }
    
    public void createBackground(float width,float height){
        
        quad=new Quad(width,height);
        background=new Geometry("Button background",quad);
        background.setUserData("correctCollision",true);
        background.setUserData("correspondingObject", this);
        background.setLocalTranslation(-width/2,0,0);
        backgroundMat=new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
        backgroundMat.setColor("Color",new ColorRGBA(0,0,255,0.7f));
        backgroundMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        background.setQueueBucket(RenderQueue.Bucket.Transparent);
        background.setMaterial(backgroundMat);
        
    }
    
    public boolean isPointed(){
        
        return pointed;
        
    }
    
    public void setPointed(boolean pointed){
        
        this.pointed=pointed;
        
        if(pointed){
            
            backgroundMat.setColor("Color", POINTED_COLOR);
            
        }else{
            
            backgroundMat.setColor("Color", NORMAL_COLOR);
            
        }
        
    }

    @Override
    public void write(JmeExporter je) throws IOException {
    }

    @Override
    public void read(JmeImporter ji) throws IOException {
    }
    
}
