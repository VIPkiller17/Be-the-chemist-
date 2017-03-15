/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.world.display;

import static android.R.attr.rotation;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import interfaces.Pointable;
import java.util.ArrayList;

/**
 *
 * @author VIPkiller17
 */
public class Display implements Pointable{
    
    private AssetManager assetManager;
    
    private ArrayList<Button> buttons;
    private ArrayList<BitmapText> texts;
    
    private BitmapFont font;
    
    private Quad quad;
    private Geometry background;
    private Material backgroundMat;
    
    private Node node;
    
    public static final int MAIN_MENU=0,SUBSTANCE_LIST=1,MATERIAL_LIST=2,PERIODIC_TABLE=3,SETTINGS_MENU=4;
    
    public Display(AssetManager assetManager,int preset){
        
        this.assetManager=assetManager;
        
        font=assetManager.loadFont("Interface/Fonts/Xolonium/Xolonium.fnt");
        
        setup(preset);
        
    }
    
    public void addButton(Button button){
        
        buttons.add(button);
        
    }
    
    public void setup(int preset){
        
        //IN HERE SET THE POSITION AND ROTATION OF THE DISPLAY WITH THE NODE CONTAINING THE BUTTONS AND TEXTS
        switch(preset){
            
            case 0:
                createBackground(2,1);
                buttons.add(new Button(assetManager,this,0));
                buttons.add(new Button(assetManager,this,1));
                buttons.add(new Button(assetManager,this,2));
                background.setLocalTranslation(new Vector3f(-0.81f,0.75f,2));
                //background.setLocalRotation(rotation);
            
        }
        
    }
    
    public void createBackground(float width,float height){
        
        quad=new Quad(width,height);
        background=new Geometry("Display background",quad);
        backgroundMat=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        backgroundMat.setColor("Color",new ColorRGBA(1,1,1,0.5f));
        backgroundMat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        background.setQueueBucket(Bucket.Translucent);
        background.setMaterial(backgroundMat);
        
        node.attachChild(background);
        
    }
    
}
