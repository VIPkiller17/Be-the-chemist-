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
    
    private boolean isToggle;
    private boolean toggleActivated;
    
    private static final ColorRGBA POINTED_COLOR=new ColorRGBA(0,255,255,0.7f);
    private static final ColorRGBA NORMAL_COLOR=new ColorRGBA(0,0,255,0.7f);
    
    //Main menu
    public static final int TOGGLE_MODE=0,CREDITS=1,EXIT_GAME=2;
        //Settings TODO
    //Substance list, material list
    public static final int GET_SUBSTANCE=3,GET_MATERIAL=53;
        //Filters
    public static final int GAS=5,LIQUID=6,SOLID=7,SUBSTANCE_PAGE_UP=45,SUBSTANCE_PAGE_DOWN=54,MATERIAL_PAGE_UP=55,MATERIAL_PAGE_DOWN=56;
    public static final int TYPE=57,CLASS=58;
            //Keyboard
    public static final int KEY_Q=8,KEY_W=9,KEY_E=10,KEY_R=11,KEY_T=12,KEY_Y=13,KEY_U=14,KEY_I=15,KEY_O=16,KEY_P=17;
    public static final int KEY_A=18,KEY_S=19,KEY_D=20,KEY_F=21,KEY_G=22,KEY_H=23,KEY_J=24,KEY_K=25,KEY_L=26,KEY_Z=27;
    public static final int KEY_X=28,KEY_C=29,KEY_V=30,KEY_B=31,KEY_N=32,KEY_M=33,KEY_1=34,KEY_2=35,KEY_3=36,KEY_4=37;
    public static final int KEY_5=38,KEY_6=39,KEY_7=40,KEY_8=41,KEY_9=42,KEY_0=43,KEY_BACKSPACE=44,KEY_SPACE=46,KEY_DASH=47;
    public static final int KEY_OPEN_PARENTHESES=48,KEY_CLOSE_PARENTHESES=49,KEY_CAPS=50,KEY_COMMA=51,KEY_PERIOD=52;
    
    
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
                node.setLocalTranslation(0,0.25f,0.05f);
                break;
                
                
            case 1:
                
                text.setSize(0.08f);
                text.setText("Credits");
                createBackground(0.2f+text.getLineWidth(),0.2f+text.getLineHeight());
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.01f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0,-0.2f,0.05f);
                break;
                
            case 2:
                
                text.setSize(0.08f);
                text.setText("Exit");
                createBackground(0.2f+text.getLineWidth(),0.2f+text.getLineHeight());
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.01f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0,-0.65f,0.05f);
                break;
                
            case 3:
                
                text.setSize(0.08f);
                text.setText("Get selected item");
                createBackground(0.2f+text.getLineWidth(),0.2f+text.getLineHeight());
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.01f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0,-0.85f,0.05f);
                break;
                
            case 4:
                
            case 5:
                
                isToggle=true;
                text.setSize(0.08f);
                text.setText("Gases");
                createBackground(0.3f,0.02f+text.getLineHeight());
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.01f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation((parentDisplay.getWidthDimension()/2)-(quad.getWidth()/2)-0.05f,((parentDisplay.getHeightDimension()/2)-quad.getHeight())-0.06f,0.05f);
                break;
                
            case 6:
                
                isToggle=true;
                text.setSize(0.08f);
                text.setText("Liquid");
                createBackground(0.3f,0.02f+text.getLineHeight());
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.01f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation((parentDisplay.getWidthDimension()/2)-(quad.getWidth()/2)-0.05f,((parentDisplay.getHeightDimension()/2)-quad.getHeight())-0.19f,0.05f);
                break;
                
            case 7:
                
                isToggle=true;
                text.setSize(0.08f);
                text.setText("Solid");
                createBackground(0.3f,0.02f+text.getLineHeight());
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.01f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation((parentDisplay.getWidthDimension()/2)-(quad.getWidth()/2)-0.05f,((parentDisplay.getHeightDimension()/2)-quad.getHeight())-0.32f,0.05f);
                break;
                
            case 8:
                
                text.setSize(0.08f);
                text.setText("Q");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.495f,0.03f,0.01f);
                break;
                
            case 9:
                
                text.setSize(0.08f);
                text.setText("W");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.385f,0.03f,0.01f);
                break;
                
            case 10:
                
                text.setSize(0.08f);
                text.setText("E");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.275f,0.03f,0.01f);
                break;
                
            case 11:
                
                text.setSize(0.08f);
                text.setText("R");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.165f,0.03f,0.01f);
                break;
                
            case 12:
                
                text.setSize(0.08f);
                text.setText("T");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.055f,0.03f,0.01f);
                break;
                
            case 13:
                
                text.setSize(0.08f);
                text.setText("Y");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.055f,0.03f,0.01f);
                break;
                
            case 14:
                
                text.setSize(0.08f);
                text.setText("U");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.165f,0.03f,0.01f);
                break;
                
            case 15:
                
                text.setSize(0.08f);
                text.setText("I");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.275f,0.03f,0.01f);
                break;
                
            case 16:
                
                text.setSize(0.08f);
                text.setText("O");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.385f,0.03f,0.01f);
                break;
                
            case 17:
                
                text.setSize(0.08f);
                text.setText("P");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.495f,0.03f,0.01f);
                break;
                
            case 18:
                
                text.setSize(0.08f);
                text.setText("A");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.44f,-0.08f,0.01f);
                break;
                
            case 19:
                
                text.setSize(0.08f);
                text.setText("S");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.33f,-0.08f,0.01f);
                break;
                
            case 20:
                
                text.setSize(0.08f);
                text.setText("D");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.22f,-0.08f,0.01f);
                break;
                
            case 21:
                
                text.setSize(0.08f);
                text.setText("F");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.11f,-0.08f,0.01f);
                break;
                
            case 22:
                
                text.setSize(0.08f);
                text.setText("G");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0f,-0.08f,0.01f);
                break;
                
            case 23:
                
                text.setSize(0.08f);
                text.setText("H");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.11f,-0.08f,0.01f);
                break;
                
            case 24:
                
                text.setSize(0.08f);
                text.setText("J");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.22f,-0.08f,0.01f);
                break;
                
            case 25:
                
                text.setSize(0.08f);
                text.setText("K");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.33f,-0.08f,0.01f);
                break;
                
            case 26:
                
                text.setSize(0.08f);
                text.setText("L");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.44f,-0.08f,0.01f);
                break;
                
            case 27:
                
                text.setSize(0.08f);
                text.setText("Z");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.385f,-0.19f,0.01f);
                break;
                
            case 28:
                
                text.setSize(0.08f);
                text.setText("X");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.275f,-0.19f,0.01f);
                break;
                
            case 29:
                
                text.setSize(0.08f);
                text.setText("C");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.165f,-0.19f,0.01f);
                break;
                
            case 30:
                
                text.setSize(0.08f);
                text.setText("V");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.055f,-0.19f,0.01f);
                break;
                
            case 31:
                
                text.setSize(0.08f);
                text.setText("B");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.055f,-0.19f,0.01f);
                break;
                
            case 32:
                
                text.setSize(0.08f);
                text.setText("N");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.165f,-0.19f,0.01f);
                break;
                
            case 33:
                
                text.setSize(0.08f);
                text.setText("M");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.275f,-0.19f,0.01f);
                break;
                
            case 34:
                
                text.setSize(0.08f);
                text.setText("1");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.495f,0.14f,0.01f);
                break;
                
            case 35:
                
                text.setSize(0.08f);
                text.setText("2");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.385f,0.14f,0.01f);
                break;
                
            case 36:
                
                text.setSize(0.08f);
                text.setText("3");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.275f,0.14f,0.01f);
                break;
                
            case 37:
                
                text.setSize(0.08f);
                text.setText("4");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.165f,0.14f,0.01f);
                break;
                
            case 38:
                
                text.setSize(0.08f);
                text.setText("5");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.055f,0.14f,0.01f);
                break;
                
            case 39:
                
                text.setSize(0.08f);
                text.setText("6");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.055f,0.14f,0.01f);
                break;
                
            case 40:
                
                text.setSize(0.08f);
                text.setText("7");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.165f,0.14f,0.01f);
                break;
                
            case 41:
                
                text.setSize(0.08f);
                text.setText("8");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.275f,0.14f,0.01f);
                break;
                
            case 42:
                
                text.setSize(0.08f);
                text.setText("9");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.385f,0.14f,0.01f);
                break;
                
            case 43:
                
                text.setSize(0.08f);
                text.setText("0");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.495f,0.14f,0.01f);
                break;
                
            case 44:
                
                text.setSize(0.08f);
                text.setText(" < ");
                createBackground(0.14f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.405f,-0.19f,0.01f);
                break;
                
            case 45:
                
                text.setSize(0.08f);
                text.setText("\u2191");
                createBackground(0.3f,0.02f+text.getLineHeight());
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.01f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation((parentDisplay.getWidthDimension()/2)-(quad.getWidth()/2)-0.05f,-((parentDisplay.getHeightDimension()/2)+quad.getHeight())+0.33f,0.05f);
                break;
                
            case 46:
                
                text.setSize(0.08f);
                text.setText(" ");
                createBackground(0.30f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0f,-0.30f,0.01f);
                break;
                
            case 47:
                
                text.setSize(0.08f);
                text.setText("-");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.21f,-0.30f,0.01f);
                break;
                
            case 48:
                
                text.setSize(0.08f);
                text.setText("(");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.43f,-0.30f,0.01f);
                break;
                
            case 49:
                
                text.setSize(0.08f);
                text.setText(")");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.32f,-0.30f,0.01f);
                break;
                
            case 50:
                
                text.setSize(0.08f);
                text.setText("\u2191");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.495f,-0.19f,0.01f);
                break;
                
            case 51:
                
                text.setSize(0.08f);
                text.setText(",");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.21f,-0.30f,0.01f);
                break;
                
            case 52:
                
                text.setSize(0.08f);
                text.setText(".");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.32f,-0.30f,0.01f);
                break;
                
            case 53:
                
                text.setSize(0.08f);
                text.setText("Get selected item");
                createBackground(0.2f+text.getLineWidth(),0.2f+text.getLineHeight());
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.01f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0,-0.85f,0.05f);
                break;
                
            case 54:
                
                text.setSize(0.08f);
                text.setText("\u2193");
                createBackground(0.3f,0.02f+text.getLineHeight());
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.01f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation((parentDisplay.getWidthDimension()/2)-(quad.getWidth()/2)-0.05f,-((parentDisplay.getHeightDimension()/2)+quad.getHeight())+0.20f,0.05f);
                break;
                
            case 55:
                
                text.setSize(0.08f);
                text.setText("\u2191");
                createBackground(0.3f,0.02f+text.getLineHeight());
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.01f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-(parentDisplay.getWidthDimension()/2)+(quad.getWidth()/2)+0.05f,-((parentDisplay.getHeightDimension()/2)+quad.getHeight())+0.33f,0.05f);
                break;
                
            case 56:
                
                text.setSize(0.08f);
                text.setText("\u2193");
                createBackground(0.3f,0.02f+text.getLineHeight());
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.01f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-(parentDisplay.getWidthDimension()/2)+(quad.getWidth()/2)+0.05f,-((parentDisplay.getHeightDimension()/2)+quad.getHeight())+0.20f,0.05f);
                break;
                
            case 57:
                
                text.setSize(0.05f);
                text.setText("Type (None)");
                createBackground(0.53f,0.02f+text.getLineHeight());
                
                //System.out.println("Type quad width: "+quad.getWidth());
                
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.01f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation((parentDisplay.getWidthDimension()/2)-(quad.getWidth()/2)-0.05f,((parentDisplay.getHeightDimension()/2)-quad.getHeight())-0.45f,0.05f);
                break;
                
            case 58:
                
                text.setSize(0.05f);
                text.setText("Class (None)");
                createBackground(0.49f,0.02f+text.getLineHeight());
                
                //System.out.println("Class quad width: "+quad.getWidth());
                
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.01f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-(parentDisplay.getWidthDimension()/2)+(quad.getWidth()/2)+0.05f,((parentDisplay.getHeightDimension()/2)-quad.getHeight())-0.06f,0.05f);
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
        
        if(!isToggle)
        
            if(pointed){

                backgroundMat.setColor("Color", POINTED_COLOR);

            }else{

                backgroundMat.setColor("Color", NORMAL_COLOR);

            }
        
    }
    
    public void setToggleActivated(boolean toggleActivated){
        
        this.toggleActivated=toggleActivated;
        
    }
    
    public boolean getToggleActivated(){
        
        return toggleActivated;
        
    }
    
    public boolean isToggle(){
        
        return isToggle;
        
    }

    @Override
    public void write(JmeExporter je) throws IOException {
    }

    @Override
    public void read(JmeImporter ji) throws IOException {
    }
    
}
